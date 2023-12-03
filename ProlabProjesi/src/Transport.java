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
        // Daha önceden verilen şirketlerin oluşturulması
        Company A = new Company("A", "A", "A123");
        Company B = new Company("B", "B", "B123");
        Company C = new Company("C", "C", "C123");
        Company D = new Company("D", "D", "D123");
        Company F  = new Company("F", "F", "F123");

        // Şirket Listesine eklenmeleri
        Transport hazirBilgiler = new Transport();
        hazirBilgiler.FirmaListesineEkle(A, B, C, D, F);

        // Demiryolu Güzergahları
        String[] guzer_1 = {"İstanbul", "Kocaeli", "Bilecik", "Eskişehir", "Ankara", "Eskişehir",
                "Bilecik", "Kocaeli", "İstanbul"};
        String[] guzer_2 = {"İstanbul", "Kocaeli", "Bilecik", "Eskişehir", "Konya", "Eskişehir",
                "Bilecik", "Kocaeli", "İstanbul"};

        // Karayolu Güzergahları
        String[] guzer_3 = {"İstanbul", "Kocaeli", "Ankara", "Kocaeli", "İstanbul"};
        String[] guzer_4 = {"İstanbul", "Kocaeli",  "Eskişehir", "Konya", "Eskişehir", "Kocaeli", "İstanbul"};

        // Havayolu Güzergahları
        String[] guzer_5 = {"İstanbul", "Konya", "İstanbul"};
        String[] guzer_6 = {"İstanbul", "Ankara", "İstanbul"};

        // Güzergahların oluşturulması
        Route guzergah_1 = new Route(guzer_1, "Demiryolu");
        Route guzergah_2 = new Route(guzer_2, "Demiryolu");
        Route guzergah_3 = new Route(guzer_3, "Karayolu");
        Route guzergah_4 = new Route(guzer_4, "Karayolu");
        Route guzergah_5 = new Route(guzer_5, "Havayolu");
        Route guzergah_6 = new Route(guzer_6, "Havayolu");

        // Güzergahların güzergah listesine eklenmesi
        hazirBilgiler.GuzergahListesineEkle(guzergah_1, guzergah_2, guzergah_3, guzergah_4, guzergah_5, guzergah_6);


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
