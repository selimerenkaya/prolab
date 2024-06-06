import sqlite3
import random
import faker

from werkzeug.security import generate_password_hash
import app
import siniflar


turkish_cities = [
        "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya", "Ankara",
        "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman",
        "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa",
        "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne",
        "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane",
        "Hakkâri", "Hatay", "Iğdır", "Isparta", "İstanbul", "İzmir", "Kahramanmaraş",
        "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kırıkkale", "Kırklareli",
        "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Mardin",
        "Mersin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize",
        "Sakarya", "Samsun", "Şanlıurfa", "Siirt", "Sinop", "Sivas", "Şırnak", "Tekirdağ",
        "Tokat", "Trabzon", "Tunceli", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"
    ]

mails = ["@gmail.com", "@outlook.com", "@yahoo.com", "@hotmail.com", "@mail.ru", "@yandex.com",
         "@msn.com", "@live.com"]

sigortalar = ["Allianz", "Anadolu", "Doga", "Ak", "Axa", "Güneş", "Diger"]

sube_isimleri = [
        "Ankara Şubesi",
        "İstanbul Şubesi",
        "İzmir Şubesi",
        "Antalya Şubesi",
        "Konya Şubesi",
        "Kocaeli Şubesi",
        "Eskişehir Şubesi"
    ]

uzmanlik_alanlari = [
        "Acil Tıp", "Aile Hekimliği", "Anesteziyoloji ve Reanimasyon",
        "Beyin ve Sinir Cerrahisi", "Çocuk Cerrahisi", "Çocuk Sağlığı ve Hastalıkları",
        "Dermatoloji (Deri ve Zührevi Hastalıklar)", "Fiziksel Tıp ve Rehabilitasyon",
        "Gastroenteroloji", "Genel Cerrahi", "Göğüs Hastalıkları",
        "Göz Hastalıkları", "İç Hastalıkları", "Kadın Hastalıkları ve Doğum",
        "Kalp ve Damar Cerrahisi", "Kardiyoloji", "Kulak Burun Boğaz Hastalıkları",
        "Nöroloji", "Ortopedi ve Travmatoloji", "Psikiyatri", "Radyoloji", "Üroloji"
    ]

raporURLS = ["https://drive.google.com/file/d/1La_VSM-7_gq1LOsNhCZ96adX9yEFCoFd/view?usp=sharing",
             "https://drive.google.com/file/d/1t8RpoVmTxAnCb5to1jWHpXCsdxwE1f7R/view?usp=sharing",
             "https://drive.google.com/file/d/1MTbRP6vptlCZF9FnU5nZEqG4W_zpCBrz/view?usp=sharing"]

raporIcerikler = ["Kan Tahlili Sonuçları", "Radyoloji Görüntüleme Raporu", "Kardiyolojik Muayene Bulguları",
                  "Nörolojik Değerlendirme Sonuçları", "Onkolojik Test Bulguları", "Endokrin Sistem İncelemesi",
                  "Solunum Fonksiyonu Testi", "Gastrointestinal İnceleme Bulguları", "Ortopedik Muayene Raporu",
                  "Hematoloji Değerleri Analizi", "Ürolojik Tetkik Bulguları", "Dermatolojik Muayene Sonuçları",
                  "Psikiyatrik Değerlendirme Raporu", "Oftalmolojik Test Sonuçları", "İmmünolojik Test Bulguları"]

# Veritabanına bağlanma fonksiyonu
def connect_db():
    db = sqlite3.connect('database.db')
    db.execute('PRAGMA foreign_keys = ON;')  # FOREIGN KEY kısıtlamalarını etkinleştir
    return db


