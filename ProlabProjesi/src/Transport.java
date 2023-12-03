import java.util.ArrayList;
import java.util.Arrays;

interface Ireservable{

}
// Firma Bilgilerini, Araçları ve Seyahat bilgileri gibi bir sürü bilgiyi barındıran Class
public class Transport implements Ireservable {
    // Şirketleri (Firmalar) Tutan ArrayList
    static ArrayList<Company> sirketler = new ArrayList<>();

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

    // Güzergah Bilgileri


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
