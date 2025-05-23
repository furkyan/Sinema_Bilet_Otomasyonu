// Dosya: com/sinema/dao/SalonDAO.java - Eksik metodlar eklendi
package com.sinema.dao;

import com.sinema.model.Salon;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonDAO {
    private Connection connection;
    
    public SalonDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    // Salon ekleme
    public boolean salonEkle(Salon salon) {
        String sql = "INSERT INTO salonlar (ad, kapasite) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, salon.getAd());
            stmt.setInt(2, salon.getKapasite());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    salon.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Salon eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Salon güncelleme
    public boolean salonGuncelle(Salon salon) {
        String sql = "UPDATE salonlar SET ad = ?, kapasite = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, salon.getAd());
            stmt.setInt(2, salon.getKapasite());
            stmt.setInt(3, salon.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Salon güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Salon silme
    public boolean salonSil(int salonId) {
        String sql = "DELETE FROM salonlar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Salon silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Salon getir
    public Salon salonGetir(int salonId) {
        String sql = "SELECT * FROM salonlar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Salon salon = new Salon();
                salon.setId(rs.getInt("id"));
                salon.setAd(rs.getString("ad"));
                salon.setKapasite(rs.getInt("kapasite"));
                
                return salon;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Salon aranırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Tüm salonları getir
    public List<Salon> tumSalonlariGetir() {
        String sql = "SELECT * FROM salonlar";
        List<Salon> salonlar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Salon salon = new Salon();
                salon.setId(rs.getInt("id"));
                salon.setAd(rs.getString("ad"));
                salon.setKapasite(rs.getInt("kapasite"));
                
                salonlar.add(salon);
            }
            
            return salonlar;
        } catch (SQLException e) {
            System.err.println("Salonlar listelenirken hata: " + e.getMessage());
            return salonlar;
        }
    }
    
    // Salonda seans var mı kontrol et
    public boolean salondaSeansVarMi(int salonId) {
        String sql = "SELECT COUNT(*) FROM seanslar WHERE salon_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Salon seans kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
    }
    
    // Boş salonları getir
    public List<Salon> bosSalonlariGetir(String tarih, String saat) {
        String sql = "SELECT * FROM salonlar WHERE id NOT IN " +
                    "(SELECT salon_id FROM seanslar WHERE tarih = ? AND saat = ?)";
        List<Salon> salonlar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tarih);
            stmt.setString(2, saat);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Salon salon = new Salon();
                salon.setId(rs.getInt("id"));
                salon.setAd(rs.getString("ad"));
                salon.setKapasite(rs.getInt("kapasite"));
                
                salonlar.add(salon);
            }
            
            return salonlar;
        } catch (SQLException e) {
            System.err.println("Boş salonlar listelenirken hata: " + e.getMessage());
            return salonlar;
        }
    }
}