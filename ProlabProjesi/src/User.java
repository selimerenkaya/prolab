import javax.swing.*;
import java.awt.*;
import java.sql.*;

// Sistemdeki kullanıcıları temsil eden class
// Abstract bir class
// 2 türe ayrılır
// 1- Admin
// 2- Company
interface ILoginable {
    // Girişleri kontrol eden metot
    boolean Giris(String kullaniciAdiGirdisi, String sifreGirdisi);
}

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
            JTextField sifreGirdisi = new JTextField();
            sifreGirdisi.setBounds(20, 100, 200, 30);
            add(sifre_baslik);
            add(sifreGirdisi);

            // Giriş Yapılmasını Sağlayan Buton
            JButton giris_butonu = new JButton("Giriş Yap");
            giris_butonu.setBounds(50, 140, 130, 40);

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

                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(giris_butonu); // Panele eklenmesi

            // Ana Menüye Geri Döndüren Buton
            JButton ana_menu_butonu = new JButton("Ana Menüye Geri Dön");
            ana_menu_butonu.setBounds(50, 190, 180, 40);

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

            // 1- Firmaların Görüntülenmesini Sağlayan Buton
            JButton firmalari_goruntule = new JButton("Firmaları Görüntüle");
            firmalari_goruntule.setBounds(50, 20, 190, 40);

            // Butona tıklanınca çalışacak kısım
            firmalari_goruntule.addActionListener(e -> {
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(firmalari_goruntule); // Panele eklenmesi

            // 2- Firmalara Yeni Firma Kaydı yapılmasını Sağlayan Buton
            JButton yeni_firma_ekle = new JButton("Yeni Firma Kaydı Yap");
            yeni_firma_ekle.setBounds(50, 70, 190, 40);

            // Butona tıklanınca çalışacak kısım
            yeni_firma_ekle.addActionListener(e -> {
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(yeni_firma_ekle); // Panele eklenmesi

            // 3- Firmalardan Firma Kaydı silinmesini Sağlayan Buton
            JButton firma_kayit_sil = new JButton("Firma Kaydı Sil");
            firma_kayit_sil.setBounds(50, 120, 190, 40);

            // Butona tıklanınca çalışacak kısım
            firma_kayit_sil.addActionListener(e -> {
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(firma_kayit_sil); // Panele eklenmesi

            // 3- Hizmet Bedeli Belirlenmesini Sağlayan Buton
            JButton hizmet_bedeli_belirle = new JButton("Hizmet Bedeli Belirle");
            hizmet_bedeli_belirle.setBounds(50, 170, 190, 40);

            // Butona tıklanınca çalışacak kısım
            hizmet_bedeli_belirle.addActionListener(e -> {
                new Company.Hizmet_Bedeli_Arayuzu();
                //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            });
            panel.add(hizmet_bedeli_belirle); // Panele eklenmesi

            // 4- Ana Menüye Geri Döndüren Buton
            JButton ana_menu_butonu = new JButton("Çıkış Yap");
            ana_menu_butonu.setBounds(50, 220, 180, 40);

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



// User Class'ının Company Classı
class Company extends User {
    static int hizmet_bedeli = 1000; // TL bazında

    // Hizmet bedeli değişkeni için Get/Set metotları
    public int get_hizmet_bedeli() {
        return hizmet_bedeli;
    }

    public void set_hizmet_bedeli(int hizmet_bedeli_girdisi) {
        hizmet_bedeli = hizmet_bedeli_girdisi;
    }

    // Company Parametresiz Constructor Metodu
    public Company(){

    }

    // Company Classı Contruct Edilirken kullanılacak metot
    Company(String kullaniciAdiDegiskeni, String sifreDegiskeni) {
        super(kullaniciAdiDegiskeni, sifreDegiskeni);
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

                }
                catch (Exception ex){
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
            // Firmaların Bulunudğu Database ile bağlantı kuran kısım

        }


    }


}