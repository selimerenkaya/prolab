# Bağlı Liste (Linked List) kullanmamızı sağlayacak Classların Oluşturulması
# Düğüm (Node) Classı
class Node:
    def __init__(self, veri):
        self.veri = veri
        self.sonraki = None


# Bağlı Liste (Linked List) Classı
class LinkedList:
    def __init__(self):
        self.baslangic = None

    # Bağlı Listenin başına düğüm eklemeyi sağlayan fonksiyon
    # 0 indexine eleman ekler
    def baslangicaDugumEkle(self, veri):
        yeni_dugum = Node(veri)
        if self.baslangic is None:
            self.baslangic = yeni_dugum
            return
        else:
            yeni_dugum.sonraki = self.baslangic
            self.baslangic = yeni_dugum

    # Bağlı Listenin sonuna düğüm eklemeyi sağlayan fonksiyon
    # -1 indexine eleman ekler
    def sonaDugumEkle(self, veri):
        yeni_dugum = Node(veri)
        if self.baslangic is None:
            self.baslangic = yeni_dugum
            return

        simdiki_dugum = self.baslangic
        while simdiki_dugum.sonraki:
            simdiki_dugum = simdiki_dugum.sonraki

        simdiki_dugum.sonraki = yeni_dugum

    # Bağlı Listenin sonuna birden fazla düğüm eklemeyi sağlayan fonksiyon
    # -1 indexine birden fazla eleman ekler
    def sonaBirdenFazlaDugumEkle(self, *veriler):
        for veri in veriler:
            self.sonaDugumEkle(veri)

    # İstenilen indexe düğüm eklemeyi sağlayan fonksiyon
    # İndexler 0'dan başlar.
    def indexDugumEkle(self, veri, index):
        yeni_dugum = Node(veri)
        simdiki_dugum = self.baslangic
        pozisyon = 0
        if pozisyon == index:
            self.baslangicaDugumEkle(veri)
        else:
            while simdiki_dugum is not None and pozisyon + 1 != index:
                pozisyon = pozisyon + 1
                simdiki_dugum = simdiki_dugum.sonraki

            if simdiki_dugum is not None:
                yeni_dugum.sonraki = simdiki_dugum.sonraki
                simdiki_dugum.sonraki = yeni_dugum
            else:
                print("İndex mevcut değil")

    # İstenilen indexteki Düğümün içeriğini değiştiren fonksiyon
    # " (içerik, index) " şeklinde kullanılır
    def dugumGuncelle(self, veri, index):
        simdiki_dugum = self.baslangic
        pozisyon = 0
        if pozisyon == index:
            simdiki_dugum.veri = veri
        else:
            while simdiki_dugum is not None and pozisyon != index:
                pozisyon = pozisyon + 1
                simdiki_dugum = simdiki_dugum.sonraki

            if simdiki_dugum is not None:
                simdiki_dugum.veri = veri
            else:
                print("İndex mevcut değil")

    # Bağlı listenin ilk düğümünü silmeyi sağlayan fonksiyon
    # 0 indexli düğüm silinir
    def ilkDugumSil(self):
        if self.baslangic is None:
            return

        self.baslangic = self.baslangic.sonraki

    # Bağlı listenin son düğümünü silmeyi sağlayan fonksiyon
    # -1 indexli düğüm silinir
    def sonDugumSil(self):

        if self.baslangic is None:
            return

        simdiki_dugum = self.baslangic
        while simdiki_dugum.sonraki.sonraki:
            simdiki_dugum = simdiki_dugum.sonraki

        simdiki_dugum.sonraki = None

    # Bağlı listeden girilen indexteki düğümünü silmeyi sağlayan fonksiyon
    # index olarak ne girilirse o silinir
    def indexDugumSil(self, index):
        if self.baslangic is None:
            return

        simdiki_dugum = self.baslangic
        pozisyon = 0
        if pozisyon == index:
            self.ilkDugumSil()
        else:
            while simdiki_dugum is not None and pozisyon + 1 != index:
                pozisyon = pozisyon + 1
                simdiki_dugum = simdiki_dugum.sonraki

            if simdiki_dugum is not None:
                simdiki_dugum.sonraki = simdiki_dugum.sonraki.sonraki
            else:
                print("Geçersiz index değeri")

    # içeriği girilen düğümü Bağlı listede bulup silen fonksiyon
    # girilen " veri " değeri Bağlı listede varsa o düğümü siler
    def dugumSil(self, veri):
        simdiki_dugum = self.baslangic

        if simdiki_dugum.veri == veri:
            self.ilkDugumSil()
            return

        while simdiki_dugum is not None and simdiki_dugum.sonraki.veri != veri:
            simdiki_dugum = simdiki_dugum.sonraki

        if simdiki_dugum is None:
            return
        else:
            simdiki_dugum.sonraki = simdiki_dugum.sonraki.sonraki

    # Bağlı listenin boyutunu döndüren fonksiyon
    def boyutDondur(self):
        boyut = 0
        if self.baslangic:
            simdiki_dugum = self.baslangic
            while simdiki_dugum:
                boyut = boyut + 1
                simdiki_dugum = simdiki_dugum.sonraki
            return boyut
        else:
            return 0

    # Bağlı listeyi yazdıran fonksiyon
    def listeyiYazdir(self):
        simdiki_dugum = self.baslangic
        while simdiki_dugum:
            print(simdiki_dugum.veri)
            simdiki_dugum = simdiki_dugum.sonraki

    # Bağlı listeyi başka bir listeyle birleştirmeye yarayan fonksiyon
    def listeBirlestir(self, eklenecekListe):
        simdikiVeri = eklenecekListe.baslangic
        while simdikiVeri:
            self.baslangicaDugumEkle(simdikiVeri.veri)
            simdikiVeri = simdikiVeri.sonraki

    # DFS Algoritması ile Veri Arama
    def dfs(self, target):
        simdikiNode = self.baslangic
        visited = set()

        while simdikiNode:
            if simdikiNode.veri == target:
                return True
            elif simdikiNode.veri not in visited:
                visited.add(simdikiNode.veri)
                sonrakiNode = simdikiNode.sonraki
                while sonrakiNode:
                    if sonrakiNode.veri == target:
                        return True
                    elif sonrakiNode.veri not in visited:
                        visited.add(sonrakiNode.veri)
                        sonrakiNode = sonrakiNode.sonraki
            simdikiNode = simdikiNode.sonraki
        return False


