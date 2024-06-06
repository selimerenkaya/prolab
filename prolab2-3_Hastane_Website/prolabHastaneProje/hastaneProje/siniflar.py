# Projede kullanılacak olan sınıfları içerir
from flask_login import UserMixin

# Randevu Sınıfı
class Randevu:
    def __init__(self, randevuID: int, randevuDoktorID: int, randevuHastaID: int,
                 randevuTarih: str, randevuSaat: str) -> None:
        self.__randevuID = randevuID
        self.__randevuTarih = randevuTarih
        self.__randevuSaat = randevuSaat
        self.__randevuDoktorID = randevuDoktorID
        self.__randevuHastaID = randevuHastaID
        print("Randevu")

    # Get/Set Metotları
    # 1-Get Metotları
    def getRandevuID(self) -> int:
        return self.__randevuID

    def getRandevuTarih(self) -> str:
        return self.__randevuTarih

    def getRandevuSaat(self) -> str:
        return self.__randevuSaat

    def getRandevuDoktorID(self) -> int:
        return self.__randevuDoktorID

    def getRandevuHastaID(self) -> int:
        return self.__randevuHastaID

    # 2-Set Metotları
    def setRandevuID(self, randevuID: int) -> None:
        self.__randevuID = randevuID

    def setRandevuTarih(self, randevuTarih: str) -> None:
        self.__randevuTarih = randevuTarih

    def setRandevuSaat(self, randevuSaat: str) -> None:
        self.__randevuSaat = randevuSaat

    def setRandevuDoktorID(self, randevuDoktorID: int) -> None:
        self.__randevuDoktorID = randevuDoktorID

    def setRandevuHastaID(self, randevuHastaID: int) -> None:
        self.__randevuHastaID = randevuHastaID


# Tıbbi Rapor Sınıfı
class TibbiRapor:
    def __init__(self, raporID: int, raporURL: str, raporTarih: str,  raporIcerik: str, raporHastaID: int) -> None:
        self.__raporID = raporID
        self.__raporURL = raporURL
        self.__raporTarih = raporTarih
        self.__raporIcerik = raporIcerik
        self.__raporHastaID = raporHastaID
        print("Tıbbi Rapor")

    # Get/Set Metotları
    # 1-Get Metotları
    def getRaporID(self) -> int:
        return self.__raporID

    def getRaporURL(self) -> str:
        return self.__raporURL

    def getRaporTarih(self) -> str:
        return self.__raporTarih

    def getRaporIcerik(self) -> str:
        return self.__raporIcerik

    def getRaporHastaID(self) -> int:
        return self.__raporHastaID

    # 2-Set Metotları
    def setRaporID(self, raporID: int) -> None:
        self.__raporID = raporID

    def setRaporURL(self, raporURL: str) -> None:
        self.__raporURL = raporURL

    def setRaporTarih(self, raporTarih: str) -> None:
        self.__raporTarih = raporTarih

    def setRaporIcerik(self, raporIcerik: str) -> None:
        self.__raporIcerik = raporIcerik

    def setRaporHastaID(self, raporHastaID: int) -> None:
        self.__raporHastaID = raporHastaID


# İnsan Sınıfı
class Insan(UserMixin):
    def __init__(self, ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str) -> None:
        self.__ad = ad
        self.__soyad = soyad
        self.__dogumTarih = dogumTarih
        self.__cinsiyet = cinsiyet
        self.__telefonNo = telefonNo
        self.__adres = adres

    # Get/Set Metotları
    # 1-Get Metotları
    def getAd(self) -> str:
        return self.__ad

    def getSoyad(self) -> str:
        return self.__soyad

    def getDogumTarih(self) -> str:
        return self.__dogumTarih

    def getCinsiyet(self) -> str:
        return self.__cinsiyet

    def getTelefonNo(self) -> str:
        return self.__telefonNo

    def getAdres(self) -> str:
        return self.__adres

    # 2-Set Metotları
    def setAd(self, ad: str) -> None:
        self.__ad = ad

    def setSoyad(self, soyad: str) -> None:
        self.__soyad = soyad

    def setDogumTarih(self, dogumTarih: str) -> None:
        self.__dogumTarih = dogumTarih

    def setCinsiyet(self, cinsiyet: str) -> None:
        self.__cinsiyet = cinsiyet

    def setTelefonNo(self, telefonNo: str) -> None:
        self.__telefonNo = telefonNo

    def setAdres(self, adres: str) -> None:
        self.__adres = adres


