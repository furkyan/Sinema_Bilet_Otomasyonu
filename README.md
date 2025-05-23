# SİNEMA BİLET OTOMASYON SİSTEMİ


Modern sinema işletmeleri için geliştirilmiş kapsamlı bilet yönetim sistemi.

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Teknolojiler](#-teknolojiler)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [Veritabanı Kurulumu](#-veritabanı-kurulumu)
- [Proje Yapısı](#-proje-yapısı)
- [Ekran Görüntüleri](#-ekran-görüntüleri)
- [API Dokümantasyonu](#-api-dokümantasyonu)
- [Katkıda Bulunma](#-katkıda-bulunma)

## 🚀 Özellikler

### Müşteri Özellikleri
- 🎬 **Film Gösterimi**: Vizyondaki filmleri görüntüleme ve detaylarını inceleme
- 🎭 **Seans Seçimi**: Tarih, saat ve salon bazında seans seçimi
- 💺 **Koltuk Rezervasyonu**: İnteraktif koltuk haritası ile rezervasyon
- 🎫 **Bilet Yönetimi**: Satın alınan biletleri görüntüleme ve iptal etme
- 👤 **Profil Yönetimi**: Kişisel bilgileri güncelleme ve şifre değiştirme

### Yönetici Özellikleri
- 🎥 **Film Yönetimi**: Film ekleme, düzenleme, silme ve afiş yükleme
- 🏢 **Salon Yönetimi**: Salon kapasitesi ve özellikleri yönetimi
- ⏰ **Seans Programlama**: Film-salon-zaman eşleştirme ve fiyat belirleme
- 👥 **Kullanıcı Yönetimi**: Kullanıcı rollerini yönetme ve hesap kontrolü
- 📊 **Raporlama**: Detaylı satış raporları ve istatistikler

### Sistem Özellikleri
- 🔐 **Güvenlik**: Rol tabanlı erişim kontrolü ve şifreli oturum yönetimi
- 🎨 **Kullanıcı Arayüzü**: Modern ve responsive Swing tabanlı GUI
- 💾 **Veri Yönetimi**: SQL Server ile güvenilir veri saklama
- 🚄 **Performans**: Optimize edilmiş veritabanı sorguları ve bağlantı havuzu
- 🛡️ **Hata Yönetimi**: Kapsamlı exception handling ve logging

## 🛠 Teknolojiler

- **Backend**: Java 8+, JDBC
- **Frontend**: Java Swing/AWT  
- **Veritabanı**: Microsoft SQL Server 2019
- **Mimari**: MVC (Model-View-Controller)
- **Design Patterns**: Singleton, Factory, Observer
- **Build Tool**: Maven/Gradle (opsiyonel)
- **IDE**: IntelliJ IDEA / Eclipse

## 📦 Kurulum

### Ön Gereksinimler

1. **Java Development Kit (JDK) 8 veya üzeri**
   ```bash
   java -version
   javac -version
   ```

2. **Microsoft SQL Server 2019 veya üzeri**
   - SQL Server Management Studio (SSMS) önerilen

3. **SQL Server JDBC Driver**
   - Otomatik olarak proje ile birlikte gelir

### Adım Adım Kurulum

1. **Projeyi İndirin**
   ```bash
   git clone https://github.com/furkanefekilic/sinema-bilet-otomasyonu.git
   cd sinema-bilet-otomasyonu
   ```

2. **Veritabanını Kurun**
   ```sql
   -- SQL Server Management Studio'da çalıştırın
   CREATE DATABASE sinema_otomasyonu;
   ```

3. **Tablo Yapılarını Oluşturun**
   ```bash
   # database-scripts/create-tables.sql dosyasını çalıştırın
   sqlcmd -S localhost -d sinema_otomasyonu -i database-scripts/create-tables.sql
   ```

4. **Örnek Verileri Yükleyin** (Opsiyonel)
   ```bash
   sqlcmd -S localhost -d sinema_otomasyonu -i database-scripts/sample-data.sql
   ```

5. **Veritabanı Bağlantı Ayarları**
   
   `src/com/sinema/util/DBConnection.java` dosyasında bağlantı bilgilerinizi güncelleyin:
   ```java
   private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=sinema_otomasyonu;encrypt=false;trustServerCertificate=true";
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

6. **Projeyi Derleyin ve Çalıştırın**
   ```bash
   # IDE kullanıyorsanız
   # src/com/sinema/main/SinemaOtomasyonu.java dosyasını çalıştırın
   
   # Komut satırından
   javac -cp ".:lib/*" src/com/sinema/main/SinemaOtomasyonu.java
   java -cp ".:lib/*:src" com.sinema.main.SinemaOtomasyonu
   ```

## 🎯 Kullanım

### İlk Çalıştırma

1. **Yönetici Hesabı Oluşturun**
   - Sistem ilk çalıştırıldığında veritabanına bir yönetici hesabı ekleyin:
   ```sql
   INSERT INTO kullanicilar (kullanici_adi, sifre, ad, soyad, email, telefon, rol)
   VALUES ('admin', 'admin123', 'Admin', 'User', 'admin@sinema.com', '5555555555', 'Yönetici');
   ```

2. **Giriş Yapın**
   - Kullanıcı Adı: `admin`
   - Şifre: `admin123`

### Temel İşlemler

#### Müşteri Olarak:
1. Kayıt ol veya giriş yap
2. Vizyondaki filmleri incele
3. Film seç ve seans belirle
4. Koltuk seçimi yap
5. Bilet satın al

#### Yönetici Olarak:
1. Yönetici hesabıyla giriş yap
2. Film, salon ve seans bilgilerini yönet
3. Satış raporlarını incele
4. Kullanıcı hesaplarını yönet

## 🗄 Veritabanı Kurulumu

### Tablo Yapıları

```sql
-- Kullanıcılar tablosu
CREATE TABLE kullanicilar (
    id INT IDENTITY(1,1) PRIMARY KEY,
    kullanici_adi NVARCHAR(50) UNIQUE NOT NULL,
    sifre NVARCHAR(255) NOT NULL,
    ad NVARCHAR(50) NOT NULL,
    soyad NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    telefon NVARCHAR(15),
    rol NVARCHAR(20) DEFAULT 'Müşteri'
);

-- Filmler tablosu
CREATE TABLE filmler (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ad NVARCHAR(200) NOT NULL,
    tur NVARCHAR(50) NOT NULL,
    sure INT NOT NULL CHECK (sure > 0),
    yonetmen NVARCHAR(100) NOT NULL,
    oyuncular NTEXT,
    aciklama NTEXT,
    afis_yolu NVARCHAR(500),
    vizyon_tarihi DATE,
    imdb_puani NVARCHAR(10),
    fragman_url NVARCHAR(500)
);

-- Salonlar tablosu
CREATE TABLE salonlar (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ad NVARCHAR(50) NOT NULL,
    kapasite INT NOT NULL CHECK (kapasite > 0)
);

-- Seanslar tablosu
CREATE TABLE seanslar (
    id INT IDENTITY(1,1) PRIMARY KEY,
    film_id INT FOREIGN KEY REFERENCES filmler(id),
    salon_id INT FOREIGN KEY REFERENCES salonlar(id),
    tarih DATE NOT NULL,
    saat TIME NOT NULL,
    fiyat DECIMAL(8,2) NOT NULL CHECK (fiyat > 0)
);

-- Biletler tablosu
CREATE TABLE biletler (
    id INT IDENTITY(1,1) PRIMARY KEY,
    kullanici_id INT FOREIGN KEY REFERENCES kullanicilar(id),
    seans_id INT FOREIGN KEY REFERENCES seanslar(id),
    koltuk_no NVARCHAR(10) NOT NULL,
    fiyat DECIMAL(8,2) NOT NULL,
    satis_tarihi DATETIME DEFAULT GETDATE(),
    durum NVARCHAR(20) DEFAULT 'Aktif'
);
```

### Örnek Veri

```sql
-- Örnek salon verisi
INSERT INTO salonlar (ad, kapasite) VALUES 
('Salon 1', 100),
('Salon 2', 80),
('VIP Salon', 50);

-- Örnek film verisi
INSERT INTO filmler (ad, tur, sure, yonetmen, oyuncular) VALUES 
('Inception', 'Bilim Kurgu', 148, 'Christopher Nolan', 'Leonardo DiCaprio, Marion Cotillard'),
('The Dark Knight', 'Aksiyon', 152, 'Christopher Nolan', 'Christian Bale, Heath Ledger'),
('Interstellar', 'Bilim Kurgu', 169, 'Christopher Nolan', 'Matthew McConaughey, Anne Hathaway');
```

## 📁 Proje Yapısı

```
sinema-bilet-otomasyonu/
├── src/
│   └── com/
│       └── sinema/
│           ├── main/
│           │   └── SinemaOtomasyonu.java
│           ├── ui/
│           │   ├── GirisEkrani.java
│           │   ├── KayitEkrani.java
│           │   ├── MusteriPaneli.java
│           │   ├── YoneticiPaneli.java
│           │   ├── KoltukSecimEkrani.java
│           │   └── DetayliRaporEkrani.java
│           ├── controller/
│           │   ├── KullaniciController.java
│           │   ├── FilmController.java
│           │   ├── SeansController.java
│           │   ├── BiletController.java
│           │   └── SalonController.java
│           ├── service/
│           │   ├── KullaniciService.java
│           │   ├── FilmService.java
│           │   ├── SeansService.java
│           │   ├── BiletService.java
│           │   └── SalonService.java
│           ├── dao/
│           │   ├── KullaniciDAO.java
│           │   ├── FilmDAO.java
│           │   ├── SeansDAO.java
│           │   ├── BiletDAO.java
│           │   ├── SalonDAO.java
│           │   └── VeriErisim.java
│           ├── model/
│           │   ├── Kullanici.java
│           │   ├── Film.java
│           │   ├── Seans.java
│           │   ├── Bilet.java
│           │   ├── Salon.java
│           │   ├── Koltuk.java
│           │   ├── Kisi.java
│           │   ├── Musteri.java
│           │   ├── Yonetici.java
│           │   ├── BiletTipi.java
│           │   └── KoltukTipi.java
│           ├── util/
│           │   └── DBConnection.java
│           └── exception/
│               ├── BiletBulunamadiException.java
│               ├── GirisHatasiException.java
│               ├── KoltukDoluException.java
│               ├── VeritabaniException.java
│               └── YetkisizErisimException.java
├── lib/
│   └── mssql-jdbc-x.x.x.jre8.jar
├── database-scripts/
│   ├── create-tables.sql
│   └── sample-data.sql
├── docs/
│   ├── user-manual.pdf
│   └── api-documentation.html
├── README.md
└── LICENSE
```

## 📸 Ekran Görüntüleri

### Giriş Ekranı

<img width="266" alt="giriş" src="https://github.com/user-attachments/assets/a7ab2e59-24a8-4c6c-b645-74d01c0b2461" />

### Film Seçimi
<img width="1280" alt="m_film" src="https://github.com/user-attachments/assets/53cfa78d-d202-4fb7-843f-1ee507853efb" />

### Koltuk Seçim Ekranı

<img width="1280" alt="m_koltuk_onay" src="https://github.com/user-attachments/assets/ba8ee43c-e4e2-4c5a-8e09-5c69ef4c132c" />

### Yönetici Paneli

<img width="1279" alt="yönetici_film" src="https://github.com/user-attachments/assets/df575066-57a8-465e-b278-8db24a3e01f4" />

### Raporlar

<img width="1276" alt="y_rapor" src="https://github.com/user-attachments/assets/7b19cd39-257b-4b87-802c-db4dc6ee650d" />

## 📚 API Dokümantasyonu

### Controller Sınıfları

#### KullaniciController
```java
// Kullanıcı girişi
public boolean girisYap(String kullaniciAdi, String sifre)

// Kullanıcı kaydı
public boolean kayitOl(String kullaniciAdi, String sifre, String ad, String soyad, String email, String telefon)

// Profil güncelleme
public boolean profilGuncelle(String ad, String soyad, String email, String telefon)
```

#### FilmController
```java
// Film ekleme
public boolean filmEkle(String ad, String tur, int sure, String yonetmen, String oyuncular)

// Tüm filmleri getir
public List<Film> tumFilmleriGetir()

// Vizyondaki filmler
public List<Film> vizyondakiFilmleriGetir()
```

#### BiletController
```java
// Bilet satın alma
public boolean biletSatinAl(int kullaniciId, int seansId, String koltukNo, double fiyat)

// Kullanıcı biletleri
public List<Bilet> kullaniciBiletleriGetir(int kullaniciId)

// Dolu koltuklar
public List<String> seansDoluKoltuklarGetir(int seansId)
```

### Exception Handling

```java
try {
    // Veritabanı işlemi
} catch (VeritabaniException e) {
    logger.error("Veritabanı hatası: " + e.getMessage());
} catch (KoltukDoluException e) {
    JOptionPane.showMessageDialog(null, "Seçilen koltuk dolu!");
} catch (BiletBulunamadiException e) {
    JOptionPane.showMessageDialog(null, "Bilet bulunamadı!");
}
```

## 🔧 Yapılandırma

### Veritabanı Ayarları
```java
// DBConnection.java
private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=sinema_otomasyonu";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Logging Ayarları
```java
// Logger konfigürasyonu
private static final Logger logger = Logger.getLogger(ClassName.class.getName());
```

## 🧪 Test Etme

### Manuel Test Senaryoları

1. **Kullanıcı Kaydı Testi**
   - Geçerli bilgilerle kayıt
   - Eksik bilgilerle kayıt (hata kontrolü)
   - Mevcut email ile kayıt (hata kontrolü)

2. **Bilet Satın Alma Testi**
   - Normal bilet satın alma
   - Dolu koltuk seçimi (hata kontrolü)
   - Birden fazla bilet alma

3. **Yönetici Fonksiyonları Testi**
   - Film ekleme/düzenleme/silme  
   - Seans programlama
   - Rapor görüntüleme

### Unit Test Örnekleri
```java
@Test
public void testKullaniciGirisi() {
    KullaniciController controller = new KullaniciController();
    boolean sonuc = controller.girisYap("test_user", "test_pass");
    assertTrue(sonuc);
}

@Test
public void testBiletSatinAlma() {
    BiletController controller = new BiletController();
    boolean sonuc = controller.biletSatinAl(1, 1, "A1", 45.0);
    assertTrue(sonuc);
}
```

## 🚨 Sorun Giderme

### Yaygın Sorunlar ve Çözümleri

1. **Veritabanı Bağlantı Hatası**
   ```
   Hata: "Login failed for user"
   Çözüm: DBConnection.java'da kullanıcı adı/şifre kontrol edin
   ```

2. **JDBC Driver Bulunamadı**
   ```
   Hata: "ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver"
   Çözüm: SQL Server JDBC driver'ı classpath'e ekleyin
   ```

3. **Port Bağlantı Sorunu**
   ```
   Hata: "Connection refused"
   Çözüm: SQL Server'ın 1433 portunda çalıştığını kontrol edin
   ```

### Log Dosyaları
- Uygulama logları: `logs/application.log`
- Hata logları: `logs/error.log`
- Veritabanı logları: SQL Server log dosyaları

## 🤝 Katkıda Bulunma

### Geliştirme Katkısı

1. Bu repository'yi fork edin
2. Feature branch oluşturun (`git checkout -b feature/YeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik: Açıklama'`)
4. Branch'inizi push edin (`git push origin feature/YeniOzellik`)
5. Pull Request oluşturun

### Kod Standartları

- Java naming conventions kullanın
- Javadoc ile kod dokümantasyonu yapın
- Exception handling ekleyin
- Unit testler yazın

### Bug Raporlama

Bug bulduğunuzda lütfen aşağıdaki bilgileri ekleyin:
- İşletim sistemi ve Java versiyonu
- Hata mesajının tam metni
- Hatayı reproduce etme adımları
- Ekran görüntüsü (varsa)


## 👤 Geliştirici

**Furkan Efe KILIÇ**
- Email: furkanefe0066@gmail.com


## 📚 Ek Kaynaklar

- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [SQL Server Documentation](https://docs.microsoft.com/en-us/sql/sql-server/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Design Patterns in Java](https://java-design-patterns.com/)

---

⭐ Bu projeyi beğendiyseniz lütfen star verin!

📧 Sorularınız için issue açabilir veya email gönderebilirsiniz.
