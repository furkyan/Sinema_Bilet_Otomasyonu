package com.sinema.ui;

import com.sinema.controller.KullaniciController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GirisEkrani extends JFrame {
    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;
    private JButton btnKayitOl;
    private KullaniciController kullaniciController;
    
    public GirisEkrani() {
        kullaniciController = new KullaniciController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sinema Bilet Otomasyonu - Giriş");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Logo veya başlık
        JLabel lblBaslik = new JLabel("SİNEMA BİLET OTOMASYONU");
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
        lblBaslik.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblBaslik, gbc);
        
        // Alt başlık
        JLabel lblAltBaslik = new JLabel("Hoş Geldiniz");
        lblAltBaslik.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAltBaslik.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        add(lblAltBaslik, gbc);
        
        // Panel için
        JPanel panelGiris = new JPanel(new GridBagLayout());
        panelGiris.setBackground(Color.WHITE);
        panelGiris.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(5, 5, 5, 5);
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        
        // Kullanıcı adı
        JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı:");
        lblKullaniciAdi.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.anchor = GridBagConstraints.WEST;
        panelGiris.add(lblKullaniciAdi, gbcPanel);
        
        txtKullaniciAdi = new JTextField(20);
        txtKullaniciAdi.setFont(new Font("Arial", Font.PLAIN, 14));
        txtKullaniciAdi.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbcPanel.gridy = 1;
        panelGiris.add(txtKullaniciAdi, gbcPanel);
        
        // Şifre
        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcPanel.gridy = 2;
        gbcPanel.insets = new Insets(15, 5, 5, 5);
        panelGiris.add(lblSifre, gbcPanel);
        
        txtSifre = new JPasswordField(20);
        txtSifre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSifre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbcPanel.gridy = 3;
        gbcPanel.insets = new Insets(5, 5, 5, 5);
        panelGiris.add(txtSifre, gbcPanel);
        
        // Butonlar paneli
        JPanel panelButonlar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelButonlar.setBackground(Color.WHITE);
        
        btnGiris = new JButton("Giriş Yap");
        btnGiris.setFont(new Font("Arial", Font.BOLD, 14));
        btnGiris.setBackground(new Color(52, 152, 219));
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setFocusPainted(false);
        btnGiris.setBorderPainted(false);
        btnGiris.setPreferredSize(new Dimension(120, 35));
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnKayitOl = new JButton("Kayıt Ol");
        btnKayitOl.setFont(new Font("Arial", Font.BOLD, 14));
        btnKayitOl.setBackground(new Color(46, 204, 113));
        btnKayitOl.setForeground(Color.WHITE);
        btnKayitOl.setFocusPainted(false);
        btnKayitOl.setBorderPainted(false);
        btnKayitOl.setPreferredSize(new Dimension(120, 35));
        btnKayitOl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelButonlar.add(btnGiris);
        panelButonlar.add(btnKayitOl);
        
        gbcPanel.gridy = 4;
        gbcPanel.insets = new Insets(20, 5, 5, 5);
        panelGiris.add(panelButonlar, gbcPanel);
        
        // Ana paneli ekle
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(panelGiris, gbc);
        
        // Event listeners
        btnGiris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                girisYap();
            }
        });
        
        btnKayitOl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kayitOlEkraniAc();
            }
        });
        
        // Enter tuşu ile giriş
        txtSifre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    girisYap();
                }
            }
        });
        
        // Hover efektleri
        btnGiris.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGiris.setBackground(new Color(41, 128, 185));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnGiris.setBackground(new Color(52, 152, 219));
            }
        });
        
        btnKayitOl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnKayitOl.setBackground(new Color(39, 174, 96));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnKayitOl.setBackground(new Color(46, 204, 113));
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void girisYap() {
        String kullaniciAdi = txtKullaniciAdi.getText().trim();
        String sifre = new String(txtSifre.getPassword());
        
        if (kullaniciAdi.isEmpty() || sifre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Kullanıcı adı ve şifre boş olamaz!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (kullaniciController.girisYap(kullaniciAdi, sifre)) {
            // Admin mi kontrol et
            if (KullaniciController.adminMi()) {
                new YoneticiPaneli().setVisible(true);
            } else {
                new MusteriPaneli().setVisible(true);
            }
            this.dispose();
        }
    }
    
    private void kayitOlEkraniAc() {
        new KayitEkrani().setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GirisEkrani().setVisible(true);
            }
        });
    }
}