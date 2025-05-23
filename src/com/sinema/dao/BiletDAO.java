// Dosya: com/sinema/dao/BiletDAO.java - SQL Server için tam versiyon
package com.sinema.dao;

import com.sinema.model.Bilet;
import com.sinema.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BiletDAO {
    private Connection connection;
    
    public BiletDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    // Bilet satış
    public boolean biletSat(Bilet bilet) {
        String sql = "INSERT INTO biletler (kullanici_id, seans_id, koltuk_no, fiyat, satis_tarihi, durum) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bilet.getKullaniciId());
            stmt.setInt(2, bilet.getSeansId());
            stmt.setString(3, bilet.getKoltukNo());
            stmt.setDouble(4, bilet.getFiyat());
            stmt.setTimestamp(5, bilet.getSatisTarihi());
            stmt.setString(6, bilet.getDurum());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bilet.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Bilet satılırken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Bilet iptal et
    public boolean biletIptalEt(int biletId) {
        String sql = "UPDATE biletler SET durum = 'İptal' WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, biletId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Bilet iptal edilirken hata: " + e.getMessage());
            return false;
        }
    }
    
    // Kullanıcı biletlerini getir
    public List<Bilet> kullaniciBiletleriGetir(int kullaniciId) {
        String sql = "SELECT * FROM biletler WHERE kullanici_id = ?";
        List<Bilet> biletler = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.setId(rs.getInt("id"));
                bilet.setKullaniciId(rs.getInt("kullanici_id"));
                bilet.setSeansId(rs.getInt("seans_id"));
                bilet.setKoltukNo(rs.getString("koltuk_no"));
                bilet.setFiyat(rs.getDouble("fiyat"));
                bilet.setSatisTarihi(rs.getTimestamp("satis_tarihi"));
                bilet.setDurum(rs.getString("durum"));
                
                biletler.add(bilet);
            }
            
            return biletler;
        } catch (SQLException e) {
            System.err.println("Kullanıcı biletleri listelenirken hata: " + e.getMessage());
            return biletler;
        }
    }
    
    // Koltuk dolu mu kontrol et
    public boolean koltukDoluMu(int seansId, String koltukNo) {
        String sql = "SELECT COUNT(*) FROM biletler WHERE seans_id = ? AND koltuk_no = ? AND durum = 'Aktif'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seansId);
            stmt.setString(2, koltukNo);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Koltuk kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
    }
    
    // Seans dolu koltukları getir
    public List<String> seansDoluKoltuklarGetir(int seansId) {
        String sql = "SELECT koltuk_no FROM biletler WHERE seans_id = ? AND durum = 'Aktif'";
        List<String> doluKoltuklar = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seansId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                doluKoltuklar.add(rs.getString("koltuk_no"));
            }
            
            return doluKoltuklar;
        } catch (SQLException e) {
            System.err.println("Dolu koltuklar listelenirken hata: " + e.getMessage());
            return doluKoltuklar;
        }
    }
    
    // Bilet getir
    public Bilet biletGetir(int biletId) {
        String sql = "SELECT * FROM biletler WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, biletId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.setId(rs.getInt("id"));
                bilet.setKullaniciId(rs.getInt("kullanici_id"));
                bilet.setSeansId(rs.getInt("seans_id"));
                bilet.setKoltukNo(rs.getString("koltuk_no"));
                bilet.setFiyat(rs.getDouble("fiyat"));
                bilet.setSatisTarihi(rs.getTimestamp("satis_tarihi"));
                bilet.setDurum(rs.getString("durum"));
                
                return bilet;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Bilet aranırken hata: " + e.getMessage());
            return null;
        }
    }
    
    // Günlük satış - SQL Server için düzeltildi
    public double gunlukSatisGetir() {
        String sql = "SELECT ISNULL(SUM(fiyat), 0) FROM biletler " +
                    "WHERE CAST(satis_tarihi AS DATE) = CAST(GETDATE() AS DATE) " +
                    "AND durum = 'Aktif'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
            return 0.0;
        } catch (SQLException e) {
            System.err.println("Günlük satış hesaplanırken hata: " + e.getMessage());
            return 0.0;
        }
    }
    
    // Aylık satış - SQL Server için düzeltildi
    public double aylikSatisGetir() {
        String sql = "SELECT ISNULL(SUM(fiyat), 0) FROM biletler " +
                    "WHERE MONTH(satis_tarihi) = MONTH(GETDATE()) " +
                    "AND YEAR(satis_tarihi) = YEAR(GETDATE()) " +
                    "AND durum = 'Aktif'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
            return 0.0;
        } catch (SQLException e) {
            System.err.println("Aylık satış hesaplanırken hata: " + e.getMessage());
            return 0.0;
        }
    }
    
    // Seans doluluk oranı
    public double seansDolulukOrani(int seansId) {
        // Önce salon kapasitesini al
        String kapasiteSql = "SELECT s.kapasite FROM salonlar s " +
                           "INNER JOIN seanslar sn ON s.id = sn.salon_id " +
                           "WHERE sn.id = ?";
        
        String biletSql = "SELECT COUNT(*) FROM biletler WHERE seans_id = ? AND durum = 'Aktif'";
        
        try (PreparedStatement kapasiteStmt = connection.prepareStatement(kapasiteSql);
             PreparedStatement biletStmt = connection.prepareStatement(biletSql)) {
            
            // Salon kapasitesini al
            kapasiteStmt.setInt(1, seansId);
            ResultSet kapasiteRs = kapasiteStmt.executeQuery();
            
            int salonKapasitesi = 80; // Varsayılan değer
            if (kapasiteRs.next()) {
                salonKapasitesi = kapasiteRs.getInt(1);
            }
            
            // Satılan bilet sayısını al
            biletStmt.setInt(1, seansId);
            ResultSet biletRs = biletStmt.executeQuery();
            
            if (biletRs.next()) {
                int satilanBilet = biletRs.getInt(1);
                return (double) satilanBilet / salonKapasitesi * 100.0;
            }
            
            return 0.0;
        } catch (SQLException e) {
            System.err.println("Doluluk oranı hesaplanırken hata: " + e.getMessage());
            return 0.0;
        }
    }
    
    // En çok izlenen filmleri getir
    public List<FilmIstatistik> enCokIzlenenFilmler(int limit) {
        String sql = "SELECT TOP " + limit + " f.id, f.ad, COUNT(b.id) as izlenme_sayisi, " +
                    "SUM(b.fiyat) as toplam_gelir " +
                    "FROM filmler f " +
                    "INNER JOIN seanslar s ON f.id = s.film_id " +
                    "INNER JOIN biletler b ON s.id = b.seans_id " +
                    "WHERE b.durum = 'Aktif' " +
                    "GROUP BY f.id, f.ad " +
                    "ORDER BY izlenme_sayisi DESC";
        
        List<FilmIstatistik> filmler = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                FilmIstatistik fs = new FilmIstatistik();
                fs.setFilmId(rs.getInt("id"));
                fs.setFilmAdi(rs.getString("ad"));
                fs.setIzlenmeSayisi(rs.getInt("izlenme_sayisi"));
                fs.setToplamGelir(rs.getDouble("toplam_gelir"));
                
                filmler.add(fs);
            }
            
            return filmler;
        } catch (SQLException e) {
            System.err.println("En çok izlenen filmler alınırken hata: " + e.getMessage());
            return filmler;
        }
    }
    
    // Salon doluluk istatistikleri
    public List<SalonIstatistik> salonDolulukIstatistikleri() {
        String sql = "SELECT s.id, s.ad, s.kapasite, " +
                    "COUNT(DISTINCT sn.id) as toplam_seans, " +
                    "COUNT(b.id) as satilan_bilet, " +
                    "CASE WHEN COUNT(DISTINCT sn.id) > 0 THEN " +
                    "CAST(COUNT(b.id) * 100.0 / (COUNT(DISTINCT sn.id) * s.kapasite) AS DECIMAL(5,2)) " +
                    "ELSE 0 END as doluluk_orani " +
                    "FROM salonlar s " +
                    "LEFT JOIN seanslar sn ON s.id = sn.salon_id " +
                    "LEFT JOIN biletler b ON sn.id = b.seans_id AND b.durum = 'Aktif' " +
                    "GROUP BY s.id, s.ad, s.kapasite " +
                    "ORDER BY doluluk_orani DESC";
        
        List<SalonIstatistik> salonlar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SalonIstatistik ss = new SalonIstatistik();
                ss.setSalonId(rs.getInt("id"));
                ss.setSalonAdi(rs.getString("ad"));
                ss.setKapasite(rs.getInt("kapasite"));
                ss.setToplamSeans(rs.getInt("toplam_seans"));
                ss.setSatilanBilet(rs.getInt("satilan_bilet"));
                ss.setDolulukOrani(rs.getDouble("doluluk_orani"));
                
                salonlar.add(ss);
            }
            
            return salonlar;
        } catch (SQLException e) {
            System.err.println("Salon istatistikleri alınırken hata: " + e.getMessage());
            return salonlar;
        }
    }
    
    // Günlük bilet satış detayları
    public List<GunlukSatis> gunlukSatisDetayları(int gunSayisi) {
        String sql = "SELECT CAST(satis_tarihi AS DATE) as tarih, " +
                    "COUNT(*) as bilet_sayisi, " +
                    "SUM(fiyat) as toplam_gelir " +
                    "FROM biletler " +
                    "WHERE durum = 'Aktif' " +
                    "AND satis_tarihi >= DATEADD(day, -" + gunSayisi + ", GETDATE()) " +
                    "GROUP BY CAST(satis_tarihi AS DATE) " +
                    "ORDER BY tarih DESC";
        
        List<GunlukSatis> satislar = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                GunlukSatis gs = new GunlukSatis();
                gs.setTarih(rs.getDate("tarih"));
                gs.setBiletSayisi(rs.getInt("bilet_sayisi"));
                gs.setToplamGelir(rs.getDouble("toplam_gelir"));
                
                satislar.add(gs);
            }
            
            return satislar;
        } catch (SQLException e) {
            System.err.println("Günlük satış detayları alınırken hata: " + e.getMessage());
            return satislar;
        }
    }
    
    // İstatistik sınıfları
    public static class FilmIstatistik {
        private int filmId;
        private String filmAdi;
        private int izlenmeSayisi;
        private double toplamGelir;
        
        // Getter ve Setter metodları
        public int getFilmId() { return filmId; }
        public void setFilmId(int filmId) { this.filmId = filmId; }
        
        public String getFilmAdi() { return filmAdi; }
        public void setFilmAdi(String filmAdi) { this.filmAdi = filmAdi; }
        
        public int getIzlenmeSayisi() { return izlenmeSayisi; }
        public void setIzlenmeSayisi(int izlenmeSayisi) { this.izlenmeSayisi = izlenmeSayisi; }
        
        public double getToplamGelir() { return toplamGelir; }
        public void setToplamGelir(double toplamGelir) { this.toplamGelir = toplamGelir; }
    }
    
    public static class SalonIstatistik {
        private int salonId;
        private String salonAdi;
        private int kapasite;
        private int toplamSeans;
        private int satilanBilet;
        private double dolulukOrani;
        
        // Getter ve Setter metodları
        public int getSalonId() { return salonId; }
        public void setSalonId(int salonId) { this.salonId = salonId; }
        
        public String getSalonAdi() { return salonAdi; }
        public void setSalonAdi(String salonAdi) { this.salonAdi = salonAdi; }
        
        public int getKapasite() { return kapasite; }
        public void setKapasite(int kapasite) { this.kapasite = kapasite; }
        
        public int getToplamSeans() { return toplamSeans; }
        public void setToplamSeans(int toplamSeans) { this.toplamSeans = toplamSeans; }
        
        public int getSatilanBilet() { return satilanBilet; }
        public void setSatilanBilet(int satilanBilet) { this.satilanBilet = satilanBilet; }
        
        public double getDolulukOrani() { return dolulukOrani; }
        public void setDolulukOrani(double dolulukOrani) { this.dolulukOrani = dolulukOrani; }
    }
    
    public static class GunlukSatis {
        private Date tarih;
        private int biletSayisi;
        private double toplamGelir;
        
        // Getter ve Setter metodları
        public Date getTarih() { return tarih; }
        public void setTarih(Date tarih) { this.tarih = tarih; }
        
        public int getBiletSayisi() { return biletSayisi; }
        public void setBiletSayisi(int biletSayisi) { this.biletSayisi = biletSayisi; }
        
        public double getToplamGelir() { return toplamGelir; }
        public void setToplamGelir(double toplamGelir) { this.toplamGelir = toplamGelir; }
    }
}