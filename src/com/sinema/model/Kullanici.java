// Dosya: com/sinema/model/Kullanici.java
package com.sinema.model;

public class Kullanici {
    private int id;
    private String kullaniciAdi;
    private String sifre;
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private String rol;
    
    // Constructor
    public Kullanici() {}
    
    public Kullanici(int id, String kullaniciAdi, String sifre, String ad, String soyad, 
                     String email, String telefon, String rol) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.telefon = telefon;
        this.rol = rol;
    }
    
    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getKullaniciAdi() { return kullaniciAdi; }
    public void setKullaniciAdi(String kullaniciAdi) { this.kullaniciAdi = kullaniciAdi; }
    
    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }
    
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
