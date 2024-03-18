// Lokasyon Nesnesinin Sınıfı
public class Location {
    private String bolge; // "winter, summer" şeklinde tutar
    private int x_coordinate;
    private int y_coordinate;
    private boolean engelDurum = false; // Lokasyonda engel olup olmadığını tutar
    private Obstacle konumdakiNesne;

    // Get-Set Metotları
    // Get Metotları
    String getBolge() {
        return bolge;
    }

    int getXCoord() {
        return x_coordinate;
    }

    int getYCoord() {
        return y_coordinate;
    }

    boolean getEngelDurum() {
        return engelDurum;
    }

    Obstacle getKonumdakiNesne() {
        return konumdakiNesne;
    }

    // Set Metotları
    void setBolge(String bolge) {
        this.bolge = bolge;
    }

    void setXCoord(int x) {
        this.x_coordinate = x;
    }

    void setYCoord(int y) {
        this.y_coordinate = y;
    }

    void setEngelDurum(boolean engelDurum) {
        this.engelDurum = engelDurum;
    }

    void setKonumdakiNesne(Obstacle konumdakiNesne) {
        this.konumdakiNesne = konumdakiNesne;
    }


    // Constructor Metotları
    Location() {}

    // Engelsiz Düz Nesne
    Location(int x, int y) {
        setXCoord(x);
        setYCoord(y);
    }

    // Engelsiz Düz Nesne
    Location(int x, int y, String bolge) {
        setXCoord(x);
        setYCoord(y);
        setBolge(bolge);
    }

    // Engele Sahip Nesne
    Location(int x, int y, boolean engelDurum, String bolge) {
        setXCoord(x);
        setYCoord(y);
        setEngelDurum(engelDurum);
        setBolge(bolge);
    }

}
