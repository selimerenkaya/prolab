import java.util.ArrayList;
import java.util.Arrays;

interface Ireservable{

}
// Firma Bilgilerini, Araçları ve Seyahat bilgileri gibi bir sürü bilgiyi barındıran Class
public class Transport implements Ireservable {
    // Şirketleri (Firmalar) Tutan ArrayList
    static ArrayList<Company> sirketler = new ArrayList<>();

    // Güzergahları Tutan ArrayList
    static ArrayList<Route> guzergahListesi = new ArrayList<>();

    // Ödevde daha önceden verilmiş şirketlerin bilgileriyle beraber oluşturulması
    public void HazirBilgileriOlustur() {
        Transport hazirBilgiler = new Transport();
        // NOT!
        // Şehirler arası sayılar her birinin arasındaki mesafeyi belli eder
        // Gidilecek toplam yol hesaplanırken for döngüsü içinde
        // Kalkış ve varış noktası bulunur. Eğer düz istikamettte yoksa
        // Ters şekilde aratılır. Bulunduktan sonra şehirlerin arasında kalan her mesafe
        // Toplanır ve toplam gidilecek mesafe hesaplanır müşteri için. Buna göre de
        // fiyat belirlenir ve sunulur.
        //
        //
        // Demiryolu Güzergahları
        String[] guzer_1 = {"İstanbul", "75", "Kocaeli", "75" ,"Bilecik", "75", "Eskişehir", "150", "Ankara"};
        String[] guzer_2 = {"İstanbul", "75", "Kocaeli", "75", "Bilecik", "75", "Eskişehir", "225", "Konya"};

        // Karayolu Güzergahları
        String[] guzer_3 = {"İstanbul", "100", "Kocaeli", "400", "Ankara"};
        String[] guzer_4 = {"İstanbul", "100", "Kocaeli", "200",  "Eskişehir", "300", "Konya"};

        // Havayolu Güzergahları
        String[] guzer_5 = {"İstanbul", "300", "Konya"};
        String[] guzer_6 = {"İstanbul", "250", "Ankara"};

        // Güzergahların fiyat bilgilerinin oluşturulması
        // Şehir - Fiyat - Şehir
        // Demiryolu Fiyatları
        String[][] demiryolu_fiyat = {{"İstanbul", "50", "Kocaeli"}, {"İstanbul", "150", "Bilecik"},
                {"İstanbul", "250", "Ankara"}, {"İstanbul", "200", "Eskişehir"}, {"İstanbul", "300", "Konya"}
                , {"Kocaeli", "50", "Bilecik"}, {"Kocaeli", "200", "Ankara"}, {"Kocaeli", "100", "Eskişehir"}
                , {"Kocaeli", "250", "Konya"}, {"Bilecik", "150", "Ankara"}, {"Bilecik", "50", "Eskişehir"}
                , {"Bilecik", "200", "Konya"}, {"Ankara", "100", "Eskişehir"}, {"Eskişehir", "150", "Konya"}};

        // Karayolu Fiyatları
        String[][] karayolu_fiyat = {{"İstanbul", "50", "Kocaeli"}, {"İstanbul", "300", "Ankara"}
                , {"İstanbul", "150", "Eskişehir"}, {"İstanbul", "300", "Konya"}
                , {"Kocaeli", "400", "Ankara"}, {"Kocaeli", "100", "Eskişehir"}
                , {"Kocaeli", "250", "Konya"}, {"Eskişehir", "150", "Konya"}};

        // Havayolu Fiyatları
        String[][] havayolu_fiyat = {{"İstanbul", "1000", "Ankara"}, {"İstanbul", "1200", "Konya"}};


        // Güzergahların oluşturulması
        Route guzergah_1 = new Route(guzer_1, "Demiryolu", demiryolu_fiyat);
        Route guzergah_2 = new Route(guzer_2, "Demiryolu", demiryolu_fiyat);
        Route guzergah_3 = new Route(guzer_3, "Karayolu", karayolu_fiyat);
        Route guzergah_4 = new Route(guzer_4, "Karayolu", karayolu_fiyat);
        Route guzergah_5 = new Route(guzer_5, "Havayolu", havayolu_fiyat);
        Route guzergah_6 = new Route(guzer_6, "Havayolu", havayolu_fiyat);

        // Güzergahların güzergah listesine eklenmesi
        hazirBilgiler.GuzergahListesineEkle(guzergah_1, guzergah_2, guzergah_3, guzergah_4, guzergah_5, guzergah_6);


        // Araçların Oluşturulması
        // A firmasının Araçları
        Bus A_arac_1 = new Bus("Otobüs 1", "Benzin", 10,
                20, 5000, 2000);
        Bus A_arac_2 = new Bus("Otobüs 2", "Benzin", 10,
                15, 5000, 2000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> A_aracListesi = new ArrayList<>();
        A_aracListesi.add(A_arac_1);
        A_aracListesi.add(A_arac_2);


        // B firmasının Araçları
        Bus B_arac_1 = new Bus("Otobüs 1", "Motorin", 5,
                15, 3000, 1500);
        Bus B_arac_2 = new Bus("Otobüs 2", "Motorin", 5,
                20, 3000, 1500);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> B_aracListesi = new ArrayList<>();
        B_aracListesi.add(B_arac_1);
        B_aracListesi.add(B_arac_2);


        // C firmasının Araçları
        Bus C_arac_1 = new Bus("Otobüs 1", "Motorin", 6,
                20, 4000, 2000);
        Airplane C_arac_2 = new Airplane("Uçak 1", "Gaz", 25,
                30, 10000, 6000);
        Airplane C_arac_3 = new Airplane("Uçak 2", "Gaz", 25,
                30, 10000, 6000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> C_aracListesi = new ArrayList<>();
        C_aracListesi.add(C_arac_1);
        C_aracListesi.add(C_arac_2);
        C_aracListesi.add(C_arac_3);


        // D firmasının Araçları
        Train D_arac_1 = new Train("Tren 1", "Elektrik", 3,
                25, 2000, 1000);
        Train D_arac_2 = new Train("Tren 2", "Elektrik", 3,
                25, 2000, 1000);
        Train D_arac_3 = new Train("Tren 3", "Elektrik", 3,
                25, 2000, 1000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> D_aracListesi = new ArrayList<>();
        D_aracListesi.add(D_arac_1);
        D_aracListesi.add(D_arac_2);
        D_aracListesi.add(D_arac_3);


        // F firmasının Araçları
        Airplane F_arac_1 = new Airplane("Uçak 1", "Gaz", 20,
                30, 7500, 4000);
        Airplane F_arac_2 = new Airplane("Uçak 2", "Gaz", 20,
                30, 7500, 4000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> F_aracListesi = new ArrayList<>();
        F_aracListesi.add(F_arac_1);
        F_aracListesi.add(F_arac_2);


        // Seferlerin Oluşturulması

        // A Firmasının Araçlarına ait seferlerin oluşturulması
        // 1. Araç
        Trip A_sefer_1 = new Trip(A_arac_1, guzergah_3, "04/12/2023");
        Trip A_sefer_2 = new Trip(A_arac_1, guzergah_3, "05/12/2023");
        Trip A_sefer_3 = new Trip(A_arac_1, guzergah_3, "06/12/2023");
        Trip A_sefer_4 = new Trip(A_arac_1, guzergah_3, "07/12/2023");
        Trip A_sefer_5 = new Trip(A_arac_1, guzergah_3, "08/12/2023");
        Trip A_sefer_6 = new Trip(A_arac_1, guzergah_3, "09/12/2023");
        Trip A_sefer_7 = new Trip(A_arac_1, guzergah_3, "10/12/2023");

        // 2. Araç
        Trip A_sefer_8 = new Trip(A_arac_2, guzergah_3, "04/12/2023");
        Trip A_sefer_9 = new Trip(A_arac_2, guzergah_3, "05/12/2023");
        Trip A_sefer_10 = new Trip(A_arac_2, guzergah_3, "06/12/2023");
        Trip A_sefer_11 = new Trip(A_arac_2, guzergah_3, "07/12/2023");
        Trip A_sefer_12 = new Trip(A_arac_2, guzergah_3, "08/12/2023");
        Trip A_sefer_13 = new Trip(A_arac_2, guzergah_3, "09/12/2023");
        Trip A_sefer_14 = new Trip(A_arac_2, guzergah_3, "10/12/2023");

        // Seferlerin ArrayListe eklenmesi
        ArrayList<Trip> A_seferListesi = new ArrayList<>();
        A_seferListesi.add(A_sefer_1);
        A_seferListesi.add(A_sefer_2);
        A_seferListesi.add(A_sefer_3);
        A_seferListesi.add(A_sefer_4);
        A_seferListesi.add(A_sefer_5);
        A_seferListesi.add(A_sefer_6);
        A_seferListesi.add(A_sefer_7);
        A_seferListesi.add(A_sefer_8);
        A_seferListesi.add(A_sefer_9);
        A_seferListesi.add(A_sefer_10);
        A_seferListesi.add(A_sefer_11);
        A_seferListesi.add(A_sefer_12);
        A_seferListesi.add(A_sefer_13);
        A_seferListesi.add(A_sefer_14);


        // B Firmasının Araçlarına ait seferlerin oluşturulması
        // 1. Araç
        Trip B_sefer_1 = new Trip(B_arac_1, guzergah_3, "04/12/2023");
        Trip B_sefer_2 = new Trip(B_arac_1, guzergah_3, "05/12/2023");
        Trip B_sefer_3 = new Trip(B_arac_1, guzergah_3, "06/12/2023");
        Trip B_sefer_4 = new Trip(B_arac_1, guzergah_3, "07/12/2023");
        Trip B_sefer_5 = new Trip(B_arac_1, guzergah_3, "08/12/2023");
        Trip B_sefer_6 = new Trip(B_arac_1, guzergah_3, "09/12/2023");
        Trip B_sefer_7 = new Trip(B_arac_1, guzergah_3, "10/12/2023");

        // 2. Araç
        Trip B_sefer_8 = new Trip(B_arac_2, guzergah_4, "04/12/2023");
        Trip B_sefer_9 = new Trip(B_arac_2, guzergah_4, "05/12/2023");
        Trip B_sefer_10 = new Trip(B_arac_2, guzergah_4, "06/12/2023");
        Trip B_sefer_11 = new Trip(B_arac_2, guzergah_4, "07/12/2023");
        Trip B_sefer_12 = new Trip(B_arac_2, guzergah_4, "08/12/2023");
        Trip B_sefer_13 = new Trip(B_arac_2, guzergah_4, "09/12/2023");
        Trip B_sefer_14 = new Trip(B_arac_2, guzergah_4, "10/12/2023");

        // Seferlerin ArrayListe eklenmesi
        ArrayList<Trip> B_seferListesi = new ArrayList<>();
        B_seferListesi.add(B_sefer_1);
        B_seferListesi.add(B_sefer_2);
        B_seferListesi.add(B_sefer_3);
        B_seferListesi.add(B_sefer_4);
        B_seferListesi.add(B_sefer_5);
        B_seferListesi.add(B_sefer_6);
        B_seferListesi.add(B_sefer_7);
        B_seferListesi.add(B_sefer_8);
        B_seferListesi.add(B_sefer_9);
        B_seferListesi.add(B_sefer_10);
        B_seferListesi.add(B_sefer_11);
        B_seferListesi.add(B_sefer_12);
        B_seferListesi.add(B_sefer_13);
        B_seferListesi.add(B_sefer_14);


        // C Firmasının Araçlarına ait seferlerin oluşturulması
        // 1. Araç
        Trip C_sefer_1 = new Trip(C_arac_1, guzergah_4, "04/12/2023");
        Trip C_sefer_2 = new Trip(C_arac_1, guzergah_4, "05/12/2023");
        Trip C_sefer_3 = new Trip(C_arac_1, guzergah_4, "06/12/2023");
        Trip C_sefer_4 = new Trip(C_arac_1, guzergah_4, "07/12/2023");
        Trip C_sefer_5 = new Trip(C_arac_1, guzergah_4, "08/12/2023");
        Trip C_sefer_6 = new Trip(C_arac_1, guzergah_4, "09/12/2023");
        Trip C_sefer_7 = new Trip(C_arac_1, guzergah_4, "10/12/2023");

        // 2. Araç
        Trip C_sefer_8 = new Trip(C_arac_2, guzergah_5, "04/12/2023");
        Trip C_sefer_9 = new Trip(C_arac_2, guzergah_5, "05/12/2023");
        Trip C_sefer_10 = new Trip(C_arac_2, guzergah_5, "06/12/2023");
        Trip C_sefer_11 = new Trip(C_arac_2, guzergah_5, "07/12/2023");
        Trip C_sefer_12 = new Trip(C_arac_2, guzergah_5, "08/12/2023");
        Trip C_sefer_13 = new Trip(C_arac_2, guzergah_5, "09/12/2023");
        Trip C_sefer_14 = new Trip(C_arac_2, guzergah_5, "10/12/2023");

        // 3. Araç
        Trip C_sefer_15 = new Trip(C_arac_3, guzergah_5, "04/12/2023");
        Trip C_sefer_16 = new Trip(C_arac_3, guzergah_5, "05/12/2023");
        Trip C_sefer_17 = new Trip(C_arac_3, guzergah_5, "06/12/2023");
        Trip C_sefer_18 = new Trip(C_arac_3, guzergah_5, "07/12/2023");
        Trip C_sefer_19 = new Trip(C_arac_3, guzergah_5, "08/12/2023");
        Trip C_sefer_20 = new Trip(C_arac_3, guzergah_5, "09/12/2023");
        Trip C_sefer_21 = new Trip(C_arac_3, guzergah_5, "10/12/2023");

        // Seferlerin ArrayListe eklenmesi
        ArrayList<Trip> C_seferListesi = new ArrayList<>();
        C_seferListesi.add(C_sefer_1);
        C_seferListesi.add(C_sefer_2);
        C_seferListesi.add(C_sefer_3);
        C_seferListesi.add(C_sefer_4);
        C_seferListesi.add(C_sefer_5);
        C_seferListesi.add(C_sefer_6);
        C_seferListesi.add(C_sefer_7);
        C_seferListesi.add(C_sefer_8);
        C_seferListesi.add(C_sefer_9);
        C_seferListesi.add(C_sefer_10);
        C_seferListesi.add(C_sefer_11);
        C_seferListesi.add(C_sefer_12);
        C_seferListesi.add(C_sefer_13);
        C_seferListesi.add(C_sefer_14);
        C_seferListesi.add(C_sefer_15);
        C_seferListesi.add(C_sefer_16);
        C_seferListesi.add(C_sefer_17);
        C_seferListesi.add(C_sefer_18);
        C_seferListesi.add(C_sefer_19);
        C_seferListesi.add(C_sefer_20);
        C_seferListesi.add(C_sefer_21);


        // D Firmasının Araçlarına ait seferlerin oluşturulması
        // 1. Araç
        Trip D_sefer_1 = new Trip(D_arac_1, guzergah_1, "04/12/2023");
        Trip D_sefer_2 = new Trip(D_arac_1, guzergah_1, "05/12/2023");
        Trip D_sefer_3 = new Trip(D_arac_1, guzergah_1, "06/12/2023");
        Trip D_sefer_4 = new Trip(D_arac_1, guzergah_1, "07/12/2023");
        Trip D_sefer_5 = new Trip(D_arac_1, guzergah_1, "08/12/2023");
        Trip D_sefer_6 = new Trip(D_arac_1, guzergah_1, "09/12/2023");
        Trip D_sefer_7 = new Trip(D_arac_1, guzergah_1, "10/12/2023");

        // 2. Araç
        Trip D_sefer_8 = new Trip(D_arac_2, guzergah_2, "04/12/2023");
        Trip D_sefer_9 = new Trip(D_arac_2, guzergah_2, "05/12/2023");
        Trip D_sefer_10 = new Trip(D_arac_2, guzergah_2, "06/12/2023");
        Trip D_sefer_11 = new Trip(D_arac_2, guzergah_2, "07/12/2023");
        Trip D_sefer_12 = new Trip(D_arac_2, guzergah_2, "08/12/2023");
        Trip D_sefer_13 = new Trip(D_arac_2, guzergah_2, "09/12/2023");
        Trip D_sefer_14 = new Trip(D_arac_2, guzergah_2, "10/12/2023");

        // 3. Araç
        Trip D_sefer_15 = new Trip(D_arac_3, guzergah_2, "04/12/2023");
        Trip D_sefer_16 = new Trip(D_arac_3, guzergah_2, "05/12/2023");
        Trip D_sefer_17 = new Trip(D_arac_3, guzergah_2, "06/12/2023");
        Trip D_sefer_18 = new Trip(D_arac_3, guzergah_2, "07/12/2023");
        Trip D_sefer_19 = new Trip(D_arac_3, guzergah_2, "08/12/2023");
        Trip D_sefer_20 = new Trip(D_arac_3, guzergah_2, "09/12/2023");
        Trip D_sefer_21 = new Trip(D_arac_3, guzergah_2, "10/12/2023");

        // Seferlerin ArrayListe eklenmesi
        ArrayList<Trip> D_seferListesi = new ArrayList<>();
        D_seferListesi.add(D_sefer_1);
        D_seferListesi.add(D_sefer_2);
        D_seferListesi.add(D_sefer_3);
        D_seferListesi.add(D_sefer_4);
        D_seferListesi.add(D_sefer_5);
        D_seferListesi.add(D_sefer_6);
        D_seferListesi.add(D_sefer_7);
        D_seferListesi.add(D_sefer_8);
        D_seferListesi.add(D_sefer_9);
        D_seferListesi.add(D_sefer_10);
        D_seferListesi.add(D_sefer_11);
        D_seferListesi.add(D_sefer_12);
        D_seferListesi.add(D_sefer_13);
        D_seferListesi.add(D_sefer_14);
        D_seferListesi.add(D_sefer_15);
        D_seferListesi.add(D_sefer_16);
        D_seferListesi.add(D_sefer_17);
        D_seferListesi.add(D_sefer_18);
        D_seferListesi.add(D_sefer_19);
        D_seferListesi.add(D_sefer_20);
        D_seferListesi.add(D_sefer_21);


        // F Firmasının Araçlarına ait seferlerin oluşturulması
        // 1. Araç
        Trip F_sefer_1 = new Trip(F_arac_1, guzergah_6, "04/12/2023");
        Trip F_sefer_2 = new Trip(F_arac_1, guzergah_6, "05/12/2023");
        Trip F_sefer_3 = new Trip(F_arac_1, guzergah_6, "06/12/2023");
        Trip F_sefer_4 = new Trip(F_arac_1, guzergah_6, "07/12/2023");
        Trip F_sefer_5 = new Trip(F_arac_1, guzergah_6, "08/12/2023");
        Trip F_sefer_6 = new Trip(F_arac_1, guzergah_6, "09/12/2023");
        Trip F_sefer_7 = new Trip(F_arac_1, guzergah_6, "10/12/2023");

        // 2. Araç
        Trip F_sefer_8 = new Trip(F_arac_2, guzergah_6, "04/12/2023");
        Trip F_sefer_9 = new Trip(F_arac_2, guzergah_6, "05/12/2023");
        Trip F_sefer_10 = new Trip(F_arac_2, guzergah_6, "06/12/2023");
        Trip F_sefer_11 = new Trip(F_arac_2, guzergah_6, "07/12/2023");
        Trip F_sefer_12 = new Trip(F_arac_2, guzergah_6, "08/12/2023");
        Trip F_sefer_13 = new Trip(F_arac_2, guzergah_6, "09/12/2023");
        Trip F_sefer_14 = new Trip(F_arac_2, guzergah_6, "10/12/2023");

        // Seferlerin ArrayListe eklenmesi
        ArrayList<Trip> F_seferListesi = new ArrayList<>();
        F_seferListesi.add(F_sefer_1);
        F_seferListesi.add(F_sefer_2);
        F_seferListesi.add(F_sefer_3);
        F_seferListesi.add(F_sefer_4);
        F_seferListesi.add(F_sefer_5);
        F_seferListesi.add(F_sefer_6);
        F_seferListesi.add(F_sefer_7);
        F_seferListesi.add(F_sefer_8);
        F_seferListesi.add(F_sefer_9);
        F_seferListesi.add(F_sefer_10);
        F_seferListesi.add(F_sefer_11);
        F_seferListesi.add(F_sefer_12);
        F_seferListesi.add(F_sefer_13);
        F_seferListesi.add(F_sefer_14);



        // Daha önceden verilen şirketlerin oluşturulması
        Company A = new Company("A", "A", "A123", A_aracListesi, A_seferListesi);
        Company B = new Company("B", "B", "B123", B_aracListesi, B_seferListesi);
        Company C = new Company("C", "C", "C123", C_aracListesi, C_seferListesi);
        Company D = new Company("D", "D", "D123", D_aracListesi, D_seferListesi);
        Company F  = new Company("F", "F", "F123", F_aracListesi, F_seferListesi);

        // Şirket Listesine eklenmeleri
        hazirBilgiler.FirmaListesineEkle(A, B, C, D, F);



    }

    // Şirket Listesine firma eklemeyi sağlayan metot
    public void FirmaListesineEkle(Company... firmalar) {
        // Birden fazla firma aynı anda eklenebilir halde
        sirketler.addAll(Arrays.asList(firmalar));
    }

    // Şirket Listesinden firma silmeye yarayan metot
    public void FirmaListesindenSil(Company firma) {
        // Tek seferde sadece tek firma silinebilir
        sirketler.remove(firma);
    }

    // Firma bilgilerini döndüren metot
    public ArrayList<Company> FirmaBilgileriniDondur() {
        return sirketler;
    }


    // Güzergah listesine güzergah eklemeyi sağlayan metot
    public void GuzergahListesineEkle(Route... guzergahlar) {
        // Birden fazla firma aynı anda eklenebilir halde
        guzergahListesi.addAll(Arrays.asList(guzergahlar));
    }

    // Güzergah listesinden güzergah silmeye yarayan metot
    public void GuzergahListesindenSil(Route guzergah) {
        // Tek seferde sadece tek güzergah silinebilir
        guzergahListesi.remove(guzergah);
    }

    // Güzergah bilgilerini döndüren metot
    public ArrayList<Route> GuzergahBilgileriniDondur() {
        return guzergahListesi;
    }



    // Koltuk Durum Bilgisi Fonksiyonu
    public void koltuk_durum_bilgi() {

    }

    // Koltuk Rezervasyon Fonksiyonu
    public void koltuk_rezervasyon() {

    }

    // Boş Construct Metodu
    public Transport() {
    }
}
