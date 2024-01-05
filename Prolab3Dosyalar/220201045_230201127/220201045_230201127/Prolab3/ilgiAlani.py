# Noktalama işaretlerini çıkarma
import time

import LinkedList as linked
import string
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

# from nltk.stem import PorterStemmer, WordNetLemmatizer

# LinkedList Halinde tutulan gereksiz kelimeler listesi
etkisiz_kelimeler = linked.LinkedList()
etkisiz_kelimeler.sonaBirdenFazlaDugumEkle(stopwords.words('turkish'))

gereksizKelimeler = linked.LinkedList()
gereksizKelimeler.sonaBirdenFazlaDugumEkle("bu", "sonraki", "ayrıca", "ve", "’", "bir",
                                           "olarak", "için", "aynı", "sonra", "da", "veya", "ile",
                                           "de", "yani", "bazı", "iki", "üç", "ek", "nın",
                                           "yer", "iyi", "birçok", "fakat", "çoğu", "örnek",
                                           "üzerine", "bundan", "ancak", "yüze", "daha", "çok", "her", "konu",
                                           "oysa", "ilk", "bugün", "yapmıştır", "kendisinin", "hem", "grup", "diğer",
                                           "yeni", "ikisi", "önemli")


# Kullanıcıların ilgi alanlarını bulmaya yaran fonksiyon
def ilgi_Alani_Bulma(tweets: linked.LinkedList):
    tutu = time.time()

    butun_tweetler = ""
    simdiki_tweet = tweets.baslangic
    while simdiki_tweet:
        tweet = simdiki_tweet.veri

        # Tweet içerisinde bulunan noktalama işaretlerini yok eden kısım
        tweet = tweet.translate(str.maketrans("", "", string.punctuation))

        butun_tweetler = butun_tweetler + tweet + " "
        simdiki_tweet = simdiki_tweet.sonraki

    genelKelimeler = word_tokenize(butun_tweetler)

    # print(f"\n\nİLGİALANI ANALİZ\n1. AŞAMA {time.time() - tutu}s")

    # Kelimeleri Düzenleyip gereksiz kelimeleri silen kısım
    for kelime in genelKelimeler:
        etkisiz_kelimeler_Eleman = etkisiz_kelimeler.baslangic
        while etkisiz_kelimeler_Eleman:
            if not etkisiz_kelimeler_Eleman.veri == kelime.lower().strip():
                genelKelimeler.remove(kelime)
            etkisiz_kelimeler_Eleman = etkisiz_kelimeler_Eleman.sonraki

    tutu = time.time()

    for kelime in genelKelimeler:
        gereksizKelimeEleman = gereksizKelimeler.baslangic
        while gereksizKelimeEleman:
            if gereksizKelimeEleman.veri == kelime.lower().strip():
                genelKelimeler.remove(kelime)
            gereksizKelimeEleman = gereksizKelimeEleman.sonraki

        if kelime.lower()[-3:] == "mak" or kelime.lower()[-3:] == "mek":
            genelKelimeler.remove(kelime)

    # print(f"3. AŞAMA {time.time() - tutu}s")
    tutu = time.time()

    # İlgi Alanlarını Belirleyen kısım

    taggent_sent = nltk.pos_tag(genelKelimeler)
    ozelKelimeler = [word for word, tag in taggent_sent if (tag == 'NNP')]  # Özel İsim ise ekliyo
    ozelKelimeAlan = nltk.FreqDist(ozelKelimeler)

    # print(f"4. AŞAMA {time.time() - tutu}s")

    # İlgi Alanlarını LinkedList şeklinde kaydedip geri döndüren kısım
    ilgiAlanlari = linked.LinkedList()
    ilgiAlanlari.baslangicaDugumEkle(ozelKelimeAlan.most_common(3)[2][0])
    ilgiAlanlari.baslangicaDugumEkle(ozelKelimeAlan.most_common(3)[1][0])
    ilgiAlanlari.baslangicaDugumEkle(ozelKelimeAlan.most_common(3)[0][0])

    return ilgiAlanlari
