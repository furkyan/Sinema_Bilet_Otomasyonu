package com.sinema.controller;

import com.sinema.model.Kullanici;
import com.sinema.service.KullaniciService;
import java.util.List;
import javax.swing.JOptionPane;

public class KullaniciController {
    private KullaniciService kullaniciService;
    private static Kullanici aktifKullanici; // Oturum açmış kullanıcı
    
    public KullaniciController() {
        this.kullaniciService = new KullaniciService();
    }
    
    // Kullanıcı girişi
    public boolean girisYap(String kullaniciAdi, String sifre) {
        try {
            Kullanici kullanici = kullaniciService.girisYap(kullaniciAdi, sifre);
            
            if (kullanici != null) {
                aktifKullanici = kullanici;
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Kullanıcı adı veya şifre hatalı!", 
                    "Giriş Hatası", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Giriş yapılırken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Kullanıcı kaydı
    public boolean kayitOl(String kullaniciAdi, String sifre, String ad, String soyad, String email, String telefon) {
        try {
            // Kullanıcı adı kontrolü
            if (kullaniciService.kullaniciAdiVarMi(kullaniciAdi)) {
                JOptionPane.showMessageDialog(null, 
                    "Bu kullanıcı adı zaten kullanılıyor!", 
                    "Kayıt Hatası", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            Kullanici yeniKullanici = new Kullanici();
            yeniKullanici.setKullaniciAdi(kullaniciAdi);
            yeniKullanici.setSifre(sifre);
            yeniKullanici.setAd(ad);
            yeniKullanici.setSoyad(soyad);
            yeniKullanici.setEmail(email);
            yeniKullanici.setTelefon(telefon);
            yeniKullanici.setRol("Müşteri"); // Varsayılan rol
            
            boolean sonuc = kullaniciService.kullaniciEkle(yeniKullanici);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Kayıt başarılı! Giriş yapabilirsiniz.");
            }
            return sonuc;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kayıt olurken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Profil güncelleme
    public boolean profilGuncelle(String ad, String soyad, String email, String telefon) {
        try {
            if (aktifKullanici == null) {
                JOptionPane.showMessageDialog(null, "Oturum açmanız gerekiyor!");
                return false;
            }
            
            aktifKullanici.setAd(ad);
            aktifKullanici.setSoyad(soyad);
            aktifKullanici.setEmail(email);
            aktifKullanici.setTelefon(telefon);
            
            boolean sonuc = kullaniciService.kullaniciGuncelle(aktifKullanici);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Profil başarıyla güncellendi!");
            }
            return sonuc;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Profil güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Şifre değiştirme
    public boolean sifreDegistir(String eskiSifre, String yeniSifre) {
        try {
            if (aktifKullanici == null) {
                JOptionPane.showMessageDialog(null, "Oturum açmanız gerekiyor!");
                return false;
            }
            
            // Eski şifre kontrolü
            if (!aktifKullanici.getSifre().equals(eskiSifre)) {
                JOptionPane.showMessageDialog(null, "Eski şifre hatalı!");
                return false;
            }
            
            aktifKullanici.setSifre(yeniSifre);
            boolean sonuc = kullaniciService.kullaniciGuncelle(aktifKullanici);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Şifre başarıyla değiştirildi!");
            }
            return sonuc;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Şifre değiştirilirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Oturumu kapat
    public void oturumuKapat() {
        aktifKullanici = null;
        JOptionPane.showMessageDialog(null, "Oturum kapatıldı.");
    }
    
    // Admin: Tüm kullanıcıları getir
    public List<Kullanici> tumKullanicilariGetir() {
        try {
            if (aktifKullanici == null || !aktifKullanici.getRol().equals("Yönetici")) {
                JOptionPane.showMessageDialog(null, "Bu işlem için yetkiniz yok!");
                return null;
            }
            
            return kullaniciService.tumKullanicilariGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kullanıcılar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Admin: Kullanıcı rolü değiştir
    public boolean kullaniciRoluDegistir(int kullaniciId, String yeniRol) {
        try {
            if (aktifKullanici == null || !aktifKullanici.getRol().equals("Yönetici")) {
                JOptionPane.showMessageDialog(null, "Bu işlem için yetkiniz yok!");
                return false;
            }
            
            return kullaniciService.kullaniciRoluGuncelle(kullaniciId, yeniRol);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Rol değiştirilirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Aktif kullanıcıyı getir
    public static Kullanici getAktifKullanici() {
        return aktifKullanici;
    }
    
    // Aktif kullanıcı var mı?
    public static boolean oturumAcikMi() {
        return aktifKullanici != null;
    }
    
    // Kullanıcı admin mi?
    public static boolean adminMi() {
        return aktifKullanici != null && aktifKullanici.getRol().equals("Yönetici");
    }
}