// Sistemdeki kullanıcıları temsil eden class
// Abstract bir class
// 2 türe ayrılır
// 1- Admin
// 2- Company
interface ILoginable {
    void BilgiAl();
}

abstract class User implements ILoginable {
    // User Class'ının özellikleri
    String kullaniciAdi;
    String sifre;

    // Construct edilirken kulllanılacak metot
    User() {

    }

    User(String kullaniciAdiDegiskeni, String sifreDegiskeni){
        kullaniciAdi = kullaniciAdiDegiskeni;
        sifre = sifreDegiskeni;
    }

    // Kullanıcı Adı değişkeni için Get/Set Metotları
    public String get_kullaniciAdi() {
        return kullaniciAdi;
    }

    public void set_kullaniciAdi(String kullaniciAdiYeni) {
        kullaniciAdi = kullaniciAdiYeni;
    }

    // Şifre değişkeni için Get/Set Metotları
    public String get_sifre() {
        return sifre;
    }

    public void set_sifre(String sifreYeni) {
        sifre = sifreYeni;
    }


    public void BilgiAl() {
    }

}

// User Class'ının Admin Classı
class Admin extends User {
    String kullaniciAdi = "admin";
    String sifre = "admin123";

    // Admin Classı Contruct Edilirken kullanılacak metot
    Admin() {

    }

    // Bilgileri kontrol eden kısım
    public boolean Giris(String kullaniciAdiGirdisi, String sifreGirdisi) {
        // Admin girişi için doğru bilgiler girildiyse TRUE yanlış girildiyse FALSE değer
        // döndüren metot
        return kullaniciAdiGirdisi.equals(kullaniciAdi) && sifreGirdisi.equals(sifre);
    }

}

// User Class'ının Company Classı
class Company extends User {

    // Company Classı Contruct Edilirken kullanılacak metot
    Company(String kullaniciAdiDegiskeni, String sifreDegiskeni) {
        super(kullaniciAdiDegiskeni, sifreDegiskeni);
    }

}