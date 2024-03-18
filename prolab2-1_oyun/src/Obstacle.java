// HAREKETLİ ENGELELRİN 3 KONUMU, DOĞUŞ, HAREKET SINIRLARI
// SABİTLERİN 1 KONUMU, MEVCUT KONUM
// ORTAK OLARAK BOYUT

interface sabitEngel {

}

interface dinamikEngel {

}

public class Obstacle {

    protected Location konum;  // Konum
    protected int boyutX;  // Genişlik
    protected int boyutY;  // Yükseklik
    protected String ImagePath;
    public String sinifAd;

    // Get-Set Metotları
    // Get Metotları
    Location getKonum() {
        return konum;
    }

    int getBoyutX() {
        return boyutX;
    }

    int getBoyutY() {
        return boyutY;
    }

    String getImagePath() {
        return ImagePath;
    }

    String getSinifAd() { return sinifAd;}

    // Set Metotları
    void setKonum(Location konum) {
        this.konum = konum;
        this.konum.setEngelDurum(true);
    }

    void setBoyutX(int boyutX) {
        this.boyutX = boyutX;
    }

    void setBoyutY(int boyutY) {
        this.boyutY = boyutY;
    }

    void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }


    // Constructor Metotları
    public Obstacle() {}

    // Hazır Lokasyon kullanan construct metodu
    public Obstacle(Location konumGirdi) {
        setKonum(konumGirdi);
    }
}


// Dinamik Engel Sınıfı
class DynamicObstacle extends Obstacle {
    // Gui Değişkenleri
    private GuiPanel gp;
    private int yon = 1; // 1 ise sağ, 0 ise sola
    private int ilerlenenKareX = 0;
    private int ilerlenenKareY = 0;
    public int animationCounter = 0;

    private int ileriMaxKareX;
    private int ileriMaxKareY;

    // Arı ve Kuş Sınıfında Override Edilecek
    public void update() {
    }

    // İlerledikleri yolun bilgisini döndüren metot
    public int guzergahBitis() {
        return 0;
    }

    // Get - Set Metotları
    // Get Metotları
    GuiPanel getGp() {
        return gp;
    }

    int getYon() {
        return yon;
    }

    int getIlerlenenKareX() {
        return ilerlenenKareX;
    }

    int getIlerlenenKareY() {
        return ilerlenenKareY;
    }

    int getIleriMaxKareX() {
        return ileriMaxKareX;
    }

    int getIleriMaxKareY() {
        return ileriMaxKareY;
    }

    // Set Metotları
    void setGp(GuiPanel gp){
        this.gp = gp;
    }

    void setYon(int yon) {
        this.yon = yon;
    }

    void setIlerlenenKareX(int ilerlenenKareX) {
        this.ilerlenenKareX = ilerlenenKareX;
    }

    void setIlerlenenKareY(int ilerlenenKareY) {
        this.ilerlenenKareY = ilerlenenKareY;
    }

    void setIleriMaxKareX(int ileriMaxKareX) {
        this.ileriMaxKareX = ileriMaxKareX;
    }

    void setIleriMaxKareY(int ileriMaxKareY) {
        this.ileriMaxKareY = ileriMaxKareY;
    }


    DynamicObstacle() {}

    DynamicObstacle(Location konum) {
        super(konum);
    }
}
