import time
import asyncio

import User as user
import HashTable as hashtable
import LinkedList as linked
import json

import networkx as nx
import matplotlib.pyplot as plt
import numpy as np

def hashtagTrendGraf(hashtags, values, bolge):
    plt.rcdefaults()

    hashtagler = hashtags
    degerler = values

    font1 = {'family': 'serif', 'color': '#96E1B9', 'size': 20}
    font2 = {'family': 'serif', 'color': '#96E1B9', 'size': 15}

    y_pos = np.arange(len(hashtagler))
    fig = plt.figure(figsize=(32, 18), facecolor="#001942")
    plt.axes().set_facecolor("#001942")
    plt.grid(axis='x', color='#253145')
    plt.barh(y_pos, degerler, align='center', alpha=0.5, color="#05f551")
    plt.yticks(y_pos, hashtagler, fontsize=24, fontdict=font2)
    plt.ylabel('HASHTAGLER', fontsize=36, fontdict=font2)
    plt.xlabel('KULLANIM', fontsize=36, fontdict=font2)
    plt.title(f'{bolge} Bölgesine ait Trend Olan HashTagler', fontsize=40, fontdict=font1)

    plt.savefig("graphFiles/trendHashTags.png")
    plt.show()

# 1- Kullanıcı bilgilerini nesne oluşturup hashtable içine atamayı ve
# kullanıcı adlarını linkedlist içinde tutup geri döndürmeyi sağlayan metot
async def userOlustur(bilgi, table: hashtable.HashTable, usernames: linked.LinkedList):
    username = bilgi["username"]
    name = bilgi["name"]
    followers_count = bilgi["followers_count"]
    following_count = bilgi["following_count"]
    language = bilgi["language"]
    region = bilgi["region"]
    tweets = bilgi["tweets"]
    following = bilgi["following"]
    followers = bilgi["followers"]
    nesne = user.User(username, name, followers_count, following_count,
                      language, region, tweets, following, followers)

    table.insert(bilgi["username"], nesne)
    usernames.baslangicaDugumEkle(username)

async def userTopluOlustur(bilgiIndex, veri, table: hashtable.HashTable, usernames: linked.LinkedList):
    topluFonksiyonlar = await asyncio.gather(
        userOlustur(veri[bilgiIndex], table, usernames),
        userOlustur(veri[bilgiIndex + 1], table, usernames),
        userOlustur(veri[bilgiIndex + 2], table, usernames),
        userOlustur(veri[bilgiIndex + 3], table, usernames),
        userOlustur(veri[bilgiIndex + 4], table, usernames),
        userOlustur(veri[bilgiIndex + 5], table, usernames),
        userOlustur(veri[bilgiIndex + 6], table, usernames),
        userOlustur(veri[bilgiIndex + 7], table, usernames),
        userOlustur(veri[bilgiIndex + 8], table, usernames),
        userOlustur(veri[bilgiIndex + 9], table, usernames),
        userOlustur(veri[bilgiIndex + 10], table, usernames),
        userOlustur(veri[bilgiIndex + 11], table, usernames),
        userOlustur(veri[bilgiIndex + 12], table, usernames),
        userOlustur(veri[bilgiIndex + 13], table, usernames),
        userOlustur(veri[bilgiIndex + 14], table, usernames),
        userOlustur(veri[bilgiIndex + 15], table, usernames),
        userOlustur(veri[bilgiIndex + 16], table, usernames),
        userOlustur(veri[bilgiIndex + 17], table, usernames),
        userOlustur(veri[bilgiIndex + 18], table, usernames),
        userOlustur(veri[bilgiIndex + 19], table, usernames),
        userOlustur(veri[bilgiIndex + 20], table, usernames),
        userOlustur(veri[bilgiIndex + 21], table, usernames),
        userOlustur(veri[bilgiIndex + 22], table, usernames),
        userOlustur(veri[bilgiIndex + 23], table, usernames),
        userOlustur(veri[bilgiIndex + 24], table, usernames),
        userOlustur(veri[bilgiIndex + 25], table, usernames),
        userOlustur(veri[bilgiIndex + 26], table, usernames),
        userOlustur(veri[bilgiIndex + 27], table, usernames),
        userOlustur(veri[bilgiIndex + 28], table, usernames),
        userOlustur(veri[bilgiIndex + 29], table, usernames),
        userOlustur(veri[bilgiIndex + 30], table, usernames),
        userOlustur(veri[bilgiIndex + 31], table, usernames),
        userOlustur(veri[bilgiIndex + 32], table, usernames),
        userOlustur(veri[bilgiIndex + 33], table, usernames),
        userOlustur(veri[bilgiIndex + 34], table, usernames),
        userOlustur(veri[bilgiIndex + 35], table, usernames),
        userOlustur(veri[bilgiIndex + 36], table, usernames),
        userOlustur(veri[bilgiIndex + 37], table, usernames),
        userOlustur(veri[bilgiIndex + 38], table, usernames),
        userOlustur(veri[bilgiIndex + 39], table, usernames),
        userOlustur(veri[bilgiIndex + 40], table, usernames),
        userOlustur(veri[bilgiIndex + 41], table, usernames),
        userOlustur(veri[bilgiIndex + 42], table, usernames),
        userOlustur(veri[bilgiIndex + 43], table, usernames),
        userOlustur(veri[bilgiIndex + 44], table, usernames),
        userOlustur(veri[bilgiIndex + 45], table, usernames),
        userOlustur(veri[bilgiIndex + 46], table, usernames),
        userOlustur(veri[bilgiIndex + 47], table, usernames),
        userOlustur(veri[bilgiIndex + 48], table, usernames),
        userOlustur(veri[bilgiIndex + 49], table, usernames),
        userOlustur(veri[bilgiIndex + 50], table, usernames),
        userOlustur(veri[bilgiIndex + 51], table, usernames),
        userOlustur(veri[bilgiIndex + 52], table, usernames),
        userOlustur(veri[bilgiIndex + 53], table, usernames),
        userOlustur(veri[bilgiIndex + 54], table, usernames),
        userOlustur(veri[bilgiIndex + 55], table, usernames),
        userOlustur(veri[bilgiIndex + 56], table, usernames),
        userOlustur(veri[bilgiIndex + 57], table, usernames),
        userOlustur(veri[bilgiIndex + 58], table, usernames),
        userOlustur(veri[bilgiIndex + 59], table, usernames),
        userOlustur(veri[bilgiIndex + 60], table, usernames),
        userOlustur(veri[bilgiIndex + 61], table, usernames),
        userOlustur(veri[bilgiIndex + 62], table, usernames),
        userOlustur(veri[bilgiIndex + 63], table, usernames),
        userOlustur(veri[bilgiIndex + 64], table, usernames),
        userOlustur(veri[bilgiIndex + 65], table, usernames),
        userOlustur(veri[bilgiIndex + 66], table, usernames),
        userOlustur(veri[bilgiIndex + 67], table, usernames),
        userOlustur(veri[bilgiIndex + 68], table, usernames),
        userOlustur(veri[bilgiIndex + 69], table, usernames),
        userOlustur(veri[bilgiIndex + 70], table, usernames),
        userOlustur(veri[bilgiIndex + 71], table, usernames),
        userOlustur(veri[bilgiIndex + 72], table, usernames),
        userOlustur(veri[bilgiIndex + 73], table, usernames),
        userOlustur(veri[bilgiIndex + 74], table, usernames),
        userOlustur(veri[bilgiIndex + 75], table, usernames),
        userOlustur(veri[bilgiIndex + 76], table, usernames),
        userOlustur(veri[bilgiIndex + 77], table, usernames),
        userOlustur(veri[bilgiIndex + 78], table, usernames),
        userOlustur(veri[bilgiIndex + 79], table, usernames),
        userOlustur(veri[bilgiIndex + 80], table, usernames),
        userOlustur(veri[bilgiIndex + 81], table, usernames),
        userOlustur(veri[bilgiIndex + 82], table, usernames),
        userOlustur(veri[bilgiIndex + 83], table, usernames),
        userOlustur(veri[bilgiIndex + 84], table, usernames),
        userOlustur(veri[bilgiIndex + 85], table, usernames),
        userOlustur(veri[bilgiIndex + 86], table, usernames),
        userOlustur(veri[bilgiIndex + 87], table, usernames),
        userOlustur(veri[bilgiIndex + 88], table, usernames),
        userOlustur(veri[bilgiIndex + 89], table, usernames),
        userOlustur(veri[bilgiIndex + 90], table, usernames),
        userOlustur(veri[bilgiIndex + 91], table, usernames),
        userOlustur(veri[bilgiIndex + 92], table, usernames),
        userOlustur(veri[bilgiIndex + 93], table, usernames),
        userOlustur(veri[bilgiIndex + 94], table, usernames),
        userOlustur(veri[bilgiIndex + 95], table, usernames),
        userOlustur(veri[bilgiIndex + 96], table, usernames),
        userOlustur(veri[bilgiIndex + 97], table, usernames),
        userOlustur(veri[bilgiIndex + 98], table, usernames),
        userOlustur(veri[bilgiIndex + 99], table, usernames),
    )

