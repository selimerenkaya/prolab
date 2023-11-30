// Programlaba Laboratuvarı 2. Proje -- Rezervasyon Sistemi
//
// --- Projeyi Geliştirenler ---
// 230201127 Selim Eren Kaya
// 200201042 Fedai Engin Can Yılmaz

// Kullanılan Kütüphaneler
import javax.swing.*;
import java.lang.*;

// Giriş Ekranının arayüzü
class Arayuz extends JFrame {

    // Giriş Ekranı oluşturulduğunda çalışacak kod
    public Arayuz(){
        // Ana Menünün Arayüzünün oluşturulması
        setTitle("Giriş Ekranı");
        setSize(800, 600);


        // Panel ve Butonların oluşturulması
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);


        // Butonlar
        // 1- Admin Giriş Butonu
        JButton admin_giris_butonu = new JButton("Admin Girişi");
        admin_giris_butonu.setBounds(20, 20, 150, 50);

        // Butona tıklanınca çalışacak kısım
        admin_giris_butonu.addActionListener(e -> {
            dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            new Admin_Arayuz(); // Admin Panelinin gözükmesini sağlayan komut
        });
        panel.add(admin_giris_butonu); // Panele eklenmesi


        // 2- Firma Giriş Butonu
        JButton firma_giris_butonu = new JButton("Firma Girişi");
        firma_giris_butonu.setBounds(190, 20, 150, 50);

        // Butona tıklanınca çalışacak kısım
        firma_giris_butonu.addActionListener(e -> {
            dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            new Firma_Arayuz(); // Firma Panelinin gözükmesini sağlayan komut
        });
        panel.add(firma_giris_butonu); // Panele eklenmesi


        // 3- Bilet Ara Butonu
        JButton bilet_ara_butonu = new JButton("Bilet Ara");
        bilet_ara_butonu.setBounds(360, 20, 150, 50);

        // Butona tıklanınca çalışacak kısım
        bilet_ara_butonu.addActionListener(e -> {
            dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
            new Kullanici_Arayuz(); // Kullanıcı Panelinin gözükmesini sağlayan komut
        });
        panel.add(bilet_ara_butonu); // Panele eklenmesi


        this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım



        setVisible(true); // Arayüzü görünür kılan metot
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot

    }
}

// Admin Panelinin Arayüzü
class Admin_Arayuz extends JFrame {

    // Admin Panel ekranı oluşturulduğunda çalışacak kod
    public Admin_Arayuz() {
        // Ana Menünün Arayüzünün oluşturulması
        setTitle("Admin Paneli");
        setSize(800, 600);


        // Panel ve Butonların oluşturulması
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Giriş Bilgilerini alan kısım
        // 1- Kullanıcı adı
        JLabel kullanici_adi_baslik = new JLabel("Kullanıcı Adı");
        kullanici_adi_baslik.setBounds(20, 20, 100, 20);
        JTextField kullanici_adi = new JTextField();
        kullanici_adi.setBounds(20, 40, 200, 30);
        add(kullanici_adi_baslik);
        add(kullanici_adi);

        // 2- Şifre
        JLabel sifre_baslik = new JLabel("Şifre");
        sifre_baslik.setBounds(20, 80, 100, 20);
        JTextField sifre = new JTextField();
        sifre.setBounds(20, 100, 200, 30);
        add(sifre_baslik);
        add(sifre);

        // Giriş Yapılmasını Sağlayan Buton
        JButton giris_butonu = new JButton("Giriş Yap");
        giris_butonu.setBounds(50, 140, 130, 40);

        // Butona tıklanınca çalışacak kısım
        giris_butonu.addActionListener(e -> {
            Admin kayit_bilgisi_admin = new Admin();
            boolean giris_izni = kayit_bilgisi_admin.Giris(kullanici_adi.getText(), sifre.getText());
            if (giris_izni){
                System.out.println("Giriş başarılı");
            }
            else{
                System.out.println("Giriş başarısız.");
            }

            //dispose(); // Butona tıklanınca Giriş Ekranını kapatan komut
        });
        panel.add(giris_butonu); // Panele eklenmesi

        this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım



        setVisible(true); // Arayüzü görünür kılan metot
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Arayüzden çıkış yapmayı sağlayan metot
    }
}

// Firma Panelinin Arayüzü
class Firma_Arayuz extends JFrame {

    // Admin Panel ekranı oluşturulduğunda çalışacak kod
    public Firma_Arayuz() {
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

// Admin Panelinin Arayüzü
class Kullanici_Arayuz extends JFrame {

    // Admin Panel ekranı oluşturulduğunda çalışacak kod
    public Kullanici_Arayuz() {
        // Ana Menünün Arayüzünün oluşturulması
        setTitle("Kullanıcı Paneli");
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


public class Main {
    public static void main(String[] args) {

        // Giriş Ekranının main içinde çağrılması
        Arayuz giris_ekrani = new Arayuz();




    }
}

