import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Character {
    private String charImagePath = "textures/character/character.png";
    private String id;
    private String ad;
    private Location konum;
    private ArrayList<Location> ilerlenenKonumlar = new ArrayList<>();

    // Gui Değişkenleri
    private GuiPanel gp;
    private KeyHandler keyH;
    double worldX;
    double worldY;
    double screenX;
    double screenY;

    int girisEkranX = 0;
    int girisEkranY = 0;

    // Animasyon Değişkenleri
    int animationCounter = 0;
    int animationNum = 1;

    // Sandık Ararken Kullanılacak Metotlar ve Değişkenler
    int atilanAdimSayisi = 0;
    ArrayList<Obstacle> kesfedilenNesneler = new ArrayList<>();  // Yolda Gezerken keşfedilen Nesneleri tutacak
    ArrayList<Chest> gorulenSandiklar = new ArrayList<>();  // Gezerken gördüğü sandıkları tutacak
    ArrayList<Chest> toplanmisSandiklar = new ArrayList<>();  // Toplanmış sandıkları tutacak
    ArrayList<Chest> siraliToplanmisSandiklar = new ArrayList<>(); // Toplanmış sandıkların değer sırasına göre sıralanmış hali


    ArrayList<Location> gidilecekYol = new ArrayList<>();  // Karakterin bir yere giderken kullanacağı lokasyonları tutan arraylist
    ArrayList<Location> gezilmisKonumlar = new ArrayList<>();  // Rastgele gezmeye sağlarken gidilmeyecek kareleri tutar
    ArrayList<Location> gezilebilecekKonumlar = new ArrayList<>();  // Karakterin gidip keşfedebileceği kareleri tutar


    // Get-Set Metotları
    // Get Metotları
    String getCharImagePath() {
        return charImagePath;
    }

    String getAd() {
        return ad;
    }

    String getId() {
        return id;
    }

    Location getKonum() {
        return konum;
    }

    ArrayList<Location> getIlerlenenKonumlar() {
        return ilerlenenKonumlar;
    }

    GuiPanel getGp() {
        return gp;
    }

    KeyHandler getKeyH() {
        return keyH;
    }

    int getWorldX() {
        return (int)worldX;
    }

    int getWorldY() {
        return (int)worldY;
    }

    int getScreenX() {
        return (int)screenX;
    }

    int getScreenY() {
        return (int)screenY;
    }

    // Set Metotları
    void setCharImagePath(String charImagePath) {
        this.charImagePath = charImagePath;
    }

    void setAd(String ad) {
        this.ad = ad;
    }

    void setId(String id) {
        this.id = id;
    }

    // Hazır konum bilgisiyle setKonum
    void setKonum(Location konum) {
        this.konum = konum;
    }

    void setIlerlenenKonumlar(ArrayList<Location> ilerlenenKonumlar) {
        this.ilerlenenKonumlar = ilerlenenKonumlar;
    }

    void setGp(GuiPanel gp){
        this.gp = gp;
    }

    void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }


    // FONKSİYONLAR
    // Görülen Nesnelerin kaydedilmesini sağlayan metot
    public void gorulenNesneleriKaydet() {
        for(int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Location nesneKonum = gp.lokasyonlarHashTable.get( (konum.getXCoord() + x - 3) + "," + (konum.getYCoord() + y - 3));
                if ( nesneKonum != null && nesneKonum.getEngelDurum() && nesneKonum.getKonumdakiNesne() != null)  {
                    // Nesne Daha önce keşfedilmediyse ekler
                    if (!kesfedilenNesneler.contains(nesneKonum.getKonumdakiNesne())) {
                        kesfedilenNesneler.add(nesneKonum.getKonumdakiNesne());
                    }

                    // Nesne sınıfı sandık ise çalışacak kısım
                    // Altın sandık
                    if (nesneKonum.getKonumdakiNesne().getClass() == ChestGold.class) {
                        // Nesne daha önce görülmemiş ve toplanmadıysa çalışacak kısım
                        if (!gorulenSandiklar.contains((ChestGold) nesneKonum.getKonumdakiNesne()) &&
                                !((ChestGold) nesneKonum.getKonumdakiNesne()).getBulunmaDurum() ) {

                            // Sandığın karaktere en yakın olan konumunu bulmak için kullanılır
                            int uzaklik = 9999;
                            int enYakinSandikX = 0;
                            int enYakinSandikY = 0;

                            // Karakterin Sandığın bulunduğu yerden geçmesini sağlayan kısım
                            Location sandikKonum = nesneKonum.getKonumdakiNesne().getKonum();
                            for(int sandikX = 0; sandikX < 2; sandikX++){
                                for(int sandikY = 0; sandikY < 2; sandikY++) {
                                    gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)).setEngelDurum(false);


                                    // Sandığın Her bir konumu kontrol edilir
                                    // Karaktere en yakın olan konum asıl konumu olarak atanır
                                    enKisaYol(gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)));
                                    int mesafe = gidilecekYol.size();
                                    if (mesafe <= uzaklik) {
                                        uzaklik = mesafe;
                                        enYakinSandikX = sandikX;
                                        enYakinSandikY = sandikY;
                                    }

                                }
                            }

                            Location sandikEnYakinKonum = gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + enYakinSandikX)
                                    + "," + (sandikKonum.getYCoord() + enYakinSandikY));

                            gorulenSandiklar.add( (ChestGold) sandikEnYakinKonum.getKonumdakiNesne());


                            System.out.println("ALTIN SANDIK BULUNDU! Konumu: [" + nesneKonum.getXCoord() + "][" + nesneKonum.getYCoord() + "]");

                        }
                    }
                    // Gümüş sandık
                    else if (nesneKonum.getKonumdakiNesne().getClass() == ChestSilver.class) {
                        // Nesne daha önce görülmemiş ve toplanmadıysa çalışacak kısım
                        if (!gorulenSandiklar.contains((ChestSilver) nesneKonum.getKonumdakiNesne()) &&
                                !((ChestSilver) nesneKonum.getKonumdakiNesne()).getBulunmaDurum() ) {

                            // Sandığın karaktere en yakın olan konumunu bulmak için kullanılır
                            int uzaklik = 9999;
                            int enYakinSandikX = 0;
                            int enYakinSandikY = 0;

                            // Karakterin Sandığın bulunduğu yerden geçmesini sağlayan kısım
                            Location sandikKonum = nesneKonum.getKonumdakiNesne().getKonum();
                            for(int sandikX = 0; sandikX < 2; sandikX++){
                                for(int sandikY = 0; sandikY < 2; sandikY++) {
                                    gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)).setEngelDurum(false);


                                    // Sandığın Her bir konumu kontrol edilir
                                    // Karaktere en yakın olan konum asıl konumu olarak atanır
                                    enKisaYol(gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)));
                                    int mesafe = gidilecekYol.size();
                                    if (mesafe <= uzaklik) {
                                        uzaklik = mesafe;
                                        enYakinSandikX = sandikX;
                                        enYakinSandikY = sandikY;
                                    }

                                }
                            }

                            Location sandikEnYakinKonum = gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + enYakinSandikX)
                                    + "," + (sandikKonum.getYCoord() + enYakinSandikY));

                            gorulenSandiklar.add( (ChestSilver) sandikEnYakinKonum.getKonumdakiNesne());

                            System.out.println("GÜMÜŞ SANDIK BULUNDU! Konumu: [" + nesneKonum.getXCoord() + "][" + nesneKonum.getYCoord() + "]");

                        }
                    }
                    // Zümrüt sandık
                    else if (nesneKonum.getKonumdakiNesne().getClass() == ChestEmerald.class) {
                        // Nesne daha önce görülmemiş ve toplanmadıysa çalışacak kısım
                        if (!gorulenSandiklar.contains((ChestEmerald) nesneKonum.getKonumdakiNesne()) &&
                                !((ChestEmerald) nesneKonum.getKonumdakiNesne()).getBulunmaDurum() ) {

                            // Sandığın karaktere en yakın olan konumunu bulmak için kullanılır
                            int uzaklik = 9999;
                            int enYakinSandikX = 0;
                            int enYakinSandikY = 0;

                            // Karakterin Sandığın bulunduğu yerden geçmesini sağlayan kısım
                            Location sandikKonum = nesneKonum.getKonumdakiNesne().getKonum();
                            for(int sandikX = 0; sandikX < 2; sandikX++){
                                for(int sandikY = 0; sandikY < 2; sandikY++) {
                                    gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)).setEngelDurum(false);


                                    // Sandığın Her bir konumu kontrol edilir
                                    // Karaktere en yakın olan konum asıl konumu olarak atanır
                                    enKisaYol(gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)));
                                    int mesafe = gidilecekYol.size();
                                    if (mesafe <= uzaklik) {
                                        uzaklik = mesafe;
                                        enYakinSandikX = sandikX;
                                        enYakinSandikY = sandikY;
                                    }

                                }
                            }

                            Location sandikEnYakinKonum = gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + enYakinSandikX)
                                    + "," + (sandikKonum.getYCoord() + enYakinSandikY));

                            gorulenSandiklar.add( (ChestEmerald) sandikEnYakinKonum.getKonumdakiNesne());

                            System.out.println("ZÜMRÜT SANDIK BULUNDU! Konumu: [" + nesneKonum.getXCoord() + "][" + nesneKonum.getYCoord() + "]");

                        }
                    }
                    // Bakır sandık
                    else if (nesneKonum.getKonumdakiNesne().getClass() == ChestCopper.class) {
                        // Nesne daha önce görülmemiş ve toplanmadıysa çalışacak kısım
                        if (!gorulenSandiklar.contains((ChestCopper) nesneKonum.getKonumdakiNesne()) &&
                                !((ChestCopper) nesneKonum.getKonumdakiNesne()).getBulunmaDurum() ) {

                            // Sandığın karaktere en yakın olan konumunu bulmak için kullanılır
                            int uzaklik = 9999;
                            int enYakinSandikX = 0;
                            int enYakinSandikY = 0;

                            // Karakterin Sandığın bulunduğu yerden geçmesini sağlayan kısım
                            Location sandikKonum = nesneKonum.getKonumdakiNesne().getKonum();
                            for(int sandikX = 0; sandikX < 2; sandikX++){
                                for(int sandikY = 0; sandikY < 2; sandikY++) {
                                    gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)).setEngelDurum(false);


                                    // Sandığın Her bir konumu kontrol edilir
                                    // Karaktere en yakın olan konum asıl konumu olarak atanır
                                    enKisaYol(gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + sandikX) + "," +
                                            (sandikKonum.getYCoord() + sandikY)));
                                    int mesafe = gidilecekYol.size();
                                    if (mesafe <= uzaklik) {
                                        uzaklik = mesafe;
                                        enYakinSandikX = sandikX;
                                        enYakinSandikY = sandikY;
                                    }

                                }
                            }

                            Location sandikEnYakinKonum = gp.lokasyonlarHashTable.get((sandikKonum.getXCoord() + enYakinSandikX)
                                    + "," + (sandikKonum.getYCoord() + enYakinSandikY));

                            gorulenSandiklar.add( (ChestCopper) sandikEnYakinKonum.getKonumdakiNesne());

                            System.out.println("BAKIR SANDIK BULUNDU! Konumu: [" + nesneKonum.getXCoord() + "][" + nesneKonum.getYCoord() + "]");

                        }
                    }


                }
            }
        }
    }


    //  En Kısa Yol Fonksiyonu
    public void enKisaYol(Location hedef) {
        shortestPath enKisaYolNesnesi = new shortestPath(getGp().lokasyonlarHashTable, this, hedef, gp.worldDikeyKareSayisi);
        gidilecekYol = enKisaYolNesnesi.getGidilecekLokasyonlar();
    }

    // Karakterin belirli bir konuma gitmesini sağlar
    public void goToLocation(Location gidilecekKonum) {
        atilanAdimSayisi++;
        konum = gidilecekKonum;
        worldY = getKonum().getYCoord() * getGp().tileSize;
        worldX = getKonum().getXCoord() * getGp().tileSize;
        // Daha önce bu konuma gelinmediyse ilerlenene konumlara ekler
        if (!ilerlenenKonumlar.contains(konum)) {
            ilerlenenKonumlar.add(konum);
        }
        gorulenNesneleriKaydet();
        gezilmisKareleriGuncelle();
        gezilebilecekKareleriGuncelle();
    }


    // Oyuncunun oyun sırasında gidemeyeceği karelerin kaydını sağlar
    public void gezilmisKareleriGuncelle() {
        // Sis bölgesinin dış sınırları dışındaki alanı gezilmiş karelere ekler
        for(int x = -(getGp().fogSize / 2) + 1; x < getGp().fogSize / 2; x++){
            for(int y = -(getGp().fogSize / 2) + 1; y < getGp().fogSize / 2; y++){

                // Konum Geçerli mi kontrol eden kısım
                if (getGp().lokasyonlarHashTable.get((getKonum().getXCoord() + x) + "," + (getKonum().getYCoord() + y)) != null) {
                    Location gezilmisKonum = getGp().lokasyonlarHashTable.get((getKonum().getXCoord() + x) + "," + (getKonum().getYCoord() + y));

                    // Konum daha önce gezilmiş mi bakan kısım
                    if (!gezilmisKonumlar.contains(gezilmisKonum)) {
                        // Gezilmiş konumlara ekleyen kısım
                        gezilmisKonumlar.add(gezilmisKonum);

                        // Gezilebilecek konumlarda varsa o listeden çıkartan kısım
                        gezilebilecekKonumlar.remove(gezilmisKonum);

                    }
                }

            }
        }
    }

    // Oyuncunun oyun sırasında gidebileceği rastgele karelerin hareketini sağlar
    public void gezilebilecekKareleriGuncelle() {
        for(int x = -(getGp().fogSize / 2) - 1; x < getGp().fogSize / 2 + 2; x++) {
            for(int y = -(getGp().fogSize / 2) - 1; y < getGp().fogSize / 2 + 2; y++) {
                // Konum Geçerli mi kontrol eden kısım
                if (getGp().lokasyonlarHashTable.get((getKonum().getXCoord() + x) + "," + (getKonum().getYCoord() + y)) != null) {
                    Location gezilebilecekKonum = getGp().lokasyonlarHashTable.get((getKonum().getXCoord() + x) + "," + (getKonum().getYCoord() + y));

                    if (!gezilmisKonumlar.contains(gezilebilecekKonum) && !gezilebilecekKonumlar.contains(gezilebilecekKonum)
                            && !gezilebilecekKonum.getEngelDurum()) {
                        gezilebilecekKonumlar.add(gezilebilecekKonum);
                    }
                }
            }
        }
    }

    // Oyun Sonu Görülen Nesnelerin sırasıyla yazdırılması
    public void gorulenNesneleriYazdir() {
        for (Obstacle engel : kesfedilenNesneler){
            System.out.println( engel.getSinifAd() + " keşfedildi! Konumu: [" + engel.getKonum().getXCoord() +
                    "]["+ engel.getKonum().getYCoord() + "]");
        }
    }


    // Başlangıç Değeri oluşturan metot
    public void baslangicDegerleriOlustur() {
        screenX = getGp().screenWidth / 2 - (getGp().tileSize / 2);
        screenY = getGp().screenHeight / 2 - (getGp().tileSize / 2);


        worldX = getKonum().getXCoord() * getGp().tileSize;
        worldY = getKonum().getYCoord() * getGp().tileSize;

        getGp().playerSpeed = getGp().tileSize;

        kesfedilenNesneler = new ArrayList<>();  // Yolda Gezerken keşfedilen Nesneleri tutacak
        gorulenSandiklar = new ArrayList<>();  // Gezerken gördüğü sandıkları tutacak
        toplanmisSandiklar = new ArrayList<>();  // Toplanmış sandıkları tutacak
    }


    public void update() {
        // Oyun Giriş Ekranındayken Kıpırdama
        if (getGp().oyunDurum.equals("giris")) {

            if(keyH.upPressed) {
                if(girisEkranY > -90)
                    girisEkranY -= 10;
            }
            else if(keyH.downPressed) {
                if(girisEkranY < 210)
                    girisEkranY += 10;
            }
            else if(keyH.leftPressed) {
                if(girisEkranX > -360)
                    girisEkranX -= 10;
            }
            else if(keyH.rightPressed) {
                if(girisEkranX < 290)
                    girisEkranX += 10;
            }


        }
        // Harita oluşturma aşamasında karakterin yapabilecekleri
        else if(getGp().oyunDurum.equals("olusturma")) {

            // Hareket sağlama
            if(keyH.upPressed) {
                worldY -= gp.playerSpeed;
            }
            else if(keyH.downPressed) {
                worldY += gp.playerSpeed;
            }
            else if(keyH.leftPressed) {
                worldX -= gp.playerSpeed;
            }
            else if(keyH.rightPressed) {
                worldX += gp.playerSpeed;
            }


        }
        // Oyun Aşamasında karakterin yapabilecekleri
        else if (getGp().oyunDurum.equals("oyun")) {
            // Etraftaki Engelleri Bulma
            gorulenNesneleriKaydet();

            // Hareketi için görmüş olduğu kareleri güncelleme
            gezilmisKareleriGuncelle();
            gezilebilecekKareleriGuncelle();


            // Görmüş olduğu ve toplanmamış sandıklar varsa oraya yönelmesini sağlayan kısım
            // Hepsini toplamadan rastgele yürümeye başlamaz
            while (!gorulenSandiklar.isEmpty()) {
                Chest sandik = gorulenSandiklar.get(0);

                enKisaYol(sandik.getKonum());
                for(Location sonrakiKonum : gidilecekYol) {

                    goToLocation(sonrakiKonum);
                }
                sandik.setBulunmaDurum(true);
                sandik.setImagePath("");
                gorulenSandiklar.remove(sandik);
                toplanmisSandiklar.add(sandik);
            }

            // Tüm Sandıklar Toplandı mı Kontrol Eden kısım
            if (toplanmisSandiklar.size() == 20) {
                getGp().FPS = 60;
                System.out.println("Atılan adım sayısı: " + atilanAdimSayisi);
                System.out.println("Bulunan sandık sayısı: " + toplanmisSandiklar.size());
                gorulenNesneleriYazdir();

                int altinS = 0;
                int gumusS = 0;
                int zumrutS = 0;
                int bakirS = 0;

                while(altinS != 5) {
                    for(Chest sandik : toplanmisSandiklar) {
                        if (sandik.getSinifAd().equals("Altın Sandık")) {
                            siraliToplanmisSandiklar.add(0, sandik);
                            altinS++;
                        }
                    }
                }
                while(gumusS != 5) {
                    for(Chest sandik : toplanmisSandiklar) {
                        if (sandik.getSinifAd().equals("Gümüş Sandık")) {
                            siraliToplanmisSandiklar.add(5, sandik);
                            gumusS++;
                        }
                    }
                }
                while(zumrutS != 5) {
                    for(Chest sandik : toplanmisSandiklar) {
                        if (sandik.getSinifAd().equals("Zümrüt Sandık")) {
                            siraliToplanmisSandiklar.add(10, sandik);
                            zumrutS++;
                        }
                    }
                }
                while(bakirS != 5) {
                    for(Chest sandik : toplanmisSandiklar) {
                        if (sandik.getSinifAd().equals("Bakır Sandık")) {
                            siraliToplanmisSandiklar.add(15, sandik);
                            bakirS++;
                        }
                    }
                }



                getGp().oyunDurum = "bitis";
            }
            else {
                // Haritada Rastgele gezinmesini sağlayan kısım
                if (!gezilebilecekKonumlar.isEmpty()) {
                    enKisaYol(gezilebilecekKonumlar.get(gezilebilecekKonumlar.size() - 1));
                    for(Location sonrakiKonum : gidilecekYol) {
                        goToLocation(sonrakiKonum);
                        // Gezinirken yolda sandık gördüyse rastgele gezinmeyi durduran kısım
                        if (!gorulenSandiklar.isEmpty()) {
                            break;
                        }
                    }
                }
            }

        }
        // Bitiş Aşamasında Karakterin yapabilecekleri
        else if (gp.oyunDurum.equals("bitis")) {

            // Hareket sağlama
            if(keyH.upPressed) {
                worldY -= gp.playerSpeed;
            }
            else if(keyH.downPressed) {
                worldY += gp.playerSpeed;
            }
            else if(keyH.leftPressed) {
                worldX -= gp.playerSpeed;
            }
            else if(keyH.rightPressed) {
                worldX += gp.playerSpeed;
            }
        }


        // HANGİ AŞAMADA OLURSA OLUNSUN ÇALIŞACAK KISIM
        // Animasyon Hızı
        animationCounter++;
        if (animationCounter > getGp().FPS / 10 - 1) {
            if (animationNum == 1) {
                animationNum = 2;
            }
            else if (animationNum == 2) {
                animationNum = 3;
            }
            else if (animationNum == 3) {
                animationNum = 4;
            }
            else if (animationNum == 4) {
                animationNum = 5;
            }
            else if (animationNum == 5) {
                animationNum = 6;
            }
            else if (animationNum == 6) {
                animationNum = 1;
            }
            animationCounter = 0;
        }

        // Animasyon
        if (animationNum == 1) {
            setCharImagePath("textures/character/character_2.png");
        }
        else if (animationNum == 2) {
            setCharImagePath("textures/character/character_3.png");
        }
        else if (animationNum == 3) {
            setCharImagePath("textures/character/character_4.png");
        }
        else if (animationNum == 4) {
            setCharImagePath("textures/character/character_5.png");
        }
        else if (animationNum == 5) {
            setCharImagePath("textures/character/character_6.png");
        }
        else if (animationNum == 6) {
            setCharImagePath("textures/character/character.png");
        }
    }



    // ÇİZİM FONKSİYONLARI
    // Değişken Yükseklik ve Genişlik ile karakter çizimi
    public void draw(Graphics2D g2) {
        // Karakter Çizimi
        Image karakterImage = new ImageIcon(getCharImagePath()).getImage();
        g2.drawImage(karakterImage, getScreenX(), getScreenY(), gp.tileSize, gp.tileSize, null);

    }


    // Sabit Yükseklik ve Genişlik ile Karkater Çizimi
    public void draw(Graphics2D g2, int xCord, int yCord, int width, int height) {
        // Karakter Çizimi
        Image karakterImage = new ImageIcon(getCharImagePath()).getImage();
        g2.drawImage(karakterImage, xCord, yCord, width, height, null);
    }


    // Karakterin ilerlediği kareleri yeşil renkle çizen fonksiyon
    public void ilerlenenKareleriCizdir(Graphics2D g2) {
        for(Location gezilenKonum : getIlerlenenKonumlar()) {

            // Gezilen Konum kordinatları
            int konumX = gezilenKonum.getXCoord() * getGp().tileSize - getWorldX() + getScreenX();
            int konumY = gezilenKonum.getYCoord() * getGp().tileSize - getWorldY() + getScreenY();

            // Konum Genişlikleri
            int kareGenislik = getGp().tileSize;
            int kareYukseklik = getGp().tileSize;


            Rectangle2D gezilenKonumArea = new Rectangle2D.Double(konumX, konumY, kareGenislik, kareYukseklik);

            // Gezilen Konumun Renginin Ayarlanması
            Color kareColor = new Color(0, 1, 0, 0.6f);
            g2.setColor(kareColor);

            // Gezilen Konumun Renklendirilmesi
            g2.fill(gezilenKonumArea);
        }
    }


    // Constructor Metotları
    Character() {}

    // Konum Bilgisi olmadan constructor metodu
    Character(String id, String ad){
        setId(id);
        setAd(ad);
        ilerlenenKonumlar.add(konum);
    }

    // Hazır konum bilgisiyle constructor metodu
    Character(String id, String ad, Location konum){
        setId(id);
        setAd(ad);
        setKonum(konum);
        ilerlenenKonumlar.add(konum);
    }

}
