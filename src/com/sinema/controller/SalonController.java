package com.sinema.controller;

import com.sinema.model.Salon;
import com.sinema.service.SalonService;
import java.util.List;
import javax.swing.JOptionPane;

public class SalonController {
    private SalonService salonService;
    
    public SalonController() {
        this.salonService = new SalonService();
    }
    
    // Salon ekleme
    public boolean salonEkle(String ad, int kapasite) {
        try {
            Salon salon = new Salon();
            salon.setAd(ad);
            salon.setKapasite(kapasite);
            
            return salonService.salonEkle(salon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salon eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Salon güncelleme
    public boolean salonGuncelle(int id, String ad, int kapasite) {
        try {
            Salon salon = new Salon();
            salon.setId(id);
            salon.setAd(ad);
            salon.setKapasite(kapasite);
            
            return salonService.salonGuncelle(salon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salon güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Salon silme
    public boolean salonSil(int salonId) {
        try {
            // Salonda seans var mı kontrol et
            if (salonService.salondaSeansVarMi(salonId)) {
                JOptionPane.showMessageDialog(null, 
                    "Bu salonda aktif seanslar var! Önce seansları silmelisiniz.", 
                    "Uyarı", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            int cevap = JOptionPane.showConfirmDialog(null, 
                "Salonu silmek istediğinize emin misiniz?", 
                "Salon Silme", 
                JOptionPane.YES_NO_OPTION);
            
            if (cevap == JOptionPane.YES_OPTION) {
                return salonService.salonSil(salonId);
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salon silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Tüm salonları getir
    public List<Salon> tumSalonlariGetir() {
        try {
            return salonService.tumSalonlariGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salonlar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // ID'ye göre salon getir
    public Salon salonGetir(int salonId) {
        try {
            return salonService.salonGetir(salonId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salon bilgisi alınırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Boş salonları getir (belirli tarih ve saat için)
    public List<Salon> bosSalonlariGetir(String tarih, String saat) {
        try {
            return salonService.bosSalonlariGetir(tarih, saat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Boş salonlar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
}