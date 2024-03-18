//Araçtaki 4 personeli ve yolcu adlarını
// temsil eden abstract class
//2 türe ayrılır
//1. passenger
//2.personel
abstract class Person {
    String Ad;
    String Soyad;

    abstract String getAd();

    abstract void setAd(String AdGirdisi);

    abstract String getSoyad();

    abstract void setSoyad(String SoyadGirdisi);

    Person() {

    }

    Person(String AdGirdi, String SoyadGirdi) {
        setAd(AdGirdi);
        setSoyad(SoyadGirdi);
    }
}

class Passenger extends Person{

    @Override
    public String getAd() {
        return Ad;
    }

    @Override
    public void setAd(String AdGirdisi) {
        this.Ad = AdGirdisi;
    }

    @Override
    public String getSoyad() {
        return Soyad;
    }

    @Override
    public void setSoyad(String SoyadGirdisi) {
        this.Soyad = SoyadGirdisi;
    }

    Passenger() {

    }

    Passenger(String AdGirdi, String SoyadGirdi) {
        super(AdGirdi, SoyadGirdi);
    }

}

// Her Araçta 4 Personel vardır. 2 Araç Kullanan, 2 Hizmet Veren personel.
class Personel extends Person{
    String is_durum; // Personelin nerede çalıştığını belirten değişken - Araç ise Araç Kullanan, Hizmet ise Hizmet Veren

    public String getIs_durum() {
        return is_durum;
    }

    public void setIs_durum(String is_durum) {
        this.is_durum = is_durum;
    }

    @Override
    public String getAd() {
        return Ad;
    }

    @Override
    public void setAd(String AdGirdisi) {
        this.Ad = AdGirdisi;
    }

    @Override
    public String getSoyad() {
        return Soyad;
    }

    @Override
    public void setSoyad(String SoyadGirdisi) {
        this.Soyad = SoyadGirdisi;
    }


    Personel() {

    }

    Personel(String AdGirdi, String SoyadGirdi, String is_durum) {
        super(AdGirdi, SoyadGirdi);
        setIs_durum(is_durum);
    }
}

