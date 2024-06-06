from flask import Flask, render_template, request, redirect, url_for, jsonify
from flask_login import LoginManager, login_user, logout_user, login_required, current_user
from werkzeug.security import generate_password_hash, check_password_hash
import sqlFonksiyonlar
import siniflar
import sqlite3

app = Flask(__name__, template_folder='templates')
tablo_olusma = False  # Sql Tablosu ilk defa oluşturulurken kullanılan kısım

# Login - Logout metotları için gerekenler
app.secret_key = "354122"
login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'girisYap'

@login_manager.user_loader
def load_user(user_id):
    hastaBilgi: list = sqlFonksiyonlar.HastaAra(user_id)
    if len(hastaBilgi) > 0:
        hasta = siniflar.Hasta(*hastaBilgi[0])
        return hasta

    yoneticiBilgi: list = sqlFonksiyonlar.YoneticiAra(user_id)
    if len(yoneticiBilgi) > 0:
        yonetici = siniflar.Yonetici(*yoneticiBilgi[0])
        return yonetici

    doktorBilgi: list = sqlFonksiyonlar.DoktorAra(user_id)
    if len(doktorBilgi) > 0:
        doktor = siniflar.Doktor(*doktorBilgi[0])
        return doktor

    return None

@app.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('home'))

@app.route('/')
def home():
    global tablo_olusma
    if not tablo_olusma:
        tablo_olusma = True
        sqlFonksiyonlar.tabloOlustur()
        sqlFonksiyonlar.yoneticileriOlustur()

    return render_template('home.html', title='anasayfa')

@app.route('/about')
def about():
    return render_template('about.html', title='hakkımızda')

@app.route('/yoneticiler')
def yoneticiler():
    return render_template('yoneticiler.html', title='yoneticileriz')


# GirişOl Fonksiyonunda kullanılacak fonksiyonlar
# 1 - Seçilen giriş türüne ait tabloda girilen ID'ye sahip kullanıcı var mı bakar
def kullanici_bilgi_sorgula(girisTur, tcKimlikNo):
    if girisTur == "Yonetici":
        return sqlFonksiyonlar.YoneticiAra(tcKimlikNo)
    elif girisTur == "Doktor":
        return sqlFonksiyonlar.DoktorAra(tcKimlikNo)
    elif girisTur == "Hasta":
        return sqlFonksiyonlar.HastaAra(tcKimlikNo)
    return None

# 2 - Girilen ID'yi kullanarak bulduğu tablodaki bilgilerle nesne oluşturur ve döndürür
def kullanici_olustur(girisTur, bilgiler):
    if girisTur == "Yonetici":
        return siniflar.Yonetici(*bilgiler)
    elif girisTur == "Doktor":
        return siniflar.Doktor(*bilgiler)
    elif girisTur == "Hasta":
        return siniflar.Hasta(*bilgiler)
    return None

# 3 - Girilen bilgilerin hepsi doğruysa uygun sayfanın bağlantı fonksiyonu ismini döndürür
def kullanici_menu_yonlendir(girisTur):
    if girisTur == "Yonetici":
        return "yoneticiMenu"
    elif girisTur == "Doktor":
        return "doktorMenu"
    elif girisTur == "Hasta":
        return "hastaMenu"
    return None

@app.route('/girisYap', methods=['GET', 'POST'])
def girisYap():
    if request.method == 'POST':
        # Form verilerini değişkenlere ata
        girisTur = request.form.get("girisTur")
        tcKimlikNo = request.form.get("tcKimlikNo")
        parola = request.form.get("parola")

        # Değişkenleri kontrol et
        if girisTur == "Secilmemis":
            hata_mesaj = "Giriş Türünü seçiniz!"
            return jsonify({'error': hata_mesaj}), 400

        elif tcKimlikNo == "":
            hata_mesaj = "T.C Kimlik Numarası giriniz!"
            return jsonify({'error': hata_mesaj}), 400

        elif not tcKimlikNo.isdigit():
            hata_mesaj = "T.C Kimlik Numarası sadece sayılardan oluşmalıdır!"
            return jsonify({'error': hata_mesaj}), 400

        elif len(tcKimlikNo) != 11:
            hata_mesaj = "T.C Kimlik Numarasını doğru uzunlukta giriniz!"
            return jsonify({'error': hata_mesaj}), 400

        elif parola == "":
            hata_mesaj = "Parola kısmı boş bırakılamaz!"
            return jsonify({'error': hata_mesaj}), 400

        kullaniciBilgi = kullanici_bilgi_sorgula(girisTur, tcKimlikNo)
        if kullaniciBilgi and len(kullaniciBilgi) > 0:
            bilgiler = kullaniciBilgi[0]
            print(bilgiler)
            *kalan, kullaniciID, kullanici_posta, kullanici_parola = bilgiler
            print(kullaniciID, kullanici_posta, kullanici_parola)
            if check_password_hash(kullanici_parola, parola):  # Girilen şifreyi kontrol et
                kullanici = kullanici_olustur(girisTur, bilgiler)
                logout_user()
                login_user(kullanici)

                return jsonify({'girisTur': girisTur}), 200  # Menüye yönlendir
            else:
                hata_mesaj = "Yanlış parola!"
                return jsonify({'error': hata_mesaj}), 400
        else:
            hata_mesaj = "Böyle bir kayıtlı kullanıcı yok!"
            return jsonify({'error': hata_mesaj}), 400


    return render_template('girisYap.html', title='girisYap')


@app.route('/kayitOl', methods=['GET', 'POST'])
def kayitOl():
    if request.method == 'POST':
        ad = request.form.get('ad')
        soyad = request.form.get('soyad')
        eposta = request.form.get('eposta')
        parola = request.form.get('parola')
        parola_onay = request.form.get('parola_onay')
        cinsiyet = request.form.get('cinsiyet')
        hastaID = request.form.get('hastaID')
        dogumTarih = request.form.get('dogumTarih')
        sigorta = request.form.get('sigorta')
        adres = request.form.get('adres')
        telNo = request.form.get('telefonNo')
        kosulOnayla = request.form.get('kosulOnayla')


        while True:
            if ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola == "":
                hata_mesaj = "Parola kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola_onay == "":
                hata_mesaj = "Parola Onay kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola != parola_onay:
                hata_mesaj = "Parolalar eşleşmiyor!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyeti seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif hastaID == "":
                hata_mesaj = "T.C Kimlik Numarası kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "T.C Kimlik Numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(hastaID) != 11:
                hata_mesaj = "T.C Kimlik Numarasını doğru uzunlukta giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum Tarihi Boş Bırakılamaz"
                return jsonify({'error': hata_mesaj}), 400

            elif sigorta == "Secilmemis":
                hata_mesaj = "Sağlık Sigortanızı seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telNo == "":
                hata_mesaj = "Telefon Numarası kısmı boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telNo.isdigit():
                hata_mesaj = "Telefon Numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telNo) != 10:
                hata_mesaj = "Telefon Numarasını doğru uzunlukta giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif kosulOnayla == "Onaysiz":
                hata_mesaj = "Şartlar ve Koşulları onaylayınız!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                # Girilen T.C Kimlik Numarasına sahip hasta var mı kontrol
                hastaBilgiler = sqlFonksiyonlar.HastaAra(hastaID)
                yoneticiBilgiler = sqlFonksiyonlar.YoneticiAra(hastaID)
                doktorBilgiler = sqlFonksiyonlar.DoktorAra(hastaID)
                tarihListe = dogumTarih.split("-")
                dogumTarih = tarihListe[0] + "/" + str(int(tarihListe[1])) + "/" + str(int(tarihListe[2]))

                if len(hastaBilgiler) > 0 or len(yoneticiBilgiler) > 0 or len(doktorBilgiler) > 0:  # Varsa Çalışacak Kısım
                    hata_mesaj = "Bu T.C Kimlik Numarasına sahip biri zaten kayıtlı!"
                    return jsonify({'error': hata_mesaj}), 400

                else:  # Yoksa Çalışacak Kısım
                    hasta = siniflar.Hasta(ad=ad, soyad=soyad, dogumTarih=dogumTarih, cinsiyet=cinsiyet,
                                           telefonNo=telNo, adres=adres, saglikSigorta=sigorta,
                                           hastaID=hastaID, eposta=eposta, parola=generate_password_hash(parola))
                    sqlFonksiyonlar.HastaEkle(hasta)
                    return jsonify(), 200



    return render_template('kayitOl.html', title='kayitOl')



