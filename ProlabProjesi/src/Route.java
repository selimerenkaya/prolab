import java.util.ArrayList;

public class Route {
    String[] guzergah;
    String ulasim_turu; // Demiryolu, Karayolu, Havayolu gibi
    String[][] guzergahFiyatBilgileri;

    // Güzergah değişkeni için Get/Set metotları
    public String[] get_guzergah(){
        return guzergah;
    }

    public void set_guzergah (String[] guzergahGirdisi) {
        guzergah = guzergahGirdisi;
    }

    // Ulaşım türü değişkeni için Get/Set metotları
    public String get_ulasim_turu(){
        return ulasim_turu;
    }

    public void set_guzergah (String ulasim_turu_girdisi) {
        ulasim_turu_girdisi = ulasim_turu_girdisi;
    }

    // Güzergah Fiyat Bilgileri değişkeni için Get/Set metotları
    public String[][] get_guzergahFiyatBilgileri(){
        return guzergahFiyatBilgileri;
    }

    public void set_guzergahFiyatBilgileri (String[][] guzergahFiyatBilgileriGirdisi) {
        guzergahFiyatBilgileri = guzergahFiyatBilgileriGirdisi;
    }


    // Parametresiz Construct Metodu
    Route () {

    }

    // Güzergah oluşturulurken kullanılan Construct Metodu
    Route (String[] guzergahGirdisi, String ulasim_turuGirdisi, String[][] guzergahFiyatBilgileriGirdisi) {
        guzergah = guzergahGirdisi;
        ulasim_turu = ulasim_turuGirdisi;
        guzergahFiyatBilgileri = guzergahFiyatBilgileriGirdisi;
    }

}
