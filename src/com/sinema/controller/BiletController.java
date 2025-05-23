package com.sinema.controller;

import com.sinema.model.Bilet;
import com.sinema.service.BiletService;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JOptionPane;

public class BiletController {
    private BiletService biletService;
    
    public BiletController() {
        this.biletService = new BiletService();
    }
    
    // Bilet satın alma
    public boolean biletSatinAl(int kullaniciId, int seansId, String koltukNo, double fiyat) {
        try {
            // Koltuk dolu mu kontrol et
            if (biletService.koltukDoluMu(seansId, koltukNo)) {
                JOptionPane.showMessageDialog(null, "Bu koltuk dolu! Lütfen başka bir koltuk seçin.");
                return false;
            }
            
            Bilet bilet = new Bilet();
            bilet.setKullaniciId(kullaniciId);
            bilet.setSeansId(seansId);
            bilet.setKoltukNo(koltukNo);
            bilet.setFiyat(fiyat);
            bilet.setSatisTarihi(new Timestamp(System.currentTimeMillis()));
            bilet.setDurum("Aktif");
            
            boolean sonuc = biletService.biletSat(bilet);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Bilet başarıyla satın alındı!");
            }
            return sonuc;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bilet satın alınırken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Bilet iptal etme
    public boolean biletIptalEt(int biletId) {
        try {
            int cevap = JOptionPane.showConfirmDialog(null, 
                "Bileti iptal etmek istediğinize emin misiniz?", 
                "Bilet İptal", 
                JOptionPane.YES_NO_OPTION);
            
            if (cevap == JOptionPane.YES_OPTION) {
                boolean sonuc = biletService.biletIptalEt(biletId);
                if (sonuc) {
                    JOptionPane.showMessageDialog(null, "Bilet başarıyla iptal edildi.");
                }
                return sonuc;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bilet iptal edilirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Kullanıcının biletlerini getir
    public List<Bilet> kullaniciBiletleriGetir(int kullaniciId) {
        try {
            return biletService.kullaniciBiletleriGetir(kullaniciId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Biletler yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Seanstaki dolu koltukları getir
    public List<String> seansDoluKoltuklarGetir(int seansId) {
        try {
            return biletService.seansDoluKoltuklarGetir(seansId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koltuk bilgileri yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Bilet ID'ye göre bilet getir
    public Bilet biletGetir(int biletId) {
        try {
            return biletService.biletGetir(biletId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bilet bilgisi alınırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Günlük satış raporu
    public double gunlukSatisGetir() {
        try {
            return biletService.gunlukSatisGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Satış bilgileri alınırken hata: " + e.getMessage());
            return 0;
        }
    }
    
    // Aylık satış raporu
    public double aylikSatisGetir() {
        try {
            return biletService.aylikSatisGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Satış bilgileri alınırken hata: " + e.getMessage());
            return 0;
        }
    }
    
    // Seans doluluk oranını hesapla
    public double seansDolulukOraniGetir(int seansId) {
        try {
            return biletService.seansDolulukOrani(seansId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Doluluk oranı hesaplanırken hata: " + e.getMessage());
            return 0;
        }
    }
}