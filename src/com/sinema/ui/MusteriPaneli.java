package com.sinema.ui;

import com.sinema.controller.*;
import com.sinema.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MusteriPaneli extends JFrame {
    private JPanel panelMenu;
    private JPanel panelIcerik;
    private JLabel lblHosgeldin;
    private JButton btnFilmler;
    private JButton btnBiletlerim;
    private JButton btnProfil;
    private JButton btnCikis;
    
    private CardLayout cardLayout;
    private Kullanici aktifKullanici;
    
    public MusteriPaneli() {
        aktifKullanici = KullaniciController.getAktifKullanici();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sinema Bilet Sistemi - Müşteri Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Sol menü paneli
        panelMenu = new JPanel();
        panelMenu.setBackground(new Color(44, 62, 80));
        panelMenu.setPreferredSize(new Dimension(250, 600));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        
        // Hoşgeldin mesajı
        lblHosgeldin = new JLabel("Hoş Geldiniz,");
        lblHosgeldin.setForeground(Color.WHITE);
        lblHosgeldin.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHosgeldin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblKullaniciAdi = new JLabel(aktifKullanici.getAd() + " " + aktifKullanici.getSoyad());
        lblKullaniciAdi.setForeground(Color.WHITE);
        lblKullaniciAdi.setFont(new Font("Arial", Font.BOLD, 16));
        lblKullaniciAdi.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        panelMenu.add(lblHosgeldin);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 5)));
        panelMenu.add(lblKullaniciAdi);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Menü butonları
        btnFilmler = createMenuButton("Filmler ve Seanslar", "/icons/film.png");
        btnBiletlerim = createMenuButton("Biletlerim", "/icons/ticket.png");
        btnProfil = createMenuButton("Profil", "/icons/profile.png");
        btnCikis = createMenuButton("Çıkış", "/icons/logout.png");
        
        panelMenu.add(btnFilmler);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnBiletlerim);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnProfil);
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(btnCikis);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Sağ içerik paneli
        cardLayout = new CardLayout();
        panelIcerik = new JPanel(cardLayout);
        panelIcerik.setBackground(new Color(236, 240, 241));
        
        // Panelleri ekle
        panelIcerik.add(new FilmlerPanel(), "filmler");
        panelIcerik.add(new BiletlerimPanel(), "biletlerim");
        panelIcerik.add(new ProfilPanel(), "profil");
        
        // Ana layout
        add(panelMenu, BorderLayout.WEST);
        add(panelIcerik, BorderLayout.CENTER);
        
        // Event listeners
        btnFilmler.addActionListener(e -> cardLayout.show(panelIcerik, "filmler"));
        btnBiletlerim.addActionListener(e -> cardLayout.show(panelIcerik, "biletlerim"));
        btnProfil.addActionListener(e -> cardLayout.show(panelIcerik, "profil"));
        btnCikis.addActionListener(e -> cikisYap());
        
        // Varsayılan olarak filmler panelini göster
        cardLayout.show(panelIcerik, "filmler");
        
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
    }
    
    private JButton createMenuButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(44, 62, 80));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover efekti
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(52, 73, 94));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(44, 62, 80));
            }
        });
        
        return button;
    }
    
    private void cikisYap() {
        int cevap = JOptionPane.showConfirmDialog(this, 
            "Çıkış yapmak istediğinize emin misiniz?", 
            "Çıkış", 
            JOptionPane.YES_NO_OPTION);
        
        if (cevap == JOptionPane.YES_OPTION) {
            KullaniciController controller = new KullaniciController();
            controller.oturumuKapat();
            new GirisEkrani().setVisible(true);
            this.dispose();
        }
    }
    
    // İç panel sınıfları
    
    class FilmlerPanel extends JPanel {
        private JComboBox<Film> cmbFilmler;
        private JList<String> listSeanslar;
        private DefaultListModel<String> seansListModel;
        private JButton btnBiletAl;
        private FilmController filmController;
        private SeansController seansController;
        private JTextArea txtFilmDetay;
        private List<Seans> mevcutSeanslar;
        private Seans seciliSeans;
        
        public FilmlerPanel() {
            filmController = new FilmController();
            seansController = new SeansController();
            seansListModel = new DefaultListModel<>();
            mevcutSeanslar = new ArrayList<>();
            initPanel();
            loadFilmler();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel
            JPanel panelUst = new JPanel(new BorderLayout());
            panelUst.setBackground(Color.WHITE);
            panelUst.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel lblBaslik = new JLabel("Film ve Seans Seçimi");
            lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
            panelUst.add(lblBaslik, BorderLayout.NORTH);
            
            // Film seçimi
            JPanel panelFilmSecim = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelFilmSecim.setBackground(Color.WHITE);
            
            JLabel lblFilm = new JLabel("Film Seçin:");
            lblFilm.setFont(new Font("Arial", Font.BOLD, 14));
            panelFilmSecim.add(lblFilm);
            
            cmbFilmler = new JComboBox<>();
            cmbFilmler.setPreferredSize(new Dimension(400, 30));
            cmbFilmler.setFont(new Font("Arial", Font.PLAIN, 14));
            cmbFilmler.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Film) {
                        Film film = (Film) value;
                        setText(film.getAd() + " (" + film.getSure() + " dk)");
                    }
                    return this;
                }
            });
            panelFilmSecim.add(cmbFilmler);
            
            panelUst.add(panelFilmSecim, BorderLayout.CENTER);
            
            // Film detayları
            txtFilmDetay = new JTextArea(3, 50);
            txtFilmDetay.setEditable(false);
            txtFilmDetay.setBorder(BorderFactory.createTitledBorder("Film Detayları"));
            txtFilmDetay.setBackground(new Color(248, 249, 250));
            txtFilmDetay.setFont(new Font("Arial", Font.PLAIN, 12));
            panelUst.add(txtFilmDetay, BorderLayout.SOUTH);
            
            // Orta panel - Seanslar
            JPanel panelOrta = new JPanel(new BorderLayout());
            panelOrta.setBackground(Color.WHITE);
            panelOrta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel lblSeanslar = new JLabel("Seanslar:");
            lblSeanslar.setFont(new Font("Arial", Font.BOLD, 16));
            panelOrta.add(lblSeanslar, BorderLayout.NORTH);
            
            listSeanslar = new JList<>(seansListModel);
            listSeanslar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSeanslar.setFont(new Font("Arial", Font.PLAIN, 14));
            listSeanslar.setBackground(Color.WHITE);
            listSeanslar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            JScrollPane scrollPane = new JScrollPane(listSeanslar);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
            panelOrta.add(scrollPane, BorderLayout.CENTER);
            
            // Alt panel - Bilet al butonu
            JPanel panelAlt = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelAlt.setBackground(Color.WHITE);
            
            btnBiletAl = new JButton("Bilet Al");
            btnBiletAl.setBackground(new Color(46, 204, 113));
            btnBiletAl.setForeground(Color.WHITE);
            btnBiletAl.setFont(new Font("Arial", Font.BOLD, 16));
            btnBiletAl.setPreferredSize(new Dimension(150, 40));
            btnBiletAl.setFocusPainted(false);
            btnBiletAl.setBorderPainted(false);
            btnBiletAl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnBiletAl.setEnabled(false);
            
            panelAlt.add(btnBiletAl);
            
            add(panelUst, BorderLayout.NORTH);
            add(panelOrta, BorderLayout.CENTER);
            add(panelAlt, BorderLayout.SOUTH);
            
            // Event listeners
            cmbFilmler.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Film seciliFilm = (Film) e.getItem();
                    if (seciliFilm != null) {
                        loadSeanslar(seciliFilm);
                        gosterFilmDetaylari(seciliFilm);
                    }
                }
            });
            
            listSeanslar.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int seciliIndex = listSeanslar.getSelectedIndex();
                    if (seciliIndex >= 0 && seciliIndex < mevcutSeanslar.size()) {
                        seciliSeans = mevcutSeanslar.get(seciliIndex);
                        btnBiletAl.setEnabled(true);
                    } else {
                        seciliSeans = null;
                        btnBiletAl.setEnabled(false);
                    }
                }
            });
            
            btnBiletAl.addActionListener(e -> biletAl());
        }
        
        private void loadFilmler() {
            try {
                cmbFilmler.removeAllItems();
                System.out.println("Film controller ile filmler yükleniyor...");
                
                List<Film> filmler = filmController.vizyondakiFilmleriGetir();
                System.out.println("Yüklenen film sayısı: " + (filmler != null ? filmler.size() : 0));
                
                if (filmler != null && !filmler.isEmpty()) {
                    for (Film film : filmler) {
                        System.out.println("Film ekleniyor: " + film.getAd());
                        cmbFilmler.addItem(film);
                    }
                    
                    // İlk filmi seç
                    if (cmbFilmler.getItemCount() > 0) {
                        Film ilkFilm = (Film) cmbFilmler.getItemAt(0);
                        SwingUtilities.invokeLater(() -> {
                            loadSeanslar(ilkFilm);
                            gosterFilmDetaylari(ilkFilm);
                        });
                    }
                    System.out.println("Filmler başarıyla yüklendi!");
                } else {
                    System.out.println("Hiç film bulunamadı!");
                    txtFilmDetay.setText("Şu anda vizyonda film bulunmamaktadır.\nLütfen yönetici ile iletişime geçin.");
                    seansListModel.clear();
                }
            } catch (Exception e) {
                System.err.println("Film yükleme hatası: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Filmler yüklenirken hata oluştu:\n" + e.getMessage() + 
                    "\n\nLütfen veritabanı bağlantısını kontrol edin.",
                    "Hata", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void loadSeanslar(Film film) {
            try {
                seansListModel.clear();
                mevcutSeanslar.clear();
                btnBiletAl.setEnabled(false);
                
                if (film == null) return;
                
                List<Seans> seanslar = seansController.filminSeanslariniGetir(film.getId());
                
                if (seanslar != null && !seanslar.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    
                    for (Seans seans : seanslar) {
                        // Sadece bugün ve sonraki seansları göster
                        if (seans.getTarih().compareTo(new java.sql.Date(System.currentTimeMillis())) >= 0) {
                            // Salon bilgisini al
                            SalonController salonController = new SalonController();
                            Salon salon = salonController.salonGetir(seans.getSalonId());
                            String salonAdi = salon != null ? salon.getAd() : "Salon " + seans.getSalonId();
                            
                            // Doluluk oranını hesapla
                            BiletController biletController = new BiletController();
                            double dolulukOrani = biletController.seansDolulukOraniGetir(seans.getId());
                            
                            String seansMetni = String.format("%s - %s | %s | %.2f TL | Doluluk: %%%.0f",
                                dateFormat.format(seans.getTarih()),
                                timeFormat.format(seans.getSaat()),
                                salonAdi,
                                seans.getFiyat(),
                                dolulukOrani
                            );
                            
                            seansListModel.addElement(seansMetni);
                            mevcutSeanslar.add(seans);
                        }
                    }
                    
                    if (seansListModel.isEmpty()) {
                        seansListModel.addElement("Bu film için aktif seans bulunmamaktadır.");
                    }
                } else {
                    seansListModel.addElement("Bu film için seans bulunmamaktadır.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Seanslar yüklenirken hata: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        private void gosterFilmDetaylari(Film film) {
            if (film != null) {
                StringBuilder detay = new StringBuilder();
                detay.append("Film: ").append(film.getAd()).append("\n");
                detay.append("Tür: ").append(film.getTur()).append(" | ");
                detay.append("Süre: ").append(film.getSure()).append(" dk | ");
                detay.append("Yönetmen: ").append(film.getYonetmen()).append("\n");
                
                if (film.getOyuncular() != null && !film.getOyuncular().trim().isEmpty()) {
                    detay.append("Oyuncular: ").append(film.getOyuncular());
                }
                
                txtFilmDetay.setText(detay.toString());
            } else {
                txtFilmDetay.setText("");
            }
        }
        
        private void biletAl() {
            if (seciliSeans == null) {
                JOptionPane.showMessageDialog(this, "Lütfen bir seans seçin!");
                return;
            }
            
            if (KullaniciController.getAktifKullanici() == null) {
                JOptionPane.showMessageDialog(this, "Bilet almak için giriş yapmalısınız!");
                return;
            }
            
            // Koltuk seçim ekranını aç
            SwingUtilities.invokeLater(() -> {
                new KoltukSecimEkrani(seciliSeans).setVisible(true);
            });
        }
    }
    
 // MusteriPaneli.java içinde BiletlerimPanel sınıfını değiştirin:

    class BiletlerimPanel extends JPanel {
        private JTable tableBiletler;
        private DefaultTableModel tableModel;
        private JButton btnIptal;
        private JButton btnYenile;
        private BiletController biletController;
        private FilmController filmController;
        private SeansController seansController;
        private SalonController salonController;
        
        public BiletlerimPanel() {
            biletController = new BiletController();
            filmController = new FilmController();
            seansController = new SeansController();
            salonController = new SalonController();
            initPanel();
            loadBiletler(); // Bu satırı ekledik
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel
            JPanel panelUst = new JPanel(new BorderLayout());
            panelUst.setBackground(Color.WHITE);
            panelUst.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel lblBaslik = new JLabel("Biletlerim");
            lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
            panelUst.add(lblBaslik, BorderLayout.WEST);
            
            // Yenile butonu
            btnYenile = new JButton("🔄 Yenile");
            btnYenile.setBackground(new Color(52, 152, 219));
            btnYenile.setForeground(Color.WHITE);
            btnYenile.setFont(new Font("Arial", Font.BOLD, 12));
            btnYenile.setFocusPainted(false);
            btnYenile.setBorderPainted(false);
            btnYenile.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelUst.add(btnYenile, BorderLayout.EAST);
            
            // Tablo
            String[] kolonlar = {"Bilet No", "Film", "Salon", "Tarih", "Saat", "Koltuk", "Fiyat", "Durum"};
            tableModel = new DefaultTableModel(kolonlar, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            tableBiletler = new JTable(tableModel);
            tableBiletler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tableBiletler.setRowHeight(30);
            tableBiletler.setFont(new Font("Arial", Font.PLAIN, 13));
            tableBiletler.getTableHeader().setBackground(new Color(52, 73, 94));
            tableBiletler.getTableHeader().setForeground(Color.WHITE);
            tableBiletler.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            
            JScrollPane scrollPane = new JScrollPane(tableBiletler);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Bilet Listesi"));
            
            // Alt panel
            JPanel panelAlt = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelAlt.setBackground(Color.WHITE);
            panelAlt.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
            
            btnIptal = new JButton("Bilet İptal Et");
            btnIptal.setBackground(new Color(231, 76, 60));
            btnIptal.setForeground(Color.WHITE);
            btnIptal.setFont(new Font("Arial", Font.BOLD, 14));
            btnIptal.setPreferredSize(new Dimension(150, 35));
            btnIptal.setFocusPainted(false);
            btnIptal.setBorderPainted(false);
            btnIptal.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelAlt.add(btnIptal);
            
            add(panelUst, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(panelAlt, BorderLayout.SOUTH);
            
            // Event listeners
            btnIptal.addActionListener(e -> biletIptal());
            btnYenile.addActionListener(e -> loadBiletler());
        }
        
        private void loadBiletler() {
            try {
                tableModel.setRowCount(0);
                
                if (aktifKullanici == null) {
                    System.err.println("Aktif kullanıcı bulunamadı!");
                    return;
                }
                
                List<Bilet> biletler = biletController.kullaniciBiletleriGetir(aktifKullanici.getId());
                
                if (biletler != null && !biletler.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    
                    for (Bilet bilet : biletler) {
                        // Seans bilgisini al
                        Seans seans = seansController.seansGetir(bilet.getSeansId());
                        if (seans == null) continue;
                        
                        // Film bilgisini al
                        Film film = filmController.filmGetir(seans.getFilmId());
                        String filmAdi = film != null ? film.getAd() : "Bilinmeyen Film";
                        
                        // Salon bilgisini al
                        Salon salon = salonController.salonGetir(seans.getSalonId());
                        String salonAdi = salon != null ? salon.getAd() : "Salon " + seans.getSalonId();
                        
                        Object[] row = {
                            bilet.getId(),
                            filmAdi,
                            salonAdi,
                            dateFormat.format(seans.getTarih()),
                            timeFormat.format(seans.getSaat()),
                            bilet.getKoltukNo(),
                            String.format("%.2f TL", bilet.getFiyat()),
                            bilet.getDurum()
                        };
                        tableModel.addRow(row);
                    }
                    
                    System.out.println("Biletler yüklendi: " + biletler.size() + " adet");
                } else {
                    System.out.println("Hiç bilet bulunamadı veya liste boş.");
                    // Boş mesajı için bir satır ekle
                    Object[] bosRow = {"", "Henüz bilet alınmamış", "", "", "", "", "", ""};
                    tableModel.addRow(bosRow);
                }
            } catch (Exception e) {
                System.err.println("Biletler yüklenirken hata: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Biletler yüklenirken hata oluştu: " + e.getMessage(),
                    "Hata", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void biletIptal() {
            int selectedRow = tableBiletler.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen iptal edilecek bileti seçin!");
                return;
            }
            
            try {
                Object biletIdObj = tableModel.getValueAt(selectedRow, 0);
                if (biletIdObj == null || biletIdObj.toString().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Geçersiz bilet seçimi!");
                    return;
                }
                
                int biletId = Integer.parseInt(biletIdObj.toString());
                String filmAdi = tableModel.getValueAt(selectedRow, 1).toString();
                String durum = tableModel.getValueAt(selectedRow, 7).toString();
                
                if ("İptal".equals(durum)) {
                    JOptionPane.showMessageDialog(this, "Bu bilet zaten iptal edilmiş!");
                    return;
                }
                
                int cevap = JOptionPane.showConfirmDialog(this,
                    "'" + filmAdi + "' filmi için alınan bileti iptal etmek istediğinize emin misiniz?\n" +
                    "Bu işlem geri alınamaz!",
                    "Bilet İptal Onayı",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (cevap == JOptionPane.YES_OPTION) {
                    if (biletController.biletIptalEt(biletId)) {
                        JOptionPane.showMessageDialog(this, "Bilet başarıyla iptal edildi!");
                        loadBiletler(); // Tabloyu yenile
                    } else {
                        JOptionPane.showMessageDialog(this, "Bilet iptal edilirken hata oluştu!");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçersiz bilet ID!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bilet iptal edilirken hata: " + e.getMessage());
            }
        }
    }
    
    class ProfilPanel extends JPanel {
        private JTextField txtAd, txtSoyad, txtEmail, txtTelefon;
        private JPasswordField txtEskiSifre, txtYeniSifre, txtYeniSifreTekrar;
        private JButton btnGuncelle, btnSifreDegistir;
        private KullaniciController kullaniciController;
        
        public ProfilPanel() {
            kullaniciController = new KullaniciController();
            initPanel();
            loadProfilBilgileri();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel
            JPanel panelUst = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelUst.setBackground(Color.WHITE);
            panelUst.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel lblBaslik = new JLabel("Profil Bilgilerim");
            lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
            panelUst.add(lblBaslik);
            
            // Ana panel
            JPanel panelAna = new JPanel();
            panelAna.setLayout(new BoxLayout(panelAna, BoxLayout.Y_AXIS));
            panelAna.setBackground(new Color(236, 240, 241));
            
            // Profil bilgileri paneli
            JPanel panelProfil = new JPanel(new GridBagLayout());
            panelProfil.setBackground(Color.WHITE);
            panelProfil.setBorder(BorderFactory.createTitledBorder("Kişisel Bilgiler"));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Form alanları
            int row = 0;
            panelProfil.add(new JLabel("Ad:"), createGbc(0, row));
            txtAd = new JTextField(20);
            panelProfil.add(txtAd, createGbc(1, row++));
            
            panelProfil.add(new JLabel("Soyad:"), createGbc(0, row));
            txtSoyad = new JTextField(20);
            panelProfil.add(txtSoyad, createGbc(1, row++));
            
            panelProfil.add(new JLabel("E-mail:"), createGbc(0, row));
            txtEmail = new JTextField(20);
            panelProfil.add(txtEmail, createGbc(1, row++));
            
            panelProfil.add(new JLabel("Telefon:"), createGbc(0, row));
            txtTelefon = new JTextField(20);
            panelProfil.add(txtTelefon, createGbc(1, row++));
            
            btnGuncelle = new JButton("Bilgileri Güncelle");
            btnGuncelle.setBackground(new Color(52, 152, 219));
            btnGuncelle.setForeground(Color.WHITE);
            gbc = createGbc(1, row);
            gbc.anchor = GridBagConstraints.EAST;
            panelProfil.add(btnGuncelle, gbc);
            
            // Şifre değiştirme paneli
            JPanel panelSifre = new JPanel(new GridBagLayout());
            panelSifre.setBackground(Color.WHITE);
            panelSifre.setBorder(BorderFactory.createTitledBorder("Şifre Değiştir"));
            
            row = 0;
            panelSifre.add(new JLabel("Eski Şifre:"), createGbc(0, row));
            txtEskiSifre = new JPasswordField(20);
            panelSifre.add(txtEskiSifre, createGbc(1, row++));
            
            panelSifre.add(new JLabel("Yeni Şifre:"), createGbc(0, row));
            txtYeniSifre = new JPasswordField(20);
            panelSifre.add(txtYeniSifre, createGbc(1, row++));
            
            panelSifre.add(new JLabel("Yeni Şifre (Tekrar):"), createGbc(0, row));
            txtYeniSifreTekrar = new JPasswordField(20);
            panelSifre.add(txtYeniSifreTekrar, createGbc(1, row++));
            
            btnSifreDegistir = new JButton("Şifre Değiştir");
            btnSifreDegistir.setBackground(new Color(231, 76, 60));
            btnSifreDegistir.setForeground(Color.WHITE);
            gbc = createGbc(1, row);
            gbc.anchor = GridBagConstraints.EAST;
            panelSifre.add(btnSifreDegistir, gbc);
            
            panelAna.add(panelProfil);
            panelAna.add(Box.createRigidArea(new Dimension(0, 20)));
            panelAna.add(panelSifre);
            
            JScrollPane scrollPane = new JScrollPane(panelAna);
            scrollPane.setBorder(null);
            
            add(panelUst, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            
            // Event listeners
            btnGuncelle.addActionListener(e -> profilGuncelle());
            btnSifreDegistir.addActionListener(e -> sifreDegistir());
        }
        
        private GridBagConstraints createGbc(int x, int y) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            return gbc;
        }
        
        private void loadProfilBilgileri() {
            Kullanici k = KullaniciController.getAktifKullanici();
            if (k != null) {
                txtAd.setText(k.getAd());
                txtSoyad.setText(k.getSoyad());
                txtEmail.setText(k.getEmail());
                txtTelefon.setText(k.getTelefon());
            }
        }
        
        private void profilGuncelle() {
            kullaniciController.profilGuncelle(
                txtAd.getText(),
                txtSoyad.getText(),
                txtEmail.getText(),
                txtTelefon.getText()
            );
        }
        
        private void sifreDegistir() {
            String eskiSifre = new String(txtEskiSifre.getPassword());
            String yeniSifre = new String(txtYeniSifre.getPassword());
            String yeniSifreTekrar = new String(txtYeniSifreTekrar.getPassword());
            
            if (!yeniSifre.equals(yeniSifreTekrar)) {
                JOptionPane.showMessageDialog(this, "Yeni şifreler eşleşmiyor!");
                return;
            }
            
            kullaniciController.sifreDegistir(eskiSifre, yeniSifre);
        }
    }
}