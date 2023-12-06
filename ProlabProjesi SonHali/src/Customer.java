import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

// Müşteri Classı
public class Customer {
    // Müşterinin özellikleri
    String Ad;
    String Soyad;
    double TcKimlik;
    String DogumTarihi;

    Customer() {

    }

    // Customer Class'ının Constructu
    // Gerekli olan tüm bilgileri içermelidir
    Customer(String isim, String soyisim, double kimlik, String dogumGunu,
             String[] seferBilgiGirdi, Trip uygunSeferGirdi, Koltuk uygunKoltuk){
        Ad = isim;
        Soyad = soyisim;
        TcKimlik = kimlik;
        DogumTarihi = dogumGunu;
        new Customer.Rezerv_Etme(seferBilgiGirdi, uygunSeferGirdi, uygunKoltuk);
    }

    // Kullanıcı Panelinin Arayüzü
    static class Kullanici_Arayuz extends JFrame {

        // Kullanıcı Panel ekranı oluşturulduğunda çalışacak kod
        public Kullanici_Arayuz() {
            // Pencere Ayarları
            setTitle("Kullanıcı Paneli");
            setSize(650, 400);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setLayout(null);

            // 1- Sefer Aratma Bilgilerinin Başlığı (Kalkış Noktası, Varış Noktası ve Tarih)
            JLabel sefer_aratma_bilgileri_baslik = new JLabel("Kalkış Noktası, Varış Noktası, Tarih giriniz (Araya virgül koyarak)");
            sefer_aratma_bilgileri_baslik.setBounds(140, 30, 550, 30);
            panel.add(sefer_aratma_bilgileri_baslik);

            // 2- Sefer Aratma Bilgilerini Alan Kısım
            JTextField sefer_aratma_bilgileri = new JTextField();
            sefer_aratma_bilgileri.setBounds(190, 60, 210, 30);
            panel.add(sefer_aratma_bilgileri);

            // 3- Geri Bildirim Yazısı
            JLabel geri_bildirim = new JLabel();
            Font geri_bildirim_font = geri_bildirim.getFont();
            geri_bildirim.setFont(geri_bildirim_font.deriveFont(geri_bildirim_font.getStyle() | Font.BOLD, 16));
            geri_bildirim.setBounds(265, 200, 200, 20);

            geri_bildirim.setVisible(false);
            panel.add(geri_bildirim);

            // 4- Arama Yapılmasını Sağlayan Buton
            JButton arama_butonu = new JButton("Sefer Bul");
            arama_butonu.setBounds(230, 100, 130, 40);
            arama_butonu.setBackground(new Color(120, 130, 255));

            // Butona tıklanınca çalışacak kısım
            arama_butonu.addActionListener(e -> {
                geri_bildirim.setVisible(false);
                geri_bildirim.setText("Hata Alındı!");
                // Geri Bildirim Almayı Sağlayan kısım
                try {
                    if(!sefer_aratma_bilgileri.getText().isEmpty()) {
                        String[] bilgi = sefer_aratma_bilgileri.getText().split(",");
                        if (bilgi.length >= 4) {
                            geri_bildirim.setText("Fazla Parametre Girildi!");
                            throw new Exception("Fazla Parametre Girdisi!");
                        }

                        // Aratma Bilgilerini Alan Kısım
                        String kalkis = bilgi[0].strip(), varis = bilgi[1].strip(), zaman = bilgi[2].strip();


                        // Uygun Seferleri Gösteren Arayüzün Çağrılması
                        new Customer.Uygun_Seferler(kalkis, varis, zaman);


                    }
                    else {
                        geri_bildirim.setText("Boş Girdi!");
                        throw new Exception("Boş Girdi!");
                    }

                } catch (Exception ex) {
                    System.out.println("Hata alındı!" + ex);
                    geri_bildirim.setForeground(Color.RED);
                    geri_bildirim.setVisible(true);
                }

            });
            panel.add(arama_butonu); // Panele eklenmesi

            // 5- Ana Menüye Geri Döndüren Buton
            JButton ana_menu_butonu = new JButton("Ana Menüye Geri Dön");
            ana_menu_butonu.setBounds(210, 150, 180, 40);
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


    // Uygun Seferler Arayüzü
    static class Uygun_Seferler extends JFrame {

        // Uygun Seferler Arayüzü
        public Uygun_Seferler(String kalkisGirdi, String varisGirdi, String zamanGirdi) {
            // Pencere Ayarları
            setTitle("Uygun Sefeler");
            setSize(1600, 800);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel(new GridLayout(4, 40));
            panel.setBackground(Color.white);


            // Aynı anda doldurularak aynı şirketin özelliklerini temsil edecekler
            ArrayList<Company> uygunSirketler = new ArrayList<>();
            ArrayList<Trip> uygunSeferler = new ArrayList<>();

            // Uygun Şirket ve Seferlerin Belirlenmesi
            Transport bilgiDeposu = new Transport();
            ArrayList<Company> butunSirketler = bilgiDeposu.FirmaBilgileriniDondur();
            for(Company sirket : butunSirketler) {
                ArrayList<Trip> seferler = sirket.get_seferBilgileri();
                for(Trip kontrol_sefer : seferler) {
                    String kalkis_noktasi = kontrol_sefer.get_guzergah().get_guzergah()[0];
                    String varis_noktasi = kontrol_sefer.get_guzergah().
                            get_guzergah()[kontrol_sefer.get_guzergah().get_guzergah().length - 1];
                    String zaman = kontrol_sefer.get_zaman();

                    if (kalkis_noktasi.equals(kalkisGirdi) && varis_noktasi.equals(varisGirdi)
                    && zaman.equals(zamanGirdi)) {
                        uygunSeferler.add(kontrol_sefer);
                        uygunSirketler.add(sirket);
                    }
                }
            }

            // Tablo Bilgilerini Tutan kısım
            String[][] bilgi = {};
            int sefer_numara = 1;

            // Tablo Oluşturulması
            for (int ortak_index = 0; ortak_index < uygunSirketler.size(); ortak_index++) {
                // Tabloda kullanılacak Bilgileri Alan Kısım
                Class arac_sinif = uygunSeferler.get(ortak_index).get_arac().getClass();
                switch (arac_sinif.getName()) {
                    case "Bus" -> {
                        Bus sefer_arac = (Bus) uygunSeferler.get(ortak_index).get_arac();

                        String firma_isim = uygunSirketler.get(ortak_index).get_firma_isim();
                        String arac_id = sefer_arac.get_arac_id();
                        String ulasim_turu = uygunSeferler.get(ortak_index).get_guzergah().get_ulasim_turu();
                        String kalkis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().get_guzergah()[0];
                        String varis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().
                                get_guzergah()[uygunSeferler.get(ortak_index).get_guzergah().get_guzergah().length - 1];
                        String tarih = uygunSeferler.get(ortak_index).get_zaman();
                        String sefer_no = Integer.toString(sefer_numara);


                        String[] tablo_bilgisi = {sefer_no, firma_isim, arac_id, ulasim_turu, kalkis_noktasi, varis_noktasi,
                        tarih};



                        bilgi = Arrays.copyOf(bilgi, bilgi.length + 1);
                        bilgi[bilgi.length - 1] = tablo_bilgisi;

                        sefer_numara++;


                    }
                    case "Train" -> {
                        Train sefer_arac = (Train) uygunSeferler.get(ortak_index).get_arac();

                        String firma_isim = uygunSirketler.get(ortak_index).get_firma_isim();
                        String arac_id = sefer_arac.get_arac_id();
                        String ulasim_turu = uygunSeferler.get(ortak_index).get_guzergah().get_ulasim_turu();
                        String kalkis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().get_guzergah()[0];
                        String varis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().
                                get_guzergah()[uygunSeferler.get(ortak_index).get_guzergah().get_guzergah().length - 1];
                        String tarih = uygunSeferler.get(ortak_index).get_zaman();
                        String sefer_no = Integer.toString(sefer_numara);


                        String[] tablo_bilgisi = {sefer_no, firma_isim, arac_id, ulasim_turu, kalkis_noktasi, varis_noktasi,
                                tarih};



                        bilgi = Arrays.copyOf(bilgi, bilgi.length + 1);
                        bilgi[bilgi.length - 1] = tablo_bilgisi;
                        sefer_numara++;


                    }
                    case "Airplane" -> {
                        Airplane sefer_arac = (Airplane) uygunSeferler.get(ortak_index).get_arac();

                        String firma_isim = uygunSirketler.get(ortak_index).get_firma_isim();
                        String arac_id = sefer_arac.get_arac_id();
                        String ulasim_turu = uygunSeferler.get(ortak_index).get_guzergah().get_ulasim_turu();
                        String kalkis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().get_guzergah()[0];
                        String varis_noktasi = uygunSeferler.get(ortak_index).get_guzergah().
                                get_guzergah()[uygunSeferler.get(ortak_index).get_guzergah().get_guzergah().length - 1];
                        String tarih = uygunSeferler.get(ortak_index).get_zaman();
                        String sefer_no = Integer.toString(sefer_numara);


                        String[] tablo_bilgisi = {sefer_no, firma_isim, arac_id, ulasim_turu, kalkis_noktasi, varis_noktasi,
                                tarih};




                        bilgi = Arrays.copyOf(bilgi, bilgi.length + 1);
                        bilgi[bilgi.length - 1] = tablo_bilgisi;
                        sefer_numara++;


                    }
                }

            }

            // Sütün adları
            String[] sutunAdlari = {"Sefer Numarası", "Firma", "Araç İD", "Ulaşım Türü", "Kalkış Noktası", "Varış Noktası",
            "Tarih"};

            // Bilgi Tablosunun oluşturulması
            JTable BilgiTablosu = new JTable(bilgi, sutunAdlari);
            //BilgiTablosu.setBounds(0, 40, 1350, 300);
            BilgiTablosu.setFocusable(false);
            BilgiTablosu.setRowSelectionAllowed(false);
            BilgiTablosu.setBackground(Color.white);
            BilgiTablosu.setRowHeight(30);

            BilgiTablosu.setPreferredScrollableViewportSize(BilgiTablosu.getPreferredSize());
            BilgiTablosu.setFillsViewportHeight(true);

            // Bilgi Tablosuna scroll eklenmesi
            JScrollPane scroll = new JScrollPane(BilgiTablosu);
            //scroll.setPreferredSize(new Dimension(1350, 400));
            panel.add(scroll);



            // 1- Sefer Seçme Bilgilerinin Başlığı (Sefer Numarası)
            JLabel sefer_sec_baslik = new JLabel("Sefer Numarası giriniz");
            //sefer_aratma_bilgileri_baslik.setBounds(140, 670, 550, 30);
            panel.add(sefer_sec_baslik);

            // 2- Sefer Seçme Bilgilerini Alan Kısım
            JTextField sefer_secme_bilgileri = new JTextField();
            //sefer_aratma_bilgileri.setBounds(190, 700, 210, 30);
            panel.add(sefer_secme_bilgileri);

            // 3- Seferi Seç ve Onayla
            JButton sefer_sec_onayla_buton = new JButton("Seferi Seç ve Onayla");
            sefer_sec_onayla_buton.setBackground(new Color(120, 130, 255));
            // Butona basınca çalışacak kısım
            int finalSefer_numara = sefer_numara;
            String[][] finalBilgi = bilgi;
            sefer_sec_onayla_buton.addActionListener(e -> {
                try {
                    if (!sefer_secme_bilgileri.getText().isEmpty())
                    {
                        if (finalSefer_numara > Integer.parseInt(sefer_secme_bilgileri.getText()) &&
                                Integer.parseInt(sefer_secme_bilgileri.getText()) > 0)
                        {
                            String[] kullanilacakBilgi = finalBilgi[Integer.parseInt(sefer_secme_bilgileri.getText()) - 1];
                            new Customer.Uygun_Arac(kullanilacakBilgi);


                        }
                        else{
                            throw new Exception("Hatalı Girdi Değeri!");
                        }

                    }
                    else {
                        throw new Exception("Girdi Boş!");
                    }
                }
                catch (Exception ex) {
                    System.out.println("Hata Alındı! " + ex);
                }

            });
            panel.add(sefer_sec_onayla_buton);




            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true); // Arayüzü görünür kılan metot

        }
    }

