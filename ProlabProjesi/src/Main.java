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
            new Admin.Admin_Giris_Arayuz(); // Admin Giriş Panelinin gözükmesini sağlayan komut
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

// Kullanıcı Panelinin Arayüzü
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

