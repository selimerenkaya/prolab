import time

import LinkedList as linked
import ilgiAlani as ilgiAlaniClass
import HashTable as hashtable


# Twitter Kullanıcılarının bilgilerini tutmamıza yardımcı olan Class
class User:
    # User nesnesi oluşturulurken kullanılan fonksiyon
    def __init__(self, username: str, name: str, followers_count: int, following_count: int, language: str,
                 region: str, tweets, following, followers):

        self.username = username
        self.name = name
        self.followers_count = followers_count
        self.following_count = following_count
        self.language = language
        self.region = region
        # Takipçi ile Takip Edilenlerin eşlenmiş halini tutar
        # Başlangıçta içi boştur
        self.eslenmisTakipciTakip = hashtable.HashTable(20000)  # Linkedlist Nesnesi tutar - içerisinde User
        # eşlenmiş takipçilerin isimleri buradan elde edilir
        self.eslenmisTakipciTakipUsersLL = linked.LinkedList()  # İçerisinde User nesnesi tutar

        # LinkedList şeklinde tutulanlar
        # Tweetler
        self.tweetsLL = linked.LinkedList()
        for tweet in tweets:
            self.tweetsLL.baslangicaDugumEkle(tweet)

        # Takip Edilenler
        self.followingLL = linked.LinkedList()
        for follow in following:
            self.followingLL.baslangicaDugumEkle(follow)

        # Takip Edenler
        self.followersLL = linked.LinkedList()
        for follower in followers:
            self.followersLL.baslangicaDugumEkle(follower)

        # İlgi Alanlari
        # Kullanıcının ilgi alanlarını bulup LinkedList şeklinde saklayan kısım
        self.ilgiAlanlariLL = ilgiAlaniClass.ilgi_Alani_Bulma(self.get_tweetsLL())

    # Get/Set Metotları
    # Get metotları
    def get_username(self):
        return self.username

    def get_name(self):
        return self.name

    def get_followers_count(self):
        return self.followers_count

    def get_following_count(self):
        return self.following_count

    def get_language(self):
        return self.language

    def get_region(self):
        return self.region

    def get_tweetsLL(self):
        return self.tweetsLL

    def get_followingLL(self):
        return self.followingLL

    def get_followersLL(self):
        return self.followersLL

    def get_ilgiAlanlariLL(self):
        return self.ilgiAlanlariLL

    def get_eslenmisTakipciTakip(self):
        return self.eslenmisTakipciTakip

    def get_eslenmisTakipciTakipUsersLL(self):
        return self.eslenmisTakipciTakipUsersLL

    # Set metotları
    def set_username(self, username):
        self.username = username

    def set_name(self, name):
        self.name = name

    def set_followers_count(self, followers_count):
        self.followers_count = followers_count

    def set_following_count(self, following_count):
        self.following_count = following_count

    def set_language(self, language):
        self.language = language

    def set_region(self, region):
        self.region = region

    def set_eslenmisTakipciTakip(self, eslenmisTakipTakipciListe):
        self.eslenmisTakipciTakip = eslenmisTakipTakipciListe

    def set_eslenmisTakipciTakipUsersLL(self, eslenmisTakipciTakipUsersLL):
        self.eslenmisTakipciTakipUsersLL = eslenmisTakipciTakipUsersLL