    // Uygun Araç Arayüzü
    static class Uygun_Arac extends JFrame {

        // Uygun Araç Arayüzü
        public Uygun_Arac(String[] seferBilgiGirdi) {
            // Pencere Ayarları
            setTitle("Uygun Araç");
            setSize(1600, 800);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel(new GridLayout(4, 40));
            panel.setBackground(Color.white);

            // İstenilen Sefer Bilgilerini Tutan Kısım
            // Sefer Numarası, Firma İsim, Araç İD, Ulaşım Türü, Kalkış Noktası, Varış Noktası, Tarih
            String[] seferBilgi = seferBilgiGirdi;

            // ------- Uygun Seferi Bulan Kısım -------

            // 1- Uygun Şirketin Bulunması
            ArrayList<Company> butunSirketler = new Transport().FirmaBilgileriniDondur();
            Company uygun_sirket = new Company();
            for(Company sirket : butunSirketler)
            {
                if (sirket.get_firma_isim().equals(seferBilgi[1])){
                    uygun_sirket = sirket;
                    break;
                }
            }

            // Uygun Seferi bulan Kısım
            ArrayList<Trip> butunSeferler = uygun_sirket.get_seferBilgileri();
            Trip uygun_sefer = new Trip();
            label:
            for(Trip sefer : butunSeferler){
                // Tarih, Kalkış Noktası ve Varış Noktasını kontrol eden kısım
                if (sefer.get_zaman().equals(seferBilgi[6])
                && sefer.get_guzergah().get_guzergah()[0].equals(seferBilgi[4])
                && sefer.get_guzergah().get_guzergah()[sefer.get_guzergah().get_guzergah().length - 1].equals(seferBilgi[5]))
                {
                    Class arac_sinif = sefer.get_arac().getClass();
                    switch (arac_sinif.getName()) {
                        case "Bus" -> {
                            Bus sefer_arac = (Bus) sefer.get_arac();
                            if (sefer_arac.get_arac_id().equals(seferBilgi[2])) {
                                uygun_sefer = sefer;
                                break label;
                            }

                        }
                        case "Train" -> {
                            Train sefer_arac = (Train) sefer.get_arac();
                            if (sefer_arac.get_arac_id().equals(seferBilgi[2])) {
                                uygun_sefer = sefer;
                                break label;
                            }

                        }
                        case "Airplane" -> {
                            Airplane sefer_arac = (Airplane) sefer.get_arac();
                            if (sefer_arac.get_arac_id().equals(seferBilgi[2])) {
                                uygun_sefer = sefer;
                                break label;
                            }

                        }
                    }

                }
            }

            // Tablo Bilgilerini Tutan kısım
            String[][] bilgi = {};
            int sefer_numara = 1;

            // Tablo Oluşturulması
            for(Koltuk seferKoltuk : uygun_sefer.getKoltuklar()) {
                // Koltuk Dolu ise Çalışacak Kısım
                String koltuk_numara = Integer.toString(seferKoltuk.getKoltuk_numarasi());
                String koltuk_durum = seferKoltuk.getKoltuk_durumu().toUpperCase();
                String[] koltuk_bilgisi = { koltuk_numara, koltuk_durum};


                bilgi = Arrays.copyOf(bilgi, bilgi.length + 1);
                bilgi[bilgi.length - 1] = koltuk_bilgisi;
            }


            // Sütün adları
            String[] sutunAdlari = {"Koltuk Numarasi", "Koltuk Durumu"};

            // Bilgi Tablosunun oluşturulması
            JTable BilgiTablosu = new JTable(bilgi, sutunAdlari);
            //BilgiTablosu.setBounds(0, 40, 1350, 300);
            BilgiTablosu.setFocusable(false);
            BilgiTablosu.setRowSelectionAllowed(false);
            BilgiTablosu.setBackground(Color.white);
            BilgiTablosu.setRowHeight(30);

            BilgiTablosu.setPreferredScrollableViewportSize(BilgiTablosu.getPreferredSize());
            BilgiTablosu.setFillsViewportHeight(true);

            // Bilgi Tablosuna scroll eklenmesi
            JScrollPane scroll = new JScrollPane(BilgiTablosu);
            //scroll.setPreferredSize(new Dimension(1350, 400));
            panel.add(scroll);



            // 1- Koltuk Seçme Bilgilerinin Başlığı (Koltuk Numarası)
            JLabel koltuk_sec_baslik = new JLabel("Koltuk Numarası giriniz (Birden fazla girilecekse virgül ile ayırınız)");
            panel.add(koltuk_sec_baslik);

            // 2- Koltuk Seçme Bilgilerini Alan Kısım
            JTextField koltuk_secme_bilgileri = new JTextField();
            panel.add(koltuk_secme_bilgileri);

            // 3- Onayla Butonu
            JButton onayla_butonu = new JButton("Onayla ve Satın Al");
            onayla_butonu.setBackground(new Color(130, 85, 240));
            Trip finalUygun_sefer = uygun_sefer;
            onayla_butonu.addActionListener(e -> {
                try {
                    if (!koltuk_secme_bilgileri.getText().isEmpty())
                    {
                        // Rezerve Edilmek İstenilen Koltuk Numaraları Tutmayı Sağlayan kısım
                        String[] secilenKoltuklar = koltuk_secme_bilgileri.getText().split(",");
                        ArrayList<Integer> koltukNumaralari = new ArrayList<>(); // Rezerve Edilmek istenilen koltuk numaralari
                        for (String koltuk : secilenKoltuklar) {
                            koltukNumaralari.add(Integer.parseInt(koltuk.strip()));
                        }

                        // Koltuk Numaraları Uygun Aralıkta mı kontrol eden kısım
                        boolean gecerli_koltuk_no;
                        for(Integer koltukNo : koltukNumaralari) {
                            gecerli_koltuk_no = false;
                            for(Koltuk seferKoltuk : finalUygun_sefer.getKoltuklar()) {
                                if (koltukNo == seferKoltuk.getKoltuk_numarasi() ) {
                                    gecerli_koltuk_no = true;
                                }
                            }
                            // Koltuk Numarası Geçersiz ise Çalışacak Kısım
                            if (!gecerli_koltuk_no) {
                                throw new Exception("Geçersiz Koltuk Numarası!");
                            }
                        }


                        // Koltuklar Dolu mu kontrol eden kısım
                        ArrayList<Koltuk> uygunKoltuklar = new ArrayList<>();
                        for (Integer koltukNo : koltukNumaralari) {
                            for(Koltuk seferKoltuk : finalUygun_sefer.getKoltuklar()) {
                                if (koltukNo == seferKoltuk.getKoltuk_numarasi() ) {
                                    // Koltuk Dolu İse Uyarı veren kısım
                                    if (seferKoltuk.getKoltuk_durumu().equals("Dolu")) {
                                        throw new Exception("Dolu Koltuk Seçilmiş!");
                                    }
                                    else {
                                        uygunKoltuklar.add(seferKoltuk);
                                    }
                                }
                            }
                        }

                        // Koltuklarla İşlem Yapan Kısım
                        new Customer.Odeme_Arayuz(seferBilgi, finalUygun_sefer, uygunKoltuklar);



                    }
                    else {
                        throw new Exception("Girdi boş!");
                    }
                }
                catch (Exception ex) {
                    System.out.println("Hata Alındı! " + ex);
                }

            });

            panel.add(onayla_butonu);




            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true); // Arayüzü görünür kılan metot

        }
    }