# Hasta Sınıfı
class Hasta(Insan):
    # Ad, Soyad, Doğum Tarihi, Cinsiyet, Telefon, Adres, Sağlık Sigortası, Tc Kimlik No (Hasta ID), Eposta, Parola
    def __init__(self, ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                 saglikSigorta: str, hastaID: int, eposta: str, parola: str) -> None:
        super().__init__(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres)
        self.__hastaID = hastaID
        self.id = self.__hastaID
        self.__saglikSigorta = saglikSigorta
        self.__eposta = eposta
        self.__parola = parola
        self.__sinif = "Hasta"

    # Get/Set Metotları
    # 1-Get Metotları
    def getSinif(self):
        return self.__sinif

    def getHastaID(self) -> int:
        return self.__hastaID

    def getSaglikSigorta(self) -> str:
        return self.__saglikSigorta

    def getEposta(self) -> str:
        return self.__eposta

    def getParola(self) -> str:
        return self.__parola

    # 2-Set Metotları
    def setHastaID(self, hastaID: int) -> None:
        self.__hastaID = hastaID

    def setSaglikSigorta(self, saglikSigorta: str) -> None:
        self.__saglikSigorta = saglikSigorta

    def setEposta(self, eposta: str) -> None:
        self.__eposta = eposta

    def setParola(self, parola: str) -> None:
        self.__parola = parola

    def __str__(self):
        return (f'Hasta ID: {self.getHastaID()}\n'
                f'Parola: {self.getParola()}\n'
                f'İsim: {self.getAd()} {self.getSoyad()}\n'
                f'Eposta: {self.getEposta()}\n'
                f'Telefon: {self.getTelefonNo()}\n'
                f'Sigorta: {self.getSaglikSigorta()}\n'
                f'Adres: {self.getAdres()}\n'
                f'Doğum Tarihi: {self.getDogumTarih()}\n'
                f'Cinsiyet: {self.getCinsiyet()}\n')


# Doktor Sınıfı
class Doktor(Insan):
    def __init__(self, ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                 uzmanlikAlan: str, calisilanHastane: str, doktorID: int, eposta: str, parola: str) -> None:
        super().__init__(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres)
        self.__doktorID = doktorID
        self.id = self.__doktorID
        self.__uzmanlikAlan = uzmanlikAlan
        self.__calisilanHastane = calisilanHastane
        self.__parola = parola
        self.__eposta = eposta
        self.__sinif = "Doktor"

    # Get/Set Metotları
    # 1-Get Metotları
    def getSinif(self):
        return self.__sinif

    def getDoktorID(self) -> int:
        return self.__doktorID

    def getUzmanlikAlan(self) -> str:
        return self.__uzmanlikAlan

    def getCalisilanHastane(self) -> str:
        return self.__calisilanHastane

    def getParola(self) -> str:
        return self.__parola

    def getEposta(self) -> str:
        return self.__eposta

    # 2-Set Metotları
    def setDoktorID(self, doktorID: int) -> None:
        self.__doktorID = doktorID

    def setUzmanlikAlan(self, uzmanlikAlan: str) -> None:
        self.__uzmanlikAlan = uzmanlikAlan

    def setCalisilanHastane(self, calisilanHastane: str) -> None:
        self.__calisilanHastane = calisilanHastane

    def setParola(self, parola: str) -> None:
        self.__parola = parola

    def setEposta(self, eposta: str) -> None:
        self.__eposta = eposta

    def __str__(self):
        return (f'Ad: {self.getAd()}\n'
                f'Soyad: {self.getSoyad()}\n'
                f'Doğum Tarihi: {self.getDogumTarih()}\n'
                f'Cinsiyet: {self.getCinsiyet()}\n'
                f'Telefon Numarası: {self.getTelefonNo()}\n'
                f'Adres: {self.getAdres()}\n'
                f'Doktor ID: {self.getDoktorID()}\n'
                f'Uzmanlık Alanı: {self.getUzmanlikAlan()}\n'
                f'Çalışılan Hastane: {self.getCalisilanHastane()}\n'
                f'Parola: {self.getParola()}\n')


# Yönetici Sınıfı
class Yonetici(Insan):
    def __init__(self, ad: str, soyad: str, dogumTarih: str, cinsiyet: str, telefonNo: str, adres: str,
                 yoneticiID: int, eposta: str, parola: str) -> None:
        super().__init__(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres)
        self.__yoneticiID = yoneticiID
        self.id = self.__yoneticiID
        self.__parola = parola
        self.__eposta = eposta
        self.__sinif = "Yonetici"

    # Get/Set Metotları
    # 1-Get Metotları
    def getSinif(self):
        return self.__sinif

    def getYoneticiID(self) -> int:
        return self.__yoneticiID

    def getParola(self) -> str:
        return self.__parola

    def getEposta(self) -> str:
        return self.__eposta

    # 2-Set Metotları
    def setYoneticiID(self, yoneticiID: int) -> None:
        self.__yoneticiID = yoneticiID

    def setParola(self, parola: str) -> None:
        self.__parola = parola

    def setEposta(self, eposta: str) -> None:
        self.__eposta = eposta

    def __str__(self):
        return (f'Ad: {self.getAd()}\n'
                f'Soyad: {self.getSoyad()}\n'
                f'Doğum Tarihi: {self.getDogumTarih()}\n'
                f'Cinsiyet: {self.getCinsiyet()}\n'
                f'Telefon Numarası: {self.getTelefonNo()}\n'
                f'Adres: {self.getAdres()}\n'
                f'Yönetici ID: {self.getYoneticiID()}\n'
                f'Parola: {self.getParola()}\n')
