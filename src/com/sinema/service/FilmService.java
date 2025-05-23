// Dosya: com/sinema/service/FilmService.java - Afiş desteği ve yeni metodlar
package com.sinema.service;

import com.sinema.dao.FilmDAO;
import com.sinema.model.Film;
import java.util.List;

public class FilmService {
    private FilmDAO filmDAO;
    
    public FilmService() {
        this.filmDAO = new FilmDAO();
    }
    
    // Film ekleme
    public boolean filmEkle(Film film) {
        try {
            // Business logic validasyonları
            if (film == null) {
                throw new IllegalArgumentException("Film bilgisi boş olamaz!");
            }
            
            if (film.getAd() == null || film.getAd().trim().isEmpty()) {
                throw new IllegalArgumentException("Film adı boş olamaz!");
            }
            
            if (film.getTur() == null || film.getTur().trim().isEmpty()) {
                throw new IllegalArgumentException("Film türü boş olamaz!");
            }
            
            if (film.getSure() <= 0) {
                throw new IllegalArgumentException("Film süresi pozitif olmalıdır!");
            }
            
            if (film.getSure() > 500) {
                throw new IllegalArgumentException("Film süresi 500 dakikayı geçemez!");
            }
            
            if (film.getYonetmen() == null || film.getYonetmen().trim().isEmpty()) {
                throw new IllegalArgumentException("Yönetmen adı boş olamaz!");
            }
            
            // Aynı isimde film var mı kontrol et
            List<Film> mevcutFilmler = filmDAO.filmAra(film.getAd());
            for (Film mevcutFilm : mevcutFilmler) {
                if (mevcutFilm.getAd().equalsIgnoreCase(film.getAd().trim()) && 
                    mevcutFilm.getYonetmen().equalsIgnoreCase(film.getYonetmen().trim())) {
                    throw new IllegalArgumentException("Bu isimde ve yönetmende bir film zaten mevcut!");
                }
            }
            
            return filmDAO.filmEkle(film);
        } catch (Exception e) {
            throw new RuntimeException("Film eklenirken hata: " + e.getMessage(), e);
        }
    }
    
    // Film güncelleme
    public boolean filmGuncelle(Film film) {
        try {
            if (film == null || film.getId() <= 0) {
                throw new IllegalArgumentException("Geçersiz film bilgisi!");
            }
            
            // Mevcut film var mı kontrol et
            Film mevcutFilm = filmDAO.filmGetir(film.getId());
            if (mevcutFilm == null) {
                throw new IllegalArgumentException("Güncellenecek film bulunamadı!");
            }
            
            // Aynı validasyonları uygula
            if (film.getAd() == null || film.getAd().trim().isEmpty()) {
                throw new IllegalArgumentException("Film adı boş olamaz!");
            }
            
            if (film.getTur() == null || film.getTur().trim().isEmpty()) {
                throw new IllegalArgumentException("Film türü boş olamaz!");
            }
            
            if (film.getSure() <= 0) {
                throw new IllegalArgumentException("Film süresi pozitif olmalıdır!");
            }
            
            if (film.getSure() > 500) {
                throw new IllegalArgumentException("Film süresi 500 dakikayı geçemez!");
            }
            
            if (film.getYonetmen() == null || film.getYonetmen().trim().isEmpty()) {
                throw new IllegalArgumentException("Yönetmen adı boş olamaz!");
            }
            
            return filmDAO.filmGuncelle(film);
        } catch (Exception e) {
            throw new RuntimeException("Film güncellenirken hata: " + e.getMessage(), e);
        }
    }
    
    // Film silme
    public boolean filmSil(int filmId) {
        try {
            if (filmId <= 0) {
                throw new IllegalArgumentException("Geçersiz film ID!");
            }
            
            // Film var mı kontrol et
            Film film = filmDAO.filmGetir(filmId);
            if (film == null) {
                throw new IllegalArgumentException("Silinecek film bulunamadı!");
            }
            
            return filmDAO.filmSil(filmId);
        } catch (Exception e) {
            throw new RuntimeException("Film silinirken hata: " + e.getMessage(), e);
        }
    }
    
    // Film getir
    public Film filmGetir(int filmId) {
        try {
            if (filmId <= 0) {
                throw new IllegalArgumentException("Geçersiz film ID!");
            }
            
            return filmDAO.filmGetir(filmId);
        } catch (Exception e) {
            throw new RuntimeException("Film bilgisi alınırken hata: " + e.getMessage(), e);
        }
    }
    
    // Tüm filmleri getir
    public List<Film> tumFilmleriGetir() {
        try {
            return filmDAO.tumFilmleriGetir();
        } catch (Exception e) {
            throw new RuntimeException("Filmler yüklenirken hata: " + e.getMessage(), e);
        }
    }
    
    // Vizyondaki filmleri getir
    public List<Film> vizyondakiFilmleriGetir() {
        try {
            return filmDAO.vizyondakiFilmleriGetir();
        } catch (Exception e) {
            throw new RuntimeException("Vizyondaki filmler yüklenirken hata: " + e.getMessage(), e);
        }
    }
    
    // Film arama
    public List<Film> filmAra(String anahtar) {
        try {
            if (anahtar == null || anahtar.trim().isEmpty()) {
                return tumFilmleriGetir();
            }
            
            return filmDAO.filmAra(anahtar.trim());
        } catch (Exception e) {
            throw new RuntimeException("Film araması sırasında hata: " + e.getMessage(), e);
        }
    }
    
    // Türe göre filmler
    public List<Film> tureGoreFilmler(String tur) {
        try {
            if (tur == null || tur.trim().isEmpty()) {
                return tumFilmleriGetir();
            }
            
            return filmDAO.tureGoreFilmler(tur.trim());
        } catch (Exception e) {
            throw new RuntimeException("Türe göre filmler yüklenirken hata: " + e.getMessage(), e);
        }
    }
    
    // Film türlerini getir
    public List<String> filmTurleriniGetir() {
        try {
            return filmDAO.filmTurleriniGetir();
        } catch (Exception e) {
            throw new RuntimeException("Film türleri yüklenirken hata: " + e.getMessage(), e);
        }
    }
    
    // İstatistikler için metodlar
    public int toplamFilmSayisi() {
        try {
            List<Film> filmler = filmDAO.tumFilmleriGetir();
            return filmler != null ? filmler.size() : 0;
        } catch (Exception e) {
            throw new RuntimeException("Film sayısı hesaplanırken hata: " + e.getMessage(), e);
        }
    }
    
    public int vizyondakiFilmSayisi() {
        try {
            List<Film> filmler = filmDAO.vizyondakiFilmleriGetir();
            return filmler != null ? filmler.size() : 0;
        } catch (Exception e) {
            throw new RuntimeException("Vizyondaki film sayısı hesaplanırken hata: " + e.getMessage(), e);
        }
    }
    
    public double ortalamaSure() {
        try {
            List<Film> filmler = filmDAO.tumFilmleriGetir();
            if (filmler == null || filmler.isEmpty()) {
                return 0.0;
            }
            
            int toplamSure = 0;
            for (Film film : filmler) {
                toplamSure += film.getSure();
            }
            
            return (double) toplamSure / filmler.size();
        } catch (Exception e) {
            throw new RuntimeException("Ortalama süre hesaplanırken hata: " + e.getMessage(), e);
        }
    }
}