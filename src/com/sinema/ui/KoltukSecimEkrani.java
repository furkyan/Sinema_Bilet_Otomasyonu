package com.sinema.ui;

import com.sinema.controller.BiletController;
import com.sinema.controller.KullaniciController;
import com.sinema.model.Seans;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class KoltukSecimEkrani extends JFrame {
    private JPanel panelKoltuklar;
    private JLabel lblPerde;
    private JButton btnOnayla;
    private JButton btnIptal;
    private JLabel lblSeciliKoltuklar;
    private JLabel lblToplamFiyat;
    
    private List<JToggleButton> koltukButonlari;
    private List<String> seciliKoltuklar;
    private List<String> doluKoltuklar;
    
    private BiletController biletController;
    private Seans seans;
    private double biletFiyati;
    
    private static final int KOLTUK_SATIR = 8;
    private static final int KOLTUK_SUTUN = 10;
    
    // Renkler
    private static final Color RENK_BOS = new Color(76, 175, 80);      // Yeşil
    private static final Color RENK_SECILI = new Color(255, 152, 0);   // Turuncu
    private static final Color RENK_DOLU = new Color(244, 67, 54);     // Kırmızı
    private static final Color RENK_DISABLED = new Color(158, 158, 158); // Gri
    
    public KoltukSecimEkrani() {
        this(null); // Test için
    }
    
    public KoltukSecimEkrani(Seans seans) {
        this.seans = seans;
        this.biletController = new BiletController();
        this.koltukButonlari = new ArrayList<>();
        this.seciliKoltuklar = new ArrayList<>();
        this.biletFiyati = seans != null ? seans.getFiyat() : 50.0; // Test fiyatı
        
        initComponents();
        loadDoluKoltuklar();
    }
    
    private void initComponents() {
        setTitle("Koltuk Seçimi - " + (seans != null ? "Seans #" + seans.getId() : "Test"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Üst panel - Perde
        JPanel panelUst = new JPanel();
        panelUst.setBackground(new Color(240, 240, 240));
        panelUst.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        lblPerde = new JLabel("🎬 PERDE 🎬");
        lblPerde.setFont(new Font("Arial", Font.BOLD, 20));
        lblPerde.setForeground(new Color(44, 62, 80));
        lblPerde.setHorizontalAlignment(SwingConstants.CENTER);
        lblPerde.setPreferredSize(new Dimension(600, 40));
        lblPerde.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(44, 62, 80), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        lblPerde.setOpaque(true);
        lblPerde.setBackground(new Color(236, 240, 241));
        panelUst.add(lblPerde);
        
        // Orta panel - Koltuklar
        panelKoltuklar = new JPanel(new GridBagLayout());
        panelKoltuklar.setBackground(new Color(240, 240, 240));
        panelKoltuklar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        // Koltukları oluştur
        for (int i = 0; i < KOLTUK_SATIR; i++) {
            // Satır etiketi (A, B, C...)
            JLabel lblSatir = new JLabel(String.valueOf((char)('A' + i)));
            lblSatir.setFont(new Font("Arial", Font.BOLD, 16));
            lblSatir.setForeground(new Color(44, 62, 80));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.CENTER;
            panelKoltuklar.add(lblSatir, gbc);
            
            // Koltuklar
            for (int j = 0; j < KOLTUK_SUTUN; j++) {
                String koltukNo = String.valueOf((char)('A' + i)) + (j + 1);
                JToggleButton btnKoltuk = createKoltukButton(koltukNo, j + 1);
                
                // Koridor boşluğu
                if (j == 4) {
                    gbc.gridx = j + 2;
                } else if (j > 4) {
                    gbc.gridx = j + 3;
                } else {
                    gbc.gridx = j + 1;
                }
                gbc.gridy = i;
                
                koltukButonlari.add(btnKoltuk);
                panelKoltuklar.add(btnKoltuk, gbc);
            }
        }
        
        // Alt panel - Bilgi ve butonlar
        JPanel panelAlt = new JPanel(new BorderLayout());
        panelAlt.setBackground(Color.WHITE);
        panelAlt.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Sol - Açıklama
        JPanel panelAciklama = new JPanel(new GridLayout(3, 2, 15, 8));
        panelAciklama.setBackground(Color.WHITE);
        panelAciklama.setBorder(BorderFactory.createTitledBorder("Koltuk Durumu"));
        
        panelAciklama.add(createLegendItem("Boş", RENK_BOS));
        panelAciklama.add(createLegendItem("Seçili", RENK_SECILI));
        panelAciklama.add(createLegendItem("Dolu", RENK_DOLU));
        panelAciklama.add(new JLabel("")); // Boş alan
        
        // Orta - Seçim bilgisi
        JPanel panelBilgi = new JPanel(new GridLayout(2, 1, 5, 10));
        panelBilgi.setBackground(Color.WHITE);
        panelBilgi.setBorder(BorderFactory.createTitledBorder("Seçim Bilgisi"));
        
        lblSeciliKoltuklar = new JLabel("Seçili Koltuklar: -");
        lblSeciliKoltuklar.setFont(new Font("Arial", Font.PLAIN, 14));
        
        lblToplamFiyat = new JLabel("Toplam: 0.00 TL");
        lblToplamFiyat.setFont(new Font("Arial", Font.BOLD, 16));
        lblToplamFiyat.setForeground(new Color(46, 204, 113));
        
        panelBilgi.add(lblSeciliKoltuklar);
        panelBilgi.add(lblToplamFiyat);
        
        // Sağ - Butonlar
        JPanel panelButonlar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButonlar.setBackground(Color.WHITE);
        
        btnIptal = new JButton("İptal");
        btnIptal.setBackground(new Color(231, 76, 60));
        btnIptal.setForeground(Color.WHITE);
        btnIptal.setFont(new Font("Arial", Font.BOLD, 14));
        btnIptal.setPreferredSize(new Dimension(100, 40));
        btnIptal.setFocusPainted(false);
        btnIptal.setBorderPainted(false);
        btnIptal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnOnayla = new JButton("Onayla ve Satın Al");
        btnOnayla.setBackground(new Color(46, 204, 113));
        btnOnayla.setForeground(Color.WHITE);
        btnOnayla.setFont(new Font("Arial", Font.BOLD, 14));
        btnOnayla.setPreferredSize(new Dimension(150, 40));
        btnOnayla.setFocusPainted(false);
        btnOnayla.setBorderPainted(false);
        btnOnayla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOnayla.setEnabled(false);
        
        panelButonlar.add(btnIptal);
        panelButonlar.add(btnOnayla);
        
        panelAlt.add(panelAciklama, BorderLayout.WEST);
        panelAlt.add(panelBilgi, BorderLayout.CENTER);
        panelAlt.add(panelButonlar, BorderLayout.EAST);
        
        // Ana layout
        add(panelUst, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(panelKoltuklar);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        add(panelAlt, BorderLayout.SOUTH);
        
        // Event listeners
        btnIptal.addActionListener(e -> dispose());
        btnOnayla.addActionListener(e -> biletleriOnayla());
        
        // Hover efektleri
        addButtonHoverEffect(btnIptal, new Color(231, 76, 60), new Color(192, 57, 43));
        addButtonHoverEffect(btnOnayla, new Color(46, 204, 113), new Color(39, 174, 96));
        
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
    }
    
    private JToggleButton createKoltukButton(String koltukNo, int koltukNumarasi) {
        JToggleButton btnKoltuk = new JToggleButton(String.valueOf(koltukNumarasi));
        btnKoltuk.setPreferredSize(new Dimension(45, 45));
        btnKoltuk.setFont(new Font("Arial", Font.BOLD, 12));
        btnKoltuk.setActionCommand(koltukNo);
        
        // Koltuk stilini ayarla
        btnKoltuk.setBackground(RENK_BOS);
        btnKoltuk.setForeground(Color.WHITE);
        btnKoltuk.setFocusPainted(false);
        btnKoltuk.setBorderPainted(true);
        btnKoltuk.setBorder(BorderFactory.createRaisedBevelBorder());
        btnKoltuk.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKoltuk.setOpaque(true);
        
        // Hover efekti
        btnKoltuk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btnKoltuk.isEnabled() && !btnKoltuk.isSelected()) {
                    btnKoltuk.setBackground(RENK_BOS.brighter());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (btnKoltuk.isEnabled() && !btnKoltuk.isSelected()) {
                    btnKoltuk.setBackground(RENK_BOS);
                }
            }
        });
        
        btnKoltuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                koltukSecildi(btnKoltuk);
            }
        });
        
        return btnKoltuk;
    }
    
    private JPanel createLegendItem(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(Color.WHITE);
        
        // Renk kutusu
        JLabel colorBox = new JLabel();
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setOpaque(true);
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        // Metin
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(colorBox);
        panel.add(label);
        
        return panel;
    }
    
    private void addButtonHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(hoverColor);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }
    
    private void loadDoluKoltuklar() {
        if (seans != null) {
            doluKoltuklar = biletController.seansDoluKoltuklarGetir(seans.getId());
            System.out.println("Dolu koltuklar: " + doluKoltuklar);
        } else {
            // Test için bazı koltukları dolu yap
            doluKoltuklar = new ArrayList<>();
            doluKoltuklar.add("A3");
            doluKoltuklar.add("A4");
            doluKoltuklar.add("B5");
            doluKoltuklar.add("C7");
            System.out.println("Test dolu koltuklar: " + doluKoltuklar);
        }
        
        // Dolu koltukları işaretle
        for (JToggleButton btn : koltukButonlari) {
            String koltukNo = btn.getActionCommand();
            if (doluKoltuklar != null && doluKoltuklar.contains(koltukNo)) {
                btn.setEnabled(false);
                btn.setSelected(true);
                btn.setBackground(RENK_DOLU);
                btn.setForeground(Color.WHITE);
                btn.setText("✗");
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    private void koltukSecildi(JToggleButton btn) {
        String koltukNo = btn.getActionCommand();
        
        if (btn.isSelected()) {
            seciliKoltuklar.add(koltukNo);
            btn.setBackground(RENK_SECILI);
            btn.setForeground(Color.WHITE);
        } else {
            seciliKoltuklar.remove(koltukNo);
            btn.setBackground(RENK_BOS);
            btn.setForeground(Color.WHITE);
        }
        
        guncelleSecimBilgisi();
    }
    
    private void guncelleSecimBilgisi() {
        if (seciliKoltuklar.isEmpty()) {
            lblSeciliKoltuklar.setText("Seçili Koltuklar: -");
            lblToplamFiyat.setText("Toplam: 0.00 TL");
            btnOnayla.setEnabled(false);
        } else {
            // Koltukları sırala
            seciliKoltuklar.sort((a, b) -> {
                if (a.charAt(0) != b.charAt(0)) {
                    return Character.compare(a.charAt(0), b.charAt(0));
                }
                return Integer.compare(
                    Integer.parseInt(a.substring(1)), 
                    Integer.parseInt(b.substring(1))
                );
            });
            
            lblSeciliKoltuklar.setText("Seçili Koltuklar: " + String.join(", ", seciliKoltuklar));
            double toplam = seciliKoltuklar.size() * biletFiyati;
            lblToplamFiyat.setText(String.format("Toplam: %.2f TL", toplam));
            btnOnayla.setEnabled(true);
        }
    }
    
    private void biletleriOnayla() {
        if (seciliKoltuklar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen en az bir koltuk seçin!");
            return;
        }
        
        if (KullaniciController.getAktifKullanici() == null) {
            JOptionPane.showMessageDialog(this, "Bilet almak için giriş yapmalısınız!");
            return;
        }
        
        String mesaj = String.format(
            "Seçili koltuklar: %s\n" +
            "Bilet adedi: %d\n" +
            "Bilet fiyatı: %.2f TL (adet)\n" +
            "Toplam tutar: %.2f TL\n\n" +
            "Satın almak istediğinize emin misiniz?",
            String.join(", ", seciliKoltuklar),
            seciliKoltuklar.size(),
            biletFiyati,
            seciliKoltuklar.size() * biletFiyati
        );
        
        int cevap = JOptionPane.showConfirmDialog(this,
            mesaj,
            "Bilet Satın Alma Onayı",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (cevap == JOptionPane.YES_OPTION) {
            boolean basarili = true;
            int kullaniciId = KullaniciController.getAktifKullanici().getId();
            List<String> basarisizKoltuklar = new ArrayList<>();
            
            for (String koltukNo : seciliKoltuklar) {
                if (seans != null) {
                    if (!biletController.biletSatinAl(kullaniciId, seans.getId(), koltukNo, biletFiyati)) {
                        basarili = false;
                        basarisizKoltuklar.add(koltukNo);
                    }
                } else {
                    // Test için
                    System.out.println("Test: Bilet satın alındı - Koltuk: " + koltukNo);
                }
            }
            
            if (basarili) {
                JOptionPane.showMessageDialog(this, 
                    String.format("🎉 Tebrikler!\n\n" +
                                "Biletleriniz başarıyla satın alındı!\n" +
                                "Koltuk numaraları: %s\n" +
                                "Toplam tutar: %.2f TL\n\n" +
                                "İyi seyirler dileriz! 🍿",
                                String.join(", ", seciliKoltuklar),
                                seciliKoltuklar.size() * biletFiyati),
                    "Satın Alma Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                String hataMsg = "Bazı biletler satın alınamadı!";
                if (!basarisizKoltuklar.isEmpty()) {
                    hataMsg += "\nBaşarısız koltuklar: " + String.join(", ", basarisizKoltuklar);
                    hataMsg += "\nBu koltuklar başka müşteriler tarafından satın alınmış olabilir.";
                }
                JOptionPane.showMessageDialog(this, hataMsg, "Satın Alma Hatası", JOptionPane.ERROR_MESSAGE);
                
                // Sayfayı yenile
                loadDoluKoltuklar();
                // Seçili koltukları temizle
                for (JToggleButton btn : koltukButonlari) {
                    if (btn.isSelected() && btn.isEnabled()) {
                        btn.setSelected(false);
                        btn.setBackground(RENK_BOS);
                    }
                }
                seciliKoltuklar.clear();
                guncelleSecimBilgisi();
            }
        }
    }
    
    // Test için main metodu
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KoltukSecimEkrani().setVisible(true);
        });
    }
}