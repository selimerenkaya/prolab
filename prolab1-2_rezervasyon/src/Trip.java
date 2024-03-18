import java.awt.*;
import java.util.ArrayList;

public class Trip {
    Object arac; // Araç Nesnesi
    Route guzergah; // Güzergah Nesnesi
    String zaman; // Zaman Nesnesi -------- gün/ay/yıl şeklinde olacak
    ArrayList<Koltuk> koltuklar;
    int fiyat; // Fiyat Nesnesi   // Daha sonra hesaplanacak

    // Araç için Get/Set Metotları
    public Object get_arac() {
        return arac;
    }

    public void set_arac(Object aracGirdisi) {
        arac = aracGirdisi;
    }

    // Güzergah için Get/Set Metotları
    public Route get_guzergah() {
        return guzergah;
    }

    public void set_guzergah(Route guzergahGirdisi) {
        guzergah = guzergahGirdisi;
    }

    // Zaman için Get/Set Metotları
    public String get_zaman() {
        return zaman;
    }

    public void set_zaman(String  zamanGirdisi) {
        zaman = zamanGirdisi;
    }

    // Koltuklar Değişkeni için Get/Set metotları
    public ArrayList<Koltuk> getKoltuklar() {
        return koltuklar;
    }

    public void setKoltuklar(ArrayList<Koltuk> koltuklarGirdisi) {
        koltuklar = koltuklarGirdisi;
    }



    // Boş Construct
    Trip() {

    }

    // Parametreli Construct
    Trip (Object aracGirdisi, Route guzergahGirdisi, String zamanGirdisi){
        arac = aracGirdisi;
        guzergah = guzergahGirdisi;
        zaman = zamanGirdisi;

        koltuklar = new ArrayList<>();
        Class arac_sinifi = arac.getClass();
        switch (arac_sinifi.getName()) {
            case "Bus" -> {
                Bus temp_arac = (Bus) arac;
                for (int i = 0; i < temp_arac.get_kapasite(); i++) {
                    Koltuk arac_koltuk = new Koltuk(i + 1, "Boş");
                    koltuklar.add(arac_koltuk);
                }


            }
            case "Train" -> {
                Train temp_arac = (Train) arac;
                for (int i = 0; i < temp_arac.get_kapasite(); i++) {
                    Koltuk arac_koltuk = new Koltuk(i + 1, "Boş");
                    koltuklar.add(arac_koltuk);
                }

            }
            case "Airplane" -> {
                Airplane temp_arac = (Airplane) arac;
                for (int i = 0; i < temp_arac.get_kapasite(); i++) {
                    Koltuk arac_koltuk = new Koltuk(i + 1, "Boş");
                    koltuklar.add(arac_koltuk);
                }

            }
        }

    }

}