    // Odeme Bilgisi Arayüzü
    static class Odeme_Arayuz extends JFrame {

        // Odeme Bilgisi Arayüzü
        public Odeme_Arayuz(String[] seferBilgiGirdi, Trip uygunSeferGirdi, ArrayList<Koltuk> uygunKoltuklarGirdi) {
            // Pencere Ayarları
            setTitle("Ödeyecek Kişi Bilgiler");
            setSize(800, 600);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.white);

            // Rezervasyon Sırasında Kullanılacak Bilgiler
            String[] seferBilgiler = seferBilgiGirdi;
            Trip uygunSefer = uygunSeferGirdi;
            ArrayList<Koltuk> uygunKoltuklar = uygunKoltuklarGirdi;


            // İSİM, SOYİSİM, KİMLİK, DOĞUM TARİHİ
            // 1- İsim
            JLabel isim_baslik = new JLabel("İsminizi Giriniz");
            isim_baslik.setBounds(20, 20, 300, 30);
            panel.add(isim_baslik);

            // 2- İsim Alan Kısım
            JTextField isim = new JTextField();
            isim.setBounds(20, 50, 300, 30);
            panel.add(isim);

            // 3- Soyisim
            JLabel soyisim_baslik = new JLabel("Soyisminizi Giriniz");
            soyisim_baslik.setBounds(20, 90, 300, 30);
            panel.add(soyisim_baslik);

