package com.sinema.controller;

import com.sinema.model.Film;
import com.sinema.service.FilmService;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class FilmController {
    private FilmService filmService;
    
    public FilmController() {
        this.filmService = new FilmService();
    }
    
    // Film ekleme işlemi - afiş desteği ile
    public boolean filmEkle(String ad, String tur, int sure, String yonetmen, String oyuncular) {
        return filmEkle(ad, tur, sure, yonetmen, oyuncular, null, null, null, null, null);
    }
    
    // Tam film ekleme işlemi
    public boolean filmEkle(String ad, String tur, int sure, String yonetmen, String oyuncular, 
                           String aciklama, String afisYolu, Date vizyonTarihi, String imdbPuani, String fragmanUrl) {
        try {
            // Validasyon
            if (ad == null || ad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Film adı boş olamaz!");
                return false;
            }
            
            if (tur == null || tur.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Film türü boş olamaz!");
                return false;
            }
            
            if (sure <= 0) {
                JOptionPane.showMessageDialog(null, "Film süresi 0'dan büyük olmalıdır!");
                return false;
            }
            
            if (yonetmen == null || yonetmen.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Yönetmen adı boş olamaz!");
                return false;
            }
            
            Film film = new Film();
            film.setAd(ad.trim());
            film.setTur(tur.trim());
            film.setSure(sure);
            film.setYonetmen(yonetmen.trim());
            film.setOyuncular(oyuncular != null ? oyuncular.trim() : "");
            film.setAciklama(aciklama != null ? aciklama.trim() : "");
            film.setAfisYolu(afisYolu != null ? afisYolu.trim() : "");
            film.setVizyonTarihi(vizyonTarihi);
            film.setImdbPuani(imdbPuani != null ? imdbPuani.trim() : "");
            film.setFragmanUrl(fragmanUrl != null ? fragmanUrl.trim() : "");
            
            boolean sonuc = filmService.filmEkle(film);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Film başarıyla eklendi!");
            } else {
                JOptionPane.showMessageDialog(null, "Film eklenirken bir hata oluştu!");
            }
            
            return sonuc;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film güncelleme işlemi - afiş desteği ile
    public boolean filmGuncelle(int id, String ad, String tur, int sure, String yonetmen, String oyuncular) {
        return filmGuncelle(id, ad, tur, sure, yonetmen, oyuncular, null, null, null, null, null);
    }
    
    // Tam film güncelleme işlemi
    public boolean filmGuncelle(int id, String ad, String tur, int sure, String yonetmen, String oyuncular,
                               String aciklama, String afisYolu, Date vizyonTarihi, String imdbPuani, String fragmanUrl) {
        try {
            // Validasyon
            if (ad == null || ad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Film adı boş olamaz!");
                return false;
            }
            
            if (tur == null || tur.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Film türü boş olamaz!");
                return false;
            }
            
            if (sure <= 0) {
                JOptionPane.showMessageDialog(null, "Film süresi 0'dan büyük olmalıdır!");
                return false;
            }
            
            if (yonetmen == null || yonetmen.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Yönetmen adı boş olamaz!");
                return false;
            }
            
            Film film = new Film();
            film.setId(id);
            film.setAd(ad.trim());
            film.setTur(tur.trim());
            film.setSure(sure);
            film.setYonetmen(yonetmen.trim());
            film.setOyuncular(oyuncular != null ? oyuncular.trim() : "");
            film.setAciklama(aciklama != null ? aciklama.trim() : "");
            film.setAfisYolu(afisYolu != null ? afisYolu.trim() : "");
            film.setVizyonTarihi(vizyonTarihi);
            film.setImdbPuani(imdbPuani != null ? imdbPuani.trim() : "");
            film.setFragmanUrl(fragmanUrl != null ? fragmanUrl.trim() : "");
            
            boolean sonuc = filmService.filmGuncelle(film);
            
            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Film başarıyla güncellendi!");
            } else {
                JOptionPane.showMessageDialog(null, "Film güncellenirken bir hata oluştu!");
            }
            
            return sonuc;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film silme işlemi
    public boolean filmSil(int filmId) {
        try {
            Film film = filmService.filmGetir(filmId);
            if (film == null) {
                JOptionPane.showMessageDialog(null, "Silinecek film bulunamadı!");
                return false;
            }
            
            int cevap = JOptionPane.showConfirmDialog(null, 
                "'" + film.getAd() + "' filmini silmek istediğinize emin misiniz?\n" +
                "Bu işlem geri alınamaz!", 
                "Film Silme Onayı", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (cevap == JOptionPane.YES_OPTION) {
                boolean sonuc = filmService.filmSil(filmId);
                
                if (sonuc) {
                    JOptionPane.showMessageDialog(null, "Film başarıyla silindi!");
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Film silinemedi!\nBu filme ait seanslar olabilir, önce seansları silin.");
                }
                
                return sonuc;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Tüm filmleri listele
    public List<Film> tumFilmleriGetir() {
        try {
            return filmService.tumFilmleriGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Filmler yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // ID'ye göre film getir
    public Film filmGetir(int filmId) {
        try {
            return filmService.filmGetir(filmId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film bilgisi alınırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Vizyondaki filmleri getir
    public List<Film> vizyondakiFilmleriGetir() {
        try {
            return filmService.vizyondakiFilmleriGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vizyondaki filmler yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Film arama
    public List<Film> filmAra(String anahtar) {
        try {
            if (anahtar == null || anahtar.trim().isEmpty()) {
                return tumFilmleriGetir();
            }
            return filmService.filmAra(anahtar.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film araması sırasında hata: " + e.getMessage());
            return null;
        }
    }
    
    // Türe göre filmler
    public List<Film> tureGoreFilmler(String tur) {
        try {
            if (tur == null || tur.trim().isEmpty()) {
                return tumFilmleriGetir();
            }
            return filmService.tureGoreFilmler(tur.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Türe göre filmler yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Film türlerini getir
    public List<String> filmTurleriniGetir() {
        try {
            return filmService.filmTurleriniGetir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Film türleri yüklenirken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Film doğrulama
    public boolean filmDogrula(String ad, String tur, int sure, String yonetmen) {
        if (ad == null || ad.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Film adı boş olamaz!");
            return false;
        }
        
        if (tur == null || tur.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Film türü boş olamaz!");
            return false;
        }
        
        if (sure <= 0) {
            JOptionPane.showMessageDialog(null, "Film süresi 0'dan büyük olmalıdır!");
            return false;
        }
        
        if (sure > 500) {
            JOptionPane.showMessageDialog(null, "Film süresi 500 dakikadan fazla olamaz!");
            return false;
        }
        
        if (yonetmen == null || yonetmen.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Yönetmen adı boş olamaz!");
            return false;
        }
        
        return true;
    }
}