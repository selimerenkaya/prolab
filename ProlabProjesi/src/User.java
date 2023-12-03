import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


// Giriş Yapılabilir Classları temsil eden interface
interface ILoginable {
    // Girişleri kontrol eden metot
    boolean Giris(String kullaniciAdiGirdisi, String sifreGirdisi);
}

// Sistemdeki kullanıcıları temsil eden class
// Abstract bir class
// 2 türe ayrılır
// 1- Admin
// 2- Company
abstract class User implements ILoginable {
    // User Class'ının özellikleri
    String kullaniciAdi;
    String sifre;

    // Construct edilirken kulllanılacak metot
    User() {

    }

    User(String kullaniciAdiDegiskeni, String sifreDegiskeni){
        kullaniciAdi = kullaniciAdiDegiskeni;
        sifre = sifreDegiskeni;
    }

    // Kullanıcı Adı değişkeni için Get/Set Metotları
    public abstract String get_kullaniciAdi();

    public abstract void set_kullaniciAdi(String kullaniciAdiYeni);

    // Şifre değişkeni için Get/Set Metotları
    public abstract String get_sifre();

    public abstract void set_sifre(String sifreYeni);

}



// User Class'ının Admin Classı
class Admin extends User {
    // Admin hesabının giriş bilgileri
    String kullaniciAdi = "admin";
    String sifre = "admin123";
    static boolean admin_aktif = false; // Sistemde sadece tek bir adminin aktif olmasını sağlayan değişken

    // Kullanıcı Adı değişkeni için Get/Set Metotları
    @Override
    public String get_kullaniciAdi() {
        return kullaniciAdi;
    }
    @Override
    public void set_kullaniciAdi(String kullaniciAdiYeni) {
        kullaniciAdi = kullaniciAdiYeni;
    }

    // Şifre değişkeni için Get/Set Metotları
    @Override
    public String get_sifre() {
        return sifre;
    }
    @Override
    public void set_sifre(String sifreYeni) {
        sifre = sifreYeni;
    }

    // Admin Classı Contruct Edilirken kullanılacak metot
    Admin() {

    }


    // Giriş Bilgilerini kontrol eden kısım
    @Override
    public boolean Giris(String kullaniciAdiGirdisi, String sifreGirdisi) {
        // Admin girişi için doğru bilgiler girildiyse TRUE yanlış girildiyse FALSE değer
        // döndüren metot
        return kullaniciAdiGirdisi.equals(new Admin().get_kullaniciAdi()) && sifreGirdisi.equals(new Admin().get_sifre());
    }

    // Admin_Aktif değişkeni için Get/Set metotları
    public boolean get_Admin_Aktif() {
        return admin_aktif;
    }

    // Sisteme admin giriş yaptığında admin olduğunu işleyen metot
    public void set_Admin_Aktif_Et() {
        admin_aktif = true;
    }

    // Sistemden admin çıkış yaptığında admin yok olduğunu işleyen metot
    public void set_Admin_Deaktif_Et() {
        admin_aktif = false;
    }



    // Admin Class'ının Giriş Arayüzünü İçeren Class
    static class Admin_Giris_Arayuz extends JFrame {

        // Admin Giriş Arayüzü
        public Admin_Giris_Arayuz() {
            // Ana Menünün Arayüzünün oluşturulması
            setTitle("Admin Giriş Paneli");
            setSize(800, 600);



            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.white);

            // Giriş Bilgilerini alan kısım
            // 1- Kullanıcı adı
            JLabel kullanici_adi_baslik = new JLabel("Kullanıcı Adı");
            kullanici_adi_baslik.setBounds(20, 20, 100, 20);
            JTextField kullaniciAdiGirdisi = new JTextField();
            kullaniciAdiGirdisi.setBounds(20, 40, 200, 30);
            add(kullanici_adi_baslik);
            add(kullaniciAdiGirdisi);

            // 2- Şifre
            JLabel sifre_baslik = new JLabel("Şifre");
            sifre_baslik.setBounds(20, 80, 100, 20);
            JTextField sifreGirdisi = new JTextField(); //veri arayüzde böyle alınıyor
            sifreGirdisi.setBounds(20, 100, 200, 30);
            add(sifre_baslik);
            add(sifreGirdisi);

