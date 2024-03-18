public class Koltuk {
    int koltuk_numarasi;
    String koltuk_durumu; // Dolu, Boş;

    public int getKoltuk_numarasi() {
        return koltuk_numarasi;
    }

    public void setKoltuk_numarasi(int koltuk_numarasi) {
        this.koltuk_numarasi = koltuk_numarasi;
    }

    public String getKoltuk_durumu() {
        return koltuk_durumu;
    }

    public void setKoltuk_durumu(String koltuk_durumu) {
        this.koltuk_durumu = koltuk_durumu;
    }

    Koltuk() {

    }

    Koltuk(int koltuk_numarasiGirdi, String koltuk_durumuGirdi) {
        setKoltuk_numarasi(koltuk_numarasiGirdi);
        setKoltuk_durumu(koltuk_durumuGirdi);
    }
}
