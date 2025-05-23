// Dosya: com/sinema/dao/FilmDAO.java - Afiş desteği eklenmiş
package com.sinema.dao;

import com.sinema.model.Film;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private Connection connection;
    
    public FilmDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    // Film ekleme - afiş desteği ile
    public boolean filmEkle(Film film) {
        String sql = "INSERT INTO filmler (ad, tur, sure, yonetmen, oyuncular, aciklama, afis_yolu, vizyon_tarihi, imdb_puani, fragman_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, film.getAd());
            stmt.setString(2, film.getTur());
            stmt.setInt(3, film.getSure());
            stmt.setString(4, film.getYonetmen());
            stmt.setString(5, film.getOyuncular());
            stmt.setString(6, film.getAciklama());
            stmt.setString(7, film.getAfisYolu());
            
            if (film.getVizyonTarihi() != null) {
                stmt.setDate(8, new java.sql.Date(film.getVizyonTarihi().getTime()));
            } else {
                stmt.setNull(8, Types.DATE);
            }
            
            stmt.setString(9, film.getImdbPuani());
            stmt.setString(10, film.getFragmanUrl());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    film.setId(generatedKeys.getInt(1));
                }
                System.out.println("Film başarıyla eklendi: " + film.getAd());
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Film eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film güncelleme - afiş desteği ile
    public boolean filmGuncelle(Film film) {
        String sql = "UPDATE filmler SET ad = ?, tur = ?, sure = ?, yonetmen = ?, oyuncular = ?, aciklama = ?, afis_yolu = ?, vizyon_tarihi = ?, imdb_puani = ?, fragman_url = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, film.getAd());
            stmt.setString(2, film.getTur());
            stmt.setInt(3, film.getSure());
            stmt.setString(4, film.getYonetmen());
            stmt.setString(5, film.getOyuncular());
            stmt.setString(6, film.getAciklama());
            stmt.setString(7, film.getAfisYolu());
            
            if (film.getVizyonTarihi() != null) {
                stmt.setDate(8, new java.sql.Date(film.getVizyonTarihi().getTime()));
            } else {
                stmt.setNull(8, Types.DATE);
            }
            
            stmt.setString(9, film.getImdbPuani());
            stmt.setString(10, film.getFragmanUrl());
            stmt.setInt(11, film.getId());
            
            boolean sonuc = stmt.executeUpdate() > 0;
            if (sonuc) {
                System.out.println("Film başarıyla güncellendi: " + film.getAd());
            }
            return sonuc;
        } catch (SQLException e) {
            System.err.println("Film güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film silme - seans kontrolü ile
    public boolean filmSil(int filmId) {
        // Önce bu filme ait seanslar var mı kontrol et
        String kontrolSql = "SELECT COUNT(*) FROM seanslar WHERE film_id = ?";
        
        try (PreparedStatement kontrolStmt = connection.prepareStatement(kontrolSql)) {
            kontrolStmt.setInt(1, filmId);
            ResultSet rs = kontrolStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                System.err.println("Bu filme ait seanslar var, önce seansları silin!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Film seans kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
        
        // Film silme
        String sql = "DELETE FROM filmler WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            
            boolean sonuc = stmt.executeUpdate() > 0;
            if (sonuc) {
                System.out.println("Film başarıyla silindi: ID " + filmId);
            }
            return sonuc;
        } catch (SQLException e) {
            System.err.println("Film silinirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Film getir - tüm alanlar ile
    public Film filmGetir(int filmId) {
        String sql = "SELECT * FROM filmler WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return filmOlustur(rs);
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Film aranırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Tüm filmleri getir
    public List<Film> tumFilmleriGetir() {
        String sql = "SELECT * FROM filmler ORDER BY ad";
        List<Film> filmler = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Film film = filmOlustur(rs);
                if (film != null) {
                    filmler.add(film);
                }
            }
            
            return filmler;
        } catch (SQLException e) {
            System.err.println("Filmler listelenirken hata: " + e.getMessage());
            return filmler;
        }
    }
    
    // Vizyondaki filmleri getir
    public List<Film> vizyondakiFilmleriGetir() {
        String sql = "SELECT * FROM filmler WHERE vizyon_tarihi IS NULL OR vizyon_tarihi <= GETDATE() ORDER BY ad";
        List<Film> filmler = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Film film = filmOlustur(rs);
                if (film != null) {
                    filmler.add(film);
                }
            }
            
            return filmler;
        } catch (SQLException e) {
            System.err.println("Vizyondaki filmler listelenirken hata: " + e.getMessage());
            return filmler;
        }
    }
    
    // Film ada göre arama
    public List<Film> filmAra(String anahtar) {
        String sql = "SELECT * FROM filmler WHERE ad LIKE ? OR yonetmen LIKE ? OR tur LIKE ? ORDER BY ad";
        List<Film> filmler = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String aramaDeseni = "%" + anahtar + "%";
            stmt.setString(1, aramaDeseni);
            stmt.setString(2, aramaDeseni);
            stmt.setString(3, aramaDeseni);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Film film = filmOlustur(rs);
                if (film != null) {
                    filmler.add(film);
                }
            }
            
            return filmler;
        } catch (SQLException e) {
            System.err.println("Film aranırken hata: " + e.getMessage());
            return filmler;
        }
    }
    
    // Türe göre filmler
    public List<Film> tureGoreFilmler(String tur) {
        String sql = "SELECT * FROM filmler WHERE tur = ? ORDER BY ad";
        List<Film> filmler = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tur);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Film film = filmOlustur(rs);
                if (film != null) {
                    filmler.add(film);
                }
            }
            
            return filmler;
        } catch (SQLException e) {
            System.err.println("Türe göre filmler listelenirken hata: " + e.getMessage());
            return filmler;
        }
    }
    
    // ResultSet'ten Film nesnesi oluştur
    private Film filmOlustur(ResultSet rs) {
        try {
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setAd(rs.getString("ad"));
            film.setTur(rs.getString("tur"));
            film.setSure(rs.getInt("sure"));
            film.setYonetmen(rs.getString("yonetmen"));
            film.setOyuncular(rs.getString("oyuncular"));
            film.setAciklama(rs.getString("aciklama"));
            film.setAfisYolu(rs.getString("afis_yolu"));
            
            Date vizyonTarihi = rs.getDate("vizyon_tarihi");
            if (vizyonTarihi != null) {
                film.setVizyonTarihi(new java.util.Date(vizyonTarihi.getTime()));
            }
            
            film.setImdbPuani(rs.getString("imdb_puani"));
            film.setFragmanUrl(rs.getString("fragman_url"));
            
            return film;
        } catch (SQLException e) {
            System.err.println("Film oluşturulurken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Film türlerini getir (dropdown için)
    public List<String> filmTurleriniGetir() {
        String sql = "SELECT DISTINCT tur FROM filmler WHERE tur IS NOT NULL ORDER BY tur";
        List<String> turler = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String tur = rs.getString("tur");
                if (tur != null && !tur.trim().isEmpty()) {
                    turler.add(tur);
                }
            }
            
            return turler;
        } catch (SQLException e) {
            System.err.println("Film türleri listelenirken hata: " + e.getMessage());
            return turler;
        }
    }
}