# DFS Algoritması ile Tweet Arama
def dfsTweet(target: str, tweetLL: LinkedList):
    simdikiNode = tweetLL.baslangic
    visited = set()

    while simdikiNode:
        if simdikiNode.veri.find(target) != -1:
            return simdikiNode.veri
        elif simdikiNode.veri not in visited:
            visited.add(simdikiNode.veri)
            sonrakiNode = simdikiNode.sonraki
            while sonrakiNode:
                if sonrakiNode.veri.find(target) != -1:
                    return sonrakiNode.veri
                elif sonrakiNode.veri not in visited:
                    visited.add(sonrakiNode.veri)
                    sonrakiNode = sonrakiNode.sonraki
        simdikiNode = simdikiNode.sonraki
    return


def llIcindeLLSiralaBuyukKucuk(bagliListe: LinkedList):
    if bagliListe.baslangic is None:
        return

    swapped = True
    while swapped:
        swapped = False
        simdiki_dugum: Node = bagliListe.baslangic

        while simdiki_dugum.sonraki:
            if simdiki_dugum.veri.baslangic.veri < simdiki_dugum.sonraki.veri.baslangic.veri:
                simdiki_dugum.veri.baslangic.veri, simdiki_dugum.sonraki.veri.baslangic.veri \
                    = simdiki_dugum.sonraki.veri.baslangic.veri, simdiki_dugum.veri.baslangic.veri

                swapped = True
            simdiki_dugum = simdiki_dugum.sonraki
