// Dosya: com/sinema/model/Film.java - Afiş desteği ve güncellemeler
package com.sinema.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film {
    private int id;
    private String ad;
    private String yonetmen;
    private String tur;
    private int sure; // dakika cinsinden
    private String aciklama;
    private String afisYolu; // Afiş resim dosyası yolu
    private Date vizyonTarihi;
    private String oyuncular;
    private List<Seans> seanslar;
    private String imdbPuani; // IMDb puanı (opsiyonel)
    private String fragmanUrl; // Fragman linki (opsiyonel)
    
    // Constructor
    public Film() {
        this.seanslar = new ArrayList<>();
    }
    
    public Film(int id, String ad, String yonetmen, String tur, int sure, 
                String aciklama, String afisYolu, Date vizyonTarihi, String oyuncular) {
        this.id = id;
        this.ad = ad;
        this.yonetmen = yonetmen;
        this.tur = tur;
        this.sure = sure;
        this.aciklama = aciklama;
        this.afisYolu = afisYolu;
        this.vizyonTarihi = vizyonTarihi;
        this.oyuncular = oyuncular;
        this.seanslar = new ArrayList<>();
    }
    
    // Film için özel metodlar
    public boolean vizyondaMi() {
        // Filmin şu anda vizyonda olup olmadığını kontrol et
        Date bugun = new Date();
        return vizyonTarihi != null && vizyonTarihi.before(bugun);
    }
    
    public String getKisaAciklama() {
        if (aciklama != null && aciklama.length() > 100) {
            return aciklama.substring(0, 100) + "...";
        }
        return aciklama;
    }
    
    public String getSureText() {
        int saat = sure / 60;
        int dakika = sure % 60;
        return saat + "s " + dakika + "dk";
    }
    
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

    public String getYonetmen() {
        return yonetmen;
    }

    public void setYonetmen(String yonetmen) {
        this.yonetmen = yonetmen;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getSure() {
        return sure;
    }

    public void setSure(int sure) {
        this.sure = sure;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getAfisYolu() {
        return afisYolu;
    }

    public void setAfisYolu(String afisYolu) {
        this.afisYolu = afisYolu;
    }

    public Date getVizyonTarihi() {
        return vizyonTarihi;
    }

    public void setVizyonTarihi(Date vizyonTarihi) {
        this.vizyonTarihi = vizyonTarihi;
    }

    public String getOyuncular() {
        return oyuncular;
    }

    public void setOyuncular(String oyuncular) {
        this.oyuncular = oyuncular;
    }

    public List<Seans> getSeanslar() {
        return seanslar;
    }

    public void setSeanslar(List<Seans> seanslar) {
        this.seanslar = seanslar;
    }
    
    public String getImdbPuani() {
        return imdbPuani;
    }

    public void setImdbPuani(String imdbPuani) {
        this.imdbPuani = imdbPuani;
    }

    public String getFragmanUrl() {
        return fragmanUrl;
    }

    public void setFragmanUrl(String fragmanUrl) {
        this.fragmanUrl = fragmanUrl;
    }
    
    // Seans ekleme metodu
    public void seansEkle(Seans seans) {
        this.seanslar.add(seans);
    }
    
    @Override
    public String toString() {
        return ad + " (" + getSureText() + ")";
    }
}