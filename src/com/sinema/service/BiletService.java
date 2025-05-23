// Dosya: com/sinema/service/BiletService.java - Eksik metodlar eklendi
package com.sinema.service;

import com.sinema.dao.BiletDAO;
import com.sinema.model.Bilet;
import java.util.List;

public class BiletService {
    private BiletDAO biletDAO;
    
    public BiletService() {
        this.biletDAO = new BiletDAO();
    }
    
    // Bilet satış
    public boolean biletSat(Bilet bilet) {
        try {
            return biletDAO.biletSat(bilet);
        } catch (Exception e) {
            throw new RuntimeException("Bilet satılırken hata: " + e.getMessage());
        }
    }
    
    // Bilet iptal et
    public boolean biletIptalEt(int biletId) {
        try {
            return biletDAO.biletIptalEt(biletId);
        } catch (Exception e) {
            throw new RuntimeException("Bilet iptal edilirken hata: " + e.getMessage());
        }
    }
    
    // Kullanıcı biletlerini getir
    public List<Bilet> kullaniciBiletleriGetir(int kullaniciId) {
        try {
            return biletDAO.kullaniciBiletleriGetir(kullaniciId);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı biletleri yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Koltuk dolu mu kontrol et
    public boolean koltukDoluMu(int seansId, String koltukNo) {
        try {
            return biletDAO.koltukDoluMu(seansId, koltukNo);
        } catch (Exception e) {
            throw new RuntimeException("Koltuk kontrolü sırasında hata: " + e.getMessage());
        }
    }
    
    // Seans dolu koltukları getir
    public List<String> seansDoluKoltuklarGetir(int seansId) {
        try {
            return biletDAO.seansDoluKoltuklarGetir(seansId);
        } catch (Exception e) {
            throw new RuntimeException("Dolu koltuklar yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Bilet getir
    public Bilet biletGetir(int biletId) {
        try {
            return biletDAO.biletGetir(biletId);
        } catch (Exception e) {
            throw new RuntimeException("Bilet bilgisi alınırken hata: " + e.getMessage());
        }
    }
    
    // Günlük satış
    public double gunlukSatisGetir() {
        try {
            return biletDAO.gunlukSatisGetir();
        } catch (Exception e) {
            throw new RuntimeException("Günlük satış hesaplanırken hata: " + e.getMessage());
        }
    }
    
    // Aylık satış
    public double aylikSatisGetir() {
        try {
            return biletDAO.aylikSatisGetir();
        } catch (Exception e) {
            throw new RuntimeException("Aylık satış hesaplanırken hata: " + e.getMessage());
        }
    }
    
    // Seans doluluk oranı
    public double seansDolulukOrani(int seansId) {
        try {
            return biletDAO.seansDolulukOrani(seansId);
        } catch (Exception e) {
            throw new RuntimeException("Doluluk oranı hesaplanırken hata: " + e.getMessage());
        }
    }
}