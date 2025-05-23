// Dosya: com/sinema/model/Koltuk.java - Düzeltilmiş version
package com.sinema.model;

public class Koltuk implements Rezervedilebilir {
    private int id;
    private Salon salon;
    private int siraNo;
    private int koltukNo;
    private KoltukTipi tip;
    private boolean musait;
    
    // Constructor
    public Koltuk() {
        this.musait = true; // Varsayılan olarak müsait
    }
    
    // Rezervedilebilir interface'inden gelen metodlar
    @Override
    public boolean musaitMi() {
        return musait;
    }
    
    @Override
    public void rezerveEt() {
        this.musait = false;
    }
    
    @Override
    public void rezerveIptal() {
        this.musait = true;
    }
    
    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Salon getSalon() { return salon; }
    public void setSalon(Salon salon) { this.salon = salon; }
    
    public int getSiraNo() { return siraNo; }
    public void setSiraNo(int siraNo) { this.siraNo = siraNo; }
    
    public int getKoltukNo() { return koltukNo; }
    public void setKoltukNo(int koltukNo) { this.koltukNo = koltukNo; }
    
    public KoltukTipi getTip() { return tip; }
    public void setTip(KoltukTipi tip) { this.tip = tip; }
    
    public boolean isMusait() { return musait; }
    public void setMusait(boolean musait) { this.musait = musait; }
}