async def TopluTopluOlustur(bilIndex, veri, table, usernames):
    topluFonksiyonlar = await asyncio.gather(
        userTopluOlustur(bilIndex, veri, table, usernames),
        userTopluOlustur(bilIndex + 100, veri, table, usernames),
        userTopluOlustur(bilIndex + 200, veri, table, usernames),
        userTopluOlustur(bilIndex + 300, veri, table, usernames),
        userTopluOlustur(bilIndex + 400, veri, table, usernames),
        userTopluOlustur(bilIndex + 500, veri, table, usernames),
        userTopluOlustur(bilIndex + 600, veri, table, usernames),
        userTopluOlustur(bilIndex + 700, veri, table, usernames),
        userTopluOlustur(bilIndex + 800, veri, table, usernames),
        userTopluOlustur(bilIndex + 900, veri, table, usernames)
    )


# 1- Kullanıcı bilgilerini nesne oluşturup hashtable içine atamayı ve
# kullanıcı adlarını linkedlist içinde tutup geri döndürmeyi sağlayan metot
def hashTableAta(table: hashtable.HashTable, veri):
    # Kullanıcı adlarını tutacak LinkedList
    usernames = linked.LinkedList()
    bilIndex = 0
    for bilgiIndex in range(len(veri) // 1000):
        titi = time.time()
        print(f"{bilgiIndex * 1000 + 1000}'e kadar olan kullanıcılar kaydediliyor...")
        asyncio.run(TopluTopluOlustur(bilIndex, veri, table, usernames))
        bilIndex += 1000
        print(f"{bilgiIndex * 1000 + 1000}'e kadar olan kullanıcılar kaydedildi. Geçen süre: {time.time() - titi}")

    return table, usernames


# 2 - Kullanıcı Bilgilerilerinden İlgi Alanlarını alıp hashtable içerisinde
# saklayan kısım
def hashTableIlgiAlan(dataTable: hashtable.HashTable, usernames: linked.LinkedList):
    # İlgi Alanlarını tutacak hashtable'in oluşturulması
    ilgiAlanTable = hashtable.HashTable(200000)
    ilgiAlanLinkedList = linked.LinkedList()

    # Her bir kullanıcının ilgi alanlarının bulunması
    simdiki_user = usernames.baslangic
    while simdiki_user:
        # Kullanıcı datasını tutan hashtable içinde kullanıcı verisini arayıp bulan kısım
        bilgi = dataTable.search(simdiki_user.veri)
        ilgiAlanLL = bilgi.get_ilgiAlanlariLL()

        # Kullanıcının tüm ilgi alanlarını hashtable içerisine ekleyen kısım
        ilgiAlan_simdiki = ilgiAlanLL.baslangic
        while ilgiAlan_simdiki:
            # Eğer ilgi alanı hashtable içerisinde mevcutsa değerlerini güncelleyen kısım
            if ilgiAlan_simdiki.veri in ilgiAlanTable:
                tempDegerLL = ilgiAlanTable.search(ilgiAlan_simdiki.veri)
                tempDegerLL.baslangic.veri = tempDegerLL.baslangic.veri + 1
                tempDegerLL.sonaDugumEkle(simdiki_user.veri)

                ilgiAlanTable.insert(ilgiAlan_simdiki.veri.lower(), tempDegerLL)

            # Eğer ilgi alanı mevcut değilse oluşturan kısım
            else:
                # İlgi Alanına dair bilgileri tutacak linkedlist
                # baslangic degeri kaç kişinin bu ilgi alanına sahip olduğu
                # sonraki değerler bu ilgi alanına sahip olan kullanıcı isimleri
                degerLL = linked.LinkedList()
                degerLL.sonaDugumEkle(1)
                degerLL.sonaDugumEkle(simdiki_user.veri)

                ilgiAlanTable.insert(ilgiAlan_simdiki.veri, degerLL)
                ilgiAlanLinkedList.baslangicaDugumEkle(ilgiAlan_simdiki.veri)

            ilgiAlan_simdiki = ilgiAlan_simdiki.sonraki

        simdiki_user = simdiki_user.sonraki

    return ilgiAlanTable, ilgiAlanLinkedList


# 3 - İlgi aranı Hashtable'ı içerisinden ilgi alanı bulup döndüren fonksiyon
def ilgiAlaniBul(dataTable: hashtable.HashTable, ilgiAlan):
    # İlgi alanı mevcut mu diye kontrol eden kısım
    # mevcut ise çalışan kısım
    if ilgiAlan in dataTable:
        print("İlgi alanı bulundu ve döndürüldü.")
        ilgiAlanLL = dataTable.search(ilgiAlan)

        # İlgi Alanıyla ilgili bilgileri ekrana yazdıran kısım
        simdiki = ilgiAlanLL.baslangic
        while simdiki:
            simdiki = simdiki.sonraki

        return ilgiAlanLL
    # mevcut değilse çalışan kısım
    else:
        print("Böyle bir ilgi alanı mevcut değil.")
        return None


# ASYNC FONKSİYONLARI
async def yuzSonraki(degistirilecekNode: linked.Node):
    yuzSonrakiNode = (degistirilecekNode.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.
                      sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
                      .sonraki.sonraki)
    return yuzSonrakiNode


# 1 tane Takipçi veya Takip Edilen Bilgisi var mı kontrol eden fonksiyon
async def bilgiKontrolEtBireysel(kullaniciTable: hashtable.HashTable, username: linked.Node,
                                 userLL: linked.LinkedList):
    userUsername: linked.Node = username
    try:
        userElement = kullaniciTable.search(userUsername.veri)
        userLL.baslangicaDugumEkle(userElement)

    except KeyError:
        print(end="")


# 10 tane Takipçi veya Takip Edilen Bilgisi var mı kontrol eden fonksiyon
async def bilgiKontrolEtOnlu(kullaniciTable: hashtable.HashTable, username: linked.Node,
                             userLL: linked.LinkedList):
    userUsername: linked.Node = username

    user1 = userUsername
    user2 = user1.sonraki
    user3 = user2.sonraki
    user4 = user3.sonraki
    user5 = user4.sonraki
    user6 = user5.sonraki
    user7 = user6.sonraki
    user8 = user7.sonraki
    user9 = user8.sonraki
    user10 = user9.sonraki
    topluFonksiyonlar = await asyncio.gather(
        bilgiKontrolEtBireysel(kullaniciTable, user1, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user2, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user3, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user4, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user5, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user6, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user7, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user8, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user9, userLL),
        bilgiKontrolEtBireysel(kullaniciTable, user10, userLL)
    )


# 100 tane Takipçi veya Takip Edilen Bilgisi var mı kontrol eden fonksiyon
async def bilgiKontrolEtYuzlu(kullaniciTable: hashtable.HashTable, username: linked.Node,
                              userLL: linked.LinkedList):
    userUsername: linked.Node = username

    user1 = userUsername
    user2 = user1.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user3 = user2.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user4 = user3.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user5 = user4.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user6 = user5.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user7 = user6.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user8 = user7.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user9 = user8.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    user10 = user9.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    topluFonksiyonlar = await asyncio.gather(
        bilgiKontrolEtOnlu(kullaniciTable, user1, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user2, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user3, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user4, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user5, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user6, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user7, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user8, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user9, userLL),
        bilgiKontrolEtOnlu(kullaniciTable, user10, userLL)
    )


# Girilen LinkedListteki Takipçi veya Takip Edilenlerin Bilgisi var mı kontrol eden fonksiyon
async def bilgiKontrolEt(kullaniciTable: hashtable.HashTable, usernames: linked.LinkedList):
    username: linked.Node = usernames.baslangic
    userLL = linked.LinkedList()
    birHaneliSayi = usernames.boyutDondur() % 10
    onHaneliSayi = usernames.boyutDondur() % 100 - birHaneliSayi
    yuzHaneliSayi = usernames.boyutDondur() % 1000 - onHaneliSayi - birHaneliSayi

    for i in range(birHaneliSayi):
        await bilgiKontrolEtBireysel(kullaniciTable, username, userLL)
        username = username.sonraki

    for i in range(onHaneliSayi // 10):
        await bilgiKontrolEtOnlu(kullaniciTable, username, userLL)
        username = username.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki

    for i in range(yuzHaneliSayi // 100):
        await bilgiKontrolEtYuzlu(kullaniciTable, username, userLL)
        username = await yuzSonraki(username)

    return userLL


async def kullaniciBireyselEsle(eslenecekUser: user.User, takipEdilenUsers: linked.LinkedList, kullanici: user.User):
    eslemeBilgiLL = linked.LinkedList()
    eslenecekUserIlgiAlan: linked.LinkedList = eslenecekUser.get_ilgiAlanlariLL()
    simdikiTakipEdilenUser: linked.Node = takipEdilenUsers.baslangic
    bulundu = False
    while simdikiTakipEdilenUser:

        simdikiEslenecekIlgiAlan: linked.Node = eslenecekUserIlgiAlan.baslangic
        while simdikiEslenecekIlgiAlan:
            if bulundu:
                break
            else:
                simdikiTakipEdilenIlgiAlan: linked.Node = simdikiTakipEdilenUser.veri.get_ilgiAlanlariLL().baslangic

                while simdikiTakipEdilenIlgiAlan:
                    if bulundu:
                        break
                    else:
                        if simdikiEslenecekIlgiAlan.veri == simdikiTakipEdilenIlgiAlan.veri:
                            eslemeBilgiLL.baslangicaDugumEkle(simdikiTakipEdilenUser.veri)
                            bulundu = True

                    simdikiTakipEdilenIlgiAlan = simdikiTakipEdilenIlgiAlan.sonraki

                simdikiEslenecekIlgiAlan = simdikiEslenecekIlgiAlan.sonraki

        simdikiTakipEdilenUser = simdikiTakipEdilenUser.sonraki

    kullanici.get_eslenmisTakipciTakip().insert(eslenecekUser.get_username(), eslemeBilgiLL)


async def kullaniciOnluBireyselEsle(eslenecekUserlar: linked.Node,
                                    takipEdilenUsers: linked.LinkedList, kullanici: user.User):
    onHaneliKullanici1 = eslenecekUserlar
    onHaneliKullanici2 = onHaneliKullanici1.sonraki
    onHaneliKullanici3 = onHaneliKullanici2.sonraki
    onHaneliKullanici4 = onHaneliKullanici3.sonraki
    onHaneliKullanici5 = onHaneliKullanici4.sonraki
    onHaneliKullanici6 = onHaneliKullanici5.sonraki
    onHaneliKullanici7 = onHaneliKullanici6.sonraki
    onHaneliKullanici8 = onHaneliKullanici7.sonraki
    onHaneliKullanici9 = onHaneliKullanici8.sonraki
    onHaneliKullanici10 = onHaneliKullanici9.sonraki
    topluFonksiyonlar = await asyncio.gather(
        kullaniciBireyselEsle(onHaneliKullanici1.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici2.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici3.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici4.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici5.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici6.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici7.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici8.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici9.veri, takipEdilenUsers, kullanici),
        kullaniciBireyselEsle(onHaneliKullanici10.veri, takipEdilenUsers, kullanici)
    )


# 1 tane kullanıcı için ASYNC Takip edilen ve Takipçi eşleme fonksiyonu
async def kullaniciTakipcilerEsle(kullaniciTable: hashtable.HashTable, username: linked.Node):
    # User İçine Aktarılacak HashTable
    userUsername = username.veri
    kullanici: user.User = kullaniciTable.search(userUsername)
    kullaniciTakipciler = kullanici.get_followersLL()
    kullaniciTakipEdilenler = kullanici.get_followingLL()

    # 2 Değişken de LinkedList türünde içerisinde User Nesnesi tutar
    kullaniciTakipcilerUser, kullaniciTakipEdilenlerUser = await asyncio.gather(
        bilgiKontrolEt(kullaniciTable, kullaniciTakipciler),
        bilgiKontrolEt(kullaniciTable, kullaniciTakipEdilenler)
    )

    kullanici.set_eslenmisTakipciTakipUsersLL(kullaniciTakipcilerUser)
    tekHaneliTakipciSayisi = kullaniciTakipcilerUser.boyutDondur() % 10
    onHaneliTakipciSayisi = kullaniciTakipcilerUser.boyutDondur() % 100 - tekHaneliTakipciSayisi
    yuzHaneliTakipciSayisi = kullaniciTakipcilerUser.boyutDondur() % 1000 - tekHaneliTakipciSayisi - onHaneliTakipciSayisi

    # Takipçi Sayısının birler basamağı kadar eşleme yapan kısım
    simdikiEslenecekTakipci: linked.Node = kullaniciTakipcilerUser.baslangic
    for sayi in range(tekHaneliTakipciSayisi):
        await kullaniciBireyselEsle(simdikiEslenecekTakipci.veri, kullaniciTakipEdilenlerUser, kullanici)
        simdikiEslenecekTakipci = simdikiEslenecekTakipci.sonraki

    tempSonraki = simdikiEslenecekTakipci
    for onHaneliSayi in range(onHaneliTakipciSayisi // 10):
        topluFonksiyonlar = await asyncio.gather(
            kullaniciOnluBireyselEsle(tempSonraki, kullaniciTakipEdilenlerUser, kullanici),
        )
        tempSonraki = tempSonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki

    for yuzHaneliSayi in range(yuzHaneliTakipciSayisi // 100):
        yuzHaneli1 = tempSonraki
        yuzHaneli2 = yuzHaneli1.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli3 = yuzHaneli2.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli4 = yuzHaneli3.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli5 = yuzHaneli4.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli6 = yuzHaneli5.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli7 = yuzHaneli6.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli8 = yuzHaneli7.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli9 = yuzHaneli8.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        yuzHaneli10 = yuzHaneli9.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
        topluFonksiyonlar = await asyncio.gather(
            kullaniciOnluBireyselEsle(yuzHaneli1, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli2, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli3, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli4, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli5, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli6, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli7, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli8, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli9, kullaniciTakipEdilenlerUser, kullanici),
            kullaniciOnluBireyselEsle(yuzHaneli10, kullaniciTakipEdilenlerUser, kullanici),
        )

        tempSonraki = yuzHaneli9.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki


# Kullanıcı Eşlemesiin 10 kişiyle Yapan Kısım
# 10 tane kullanıcı için ASYNC Takip edilen ve Takipçi eşleme fonksiyonu
async def kullaniciEsleOnlu(kullaniciTable: hashtable.HashTable, username: linked.Node):
    simdikiUser = username
    user1 = simdikiUser
    user2 = user1.sonraki
    user3 = user2.sonraki
    user4 = user3.sonraki
    user5 = user4.sonraki
    user6 = user5.sonraki
    user7 = user6.sonraki
    user8 = user7.sonraki
    user9 = user8.sonraki
    user10 = user9.sonraki

    topluFonksiyon = await asyncio.gather(
        kullaniciTakipcilerEsle(kullaniciTable, user1),
        kullaniciTakipcilerEsle(kullaniciTable, user2),
        kullaniciTakipcilerEsle(kullaniciTable, user3),
        kullaniciTakipcilerEsle(kullaniciTable, user4),
        kullaniciTakipcilerEsle(kullaniciTable, user5),
        kullaniciTakipcilerEsle(kullaniciTable, user6),
        kullaniciTakipcilerEsle(kullaniciTable, user7),
        kullaniciTakipcilerEsle(kullaniciTable, user8),
        kullaniciTakipcilerEsle(kullaniciTable, user9),
        kullaniciTakipcilerEsle(kullaniciTable, user10)
    )

    return


# Kullanıcı Eşlemesiin 100 kişiyle Yapan Kısım
# 100 tane kullanıcı için ASYNC Takip edilen ve Takipçi eşleme fonksiyonu
async def kullaniciEsleYuzlu(kullaniciTable: hashtable.HashTable, usernames: linked.Node):
    simdikiUser = usernames
    userlar1 = simdikiUser
    userlar2 = userlar1.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar3 = userlar2.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar4 = userlar3.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar5 = userlar4.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar6 = userlar5.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar7 = userlar6.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar8 = userlar7.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar9 = userlar8.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    userlar10 = userlar9.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki.sonraki
    topluFonksiyon = await asyncio.gather(
        kullaniciEsleOnlu(kullaniciTable, userlar1),
        kullaniciEsleOnlu(kullaniciTable, userlar2),
        kullaniciEsleOnlu(kullaniciTable, userlar3),
        kullaniciEsleOnlu(kullaniciTable, userlar4),
        kullaniciEsleOnlu(kullaniciTable, userlar5),
        kullaniciEsleOnlu(kullaniciTable, userlar6),
        kullaniciEsleOnlu(kullaniciTable, userlar7),
        kullaniciEsleOnlu(kullaniciTable, userlar8),
        kullaniciEsleOnlu(kullaniciTable, userlar9),
        kullaniciEsleOnlu(kullaniciTable, userlar10)
    )

    return


# Her kullanıcı için ASYNC Takip edilen ve Takipçi eşleme fonksiyonu
async def kullaniciEsleBinli(kullaniciTable: hashtable.HashTable, usernames: linked.LinkedList):
    simdikiUser = usernames.baslangic
    x = 1
    while simdikiUser:
        zamanTutucu = time.time()
        print(f"{x}. 1000 kullanıcı eşleniyor...")
        yuzluUser1 = simdikiUser

        yuzluUser2 = await yuzSonraki(yuzluUser1)
        yuzluUser3 = await yuzSonraki(yuzluUser2)
        yuzluUser4 = await yuzSonraki(yuzluUser3)
        yuzluUser5 = await yuzSonraki(yuzluUser4)
        yuzluUser6 = await yuzSonraki(yuzluUser5)
        yuzluUser7 = await yuzSonraki(yuzluUser6)
        yuzluUser8 = await yuzSonraki(yuzluUser7)
        yuzluUser9 = await yuzSonraki(yuzluUser8)
        yuzluUser10 = await yuzSonraki(yuzluUser9)

        topluFonksiyon = await asyncio.gather(
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser1),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser2),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser3),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser4),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser5),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser6),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser7),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser8),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser9),
            kullaniciEsleYuzlu(kullaniciTable, yuzluUser10)
        )

        simdikiUser = await yuzSonraki(yuzluUser10)

        x += 1
        print(f"Eşleme bitti. Geçen süre {time.time() - zamanTutucu} s")

    return


