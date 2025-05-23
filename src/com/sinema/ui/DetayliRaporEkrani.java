package com.sinema.ui;

import com.sinema.controller.*;
import com.sinema.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class DetayliRaporEkrani extends JFrame {
    private JTabbedPane tabbedPane;
    private FilmController filmController;
    private SeansController seansController;
    private SalonController salonController;
    private BiletController biletController;
    private KullaniciController kullaniciController;
    
    public DetayliRaporEkrani() {
        filmController = new FilmController();
        seansController = new SeansController();
        salonController = new SalonController();
        biletController = new BiletController();
        kullaniciController = new KullaniciController();
        
        initComponents();
        loadAllReports();
    }
    
    private void initComponents() {
        setTitle("Detaylı Raporlar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Başlık
        JPanel panelBaslik = new JPanel();
        panelBaslik.setBackground(new Color(52, 73, 94));
        panelBaslik.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel lblBaslik = new JLabel("SİNEMA YÖNETİM SİSTEMİ - DETAYLI RAPORLAR");
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 18));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        panelBaslik.add(lblBaslik);
        
        // Tab panel
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("💰 Satış Raporu", createSatisRaporuPanel());
        tabbedPane.addTab("🎬 Film Raporu", createFilmRaporuPanel());
        tabbedPane.addTab("🏢 Salon Raporu", createSalonRaporuPanel());
        tabbedPane.addTab("👥 Kullanıcı Raporu", createKullaniciRaporuPanel());
        tabbedPane.addTab("🎭 Seans Raporu", createSeansRaporuPanel());
        
        // Alt panel - Butonlar
        JPanel panelAlt = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAlt.setBackground(Color.WHITE);
        panelAlt.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JButton btnYenile = new JButton("🔄 Verileri Yenile");
        btnYenile.setBackground(new Color(52, 152, 219));
        btnYenile.setForeground(Color.WHITE);
        btnYenile.setFont(new Font("Arial", Font.BOLD, 12));
        btnYenile.setFocusPainted(false);
        btnYenile.setPreferredSize(new Dimension(140, 35));
        btnYenile.addActionListener(e -> loadAllReports());
        
        JButton btnKapat = new JButton("❌ Kapat");
        btnKapat.setBackground(new Color(231, 76, 60));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Arial", Font.BOLD, 12));
        btnKapat.setFocusPainted(false);
        btnKapat.setPreferredSize(new Dimension(100, 35));
        btnKapat.addActionListener(e -> dispose());
        
        panelAlt.add(btnYenile);
        panelAlt.add(btnKapat);
        
        add(panelBaslik, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(panelAlt, BorderLayout.SOUTH);
        
        setSize(900, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
    }
    
    private JPanel createSatisRaporuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Üst bilgi paneli
        JPanel panelBilgi = new JPanel(new GridLayout(2, 4, 10, 10));
        panelBilgi.setBorder(BorderFactory.createTitledBorder("Satış Özeti"));
        panelBilgi.setBackground(Color.WHITE);
        panelBilgi.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Satış Özeti"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Günlük satış
        JPanel panelGunluk = createInfoCard("Günlük Satış", "0.00 TL", new Color(46, 204, 113));
        JPanel panelAylik = createInfoCard("Aylık Satış", "0.00 TL", new Color(52, 152, 219));
        JPanel panelToplamBilet = createInfoCard("Toplam Bilet", "0", new Color(155, 89, 182));
        JPanel panelOrtalama = createInfoCard("Ortalama Fiyat", "0.00 TL", new Color(230, 126, 34));
        
        panelBilgi.add(panelGunluk);
        panelBilgi.add(panelAylik);
        panelBilgi.add(panelToplamBilet);
        panelBilgi.add(panelOrtalama);
        
        // Boş alan için 4 panel daha
        for (int i = 0; i < 4; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.WHITE);
            panelBilgi.add(emptyPanel);
        }
        
        // Detaylı tablo
        String[] kolonlar = {"Tarih", "Film", "Salon", "Koltuk", "Fiyat", "Durum"};
        DefaultTableModel tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Son Satışlar"));
        
        panel.add(panelBilgi, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFilmRaporuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Film istatistikleri
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Film İstatistikleri"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelStats.setBackground(Color.WHITE);
        
        panelStats.add(createInfoCard("Toplam Film", "0", new Color(52, 152, 219)));
        panelStats.add(createInfoCard("Aksiyon", "0", new Color(231, 76, 60)));
        panelStats.add(createInfoCard("Dram", "0", new Color(46, 204, 113)));
        panelStats.add(createInfoCard("Komedi", "0", new Color(230, 126, 34)));
        
        // Film tablosu
        String[] kolonlar = {"ID", "Film Adı", "Tür", "Süre", "Yönetmen", "Seans Sayısı"};
        DefaultTableModel tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Film Listesi"));
        
        panel.add(panelStats, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSalonRaporuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Salon istatistikleri
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Salon İstatistikleri"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelStats.setBackground(Color.WHITE);
        
        panelStats.add(createInfoCard("Toplam Salon", "0", new Color(52, 152, 219)));
        panelStats.add(createInfoCard("VIP Salon", "0", new Color(230, 126, 34)));
        panelStats.add(createInfoCard("Toplam Kapasite", "0", new Color(46, 204, 113)));
        panelStats.add(createInfoCard("Ort. Kapasite", "0", new Color(155, 89, 182)));
        
        // Salon tablosu
        String[] kolonlar = {"ID", "Salon Adı", "Kapasite", "Tip", "Aktif Seans", "Doluluk %"};
        DefaultTableModel tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Salon Detayları"));
        
        panel.add(panelStats, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createKullaniciRaporuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Kullanıcı istatistikleri
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Kullanıcı İstatistikleri"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelStats.setBackground(Color.WHITE);
        
        panelStats.add(createInfoCard("Toplam Kullanıcı", "0", new Color(52, 152, 219)));
        panelStats.add(createInfoCard("Müşteri", "0", new Color(46, 204, 113)));
        panelStats.add(createInfoCard("Yönetici", "0", new Color(231, 76, 60)));
        panelStats.add(createInfoCard("Aktif Oturum", "1", new Color(230, 126, 34)));
        
        // Kullanıcı tablosu
        String[] kolonlar = {"ID", "Kullanıcı Adı", "Ad Soyad", "Email", "Rol", "Bilet Sayısı"};
        DefaultTableModel tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Kullanıcı Listesi"));
        
        panel.add(panelStats, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSeansRaporuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Seans istatistikleri
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Seans İstatistikleri"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelStats.setBackground(Color.WHITE);
        
        panelStats.add(createInfoCard("Bugünkü Seans", "0", new Color(52, 152, 219)));
        panelStats.add(createInfoCard("Toplam Seans", "0", new Color(46, 204, 113)));
        panelStats.add(createInfoCard("Dolu Seans", "0", new Color(231, 76, 60)));
        panelStats.add(createInfoCard("Boş Seans", "0", new Color(230, 126, 34)));
        
        // Seans tablosu
        String[] kolonlar = {"ID", "Film", "Salon", "Tarih", "Saat", "Fiyat", "Doluluk %"};
        DefaultTableModel tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seans Detayları"));
        
        panel.add(panelStats, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createInfoCard(String baslik, String deger, Color renk) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(renk, 2));
        card.setPreferredSize(new Dimension(150, 80));
        
        JLabel lblBaslik = new JLabel(baslik, SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 12));
        lblBaslik.setForeground(renk);
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel lblDeger = new JLabel(deger, SwingConstants.CENTER);
        lblDeger.setFont(new Font("Arial", Font.BOLD, 20));
        lblDeger.setForeground(renk);
        
        card.add(lblBaslik, BorderLayout.NORTH);
        card.add(lblDeger, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadAllReports() {
        SwingUtilities.invokeLater(() -> {
            try {
                loadSatisRaporu();
                loadFilmRaporu();
                loadSalonRaporu();
                loadKullaniciRaporu();
                loadSeansRaporu();
                JOptionPane.showMessageDialog(this, "Raporlar başarıyla yenilendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Raporlar yüklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
    
    private void loadSatisRaporu() {
        try {
            JPanel satisPanel = (JPanel) tabbedPane.getComponentAt(0);
            JPanel bilgiPanel = (JPanel) satisPanel.getComponent(0);
            
            // Satış verilerini al
            double gunlukSatis = biletController.gunlukSatisGetir();
            double aylikSatis = biletController.aylikSatisGetir();
            
            // Kartları güncelle
            updateInfoCard(bilgiPanel, 0, String.format("%.2f TL", gunlukSatis));
            updateInfoCard(bilgiPanel, 1, String.format("%.2f TL", aylikSatis));
            updateInfoCard(bilgiPanel, 2, "0"); // Toplam bilet sayısı
            updateInfoCard(bilgiPanel, 3, gunlukSatis > 0 ? String.format("%.2f TL", gunlukSatis / Math.max(1, 1)) : "0.00 TL");
            
            // Tablo verilerini yükle (örnek)
            JScrollPane scrollPane = (JScrollPane) satisPanel.getComponent(1);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            // Örnek satış verileri
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Object[] row1 = {sdf.format(new Date()), "Inception", "Salon 1", "A1", "45.00 TL", "Aktif"};
            Object[] row2 = {sdf.format(new Date()), "The Dark Knight", "Salon 2", "B2", "50.00 TL", "Aktif"};
            model.addRow(row1);
            model.addRow(row2);
            
        } catch (Exception e) {
            System.err.println("Satış raporu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void loadFilmRaporu() {
        try {
            JPanel filmPanel = (JPanel) tabbedPane.getComponentAt(1);
            JPanel statsPanel = (JPanel) filmPanel.getComponent(0);
            
            List<Film> filmler = filmController.tumFilmleriGetir();
            int toplamFilm = filmler != null ? filmler.size() : 0;
            
            // Tür bazında sayım
            Map<String, Integer> turSayilari = new HashMap<>();
            if (filmler != null) {
                for (Film film : filmler) {
                    String tur = film.getTur();
                    turSayilari.put(tur, turSayilari.getOrDefault(tur, 0) + 1);
                }
            }
            
            updateInfoCard(statsPanel, 0, String.valueOf(toplamFilm));
            updateInfoCard(statsPanel, 1, String.valueOf(turSayilari.getOrDefault("Aksiyon", 0)));
            updateInfoCard(statsPanel, 2, String.valueOf(turSayilari.getOrDefault("Dram", 0)));
            updateInfoCard(statsPanel, 3, String.valueOf(turSayilari.getOrDefault("Komedi", 0)));
            
            // Tablo güncelle
            JScrollPane scrollPane = (JScrollPane) filmPanel.getComponent(1);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            if (filmler != null) {
                for (Film film : filmler) {
                    Object[] row = {
                        film.getId(),
                        film.getAd(),
                        film.getTur(),
                        film.getSure() + " dk",
                        film.getYonetmen(),
                        "0" // Seans sayısı
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Film raporu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void loadSalonRaporu() {
        try {
            JPanel salonPanel = (JPanel) tabbedPane.getComponentAt(2);
            JPanel statsPanel = (JPanel) salonPanel.getComponent(0);
            
            List<Salon> salonlar = salonController.tumSalonlariGetir();
            int toplamSalon = salonlar != null ? salonlar.size() : 0;
            int vipSalon = 0;
            int toplamKapasite = 0;
            
            if (salonlar != null) {
                for (Salon salon : salonlar) {
                    if (salon.isVip()) vipSalon++;
                    toplamKapasite += salon.getKapasite();
                }
            }
            
            int ortalamaKapasite = toplamSalon > 0 ? toplamKapasite / toplamSalon : 0;
            
            updateInfoCard(statsPanel, 0, String.valueOf(toplamSalon));
            updateInfoCard(statsPanel, 1, String.valueOf(vipSalon));
            updateInfoCard(statsPanel, 2, String.valueOf(toplamKapasite));
            updateInfoCard(statsPanel, 3, String.valueOf(ortalamaKapasite));
            
            // Tablo güncelle
            JScrollPane scrollPane = (JScrollPane) salonPanel.getComponent(1);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            if (salonlar != null) {
                for (Salon salon : salonlar) {
                    Object[] row = {
                        salon.getId(),
                        salon.getAd(),
                        salon.getKapasite(),
                        salon.isVip() ? "VIP" : "Normal",
                        "0", // Aktif seans
                        "0%" // Doluluk oranı
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Salon raporu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void loadKullaniciRaporu() {
        try {
            JPanel kullaniciPanel = (JPanel) tabbedPane.getComponentAt(3);
            JPanel statsPanel = (JPanel) kullaniciPanel.getComponent(0);
            
            List<Kullanici> kullanicilar = kullaniciController.tumKullanicilariGetir();
            int toplamKullanici = kullanicilar != null ? kullanicilar.size() : 0;
            int musteriSayisi = 0;
            int yoneticiSayisi = 0;
            
            if (kullanicilar != null) {
                for (Kullanici k : kullanicilar) {
                    if ("Müşteri".equals(k.getRol())) {
                        musteriSayisi++;
                    } else if ("Yönetici".equals(k.getRol())) {
                        yoneticiSayisi++;
                    }
                }
            }
            
            updateInfoCard(statsPanel, 0, String.valueOf(toplamKullanici));
            updateInfoCard(statsPanel, 1, String.valueOf(musteriSayisi));
            updateInfoCard(statsPanel, 2, String.valueOf(yoneticiSayisi));
            updateInfoCard(statsPanel, 3, "1"); // Aktif oturum
            
            // Tablo güncelle
            JScrollPane scrollPane = (JScrollPane) kullaniciPanel.getComponent(1);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            if (kullanicilar != null) {
                for (Kullanici k : kullanicilar) {
                    Object[] row = {
                        k.getId(),
                        k.getKullaniciAdi(),
                        k.getAd() + " " + k.getSoyad(),
                        k.getEmail(),
                        k.getRol(),
                        "0" // Bilet sayısı
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Kullanıcı raporu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void loadSeansRaporu() {
        try {
            JPanel seansPanel = (JPanel) tabbedPane.getComponentAt(4);
            JPanel statsPanel = (JPanel) seansPanel.getComponent(0);
            
            List<Seans> seanslar = seansController.aktifSeansGetir();
            int toplamSeans = seanslar != null ? seanslar.size() : 0;
            
            // Bugünkü seansları say
            int bugunSeanslar = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String bugun = sdf.format(new Date());
            
            if (seanslar != null) {
                for (Seans seans : seanslar) {
                    if (bugun.equals(sdf.format(seans.getTarih()))) {
                        bugunSeanslar++;
                    }
                }
            }
            
            updateInfoCard(statsPanel, 0, String.valueOf(bugunSeanslar));
            updateInfoCard(statsPanel, 1, String.valueOf(toplamSeans));
            updateInfoCard(statsPanel, 2, "0"); // Dolu seans
            updateInfoCard(statsPanel, 3, String.valueOf(toplamSeans)); // Boş seans
            
            // Tablo güncelle
            JScrollPane scrollPane = (JScrollPane) seansPanel.getComponent(1);
            JTable table = (JTable) scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            if (seanslar != null) {
                for (Seans seans : seanslar) {
                    Film film = filmController.filmGetir(seans.getFilmId());
                    Salon salon = salonController.salonGetir(seans.getSalonId());
                    
                    Object[] row = {
                        seans.getId(),
                        film != null ? film.getAd() : "Bilinmeyen",
                        salon != null ? salon.getAd() : "Bilinmeyen",
                        seans.getTarih(),
                        seans.getSaat(),
                        String.format("%.2f TL", seans.getFiyat()),
                        "0%" // Doluluk oranı
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Seans raporu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void updateInfoCard(JPanel parentPanel, int cardIndex, String newValue) {
        try {
            if (cardIndex < parentPanel.getComponentCount()) {
                JPanel card = (JPanel) parentPanel.getComponent(cardIndex);
                if (card.getComponentCount() > 1) {
                    JLabel lblDeger = (JLabel) card.getComponent(1);
                    lblDeger.setText(newValue);
                }
            }
        } catch (Exception e) {
            System.err.println("Kart güncellenirken hata: " + e.getMessage());
        }
    }
}