// DBConnection.java - Debug ve test metodları eklenmiş versiyon
package com.sinema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // MS SQL Server bağlantı bilgileri
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=sinema_otomasyonu;encrypt=false;trustServerCertificate=true";
    private static final String USERNAME = "hey"; // MS SQL Server kullanıcı adınız
    private static final String PASSWORD = "2022b"; // MS SQL Server şifreniz
    
    private static Connection connection = null;
    
    // Private constructor - dışarıdan nesne oluşturulmasını engeller (Singleton pattern)
    private DBConnection() {}
    
    // Tek bir bağlantı örneği döndüren statik metod
    public static Connection getConnection() {
        if (connection == null) {
            try {
                System.out.println("Veritabanı bağlantısı kuruluyor...");
                System.out.println("URL: " + JDBC_URL);
                System.out.println("Kullanıcı: " + USERNAME);
                
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                
                System.out.println("Veritabanı bağlantısı başarılı!");
                
                // Bağlantıyı test et
                testConnection();
                
            } catch (ClassNotFoundException e) {
                System.err.println("SQL Server JDBC sürücüsü bulunamadı: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Veritabanına bağlanırken hata oluştu: " + e.getMessage());
                System.err.println("Error Code: " + e.getErrorCode());
                System.err.println("SQL State: " + e.getSQLState());
                e.printStackTrace();
            }
        } else {
            // Bağlantının hala aktif olup olmadığını kontrol et
            try {
                if (connection.isClosed()) {
                    System.out.println("Bağlantı kapalı, yeniden kuruluyor...");
                    connection = null;
                    return getConnection(); // Recursive call
                }
            } catch (SQLException e) {
                System.err.println("Bağlantı kontrolü sırasında hata: " + e.getMessage());
                connection = null;
                return getConnection(); // Recursive call
            }
        }
        return connection;
    }
    
    // Bağlantıyı test et
    private static void testConnection() {
        try (Statement stmt = connection.createStatement()) {
            // Basit bir test sorgusu
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as table_count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");
            
            if (rs.next()) {
                int tableCount = rs.getInt("table_count");
                System.out.println("Veritabanında " + tableCount + " tablo bulundu.");
                
                // Film sayısını kontrol et
                ResultSet filmRs = stmt.executeQuery("SELECT COUNT(*) as film_count FROM filmler");
                if (filmRs.next()) {
                    int filmCount = filmRs.getInt("film_count");
                    System.out.println("Veritabanında " + filmCount + " film bulundu.");
                }
                
                // Seans sayısını kontrol et
                ResultSet seansRs = stmt.executeQuery("SELECT COUNT(*) as seans_count FROM seanslar WHERE tarih >= CAST(GETDATE() AS DATE)");
                if (seansRs.next()) {
                    int seansCount = seansRs.getInt("seans_count");
                    System.out.println("Bugün ve sonrası için " + seansCount + " seans bulundu.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı test sorgusu sırasında hata: " + e.getMessage());
        }
    }
    
    // Veritabanı bağlantısını kapatma
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            } catch (SQLException e) {
                System.err.println("Veritabanı bağlantısı kapatılırken hata: " + e.getMessage());
            }
        }
    }
    
    // Bağlantı durumunu kontrol et
    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    // Debug: Veritabanı bilgilerini yazdır
    public static void printDatabaseInfo() {
        Connection conn = getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                System.out.println("=== VERİTABANI BİLGİLERİ ===");
                
                // Tablo sayıları
                String[] tables = {"kullanicilar", "filmler", "salonlar", "seanslar", "biletler"};
                for (String table : tables) {
                    try {
                        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM " + table);
                        if (rs.next()) {
                            System.out.println(table + ": " + rs.getInt("count") + " kayıt");
                        }
                    } catch (SQLException e) {
                        System.out.println(table + ": Tablo bulunamadı veya erişim hatası");
                    }
                }
                
                System.out.println("========================");
                
            } catch (SQLException e) {
                System.err.println("Database info yazdırılırken hata: " + e.getMessage());
            }
        }
    }
}