# Belirli bölge ve dil için trend olan hashtagleri listeyen fonksiyon
def bolgeTrendHashtagDondur(bolge: str, table: hashtable.HashTable, usernames: linked.LinkedList):
    # bolge -> region / EN, TR
    # Girilen bölgedeki trend olan hashtagleri tutacak HashTable
    trendHashtagler: hashtable.HashTable = hashtable.HashTable(100000)
    # Hashtaglerin yazılarını tutacak LinkedList
    trendHashtaglerLL: linked.LinkedList = linked.LinkedList()

    simdikiUser: linked.Node = usernames.baslangic
    while simdikiUser:
        kullaniciVerisi: user.User = table.search(simdikiUser.veri)
        # Eğer kullanıcının bölgesi girilen bölgeye eşdeğer ise işlem yapan kısım
        if bolge == kullaniciVerisi.get_region():
            kullaniciTweets = kullaniciVerisi.get_tweetsLL()

            simdikiTweet: linked.Node = kullaniciTweets.baslangic
            # Tek Tek tüm tweetlerdeki hashtagleri bulup hashtable içine atayan kısım
            while simdikiTweet:
                tweetVerisi: str = simdikiTweet.veri
                hashTagIndex: int = tweetVerisi.find("#")
                hashTag: str = tweetVerisi[hashTagIndex:]

                # Hashtag daha önceden bulunmadıysa çalışacak kısım
                if hashTag not in trendHashtagler:
                    trendHashtagler.insert(hashTag, 1)
                    trendHashtaglerLL.baslangicaDugumEkle(hashTag)
                # Hashtag daha önceden bulunduysa çalışacak kısım
                else:
                    hashtagSayi = trendHashtagler.search(hashTag)
                    trendHashtagler.insert(hashTag, hashtagSayi + 1)

                simdikiTweet = simdikiTweet.sonraki

        simdikiUser = simdikiUser.sonraki

    # Hashtaglerin en çok kullanılana göre sıralanması
    trendHashtagsWithUsage: linked.LinkedList = linked.LinkedList()
    simdikiHashtag: linked.Node = trendHashtaglerLL.baslangic
    while simdikiHashtag:
        usageNumber = trendHashtagler.search(simdikiHashtag.veri)
        tempLL: linked.LinkedList = linked.LinkedList()
        tempLL.baslangicaDugumEkle(simdikiHashtag.veri)
        tempLL.baslangicaDugumEkle(usageNumber)

        trendHashtagsWithUsage.baslangicaDugumEkle(tempLL)


        simdikiHashtag = simdikiHashtag.sonraki

    # Başta en çok kullanılanları tutmasını sağlar
    linked.llIcindeLLSiralaBuyukKucuk(trendHashtagsWithUsage)

    # En çok kullaınlan 30 hashtagin bulunması ve kaydedilmesi
    top30TrendHashtags: hashtable.HashTable = hashtable.HashTable(3000)
    top30TrendHashtagsLL: linked.LinkedList = linked.LinkedList()
    sayac = 0

    currentHashtag: linked.Node = trendHashtagsWithUsage.baslangic
    while currentHashtag:
        if sayac == 30:
            break
        geciciLL: linked.LinkedList = currentHashtag.veri
        top30TrendHashtags.insert(geciciLL.baslangic.sonraki.veri, geciciLL.baslangic.veri)
        top30TrendHashtagsLL.sonaDugumEkle(geciciLL.baslangic.sonraki.veri)

        currentHashtag = currentHashtag.sonraki
        sayac += 1

    return top30TrendHashtags, top30TrendHashtagsLL


