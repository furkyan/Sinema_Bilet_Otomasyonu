// Dosya: com/sinema/ui/SeansSecimEkrani.java - Hata düzeltmeleri
package com.sinema.ui;

import com.sinema.controller.FilmController;
import com.sinema.controller.SeansController;
import com.sinema.controller.BiletController; // Eksik import eklendi
import com.sinema.model.Film;
import com.sinema.model.Seans;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class SeansSecimEkrani extends JFrame {
    private JComboBox<Film> cmbFilmler;
    private JPanel panelSeanslar;
    private JButton btnDevam;
    private JButton btnGeri;
    private JLabel lblFilmDetay;
    
    private FilmController filmController;
    private SeansController seansController;
    private BiletController biletController; // Eksik alan eklendi
    private Seans seciliSeans;
    
    public SeansSecimEkrani() {
        filmController = new FilmController();
        seansController = new SeansController();
        biletController = new BiletController(); // Eksik başlatma eklendi
        initComponents();
        loadFilmler();
    }
    
    private void initComponents() {
        setTitle("Film ve Seans Seçimi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Üst Panel - Film Seçimi
        JPanel panelUst = new JPanel(new BorderLayout());
        panelUst.setBackground(Color.WHITE);
        panelUst.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelFilmSecim = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFilmSecim.setBackground(Color.WHITE);
        
        JLabel lblFilm = new JLabel("Film Seçin:");
        lblFilm.setFont(new Font("Arial", Font.BOLD, 14));
        panelFilmSecim.add(lblFilm);
        
        cmbFilmler = new JComboBox<>();
        cmbFilmler.setPreferredSize(new Dimension(300, 30));
        cmbFilmler.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbFilmler.setRenderer(new FilmComboBoxRenderer()); // Renderer eklendi
        panelFilmSecim.add(cmbFilmler);
        
        JButton btnFilmDetay = new JButton("Film Detayları");
        btnFilmDetay.setBackground(new Color(52, 152, 219));
        btnFilmDetay.setForeground(Color.WHITE);
        btnFilmDetay.setFocusPainted(false);
        panelFilmSecim.add(btnFilmDetay);
        
        panelUst.add(panelFilmSecim, BorderLayout.NORTH);
        
        // Film detay etiketi
        lblFilmDetay = new JLabel();
        lblFilmDetay.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFilmDetay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUst.add(lblFilmDetay, BorderLayout.CENTER);
        
        // Orta Panel - Seanslar
        panelSeanslar = new JPanel();
        panelSeanslar.setLayout(new BoxLayout(panelSeanslar, BoxLayout.Y_AXIS));
        panelSeanslar.setBackground(new Color(240, 240, 240));
        panelSeanslar.setBorder(BorderFactory.createTitledBorder("Seanslar"));
        
        JScrollPane scrollPane = new JScrollPane(panelSeanslar);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        // Alt Panel - Butonlar
        JPanel panelAlt = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAlt.setBackground(Color.WHITE);
        panelAlt.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        btnGeri = new JButton("Geri");
        btnGeri.setBackground(new Color(149, 165, 166));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFont(new Font("Arial", Font.BOLD, 14));
        btnGeri.setPreferredSize(new Dimension(100, 35));
        btnGeri.setFocusPainted(false);
        
        btnDevam = new JButton("Devam");
        btnDevam.setBackground(new Color(46, 204, 113));
        btnDevam.setForeground(Color.WHITE);
        btnDevam.setFont(new Font("Arial", Font.BOLD, 14));
        btnDevam.setPreferredSize(new Dimension(100, 35));
        btnDevam.setFocusPainted(false);
        btnDevam.setEnabled(false);
        
        panelAlt.add(btnGeri);
        panelAlt.add(btnDevam);
        
        // Ana layout
        add(panelUst, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelAlt, BorderLayout.SOUTH);
        
        // Event listeners
        cmbFilmler.addActionListener(e -> {
            Film seciliFilm = (Film) cmbFilmler.getSelectedItem();
            if (seciliFilm != null) {
                loadSeanslar(seciliFilm.getId());
                showFilmDetay(seciliFilm);
            }
        });
        
        btnFilmDetay.addActionListener(e -> showFilmDetayDialog());
        btnGeri.addActionListener(e -> dispose());
        btnDevam.addActionListener(e -> koltukSecimineGec());
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void loadFilmler() {
        List<Film> filmler = filmController.vizyondakiFilmleriGetir();
        if (filmler != null) {
            for (Film film : filmler) {
                cmbFilmler.addItem(film);
            }
        }
    }
    
    private void loadSeanslar(int filmId) {
        panelSeanslar.removeAll();
        seciliSeans = null;
        btnDevam.setEnabled(false);
        
        List<Seans> seanslar = seansController.filminSeanslariniGetir(filmId);
        
        if (seanslar == null || seanslar.isEmpty()) {
            JLabel lblBosMsg = new JLabel("Bu film için seans bulunmamaktadır.");
            lblBosMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelSeanslar.add(lblBosMsg);
        } else {
            ButtonGroup group = new ButtonGroup();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            for (Seans seans : seanslar) {
                JRadioButton rbSeans = new JRadioButton();
                rbSeans.setText(String.format("%s - %s | Salon: %s | Fiyat: %.2f TL",
                    dateFormat.format(seans.getTarih()),
                    timeFormat.format(seans.getSaat()),
                    "Salon " + seans.getSalonId(),
                    seans.getFiyat()
                ));
                rbSeans.setFont(new Font("Arial", Font.PLAIN, 14));
                rbSeans.setBackground(Color.WHITE);
                rbSeans.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                rbSeans.setActionCommand(String.valueOf(seans.getId()));
                
                rbSeans.addActionListener(e -> {
                    seciliSeans = seans;
                    btnDevam.setEnabled(true);
                });
                
                group.add(rbSeans);
                
                JPanel seansPanel = new JPanel(new BorderLayout());
                seansPanel.setBackground(Color.WHITE);
                seansPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                seansPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                seansPanel.add(rbSeans, BorderLayout.CENTER);
                
                // Doluluk oranı
                double dolulukOrani = biletController.seansDolulukOraniGetir(seans.getId());
                JLabel lblDoluluk = new JLabel(String.format("Doluluk: %%%.0f", dolulukOrani));
                lblDoluluk.setFont(new Font("Arial", Font.PLAIN, 12));
                lblDoluluk.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
                
                if (dolulukOrani > 80) {
                    lblDoluluk.setForeground(Color.RED);
                } else if (dolulukOrani > 50) {
                    lblDoluluk.setForeground(Color.ORANGE);
                } else {
                    lblDoluluk.setForeground(Color.GREEN);
                }
                
                seansPanel.add(lblDoluluk, BorderLayout.EAST);
                panelSeanslar.add(seansPanel);
                panelSeanslar.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        panelSeanslar.revalidate();
        panelSeanslar.repaint();
    }
    
    private void showFilmDetay(Film film) {
        // Film sınıfında getOyuncular() metodu yok, sadece temel bilgileri gösterelim
        lblFilmDetay.setText(String.format(
            "<html><b>%s</b><br>Tür: %s | Süre: %d dk<br>Yönetmen: %s</html>",
            film.getAd(), film.getTur(), film.getSure(), film.getYonetmen()
        ));
    }
    
    private void showFilmDetayDialog() {
        Film seciliFilm = (Film) cmbFilmler.getSelectedItem();
        if (seciliFilm == null) {
            JOptionPane.showMessageDialog(this, "Lütfen bir film seçin!");
            return;
        }
        
        String detay = String.format(
            "Film: %s\n\n" +
            "Tür: %s\n" +
            "Süre: %d dakika\n" +
            "Yönetmen: %s",
            seciliFilm.getAd(),
            seciliFilm.getTur(),
            seciliFilm.getSure(),
            seciliFilm.getYonetmen()
        );
        
        JOptionPane.showMessageDialog(this, detay, "Film Detayları", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void koltukSecimineGec() {
        if (seciliSeans == null) {
            JOptionPane.showMessageDialog(this, "Lütfen bir seans seçin!");
            return;
        }
        
        new KoltukSecimEkrani(seciliSeans).setVisible(true);
        this.dispose();
    }
    
    // Film nesnesi için ComboBox renderer
    private static class FilmComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Film) {
                Film film = (Film) value;
                setText(film.getAd());
            }
            
            return this;
        }
    }
}