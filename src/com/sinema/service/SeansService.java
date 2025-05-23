// Dosya: com/sinema/service/SeansService.java - Eksik metodlar eklendi
package com.sinema.service;

import com.sinema.dao.SeansDAO;
import com.sinema.model.Seans;
import java.sql.Date;
import java.util.List;

public class SeansService {
    private SeansDAO seansDAO;
    
    public SeansService() {
        this.seansDAO = new SeansDAO();
    }
    
    // Seans ekleme
    public boolean seansEkle(Seans seans) {
        try {
            return seansDAO.seansEkle(seans);
        } catch (Exception e) {
            throw new RuntimeException("Seans eklenirken hata: " + e.getMessage());
        }
    }
    
    // Seans güncelleme
    public boolean seansGuncelle(Seans seans) {
        try {
            return seansDAO.seansGuncelle(seans);
        } catch (Exception e) {
            throw new RuntimeException("Seans güncellenirken hata: " + e.getMessage());
        }
    }
    
    // Seans silme
    public boolean seansSil(int seansId) {
        try {
            return seansDAO.seansSil(seansId);
        } catch (Exception e) {
            throw new RuntimeException("Seans silinirken hata: " + e.getMessage());
        }
    }
    
    // Filmin seanslarını getir
    public List<Seans> filminSeanslariniGetir(int filmId) {
        try {
            return seansDAO.filminSeanslariniGetir(filmId);
        } catch (Exception e) {
            throw new RuntimeException("Film seansları yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Tarihe göre seansları getir
    public List<Seans> tariheGoreSeansGetir(Date tarih) {
        try {
            return seansDAO.tariheGoreSeansGetir(tarih);
        } catch (Exception e) {
            throw new RuntimeException("Tarih seansları yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Salon seanslarını getir
    public List<Seans> salonSeanslariniGetir(int salonId) {
        try {
            return seansDAO.salonSeanslariniGetir(salonId);
        } catch (Exception e) {
            throw new RuntimeException("Salon seansları yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Aktif seansları getir
    public List<Seans> aktifSeansGetir() {
        try {
            return seansDAO.aktifSeansGetir();
        } catch (Exception e) {
            throw new RuntimeException("Aktif seanslar yüklenirken hata: " + e.getMessage());
        }
    }
    
    // Seans getir
    public Seans seansGetir(int seansId) {
        try {
            return seansDAO.seansGetir(seansId);
        } catch (Exception e) {
            throw new RuntimeException("Seans bilgisi alınırken hata: " + e.getMessage());
        }
    }
}