#  - Graf Modellemesi sağlayan metot
# Geçici olarak kullanım dışı
def grafKullaniciModelleme(username, dataHashTableNesne):
    # Yönlü graf oluşturma
    Graf = nx.DiGraph()
    takipciEdges = []
    val_map = {username: 0.5714285714285714}

    # Düğümleri ekleme
    bilgi = dataHashTableNesne.search(username)
    simdiki_follower = bilgi.get_followersLL().baslangic
    while simdiki_follower:
        Graf.add_edge(simdiki_follower.veri, bilgi.get_username(), length=1230)
        takipciEdges.append((simdiki_follower.veri, bilgi.get_username(),))
        val_map[simdiki_follower.veri] = 1.0
        simdiki_follower = simdiki_follower.sonraki


    values = [val_map.get(node, 0.25) for node in Graf.nodes()]

    edge_colours = ['black' if edge not in takipciEdges else 'red'
                    for edge in Graf.edges()]
    black_edges = [edge for edge in Graf.edges() if edge not in takipciEdges]

    # Grafı görselleştirme
    # Çıktı boyutunu belirleme
    plt.figure(figsize=(32, 32))

    pos = nx.spring_layout(Graf)  # Düğümleri uygun bir şekilde yerleştirme
    nx.draw_networkx_nodes(Graf, pos, cmap=plt.get_cmap('jet'),
                           node_color=values, node_size=500)
    nx.draw_networkx_labels(Graf, pos)
    nx.draw_networkx_edges(Graf, pos, edgelist=takipciEdges, edge_color='r', arrows=True)
    nx.draw_networkx_edges(Graf, pos, edgelist=black_edges, arrows=False)
    plt.title(f"{username} Adlı Kullanıcının Takipçi Grafı", fontsize=46)
    plt.savefig(f"graphFiles/Follower.png")
    plt.show()
    plt.clf()

    # Yönlü graf oluşturma
    Graf = nx.DiGraph()
    takipciEdges = []
    val_map = {username: 0.5714285714285714}

    # Düğümleri ekleme
    bilgi = dataHashTableNesne.search(username)
    simdiki_following = bilgi.get_followingLL().baslangic
    while simdiki_following:
        Graf.add_edge(bilgi.get_username(), simdiki_following.veri, length=530)
        takipciEdges.append((bilgi.get_username(), simdiki_following.veri,))
        simdiki_following = simdiki_following.sonraki

    values = [val_map.get(node, 0.25) for node in Graf.nodes()]

    edge_colours = ['black' if edge not in takipciEdges else 'red'
                    for edge in Graf.edges()]
    black_edges = [edge for edge in Graf.edges() if edge not in takipciEdges]

    # Grafı görselleştirme
    # Çıktı boyutunu belirleme
    plt.figure(figsize=(32, 32))

    pos = nx.spring_layout(Graf)  # Düğümleri uygun bir şekilde yerleştirme
    nx.draw_networkx_nodes(Graf, pos, cmap=plt.get_cmap('jet'),
                           node_color=values, node_size=500)
    nx.draw_networkx_labels(Graf, pos)
    nx.draw_networkx_edges(Graf, pos, edgelist=takipciEdges, edge_color='b', arrows=True)
    nx.draw_networkx_edges(Graf, pos, edgelist=black_edges, arrows=False)
    plt.title(f"{username} Adlı Kullanıcının Takip Edilen Grafı", fontsize=46)
    plt.savefig(f"graphFiles/Following.png")
    plt.show()
    plt.clf()

