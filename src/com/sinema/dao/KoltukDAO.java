// Dosya: com/sinema/dao/KoltukDAO.java
package com.sinema.dao;

import com.sinema.model.Koltuk;
import com.sinema.model.KoltukTipi;
import com.sinema.model.Salon;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KoltukDAO {
    private Connection connection;
    private SalonDAO salonDAO;
    
    public KoltukDAO() {
        this.connection = DBConnection.getConnection();
        this.salonDAO = new SalonDAO();
    }
    
    public boolean koltukEkle(Koltuk koltuk) {
        String sql = "INSERT INTO koltuklar (salon_id, sira_no, koltuk_no, tip) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, koltuk.getSalon().getId());
            stmt.setInt(2, koltuk.getSiraNo());
            stmt.setInt(3, koltuk.getKoltukNo());
            stmt.setString(4, koltuk.getTip().toString());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    koltuk.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Koltuk eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public boolean koltukGuncelle(Koltuk koltuk) {
        String sql = "UPDATE koltuklar SET salon_id = ?, sira_no = ?, koltuk_no = ?, tip = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, koltuk.getSalon().getId());
            stmt.setInt(2, koltuk.getSiraNo());
            stmt.setInt(3, koltuk.getKoltukNo());
            stmt.setString(4, koltuk.getTip().toString());
            stmt.setInt(5, koltuk.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Koltuk güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public boolean koltukSil(int koltukId) {
        String sql = "DELETE FROM koltuklar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, koltukId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Koltuk silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public Koltuk koltukGetir(int koltukId) {
        String sql = "SELECT * FROM koltuklar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, koltukId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return koltukOlustur(rs);
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Koltuk aranırken hata: " + e.getMessage());
            return null;
        }
    }
    
    public List<Koltuk> tumKoltuklariGetir() {
        String sql = "SELECT * FROM koltuklar";
        List<Koltuk> koltuklar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Koltuk koltuk = koltukOlustur(rs);
                if (koltuk != null) {
                    koltuklar.add(koltuk);
                }
            }
            
            return koltuklar;
        } catch (SQLException e) {
            System.err.println("Koltuklar listelenirken hata: " + e.getMessage());
            return koltuklar;
        }
    }
    
    public List<Koltuk> salonKoltuklariniGetir(int salonId) {
        String sql = "SELECT * FROM koltuklar WHERE salon_id = ? ORDER BY sira_no, koltuk_no";
        List<Koltuk> koltuklar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Koltuk koltuk = koltukOlustur(rs);
                if (koltuk != null) {
                    koltuklar.add(koltuk);
                }
            }
            
            return koltuklar;
        } catch (SQLException e) {
            System.err.println("Salon koltukları listelenirken hata: " + e.getMessage());
            return koltuklar;
        }
    }
    
    public boolean koltukMusaitMi(int salonId, int siraNo, int koltukNo, int seansId) {
        String sql = "SELECT COUNT(*) FROM biletler b " +
                    "INNER JOIN koltuklar k ON b.koltuk_id = k.id " +
                    "WHERE k.salon_id = ? AND k.sira_no = ? AND k.koltuk_no = ? " +
                    "AND b.seans_id = ? AND b.durum = 'Aktif'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            stmt.setInt(2, siraNo);
            stmt.setInt(3, koltukNo);
            stmt.setInt(4, seansId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0; // 0 ise müsait
            }
            
            return true; // Varsayılan olarak müsait
        } catch (SQLException e) {
            System.err.println("Koltuk müsaitlik kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
    }
    
    // ResultSet'ten Koltuk nesnesi oluştur
    private Koltuk koltukOlustur(ResultSet rs) {
        try {
            Koltuk koltuk = new Koltuk();
            koltuk.setId(rs.getInt("id"));
            koltuk.setSiraNo(rs.getInt("sira_no"));
            koltuk.setKoltukNo(rs.getInt("koltuk_no"));
            
            // Koltuk tipini belirle
            String tipStr = rs.getString("tip");
            if ("VIP".equals(tipStr)) {
                koltuk.setTip(KoltukTipi.VIP);
            } else if ("CIFT".equals(tipStr)) {
                koltuk.setTip(KoltukTipi.CIFT);
            } else {
                koltuk.setTip(KoltukTipi.NORMAL);
            }
            
            // Salonu al
            int salonId = rs.getInt("salon_id");
            Salon salon = salonDAO.salonGetir(salonId);
            koltuk.setSalon(salon);
            
            return koltuk;
        } catch (SQLException e) {
            System.err.println("Koltuk oluşturulurken hata: " + e.getMessage());
            return null;
        }
    }
}