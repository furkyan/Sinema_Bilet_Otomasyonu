// Dosya: com/sinema/model/Yonetici.java
package com.sinema.model;

import java.util.Date;

public class Yonetici extends Kisi {
    private String yetkiSeviyesi;
    private Date sonGiris;
    
    // Constructor
    public Yonetici() {
    }
    
    public Yonetici(int id, String ad, String soyad, String email, String telefon, 
                    Date dogumTarihi, String sifre, String yetkiSeviyesi) {
        super(id, ad, soyad, email, telefon, dogumTarihi, sifre);
        this.yetkiSeviyesi = yetkiSeviyesi;
        this.sonGiris = new Date(); // Şimdiki zaman
    }
    
    // Kisi sınıfından override edilen metod
    @Override
    public boolean kimlikDogrula() {
        // Yönetici için kimlik doğrulama işlemi
        boolean temelKontrol = getEmail() != null && !getEmail().isEmpty() && getSifre() != null && !getSifre().isEmpty();
        return temelKontrol && yetkiSeviyesi != null && !yetkiSeviyesi.isEmpty();
    }
    
    // Getter ve Setter metodları
    public String getYetkiSeviyesi() {
        return yetkiSeviyesi;
    }

    public void setYetkiSeviyesi(String yetkiSeviyesi) {
        this.yetkiSeviyesi = yetkiSeviyesi;
    }

    public Date getSonGiris() {
        return sonGiris;
    }

    public void setSonGiris(Date sonGiris) {
        this.sonGiris = sonGiris;
    }
}