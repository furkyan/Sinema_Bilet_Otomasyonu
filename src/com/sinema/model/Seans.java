// Dosya: com/sinema/model/Seans.java - Düzeltilmiş version
package com.sinema.model;

import java.sql.Date;
import java.sql.Time;

public class Seans {
    private int id;
    private int filmId;
    private int salonId;
    private Date tarih;
    private Time saat;
    private double fiyat;
    
    // Constructor
    public Seans() {}
    
    public Seans(int id, int filmId, int salonId, Date tarih, Time saat, double fiyat) {
        this.id = id;
        this.filmId = filmId;
        this.salonId = salonId;
        this.tarih = tarih;
        this.saat = saat;
        this.fiyat = fiyat;
    }
    
    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    
    public int getSalonId() { return salonId; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    
    public Date getTarih() { return tarih; }
    public void setTarih(Date tarih) { this.tarih = tarih; }
    
    public Time getSaat() { return saat; }
    public void setSaat(Time saat) { this.saat = saat; }
    
    public double getFiyat() { return fiyat; }
    public void setFiyat(double fiyat) { this.fiyat = fiyat; }
}