def benzerIlgiAlanIliski(userData: hashtable.HashTable, userInterestsData: hashtable.HashTable,
                         interestNames: linked.LinkedList):
    with open("analysisFiles/similarInterestUserRelationship.txt", "w", encoding="utf8") as similerInterestRelationshipFile:

        currentInterest: linked.Node = interestNames.baslangic
        while currentInterest:
            similerInterestRelationshipFile.write(f"-------{currentInterest.veri} "
                                                  f"İlgi Alanına Sahip Kişilerin İlişkileri-------")
            # İlgi Alanına dair içeriği döndüren kısım
            interestData: linked.LinkedList = userInterestsData.search(currentInterest.veri)
            currentInterestPerson: linked.Node = interestData.baslangic.sonraki
            # Her Kullanıcı için ilişki tablosu
            while currentInterestPerson:
                similerInterestRelationshipFile.write(f"\n{currentInterestPerson.veri}:\n\t" + "{\n")

                # Kıyaslanacağı kişiler
                comparePerson: linked.Node = interestData.baslangic.sonraki
                while comparePerson:
                    # Eğer kendisi ise adımı atlayan kısım
                    if comparePerson.veri == currentInterestPerson.veri:
                        comparePerson = comparePerson.sonraki
                        continue

                    # Takipçileri ve takip ettikleri kontrol edilecek kişinin verilerini çeken kısım
                    currentInterestPersonData: user.User = userData.search(currentInterestPerson.veri)

                    # Kişinin Takipçi ve takip edilen bilgilerini çeken kısım
                    currentIntPersonFollowerLL: linked.LinkedList = currentInterestPersonData.get_followersLL()
                    currentIntPersonFollowingLL: linked.LinkedList = currentInterestPersonData.get_followingLL()

                    # Durumun halini tutan kısım
                    takipEdilmeDurum = 0
                    takipEtmeDurum = 0

                    # Takipçi mi bakan kısım
                    curFollower: linked.Node = currentIntPersonFollowerLL.baslangic
                    while curFollower:
                        if curFollower.veri == comparePerson.veri:
                            takipEdilmeDurum = 1
                            break

                        curFollower = curFollower.sonraki

                    # Takip Edilen mi bakan kısım
                    curFollowing: linked.Node = currentIntPersonFollowingLL.baslangic
                    while curFollowing:
                        if curFollowing.veri == comparePerson.veri:
                            takipEtmeDurum = 1
                            break

                        curFollowing = curFollowing.sonraki

                    similerInterestRelationshipFile.write(f"\t {comparePerson.veri}:\t ( ")
                    if takipEdilmeDurum:
                        similerInterestRelationshipFile.write("Takipçi, ")
                    if takipEtmeDurum:
                        similerInterestRelationshipFile.write("Takip Edilen, ")
                    if takipEdilmeDurum == 0 and takipEtmeDurum == 0:
                        similerInterestRelationshipFile.write("İlişkisi Yok ")
                    similerInterestRelationshipFile.write(")\n")

                    comparePerson = comparePerson.sonraki

                similerInterestRelationshipFile.write("\t}\n")
                currentInterestPerson = currentInterestPerson.sonraki

            currentInterest = currentInterest.sonraki


