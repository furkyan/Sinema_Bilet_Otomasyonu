// Dosya: com/sinema/service/SalonService.java - Eksik metodlar eklendi
package com.sinema.service;

import com.sinema.dao.SalonDAO;
import com.sinema.model.Salon;
import java.util.List;

public class SalonService {
    private SalonDAO salonDAO;
    
    public SalonService() {
        this.salonDAO = new SalonDAO();
    }
    
    // Salon ekleme
    public boolean salonEkle(Salon salon) {
        try {
            return salonDAO.salonEkle(salon);
        } catch (Exception e) {
            throw new RuntimeException("Salon eklenirken hata: " + e.getMessage());
        }
    }
    
    // Salon güncelleme
    public boolean salonGuncelle(Salon salon) {
        try {
            return salonDAO.salonGuncelle(salon);
        } catch (Exception e) {
            throw new RuntimeException("Salon güncellenirken hata: " + e.getMessage());
        }
    }
    
    // Salon silme
    public boolean salonSil(int salonId) {
        try {
            return salonDAO.salonSil(salonId);
        } catch (Exception e) {
            throw new RuntimeException("Salon silinirken hata: " + e.getMessage());
        }
    }
    
    // Salon getir
    public Salon salonGetir(int salonId) {
        try {
            return salonDAO.salonGetir(salonId);
        } catch (Exception e) {
            throw new RuntimeException("Salon bilgisi alınırken hata: " + e.getMessage());
        }
    }
    
    // Tüm salonları getir
    public List<Salon> tumSalonlariGetir() {
        try {
            return salonDAO.tumSalonlariGetir();
        } catch (Exception e) {
            throw new RuntimeException("Salonlar yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Salonda seans var mı kontrol et
    public boolean salondaSeansVarMi(int salonId) {
        try {
            return salonDAO.salondaSeansVarMi(salonId);
        } catch (Exception e) {
            throw new RuntimeException("Salon seans kontrolü sırasında hata: " + e.getMessage());
        }
    }
    
    // Boş salonları getir
    public List<Salon> bosSalonlariGetir(String tarih, String saat) {
        try {
            return salonDAO.bosSalonlariGetir(tarih, saat);
        } catch (Exception e) {
            throw new RuntimeException("Boş salonlar yüklenirken hata: " + e.getMessage());
        }
    }
}