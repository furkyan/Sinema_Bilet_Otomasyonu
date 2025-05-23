package com.sinema.main;

import com.sinema.ui.GirisEkrani;
import com.sinema.util.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class SinemaOtomasyonu {
    
    public static void main(String[] args) {
        // Look and Feel ayarla
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Splash screen göster
        showSplashScreen();
        
        // Veritabanı bağlantısını kontrol et
        if (!checkDatabaseConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Veritabanı bağlantısı kurulamadı!\nProgram kapatılacak.", 
                "Hata", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Ana uygulamayı başlat
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GirisEkrani().setVisible(true);
            }
        });
    }
    
 // SinemaOtomasyonu.java'daki showSplashScreen metodu düzeltilmiş

    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(44, 62, 80));
        content.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        
        // Ana içerik paneli
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(44, 62, 80));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Logo/Başlık paneli
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(44, 62, 80));
        
        // Ana başlık
        JLabel lblLogo = new JLabel("SİNEMA BİLET OTOMASYON SİSTEMİ");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Alt başlık
        JLabel lblAltBaslik = new JLabel("Modern Sinema Yönetim Çözümü");
        lblAltBaslik.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAltBaslik.setForeground(new Color(189, 195, 199));
        lblAltBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        
        logoPanel.add(lblLogo, BorderLayout.CENTER);
        logoPanel.add(lblAltBaslik, BorderLayout.SOUTH);
        
        // Yükleniyor paneli
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(new Color(44, 62, 80));
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        JLabel lblYukleniyor = new JLabel("Sistem başlatılıyor...");
        lblYukleniyor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblYukleniyor.setForeground(new Color(189, 195, 199));
        lblYukleniyor.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(400, 8));
        progressBar.setBackground(new Color(52, 73, 94));
        progressBar.setForeground(new Color(46, 204, 113));
        
        JPanel progressPanel = new JPanel(new FlowLayout());
        progressPanel.setBackground(new Color(44, 62, 80));
        progressPanel.add(progressBar);
        
        loadingPanel.add(lblYukleniyor, BorderLayout.NORTH);
        loadingPanel.add(progressPanel, BorderLayout.CENTER);
        
        // Version bilgisi
        JLabel lblVersion = new JLabel("Versiyon 1.0.0 | © 2025");
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(127, 140, 141));
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
        
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(loadingPanel, BorderLayout.CENTER);
        mainPanel.add(lblVersion, BorderLayout.SOUTH);
        
        content.add(mainPanel, BorderLayout.CENTER);
        
        splash.setContentPane(content);
        splash.setSize(600, 350);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        
        // Animasyonlu yükleme
        Timer timer = new Timer(100, null);
        final int[] progress = {0};
        
        timer.addActionListener(e -> {
            progress[0] += 5;
            if (progress[0] <= 50) {
                lblYukleniyor.setText("Veritabanı bağlantısı kontrol ediliyor...");
            } else if (progress[0] <= 80) {
                lblYukleniyor.setText("Sistem modülleri yükleniyor...");
            } else {
                lblYukleniyor.setText("Başlatma tamamlanıyor...");
            }
            
            if (progress[0] >= 100) {
                timer.stop();
                splash.dispose();
            }
        });
        
        timer.start();
        
        // 3 saniye bekle
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        timer.stop();
        splash.dispose();
    }
    
    private static boolean checkDatabaseConnection() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Veritabanı bağlantısı başarılı!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}