            // Giriş Yapılmasını Sağlayan Buton
            JButton giris_butonu = new JButton("Giriş Yap");
            giris_butonu.setBounds(50, 140, 130, 40);
            giris_butonu.setBackground(new Color(120, 130, 255));

            // Butona tıklanınca çalışacak kısım
            giris_butonu.addActionListener(e -> {
                Admin kayit_bilgisi_admin = new Admin();
                boolean giris_izni = kayit_bilgisi_admin.Giris(kullaniciAdiGirdisi.getText(), sifreGirdisi.getText());
                if (giris_izni && !kayit_bilgisi_admin.get_Admin_Aktif()) {
                    System.out.println("Giriş başarılı");
                    kayit_bilgisi_admin.set_Admin_Aktif_Et();
                    new Admin_Islem_Arayuz();
                } else {
                    System.out.println("Giriş başarısız.");
                }
                //bence fena değil eklenebilir
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(giris_butonu); // Panele eklenmesi

            // Ana Menüye Geri Döndüren Buton
            JButton ana_menu_butonu = new JButton("Ana Menüye Geri Dön");
            ana_menu_butonu.setBounds(50, 190, 180, 40);
            ana_menu_butonu.setBackground(new Color(120, 130, 255));

            // Butona tıklanınca çalışacak kısım
            ana_menu_butonu.addActionListener(e -> {
                new Arayuz();
                dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(ana_menu_butonu); // Panele eklenmesi


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım


            setVisible(true); // Arayüzü görünür kılan metot
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot
        }
    }

    // Admin Class'ının İşlem Arayüzünü içeren Class
    static class Admin_Islem_Arayuz extends JFrame {
        // Doğru Kullanıcı adı ve Şifre Girilince çalışacak
        // Admin İşlem Arayüzü
        public Admin_Islem_Arayuz() {
            // Ana Menünün Arayüzünün oluşturulması
            setTitle("Admin İşlem Paneli");
            setSize(800, 600);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(120, 130, 255));

            // 1- Firmaların Görüntülenmesini Sağlayan Buton
            JButton firmalari_goruntule = new JButton("Firmaları Görüntüle");
            firmalari_goruntule.setBounds(50, 20, 190, 40);
            firmalari_goruntule.setBackground(Color.white);

            // Butona tıklanınca çalışacak kısım
            firmalari_goruntule.addActionListener(e -> {
                new Company.Firmalari_Goruntule();
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(firmalari_goruntule); // Panele eklenmesi

            // 2- Firmalara Yeni Firma Kaydı yapılmasını Sağlayan Buton
            JButton yeni_firma_ekle = new JButton("Yeni Firma Kaydı Yap");
            yeni_firma_ekle.setBounds(50, 70, 190, 40);
            yeni_firma_ekle.setBackground(Color.white);

            // Butona tıklanınca çalışacak kısım
            yeni_firma_ekle.addActionListener(e -> {
                new Company.Firmalara_Ekle();
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(yeni_firma_ekle); // Panele eklenmesi

            // 3- Firmalardan Firma Kaydı silinmesini Sağlayan Buton
            JButton firma_kayit_sil = new JButton("Firma Kaydı Sil");
            firma_kayit_sil.setBounds(50, 120, 190, 40);
            firma_kayit_sil.setBackground(Color.white);

            // Butona tıklanınca çalışacak kısım
            firma_kayit_sil.addActionListener(e -> {
                new Company.Firmalardan_Sil();
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(firma_kayit_sil); // Panele eklenmesi

            // 3- Hizmet Bedeli Belirlenmesini Sağlayan Buton
            JButton hizmet_bedeli_belirle = new JButton("Hizmet Bedeli Belirle");
            hizmet_bedeli_belirle.setBounds(50, 170, 190, 40);
            hizmet_bedeli_belirle.setBackground(Color.white);

            // Butona tıklanınca çalışacak kısım
            hizmet_bedeli_belirle.addActionListener(e -> {
                new Company.Hizmet_Bedeli_Arayuzu();
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(hizmet_bedeli_belirle); // Panele eklenmesi

            // 4- Ana Menüye Geri Döndüren Buton
            JButton ana_menu_butonu = new JButton("Çıkış Yap");
            ana_menu_butonu.setBounds(50, 220, 180, 40);
            ana_menu_butonu.setBackground(Color.white);

            // Butona tıklanınca çalışacak kısım
            ana_menu_butonu.addActionListener(e -> {
                new Admin().set_Admin_Deaktif_Et();
                dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(ana_menu_butonu); // Panele eklenmesi


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım


            setVisible(true); // Arayüzü görünür kılan metot
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot
        }
    }

}

//kar ve gelir hesaplama yeteneğine sahip nesneleri temsil eder
//günlük kar hesabı genel kar zarar hesabı burada yapılmalı
interface Iprofitable{

}

// User Class'ının Company Classı Iprofitable implement edildi
class Company extends User implements Iprofitable {
    static int hizmet_bedeli = 1000; // TL bazında
    // Firma Oluşturulurken Gerekli Olan Bilgiler
    String firma_isim;
    String kullaniciAdi;
    String sifre;
    ArrayList<Object> aracBilgileri = new ArrayList<>();


    // Hizmet bedeli değişkeni için Get/Set metotları
    public int get_hizmet_bedeli() {
        return hizmet_bedeli;
    }

    public void set_hizmet_bedeli(int hizmet_bedeli_girdisi) {
        hizmet_bedeli = hizmet_bedeli_girdisi;
    }

    // Firma İsim değişkeni için Get/Set metotları
    public String get_firma_isim() {
        return firma_isim;
    }

    public void set_firma_isim(String firma_isim_girdisi) {
        firma_isim = firma_isim_girdisi;
    }

    // Company Parametresiz Constructor Metodu
    public Company() {

    }

    // Company Classı Contruct Edilirken kullanılacak metot - 3 Parametreli
    // Yeni Firma oluşturulurken çağrılır
    Company(String firma_isim_girdisi, String kullaniciAdi_girdisi, String sifre_girdisi) {
        // Araç Bilgileri Firma Paneli Arayüzünden girilecektir
        // Yoksa En başta boş olarak tanımlanır
        firma_isim = firma_isim_girdisi;
        kullaniciAdi = kullaniciAdi_girdisi;
        sifre = sifre_girdisi;
        aracBilgileri = new ArrayList<>(); // Firma Paneli - Araç Ekle kısmından düzenlenecek
    }

    // Company Classı Contruct Edilirken kullanılacak metot - 4 Parametreli
    // Kod ilk çalıştığında önceden verilen şirketler
    // oluşturulurken kullanılır
    Company(String firma_isim_girdisi, String kullaniciAdi_girdisi, String sifre_girdisi, ArrayList<Object> aracBilgileriGirdisi) {
        // Araç Bilgileri random bir şekilde hazırlanacak ve eklenecektir
        firma_isim = firma_isim_girdisi;
        kullaniciAdi = kullaniciAdi_girdisi;
        sifre = sifre_girdisi;
        aracBilgileri = aracBilgileriGirdisi;
    }


    // Kullanıcı Adı değişkeni için Get/Set Metotları
    @Override
    public String get_kullaniciAdi() {
        return kullaniciAdi;
    }

    @Override
    public void set_kullaniciAdi(String kullaniciAdiYeni) {
        kullaniciAdi = kullaniciAdiYeni;
    }

    // Şifre değişkeni için Get/Set Metotları
    @Override
    public String get_sifre() {
        return sifre;
    }

    @Override
    public void set_sifre(String sifreYeni) {
        sifre = sifreYeni;
    }

    // Firma Girişlerini kontrol eden fonksiyon
    @Override
    public boolean Giris(String kullaniciAdiGirdisi, String sifreGirdisi) {
        return false;
    }


    // ADMİN PANELİNDE KULLANILACAK OLAN COMPANY CLASSLARI
    // Company Class'ının Hizmet Bedeli Arayüzü
    static class Hizmet_Bedeli_Arayuzu extends JFrame {

        // Admin Giriş Arayüzü
        public Hizmet_Bedeli_Arayuzu() {
            // Ana Menünün Arayüzünün oluşturulması
            setTitle("Hizmet Bedeli Paneli");
            setSize(400, 300);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);

            // 1- Hizmet Bedeli girdisini alan kısım
            JLabel hizmet_bedeli_baslik = new JLabel("Hizmet Bedelini Giriniz");
            hizmet_bedeli_baslik.setBounds(20, 10, 200, 20);
            JTextField hizmetBedeliGirdisi = new JTextField();
            hizmetBedeliGirdisi.setBounds(20, 30, 250, 30);
            add(hizmet_bedeli_baslik);
            add(hizmetBedeliGirdisi);

            // 2- Geri Bildirim Yazısı
            JLabel geri_bildirim = new JLabel();
            Font geri_bildirim_font = geri_bildirim.getFont();
            geri_bildirim.setFont(geri_bildirim_font.deriveFont(geri_bildirim_font.getStyle() | Font.BOLD, 16));
            geri_bildirim.setBounds(30, 120, 200, 20);

            geri_bildirim.setVisible(false);
            panel.add(geri_bildirim);

            // 3- Onayla Butonu
            JButton onayla_butonu = new JButton("Onayla");
            onayla_butonu.setBounds(50, 70, 180, 40);


            // Butona tıklanınca çalışacak kısım
            onayla_butonu.addActionListener(e -> {
                geri_bildirim.setVisible(false);
                // Geri Bildirim Almayı Sağlayan kısım
                try {
                    new Company().set_hizmet_bedeli(Integer.parseInt(hizmetBedeliGirdisi.getText()));
                    geri_bildirim.setText("Başarıyla Değiştirildi.");
                    geri_bildirim.setForeground(Color.GREEN);
                    geri_bildirim.setVisible(true);

                } catch (Exception ex) {
                    System.out.println("Hata alındı!");
                    geri_bildirim.setText("Hata Oluştu!");
                    geri_bildirim.setForeground(Color.RED);
                    geri_bildirim.setVisible(true);
                }

            });
            panel.add(onayla_butonu); // Panele eklenmesi


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım


            setVisible(true); // Arayüzü görünür kılan metot
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot
        }
    }


    // Company Class'ının Tüm Firmaları Görüntüleyen Arayüz Classı
    static class Firmalari_Goruntule extends JFrame {

        // Firma Görüntüleme Arayüzü
        public Firmalari_Goruntule() {
            //Arayüz Ayarları
            setTitle("Firma Bilgileri");
            setSize(800, 600);

            // Panelin Oluşturulması
            JPanel panel = new JPanel();

            // Firma Bilgilerini Alan kısım
            ArrayList<Company> firmaBilgileri = new Transport().FirmaBilgileriniDondur();

            // Firma Bilgilerini Bilgi Listine atayan kısım
            String[][] bilgi = {};
            for (Company bilgiler : firmaBilgileri) {
                String[] yeni_firma = {bilgiler.get_firma_isim(), bilgiler.get_kullaniciAdi(), bilgiler.get_sifre()};
                bilgi = Arrays.copyOf(bilgi, bilgi.length + 1);
                bilgi[bilgi.length - 1] = yeni_firma;

            }

            // Sütün adları
            String[] sutunAdlari = {"Firma İsmi", "Kullanıcı Adı", "Şifre"};

            // Bilgi Tablosunun oluşturulması
            JTable BilgiTablosu = new JTable(bilgi, sutunAdlari);
            BilgiTablosu.setBounds(30, 40, 200, 300);
            BilgiTablosu.setFocusable(false);
            BilgiTablosu.setRowSelectionAllowed(false);

            // Bilgi Tablosuna scroll eklenmesi
            JScrollPane scroll = new JScrollPane(BilgiTablosu);
            panel.add(scroll);


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true);


        }

    }

    // Company Class'ının Firma Eklerken Kullanılan Arayüz Classı
    static class Firmalara_Ekle extends JFrame {
        public Firmalara_Ekle() {
            // Firma Ekleme Menüsünün Arayüzünün oluşturulması
            setTitle("Firma Ekle");
            setSize(800, 600);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);

            // 1- Firma İsmi Alan Kısım
            JLabel firma_ismi_baslik = new JLabel("Firma İsmi Giriniz");
            firma_ismi_baslik.setBounds(20, 10, 200, 20);
            JTextField firmaIsmiGirdisi = new JTextField();
            firmaIsmiGirdisi.setBounds(20, 30, 250, 30);
            add(firma_ismi_baslik);
            add(firmaIsmiGirdisi);

            // 2- Firma Girişi için Kullanıcı Adı Alan Kısım
            JLabel firma_kullaniciAdi_baslik = new JLabel("Firma için Kullanıcı Adı Giriniz");
            firma_kullaniciAdi_baslik.setBounds(20, 70, 200, 20);
            JTextField firmaKullaniciAdiGirdisi = new JTextField();
            firmaKullaniciAdiGirdisi.setBounds(20, 90, 250, 30);
            add(firma_kullaniciAdi_baslik);
            add(firmaKullaniciAdiGirdisi);

            // 3- Firma Girişi için Şifre Alan Kısım
            JLabel firma_sifre_baslik = new JLabel("Firma için Şifre Giriniz");
            firma_sifre_baslik.setBounds(20, 130, 200, 20);
            JTextField firmaSifreGirdisi = new JTextField();
            firmaSifreGirdisi.setBounds(20, 150, 250, 30);
            add(firma_sifre_baslik);
            add(firmaSifreGirdisi);

            // 4- Geri Bildirim Yazısı
            JLabel geri_bildirim = new JLabel();
            Font geri_bildirim_font = geri_bildirim.getFont();
            geri_bildirim.setFont(geri_bildirim_font.deriveFont(geri_bildirim_font.getStyle() | Font.BOLD, 16));
            geri_bildirim.setBounds(30, 240, 360, 20);

            geri_bildirim.setVisible(false);
            panel.add(geri_bildirim);

            // 5- Onayla Butonu
            JButton onayla_butonu = new JButton("Eklemeyi Onayla");
            onayla_butonu.setBounds(50, 190, 180, 40);


            // Butona tıklanınca çalışacak kısım
            onayla_butonu.addActionListener(e -> {
                geri_bildirim.setVisible(false);
                // Geri Bildirim Almayı Sağlayan kısım
                try {
                    boolean firma_mevcut = false;
                    boolean kullaniciAdi_mevcut = false;
                    // Girdiler Boş Değilse çalışacak kısım
                    if (!firmaIsmiGirdisi.getText().isEmpty() && !firmaKullaniciAdiGirdisi.getText().isEmpty() && !firmaSifreGirdisi.getText().isEmpty()) {
                        for (Company firma : Transport.sirketler) {
                            // Girilen Firma ismi kayıtlı mı diye kontrol eden kısım
                            if (firmaIsmiGirdisi.getText().equals(firma.get_firma_isim())) {
                                firma_mevcut = true;
                            }
                            if (firmaKullaniciAdiGirdisi.getText().equals(firma.get_kullaniciAdi())) {
                                kullaniciAdi_mevcut = true;
                            }
                        }
                        // Firma mevcut değil ise ekleyen kısım
                        if (!firma_mevcut) {
                            if(!kullaniciAdi_mevcut){
                                Company yeni_firma = new Company(firmaIsmiGirdisi.getText(), firmaKullaniciAdiGirdisi.getText(), firmaSifreGirdisi.getText());
                                new Transport().FirmaListesineEkle(yeni_firma);
                                geri_bildirim.setText("Başarıyla Eklendi.");
                                geri_bildirim.setForeground(Color.GREEN);
                                geri_bildirim.setVisible(true);
                            }
                            else{
                                geri_bildirim.setText("Hata Oluştu! Kullanıcı Adı zaten mevcut!");
                                throw new Exception("Kullanıcı Adı zaten mevcut!");
                            }
                        }
                        else {
                            geri_bildirim.setText("Hata Oluştu! Firma zaten mevcut!");
                            throw new Exception("Firma zaten mevcut!");
                        }

                    } else {
                        geri_bildirim.setText("Hata Oluştu! Boş girdi Kısmı!");
                        throw new Exception("Girdilerde boş olan kısım var!");
                    }

                } catch (Exception ex) {
                    System.out.println("Hata alındı!");
                    geri_bildirim.setForeground(Color.RED);
                    geri_bildirim.setVisible(true);
                }

            });
            panel.add(onayla_butonu); // Panele eklenmesi


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true);
        }
    }

    // Company Class'ının Firma Silerken Kullanılan Arayüz Classı
    static class Firmalardan_Sil extends JFrame {
        public Firmalardan_Sil() {
            // Firma Ekleme Menüsünün Arayüzünün oluşturulması
            setTitle("Firma Ekle");
            setSize(800, 600);

            // Panelin oluşturulması
            JPanel panel = new JPanel();
            panel.setLayout(null);

            // 1- Silinecek firmanın isminin girileceği başlık
            JLabel silinecek_firma_isim_baslik = new JLabel("Silinecek Firma İsmini Giriniz");
            silinecek_firma_isim_baslik.setBounds(20, 10, 250, 30);
            panel.add(silinecek_firma_isim_baslik);

            // 2- Silinecek firmanın isim bilgisin alan kısım
            JTextField silinecek_firma_isim = new JTextField();
            silinecek_firma_isim.setBounds(20, 40, 150, 30);
            panel.add(silinecek_firma_isim);

            // 3- Geri Bildirim Yazısı
            JLabel geri_bildirim = new JLabel();
            Font geri_bildirim_font = geri_bildirim.getFont();
            geri_bildirim.setFont(geri_bildirim_font.deriveFont(geri_bildirim_font.getStyle() | Font.BOLD, 16));
            geri_bildirim.setBounds(30, 120, 300, 50);

            geri_bildirim.setVisible(false);
            panel.add(geri_bildirim);

            // 4- Firmanın silinmesini onaylayan buton
            JButton onayla_butonu = new JButton("Onayla ve Sil");
            onayla_butonu.setBounds(20, 80, 150, 30);
            // Butona basınca çalışacak kısım
            onayla_butonu.addActionListener(e -> {
                // Geri Bildirim Almayı Sağlayan kısım
                try {
                    boolean firma_var = false;
                    // Girdiler Boş Değilse çalışacak kısım
                    if (!silinecek_firma_isim.getText().isEmpty()){ // Girdi Kısmı dolu mu diye kontrol eden kısım
                        for (Company firma : Transport.sirketler){
                            if (silinecek_firma_isim.getText().equals(firma.get_firma_isim())){
                                new Transport().FirmaListesindenSil(firma);
                                firma_var = true;
                                break;
                            }
                        }
                        if (firma_var){
                            geri_bildirim.setText("Başarıyla Silindi.");
                            geri_bildirim.setForeground(Color.GREEN);
                            geri_bildirim.setVisible(true);
                        }
                        else {
                            geri_bildirim.setText("Hata Oluştu! Geçersiz Firma!");
                            throw new Exception("Girilen ad geçerli bir Firma adı Değil!");
                        }

                    } else {
                        geri_bildirim.setText("Hata Oluştu! Boş Girdi Kısmı!");
                        throw new Exception("Girdilerde boş olan kısım var!");
                    }

                } catch (Exception ex) {
                    System.out.println("Hata alındı!");
                    geri_bildirim.setForeground(Color.RED);
                    geri_bildirim.setVisible(true);
                }

            });

            panel.add(onayla_butonu);


            this.getContentPane().add(panel); // Oluşturulan içerikleri arayüze ekleyen kısım
            setVisible(true);
        }
    }

    // FİRMA PANELİNDE KULLANILACAK OLAN COMPANY CLASSLARI
    // Firma Panelinin Arayüzü
    static class Firma_Giris_Arayuz extends JFrame {

        // Admin Panel ekranı oluşturulduğunda çalışacak kod
        public Firma_Giris_Arayuz() {
            // Ana Menünün Arayüzünün oluşturulması
            setTitle("Firma Paneli");
            setSize(800, 600);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);


            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım



            setVisible(true); // Arayüzü görünür kılan metot
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot
        }
    }
}