# Hasta Menüleri
@app.route('/hastaMenu')
@login_required
def hastaMenu():
    # Hasta olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Hasta":
        return redirect(url_for('girisYap'))

    return render_template('hastaMenu.html', title='hastaMenu')


@app.route('/hastaMenu/hastaProfil', methods=['GET', 'POST'])
@login_required
def hastaProfil():
    # Hasta olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Hasta":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        durum = request.form.get("durum")
        if durum == "bilgiDegisme":
            return jsonify(), 200

        elif durum == "bilgiİptal":
            current_user_verileri = {
                "ad": current_user.getAd(),
                "soyad": current_user.getSoyad(),
                "eposta": current_user.getEposta(),
                "cinsiyet": current_user.getCinsiyet(),
                "dogumTarih": current_user.getDogumTarih(),
                "sigorta": current_user.getSaglikSigorta(),
                "adres": current_user.getAdres(),
                "telefonNo": current_user.getTelefonNo()
            }
            return jsonify(current_user_verileri), 200

        elif durum == "bilgiGuncelleme":

            ad = request.form.get("ad")
            soyad = request.form.get("soyad")
            eposta = request.form.get("eposta")
            cinsiyet = request.form.get("cinsiyet")
            dogumTarih = request.form.get("dogumTarih")
            sigorta = request.form.get("sigorta")
            adres = request.form.get("adres")
            telefonNo = request.form.get("telefonNo")

            if ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sigorta == "Secilmemis":
                hata_mesaj = "Sigorta seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                dogumTarih.replace("-", "/")
                sqlFonksiyonlar.HastaBilgiGuncelle(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres, sigorta,
                                                   eposta, current_user.getHastaID())
                current_user.setAd(ad)
                current_user.setSoyad(soyad)
                current_user.setDogumTarih(dogumTarih)
                current_user.setCinsiyet(cinsiyet)
                current_user.setTelefonNo(telefonNo)
                current_user.setAdres(adres)
                current_user.setSaglikSigorta(sigorta)
                current_user.setEposta(eposta)

                return jsonify(), 200

    return render_template('hastaMenuler/hastaProfil.html', title='hastaProfil')


@app.route('/hastaMenu/hastaRandevuMenu', methods=['GET', 'POST'])
@login_required
def hastaRandevuMenu():
    # Hasta olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Hasta":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':

        islem = int(request.form.get('islem'))

        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            randevular = conn.execute('''SELECT * FROM Randevular 
                                        WHERE hastaID = ? 
                                        ORDER BY randevu_tarih DESC
                                        LIMIT ? 
                                        OFFSET ?''',
                                      (current_user.getHastaID(), per_page, offset)).fetchall()
            conn.close()
            randevu_list = [dict(row) for row in randevular]
            for randevu in randevu_list:
                doktorBilgi: tuple = sqlFonksiyonlar.DoktorAra(randevu.get('doktorID'))[0]
                ad, soyad, *kalan, uzmanlik, sube, doktorID, eposta, parola = doktorBilgi
                randevu['doktor_isim'] = ad + " " + soyad
                randevu['doktor_uzmanlik'] = uzmanlik
                randevu['hastane_sube'] = sube
            return jsonify(randevu_list), 200

        elif islem == 1:  # En baştaki şubelerin yazılması
            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()

            subeVerileri = cur.execute("SELECT * FROM Hastaneler").fetchall()
            sube_list = {}
            for sube in subeVerileri:
                departmanlar = []
                sube_isim, *kalan = sube
                departman_bilgiler = cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ?", (sube_isim,)).fetchall()
                for departman in departman_bilgiler:
                    sube_ad, departman_isim, *kalan = departman
                    departmanlar.append(departman_isim)
                sube_list[sube_isim] = departmanlar

            con.close()
            return jsonify(list(sube_list.keys())), 200

        elif islem == 2:  # Seçilen şubeye göre departman gelmesi

            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()

            subeVerileri = cur.execute("SELECT * FROM Hastaneler").fetchall()
            sube_list = {}
            for sube in subeVerileri:
                departmanlar = []
                sube_isim, *kalan = sube
                departman_bilgiler = cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ?",
                                                 (sube_isim,)).fetchall()
                for departman in departman_bilgiler:
                    sube_ad, departman_isim, *kalan = departman
                    departmanlar.append(departman_isim)
                sube_list[sube_isim] = departmanlar

            con.close()

            sube_secim = request.form.get('sube')
            if sube_secim in sube_list:
                return jsonify(sube_list[sube_secim]), 200
            return jsonify([]), 200

        elif islem == 3:  # Seçilen deparmtana göre doktor gelmesi

            sube = request.form.get('sube')
            departman = request.form.get('department')

            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()
            departmanDoktorlar = cur.execute('''
                    SELECT doktor_ad, doktor_soyad, doktorID
                    FROM Doktorlar 
                    WHERE doktor_uzmanlikAlan = ? AND doktor_calisilanHastane = ? ORDER BY doktor_ad
                    ''', (departman, sube, )).fetchall()
            doktor_dict = {}
            for doktor in departmanDoktorlar:
                ad, soyad, doktorID, *kalan = doktor
                isim = ad + " " + soyad
                doktor_dict[isim] = doktorID

            return jsonify(doktor_dict)

        elif islem == 4:
            sube = request.form.get('sube')
            uzmanlik = request.form.get('uzmanlik')
            doktor_id = request.form.get('doktorID')
            tarih = request.form.get('tarih')
            saat = request.form.get('saat')

            if sube == "Secilmemis":
                hata_mesaj = "Şube seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif uzmanlik == "Secilmemis":
                hata_mesaj = "Departman seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif doktor_id == "Secilmemis":
                hata_mesaj = "Doktor seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif tarih == "":
                hata_mesaj = "Tarih seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) < 2024:
                hata_mesaj = "Yıl en az 2024 olmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) > 2027:
                hata_mesaj = "Yıl en fazla 2027 olabilir!"
                return jsonify({'error': hata_mesaj}), 400

            elif saat == "":
                hata_mesaj = "Saat seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[1]) % 5 != 0:
                hata_mesaj = "Dakika 5 ve katları şeklinde seçilmelidir!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) < 8:
                hata_mesaj = "Saat Sabah 8'den öncesi için randevu alınamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) > 21:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu alınamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) >= 21 and int(saat.split(":")[1]) > 0:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu alınamaz!"
                return jsonify({'error': hata_mesaj}), 400

            tarih = tarih.replace("-", "/")
            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()
            randevular = cur.execute('''
                    SELECT count(*)
                    FROM Randevular 
                    WHERE 
                    randevu_tarih = ? AND randevu_saat = ? 
                    AND (hastaID = ? OR doktorID = ?)''', (tarih, saat, current_user.getHastaID(), doktor_id,)).fetchall()

            randevu_sayi, *kalan = randevular[0]
            if int(randevu_sayi) > 0:
                con.commit()
                con.close()
                hata_mesaj = "Girilen tarih ve saat dolu başka tarih ve saati deneyin!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                toplamRandevuSayi, *kalan = cur.execute("SELECT MAX(randevuID) FROM Randevular").fetchall()[0]
                if toplamRandevuSayi is None:
                    toplamRandevuSayi = 0
                randevu_id = int(toplamRandevuSayi) + 1
                randevu = siniflar.Randevu(randevu_id, doktor_id, current_user.getHastaID(), tarih, saat)
                sqlFonksiyonlar.RandevuEkle(randevu)
                con.commit()
                con.close()
                return jsonify(), 200
        elif islem == 5:
            randevuID = request.form.get('randevuID')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE hastaID = ? AND randevuID = ?",
                                             (current_user.getHastaID(), randevuID, )).fetchall()
                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevuya sahip değilsiniz!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    cur.execute("DELETE FROM Randevular WHERE hastaID = ? AND randevuID = ?",
                                (current_user.getHastaID(), randevuID, ))
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == 6:
            randevuID = request.form.get('randevuID')
            saat = request.form.get('saat')
            tarih = request.form.get('tarih')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            elif tarih == "":
                hata_mesaj = "Tarih seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) < 2024:
                hata_mesaj = "Yıl en az 2024 olmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) > 2027:
                hata_mesaj = "Yıl en fazla 2027 olabilir!"
                return jsonify({'error': hata_mesaj}), 400

            elif saat == "":
                hata_mesaj = "Saat seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[1]) % 5 != 0:
                hata_mesaj = "Dakika 5 ve katları şeklinde seçilmelidir!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) < 8:
                hata_mesaj = "Saat Sabah 8'den öncesi için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) > 21:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) >= 21 and int(saat.split(":")[1]) > 0:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                tarih = tarih.replace("-", "/")
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE hastaID = ? AND randevuID = ?",
                                             (current_user.getHastaID(), randevuID,)).fetchall()

                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevuya sahip değilsiniz!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:

                    randevu_id, doktor_id, hasta_id, *kalan = randevuVeriler[0]
                    hastaVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE hastaID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                               (hasta_id, saat, tarih,)).fetchall()

                    doktorVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE doktorID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                                (doktor_id, saat, tarih,)).fetchall()

                    if len(hastaVeriler) > 0:
                        hata_mesaj = "O tarih ve saatte başka bir randevunuz var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    elif len(doktorVeriler) > 0:
                        hata_mesaj = "Doktorun o tarih ve saatte başka bir randevunuz var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    else:
                        cur.execute('''UPDATE Randevular 
                                        SET randevu_tarih = ?, randevu_saat = ?
                                         WHERE hastaID = ? AND randevuID = ?''',
                                    (tarih, saat, hasta_id, randevuID,))
                        con.commit()
                        con.close()
                        return jsonify(), 200


    return render_template('hastaMenuler/hastaRandevuMenu.html', title='hastaRandevuMenu')


