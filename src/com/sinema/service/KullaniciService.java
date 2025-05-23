// Dosya: com/sinema/service/KullaniciService.java - Eksik metodlar eklendi
package com.sinema.service;

import com.sinema.dao.KullaniciDAO;
import com.sinema.model.Kullanici;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class KullaniciService {
    private KullaniciDAO kullaniciDAO;
    
    public KullaniciService() {
        this.kullaniciDAO = new KullaniciDAO();
    }
    
    // Giriş işlemi
    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        try {
            return kullaniciDAO.girisYap(kullaniciAdi, sifre);
        } catch (Exception e) {
            throw new RuntimeException("Giriş işlemi sırasında hata: " + e.getMessage());
        }
    }
    
    // Kullanıcı ekleme
    public boolean kullaniciEkle(Kullanici kullanici) {
        try {
            return kullaniciDAO.kullaniciEkle(kullanici);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı eklenirken hata: " + e.getMessage());
        }
    }
    
    // Kullanıcı güncelleme
    public boolean kullaniciGuncelle(Kullanici kullanici) {
        try {
            return kullaniciDAO.kullaniciGuncelle(kullanici);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı güncellenirken hata: " + e.getMessage());
        }
    }
    
    // Kullanıcı adı kontrolü
    public boolean kullaniciAdiVarMi(String kullaniciAdi) {
        try {
            return kullaniciDAO.kullaniciAdiVarMi(kullaniciAdi);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı adı kontrolü sırasında hata: " + e.getMessage());
        }
    }
    
    // Tüm kullanıcıları getir
    public List<Kullanici> tumKullanicilariGetir() {
        try {
            return kullaniciDAO.tumKullanicilariGetir();
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcılar yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Kullanıcı rolü güncelleme
    public boolean kullaniciRoluGuncelle(int kullaniciId, String yeniRol) {
        try {
            return kullaniciDAO.kullaniciRoluGuncelle(kullaniciId, yeniRol);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı rolü güncellenirken hata: " + e.getMessage());
        }
    }
}
