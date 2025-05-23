// Dosya: com/sinema/model/Salon.java
package com.sinema.model;

import java.util.ArrayList;
import java.util.List;

public class Salon {
    private int id;
    private String ad;
    private int kapasite;
    private int satir;
    private int sutun;
    private List<Koltuk> koltuklar;
    private boolean vip;
    
    // Constructor
    public Salon() {
        this.koltuklar = new ArrayList<>();
    }
    
    public Salon(int id, String ad, int satir, int sutun, boolean vip) {
        this.id = id;
        this.ad = ad;
        this.satir = satir;
        this.sutun = sutun;
        this.kapasite = satir * sutun;
        this.vip = vip;
        this.koltuklar = new ArrayList<>();
    }
    
    // Salon için özel metodlar
    public List<Koltuk> bosKoltuklariGetir() {
        List<Koltuk> bosKoltuklar = new ArrayList<>();
        for (Koltuk koltuk : koltuklar) {
            if (koltuk.musaitMi()) {
                bosKoltuklar.add(koltuk);
            }
        }
        return bosKoltuklar;
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

    public int getKapasite() {
        return kapasite;
    }

    public void setKapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    public int getSatir() {
        return satir;
    }

    public void setSatir(int satir) {
        this.satir = satir;
        this.kapasite = this.satir * this.sutun;
    }

    public int getSutun() {
        return sutun;
    }

    public void setSutun(int sutun) {
        this.sutun = sutun;
        this.kapasite = this.satir * this.sutun;
    }

    public List<Koltuk> getKoltuklar() {
        return koltuklar;
    }

    public void setKoltuklar(List<Koltuk> koltuklar) {
        this.koltuklar = koltuklar;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
    
    // Koltuk ekleme metodu
    public void koltukEkle(Koltuk koltuk) {
        this.koltuklar.add(koltuk);
    }
    
    @Override
    public String toString() {
        return ad;
    }
}