@app.route('/hastaMenu/hastaRaporMenu', methods=['GET', 'POST'])
@login_required
def hastaRaporMenu():
    # Hasta olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Hasta":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        islem = request.form.get("islem")
        if islem == "0":
            hastaID = current_user.getHastaID()
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            raporlar = conn.execute('''SELECT * FROM Raporlar 
                                                    WHERE hastaID = ? 
                                                    ORDER BY rapor_tarih DESC
                                                    LIMIT ? 
                                                    OFFSET ?''',
                                    (hastaID, per_page, offset)).fetchall()
            conn.close()

            rapor_list = [dict(row) for row in raporlar]
            return jsonify(rapor_list), 200


    return render_template('hastaMenuler/hastaRaporMenu.html', title='hastaRaporMenu')


# Doktor Menüleri
@app.route('/doktorMenu', methods=['GET', 'POST'])
@login_required
def doktorMenu():
    # Doktor olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Doktor":
        return redirect(url_for('girisYap'))

    return render_template('doktorMenu.html', title='doktorMenu')


@app.route('/doktorMenu/doktorProfil', methods=['GET', 'POST'])
@login_required
def doktorProfil():
    # Doktor olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Doktor":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        durum = request.form.get("durum")
        if durum == "bilgiDegisme":
            return jsonify(), 200

        elif durum == "bilgiİptal":
            current_user_verileri = {
                "ad": current_user.getAd(),
                "soyad": current_user.getSoyad(),
                "eposta": current_user.getEposta(),
                "cinsiyet": current_user.getCinsiyet(),
                "dogumTarih": current_user.getDogumTarih(),
                "uzmanlik": current_user.getUzmanlikAlan(),
                "sube": current_user.getCalisilanHastane(),
                "adres": current_user.getAdres(),
                "telefonNo": current_user.getTelefonNo()
            }
            return jsonify(current_user_verileri), 200

        elif durum == "bilgiGuncelleme":

            ad = request.form.get("ad")
            soyad = request.form.get("soyad")
            eposta = request.form.get("eposta")
            cinsiyet = request.form.get("cinsiyet")
            dogumTarih = request.form.get("dogumTarih")
            adres = request.form.get("adres")
            telefonNo = request.form.get("telefonNo")

            if ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                dogumTarih = dogumTarih.replace("-", "/")
                sqlFonksiyonlar.DoktorBilgiGuncelle(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres, eposta, current_user.getDoktorID())
                current_user.setAd(ad)
                current_user.setSoyad(soyad)
                current_user.setDogumTarih(dogumTarih)
                current_user.setCinsiyet(cinsiyet)
                current_user.setTelefonNo(telefonNo)
                current_user.setAdres(adres)
                current_user.setEposta(eposta)

                return jsonify(), 200

    return render_template('doktorMenuler/doktorProfil.html', title='doktorProfil')


