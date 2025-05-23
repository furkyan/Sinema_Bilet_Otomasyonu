package com.sinema.dao;

import com.sinema.model.Seans;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeansDAO {
    private Connection connection;
    
    public SeansDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    public boolean seansEkle(Seans seans) {
        String sql = "INSERT INTO seanslar (film_id, salon_id, tarih, saat, fiyat) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, seans.getFilmId());
            stmt.setInt(2, seans.getSalonId());
            stmt.setDate(3, seans.getTarih());
            stmt.setTime(4, seans.getSaat());
            stmt.setDouble(5, seans.getFiyat());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    seans.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Seans eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public boolean seansGuncelle(Seans seans) {
        String sql = "UPDATE seanslar SET film_id = ?, salon_id = ?, tarih = ?, saat = ?, fiyat = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seans.getFilmId());
            stmt.setInt(2, seans.getSalonId());
            stmt.setDate(3, seans.getTarih());
            stmt.setTime(4, seans.getSaat());
            stmt.setDouble(5, seans.getFiyat());
            stmt.setInt(6, seans.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Seans güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public boolean seansSil(int seansId) {
        String sql = "DELETE FROM seanslar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seansId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Seans silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    public List<Seans> filminSeanslariniGetir(int filmId) {
        String sql = "SELECT * FROM seanslar WHERE film_id = ?";
        List<Seans> seanslar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seans seans = new Seans();
                seans.setId(rs.getInt("id"));
                seans.setFilmId(rs.getInt("film_id"));
                seans.setSalonId(rs.getInt("salon_id"));
                seans.setTarih(rs.getDate("tarih"));
                seans.setSaat(rs.getTime("saat"));
                seans.setFiyat(rs.getDouble("fiyat"));
                
                seanslar.add(seans);
            }
            
            return seanslar;
        } catch (SQLException e) {
            System.err.println("Film seansları listelenirken hata: " + e.getMessage());
            return seanslar;
        }
    }
    
    public List<Seans> tariheGoreSeansGetir(Date tarih) {
        String sql = "SELECT * FROM seanslar WHERE tarih = ?";
        List<Seans> seanslar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, tarih);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seans seans = new Seans();
                seans.setId(rs.getInt("id"));
                seans.setFilmId(rs.getInt("film_id"));
                seans.setSalonId(rs.getInt("salon_id"));
                seans.setTarih(rs.getDate("tarih"));
                seans.setSaat(rs.getTime("saat"));
                seans.setFiyat(rs.getDouble("fiyat"));
                
                seanslar.add(seans);
            }
            
            return seanslar;
        } catch (SQLException e) {
            System.err.println("Tarih seansları listelenirken hata: " + e.getMessage());
            return seanslar;
        }
    }
    
    public List<Seans> salonSeanslariniGetir(int salonId) {
        String sql = "SELECT * FROM seanslar WHERE salon_id = ?";
        List<Seans> seanslar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seans seans = new Seans();
                seans.setId(rs.getInt("id"));
                seans.setFilmId(rs.getInt("film_id"));
                seans.setSalonId(rs.getInt("salon_id"));
                seans.setTarih(rs.getDate("tarih"));
                seans.setSaat(rs.getTime("saat"));
                seans.setFiyat(rs.getDouble("fiyat"));
                
                seanslar.add(seans);
            }
            
            return seanslar;
        } catch (SQLException e) {
            System.err.println("Salon seansları listelenirken hata: " + e.getMessage());
            return seanslar;
        }
    }
    
    public List<Seans> aktifSeansGetir() {
        String sql = "SELECT * FROM seanslar WHERE tarih >= CAST(GETDATE() AS DATE) ORDER BY tarih, saat";
        List<Seans> seanslar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Seans seans = new Seans();
                seans.setId(rs.getInt("id"));
                seans.setFilmId(rs.getInt("film_id"));
                seans.setSalonId(rs.getInt("salon_id"));
                seans.setTarih(rs.getDate("tarih"));
                seans.setSaat(rs.getTime("saat"));
                seans.setFiyat(rs.getDouble("fiyat"));
                
                seanslar.add(seans);
            }
            
            return seanslar;
        } catch (SQLException e) {
            System.err.println("Aktif seanslar listelenirken hata: " + e.getMessage());
            return seanslar;
        }
    }
    
    public Seans seansGetir(int seansId) {
        String sql = "SELECT * FROM seanslar WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seansId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Seans seans = new Seans();
                seans.setId(rs.getInt("id"));
                seans.setFilmId(rs.getInt("film_id"));
                seans.setSalonId(rs.getInt("salon_id"));
                seans.setTarih(rs.getDate("tarih"));
                seans.setSaat(rs.getTime("saat"));
                seans.setFiyat(rs.getDouble("fiyat"));
                
                return seans;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Seans aranırken hata: " + e.getMessage());
            return null;
        }
    }
}