# Hastane Veritabanına ait tabloların oluşturulmasını sağlayan metot
def tabloOlustur() -> None:
    with app.app.app_context():

        # Veritabanı bağlantısını oluştur
        db = connect_db()

        # Bir cursor oluştur
        cur = db.cursor()

        # Hastalar Tablosu
        cur.execute('''CREATE TABLE IF NOT EXISTS Hastalar 
                        (
                        hasta_ad TEXT,
                        hasta_soyad TEXT,
                        hasta_dogumTarih TEXT, 
                        hasta_cinsiyet TEXT, 
                        hasta_telefon TEXT, 
                        hasta_adres TEXT, 
                        hasta_saglikSigorta TEXT,
                        hastaID INTEGER PRIMARY KEY,
                        hasta_eposta TEXT,
                        hasta_parola TEXT)
                        ''')

        # Hastaneleri Barındıran Tablo
        cur.execute('''CREATE TABLE IF NOT EXISTS Hastaneler
                        (
                        hastaneSube TEXT PRIMARY KEY
                        )
                        ''')

        # Uzmanlık Alanlarını Barındıran Tablo
        cur.execute('''CREATE TABLE IF NOT EXISTS UzmanlikAlanlar
                        (
                        uzmanlik TEXT PRIMARY KEY
                        )
                        ''')

        # Hastane Bilgileri Tablosu
        cur.execute('''CREATE TABLE IF NOT EXISTS HastaneBilgiler
                        (
                        hastaneSube TEXT,
                        uzmanlik TEXT,
                        PRIMARY KEY (hastaneSube, uzmanlik),
                        FOREIGN KEY (hastaneSube)  REFERENCES Hastaneler(hastaneSube),
                        FOREIGN KEY (uzmanlik)  REFERENCES UzmanlikAlanlar(uzmanlik)
                        )
                        ''')

        # Doktorlar Tablosu
        cur.execute('''CREATE TABLE IF NOT EXISTS Doktorlar 
                        (
                        doktor_ad TEXT, 
                        doktor_soyad TEXT,
                        doktor_dogumTarih TEXT, 
                        doktor_cinsiyet TEXT, 
                        doktor_telefon TEXT, 
                        doktor_adres TEXT,
                        doktor_uzmanlikAlan TEXT,
                        doktor_calisilanHastane TEXT,
                        doktorID INTEGER PRIMARY KEY,
                        doktor_eposta TEXT, 
                        doktor_parola TEXT,
                        FOREIGN KEY ( doktor_calisilanHastane, doktor_uzmanlikAlan ) REFERENCES HastaneBilgiler( hastaneSube, uzmanlik )
                        )
                        ''')

        # Yöneticiler Tablosu
        cur.execute('''CREATE TABLE IF NOT EXISTS Yoneticiler 
                        (
                        yonetici_ad TEXT, 
                        yonetici_soyad TEXT,
                        yonetici_dogumTarih TEXT, 
                        yonetici_cinsiyet TEXT, 
                        yonetici_telefon TEXT, 
                        yonetici_adres TEXT,
                        yoneticiID INTEGER PRIMARY KEY,
                        yonetici_eposta TEXT, 
                        yonetici_parola TEXT)
                        ''')

        # Randevular Tablosu
        cur.execute('''CREATE TABLE IF NOT EXISTS Randevular 
                        (
                        randevuID INTEGER PRIMARY KEY, 
                        doktorID INTEGER,
                        hastaID INTEGER,
                        randevu_tarih TEXT, 
                        randevu_saat TEXT,
                        FOREIGN KEY (doktorID) REFERENCES Doktorlar(doktorID),
                        FOREIGN KEY (hastaID) REFERENCES Hastalar(hastaID)
                        )
                        ''')


        # Tıbbi Raporlar
        cur.execute('''CREATE TABLE IF NOT EXISTS Raporlar
                        ( 
                        raporID INTEGER PRIMARY KEY,
                        raporURL TEXT,
                        rapor_tarih TEXT,
                        rapor_icerik TEXT,
                        hastaID INTEGER,
                        FOREIGN KEY (hastaID) REFERENCES Hastalar(hastaID)
                        )
                        ''')
        db.commit()  # Değişiklikleri kaydet
        db.close()  # Bağlantıyı kapat
        print("Tablo Olusturuldu")