@app.route('/doktorMenu/doktorHastaMenu', methods=['GET', 'POST'])
@login_required
def doktorHastaMenu():
    # Doktor olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Doktor":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':

        islem = int(request.form.get('islem'))
        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page
            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            hastaBilgiler = conn.execute('''SELECT Randevular.hastaID FROM Randevular, Hastalar 
                                        WHERE doktorID = ? AND Randevular.hastaID = Hastalar.hastaID 
                                        ORDER BY Hastalar.hasta_dogumTarih DESC
                                        LIMIT ? 
                                        OFFSET ?''',
                                         (current_user.getDoktorID(), per_page, offset)).fetchall()
            conn.close()
            hastaList = [dict(row) for row in hastaBilgiler]
            for hasta in hastaList:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(hasta.get('hastaID'))[0]
                ad, soyad, dogumTarih, cinsiyet, telefon, adres, sigorta, *kalan = hastaBilgi
                hasta['isim'] = ad + " " + soyad
                hasta['dogumTarih'] = dogumTarih
                hasta['cinsiyet'] = cinsiyet
                hasta['telefon'] = telefon
                hasta['adres'] = adres
                hasta['sigorta'] = sigorta + " Sigorta"
            return jsonify(hastaList), 200

    return render_template('doktorMenuler/doktorHastaMenu.html', title='doktorHastaMenu')


@app.route('/doktorMenu/doktorRandevuMenu', methods=['GET', 'POST'])
@login_required
def doktorRandevuMenu():
    # Doktor olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Doktor":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':

        islem = int(request.form.get('islem'))
        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            randevular = conn.execute('''SELECT * FROM Randevular 
                                                WHERE doktorID = ? 
                                                ORDER BY randevu_tarih DESC
                                                LIMIT ? 
                                                OFFSET ?''',
                                      (current_user.getDoktorID(), per_page, offset)).fetchall()
            conn.close()
            randevu_list = [dict(row) for row in randevular]
            for randevu in randevu_list:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(randevu.get('hastaID'))[0]
                ad, soyad, cinsiyet, telefon, sigorta, *kalan = hastaBilgi
                randevu['hasta_isim'] = ad + " " + soyad
                randevu['doktorID'] = current_user.getDoktorID()
                randevu['doktor_uzmanlik'] = current_user.getUzmanlikAlan()
                randevu['hastane_sube'] = current_user.getCalisilanHastane()
            return jsonify(randevu_list), 200

        elif islem == 1:
            randevuID = request.form.get('randevuID')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE doktorID = ? AND randevuID = ?",
                                             (current_user.getDoktorID(), randevuID,)).fetchall()
                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevuya sahip değilsiniz!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    cur.execute("DELETE FROM Randevular WHERE doktorID = ? AND randevuID = ?",
                                (current_user.getDoktorID(), randevuID,))
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == 2:
            randevuID = request.form.get('randevuID')
            saat = request.form.get('saat')
            tarih = request.form.get('tarih')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            elif tarih == "":
                hata_mesaj = "Tarih seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) < 2024:
                hata_mesaj = "Yıl en az 2024 olmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) > 2027:
                hata_mesaj = "Yıl en fazla 2027 olabilir!"
                return jsonify({'error': hata_mesaj}), 400

            elif saat == "":
                hata_mesaj = "Saat seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[1]) % 5 != 0:
                hata_mesaj = "Dakika 5 ve katları şeklinde seçilmelidir!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) < 8:
                hata_mesaj = "Saat Sabah 8'den öncesi için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) > 21:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) >= 21 and int(saat.split(":")[1]) > 0:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                tarih = tarih.replace("-", "/")
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE doktorID = ? AND randevuID = ?",
                                             (current_user.getDoktorID(), randevuID,)).fetchall()

                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevuya sahip değilsiniz!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:

                    randevu_id, doktor_id, hasta_id, *kalan = randevuVeriler[0]
                    hastaVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE hastaID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                               (hasta_id, saat, tarih,)).fetchall()

                    doktorVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE doktorID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                                (doktor_id, saat, tarih,)).fetchall()

                    if len(hastaVeriler) > 0:
                        hata_mesaj = "Hastanın o tarih ve saatte başka bir randevusu var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    elif len(doktorVeriler) > 0:
                        hata_mesaj = "O tarih ve saatte başka bir randevunuz var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    else:
                        cur.execute('''UPDATE Randevular 
                                        SET randevu_tarih = ?, randevu_saat = ?
                                         WHERE doktorID = ? AND randevuID = ?''',
                                    (tarih, saat, current_user.getDoktorID(), randevuID,))
                        con.commit()
                        con.close()
                        return jsonify(), 200

    return render_template('doktorMenuler/doktorRandevuMenu.html', title='doktorRandevuMenu')


@app.route('/doktorMenu/doktorRaporMenu', methods=['GET', 'POST'])
@login_required
def doktorRaporMenu():
    # Doktor olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Doktor":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        islem = request.form.get("islem")
        if islem == "0":
            doktorID = current_user.getDoktorID()
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            raporlar = conn.execute('''
                    SELECT rap.raporID, rap.hastaID, rap.rapor_tarih, rap.rapor_icerik, rap.raporURL
                    FROM Raporlar AS rap, Randevular 
                    WHERE rap.hastaID = Randevular.hastaID AND Randevular.doktorID = ?
                    ORDER BY rap.rapor_tarih DESC
                    LIMIT ? 
                    OFFSET ?''',
                    (doktorID, per_page, offset)).fetchall()
            conn.close()

            rapor_list = [dict(row) for row in raporlar]
            for rapor in rapor_list:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(rapor.get('hastaID'))[0]
                ad, soyad, *kalan = hastaBilgi
                rapor['hasta_isim'] = ad + " " + soyad
            return jsonify(rapor_list), 200

        elif islem == "1":
            raporID = request.form.get('raporID')
            doktorID = current_user.getDoktorID()
            if raporID == "":
                hata_mesaj = "RaporID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                raporVeriler = cur.execute(
                    '''
                    SELECT * 
                    FROM Raporlar AS rap, Randevular AS ran
                    WHERE rap.raporID = ? AND rap.hastaID = ran.hastaID AND ran.doktorID = ?
                    ''', (raporID, doktorID,)).fetchall()

                if len(raporVeriler) == 0:
                    hata_mesaj = "Bu rapor sizin hastalarınıza ait değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                else:
                    cur.execute("DELETE FROM Raporlar WHERE raporID = ?", (raporID,))
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == "2":
            doktorID = current_user.getDoktorID()
            raporID = request.form.get('raporID')
            hastaID = request.form.get('hastaID')
            tarih = request.form.get('raporTarih')
            raporIcerik = request.form.get('raporIcerik')
            raporURL = request.form.get('raporURL')

            if raporID == "":
                hata_mesaj = "RaporID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not raporID.isdigit():
                hata_mesaj = "RaporID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif hastaID == "":
                hata_mesaj = "HastaID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "HastaID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif tarih == "":
                hata_mesaj = "Tarih giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporIcerik == "":
                hata_mesaj = "Rapor içeriği giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporURL == "":
                hata_mesaj = "RaporURL giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                raporVeriler = cur.execute(
                    '''
                    SELECT * 
                    FROM Raporlar AS rap, Randevular AS ran
                    WHERE rap.raporID = ? AND rap.hastaID = ran.hastaID AND ran.doktorID = ?
                    ''', (raporID, doktorID,)).fetchall()

                randevuVeriler = cur.execute(
                    '''
                    SELECT * 
                    FROM Randevular AS ran
                    WHERE  ran.hastaID = ? AND ran.doktorID = ?
                    ''', (hastaID, doktorID,)).fetchall()

                raporBilgi = sqlFonksiyonlar.RaporAra(raporID)

                if len(raporVeriler) == 0:
                    hata_mesaj = "Bu rapor sizin hastalarınıza ait değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                elif len(randevuVeriler) == 0:
                    hata_mesaj = "Girilen HastaID'ye sahip kişi sizin hastalarınzıdan değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                elif len(raporBilgi) == 0:
                    hata_mesaj = "Girilen RaporID'ye ait bir rapor mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                else:
                    tarih = tarih.replace("-", "/")
                    sqlFonksiyonlar.RaporBilgisiGuncelle(raporID, raporURL, tarih, raporIcerik, hastaID)
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == "3":
            doktorID = current_user.getDoktorID()
            hastaID = request.form.get('hastaID')
            tarih = request.form.get('raporTarih')
            raporIcerik = request.form.get('raporIcerik')
            raporURL = request.form.get('raporURL')

            if hastaID == "":
                hata_mesaj = "HastaID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "HastaID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif tarih == "":
                hata_mesaj = "Tarih giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporIcerik == "":
                hata_mesaj = "Rapor içeriği giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporURL == "":
                hata_mesaj = "RaporURL giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                hastaBilgiler = cur.execute(
                    '''
                    SELECT * 
                    FROM Hastalar
                    WHERE hastaID = ? 
                    ''', (hastaID, )).fetchall()

                randevuVeriler = cur.execute(
                    '''
                    SELECT * 
                    FROM Randevular AS ran
                    WHERE  ran.hastaID = ? AND ran.doktorID = ?
                    ''', (hastaID, doktorID,)).fetchall()

                if len(randevuVeriler) == 0:
                    hata_mesaj = "Girilen HastaID'ye sahip kişi sizin hastalarınzıdan değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                else:
                    tarih = tarih.replace("-", "/")

                    cur.execute("SELECT MAX(raporID) FROM Raporlar")
                    raporBilgiler = cur.fetchall()
                    raporID, *kalan = raporBilgiler[0]
                    if raporID is None:
                        raporID = 0
                    raporID += 1

                    rapor = siniflar.TibbiRapor(raporID, raporURL, tarih, raporIcerik, hastaID)
                    sqlFonksiyonlar.RaporEkle(rapor)
                    con.commit()
                    con.close()
                    return jsonify(), 200




    return render_template('doktorMenuler/doktorRaporMenu.html', title='doktorRaporMenu')

