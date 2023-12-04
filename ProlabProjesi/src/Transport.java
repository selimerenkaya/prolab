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
        Bus A_arac_1 = new Bus("Otobüs 1", "Benzin", 10, 20, guzergah_3, 5000, 2000);
        Bus A_arac_2 = new Bus("Otobüs 2", "Benzin", 10, 15, guzergah_3, 5000, 2000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> A_aracListesi = new ArrayList<>();
        A_aracListesi.add(A_arac_1);
        A_aracListesi.add(A_arac_2);


        // B firmasının Araçları
        Bus B_arac_1 = new Bus("Otobüs 1", "Motorin", 5, 15, guzergah_3, 3000, 1500);
        Bus B_arac_2 = new Bus("Otobüs 2", "Motorin", 5, 20, guzergah_4, 3000, 1500);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> B_aracListesi = new ArrayList<>();
        B_aracListesi.add(B_arac_1);
        B_aracListesi.add(B_arac_2);


        // C firmasının Araçları
        Bus C_arac_1 = new Bus("Otobüs 1", "Motorin", 6, 20, guzergah_4, 4000, 2000);
        Airplane C_arac_2 = new Airplane("Uçak 1", "Gaz", 25, 30, guzergah_5, 10000, 6000);
        Airplane C_arac_3 = new Airplane("Uçak 2", "Gaz", 25, 30, guzergah_5, 10000, 6000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> C_aracListesi = new ArrayList<>();
        C_aracListesi.add(C_arac_1);
        C_aracListesi.add(C_arac_2);
        C_aracListesi.add(C_arac_3);


        // D firmasının Araçları
        Train D_arac_1 = new Train("Tren 1", "Elektrik", 3, 25, guzergah_1, 2000, 1000);
        Train D_arac_2 = new Train("Tren 2", "Elektrik", 3, 25, guzergah_2, 2000, 1000);
        Train D_arac_3 = new Train("Tren 3", "Elektrik", 3, 25, guzergah_2, 2000, 1000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> D_aracListesi = new ArrayList<>();
        D_aracListesi.add(D_arac_1);
        D_aracListesi.add(D_arac_2);
        D_aracListesi.add(D_arac_3);


        // F firmasının Araçları
        Airplane F_arac_1 = new Airplane("Uçak 1", "Gaz", 20, 30, guzergah_6, 7500, 4000);
        Airplane F_arac_2 = new Airplane("Uçak 2", "Gaz", 20, 30, guzergah_6, 7500, 4000);
        // Araç Bilgilerini Tutan ArrayList
        ArrayList<Object> F_aracListesi = new ArrayList<>();
        F_aracListesi.add(F_arac_1);
        F_aracListesi.add(F_arac_2);


        // Daha önceden verilen şirketlerin oluşturulması
        Company A = new Company("A", "A", "A123", A_aracListesi);
        Company B = new Company("B", "B", "B123", B_aracListesi);
        Company C = new Company("C", "C", "C123", C_aracListesi);
        Company D = new Company("D", "D", "D123", D_aracListesi);
        Company F  = new Company("F", "F", "F123", F_aracListesi);

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