            // 4- Soyisim Alan Kısım
            JTextField soyisim = new JTextField();
            soyisim.setBounds(20, 120, 300, 30);
            panel.add(soyisim);

            // 5- Kimlik
            JLabel kimlik_baslik = new JLabel("Tc Kimlik No Giriniz");
            kimlik_baslik.setBounds(20, 160, 300, 30);
            panel.add(kimlik_baslik);

            // 6- Kimlik Alan Kısım
            JTextField kimlik = new JTextField();
            kimlik.setBounds(20, 190, 300, 30);
            panel.add(kimlik);

            // 7- Doğum Tarihi
            JLabel dogum_tarih_baslik = new JLabel("Doğum Tarihi Giriniz");
            dogum_tarih_baslik.setBounds(20, 230, 300, 30);
            panel.add(dogum_tarih_baslik);

            // 8- Doğum Tarihi Alan Kısım
            JTextField dogum_tarih = new JTextField();
            dogum_tarih.setBounds(20, 260, 300, 30);
            panel.add(dogum_tarih);



            // 9- Onayla Butonu
            JButton onayla_butonu = new JButton("Bilgileri Onayla");
            onayla_butonu.setBounds(50, 300, 200, 30);
            onayla_butonu.setBackground(new Color(130, 85, 240));
            onayla_butonu.addActionListener(e -> {
                try {
                    if (!isim.getText().isEmpty() && !soyisim.getText().isEmpty()
                    && !kimlik.getText().isEmpty() && !dogum_tarih.getText().isEmpty()) {
                        // Her bir koltuk için rezerve menüsü açan kısım
                        for(Koltuk rezervKoltuk : uygunKoltuklar) {
                            Koltuk uygun_koltuk = rezervKoltuk;
                            new Customer(isim.getText(), soyisim.getText(), Double.parseDouble(kimlik.getText()) , dogum_tarih.getText(),
                                    seferBilgiler, uygunSefer, uygun_koltuk);
                        }
                        dispose();
                    }
                    else {
                        throw new Exception("Girdilerde boşluk var!");
                    }

                }
                catch (Exception ex) {
                    System.out.println("Hata Alındı! " + ex);
                }

            });

