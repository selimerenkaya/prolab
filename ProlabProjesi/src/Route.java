import java.util.ArrayList;

public class Route {
    String[] guzergah;
    String ulasim_turu; // Demiryolu, Karayolu, Havayolu gibi

    // Güzergah değişkeni için Get/Set metotları
    public String[] get_guzergah(){
        return guzergah;
    }

    public void set_guzergah (String[] guzergahGirdisi) {
        guzergah = guzergahGirdisi;
    }



    // Parametresiz Construct Metodu
    Route () {

    }

    // Güzergah oluşturulurken kullanılan Construct Metodu
    Route (String[] guzergahGirdisi, String ulasim_turuGirdisi) {
        guzergah = guzergahGirdisi;
        ulasim_turu = ulasim_turuGirdisi;
    }

}
