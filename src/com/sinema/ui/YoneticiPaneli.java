package com.sinema.ui;

import com.sinema.controller.*;
import com.sinema.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.File;

public class YoneticiPaneli extends JFrame {
    private JPanel panelMenu;
    private JPanel panelIcerik;
    private CardLayout cardLayout;
    
    private JButton btnFilmler;
    private JButton btnSeanslar;
    private JButton btnSalonlar;
    private JButton btnKullanicilar;
    private JButton btnRaporlar;
    private JButton btnDetayliRapor;
    private JButton btnCikis;
    
    private FilmController filmController;
    private SeansController seansController;
    private SalonController salonController;
    private KullaniciController kullaniciController;
    private BiletController biletController;
    
    public YoneticiPaneli() {
        filmController = new FilmController();
        seansController = new SeansController();
        salonController = new SalonController();
        kullaniciController = new KullaniciController();
        biletController = new BiletController();
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sinema Bilet Sistemi - Yönetici Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Sol menü
        panelMenu = new JPanel();
        panelMenu.setBackground(new Color(44, 62, 80));
        panelMenu.setPreferredSize(new Dimension(250, 700));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        
        // Logo/Başlık
        JLabel lblBaslik = new JLabel("YÖNETİCİ PANELİ");
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 20));
        lblBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Kullanıcı bilgisi
        Kullanici aktifKullanici = KullaniciController.getAktifKullanici();
        JLabel lblKullanici = new JLabel("Hoş geldiniz, " + (aktifKullanici != null ? aktifKullanici.getAd() : "Admin"));
        lblKullanici.setForeground(new Color(189, 195, 199));
        lblKullanici.setFont(new Font("Arial", Font.PLAIN, 12));
        lblKullanici.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        panelMenu.add(lblBaslik);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 5)));
        panelMenu.add(lblKullanici);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Menü butonları
        btnFilmler = createMenuButton("🎬 Film Yönetimi");
        btnSeanslar = createMenuButton("🎭 Seans Yönetimi");
        btnSalonlar = createMenuButton("🏢 Salon Yönetimi");
        btnKullanicilar = createMenuButton("👥 Kullanıcı Yönetimi");
        btnRaporlar = createMenuButton("📊 Raporlar");
        btnDetayliRapor = createMenuButton("📈 Detaylı Raporlar");
        btnCikis = createMenuButton("🚪 Çıkış");
        
        panelMenu.add(btnFilmler);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnSeanslar);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnSalonlar);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnKullanicilar);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnRaporlar);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnDetayliRapor);
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(btnCikis);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // İçerik paneli
        cardLayout = new CardLayout();
        panelIcerik = new JPanel(cardLayout);
        
        // Panelleri ekle
        panelIcerik.add(new FilmYonetimiPanel(), "filmler");
        panelIcerik.add(new SeansYonetimiPanel(), "seanslar");
        panelIcerik.add(new SalonYonetimiPanel(), "salonlar");
        panelIcerik.add(new KullaniciYonetimiPanel(), "kullanicilar");
        panelIcerik.add(new RaporlarPanel(), "raporlar");
        
        // Event listeners
        btnFilmler.addActionListener(e -> cardLayout.show(panelIcerik, "filmler"));
        btnSeanslar.addActionListener(e -> cardLayout.show(panelIcerik, "seanslar"));
        btnSalonlar.addActionListener(e -> cardLayout.show(panelIcerik, "salonlar"));
        btnKullanicilar.addActionListener(e -> cardLayout.show(panelIcerik, "kullanicilar"));
        btnRaporlar.addActionListener(e -> cardLayout.show(panelIcerik, "raporlar"));
        btnDetayliRapor.addActionListener(e -> new DetayliRaporEkrani().setVisible(true));
        btnCikis.addActionListener(e -> cikisYap());
        
        add(panelMenu, BorderLayout.WEST);
        add(panelIcerik, BorderLayout.CENTER);
        
        // Varsayılan panel
        cardLayout.show(panelIcerik, "filmler");
        
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(44, 62, 80));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 45));
        button.setPreferredSize(new Dimension(220, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
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
            kullaniciController.oturumuKapat();
            new GirisEkrani().setVisible(true);
            this.dispose();
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color hoverColor = bgColor.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Tablo stilini düzenleme metodu
    private void setupTableStyle(JTable table) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(184, 207, 229));
        table.setSelectionForeground(Color.BLACK);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setBackground(new Color(52, 73, 94));
            header.setForeground(Color.WHITE);
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setPreferredSize(new Dimension(header.getPreferredSize().width, 35));
            header.setReorderingAllowed(false);
            header.setOpaque(true);
            header.setDefaultRenderer(new HeaderRenderer());
        }
        
        table.setDefaultRenderer(Object.class, new ModernCellRenderer());
    }
    
    private static class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);
            setBackground(new Color(52, 73, 94));
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 14));
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(44, 62, 80)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }
    
    private static class ModernCellRenderer extends DefaultTableCellRenderer {
        public ModernCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            if (isSelected) {
                component.setBackground(new Color(184, 207, 229));
                component.setForeground(Color.BLACK);
            } else {
                if (row % 2 == 0) {
                    component.setBackground(new Color(248, 249, 250));
                } else {
                    component.setBackground(Color.WHITE);
                }
                component.setForeground(new Color(44, 62, 80));
            }
            
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
            return component;
        }
    }

    // FİLM YÖNETİMİ PANELİ
    class FilmYonetimiPanel extends JPanel {
        private JTable tableFilmler;
        private DefaultTableModel tableModel;
        private JTextField txtAd, txtTur, txtSure, txtYonetmen, txtOyuncular;
        private JTextArea txtAciklama;
        private JTextField txtAfisYolu;
        private JButton btnAfisSeç;
        private JButton btnEkle, btnGuncelle, btnSil, btnYenile;
        private JLabel lblAfisOnizleme;
        
        public FilmYonetimiPanel() {
            initPanel();
            loadFilmler();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel - Form
            JPanel panelForm = new JPanel(new GridBagLayout());
            panelForm.setBorder(BorderFactory.createTitledBorder("Film Bilgileri"));
            panelForm.setBackground(Color.WHITE);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Form alanları
            int row = 0;
            
            // Film Adı
            gbc.gridx = 0; gbc.gridy = row;
            panelForm.add(new JLabel("Film Adı:"), gbc);
            gbc.gridx = 1;
            txtAd = new JTextField(20);
            txtAd.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtAd, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Tür:"), gbc);
            gbc.gridx = 3;
            txtTur = new JTextField(15);
            txtTur.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtTur, gbc);
            
            row++;
            // Süre ve Yönetmen
            gbc.gridx = 0; gbc.gridy = row;
            panelForm.add(new JLabel("Süre (dk):"), gbc);
            gbc.gridx = 1;
            txtSure = new JTextField(20);
            txtSure.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtSure, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Yönetmen:"), gbc);
            gbc.gridx = 3;
            txtYonetmen = new JTextField(15);
            txtYonetmen.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtYonetmen, gbc);
            
            row++;
            // Oyuncular
            gbc.gridx = 0; gbc.gridy = row;
            panelForm.add(new JLabel("Oyuncular:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 3;
            txtOyuncular = new JTextField();
            txtOyuncular.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtOyuncular, gbc);
            
            row++;
            // Açıklama
            gbc.gridx = 0; gbc.gridy = row;
            gbc.gridwidth = 1;
            panelForm.add(new JLabel("Açıklama:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.BOTH;
            txtAciklama = new JTextArea(3, 30);
            txtAciklama.setFont(new Font("Arial", Font.PLAIN, 12));
            txtAciklama.setLineWrap(true);
            txtAciklama.setWrapStyleWord(true);
            JScrollPane scrollAciklama = new JScrollPane(txtAciklama);
            panelForm.add(scrollAciklama, gbc);
            
            row++;
            // Afiş seçimi
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0; gbc.gridy = row;
            gbc.gridwidth = 1;
            panelForm.add(new JLabel("Afiş:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtAfisYolu = new JTextField();
            txtAfisYolu.setEditable(false);
            txtAfisYolu.setFont(new Font("Arial", Font.PLAIN, 12));
            panelForm.add(txtAfisYolu, gbc);
            
            gbc.gridx = 3; gbc.gridwidth = 1;
            btnAfisSeç = createStyledButton("Afiş Seç", new Color(230, 126, 34));
            panelForm.add(btnAfisSeç, gbc);
            
            // Afiş önizleme
            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panelForm.add(new JLabel("Önizleme:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 3;
            lblAfisOnizleme = new JLabel();
            lblAfisOnizleme.setPreferredSize(new Dimension(100, 150));
            lblAfisOnizleme.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lblAfisOnizleme.setHorizontalAlignment(SwingConstants.CENTER);
            lblAfisOnizleme.setText("Afiş Seçilmedi");
            panelForm.add(lblAfisOnizleme, gbc);
            
            // Butonlar
            row++;
            JPanel panelButonlar = new JPanel(new FlowLayout());
            panelButonlar.setBackground(Color.WHITE);
            
            btnEkle = createStyledButton("Ekle", new Color(46, 204, 113));
            btnGuncelle = createStyledButton("Güncelle", new Color(52, 152, 219));
            btnSil = createStyledButton("Sil", new Color(231, 76, 60));
            btnYenile = createStyledButton("Yenile", new Color(149, 165, 166));
            
            panelButonlar.add(btnEkle);
            panelButonlar.add(btnGuncelle);
            panelButonlar.add(btnSil);
            panelButonlar.add(btnYenile);
            
            gbc.gridx = 0; gbc.gridy = row;
            gbc.gridwidth = 4;
            panelForm.add(panelButonlar, gbc);
            
            // Tablo
            String[] kolonlar = {"ID", "Film Adı", "Tür", "Süre", "Yönetmen", "Oyuncular"};
            tableModel = new DefaultTableModel(kolonlar, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            tableFilmler = new JTable(tableModel);
            setupTableStyle(tableFilmler);
            
            JScrollPane scrollPane = new JScrollPane(tableFilmler);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Film Listesi"));
            
            add(panelForm, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            
            // Event listeners
            btnEkle.addActionListener(e -> filmEkle());
            btnGuncelle.addActionListener(e -> filmGuncelle());
            btnSil.addActionListener(e -> filmSil());
            btnYenile.addActionListener(e -> loadFilmler());
            btnAfisSeç.addActionListener(e -> afisSeç());
            
            tableFilmler.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    tablodanBilgileriDoldur();
                }
            });
        }
        
        private void afisSeç() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Resim Dosyaları", "jpg", "jpeg", "png", "gif"));
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File secilenDosya = fileChooser.getSelectedFile();
                txtAfisYolu.setText(secilenDosya.getAbsolutePath());
                
                try {
                    ImageIcon icon = new ImageIcon(secilenDosya.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                    lblAfisOnizleme.setIcon(new ImageIcon(img));
                    lblAfisOnizleme.setText("");
                } catch (Exception e) {
                    lblAfisOnizleme.setIcon(null);
                    lblAfisOnizleme.setText("Önizleme Yüklenemedi");
                }
            }
        }
        
        private void loadFilmler() {
            tableModel.setRowCount(0);
            List<Film> filmler = filmController.tumFilmleriGetir();
            if (filmler != null) {
                for (Film film : filmler) {
                    Object[] row = {
                        film.getId(),
                        film.getAd(),
                        film.getTur(),
                        film.getSure() + " dk",
                        film.getYonetmen(),
                        film.getOyuncular()
                    };
                    tableModel.addRow(row);
                }
            }
        }
        
        private void tablodanBilgileriDoldur() {
            int selectedRow = tableFilmler.getSelectedRow();
            if (selectedRow != -1) {
                txtAd.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTur.setText(tableModel.getValueAt(selectedRow, 2).toString());
                String sureStr = tableModel.getValueAt(selectedRow, 3).toString().replace(" dk", "");
                txtSure.setText(sureStr);
                txtYonetmen.setText(tableModel.getValueAt(selectedRow, 4).toString());
                
                Object oyuncularObj = tableModel.getValueAt(selectedRow, 5);
                txtOyuncular.setText(oyuncularObj != null ? oyuncularObj.toString() : "");
            }
        }
        
        private void filmEkle() {
            try {
                String ad = txtAd.getText().trim();
                String tur = txtTur.getText().trim();
                String sureStr = txtSure.getText().trim();
                String yonetmen = txtYonetmen.getText().trim();
                String oyuncular = txtOyuncular.getText().trim();
                
                if (ad.isEmpty() || tur.isEmpty() || sureStr.isEmpty() || yonetmen.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen zorunlu alanları doldurun!");
                    return;
                }
                
                int sure = Integer.parseInt(sureStr);
                
                if (filmController.filmEkle(ad, tur, sure, yonetmen, oyuncular)) {
                    JOptionPane.showMessageDialog(this, "Film başarıyla eklendi!");
                    loadFilmler();
                    temizle();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Süre sayı olmalıdır!");
            }
        }
        
        private void filmGuncelle() {
            int selectedRow = tableFilmler.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen güncellenecek filmi seçin!");
                return;
            }
            
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String ad = txtAd.getText().trim();
                String tur = txtTur.getText().trim();
                int sure = Integer.parseInt(txtSure.getText().trim());
                String yonetmen = txtYonetmen.getText().trim();
                String oyuncular = txtOyuncular.getText().trim();
                
                if (filmController.filmGuncelle(id, ad, tur, sure, yonetmen, oyuncular)) {
                    JOptionPane.showMessageDialog(this, "Film başarıyla güncellendi!");
                    loadFilmler();
                    temizle();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Süre sayı olmalıdır!");
            }
        }
        
        private void filmSil() {
            int selectedRow = tableFilmler.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek filmi seçin!");
                return;
            }
            
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String filmAdi = tableModel.getValueAt(selectedRow, 1).toString();
            
            int cevap = JOptionPane.showConfirmDialog(this,
                "'" + filmAdi + "' filmini silmek istediğinize emin misiniz?",
                "Film Silme",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (cevap == JOptionPane.YES_OPTION) {
                if (filmController.filmSil(id)) {
                    JOptionPane.showMessageDialog(this, "Film başarıyla silindi!");
                    loadFilmler();
                    temizle();
                }
            }
        }
        
        private void temizle() {
            txtAd.setText("");
            txtTur.setText("");
            txtSure.setText("");
            txtYonetmen.setText("");
            txtOyuncular.setText("");
            txtAciklama.setText("");
            txtAfisYolu.setText("");
            lblAfisOnizleme.setIcon(null);
            lblAfisOnizleme.setText("Afiş Seçilmedi");
            tableFilmler.clearSelection();
        }
    }

    // SEANS YÖNETİMİ PANELİ
    class SeansYonetimiPanel extends JPanel {
        private JTable tableSeanslar;
        private DefaultTableModel tableModel;
        private JComboBox<Film> cmbFilmler;
        private JComboBox<Salon> cmbSalonlar;
        private JTextField txtTarih, txtSaat, txtFiyat;
        private JButton btnEkle, btnGuncelle, btnSil, btnYenile;
        
        public SeansYonetimiPanel() {
            initPanel();
            loadData();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel - Form
            JPanel panelForm = new JPanel(new GridBagLayout());
            panelForm.setBorder(BorderFactory.createTitledBorder("Seans Bilgileri"));
            panelForm.setBackground(Color.WHITE);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Form alanları
            gbc.gridx = 0; gbc.gridy = 0;
            panelForm.add(new JLabel("Film:"), gbc);
            gbc.gridx = 1;
            cmbFilmler = new JComboBox<>();
            cmbFilmler.setPreferredSize(new Dimension(200, 30));
            cmbFilmler.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, 
                        int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Film) {
                        setText(((Film) value).getAd());
                    }
                    return this;
                }
            });
            panelForm.add(cmbFilmler, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Salon:"), gbc);
            gbc.gridx = 3;
            cmbSalonlar = new JComboBox<>();
            cmbSalonlar.setPreferredSize(new Dimension(150, 30));
            cmbSalonlar.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, 
                        int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Salon) {
                        setText(((Salon) value).getAd());
                    }
                    return this;
                }
            });
            panelForm.add(cmbSalonlar, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            panelForm.add(new JLabel("Tarih (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1;
            txtTarih = new JTextField();
            txtTarih.setToolTipText("Örnek: 2025-05-24");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            txtTarih.setText(sdf.format(new java.util.Date()));
            panelForm.add(txtTarih, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Saat (HH:MM):"), gbc);
            gbc.gridx = 3;
            txtSaat = new JTextField();
            txtSaat.setToolTipText("Örnek: 14:30");
            txtSaat.setText("14:00");
            panelForm.add(txtSaat, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            panelForm.add(new JLabel("Fiyat (TL):"), gbc);
            gbc.gridx = 1;
            txtFiyat = new JTextField();
            txtFiyat.setText("45.00");
            panelForm.add(txtFiyat, gbc);
            
            // Butonlar
            JPanel panelButonlar = new JPanel(new FlowLayout());
            panelButonlar.setBackground(Color.WHITE);
            
            btnEkle = createStyledButton("Ekle", new Color(46, 204, 113));
            btnGuncelle = createStyledButton("Güncelle", new Color(52, 152, 219));
            btnSil = createStyledButton("Sil", new Color(231, 76, 60));
            btnYenile = createStyledButton("Yenile", new Color(149, 165, 166));
            
            panelButonlar.add(btnEkle);
            panelButonlar.add(btnGuncelle);
            panelButonlar.add(btnSil);
            panelButonlar.add(btnYenile);
            
            gbc.gridx = 0; gbc.gridy = 3;
            gbc.gridwidth = 4;
            panelForm.add(panelButonlar, gbc);
            
            // Tablo
            String[] kolonlar = {"ID", "Film", "Salon", "Tarih", "Saat", "Fiyat"};
            tableModel = new DefaultTableModel(kolonlar, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            tableSeanslar = new JTable(tableModel);
            setupTableStyle(tableSeanslar);
            
            JScrollPane scrollPane = new JScrollPane(tableSeanslar);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Seans Listesi"));
            
            add(panelForm, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            
            // Event listeners
            btnEkle.addActionListener(e -> seansEkle());
            btnGuncelle.addActionListener(e -> seansGuncelle());
            btnSil.addActionListener(e -> seansSil());
            btnYenile.addActionListener(e -> loadSeanslar());
            
            tableSeanslar.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    tablodanBilgileriDoldur();
                }
            });
        }
        
        private void loadData() {
            loadFilmler();
            loadSalonlar();
            loadSeanslar();
        }
        
        private void loadFilmler() {
            cmbFilmler.removeAllItems();
            List<Film> filmler = filmController.tumFilmleriGetir();
            if (filmler != null) {
                for (Film film : filmler) {
                    cmbFilmler.addItem(film);
                }
            }
        }
        
        private void loadSalonlar() {
            cmbSalonlar.removeAllItems();
            List<Salon> salonlar = salonController.tumSalonlariGetir();
            if (salonlar != null) {
                for (Salon salon : salonlar) {
                    cmbSalonlar.addItem(salon);
                }
            }
        }
        
        private void loadSeanslar() {
            tableModel.setRowCount(0);
            List<Seans> seanslar = seansController.aktifSeansGetir();
            if (seanslar != null) {
                for (Seans seans : seanslar) {
                    Film film = filmController.filmGetir(seans.getFilmId());
                    Salon salon = salonController.salonGetir(seans.getSalonId());
                    
                    Object[] row = {
                        seans.getId(),
                        film != null ? film.getAd() : "Bilinmeyen Film",
                        salon != null ? salon.getAd() : "Bilinmeyen Salon",
                        seans.getTarih(),
                        seans.getSaat(),
                        String.format("%.2f TL", seans.getFiyat())
                    };
                    tableModel.addRow(row);
                }
            }
        }
        
        private void tablodanBilgileriDoldur() {
            int selectedRow = tableSeanslar.getSelectedRow();
            if (selectedRow != -1) {
                int seansId = (int) tableModel.getValueAt(selectedRow, 0);
                Seans seans = seansController.seansGetir(seansId);
                
                if (seans != null) {
                    Film film = filmController.filmGetir(seans.getFilmId());
                    Salon salon = salonController.salonGetir(seans.getSalonId());
                    
                    if (film != null) cmbFilmler.setSelectedItem(film);
                    if (salon != null) cmbSalonlar.setSelectedItem(salon);
                    
                    txtTarih.setText(seans.getTarih().toString());
                    txtSaat.setText(seans.getSaat().toString());
                    txtFiyat.setText(String.valueOf(seans.getFiyat()));
                }
            }
        }
        
        private void seansEkle() {
            try {
                Film seciliFilm = (Film) cmbFilmler.getSelectedItem();
                Salon seciliSalon = (Salon) cmbSalonlar.getSelectedItem();
                
                if (seciliFilm == null || seciliSalon == null) {
                    JOptionPane.showMessageDialog(this, "Film ve salon seçmelisiniz!");
                    return;
                }
                
                String tarihStr = txtTarih.getText().trim();
                String saatStr = txtSaat.getText().trim();
                String fiyatStr = txtFiyat.getText().trim();
                
                if (tarihStr.isEmpty() || saatStr.isEmpty() || fiyatStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!");
                    return;
                }
                
                Date tarih = Date.valueOf(tarihStr);
                Time saat = Time.valueOf(saatStr + ":00");
                double fiyat = Double.parseDouble(fiyatStr);
                
                if (seansController.seansEkle(seciliFilm.getId(), seciliSalon.getId(), tarih, saat, fiyat)) {
                    JOptionPane.showMessageDialog(this, "Seans başarıyla eklendi!");
                    loadSeanslar();
                    temizle();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
            }
        }
        
        private void seansGuncelle() {
            int selectedRow = tableSeanslar.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen güncellenecek seansı seçin!");
                return;
            }
            
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Film seciliFilm = (Film) cmbFilmler.getSelectedItem();
                Salon seciliSalon = (Salon) cmbSalonlar.getSelectedItem();
                
                if (seciliFilm == null || seciliSalon == null) {
                    JOptionPane.showMessageDialog(this, "Film ve salon seçmelisiniz!");
                    return;
                }
                
                Date tarih = Date.valueOf(txtTarih.getText().trim());
                Time saat = Time.valueOf(txtSaat.getText().trim() + ":00");
                double fiyat = Double.parseDouble(txtFiyat.getText().trim());
                
                if (seansController.seansGuncelle(id, seciliFilm.getId(), seciliSalon.getId(), tarih, saat, fiyat)) {
                    JOptionPane.showMessageDialog(this, "Seans başarıyla güncellendi!");
                    loadSeanslar();
                    temizle();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
            }
        }
        
        private void seansSil() {
            int selectedRow = tableSeanslar.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek seansı seçin!");
                return;
            }
            
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            if (seansController.seansSil(id)) {
                JOptionPane.showMessageDialog(this, "Seans başarıyla silindi!");
                loadSeanslar();
                temizle();
            }
        }
        
        private void temizle() {
            cmbFilmler.setSelectedIndex(-1);
            cmbSalonlar.setSelectedIndex(-1);
            txtTarih.setText("");
            txtSaat.setText("");
            txtFiyat.setText("");
            tableSeanslar.clearSelection();
        }
    }

    // SALON YÖNETİMİ PANELİ
    class SalonYonetimiPanel extends JPanel {
        private JTable tableSalonlar;
        private DefaultTableModel tableModel;
        private JTextField txtAd, txtKapasite, txtSatir, txtSutun;
        private JCheckBox chkVip;
        private JButton btnEkle, btnGuncelle, btnSil, btnYenile;
        
        public SalonYonetimiPanel() {
            initPanel();
            loadSalonlar();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel - Form
            JPanel panelForm = new JPanel(new GridBagLayout());
            panelForm.setBorder(BorderFactory.createTitledBorder("Salon Bilgileri"));
            panelForm.setBackground(Color.WHITE);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Form alanları
            gbc.gridx = 0; gbc.gridy = 0;
            panelForm.add(new JLabel("Salon Adı:"), gbc);
            gbc.gridx = 1;
            txtAd = new JTextField(15);
            panelForm.add(txtAd, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Kapasite:"), gbc);
            gbc.gridx = 3;
            txtKapasite = new JTextField(10);
            panelForm.add(txtKapasite, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            panelForm.add(new JLabel("Satır Sayısı:"), gbc);
            gbc.gridx = 1;
            txtSatir = new JTextField(15);
            panelForm.add(txtSatir, gbc);
            
            gbc.gridx = 2;
            panelForm.add(new JLabel("Sütun Sayısı:"), gbc);
            gbc.gridx = 3;
            txtSutun = new JTextField(10);
            panelForm.add(txtSutun, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            chkVip = new JCheckBox("VIP Salon");
            panelForm.add(chkVip, gbc);
            
            // Butonlar
            JPanel panelButonlar = new JPanel(new FlowLayout());
            panelButonlar.setBackground(Color.WHITE);
            
            btnEkle = createStyledButton("Ekle", new Color(46, 204, 113));
            btnGuncelle = createStyledButton("Güncelle", new Color(52, 152, 219));
            btnSil = createStyledButton("Sil", new Color(231, 76, 60));
            btnYenile = createStyledButton("Yenile", new Color(149, 165, 166));
            
            panelButonlar.add(btnEkle);
            panelButonlar.add(btnGuncelle);
            panelButonlar.add(btnSil);
            panelButonlar.add(btnYenile);
            
            gbc.gridx = 0; gbc.gridy = 3;
            gbc.gridwidth = 4;
            panelForm.add(panelButonlar, gbc);
            
            // Tablo
            String[] kolonlar = {"ID", "Salon Adı", "Kapasite", "Satır", "Sütun", "VIP"};
            tableModel = new DefaultTableModel(kolonlar, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            tableSalonlar = new JTable(tableModel);
            setupTableStyle(tableSalonlar);
            
            JScrollPane scrollPane = new JScrollPane(tableSalonlar);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Salon Listesi"));
            
            add(panelForm, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            
            // Event listeners
            btnEkle.addActionListener(e -> salonEkle());
            btnGuncelle.addActionListener(e -> salonGuncelle());
            btnSil.addActionListener(e -> salonSil());
            btnYenile.addActionListener(e -> loadSalonlar());
            
            tableSalonlar.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    tablodanBilgileriDoldur();
                }
            });
            
            // Kapasite otomatik hesaplama
            KeyAdapter keyAdapter = new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    hesaplaKapasite();
                }
            };
            txtSatir.addKeyListener(keyAdapter);
            txtSutun.addKeyListener(keyAdapter);
        }
        
        private void hesaplaKapasite() {
            try {
                String satirStr = txtSatir.getText().trim();
                String sutunStr = txtSutun.getText().trim();
                
                if (!satirStr.isEmpty() && !sutunStr.isEmpty()) {
                    int satir = Integer.parseInt(satirStr);
                    int sutun = Integer.parseInt(sutunStr);
                    int kapasite = satir * sutun;
                    txtKapasite.setText(String.valueOf(kapasite));
                }
            } catch (NumberFormatException e) {
                // Geçersiz sayı - sessizce göz ardı et
            }
        }
        
        private void loadSalonlar() {
            tableModel.setRowCount(0);
            List<Salon> salonlar = salonController.tumSalonlariGetir();
            if (salonlar != null) {
                for (Salon salon : salonlar) {
                    Object[] row = {
                        salon.getId(),
                        salon.getAd(),
                        salon.getKapasite(),
                        salon.getSatir(),
                        salon.getSutun(),
                        salon.isVip() ? "Evet" : "Hayır"
                    };
                    tableModel.addRow(row);
                }
            }
        }
        
        private void tablodanBilgileriDoldur() {
            int selectedRow = tableSalonlar.getSelectedRow();
            if (selectedRow != -1) {
                txtAd.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtKapasite.setText(tableModel.getValueAt(selectedRow, 2).toString());
                
                Object satirObj = tableModel.getValueAt(selectedRow, 3);
                Object sutunObj = tableModel.getValueAt(selectedRow, 4);
                
                if (satirObj != null) txtSatir.setText(satirObj.toString());
                if (sutunObj != null) txtSutun.setText(sutunObj.toString());
                
                String vipStr = tableModel.getValueAt(selectedRow, 5).toString();
                chkVip.setSelected("Evet".equals(vipStr));
            }
        }
        
        private void salonEkle() {
            try {
                String ad = txtAd.getText().trim();
                if (ad.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Salon adı boş olamaz!");
                    return;
                }
                
                int kapasite = Integer.parseInt(txtKapasite.getText().trim());
                if (kapasite <= 0) {
                    JOptionPane.showMessageDialog(this, "Kapasite 0'dan büyük olmalıdır!");
                    return;
                }
                
                if (salonController.salonEkle(ad, kapasite)) {
                    JOptionPane.showMessageDialog(this, "Salon başarıyla eklendi!");
                    loadSalonlar();
                    temizle();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kapasite sayı olmalıdır!");
            }
        }
        
        private void salonGuncelle() {
            int selectedRow = tableSalonlar.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen güncellenecek salonu seçin!");
                return;
            }
            
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String ad = txtAd.getText().trim();
                int kapasite = Integer.parseInt(txtKapasite.getText().trim());
                
                if (salonController.salonGuncelle(id, ad, kapasite)) {
                    JOptionPane.showMessageDialog(this, "Salon başarıyla güncellendi!");
                    loadSalonlar();
                    temizle();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kapasite sayı olmalıdır!");
            }
        }
        
        private void salonSil() {
            int selectedRow = tableSalonlar.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek salonu seçin!");
                return;
            }
            
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            if (salonController.salonSil(id)) {
                JOptionPane.showMessageDialog(this, "Salon başarıyla silindi!");
                loadSalonlar();
                temizle();
            }
        }
        
        private void temizle() {
            txtAd.setText("");
            txtKapasite.setText("");
            txtSatir.setText("");
            txtSutun.setText("");
            chkVip.setSelected(false);
            tableSalonlar.clearSelection();
        }
    }

    // KULLANICI YÖNETİMİ PANELİ
    class KullaniciYonetimiPanel extends JPanel {
        private JTable tableKullanicilar;
        private DefaultTableModel tableModel;
        private JComboBox<String> cmbRol;
        private JButton btnRolDegistir, btnYenile;
        
        public KullaniciYonetimiPanel() {
            initPanel();
            loadKullanicilar();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel
            JPanel panelUst = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelUst.setBorder(BorderFactory.createTitledBorder("Kullanıcı İşlemleri"));
            panelUst.setBackground(Color.WHITE);
            
            panelUst.add(new JLabel("Yeni Rol:"));
            cmbRol = new JComboBox<>(new String[]{"Müşteri", "Yönetici"});
            panelUst.add(cmbRol);
            
            btnRolDegistir = createStyledButton("Rol Değiştir", new Color(52, 152, 219));
            panelUst.add(btnRolDegistir);
            
            btnYenile = createStyledButton("Yenile", new Color(149, 165, 166));
            panelUst.add(btnYenile);
            
            // Tablo
            String[] kolonlar = {"ID", "Kullanıcı Adı", "Ad", "Soyad", "Email", "Telefon", "Rol"};
            tableModel = new DefaultTableModel(kolonlar, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            tableKullanicilar = new JTable(tableModel);
            setupTableStyle(tableKullanicilar);
            
            JScrollPane scrollPane = new JScrollPane(tableKullanicilar);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Kullanıcı Listesi"));
            
            add(panelUst, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            
            // Event listeners
            btnRolDegistir.addActionListener(e -> rolDegistir());
            btnYenile.addActionListener(e -> loadKullanicilar());
        }
        
        private void loadKullanicilar() {
            tableModel.setRowCount(0);
            List<Kullanici> kullanicilar = kullaniciController.tumKullanicilariGetir();
            if (kullanicilar != null) {
                for (Kullanici k : kullanicilar) {
                    Object[] row = {
                        k.getId(),
                        k.getKullaniciAdi(),
                        k.getAd(),
                        k.getSoyad(),
                        k.getEmail(),
                        k.getTelefon(),
                        k.getRol()
                    };
                    tableModel.addRow(row);
                }
            }
        }
        
        private void rolDegistir() {
            int selectedRow = tableKullanicilar.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen bir kullanıcı seçin!");
                return;
            }
            
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String yeniRol = cmbRol.getSelectedItem().toString();
            
            if (kullaniciController.kullaniciRoluDegistir(id, yeniRol)) {
                JOptionPane.showMessageDialog(this, "Rol başarıyla değiştirildi!");
                loadKullanicilar();
            }
        }
    }

    // RAPORLAR PANELİ
    class RaporlarPanel extends JPanel {
        private JLabel lblGunlukSatis, lblAylikSatis;
        private JButton btnGunlukRapor, btnAylikRapor, btnFilmRaporu, btnSalonRaporu;
        
        public RaporlarPanel() {
            initPanel();
            loadRaporlar();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(236, 240, 241));
            
            // Üst panel - Başlık
            JPanel panelBaslik = new JPanel();
            panelBaslik.setBackground(Color.WHITE);
            panelBaslik.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel lblBaslik = new JLabel("SATIŞ RAPORLARI");
            lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
            lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
            panelBaslik.add(lblBaslik);
            
            // Orta panel - Kartlar
            JPanel panelKartlar = new JPanel(new GridLayout(2, 2, 20, 20));
            panelKartlar.setBackground(new Color(236, 240, 241));
            panelKartlar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Günlük satış kartı
            JPanel panelGunluk = createRaporKarti("Günlük Satış", "0.00 TL", new Color(46, 204, 113));
            lblGunlukSatis = (JLabel) ((JPanel) panelGunluk.getComponent(1)).getComponent(0);
            btnGunlukRapor = (JButton) ((JPanel) panelGunluk.getComponent(2)).getComponent(0);
            btnGunlukRapor.addActionListener(e -> gunlukRaporGoster());
            
            // Aylık satış kartı
            JPanel panelAylik = createRaporKarti("Aylık Satış", "0.00 TL", new Color(52, 152, 219));
            lblAylikSatis = (JLabel) ((JPanel) panelAylik.getComponent(1)).getComponent(0);
            btnAylikRapor = (JButton) ((JPanel) panelAylik.getComponent(2)).getComponent(0);
            btnAylikRapor.addActionListener(e -> aylikRaporGoster());
            
            // Film raporu kartı
            JPanel panelFilm = createRaporKarti("Film Raporu", "En Çok İzlenen", new Color(155, 89, 182));
            btnFilmRaporu = (JButton) ((JPanel) panelFilm.getComponent(2)).getComponent(0);
            btnFilmRaporu.addActionListener(e -> filmRaporuGoster());
            
            // Salon raporu kartı
            JPanel panelSalon = createRaporKarti("Salon Raporu", "Doluluk Oranları", new Color(230, 126, 34));
            btnSalonRaporu = (JButton) ((JPanel) panelSalon.getComponent(2)).getComponent(0);
            btnSalonRaporu.addActionListener(e -> salonRaporuGoster());
            
            panelKartlar.add(panelGunluk);
            panelKartlar.add(panelAylik);
            panelKartlar.add(panelFilm);
            panelKartlar.add(panelSalon);
            
            add(panelBaslik, BorderLayout.NORTH);
            add(panelKartlar, BorderLayout.CENTER);
        }
        
        private JPanel createRaporKarti(String baslik, String deger, Color renk) {
            JPanel kart = new JPanel(new BorderLayout());
            kart.setBackground(Color.WHITE);
            kart.setBorder(BorderFactory.createLineBorder(renk, 2));
            kart.setPreferredSize(new Dimension(250, 200));
            
            // Başlık
            JPanel panelBaslik = new JPanel();
            panelBaslik.setBackground(renk);
            JLabel lblBaslik = new JLabel(baslik);
            lblBaslik.setForeground(Color.WHITE);
            lblBaslik.setFont(new Font("Arial", Font.BOLD, 16));
            panelBaslik.add(lblBaslik);
            
            // Değer
            JPanel panelDeger = new JPanel();
            panelDeger.setBackground(Color.WHITE);
            JLabel lblDeger = new JLabel(deger);
            lblDeger.setFont(new Font("Arial", Font.BOLD, 24));
            lblDeger.setForeground(renk);
            panelDeger.add(lblDeger);
            
            // Buton
            JPanel panelButon = new JPanel();
            panelButon.setBackground(Color.WHITE);
            JButton btnDetay = new JButton("Detaylı Rapor");
            btnDetay.setBackground(renk);
            btnDetay.setForeground(Color.WHITE);
            btnDetay.setFocusPainted(false);
            btnDetay.setBorderPainted(false);
            panelButon.add(btnDetay);
            
            kart.add(panelBaslik, BorderLayout.NORTH);
            kart.add(panelDeger, BorderLayout.CENTER);
            kart.add(panelButon, BorderLayout.SOUTH);
            
            return kart;
        }
        
        private void loadRaporlar() {
            try {
                double gunlukSatis = biletController.gunlukSatisGetir();
                double aylikSatis = biletController.aylikSatisGetir();
                
                lblGunlukSatis.setText(String.format("%.2f TL", gunlukSatis));
                lblAylikSatis.setText(String.format("%.2f TL", aylikSatis));
            } catch (Exception e) {
                lblGunlukSatis.setText("Hata");
                lblAylikSatis.setText("Hata");
            }
        }
        
        private void gunlukRaporGoster() {
            try {
                double gunlukSatis = biletController.gunlukSatisGetir();
                
                StringBuilder sb = new StringBuilder();
                sb.append("=== GÜNLÜK SATIŞ RAPORU ===\n\n");
                sb.append("Tarih: ").append(new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date())).append("\n");
                sb.append("Toplam Satış: ").append(String.format("%.2f TL", gunlukSatis)).append("\n\n");
                sb.append("=== DETAYLAR ===\n");
                
                if (gunlukSatis > 0) {
                    sb.append("• Aktif satışlar mevcut\n");
                    sb.append("• Günlük hedef: 1000.00 TL\n");
                    if (gunlukSatis >= 1000) {
                        sb.append("• ✅ Günlük hedef aşıldı!\n");
                    } else {
                        sb.append("• ⚠️  Hedefin %").append(String.format("%.1f", (gunlukSatis/1000)*100)).append("'si gerçekleştirildi\n");
                    }
                } else {
                    sb.append("• Bugün henüz satış yapılmamış\n");
                }
                
                sb.append("\nBu rapor bugünkü satış verilerini gösterir.");
                
                showRaporDialog("Günlük Satış Raporu", sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Rapor oluşturulurken hata: " + e.getMessage());
            }
        }
        
        private void aylikRaporGoster() {
            try {
                double aylikSatis = biletController.aylikSatisGetir();
                
                StringBuilder sb = new StringBuilder();
                sb.append("=== AYLIK SATIŞ RAPORU ===\n\n");
                sb.append("Ay: ").append(new SimpleDateFormat("MMMM yyyy").format(new java.util.Date())).append("\n");
                sb.append("Toplam Satış: ").append(String.format("%.2f TL", aylikSatis)).append("\n\n");
                sb.append("=== DETAYLAR ===\n");
                
                if (aylikSatis > 0) {
                    sb.append("• Aylık aktif satışlar mevcut\n");
                    sb.append("• Aylık hedef: 30000.00 TL\n");
                    if (aylikSatis >= 30000) {
                        sb.append("• ✅ Aylık hedef aşıldı!\n");
                    } else {
                        sb.append("• ⚠️  Hedefin %").append(String.format("%.1f", (aylikSatis/30000)*100)).append("'si gerçekleştirildi\n");
                    }
                    
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    int gunSayisi = cal.get(java.util.Calendar.DAY_OF_MONTH);
                    double gunlukOrtalama = aylikSatis / gunSayisi;
                    sb.append("• Günlük ortalama: ").append(String.format("%.2f TL", gunlukOrtalama)).append("\n");
                } else {
                    sb.append("• Bu ay henüz satış yapılmamış\n");
                }
                
                sb.append("\nBu rapor aylık satış verilerini gösterir.");
                
                showRaporDialog("Aylık Satış Raporu", sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Rapor oluşturulurken hata: " + e.getMessage());
            }
        }
        
        private void filmRaporuGoster() {
            try {
                List<Film> filmler = filmController.tumFilmleriGetir();
                
                StringBuilder sb = new StringBuilder();
                sb.append("=== FİLM RAPORU ===\n\n");
                sb.append("Sistem Tarihi: ").append(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date())).append("\n");
                sb.append("Toplam Film Sayısı: ").append(filmler != null ? filmler.size() : 0).append("\n\n");
                
                if (filmler != null && !filmler.isEmpty()) {
                    // Tür istatistikleri
                    java.util.Map<String, Integer> turSayilari = new java.util.HashMap<>();
                    java.util.Map<String, Integer> turSureleri = new java.util.HashMap<>();
                    int toplamSure = 0;
                    
                    for (Film film : filmler) {
                        String tur = film.getTur();
                        turSayilari.put(tur, turSayilari.getOrDefault(tur, 0) + 1);
                        turSureleri.put(tur, turSureleri.getOrDefault(tur, 0) + film.getSure());
                        toplamSure += film.getSure();
                    }
                    
                    sb.append("=== İSTATİSTİKLER ===\n");
                    sb.append("• Ortalama Film Süresi: ").append(String.format("%.1f", (double)toplamSure/filmler.size())).append(" dakika\n");
                    
                    Film enUzun = filmler.stream().max((f1, f2) -> Integer.compare(f1.getSure(), f2.getSure())).orElse(null);
                    if (enUzun != null) {
                        sb.append("• En Uzun Film: ").append(enUzun.getAd()).append(" (").append(enUzun.getSure()).append(" dk)\n");
                    }
                    
                    Film enKisa = filmler.stream().min((f1, f2) -> Integer.compare(f1.getSure(), f2.getSure())).orElse(null);
                    if (enKisa != null) {
                        sb.append("• En Kısa Film: ").append(enKisa.getAd()).append(" (").append(enKisa.getSure()).append(" dk)\n");
                    }
                    
                    sb.append("\n=== TÜR DAĞILIMI ===\n");
                    for (java.util.Map.Entry<String, Integer> entry : turSayilari.entrySet()) {
                        String tur = entry.getKey();
                        int sayi = entry.getValue();
                        double yuzde = (double) sayi / filmler.size() * 100;
                        int toplamTurSure = turSureleri.get(tur);
                        double ortalamaSure = (double) toplamTurSure / sayi;
                        
                        sb.append("• ").append(tur).append(": ").append(sayi).append(" film");
                        sb.append(" (%").append(String.format("%.1f", yuzde)).append(")");
                        sb.append(" - Ort. ").append(String.format("%.0f", ortalamaSure)).append(" dk\n");
                    }
                    
                    sb.append("\n=== DETAYLI LİSTE ===\n");
                    filmler.sort((f1, f2) -> f1.getAd().compareToIgnoreCase(f2.getAd()));
                    for (int i = 0; i < filmler.size(); i++) {
                        Film film = filmler.get(i);
                        sb.append(String.format("%2d. ", i+1)).append(film.getAd());
                        sb.append(" (").append(film.getTur()).append(")");
                        sb.append(" - ").append(film.getSure()).append(" dk");
                        sb.append(" - Yön: ").append(film.getYonetmen()).append("\n");
                    }
                } else {
                    sb.append("❌ Sistemde kayıtlı film bulunmamaktadır.\n");
                    sb.append("Film eklemek için Film Yönetimi panelini kullanın.");
                }
                
                showRaporDialog("Film Raporu", sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Film raporu oluşturulurken hata oluştu:\n" + e.getMessage(), 
                    "Hata", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void salonRaporuGoster() {
            try {
                List<Salon> salonlar = salonController.tumSalonlariGetir();
                List<Seans> seanslar = seansController.aktifSeansGetir();
                
                StringBuilder sb = new StringBuilder();
                sb.append("=== SALON RAPORU ===\n\n");
                sb.append("Rapor Tarihi: ").append(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date())).append("\n");
                sb.append("Toplam Salon Sayısı: ").append(salonlar != null ? salonlar.size() : 0).append("\n");
                sb.append("Aktif Seans Sayısı: ").append(seanslar != null ? seanslar.size() : 0).append("\n\n");
                
                if (salonlar != null && !salonlar.isEmpty()) {
                    int toplamKapasite = 0;
                    int vipSalon = 0;
                    int minKapasite = Integer.MAX_VALUE;
                    int maxKapasite = 0;
                    
                    for (Salon salon : salonlar) {
                        toplamKapasite += salon.getKapasite();
                        if (salon.isVip()) vipSalon++;
                        if (salon.getKapasite() < minKapasite) minKapasite = salon.getKapasite();
                        if (salon.getKapasite() > maxKapasite) maxKapasite = salon.getKapasite();
                    }
                    
                    sb.append("=== GENEL İSTATİSTİKLER ===\n");
                    sb.append("• Toplam Kapasite: ").append(toplamKapasite).append(" kişi\n");
                    sb.append("• Ortalama Kapasite: ").append(String.format("%.1f", (double)toplamKapasite/salonlar.size())).append(" kişi\n");
                    sb.append("• En Küçük Salon: ").append(minKapasite).append(" kişi\n");
                    sb.append("• En Büyük Salon: ").append(maxKapasite).append(" kişi\n");
                    sb.append("• VIP Salon Sayısı: ").append(vipSalon).append(" adet\n");
                    sb.append("• Normal Salon Sayısı: ").append(salonlar.size() - vipSalon).append(" adet\n\n");
                    
                    sb.append("=== SALON DETAYLARI ===\n");
                    salonlar.sort((s1, s2) -> s1.getAd().compareToIgnoreCase(s2.getAd()));
                    
                    for (int i = 0; i < salonlar.size(); i++) {
                        Salon salon = salonlar.get(i);
                        
                        int salonSeanslar = 0;
                        double toplamDoluluk = 0;
                        if (seanslar != null) {
                            for (Seans seans : seanslar) {
                                if (seans.getSalonId() == salon.getId()) {
                                    salonSeanslar++;
                                    toplamDoluluk += biletController.seansDolulukOraniGetir(seans.getId());
                                }
                            }
                        }
                        
                        double ortalamaDoluluk = salonSeanslar > 0 ? toplamDoluluk / salonSeanslar : 0;
                        
                        sb.append(String.format("%2d. ", i+1)).append(salon.getAd());
                        sb.append(" - Kapasite: ").append(salon.getKapasite()).append(" kişi");
                        if (salon.isVip()) {
                            sb.append(" (VIP)");
                        }
                        sb.append("\n");
                        sb.append("    Aktif Seans: ").append(salonSeanslar).append(" adet");
                        if (salonSeanslar > 0) {
                            sb.append(" - Ortalama Doluluk: %").append(String.format("%.1f", ortalamaDoluluk));
                        }
                        sb.append("\n");
                    }
                } else {
                    sb.append("❌ Sistemde kayıtlı salon bulunmamaktadır.\n");
                    sb.append("Salon eklemek için Salon Yönetimi panelini kullanın.");
                }
                
                showRaporDialog("Salon Raporu", sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Salon raporu oluşturulurken hata oluştu:\n" + e.getMessage(), 
                    "Hata", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void showRaporDialog(String baslik, String icerik) {
            JDialog dialog = new JDialog(YoneticiPaneli.this, baslik, true);
            dialog.setLayout(new BorderLayout());
            
            JTextArea textArea = new JTextArea(icerik);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setBackground(new Color(248, 249, 250));
            textArea.setForeground(new Color(44, 62, 80));
            textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            textArea.setCaretPosition(0);
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 500));
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
            
            // Butonlar
            JPanel panelButon = new JPanel(new FlowLayout());
            panelButon.setBackground(Color.WHITE);
            
            JButton btnKapat = new JButton("Kapat");
            btnKapat.setBackground(new Color(52, 152, 219));
            btnKapat.setForeground(Color.WHITE);
            btnKapat.setFocusPainted(false);
            btnKapat.addActionListener(e -> dialog.dispose());
            
            JButton btnYazdir = new JButton("Yazdır");
            btnYazdir.setBackground(new Color(46, 204, 113));
            btnYazdir.setForeground(Color.WHITE);
            btnYazdir.setFocusPainted(false);
            btnYazdir.addActionListener(e -> {
                try {
                    textArea.print();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Yazdırma hatası: " + ex.getMessage());
                }
            });
            
            panelButon.add(btnYazdir);
            panelButon.add(btnKapat);
            
            dialog.add(scrollPane, BorderLayout.CENTER);
            dialog.add(panelButon, BorderLayout.SOUTH);
            
            dialog.setSize(650, 600);
            dialog.setLocationRelativeTo(YoneticiPaneli.this);
            dialog.setVisible(true);
        }
    }
}