# Main Fonksiyonunu çalıştıran kısım
if __name__ == '__main__':

    # BÖLÜM 1 - TWİTTER KULLANICILARINI KAYDETME BÖLÜMÜ
    with open("twitter_data.json", "r", encoding="utf8") as file:
        data = json.load(file)

    baslamaSure = time.time()  # İşleme başlamadan önce başlangıç zamanını alan kısım
    dataHashTable, dataUsernamesLL = hashTableAta(hashtable.HashTable(500000), data)
    print(f"\nKullanıcı Oluştururken geçen süre: {time.time() - baslamaSure} s")  # Data kaydetme işlemi süresi


    # BÖLÜM 2 - SEÇİLEN KULLANICININ TAKİPÇİ VE TAKİP EDİLEN ARASINDAKİ İLİŞKİSİNİN GRAF GÖSTERİMİ
    grafTime = time.time()
    kullaniciIsim = dataUsernamesLL.baslangic

    sayfaBoyut = dataUsernamesLL.boyutDondur()
    sayfaIndex = 1
    kullaniciIsimGirdisi = ""
    print(f"\n-----{sayfaIndex}-{sayfaIndex + 49} Arası Kullanıcılar-----")
    while kullaniciIsim:
        print(f"{kullaniciIsim.veri}")

        kullaniciIsim = kullaniciIsim.sonraki
        sayfaIndex += 1
        if sayfaIndex % 50 == 1:
            print(f"-----{sayfaIndex}-{sayfaIndex + 49} Arası Kullanıcılar-----")
            kullaniciIsimGirdisi = input("Graf Gösterimini İstediğiniz Kullanıcıyı Giriniz (Sonraki Sayfa -> Sonra): ")
            if kullaniciIsimGirdisi.lower() == "sonra":
                if sayfaIndex > sayfaBoyut:
                    break
                print(f"\n-----{sayfaIndex}-{sayfaIndex+49} Arası Kullanıcılar-----")
                continue
            else:
                break

    baslamaSure = baslamaSure + (time.time() - grafTime)
    grafKullaniciModelleme(kullaniciIsimGirdisi, dataHashTable)
    print(f"\nKullanıcıya Özel Graf Oluştururken geçen süre: {time.time() - baslamaSure} s")


    # BÖLÜM 3 - KULLANICILARIN İLGİ ALANI ANALİZİNİ YAPAN KISIM
    # İlgi Alanlarını HashTable içerisinde tutan ve isimlerini de LinkedList içinde tutan kısım
    # 1 - dataIlgiAlanlariHashTable = İlgi Alanlarını ve o ilgi alanına sahip olan kullanıcıları hashtable içinde tutar
    # veriler LinkedList içerisindedir
    # 2 - dataIlgiAlanlariLL = İlgi Alanlarının isimlerini tutar
    dataIlgiAlanlariHashTable, dataIlgiAlanlariLL = hashTableIlgiAlan(dataHashTable, dataUsernamesLL)
    print(f"İlgi Alanı Analizi Yapılması Sonucu geçen süre: {time.time() - baslamaSure} s")  # İlgi alanı kaydetme işlemi süresi


    # BÖLÜM 4 - YAPILAN İLGİ ALANI ANALİZLERİNİ METİN DOSYASINA KAYDEDEN KISIM
    # İlgi Alanlarının Analizlerini Metin Dosyasına kaydeden kısım
    with open("analysisFiles/detailedInterestAnalyse.txt", "w", encoding="utf8") as detailedInterestAnalyseFile:
        detailedInterestAnalyseFile.write("#Bu dosyada kullanıcıların ilgi alanlarına dair detaylı analiz vardır.")
        currentInterest: linked.Node = dataIlgiAlanlariLL.baslangic
        while currentInterest:
            detailedInterestAnalyseFile.write(f"İlgi Alanı: {currentInterest.veri} --- Sahip Olan Kişi Sayısı: ")
            InterestUsers: linked.LinkedList = dataIlgiAlanlariHashTable.search(currentInterest.veri)

            currentInterestUserNum: linked.Node = InterestUsers.baslangic
            detailedInterestAnalyseFile.write(f"{currentInterestUserNum.veri} --- İlgi Alanına Sahip Olan Kişiler: ")
            currentInterestUser: linked.Node = currentInterestUserNum.sonraki
            while currentInterestUser:
                detailedInterestAnalyseFile.write(f"{currentInterestUser.veri} / "
                                                  f"{dataHashTable.search(currentInterestUser.veri).get_region()}, ")

                currentInterestUser = currentInterestUser.sonraki

            detailedInterestAnalyseFile.write("\n")
            currentInterest = currentInterest.sonraki

    print(f"İlgi Alanı Analizi Kaydetme Sonucu geçen süre: {time.time() - baslamaSure} s\n")

    # BÖLÜM 5 - KULLANICILARI İLGİ ALANLARINA GÖRE EŞLEYEN KISIM
    asyncio.run(kullaniciEsleBinli(dataHashTable, dataUsernamesLL))
    print(f"\nKullanıcıları Eşleme Sonucu geçen süre: {time.time() - baslamaSure} s")  # Eşleme süresi


    # BÖLÜM 6 - İLGİ ANALİZİ SONUCU BENZER İLGİ ALANINA SAHİP OLAN KİŞİLERİN İLİŞKİSİNİN GÖSTERİMİ
    benzerIlgiAlanIliski(dataHashTable, dataIlgiAlanlariHashTable, dataIlgiAlanlariLL)
    print(f"Benzer İlgi Alanlarına Sahip Kişilerin İlişki Analizi Sonucu geçen süre: {time.time() - baslamaSure} s")


    # BÖLÜM 7 - BELİRLİ BİR BÖLGEDEKİ TREND HASHTAGLERİ LİSTELEYEN KISIM
    # Hangi bölgeye ait Trend Hashtaglerin analiz edileceğinin seçilmesi
    inputZamani = time.time()
    bolge: str = input("\nTrend olan HashTagleri görmek istediğiniz bölgeyi girin (TR, EN): ")
    inputYapilanaKadarGecenZaman = time.time() - inputZamani
    trendOlanHashtagTable, trendOlanHashtagLL = bolgeTrendHashtagDondur(bolge, dataHashTable, dataUsernamesLL)
    # Grafik Oluşturmak için zorunlu olarak list ve tuple şeklinde tutuluyor bilgiler
    hashtagler = ()
    kullanim = []

    # Trend Hashtaglerin metin dosyasına kaydedilmesi
    with open("analysisFiles/trendHashtagsForRegion.txt", "w", encoding="utf8") as trendHashtagsForRegionFile:
        print(f"{bolge} Bölgesine ait olan Trend HashTagler Aranıyor...")
        trendHashtagsForRegionFile.write(f"{bolge} Bölgesine ait Trend olan HashTagler\n")

        simdikiHashtag: linked.Node = trendOlanHashtagLL.baslangic
        while simdikiHashtag:
            # Seçilen Bölge için Trend olan Hashtaglerin dosyaya işlenmesi
            trendHashtagsForRegionFile.write(f"{simdikiHashtag.veri} = "
                                             f"{trendOlanHashtagTable.search(simdikiHashtag.veri)}\n")
            # Sütün Grafiği için gerekli elemanların eklenmesi
            hashtagler = hashtagler + (simdikiHashtag.veri,)
            kullanim.append(trendOlanHashtagTable.search(simdikiHashtag.veri))

            simdikiHashtag = simdikiHashtag.sonraki

    # Trend Hashtaglerin Grafiğinin Oluşturulması
    hashtagTrendGraf(hashtagler, kullanim, bolge)
    baslamaSure = baslamaSure + inputYapilanaKadarGecenZaman
    print(f"Trend HashTag Bulunması Sonucu geçen süre: {time.time() - baslamaSure} s\n")


    # BÖLÜM 8 - Girilen anahtar kelimeyi tüm kullanıcıların tweetlerinde arayan ve kelimeyi içerenleri
    # bulup metinde listeleyen kısım
    anahtarTime = time.time()
    anahtarKelime = input("Kullanıcı Tweetlerinde Aramasını Yapılmasını İstenilen Anahtar Kelime veya HashTag: ")
    anahtarGecenSure = time.time() - anahtarTime
    with open("analysisFiles/usersTweetKeyWordSearch.txt", "w", encoding="utf8") as usersTweetKeyWordSearchFile:
        usersTweetKeyWordSearchFile.write(anahtarKelime + " ANAHTAR KELİMESİNİ İÇEREN KULLANICI TWEETLERİ\n\n")
        simdikiUser: linked.Node = dataUsernamesLL.baslangic
        twitInd = 1
        while simdikiUser:
            simdikiUserVerisi: user.User = dataHashTable.search(simdikiUser.veri)
            tiwitler = linked.dfsTweet(anahtarKelime, simdikiUserVerisi.get_tweetsLL())
            if tiwitler:
                usersTweetKeyWordSearchFile.write(f"{twitInd}. {tiwitler} \n")
                twitInd += 1

            simdikiUser = simdikiUser.sonraki

    baslamaSure = baslamaSure + anahtarGecenSure
    print(f"Kullanıcı Tweetlerinde Araması Yapılan Anahtar Kelime veya HashTag Analizi Sonucu geçen süre: "
          f"{time.time() - baslamaSure} s\n")
