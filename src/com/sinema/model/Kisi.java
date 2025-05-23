// Dosya: com/sinema/model/Kisi.java
package com.sinema.model;

import java.util.Date;

public abstract class Kisi {
    private int id;
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private Date dogumTarihi;
    private String sifre;
    
    // Constructor
    public Kisi() {
    }
    
    public Kisi(int id, String ad, String soyad, String email, String telefon, Date dogumTarihi, String sifre) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.telefon = telefon;
        this.dogumTarihi = dogumTarihi;
        this.sifre = sifre;
    }
    
    // Abstract metod - polymorphism için
    public abstract boolean kimlikDogrula();
    
    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Date getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(Date dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}