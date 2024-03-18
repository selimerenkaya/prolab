public class Reservation {
    Passenger yolcu;
    Object arac;
    int koltukNumara;
    String zaman;

    public Passenger getYolcu() {
        return yolcu;
    }

    public void setYolcu(Passenger yolcu) {
        this.yolcu = yolcu;
    }

    public Object getArac() {
        return arac;
    }

    public void setArac(Object arac) {
        this.arac = arac;
    }

    public int getKoltukNumara() {
        return koltukNumara;
    }

    public void setKoltukNumara(int KoltukNumaraGirdi) {
        this.koltukNumara = KoltukNumaraGirdi;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zamanGirdi) {
        zaman = zamanGirdi;
    }

    Reservation() {

    }

    Reservation(Passenger yolcuGirdi, Object aracGirdi, int KoltukNumaraGirdi, String zamanGirdi) {
        setYolcu(yolcuGirdi);
        setArac(aracGirdi);
        setKoltukNumara(KoltukNumaraGirdi);
        setZaman(zamanGirdi);
    }
}
