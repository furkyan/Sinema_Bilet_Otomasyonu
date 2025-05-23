// Dosya: com/sinema/model/Musteri.java
package com.sinema.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Musteri extends Kisi {
    private int sadakatPuani;
    private List<Bilet> biletler;
    
    // Constructor
    public Musteri() {
        this.biletler = new ArrayList<>();
    }
    
    public Musteri(int id, String ad, String soyad, String email, String telefon, 
                   Date dogumTarihi, String sifre, int sadakatPuani) {
        super(id, ad, soyad, email, telefon, dogumTarihi, sifre);
        this.sadakatPuani = sadakatPuani;
        this.biletler = new ArrayList<>();
    }
    
    // Kisi sınıfından override edilen metod
    @Override
    public boolean kimlikDogrula() {
        // Müşteri için kimlik doğrulama işlemi
        return getEmail() != null && !getEmail().isEmpty() && getSifre() != null && !getSifre().isEmpty();
    }
    
    // Getter ve Setter metodları
    public int getSadakatPuani() {
        return sadakatPuani;
    }

    public void setSadakatPuani(int sadakatPuani) {
        this.sadakatPuani = sadakatPuani;
    }

    public List<Bilet> getBiletler() {
        return biletler;
    }

    public void setBiletler(List<Bilet> biletler) {
        this.biletler = biletler;
    }
    
    // Bilet ekleme metodu
    public void biletEkle(Bilet bilet) {
        this.biletler.add(bilet);
    }
}