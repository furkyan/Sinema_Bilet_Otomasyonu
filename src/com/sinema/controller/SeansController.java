package com.sinema.controller;

import com.sinema.model.Seans;
import com.sinema.service.SeansService;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.swing.JOptionPane;

public class SeansController {
    private SeansService seansService;
    
    public SeansController() {
        this.seansService = new SeansService();
    }
    
    // Seans ekleme
    public boolean seansEkle(int filmId, int salonId, Date tarih, Time saat, double fiyat) {
        try {
            Seans seans = new Seans();
            seans.setFilmId(filmId);
            seans.setSalonId(salonId);
            seans.setTarih(tarih);
            seans.setSaat(saat);
            seans.setFiyat(fiyat);
            
            return seansService.seansEkle(seans);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seans eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Seans güncelleme
    public boolean seansGuncelle(int id, int filmId, int salonId, Date tarih, Time saat, double fiyat) {
        try {
            Seans seans = new Seans();
            seans.setId(id);
            seans.setFilmId(filmId);
            seans.setSalonId(salonId);
            seans.setTarih(tarih);
            seans.setSaat(saat);
            seans.setFiyat(fiyat);
            
            return seansService.seansGuncelle(seans);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seans güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Seans silme
    public boolean seansSil(int seansId) {
        try {
            int cevap = JOptionPane.showConfirmDialog(null, 
                "Bu seansı silmek istediğinize emin misiniz?\nBu seanstaki tüm biletler de silinecektir!", 
                "Seans Silme", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (cevap == JOptionPane.YES_OPTION) {
                return seansService.seansSil(seansId);
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seans silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film ID'ye göre seansları getir
    public List<Seans> filminSeanslariniGetir(int filmId) {
        try {
            return seansService.filminSeanslariniGetir(filmId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seanslar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Belirli bir tarihteki seansları getir
    public List<Seans> tariheGoreSeansGetir(Date tarih) {
        try {
            return seansService.tariheGoreSeansGetir(tarih);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seanslar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Salon ID'ye göre seansları getir
    public List<Seans> salonSeanslariniGetir(int salonId) {
        try {
            return seansService.salonSeanslariniGetir(salonId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salon seansları yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Aktif seansları getir (bugün ve sonrası)
    public List<Seans> aktifSeansGetir() {
        try {
            return seansService.aktifSeansGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Aktif seanslar yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Seans ID'ye göre seans getir
    public Seans seansGetir(int seansId) {
        try {
            return seansService.seansGetir(seansId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seans bilgisi alınırken hata: " + e.getMessage());
            return null;
        }
    }
}