# Yönetici Menüleri
@app.route('/yoneticiMenu', methods=['GET', 'POST'])
@login_required
def yoneticiMenu():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        page = int(request.form.get("page"))

        per_page = 10
        offset = (page - 1) * per_page

        conn = sqlFonksiyonlar.connect_db()
        conn.row_factory = sqlite3.Row
        randevular = conn.execute('SELECT * FROM Randevular LIMIT ? OFFSET ?', (per_page, offset)).fetchall()
        conn.close()

        randevu_list = [dict(row) for row in randevular]
        print(randevu_list)
        return jsonify(randevu_list), 200

    # Render template only on GET requests
    return render_template('yoneticiMenu.html', title='yoneticiMenu')



@app.route('/yoneticiMenu/yoneticiHastaMenu', methods=['GET', 'POST'])
@login_required
def yoneticiHastaMenu():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':

        islem = int(request.form.get('islem'))
        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page
            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            hastaBilgiler = conn.execute('''SELECT hastaID FROM Hastalar 
                                        ORDER BY Hastalar.hasta_dogumTarih DESC
                                        LIMIT ? 
                                        OFFSET ?''',
                                         (per_page, offset,)).fetchall()
            conn.close()
            hastaList = [dict(row) for row in hastaBilgiler]
            for hasta in hastaList:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(hasta.get('hastaID'))[0]
                ad, soyad, dogumTarih, cinsiyet, telefon, adres, sigorta, *kalan = hastaBilgi
                hasta['isim'] = ad + " " + soyad
                hasta['dogumTarih'] = dogumTarih
                hasta['cinsiyet'] = cinsiyet
                hasta['telefon'] = telefon
                hasta['adres'] = adres
                hasta['sigorta'] = sigorta + " Sigorta"
            return jsonify(hastaList), 200

        elif islem == 1:  # Hasta Ekleme
            hastaID = request.form.get('hastaID')
            ad = request.form.get('ad')
            soyad = request.form.get('soyad')
            eposta = request.form.get('eposta')
            parola = request.form.get('parola')
            parola_onay = request.form.get('parola_onay')
            cinsiyet = request.form.get('cinsiyet')
            sigorta = request.form.get('sigorta')
            dogumTarih = request.form.get('dogumTarih')
            adres = request.form.get('adres')
            telefonNo = request.form.get('telefonNo')

            if hastaID == "":
                hata_mesaj = "T.C Kimlik No boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "T.C Kimlik No sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola == "":
                hata_mesaj = "Parola boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola_onay == "":
                hata_mesaj = "Parola Onay boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola Onay sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(parola) != int(parola_onay):
                hata_mesaj = "Parolalar eşleşmiyor!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sigorta == "Secilmemis":
                hata_mesaj = "Sigorta seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                hastaBilgi = sqlFonksiyonlar.HastaAra(hastaID)
                if len(hastaBilgi) > 0:
                    hata_mesaj = "Bu T.C Kimlik No'ya sahip bir hasta bulunmakta!"
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    tarihListe = dogumTarih.split("-")
                    dogumTarih = tarihListe[0] + "/" + str(int(tarihListe[1])) + "/" + str(int(tarihListe[2]))
                    con = sqlFonksiyonlar.connect_db()
                    cur = con.cursor()

                    hasta = siniflar.Hasta(ad, soyad, dogumTarih, cinsiyet, telefonNo,
                                           adres, sigorta, hastaID, eposta, generate_password_hash(parola))
                    sqlFonksiyonlar.HastaEkle(hasta)

                    con.commit()  # Değişiklikleri kaydet
                    con.close()  # Bağlantıyı kapat
                    return jsonify(), 200

        elif islem == 2:  # Silme işlemi
            hastaID = request.form.get('hastaID')

            if hastaID == "":
                hata_mesaj = "HastaID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Hastalar WHERE hastaID = ?",
                                             (hastaID,)).fetchall()
                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir hasta mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    sqlFonksiyonlar.HastaSil(hastaID)
                    con.commit()
                    con.close()
                    return jsonify(), 200
        elif islem == 3:  # Hasta Bilgi Güncellemesi
            hastaID = request.form.get('hastaID')
            ad = request.form.get('ad')
            soyad = request.form.get('soyad')
            eposta = request.form.get('eposta')
            parola = request.form.get('parola')
            parola_onay = request.form.get('parola_onay')
            cinsiyet = request.form.get('cinsiyet')
            sigorta = request.form.get('sigorta')
            dogumTarih = request.form.get('dogumTarih')
            adres = request.form.get('adres')
            telefonNo = request.form.get('telefonNo')

            if hastaID == "":
                hata_mesaj = "T.C Kimlik No boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "T.C Kimlik No sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola == "":
                hata_mesaj = "Parola boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola_onay == "":
                hata_mesaj = "Parola Onay boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola Onay sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(parola) != int(parola_onay):
                hata_mesaj = "Parolalar eşleşmiyor!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sigorta == "Secilmemis":
                hata_mesaj = "Sigorta seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                hastaBilgi = sqlFonksiyonlar.HastaAra(hastaID)
                if len(hastaBilgi) == 0:
                    hata_mesaj = "Bu T.C Kimlik No'ya sahip bir hasta bulunmamakta!"
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    tarihListe = dogumTarih.split("-")
                    dogumTarih = tarihListe[0] + "/" + str(int(tarihListe[1])) + "/" + str(int(tarihListe[2]))
                    con = sqlFonksiyonlar.connect_db()
                    cur = con.cursor()

                    guncelVeriler = [ad, soyad, dogumTarih, cinsiyet, telefonNo, adres, sigorta, eposta,
                                     generate_password_hash(parola), hastaID,]
                    cur.execute('''
                    UPDATE Hastalar 
                    SET hasta_ad = ?, hasta_soyad = ?, hasta_dogumTarih = ?, hasta_cinsiyet = ?,
                    hasta_telefon = ?, hasta_adres = ?, hasta_saglikSigorta = ?, hasta_eposta = ?, hasta_parola = ?
                    WHERE hastaID = ?''', guncelVeriler)

                    con.commit()  # Değişiklikleri kaydet
                    con.close()  # Bağlantıyı kapat
                    return jsonify(), 200


    return render_template('yoneticiMenuler/yoneticiHastaMenu.html', title='yoneticiHastaMenu')


