import java.time.LocalDate;

public class Trip {
    Object arac; // Araç Nesnesi
    Route guzergah; // Güzergah Nesnesi
    String zaman; // Zaman Nesnesi -------- gün/ay/yıl şeklinde olacak
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



    // Boş Construct
    Trip() {

    }

    // Parametreli Construct
    Trip (Object aracGirdisi, Route guzergahGirdisi, String zamanGirdisi){
        arac = aracGirdisi;
        guzergah = guzergahGirdisi;
        zaman = zamanGirdisi;
    }

}
