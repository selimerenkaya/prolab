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
    int kullanan_personel_ucret;
    int hizmet_personel_ucret;

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

    // Kullanan personel ücreti için Get/Set Metotları
    abstract int get_kullanan_personel_ucret();

    abstract void set_kullanan_personel_ucret(int kullanan_personel_ucret_Girdisi);

    // Hizmet personel ücreti için Get/Set Metotları
    abstract int get_hizmet_personel_ucret();

    abstract void set_hizmet_personel_ucret(int hizmet_personel_ucret_Girdisi);

    // Yakıt gideri hesaplaması yapan Metot
    abstract int CalculateFuelCost();


}

// Otobüs Classı
class Bus extends Vehicle {
    // Yakıt Türleri
    // 1- Benzin
    // 2- Motorin

    static String arac_tur = "Otobüs";
    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;
    int kullanan_personel_ucret;
    int hizmet_personel_ucret;

    public String getArac_tur() {
        return arac_tur;
    }

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

    @Override
    int get_kullanan_personel_ucret() {
        return kullanan_personel_ucret;
    }

    @Override
    void set_kullanan_personel_ucret(int kullanan_personel_ucret_Girdisi) {
        kullanan_personel_ucret = kullanan_personel_ucret_Girdisi;
    }

    @Override
    int get_hizmet_personel_ucret() {
        return hizmet_personel_ucret;
    }

    @Override
    void set_hizmet_personel_ucret(int hizmet_personel_ucret_Girdisi) {
        hizmet_personel_ucret = hizmet_personel_ucret_Girdisi;
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

    // Güzergah parametresi olmayan Parametreli Contruct
    public Bus(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi , int kapasite_Girdisi,
                int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }

    // Parametreli Contruct
    public Bus(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi , int kapasite_Girdisi,
               Route guzergahGirdisi, int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }

}

// Tren Classı
class Train extends Vehicle {
    // Yakıt Türleri
    // 1- Elektrik

    static String arac_tur = "Tren";
    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;
    int kullanan_personel_ucret;
    int hizmet_personel_ucret;

    public String getArac_tur() {
        return arac_tur;
    }

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

    @Override
    int get_kullanan_personel_ucret() {
        return kullanan_personel_ucret;
    }

    @Override
    void set_kullanan_personel_ucret(int kullanan_personel_ucret_Girdisi) {
        kullanan_personel_ucret = kullanan_personel_ucret_Girdisi;
    }

    @Override
    int get_hizmet_personel_ucret() {
        return hizmet_personel_ucret;
    }

    @Override
    void set_hizmet_personel_ucret(int hizmet_personel_ucret_Girdisi) {
        hizmet_personel_ucret = hizmet_personel_ucret_Girdisi;
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

    // Güzergah parametresi olmayan Parametreli Contruct
    public Train(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi,
                 int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }

    // Parametreli Contruct
    public Train(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi,
                 Route guzergahGirdisi, int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }
}

// Uçak Classı
class Airplane extends Vehicle {
    // Yakıt Türleri
    // 1- Gaz

    static String arac_tur = "Uçak";
    String arac_id;
    String yakit_turu;
    int yakit_ucreti;
    int kapasite;
    Route guzergah;
    int kullanan_personel_ucret;
    int hizmet_personel_ucret;

    public String getArac_tur() {
        return arac_tur;
    }

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

    @Override
    int get_kullanan_personel_ucret() {
        return kullanan_personel_ucret;
    }

    @Override
    void set_kullanan_personel_ucret(int kullanan_personel_ucret_Girdisi) {
        kullanan_personel_ucret = kullanan_personel_ucret_Girdisi;
    }

    @Override
    int get_hizmet_personel_ucret() {
        return hizmet_personel_ucret;
    }

    @Override
    void set_hizmet_personel_ucret(int hizmet_personel_ucret_Girdisi) {
        hizmet_personel_ucret = hizmet_personel_ucret_Girdisi;
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

    // Güzergah parametresi olmayan Parametreli Contruct
    public Airplane(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi,
                    int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }

    // Parametreli Contruct
    public Airplane(String arac_id_Girdisi, String yakit_turu_Girdisi, int yakit_ucreti_Girdisi, int kapasite_Girdisi,
                    Route guzergahGirdisi, int kullanan_personel_ucretGirdisi, int hizmet_personel_ucretGirdisi) {
        arac_id = arac_id_Girdisi;
        yakit_turu = yakit_turu_Girdisi;
        yakit_ucreti = yakit_ucreti_Girdisi;
        kapasite = kapasite_Girdisi;
        guzergah = guzergahGirdisi;
        kullanan_personel_ucret = kullanan_personel_ucretGirdisi;
        hizmet_personel_ucret = hizmet_personel_ucretGirdisi;
    }
}