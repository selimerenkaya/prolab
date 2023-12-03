//Araçtaki 4 personeli ve yolcu adlarını
// temsil eden abstract class
//2 türe ayrılır
//1. passenger
//2.personel
abstract class Person {
    String personAdi;
    String personSoyadi;


}

class Passenger extends Person{
}

//4 personel var 2 driver 2 hostes
class Personel extends Person{
    String driver1;
    String driver2;
    String host1;
    String host2;

}

