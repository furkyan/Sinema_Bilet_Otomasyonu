package com.sinema.ui;

import com.sinema.controller.KullaniciController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KayitEkrani extends JFrame {
    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JPasswordField txtSifreTekrar;
    private JTextField txtAd;
    private JTextField txtSoyad;
    private JTextField txtEmail;
    private JTextField txtTelefon;
    private JButton btnKayitOl;
    private JButton btnGeriDon;
    private KullaniciController kullaniciController;
    
    public KayitEkrani() {
        kullaniciController = new KullaniciController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Kayıt Ol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Başlık
        JLabel lblBaslik = new JLabel("YENİ KAYIT");
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
        lblBaslik.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblBaslik, gbc);
        
        // Kayıt formu paneli
        JPanel panelKayit = new JPanel(new GridBagLayout());
        panelKayit.setBackground(Color.WHITE);
        panelKayit.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(5, 5, 5, 5);
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel.anchor = GridBagConstraints.WEST;
        
        // Form alanları
        int row = 0;
        
        // Kullanıcı Adı
        panelKayit.add(createLabel("Kullanıcı Adı:"), createGbc(0, row++));
        txtKullaniciAdi = createTextField();
        panelKayit.add(txtKullaniciAdi, createGbc(0, row++));
        
        // Şifre
        panelKayit.add(createLabel("Şifre:"), createGbc(0, row++));
        txtSifre = createPasswordField();
        panelKayit.add(txtSifre, createGbc(0, row++));
        
        // Şifre Tekrar
        panelKayit.add(createLabel("Şifre Tekrar:"), createGbc(0, row++));
        txtSifreTekrar = createPasswordField();
        panelKayit.add(txtSifreTekrar, createGbc(0, row++));
        
        // Ad
        panelKayit.add(createLabel("Ad:"), createGbc(0, row++));
        txtAd = createTextField();
        panelKayit.add(txtAd, createGbc(0, row++));
        
        // Soyad
        panelKayit.add(createLabel("Soyad:"), createGbc(0, row++));
        txtSoyad = createTextField();
        panelKayit.add(txtSoyad, createGbc(0, row++));
        
        // Email
        panelKayit.add(createLabel("E-mail:"), createGbc(0, row++));
        txtEmail = createTextField();
        panelKayit.add(txtEmail, createGbc(0, row++));
        
        // Telefon
        panelKayit.add(createLabel("Telefon:"), createGbc(0, row++));
        txtTelefon = createTextField();
        panelKayit.add(txtTelefon, createGbc(0, row++));
        
        // Butonlar
        JPanel panelButonlar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelButonlar.setBackground(Color.WHITE);
        
        btnKayitOl = createButton("Kayıt Ol", new Color(46, 204, 113));
        btnGeriDon = createButton("Geri Dön", new Color(231, 76, 60));
        
        panelButonlar.add(btnKayitOl);
        panelButonlar.add(btnGeriDon);
        
        gbcPanel = createGbc(0, row);
        gbcPanel.insets = new Insets(20, 5, 5, 5);
        panelKayit.add(panelButonlar, gbcPanel);
        
        // Ana paneli ekle
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(panelKayit, gbc);
        
        // Event listeners
        btnKayitOl.addActionListener(e -> kayitOl());
        btnGeriDon.addActionListener(e -> geriDon());
        
        // Hover efektleri
        addHoverEffect(btnKayitOl, new Color(46, 204, 113), new Color(39, 174, 96));
        addHoverEffect(btnGeriDon, new Color(231, 76, 60), new Color(192, 57, 43));
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }
    
    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }
    
    private void kayitOl() {
        // Form validasyonu
        if (!validateForm()) {
            return;
        }
        
        String kullaniciAdi = txtKullaniciAdi.getText().trim();
        String sifre = new String(txtSifre.getPassword());
        String ad = txtAd.getText().trim();
        String soyad = txtSoyad.getText().trim();
        String email = txtEmail.getText().trim();
        String telefon = txtTelefon.getText().trim();
        
        if (kullaniciController.kayitOl(kullaniciAdi, sifre, ad, soyad, email, telefon)) {
            geriDon();
        }
    }
    
    private boolean validateForm() {
        // Boş alan kontrolü
        if (txtKullaniciAdi.getText().trim().isEmpty() ||
            txtSifre.getPassword().length == 0 ||
            txtSifreTekrar.getPassword().length == 0 ||
            txtAd.getText().trim().isEmpty() ||
            txtSoyad.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtTelefon.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Lütfen tüm alanları doldurun!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Şifre kontrolü
        String sifre = new String(txtSifre.getPassword());
        String sifreTekrar = new String(txtSifreTekrar.getPassword());
        
        if (!sifre.equals(sifreTekrar)) {
            JOptionPane.showMessageDialog(this, 
                "Şifreler eşleşmiyor!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (sifre.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Şifre en az 6 karakter olmalıdır!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Email formatı kontrolü
        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, 
                "Geçerli bir email adresi girin!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Telefon formatı kontrolü
        String telefon = txtTelefon.getText().trim();
        if (!telefon.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, 
                "Telefon numarası 10 veya 11 haneli olmalıdır!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void geriDon() {
        new GirisEkrani().setVisible(true);
        this.dispose();
    }
}