// Araçları temsil eden Class
// Abstract bir class
// 3 Türe ayrılır
// 1- Bus
// 2- Train
// 3- Airplane
abstract class Vehicle {
    String arac_id;
    String yakit_turu;
    int kapasite;
}

// Otobüs Classı
class Bus extends Vehicle {
    // Yakıt Türleri
    // 1- Benzin
    // 2- Motorin
}

// Tren Classı
class Train extends Vehicle {
    // Yakıt Türleri
    // 1- Elektrik
}

// Uçak Classı
class Airplane extends Vehicle {
    // Yakıt Türleri
    // 1- Gaz
}