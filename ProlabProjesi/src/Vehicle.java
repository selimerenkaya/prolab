// Araçları temsil eden Class
// Abstract bir class
// 3 Türe ayrılır
// 1- Bus
// 2- Train
// 3- Airplane
abstract class Vehicle {
    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;

    // Araç İD'si için Get/Set Metotları
    abstract String get_arac_id();

    abstract void set_arac_id(String arac_id_Girdisi);

    // Yakıt Türü için Get/Set Metotları
    abstract String get_yakit_turu();

    abstract void set_yakit_turu(String yakit_turu_Girdisi);

    // Yakıt Ücreti için Get/Set Metotları
    abstract int get_yakit_ucreti();
    abstract void set_yakit_ucreti(int yakit_ucreti_Girdisi);

    // Kapasite için Get/Set Metotları
    abstract int get_kapasite();

    abstract void set_kapasite(int kapasite_Girdisi);

    // Güzergah için Get/Set Metotları
    abstract Route get_guzergah();

    abstract void set_guzergah(Route guzergah_Girdisi);

    // Yakıt gideri hesaplaması yapan Metot
    abstract int CalculateFuelCost();


}

// Otobüs Classı
class Bus extends Vehicle {
    // Yakıt Türleri
    // 1- Benzin
    // 2- Motorin

    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;

    @Override
    public String get_arac_id() {
        return arac_id;
    }

    @Override
    public void set_arac_id(String arac_id_Girdisi) {
        arac_id = arac_id_Girdisi;
    }

    @Override
    public String get_yakit_turu() {
        return yakit_turu;
    }

    @Override
    public void set_yakit_turu(String yakit_turu_Girdisi) {
        yakit_turu = yakit_turu_Girdisi;
    }

    // Yakıt Ücreti için Get/Set Metotları
    @Override
    public int get_yakit_ucreti() {
        return yakit_ucreti;
    }

    @Override
    void set_yakit_ucreti(int yakit_ucreti_Girdisi) {
        yakit_ucreti = yakit_ucreti_Girdisi;
    }


    @Override
    public int get_kapasite() {
        return kapasite;
    }

    @Override
    void set_kapasite(int kapasite_Girdisi) {
        kapasite = kapasite_Girdisi;
    }

    @Override
    public Route get_guzergah() {
        return guzergah;
    }

    @Override
    void set_guzergah(Route guzergah_Girdisi) {
        guzergah = guzergah_Girdisi;
    }


    // Yakıt gideri hesaplaması yapan Metot
    @Override
    int CalculateFuelCost() {
        return 0;
    }

    // Construct Metotları
    // Parametresiz Construct
    public Bus() {

    }

    // Parametreli Contruct
    public Bus(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi , int kapasite_Girdisi, Route guzergahGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
    }

}

// Tren Classı
class Train extends Vehicle {
    // Yakıt Türleri
    // 1- Elektrik

    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;

    @Override
    public String get_arac_id() {
        return arac_id;
    }

    @Override
    public void set_arac_id(String arac_id_Girdisi) {
        arac_id = arac_id_Girdisi;
    }

    @Override
    public String get_yakit_turu() {
        return yakit_turu;
    }

    @Override
    public void set_yakit_turu(String yakit_turu_Girdisi) {
        yakit_turu = yakit_turu_Girdisi;
    }

    // Yakıt Ücreti için Get/Set Metotları
    @Override
    public int get_yakit_ucreti() {
        return yakit_ucreti;
    }

    @Override
    void set_yakit_ucreti(int yakit_ucreti_Girdisi) {
        yakit_ucreti = yakit_ucreti_Girdisi;
    }


    @Override
    public int get_kapasite() {
        return kapasite;
    }

    @Override
    void set_kapasite(int kapasite_Girdisi) {
        kapasite = kapasite_Girdisi;
    }

    @Override
    public Route get_guzergah() {
        return guzergah;
    }

    @Override
    void set_guzergah(Route guzergah_Girdisi) {
        guzergah = guzergah_Girdisi;
    }

    // Yakıt gideri hesaplaması yapan Metot
    @Override
    int CalculateFuelCost() {
        return 0;
    }

    // Construct Metotları
    // Parametresiz Construct
    public Train() {

    }

    // Parametreli Contruct
    public Train(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi, Route guzergahGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
    }
}

// Uçak Classı
class Airplane extends Vehicle {
    // Yakıt Türleri
    // 1- Gaz

    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;

    @Override
    public String get_arac_id() {
        return arac_id;
    }

    @Override
    public void set_arac_id(String arac_id_Girdisi) {
        arac_id = arac_id_Girdisi;
    }

    @Override
    public String get_yakit_turu() {
        return yakit_turu;
    }

    @Override
    public void set_yakit_turu(String yakit_turu_Girdisi) {
        yakit_turu = yakit_turu_Girdisi;
    }

    // Yakıt Ücreti için Get/Set Metotları
    @Override
    public int get_yakit_ucreti() {
        return yakit_ucreti;
    }

    @Override
    void set_yakit_ucreti(int yakit_ucreti_Girdisi) {
        yakit_ucreti = yakit_ucreti_Girdisi;
    }


    @Override
    public int get_kapasite() {
        return kapasite;
    }

    @Override
    void set_kapasite(int kapasite_Girdisi) {
        kapasite = kapasite_Girdisi;
    }

    @Override
    public Route get_guzergah() {
        return guzergah;
    }

    @Override
    void set_guzergah(Route guzergah_Girdisi) {
        guzergah = guzergah_Girdisi;
    }

    // Yakıt gideri hesaplaması yapan Metot
    @Override
    int CalculateFuelCost() {
        return 0;
    }

    // Construct Metotları
    // Parametresiz Construct
    public Airplane() {

    }

    // Parametreli Contruct
    public Airplane(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi, Route guzergahGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
    }
}