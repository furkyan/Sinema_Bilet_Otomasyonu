// Dosya: com/sinema/dao/KullaniciDAO.java - Eksik metodlar eklendi
package com.sinema.dao;

import com.sinema.model.Kullanici;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KullaniciDAO {
    private Connection connection;
    
    public KullaniciDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    // Giriş işlemi
    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        String sql = "SELECT * FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kullaniciAdi);
            stmt.setString(2, sifre);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Kullanici kullanici = new Kullanici();
                kullanici.setId(rs.getInt("id"));
                kullanici.setKullaniciAdi(rs.getString("kullanici_adi"));
                kullanici.setSifre(rs.getString("sifre"));
                kullanici.setAd(rs.getString("ad"));
                kullanici.setSoyad(rs.getString("soyad"));
                kullanici.setEmail(rs.getString("email"));
                kullanici.setTelefon(rs.getString("telefon"));
                kullanici.setRol(rs.getString("rol"));
                
                return kullanici;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Giriş yapılırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Kullanıcı ekleme
    public boolean kullaniciEkle(Kullanici kullanici) {
        String sql = "INSERT INTO kullanicilar (kullanici_adi, sifre, ad, soyad, email, telefon, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, kullanici.getKullaniciAdi());
            stmt.setString(2, kullanici.getSifre());
            stmt.setString(3, kullanici.getAd());
            stmt.setString(4, kullanici.getSoyad());
            stmt.setString(5, kullanici.getEmail());
            stmt.setString(6, kullanici.getTelefon());
            stmt.setString(7, kullanici.getRol());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    kullanici.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Kullanıcı eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Kullanıcı güncelleme
    public boolean kullaniciGuncelle(Kullanici kullanici) {
        String sql = "UPDATE kullanicilar SET kullanici_adi = ?, sifre = ?, ad = ?, soyad = ?, email = ?, telefon = ?, rol = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kullanici.getKullaniciAdi());
            stmt.setString(2, kullanici.getSifre());
            stmt.setString(3, kullanici.getAd());
            stmt.setString(4, kullanici.getSoyad());
            stmt.setString(5, kullanici.getEmail());
            stmt.setString(6, kullanici.getTelefon());
            stmt.setString(7, kullanici.getRol());
            stmt.setInt(8, kullanici.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Kullanıcı güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Kullanıcı adı var mı kontrol et
    public boolean kullaniciAdiVarMi(String kullaniciAdi) {
        String sql = "SELECT COUNT(*) FROM kullanicilar WHERE kullanici_adi = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kullaniciAdi);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Kullanıcı adı kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
    }
    
    // Tüm kullanıcıları getir
    public List<Kullanici> tumKullanicilariGetir() {
        String sql = "SELECT * FROM kullanicilar";
        List<Kullanici> kullanicilar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Kullanici kullanici = new Kullanici();
                kullanici.setId(rs.getInt("id"));
                kullanici.setKullaniciAdi(rs.getString("kullanici_adi"));
                kullanici.setSifre(rs.getString("sifre"));
                kullanici.setAd(rs.getString("ad"));
                kullanici.setSoyad(rs.getString("soyad"));
                kullanici.setEmail(rs.getString("email"));
                kullanici.setTelefon(rs.getString("telefon"));
                kullanici.setRol(rs.getString("rol"));
                
                kullanicilar.add(kullanici);
            }
            
            return kullanicilar;
        } catch (SQLException e) {
            System.err.println("Kullanıcılar listelenirken hata: " + e.getMessage());
            return kullanicilar;
        }
    }
    
    // Kullanıcı rolü güncelle
    public boolean kullaniciRoluGuncelle(int kullaniciId, String yeniRol) {
        String sql = "UPDATE kullanicilar SET rol = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, yeniRol);
            stmt.setInt(2, kullaniciId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Kullanıcı rolü güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
}