            panel.add(onayla_butonu);




            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true); // Arayüzü görünür kılan metot

        }
    }


    // Uygun Araç Arayüzü
    static class Rezerv_Etme extends JFrame {

        // Uygun Araç Arayüzü
        public Rezerv_Etme(String[] seferBilgiGirdi, Trip uygunSeferGirdi, Koltuk uygunKoltukGirdi) {
            // Pencere Ayarları
            setTitle("Yolcu Bilgileri");
            setSize(800, 600);


            // Panel ve Butonların oluşturulması
            // Panel
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.white);

            // Rezervasyon Sırasında Kullanılacak Bilgiler
            String[] seferBilgiler = seferBilgiGirdi;
            Trip uygunSefer = uygunSeferGirdi;
            Koltuk uygunKoltuk = uygunKoltukGirdi;


            // İSİM, SOYİSİM, KİMLİK, DOĞUM TARİHİ
            // 1- İsim
            JLabel isim_baslik = new JLabel("İsminizi Giriniz");
            isim_baslik.setBounds(20, 20, 300, 30);
            panel.add(isim_baslik);

            // 2- İsim Alan Kısım
            JTextField isim = new JTextField();
            isim.setBounds(20, 50, 300, 30);
            panel.add(isim);

            // 3- Soyisim
            JLabel soyisim_baslik = new JLabel("Soyisminizi Giriniz");
            soyisim_baslik.setBounds(20, 90, 300, 30);
            panel.add(soyisim_baslik);

            // 4- Soyisim Alan Kısım
            JTextField soyisim = new JTextField();
            soyisim.setBounds(20, 120, 300, 30);
            panel.add(soyisim);


            // 5- Onayla Butonu
            JButton onayla_butonu = new JButton("Bilgileri Onayla");
            onayla_butonu.setBounds(50, 300, 200, 30);
            onayla_butonu.setBackground(new Color(130, 85, 240));
            onayla_butonu.addActionListener(e -> {
                try {
                    if (!isim.getText().isEmpty() && !soyisim.getText().isEmpty())
                    {
                        // Rezerve etme kısmı için gerekli bilgilerin atandığı kısım
                        // Rezerve edilecek firma bilgisinin atanması
                        String firma_isim = seferBilgiler[1];
                        Company firma = new Company();
                        ArrayList<Company> butunSirketler = new Transport().FirmaBilgileriniDondur();
                        for (Company sirket : butunSirketler) {
                            if (firma_isim.equals(sirket.get_firma_isim())) {
                                firma = sirket;
                                break;
                            }
                        }

                        // Yolcu, Tarih ve Koltuk Numarası bilgilerinin atanması
                        Passenger yolcu = new Passenger(isim.getText(), soyisim.getText());
                        String zaman = uygunSefer.get_zaman();
                        int koltukNumara = uygunKoltuk.getKoltuk_numarasi();

                        // Araç bilgisinin atanması
                        Object arac = null;
                        ArrayList<Object> butunAraclar = firma.get_aracBilgileri();
                        for (Object tempArac : butunAraclar) {
                            Class arac_sinif = tempArac.getClass();
                            switch (arac_sinif.getName()) {
                                case "Bus" -> {
                                    Bus sefer_arac = (Bus) tempArac;
                                    if (sefer_arac.get_arac_id().equals(seferBilgiler[2])) {
                                        arac = sefer_arac;
                                    }

                                }
                                case "Train" -> {
                                    Train sefer_arac = (Train) tempArac;
                                    if (sefer_arac.get_arac_id().equals(seferBilgiler[2])) {
                                        arac = sefer_arac;
                                    }


                                }
                                case "Airplane" -> {
                                    Airplane sefer_arac = (Airplane) tempArac;
                                    if (sefer_arac.get_arac_id().equals(seferBilgiler[2])) {
                                        arac = sefer_arac;
                                    }


                                }
                            }
                        }

                        Reservation yeni_rezervasyon = new Reservation(yolcu, arac, koltukNumara, zaman);
                        new Transport().koltuk_rezervasyon(firma, yeni_rezervasyon);
                        dispose();



                    }
                    else {
                        throw new Exception("Girdide boşluk var!");
                    }

                }
                catch (Exception ex) {
                    System.out.println("Hata Alındı! " + ex);
                }

            });

            panel.add(onayla_butonu);




            this.getContentPane().add(panel); // Oluşturulan içeriklerin panele ekleyen kısım
            setVisible(true); // Arayüzü görünür kılan metot

        }
    }

}