@app.route('/yoneticiMenu/yoneticiDoktorMenu', methods=['GET', 'POST'])
@login_required
def yoneticiDoktorMenu():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':

        islem = int(request.form.get('islem'))
        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page
            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            doktorBilgiler = conn.execute('''SELECT doktorID FROM Doktorlar 
                                        ORDER BY Doktorlar.doktor_dogumTarih DESC
                                        LIMIT ? 
                                        OFFSET ?''',
                                          (per_page, offset,)).fetchall()
            conn.close()
            doktorList = [dict(row) for row in doktorBilgiler]
            for doktor in doktorList:
                doktorBilgi: tuple = sqlFonksiyonlar.DoktorAra(doktor.get('doktorID'))[0]
                ad, soyad, dogumTarih, cinsiyet, telefon, adres, uzmanlik, sube, *kalan = doktorBilgi
                doktor['isim'] = ad + " " + soyad
                doktor['dogumTarih'] = dogumTarih
                doktor['cinsiyet'] = cinsiyet
                doktor['telefon'] = telefon
                doktor['adres'] = adres
                doktor['uzmanlik'] = uzmanlik
                doktor['sube'] = sube
            return jsonify(doktorList), 200

        elif islem == 10:  # En baştaki şubelerin yazılması
            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()

            subeVerileri = cur.execute("SELECT * FROM Hastaneler").fetchall()
            sube_list = {}
            for sube in subeVerileri:
                departmanlar = []
                sube_isim, *kalan = sube
                departman_bilgiler = cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ?",
                                                 (sube_isim,)).fetchall()
                for departman in departman_bilgiler:
                    sube_ad, departman_isim, *kalan = departman
                    departmanlar.append(departman_isim)
                sube_list[sube_isim] = departmanlar

            con.close()
            return jsonify(list(sube_list.keys())), 200

        elif islem == 11:  # Seçilen şubeye göre departman gelmesi

            con = sqlFonksiyonlar.connect_db()
            cur = con.cursor()

            subeVerileri = cur.execute("SELECT * FROM Hastaneler").fetchall()
            sube_list = {}
            for sube in subeVerileri:
                departmanlar = []
                sube_isim, *kalan = sube
                departman_bilgiler = cur.execute("SELECT * FROM HastaneBilgiler WHERE hastaneSube = ?",
                                                 (sube_isim,)).fetchall()
                for departman in departman_bilgiler:
                    sube_ad, departman_isim, *kalan = departman
                    departmanlar.append(departman_isim)
                sube_list[sube_isim] = departmanlar

            con.close()

            sube_secim = request.form.get('sube')
            if sube_secim in sube_list:
                return jsonify(sube_list[sube_secim]), 200
            return jsonify([]), 200

        elif islem == 1:  # Doktor Ekleme
            doktorID = request.form.get('doktorID')
            ad = request.form.get('ad')
            soyad = request.form.get('soyad')
            eposta = request.form.get('eposta')
            parola = request.form.get('parola')
            parola_onay = request.form.get('parola_onay')
            cinsiyet = request.form.get('cinsiyet')
            sube = request.form.get('sube')
            uzmanlik = request.form.get('uzmanlik')
            dogumTarih = request.form.get('dogumTarih')
            adres = request.form.get('adres')
            telefonNo = request.form.get('telefonNo')

            if doktorID == "":
                hata_mesaj = "T.C Kimlik No boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not doktorID.isdigit():
                hata_mesaj = "T.C Kimlik No sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola == "":
                hata_mesaj = "Parola boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola_onay == "":
                hata_mesaj = "Parola Onay boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola Onay sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(parola) != int(parola_onay):
                hata_mesaj = "Parolalar eşleşmiyor!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sube == "Secilmemis":
                hata_mesaj = "Şube seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif uzmanlik == "Secilmemis":
                hata_mesaj = "Departman Adı seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                doktorBilgi = sqlFonksiyonlar.DoktorAra(doktorID)
                if len(doktorBilgi) > 0:
                    hata_mesaj = "Bu T.C Kimlik No'ya sahip bir doktor bulunmakta!"
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    tarihListe = dogumTarih.split("-")
                    dogumTarih = tarihListe[0] + "/" + str(int(tarihListe[1])) + "/" + str(int(tarihListe[2]))
                    con = sqlFonksiyonlar.connect_db()
                    cur = con.cursor()
                    doktor = siniflar.Doktor(ad, soyad, dogumTarih, cinsiyet, telefonNo, adres,
                                             uzmanlik, sube, doktorID, eposta, generate_password_hash(parola))

                    sqlFonksiyonlar.DoktorEkle(doktor)

                    con.commit()  # Değişiklikleri kaydet
                    con.close()  # Bağlantıyı kapat
                    return jsonify(), 200

        elif islem == 2:  # Silme işlemi
            doktorID = request.form.get('doktorID')

            if doktorID == "":
                hata_mesaj = "T.C Kimlik No giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                doktorVeriler = cur.execute("SELECT * FROM Doktorlar WHERE doktorID = ?",
                                            (doktorID,)).fetchall()
                if len(doktorVeriler) == 0:
                    hata_mesaj = "Böyle bir doktor mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    sqlFonksiyonlar.DoktorSil(doktorID)
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == 3:  # Doktor Bilgi Güncellemesi
            doktorID = request.form.get('doktorID')
            ad = request.form.get('ad')
            soyad = request.form.get('soyad')
            eposta = request.form.get('eposta')
            parola = request.form.get('parola')
            parola_onay = request.form.get('parola_onay')
            cinsiyet = request.form.get('cinsiyet')
            sube = request.form.get('sube')
            uzmanlik = request.form.get('uzmanlik')
            sigorta = request.form.get('sigorta')
            dogumTarih = request.form.get('dogumTarih')
            adres = request.form.get('adres')
            telefonNo = request.form.get('telefonNo')

            if doktorID == "":
                hata_mesaj = "T.C Kimlik No boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not doktorID.isdigit():
                hata_mesaj = "T.C Kimlik No sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola == "":
                hata_mesaj = "Parola boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif parola_onay == "":
                hata_mesaj = "Parola Onay boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not parola.isdigit():
                hata_mesaj = "Parola Onay sadece sayılardan oluşmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(parola) != int(parola_onay):
                hata_mesaj = "Parolalar eşleşmiyor!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sube == "Secilmemis":
                hata_mesaj = "Şube seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif uzmanlik == "Secilmemis":
                hata_mesaj = "Departman Adı seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif sigorta == "Secilmemis":
                hata_mesaj = "Sigorta seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                doktorBilgi = sqlFonksiyonlar.DoktorAra(doktorID)
                if len(doktorBilgi) == 0:
                    hata_mesaj = "Bu T.C Kimlik No'ya sahip bir doktor bulunmamakta!"
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    tarihListe = dogumTarih.split("-")
                    dogumTarih = tarihListe[0] + "/" + str(int(tarihListe[1])) + "/" + str(int(tarihListe[2]))
                    con = sqlFonksiyonlar.connect_db()
                    cur = con.cursor()

                    guncelVeriler = [ad, soyad, dogumTarih, cinsiyet, telefonNo, adres, uzmanlik, sube, eposta,
                                     generate_password_hash(parola), doktorID,]
                    cur.execute('''
                    UPDATE Doktorlar
                    SET doktor_ad = ?, doktor_soyad = ?, doktor_dogumTarih = ?, doktor_cinsiyet = ?,
                    doktor_telefon = ?, doktor_adres = ?, doktor_uzmanlikAlan = ?, 
                    doktor_calisilanHastane = ?, doktor_eposta = ?, doktor_parola = ?
                    WHERE doktorID = ?''', guncelVeriler)

                    con.commit()  # Değişiklikleri kaydet
                    con.close()  # Bağlantıyı kapat
                    return jsonify(), 200

    return render_template('yoneticiMenuler/yoneticiDoktorMenu.html', title='yoneticiDoktorMenu')


