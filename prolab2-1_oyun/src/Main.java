//  BU PROJE 2024 YILI PROGRAMLAMA LABORATUVARI 2 DERSİNİN 1. PROJESİDİR
//  PROJEYİ SELİM EREN KAYA VE GÜLSÜM NUR MASLAK BERABER YAPMIŞTIR
//  YAPIMINDA UYGULAMA OLARAK İNTELLİJ İDEA KULLANILMIŞ VE JDK 21 SÜRÜMÜYLE YAPILMIŞTIR
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Girdi almayı sağlayan scanner nesnesi
        Scanner girdiAlici = new Scanner(System.in);

        // Kare Ölçeği Alan kısım
        // Yatay Kare Sayısı
        System.out.print("Yataydaki kare sayısını giriniz: ");
        int yatayKareSayisi = girdiAlici.nextInt();

        // Dikey Kare Sayısı
        System.out.print("Dikeydeki kare sayısını giriniz: ");
        int dikeyKareSayisi = girdiAlici.nextInt();

        // Haritanın Alanı
        int haritaAlan = yatayKareSayisi * dikeyKareSayisi;

        // Kış bölgesinin sınır kordinatını tutan değişken
        // Böylece hangi lokasyonun kış bölgesinde hangisinin yaz bölgesinde olduğu
        // Kolayca belirlenebilecek
        int kisBolgeSinir = yatayKareSayisi / 2 - 1;


        // Random Olayların gerçekleşmesini sağlayan random seed
        randomSeed seedGenerator = new randomSeed();

        // İstenilen Minimum Sabit(sayı) Engel Sayıları
        int minAgacSayi = 5 + ( haritaAlan / 500 );
        int minKayaSayi = 5 + ( haritaAlan / 500 );
        int minDagSayi = 2 + ( haritaAlan / 4000 );
        int minDuvarSayi = 4 + ( haritaAlan / 900 );
        int minKusSayi = 2 + ( haritaAlan / 1800 );
        int minAriSayi = 2 + ( haritaAlan / 1800 );

        // İstenilen Minimum Sandık Sayısı
        int minAltinSandikSayi = 5;
        int minGumusSandikSayi = 5;
        int minZumrutSandikSayi = 5;
        int minBakirSandikSayi = 5;

        // Lokasyonları Tutan hashtable kordinatları ile çağrılabilirler get("0,5") şeklinde
        Hashtable<String, Location> lokasyonlarHashtable = new Hashtable<>();

        // Karelerin lokasyonlarını oluşturup hashtable içerisinde saklayan kısım
        for(int yatay = 0; yatay < yatayKareSayisi; yatay++){
            for(int dikey = 0; dikey < dikeyKareSayisi; dikey++) {
                if (yatay <= kisBolgeSinir) {

                    // Lokasyonları Hashtable içerisinde saklama fonksiyonu
                    Location lokasyon = new Location(yatay, dikey, "winter");
                    lokasyonlarHashtable.put(lokasyon.getXCoord() + "," + lokasyon.getYCoord(), lokasyon);
                }
                else {

                    // Lokasyonları Hashtable içerisinde saklama fonksiyonu
                    Location lokasyon = new Location(yatay, dikey, "summer");
                    lokasyonlarHashtable.put(lokasyon.getXCoord() + "," + lokasyon.getYCoord(), lokasyon);
                }
            }
        }

        // Haritada Rastgele engel oluşturma kısmı
        // Sabit Engelleri Tutan ArrayList
        ArrayList<Obstacle> engelListesi = new ArrayList<>();

        // Dinamik Engelleri tutan ArrayList
        ArrayList<DynamicObstacle> dinamikEngelListesi = new ArrayList<>();

        // Haritada Rastgele sandık oluşturma kısmı
        ArrayList<Obstacle> sandikListesi = new ArrayList<>();

        // 1 - Minimum sayıdaki engellerin oluşturulması

        // 1 - 1 Ağaç Engellerinin Oluşturulması
        int sabitAgacSayi = 0;
        while (sabitAgacSayi < minAgacSayi) {
            // Ağaca uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 5; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 5; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Ağacın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    // Lokasyonun Bölgesini bulan kısım
                    if (seedX <= kisBolgeSinir - 5)
                    {

                        Obstacle kisAgac = new TreeWinter(engelLokasyon);
                        engelListesi.add(kisAgac);

                        for(int boyutX = 0; boyutX < kisAgac.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < kisAgac.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(kisAgac);
                            }
                        }

                    } else {

                        Obstacle yazAgac = new TreeSummer(engelLokasyon);
                        engelListesi.add(yazAgac);

                        for(int boyutX = 0; boyutX < yazAgac.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < yazAgac.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(yazAgac);
                            }
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitAgacSayi++;
        }


        // 1 - 2 Kaya Engellerinin Oluşturulması
        int sabitKayaSayi = 0;
        while (sabitKayaSayi < minKayaSayi) {
            // Kayaya uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 3; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 3; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Kayanın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    // Lokasyonun Bölgesini bulan kısım
                    if (seedX <= kisBolgeSinir - 3)
                    {

                        Obstacle kisKaya = new RockWinter(engelLokasyon);
                        engelListesi.add(kisKaya);

                        for(int boyutX = 0; boyutX < kisKaya.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < kisKaya.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(kisKaya);
                            }
                        }

                    } else {

                        Obstacle yazKaya = new RockSummer(engelLokasyon);
                        engelListesi.add(yazKaya);

                        for(int boyutX = 0; boyutX < yazKaya.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < yazKaya.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(yazKaya);
                            }
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitKayaSayi++;
        }


        // 1 - 3 Dağ Engellerinin Oluşturulması
        int sabitDagSayi = 0;
        while (sabitDagSayi < minDagSayi) {
            // Dağa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 15; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 15; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Dağın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    // Lokasyonun Bölgesini bulan kısım
                    if (seedX <= kisBolgeSinir - 7)
                    {
                        Obstacle kisDag = new MountainWinter(engelLokasyon);
                        engelListesi.add(kisDag);

                        // Dağun bulunduğu bölgeyi engel var olarak ayarlayan kısım
                        for(int boyutX = 0; boyutX < kisDag.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < kisDag.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(kisDag);
                            }
                        }


                    } else {

                        Obstacle yazDag = new MountainSummer(engelLokasyon);
                        engelListesi.add(yazDag);

                        // Dağun bulunduğu bölgeyi engel var olarak ayarlayan kısım
                        for(int boyutX = 0; boyutX < yazDag.getBoyutX(); boyutX++)
                        {
                            for(int boyutY = 0; boyutY < yazDag.getBoyutY(); boyutY++)
                            {
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                                lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(yazDag);
                            }
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitDagSayi++;
        }

        // 1 - 4 Duvar Engellerinin Oluşturulması
        int sabitDuvarSayi = 0;
        while (sabitDuvarSayi < minDuvarSayi) {
            // Duvara uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = -1; boyutX < 11; boyutX++)
                {
                    for(int boyutY = -1; boyutY < 2; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                for(int boyutY = -1; boyutY < 11; boyutY++)
                {
                    for(int boyutX = -1; boyutX < 2; boyutX++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }

                }

                // Duvarın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    Obstacle duvar = new Wall(engelLokasyon);
                    engelListesi.add(duvar);

                    if (duvar.boyutX == 10)
                    {
                        for(int boyutX = 0; boyutX < 10; boyutX++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY)).setKonumdakiNesne(duvar);
                        }
                    }
                    else {
                        for(int boyutY = 0; boyutY < 10; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX) + "," + (seedY + boyutY)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX) + "," + (seedY + boyutY)).setKonumdakiNesne(duvar);
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitDuvarSayi++;
        }

        // 2 - 1 Kuş Engellerinin Oluşturulması
        int sabitKusSayi = 0;
        while (sabitKusSayi < minKusSayi) {
            // Kuşa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 2; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 9; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Kuşun oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    DynamicObstacle kus = new Bird(engelLokasyon);
                    dinamikEngelListesi.add(kus);

                    for(int boyutX = 0; boyutX < 2; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 9; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(kus);

                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitKusSayi++;
        }

        // 2 - 2 Arı Engellerinin Oluşturulması
        int sabitAriSayi = 0;
        while (sabitAriSayi < minAriSayi) {
            // Arıya uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;
                // Engelin atanacağı konum
                Location engelLokasyon = lokasyonlarHashtable.get(seedX + "," + seedY);

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 7; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 2; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Arının oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    DynamicObstacle ari = new Bee(engelLokasyon);
                    dinamikEngelListesi.add(ari);

                    for(int boyutX = 0; boyutX < 7; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 2; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY)).setKonumdakiNesne(ari);

                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitAriSayi++;
        }

        // Rastgele Sandık Yerleştirme
        // 3 - 1 Altın Sandıkların Oluşturulması
        int sabitAltinSandik = 0;
        while (sabitAltinSandik < minAltinSandikSayi) {
            // Altın Sandığa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 4; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 4; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Altın Sandıkların oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    Obstacle altinSandik = new ChestGold(lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)));
                    sandikListesi.add(altinSandik);

                    for(int boyutX = 0; boyutX < 2; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 2; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setKonumdakiNesne(altinSandik);
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitAltinSandik++;
        }

        // 3 - 2 Gümüş Sandıkların Oluşturulması
        int sabitGumusSandik = 0;
        while (sabitGumusSandik < minGumusSandikSayi) {
            // Gümüş Sandığa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 4; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 4; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Gümüş Sandığın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    Obstacle gumusSandik = new ChestSilver(lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)));
                    sandikListesi.add(gumusSandik);

                    for(int boyutX = 0; boyutX < 2; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 2; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setKonumdakiNesne(gumusSandik);
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitGumusSandik++;
        }

        // 3 - 3 Zümrüt Sandıkların Oluşturulması
        int sabitZumrutSandik = 0;
        while (sabitZumrutSandik < minZumrutSandikSayi) {
            // Zümrüt Sandığa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 4; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 4; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Zümrüt Sandığın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    Obstacle zumrutSandik = new ChestEmerald(lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)));
                    sandikListesi.add(zumrutSandik);

                    for(int boyutX = 0; boyutX < 2; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 2; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setKonumdakiNesne(zumrutSandik);
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitZumrutSandik++;
        }

        // 3 - 4 Bakır Sandıkların Oluşturulması
        int sabitBakirSandik = 0;
        while (sabitBakirSandik < minBakirSandikSayi) {
            // Bakır Sandığa uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 4; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 4; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Bakır Sandığın oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    Obstacle bakirSandik = new ChestCopper(lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)));
                    sandikListesi.add(bakirSandik);

                    for(int boyutX = 0; boyutX < 2; boyutX++)
                    {
                        for(int boyutY = 0; boyutY < 2; boyutY++)
                        {
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setEngelDurum(true);
                            lokasyonlarHashtable.get((seedX + boyutX + 1) + "," + (seedY + boyutY + 1)).setKonumdakiNesne(bakirSandik);
                        }
                    }

                    basariliOlusturma = true;
                }
            }

            sabitBakirSandik++;
        }

        // Karakter Nesnesinin Oluşturulması
        Character karakter = new Character();
        boolean karakterOlusumu = false;
        while (!karakterOlusumu) {
            // Karaktere uygun oluşturma yeri bulana kadar deneyen kısım
            boolean basariliOlusturma = false;
            while(!basariliOlusturma) {
                int seedX = seedGenerator.randomSeedGenerator() % yatayKareSayisi;
                int seedY = seedGenerator.randomSeedGenerator() % dikeyKareSayisi;

                // Kontrol edilecek konumlar
                boolean lokasyonUygun = true;
                for(int boyutX = 0; boyutX < 3; boyutX++)
                {
                    for(int boyutY = 0; boyutY < 3; boyutY++)
                    {
                        Location kontrolLokasyonu = lokasyonlarHashtable.get((seedX + boyutX) + "," + (seedY + boyutY));
                        if (kontrolLokasyonu == null)
                        {
                            lokasyonUygun = false;
                        }
                        else if(kontrolLokasyonu.getEngelDurum())
                        {
                            lokasyonUygun = false;
                        }
                    }
                }

                // Karakterin oluşabileceği bölgede engel yoksa oluşturulması
                if (lokasyonUygun)
                {

                    karakter = new Character("selim", "gülsüm", lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)));
                    lokasyonlarHashtable.get((seedX + 1) + "," + (seedY + 1)).setEngelDurum(true);
                    basariliOlusturma = true;
                }
            }

            karakterOlusumu = true;
        }

        // Arayüz
        Gui arayuz = new Gui(lokasyonlarHashtable, yatayKareSayisi, dikeyKareSayisi, karakter, engelListesi, dinamikEngelListesi, sandikListesi);

    }
}