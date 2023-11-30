import java.util.Date;

// Müşteri Classı
public class Customer {
    // Müşterinin özellikleri
    String Ad;
    String Soyad;
    String TcKimlik;
    Date DogumTarihi;

    // Customer Class'ının Constructu
    // Gerekli olan tüm bilgileri içermelidir
    Customer(String isim, String soyisim, String kimlik, Date kogumGunu){
        Ad = isim;
        Soyad = soyisim;
        TcKimlik = kimlik;
        DogumTarihi = kogumGunu;
    }


}