@app.route('/yoneticiMenu/yoneticiRandevuMenu', methods=['GET', 'POST'])
@login_required
def yoneticiRandevuMenu():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))
        # Render template only on GET requests

    if request.method == 'POST':

        islem = int(request.form.get('islem'))
        if islem == 0:  # Randevu Menüsüyle bir iş yoksa
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            randevular = conn.execute('''SELECT * FROM Randevular 
                                                ORDER BY randevu_tarih DESC
                                                LIMIT ? 
                                                OFFSET ?''',
                                      (per_page, offset, )).fetchall()
            conn.close()
            randevu_list = [dict(row) for row in randevular]
            for randevu in randevu_list:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(randevu.get('hastaID'))[0]
                hasta_ad, hasta_soyad, hasta_cinsiyet, hasta_telefon, hasta_sigorta, *kalan = hastaBilgi
                doktorBilgi: tuple = sqlFonksiyonlar.DoktorAra(randevu.get('doktorID'))[0]
                (doktor_ad, doktor_soyad, doktor_dogumTarih, doktor_cinsiyet, doktor_telefon, doktor_adres, doktor_uzmanlik,
                 doktor_calisilanHastane, *kalan) = doktorBilgi
                randevu['hasta_isim'] = hasta_ad + " " + hasta_soyad
                randevu['doktor_isim'] = doktor_ad + " " + doktor_soyad
                randevu['hastane_sube'] = doktor_calisilanHastane
                randevu['doktor_uzmanlik'] = doktor_uzmanlik

            return jsonify(randevu_list), 200

        elif islem == 1:
            randevuID = request.form.get('randevuID')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE randevuID = ?",
                                             (randevuID,)).fetchall()
                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevu mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    cur.execute("DELETE FROM Randevular WHERE randevuID = ?",
                                (randevuID,))
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == 2:
            randevuID = request.form.get('randevuID')
            saat = request.form.get('saat')
            tarih = request.form.get('tarih')

            if randevuID == "":
                hata_mesaj = "RandevuID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            elif tarih == "":
                hata_mesaj = "Tarih seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) < 2024:
                hata_mesaj = "Yıl en az 2024 olmalı!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(tarih.split("-")[0]) > 2027:
                hata_mesaj = "Yıl en fazla 2027 olabilir!"
                return jsonify({'error': hata_mesaj}), 400

            elif saat == "":
                hata_mesaj = "Saat seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[1]) % 5 != 0:
                hata_mesaj = "Dakika 5 ve katları şeklinde seçilmelidir!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) < 8:
                hata_mesaj = "Saat Sabah 8'den öncesi için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) > 21:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif int(saat.split(":")[0]) >= 21 and int(saat.split(":")[1]) > 0:
                hata_mesaj = "Saat Akşam 9'dan sonrası için randevu ayarlanamaz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                tarih = tarih.replace("-", "/")
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                randevuVeriler = cur.execute("SELECT * FROM Randevular WHERE randevuID = ?",
                                             (randevuID,)).fetchall()

                if len(randevuVeriler) == 0:
                    hata_mesaj = "Böyle bir randevu mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:

                    randevu_id, doktor_id, hasta_id, *kalan = randevuVeriler[0]
                    hastaVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE hastaID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                               (hasta_id, saat, tarih,)).fetchall()

                    doktorVeriler = cur.execute('''
                    SELECT * 
                    FROM Randevular 
                    WHERE doktorID = ? AND randevu_saat = ? AND randevu_tarih = ? ''',
                                                (doktor_id, saat, tarih,)).fetchall()

                    if len(hastaVeriler) > 0:
                        hata_mesaj = "Hastanın o tarih ve saatte başka bir randevusu var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    elif len(doktorVeriler) > 0:
                        hata_mesaj = "Doktorun o tarih ve saatte başka bir randevusu var!"
                        con.commit()
                        con.close()
                        return jsonify({'error': hata_mesaj}), 400

                    else:
                        cur.execute('''UPDATE Randevular 
                                        SET randevu_tarih = ?, randevu_saat = ?
                                         WHERE doktorID = ? AND randevuID = ?''',
                                    (tarih, saat, doktor_id, randevuID,))
                        con.commit()
                        con.close()
                        return jsonify(), 200

    return render_template('yoneticiMenuler/yoneticiRandevuMenu.html', title='yoneticiRandevuMenu')


