// Dosya: com/sinema/model/Bilet.java - Düzeltilmiş version
package com.sinema.model;

import java.sql.Timestamp;

public class Bilet {
    private int id;
    private int kullaniciId;
    private int seansId;
    private String koltukNo;
    private double fiyat;
    private Timestamp satisTarihi;
    private String durum;
    
    // Constructor
    public Bilet() {}
    
    public Bilet(int id, int kullaniciId, int seansId, String koltukNo, 
                 double fiyat, Timestamp satisTarihi, String durum) {
        this.id = id;
        this.kullaniciId = kullaniciId;
        this.seansId = seansId;
        this.koltukNo = koltukNo;
        this.fiyat = fiyat;
        this.satisTarihi = satisTarihi;
        this.durum = durum;
    }
    
    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(int kullaniciId) { this.kullaniciId = kullaniciId; }
    
    public int getSeansId() { return seansId; }
    public void setSeansId(int seansId) { this.seansId = seansId; }
    
    public String getKoltukNo() { return koltukNo; }
    public void setKoltukNo(String koltukNo) { this.koltukNo = koltukNo; }
    
    public double getFiyat() { return fiyat; }
    public void setFiyat(double fiyat) { this.fiyat = fiyat; }
    
    public Timestamp getSatisTarihi() { return satisTarihi; }
    public void setSatisTarihi(Timestamp satisTarihi) { this.satisTarihi = satisTarihi; }
    
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
}