# SQL Fonksiyonları
# A - HASTA FONKSİYONLARI
# 1 - Hasta Ekleme
def HastaEkle(hasta: siniflar.Hasta) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    hasta_verileri = [hasta.getAd(), hasta.getSoyad(), hasta.getDogumTarih(), hasta.getCinsiyet(),
                      hasta.getTelefonNo(), hasta.getAdres(), hasta.getSaglikSigorta(),
                      hasta.getHastaID(), hasta.getEposta(), hasta.getParola()]
    cur.execute("INSERT INTO Hastalar VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", hasta_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# 2 - Hasta Silme
def HastaSil(hastaID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    RandevuSilHastaHepsi(hastaID)  # Girilen ID'ye sahip hasta randevuları siliniyor
    RaporSilHastaID(hastaID)
    # Girilen ID'ye sahip hasta varsa siliniyor
    cur.execute("DELETE FROM Hastalar WHERE hastaID=?", (hastaID,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Hasta Bilgisi Arama
def HastaAra(hastaID: int) -> list:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Aynı HastaID'ye sahip biri var mı kontrol ediliyor
    cur.execute("SELECT * FROM Hastalar WHERE hastaID = ?", (hastaID,))
    hastaBilgiler = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return hastaBilgiler  # Bulunan Bilgileri Geri Döndür


# 4 - Hasta Bilgi Güncelle
def HastaBilgiGuncelle(ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                       saglikSigorta: str, eposta: str, hastaID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    hasta_verileri = [ad, soyad, dogumTarih, cinsiyet,
                      telefonNo, adres, saglikSigorta,
                      eposta, hastaID,]
    cur.execute('''UPDATE Hastalar
                Set hasta_ad = ?, hasta_soyad = ?, hasta_dogumTarih = ?, hasta_cinsiyet = ?,
                hasta_telefon = ?, hasta_adres = ?, hasta_saglikSigorta = ?, hasta_eposta = ?
                WHERE hastaID = ?''', hasta_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    print("Hasta Bilgileri güncellendi")


# B - HASTANE SUBE FONKSİYONLARI
# 1 - Hastane Sube Oluşturma
def SubeEkle(hastaneSube: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("INSERT INTO Hastaneler VALUES (?)", (hastaneSube,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 2 - Hastane Sube Silme
def SubeSil(hastaneSube: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    HastaneBilgiSilSube(hastaneSube)
    cur.execute("DELETE FROM Hastaneler WHERE hastaneSube = ?", (hastaneSube,))
    subeBilgiler = cur.fetchall()
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Hastane Sube Arama
def SubeAra(hastaneSube: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("SELECT * FROM Hastaneler WHERE hastaneSube = ?", (hastaneSube,))
    subeBilgiler = cur.fetchall()
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return subeBilgiler


# C - UZMANLIK ALAN FONKSİYONLARI
# 1 - Uzmanlık Alan Oluşturma
def UzmanlikEkle(uzmanlik: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("INSERT INTO UzmanlikAlanlar VALUES (?)", (uzmanlik,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 2 - Uzmanlık Alan Silme
def UzmanlikSil(uzmanlik: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    HastaneBilgiSilUzmanlik(uzmanlik)
    cur.execute("DELETE FROM UzmanlikAlanlar WHERE uzmanlik = ?", (uzmanlik,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Uzmanlık Alan Arama
def UzmanlikAra(uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("SELECT * FROM UzmanlikAlanlar WHERE uzmanlik = ?", (uzmanlik,))
    uzmanlikBilgiler = cur.fetchall()
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return uzmanlikBilgiler


# D - HASTANE BİLGİSİ FONKSİYONLARI
# 1 - Hastane Bilgisi Oluşturma
def HastaneBilgiEkle(hastaneSube: str, uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    hastaneBilgiVeriler = [hastaneSube, uzmanlik]
    cur.execute("SELECT * FROM Hastaneler WHERE hastaneSube=?", (hastaneSube,))
    hastaneSubeBilgiler = cur.fetchall()
    cur.execute("SELECT * FROM UzmanlikAlanlar WHERE uzmanlik=?", (uzmanlik,))
    uzmanlikBilgiler = cur.fetchall()

    if len(hastaneSubeBilgiler) > 0 and len(uzmanlikBilgiler) > 0:
        cur.execute("INSERT INTO HastaneBilgiler VALUES (?, ?)", hastaneBilgiVeriler)
    else:
        print("Girilen bilgiler yanlış")

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 2 - Hastane Bilgisi Silme - Girilen bilgileri içeren hastane bilgisi varsa siler
def HastaneBilgiSilBelirli(hastaneSube: str, uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    DoktorSilBelirli(hastaneSube, uzmanlik)
    cur.execute("DELETE FROM HastaneBilgiler WHERE hastaneSube = ? AND uzmanlik = ?", (hastaneSube, uzmanlik,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Hastane Bilgisi Silme - Girilen hastane şubesine ait hastane bilgilerini siler
def HastaneBilgiSilSube(hastaneSube: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    DoktorSilSube(hastaneSube)
    cur.execute("DELETE FROM HastaneBilgiler WHERE hastaneSube = ?", (hastaneSube,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 4 - Hastane Bilgisi Silme - Girilen uzmanlik alanına ait hastane bilgilerini siler
def HastaneBilgiSilUzmanlik(uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    DoktorSilUzmanlik(uzmanlik)
    cur.execute("DELETE FROM HastaneBilgiler WHERE uzmanlik = ?", (uzmanlik,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 5 - Hastane Bilgisi Arama - Girilen bilgileri içeren hastane bilgisi varsa döndürür
def HastaneBilgiAraBelirli(hastaneSube: str, uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    hastaneBilgiVeriler = [hastaneSube, uzmanlik]
    cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ? AND uzmanlik = ?", hastaneBilgiVeriler)
    hastaneBilgi = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return hastaneBilgi  # Bulunan Bilgileri Geri Döndür


# 6 - Hastane Bilgisi Arama - Girilen sube varsa hastane bilgilerini döndürür
def HastaneBilgiAraSube(hastaneSube: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ?",(hastaneSube,))
    hastaneBilgi = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return hastaneBilgi  # Bulunan Bilgileri Geri Döndür


# 7 - Hastane Bilgisi Arama - Girilen uzmanlik alanını içeren hastane bilgilerini döndürür
def HastaneBilgiAraUzmanlik(uzmanlik: str):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    cur.execute("SELECT * FROM HastaneBilgiler WHERE uzmanlik = ?",(uzmanlik,))
    hastaneBilgi = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return hastaneBilgi  # Bulunan Bilgileri Geri Döndür


# E - DOKTOR FONKSİYONLARI
# 1 - Doktor Ekleme
def DoktorEkle(doktor: siniflar.Doktor) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    doktor_verileri = [doktor.getAd(), doktor.getSoyad(), doktor.getDogumTarih(), doktor.getCinsiyet(),
                       doktor.getTelefonNo(), doktor.getAdres(), doktor.getUzmanlikAlan(),
                       doktor.getCalisilanHastane(), doktor.getDoktorID(), doktor.getEposta(), doktor.getParola()]
    cur.execute("INSERT INTO Doktorlar VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", doktor_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# 2 - Doktor Silme
def DoktorSil(doktorID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    RandevuSilDoktorHepsi(doktorID)  # Girilen ID'ye sahip doktorun randevuları siliniyor
    # Girilen ID'ye sahip doktor varsa siliniyor
    cur.execute("DELETE FROM Doktorlar WHERE doktorID=?", (doktorID,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Doktor Silme - Belirli bir hastane şubesinde doktorları siler
def DoktorSilSube(hastaneSube: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Girilen şubedeki tüm doktorlar siliniyor
    cur.execute("SELECT doktorID FROM Doktorlar WHERE doktor_calisilanHastane=?", (hastaneSube,))
    doktorBilgileri = cur.fetchall()
    for doktorBilgi in doktorBilgileri:
        doktorID, *kalan = doktorBilgi
        DoktorSil(doktorID)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 4 - Doktor Silme - Belirli bir uzmanlığa sahip tüm doktorları siler
def DoktorSilUzmanlik(uzmanlik: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Girilen uzmanlığa sahip tüm doktorlar siliniyor
    cur.execute("SELECT doktorID FROM Doktorlar WHERE doktor_uzmanlikAlan=?", (uzmanlik,))
    doktorBilgileri = cur.fetchall()
    for doktorBilgi in doktorBilgileri:
        doktorID, *kalan = doktorBilgi
        DoktorSil(doktorID)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 5 - Doktor Silme - Girilen hastane bilgilerine sahip doktorları siler
def DoktorSilBelirli(hastaneSube: str, uzmanlik: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Girilen hastane bilgilerine sahip tüm doktorlar siliniyor
    cur.execute("SELECT doktorID FROM Doktorlar WHERE doktor_uzmanlikAlan=? AND doktor_calisilanHastane=?",
                (uzmanlik, hastaneSube))
    doktorBilgileri = cur.fetchall()
    for doktorBilgi in doktorBilgileri:
        doktorID, *kalan = doktorBilgi
        DoktorSil(doktorID)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 6 - Doktor Bilgisi Arama
def DoktorAra(doktorID: int) -> list:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Aynı DoktorID'ye sahip biri var mı kontrol ediliyor
    cur.execute("SELECT * FROM Doktorlar WHERE doktorID = ?", (doktorID,))
    doktorBilgiler = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return doktorBilgiler  # Bulunan Bilgileri Geri Döndür


# 7 - Doktor Bilgi Güncelle
def DoktorBilgiGuncelle(ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                        eposta: str, doktorID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    doktor_verileri = [ad, soyad, dogumTarih, cinsiyet,
                       telefonNo, adres,
                       eposta, doktorID,]
    cur.execute('''UPDATE Doktorlar
                Set doktor_ad = ?, doktor_soyad = ?, doktor_dogumTarih = ?, doktor_cinsiyet = ?,
                doktor_telefon = ?, doktor_adres = ?, doktor_eposta = ?
                WHERE doktorID = ?''', doktor_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    print("Doktor Bilgileri güncellendi")


# F - YONETİCİ FONKSİYONLARI
# 1 - Yönetici Ekleme
def YoneticiEkle(yonetici: siniflar.Yonetici) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    yonetici_verileri = [yonetici.getAd(), yonetici.getSoyad(), yonetici.getDogumTarih(), yonetici.getCinsiyet(),
                         yonetici.getTelefonNo(), yonetici.getAdres(), yonetici.getYoneticiID(), yonetici.getEposta(),
                         yonetici.getParola(),]
    cur.execute("INSERT INTO Yoneticiler VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", yonetici_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# 2 - Yönetici Silme
def YoneticiSil(yoneticiID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Girilen ID'ye sahip biri varsa bulunup siliniyor
    cur.execute("DELETE FROM Yoneticiler WHERE yoneticiID=?", (yoneticiID,))
    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# 3 - Yönetici Bilgisi Arama
def YoneticiAra(yoneticiID: int) -> list:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Aynı YoneticiID'ye sahip biri var mı kontrol ediliyor
    cur.execute("SELECT * FROM Yoneticiler WHERE yoneticiID = ?", (yoneticiID,))
    yoneticiBilgiler = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return yoneticiBilgiler  # Bulunan Bilgileri Geri Döndür


# 4 - Yönetici Bilgi Güncelle
def YoneticiBilgiGuncelle(ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                          eposta: str, yoneticiID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    yonetici_verileri = [ad, soyad, dogumTarih, cinsiyet,
                         telefonNo, adres,
                         eposta, yoneticiID,]
    cur.execute('''UPDATE Yoneticiler
                SET yonetici_ad = ?, yonetici_soyad = ?, yonetici_dogumTarih = ?, yonetici_cinsiyet = ?,
                yonetici_telefon = ?, yonetici_adres = ?, yonetici_eposta = ?
                WHERE yoneticiID = ?''', yonetici_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# G - RANDEVU FONKSİYONLARI
# 1 - Randevu Oluşturma
def RandevuEkle(randevu: siniflar.Randevu) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    randevu_verileri = [randevu.getRandevuID(), randevu.getRandevuDoktorID(), randevu.getRandevuHastaID(),
                        randevu.getRandevuTarih(), randevu.getRandevuSaat()]
    cur.execute("INSERT INTO Randevular VALUES (?, ?, ?, ?, ?)", randevu_verileri)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat

# 2 - HastaID ile Randevu Bilgisi Arama
def RandevuAraHastaID(hastaID: int) -> list:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # HastaID'ye sahip randevuları bulur
    cur.execute("SELECT * FROM Randevular WHERE hastaID = ?", (hastaID,))
    randevuBilgiler = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return randevuBilgiler  # Bulunan Bilgileri Geri Döndür

# 3 - DoktorID ile Randevu Bilgisi Arama
def RandevuAraDoktorID(doktorID: int) -> list:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # DoktorID'ye sahip randevuları bulur
    cur.execute("SELECT * FROM Randevular WHERE doktorID = ?", (doktorID,))
    randevuBilgiler = cur.fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return randevuBilgiler  # Bulunan Bilgileri Geri Döndür

# 4 - Randevu Silme
def RandevuSilBelirli(doktorID: int, hastaID: int, randevuTarih: str, randevuSaat: str) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Uygun Randevu varsa bulup onu siler
    randevu_veriler = (doktorID, hastaID, randevuTarih, randevuSaat,)
    cur.execute('''DELETE FROM Randevular WHERE hastaID = ? AND doktorID = ? 
                AND randevu_tarih = ? AND randevu_saat = ?''', randevu_veriler)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 5 - Randevu Silme - Bir doktora ait tüm randevuları siler
def RandevuSilDoktorHepsi(doktorID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Uygun Randevu varsa bulup onu siler
    cur.execute('''DELETE FROM Randevular WHERE doktorID = ?''', (doktorID,))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 6 - Randevu Silme - Bir hastaya ait tüm randevuları siler
def RandevuSilHastaHepsi(hastaID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Uygun Randevu varsa bulup onu siler
    cur.execute('''DELETE FROM Randevular WHERE hastaID = ?''', (hastaID,))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# H - RAPOR FONKSİYONLARI
# 1 - Rapor Ekle
def RaporEkle(rapor: siniflar.TibbiRapor):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    raporVeriler = [rapor.getRaporID(), rapor.getRaporURL(), rapor.getRaporTarih(),
                    rapor.getRaporIcerik(), rapor.getRaporHastaID()]

    cur.execute("INSERT INTO Raporlar VALUES (?,?,?,?,?)", raporVeriler)

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 2 - Rapor Sil
def RaporSil(raporID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    cur.execute("DELETE FROM Raporlar WHERE raporID = ?", (raporID,))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Rapor Sil HastaID
def RaporSilHastaID(hastaID: int) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    cur.execute("DELETE FROM Raporlar WHERE hastaID = ?", (hastaID,))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 4 - Rapor Bilgisi Güncelle
def RaporBilgisiGuncelle(raporID, raporURL, raporTarih, raporIcerik, HastaID) -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    cur.execute('''UPDATE Raporlar SET raporURL = ?, rapor_tarih = ?, rapor_icerik = ?, HastaID = ?
    WHERE raporID = ?''', (raporURL, raporTarih, raporIcerik, HastaID, raporID))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 5 - Rapor Ara
def RaporAra(raporID: int):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    raporVeri = cur.execute('''SELECT * FROM Raporlar WHERE raporID = ?''', (raporID, )).fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return raporVeri


# 6 - Rapor Ara HastaID
def RaporAraHastaID(hastaID: int):
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    raporVeri = cur.execute('''SELECT * FROM Raporlar WHERE hastaID = ?''', (hastaID, )).fetchall()

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat
    return raporVeri


# G - VERİ OLUŞTURMA FONKSİYONLARI
# 1 - Sube Verisi Olustur
def SubeVerisiOlustur() -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    for sube in sube_isimleri:
        cur.execute("INSERT INTO Hastaneler VALUES (?)", (sube,))

    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 2 - Uzmanlık Alanı Verisi Olustur
def UzmanlikAlanVerisiOlustur() -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    for uzmanlik_alan in uzmanlik_alanlari:
        cur.execute("INSERT INTO UzmanlikAlanlar VALUES (?)", (uzmanlik_alan,))


    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 3 - Hastane Bilgi Verisi Olustur
def HastaneBilgiVerisiOlustur() -> None:
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur

    # Hastane ve Uzmanlık Alan Bilgisi Çekme
    cur.execute("SELECT * FROM Hastaneler")
    sube_bilgiler = cur.fetchall()
    cur.execute("SELECT * FROM UzmanlikAlanlar")
    uzmanlik_alanlar_bilgiler = cur.fetchall()

    for sube_bilgi in sube_bilgiler:
        for uzmanlik_alan_bilgi in uzmanlik_alanlar_bilgiler:
            sube, *kalan = sube_bilgi
            uzmanlik_alan, *kalan = uzmanlik_alan_bilgi
            cur.execute("INSERT INTO HastaneBilgiler VALUES (?, ?)", (sube, uzmanlik_alan,))


    conn.commit()  # Değişiklikleri kaydet
    conn.close()  # Bağlantıyı kapat


# 4 - Doktor Verisi oluşturan fonksiyon
def DoktorVerisiOlustur():
    conn = connect_db()  # Veritabanı bağlantısını oluştur
    cur = conn.cursor()  # Bir cursor oluştur
    fake = faker.Faker('tr_TR')
    cur.execute("SELECT * FROM HastaneBilgiler")
    hastaneBilgiler = cur.fetchall()

    for i in range(1, 1201):
        # Doktor Nesnesine ait bilgileri oluşturur
        doktorID = str(random.randint(10000000000, 19999999999))
        telefon = str(random.randint(5000000000, 5999999999))

        adres = random.choice(turkish_cities)
        eposta = fake.email().split("@")[0] + random.choice(mails)
        parola = generate_password_hash("123")

        gun = random.randint(1, 30)
        ay = random.randint(1, 12)
        yil = random.randint(1960, 2000)
        dogumTarih = str(yil) + "/" + str(ay) + "/" + str(gun)

        calisilanHastane, uzmanlikAlan = random.choice(hastaneBilgiler)

        secenek = random.randint(0, 1)
        if secenek == 0:  # Erkek ise
            cinsiyet = "Erkek"
            secenek_isim = random.randint(0, 1)
            if secenek_isim == 0:
                ad = fake.first_name_male()
            else:
                ad = fake.first_name_male() + " " + fake.first_name_male()

            soyad = fake.last_name_male()
        else:
            cinsiyet = "Kadın"
            secenek_isim = random.randint(0, 1)
            if secenek_isim == 0:
                ad = fake.first_name_female()
            else:
                ad = fake.first_name_female() + " " + fake.first_name_female()
            soyad = fake.last_name_female()

        print(f"{i + 1}. Doktor oluşturuldu.\n"
              f"doktorID: {doktorID}\n"
              f"Telefon:  {telefon}\n"
              f"Cinsiyet: {cinsiyet}\n"
              f"İsim: {ad} {soyad}\n"
              f"Şehir: {adres}\n"
              f"Eposta: {eposta}\n"
              f"Parola: {parola}\n"
              f"Doğum Tarihi: {dogumTarih}\n"
              f"Uzmanlık Alan: {uzmanlikAlan}\n"
              f"Çalışılan Hastane Şubesi: {calisilanHastane}\n")

        # Oluşturulan ID'ye sahip biri var mı diye kontrol eder
        hastaBilgiler = HastaAra(doktorID)
        yoneticiBilgiler = YoneticiAra(doktorID)
        doktorBilgiler = DoktorAra(doktorID)
        if len(hastaBilgiler) > 0 or len(yoneticiBilgiler) > 0 or len(doktorBilgiler) > 0:
            print("Böyle bir İD'ye sahip kayıt zaten var.")
        else:
            doktor = siniflar.Doktor(ad, soyad, dogumTarih, cinsiyet, telefon, adres, uzmanlikAlan,
                                     calisilanHastane, doktorID, eposta, parola)
            DoktorEkle(doktor)
            print("Doktor Eklendi")

    conn.commit()
    conn.close()

# 5 - Hasta Verisi oluşturan fonksiyon
def HastaVerisiOlustur():
    fake = faker.Faker('tr_TR')

    for i in range(1, 6001):
        hastaID = str(random.randint(10000000000, 67999999999))
        telefon = str(random.randint(5000000000, 5999999999))

        adres = random.choice(turkish_cities)
        eposta = fake.email().split("@")[0] + random.choice(mails)
        parola = generate_password_hash("123")

        gun = random.randint(1, 30)
        ay = random.randint(1, 12)
        yil = random.randint(1950, 2015)
        dogumTarih = str(yil) + "/" + str(ay) + "/" + str(gun)
        sigorta = random.choice(sigortalar)

        secenek = random.randint(0, 1)
        if secenek == 0:  # Erkek ise
            cinsiyet = "Erkek"
            secenek_isim = random.randint(0, 1)
            if secenek_isim == 0:
                ad = fake.first_name_male()
            else:
                ad = fake.first_name_male() + " " + fake.first_name_male()

            soyad = fake.last_name_male()
        else:
            cinsiyet = "Kadın"
            secenek_isim = random.randint(0, 1)
            if secenek_isim == 0:
                ad = fake.first_name_female()
            else:
                ad = fake.first_name_female() + " " + fake.first_name_female()
            soyad = fake.last_name_female()


        print(f"{i + 1}. Hasta oluşturuldu.\n"
              f"hastaID: {hastaID}\n"
              f"Telefon:  {telefon}\n"
              f"Cinsiyet: {cinsiyet}\n"
              f"İsim: {ad} {soyad}\n"
              f"Şehir: {adres}\n"
              f"Eposta: {eposta}\n"
              f"Parola: {parola}\n"
              f"Doğum Tarihi: {dogumTarih}\n"
              f"Sigorta: {sigorta}\n\n")

        hastaBilgiler = HastaAra(hastaID)
        yoneticiBilgiler = YoneticiAra(hastaID)
        doktorBilgiler = DoktorAra(hastaID)
        if len(hastaBilgiler) > 0 or len(yoneticiBilgiler) > 0 or len(doktorBilgiler) > 0:
            print("Böyle bir İD'ye sahip kayıt zaten var.")
        else:
            hasta = siniflar.Hasta(ad, soyad, dogumTarih, cinsiyet, telefon, adres,
                                   sigorta, hastaID, eposta, parola)
            HastaEkle(hasta)
            print("Hasta Eklendi")


# 6 - Randevu Verisi oluşturur
def RandevuVerisiOlustur():
    db = connect_db()
    cur = db.cursor()
    cur.execute("SELECT hastaID FROM Hastalar WHERE hastaID IS NOT NULL")
    hastaBilgier = cur.fetchall()

    cur.execute("SELECT doktorID FROM Doktorlar WHERE doktorID IS NOT NULL")
    doktorBilgiler = cur.fetchall()

    cur.execute("SELECT MAX(randevuID) FROM Randevular")
    randevu_bilgiler = cur.fetchall()
    next_randevu_id, *kalan = randevu_bilgiler[0]
    if next_randevu_id is None:
        next_randevu_id = 0
    next_randevu_id += 1

    for hastaBilgi in hastaBilgier:
        hastaID, *kalan = hastaBilgi
        randevu_sayisi = random.randint(80, 110)
        for i in range(1, randevu_sayisi):
            # Randevuya ait olan bilgilerin seçilmesi
            doktorBilgi = random.choice(doktorBilgiler)
            doktorID, *kalan = doktorBilgi
            gun = random.randint(1, 30)
            ay = random.randint(1, 12)
            yil = random.randint(2001, 2027)
            saat = random.randint(7, 20)
            dakika = random.randint(0, 59)
            dakika = dakika - (dakika % 5)
            if saat < 10:
                saat = "0" + str(saat)
            if dakika < 10:
                dakika = "0" + str(dakika)
            if gun < 10:
                gun = "0" + str(gun)
            if ay < 10:
                ay = "0" + str(ay)

            randevu_tarih = str(yil) + "/" + str(ay) + "/" + str(gun)
            randevu_zaman = str(saat) + ":" + str(dakika)

            # Randevunun Nesnesinin Oluşturulması
            randevu = siniflar.Randevu(next_randevu_id, doktorID, hastaID, randevu_tarih, randevu_zaman)
            RandevuEkle(randevu)  # Randevunun veritabanına eklenmesi
            next_randevu_id = next_randevu_id + 1
            db.commit()

    db.close()


# 7 - Sitede bulunacak yöneticileri oluşturan fonksiyon
def yoneticileriOlustur() -> None:
    yonetici1 = siniflar.Yonetici("Selim Eren", "Kaya", "20/09/2004", "Erkek",
                                  "5416798301", "Kocaeli", "10013168314",
                                  "selim@gmail.com", generate_password_hash("123"),)
    if len(YoneticiAra(yonetici1.getYoneticiID())) == 0:
        YoneticiEkle(yonetici1)

    yonetici2 = siniflar.Yonetici("Bilge", "Çeşme", "7/01/2004", "Kadın",
                                  "2355354235", "İzmir", "13523535035",
                                  "bilge@gmail.com", generate_password_hash("bilge35Forever"))
    if len(YoneticiAra(yonetici2.getYoneticiID())) == 0:
        YoneticiEkle(yonetici2)

    yonetici3 = siniflar.Yonetici("Yalçın", "Dağbaşı", "27/09/2003", "Erkek",
                                  "2503054824", "Çanakkale", "20030035140",
                                  "yalcin@gmail.com", generate_password_hash("Yalcin123"))
    if len(YoneticiAra(yonetici3.getYoneticiID())) == 0:
        YoneticiEkle(yonetici3)


# Veri Temizleme Fonksiyonu
# Sıfırdan veriler oluşturulmayacağı sürece çağrılmamalı
def VeritabaniVeriTemizle():
    con = connect_db()
    cur = con.cursor()
    cur.execute("DELETE FROM Randevular WHERE 1=1")
    cur.execute("DELETE FROM Doktorlar WHERE 1=1")
    cur.execute("DELETE FROM HastaneBilgiler WHERE 1=1")
    cur.execute("DELETE FROM UzmanlikAlanlar WHERE 1=1")
    cur.execute("DELETE FROM Hastaneler WHERE 1=1")
    cur.execute("DELETE FROM Hastalar WHERE 1=1")
    con.commit()
    con.close()


def RaporVeriOlustur():
    con = connect_db()
    cur = con.cursor()
    cur.execute("SELECT hastaID FROM Hastalar WHERE hastaID IS NOT NULL")
    hastaBilgier = cur.fetchall()

    cur.execute("SELECT MAX(raporID) FROM Raporlar")
    raporBilgiler = cur.fetchall()
    next_rapor_id, *kalan = raporBilgiler[0]
    if next_rapor_id is None:
        next_rapor_id = 0
    next_rapor_id += 1
    sayac = 1
    for hastaBilgi in hastaBilgier:
        hastaID, *kalan = hastaBilgi
        rapor_sayisi = random.randint(7, 12)
        for i in range(1, rapor_sayisi):

            gun = random.randint(1, 30)
            ay = random.randint(1, 12)
            yil = random.randint(2014, 2025)
            saat = random.randint(7, 20)
            dakika = random.randint(0, 59)
            dakika = dakika - (dakika % 5)
            if saat < 10:
                saat = "0" + str(saat)
            if dakika < 10:
                dakika = "0" + str(dakika)
            if gun < 10:
                gun = "0" + str(gun)
            if ay < 10:
                ay = "0" + str(ay)

            rapor_tarih = str(yil) + "/" + str(ay) + "/" + str(gun)

            rapor_url = random.choice(raporURLS)
            rapor_icerik = random.choice(raporIcerikler)

            rapor = siniflar.TibbiRapor(next_rapor_id, rapor_url, rapor_tarih, rapor_icerik, hastaID)
            RaporEkle(rapor)
            next_rapor_id = next_rapor_id + 1
            print(sayac)
            sayac += 1
            con.commit()

    con.close()