@app.route('/yoneticiMenu/yoneticiRaporMenu', methods=['GET', 'POST'])
@login_required
def yoneticiRaporMenu():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        islem = request.form.get("islem")
        if islem == "0":
            page = int(request.form.get("page"))
            per_page = 10
            offset = (page - 1) * per_page

            conn = sqlFonksiyonlar.connect_db()
            conn.row_factory = sqlite3.Row
            raporlar = conn.execute('''
                    SELECT rap.raporID, rap.hastaID, rap.rapor_tarih, rap.rapor_icerik, rap.raporURL
                    FROM Raporlar AS rap
                    ORDER BY rap.rapor_tarih DESC
                    LIMIT ? 
                    OFFSET ?''',
                    (per_page, offset)).fetchall()
            conn.close()

            rapor_list = [dict(row) for row in raporlar]
            for rapor in rapor_list:
                hastaBilgi: tuple = sqlFonksiyonlar.HastaAra(rapor.get('hastaID'))[0]
                ad, soyad, *kalan = hastaBilgi
                rapor['hasta_isim'] = ad + " " + soyad
            return jsonify(rapor_list), 200

        elif islem == "1":
            raporID = request.form.get('raporID')
            if raporID == "":
                hata_mesaj = "RaporID giriniz!"
                return jsonify({'error': hata_mesaj}), 400
            else:

                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                raporVeriler = cur.execute(
                    '''
                    SELECT * 
                    FROM Raporlar AS rap
                    WHERE rap.raporID = ?
                    ''', (raporID, )).fetchall()

                if len(raporVeriler) == 0:
                    hata_mesaj = "Girilen RaporID'ye sahip bir rapor yok!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400
                else:
                    cur.execute("DELETE FROM Raporlar WHERE raporID = ?", (raporID,))
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == "2":
            raporID = request.form.get('raporID')
            hastaID = request.form.get('hastaID')
            tarih = request.form.get('raporTarih')
            raporIcerik = request.form.get('raporIcerik')
            raporURL = request.form.get('raporURL')

            if raporID == "":
                hata_mesaj = "RaporID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not raporID.isdigit():
                hata_mesaj = "RaporID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif hastaID == "":
                hata_mesaj = "HastaID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "HastaID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif tarih == "":
                hata_mesaj = "Tarih giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporIcerik == "":
                hata_mesaj = "Rapor içeriği giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporURL == "":
                hata_mesaj = "RaporURL giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                hastaBilgiler = cur.execute(
                    '''
                    SELECT * 
                    FROM Hastalar
                    WHERE hastaID = ? 
                    ''', (hastaID, )).fetchall()

                raporBilgi = sqlFonksiyonlar.RaporAra(raporID)

                if len(hastaBilgiler) == 0:
                    hata_mesaj = "Girilen HastaID'ye sahip bir hasta mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                elif len(raporBilgi) == 0:
                    hata_mesaj = "Girilen RaporID'ye ait bir rapor mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                else:
                    tarih = tarih.replace("-", "/")
                    sqlFonksiyonlar.RaporBilgisiGuncelle(raporID, raporURL, tarih, raporIcerik, hastaID)
                    con.commit()
                    con.close()
                    return jsonify(), 200

        elif islem == "3":
            hastaID = request.form.get('hastaID')
            tarih = request.form.get('raporTarih')
            raporIcerik = request.form.get('raporIcerik')
            raporURL = request.form.get('raporURL')

            if hastaID == "":
                hata_mesaj = "HastaID giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not hastaID.isdigit():
                hata_mesaj = "HastaID sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif tarih == "":
                hata_mesaj = "Tarih giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporIcerik == "":
                hata_mesaj = "Rapor içeriği giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif raporURL == "":
                hata_mesaj = "RaporURL giriniz!"
                return jsonify({'error': hata_mesaj}), 400

            else:
                con = sqlFonksiyonlar.connect_db()
                cur = con.cursor()
                hastaBilgiler = cur.execute(
                    '''
                    SELECT * 
                    FROM Hastalar
                    WHERE hastaID = ? 
                    ''', (hastaID, )).fetchall()


                if len(hastaBilgiler) == 0:
                    hata_mesaj = "Girilen HastaID'ye sahip bir hasta mevcut değil!"
                    con.commit()
                    con.close()
                    return jsonify({'error': hata_mesaj}), 400

                else:
                    tarih = tarih.replace("-", "/")

                    cur.execute("SELECT MAX(raporID) FROM Raporlar")
                    raporBilgiler = cur.fetchall()
                    raporID, *kalan = raporBilgiler[0]
                    if raporID is None:
                        raporID = 0
                    raporID += 1

                    rapor = siniflar.TibbiRapor(raporID, raporURL, tarih, raporIcerik, hastaID)
                    sqlFonksiyonlar.RaporEkle(rapor)
                    con.commit()
                    con.close()
                    return jsonify(), 200

    return render_template('yoneticiMenuler/yoneticiRaporMenu.html', title='yoneticiRaporMenu')


@app.route('/yoneticiMenu/yoneticiProfil', methods=['GET', 'POST'])
@login_required
def yoneticiProfil():
    # Yönetici olarak mı giriş yapılmış diye kontrol eden kısım
    if not current_user.getSinif() == "Yonetici":
        return redirect(url_for('girisYap'))

    if request.method == 'POST':
        durum = request.form.get("durum")
        if durum == "bilgiDegisme":
            return jsonify(), 200

        elif durum == "bilgiİptal":
            current_user_verileri = {
                "ad": current_user.getAd(),
                "soyad": current_user.getSoyad(),
                "eposta": current_user.getEposta(),
                "cinsiyet": current_user.getCinsiyet(),
                "dogumTarih": current_user.getDogumTarih(),
                "adres": current_user.getAdres(),
                "telefonNo": current_user.getTelefonNo()
            }
            return jsonify(current_user_verileri), 200

        elif durum == "bilgiGuncelleme":

            ad = request.form.get("ad")
            soyad = request.form.get("soyad")
            eposta = request.form.get("eposta")
            cinsiyet = request.form.get("cinsiyet")
            dogumTarih = request.form.get("dogumTarih")
            adres = request.form.get("adres")
            telefonNo = request.form.get("telefonNo")

            if ad == "":
                hata_mesaj = "Ad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in ad):
                hata_mesaj = "Ad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif soyad == "":
                hata_mesaj = "Soyad boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not all(x.isalpha() or x.isspace() for x in soyad):
                hata_mesaj = "Soyad sadece harflerden oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif eposta == "":
                hata_mesaj = "Eposta boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif cinsiyet == "Secilmemis":
                hata_mesaj = "Cinsiyet seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif dogumTarih == "":
                hata_mesaj = "Doğum tarihi seçiniz!"
                return jsonify({'error': hata_mesaj}), 400

            elif adres == "":
                hata_mesaj = "Adres boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif telefonNo == "":
                hata_mesaj = "Telefon numarası boş bırakılamaz!"
                return jsonify({'error': hata_mesaj}), 400

            elif not telefonNo.isdigit():
                hata_mesaj = "Telefon numarası sadece sayılardan oluşmalıdır!"
                return jsonify({'error': hata_mesaj}), 400

            elif len(telefonNo) != 10:
                hata_mesaj = "Telefon numarası doğru uzunlukta olmalıdır!"
                return jsonify({'error': hata_mesaj}), 400
            else:
                dogumTarih.replace("-", "/")
                sqlFonksiyonlar.YoneticiBilgiGuncelle(ad, soyad, dogumTarih, cinsiyet,
                                                      telefonNo, adres, eposta, current_user.getYoneticiID())

                current_user.setAd(ad)
                current_user.setSoyad(soyad)
                current_user.setDogumTarih(dogumTarih)
                current_user.setCinsiyet(cinsiyet)
                current_user.setTelefonNo(telefonNo)
                current_user.setAdres(adres)
                current_user.setEposta(eposta)

                return jsonify(), 200

    return render_template('yoneticiMenuler/yoneticiProfil.html', title='yoneticiProfil')



if __name__ == '__main__':
    app.run()
