//Araçtaki 4 personeli ve yolcu adlarını
// temsil eden abstract class
//2 türe ayrılır
//1. passenger
//2.personel
abstract class Person {
    String personAdi;
    String personSoyadi;

    public String getPersonAdi() {
        return personAdi;
    }

    public void setPersonAdi(String personAdi) {
        this.personAdi = personAdi;
    }

    public String getPersonSoyadi() {
        return personSoyadi;
    }

    public void setPersonSoyadi(String personSoyadi) {
        this.personSoyadi = personSoyadi;
    }

    Person() {

    }

    Person(String personAdiGirdi, String personSoyadiGirdi) {
        setPersonAdi(personAdiGirdi);
        setPersonSoyadi(personSoyadiGirdi);
    }
}

class Passenger extends Person{
}

//4 personel var 2 driver 2 hostes
class Personel extends Person{
    String is_durum; // Personelin nerede çalıştığını belirten değişken

    public String getIs_durum() {
        return is_durum;
    }

    public void setIs_durum(String is_durum) {
        this.is_durum = is_durum;
    }

    Personel() {

    }

    Personel(String personAdiGirdi, String personSoyadiGirdi, String is_durum) {
        super(personAdiGirdi, personSoyadiGirdi);
        setIs_durum(is_durum);
    }
}

