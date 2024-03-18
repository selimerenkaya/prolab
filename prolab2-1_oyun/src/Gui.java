import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;


// Pencere
public class Gui extends JFrame{
    Character karakterNesnesi;
    ArrayList<Obstacle> engelListesi;
    ArrayList<Obstacle> sandikListesi;
    ArrayList<DynamicObstacle> dinamikEngelListesi;


    // Get - Set Metotları
    // GET METOTLARI
    Character getKarakterNesnesi() {
        return karakterNesnesi;
    }

    ArrayList<Obstacle> getEngelListesi() {
        return engelListesi;
    }

    ArrayList<Obstacle> getSandikListesi() {
        return sandikListesi;
    }

    ArrayList<DynamicObstacle> getDinamikEngelListesi() {
        return dinamikEngelListesi;
    }

    // SET METOTLARI
    void setKarakterNesnesi(Character karakterNesnesi){
        this.karakterNesnesi = karakterNesnesi;
    }

    void setEngelListesi(ArrayList<Obstacle> engelListesi) {
        this.engelListesi = engelListesi;
    }

    void setSandikListesi(ArrayList<Obstacle> sandikListesi) {
        this.sandikListesi = sandikListesi;
    }

    void setDinamikEngelListesi(ArrayList<DynamicObstacle> dinamikEngelListesi) {
        this.dinamikEngelListesi = dinamikEngelListesi;
    }


    // Constructorlar
    public Gui(Hashtable<String, Location> lokasyonlar,
               int yatayKareSayisi, int dikeyKareSayisi,
               Character karakterNesnesi,
               ArrayList<Obstacle> engelListesi,
               ArrayList<DynamicObstacle> dinamikEngelListesi,
               ArrayList<Obstacle> sandikListesi) {
        // Nesnelerin atanması
        setKarakterNesnesi(karakterNesnesi);
        setEngelListesi(engelListesi);
        setDinamikEngelListesi(dinamikEngelListesi);
        setSandikListesi(sandikListesi);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Yalçının Maceraları");



        GuiPanel gamePanel = new GuiPanel(lokasyonlar, yatayKareSayisi, dikeyKareSayisi,
                getKarakterNesnesi(), getEngelListesi(), getDinamikEngelListesi(), getSandikListesi());
        this.add(gamePanel);


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        gamePanel.startGameThread();

    }

}


// Pencere içeriği
class GuiPanel extends JPanel implements Runnable {
    // Oyunun Ne Durumda Olduğunu Belirleyen Değişkenler
    String oyunDurum;
    String girisEkrani = "giris";
    String olusturmaEkrani = "olusturma";
    String oyunEkrani = "oyun";
    String bitisEkrani = "bitis";

    // Mouse Dinlemesi Değişkenleri
    boolean tiklandiGiris = false;
    boolean tiklandiOlusturma = false;

    // Font Nesnesi
    Font purisaB;

    // Oyun Nesneleri
    Character karakter;
    ArrayList<Obstacle> engelListesi;
    ArrayList<Obstacle> sandikListesi;
    ArrayList<DynamicObstacle> dinamikEngelListesi;
    Hashtable<String, Location> lokasyonlarHashTable;


    // Ekran Ayarları
    public final int originalTileSize = 20; // 20x20 Orijinal kare boyutu
    public final int scale = 3; // Oran

    public int tileSize = originalTileSize * scale; // 1 karenin hangi oranda gözükeceği
    final int maxScreenCol = 24; // Ekranda gözükecek dikey kare sayisi
    final int maxScreenRow = 12; // Ekrande gözükecek yatay kare sayısı
    final int screenWidth = tileSize * maxScreenCol; // Ekran Genişliği
    final int screenHeight = tileSize * maxScreenRow; // Ekran yüksekliği

    // Dünya değişkenleri
    int worldYatayKareSayisi;
    int worldDikeyKareSayisi;
    int worldWidth;
    int worldHeight;
    int newWorldWidth;
    int newWorldHeight;

    // Çizgilerin renk nesnesi
    float transparency = 0.4f;
    Color transparentBlack = new Color(0, 0, 0, transparency);

    // Fps
    int FPS = 60;

    // Ayar Değişkenleri
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Thread gameThread;

    // Sis Ayarları
    int fogSize = 25;  // Kaç Karelik alan görebileceği - 7 orijinal değeri
    //  NOT - yazan değer önemsiz oyun moduna göre kendi ayarlıyor
    Fog fogManager;



    // Karakterin hızı
    public double playerSpeed = tileSize;


    // Fonksiyonlar

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(0);
    }


    @Override
    public void run() {
        // zaman değişkenleri
        double drawInternal = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInternal;
            lastTime = currentTime;

            if (delta > 1)
            {
                // 1- Update - Karakter lokasyonları vs.
                update();

                // 2 - Draw - Ekrandaki nesneleri güncellenmiş bilgiyle çiz
                repaint();


                delta--;
            }

        }

    }


    // Güncelleme alınmasını sağlar
    public void update() {

        // Giriş Ekranındayken Çizilecekler
        if (oyunDurum.equals(girisEkrani))
        {

            karakter.update();

        }
        // Harita Oluşturma Ekranındayken Çizilecekler
        else if (oyunDurum.equals(olusturmaEkrani))
        {

            karakter.update();
            updateDinamikEngeller();

        }
        // Oyun Oynanma Ekranındayken Çizilecekler
        else if (oyunDurum.equals(oyunEkrani))
        {
            karakter.update();
            updateDinamikEngeller();

        }
        // Bitiş Ekranında Çizilecekler
        else if (oyunDurum.equals(bitisEkrani))
        {
            karakter.update();
            updateDinamikEngeller();
        }

    }

    // Her şeyin çizdirilmesini sağlar
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Giriş Ekranındayken Çizilecekler
        if (oyunDurum.equals(girisEkrani))
        {

            girisEkraniCiz(g2);

        }
        // Harita Oluşturma Ekranındayken Çizilecekler
        else if (oyunDurum.equals(olusturmaEkrani))
        {

            olusturmaEkraniCiz(g2);

        }
        // Oyun Oynanma Ekranındayken Çizilecekler
        else if (oyunDurum.equals(oyunEkrani))
        {
            oyunEkraniCiz(g2);
        }
        // Bitiş Ekranında Çizilecekler
        else if (oyunDurum.equals(bitisEkrani))
        {
            oyunBitisCiz(g2);

        }

        g2.dispose();
    }

    // GİRİS EKRANI FONKSİYONLARI
    // Giriş Ekranını Çizen Fonksiyon
    public void girisEkraniCiz(Graphics2D g2){
        g2.setFont(purisaB);

        // Giris Ekranı Arkaplanı
        Image backgroundImage = new ImageIcon("textures/girisEkran.png").getImage();
        g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);

        // Karakter Çizimi
        karakter.draw(g2,
                screenWidth/2 - 30 + karakter.girisEkranX,
                60 * 5 + karakter.girisEkranY,
                80, 80);

        // Giris Ekranı Arkaplanı Ön Taraf
        Image frontgroundImage = new ImageIcon("textures/girisEkranOn.png").getImage();
        g2.drawImage(frontgroundImage, 0, 0, screenWidth, screenHeight, null);

        // Giris Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String girisBaslik = "Yalçının Maceraları";
        int x = getXOrtalanmisBaslik(girisBaslik, g2);
        int y = 60 * 3;

        // Giriş Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(girisBaslik, x + 5, y + 5);

        // Giriş Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(girisBaslik, x, y);


        // Harita Oluşturma Bilgisi Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        String olusturmaBaslik = "Harita oluşturmak için tıklayın";
        x = getXOrtalanmisBaslik(olusturmaBaslik, g2);
        y = 60 * 10;

        // Harita Oluşturma Butonu
        Image butonImage = new ImageIcon("textures/buton.png").getImage();
        g2.drawImage(butonImage, -130, y-100, screenWidth + 250, 185, null);


        // Harita Oluşturma Bilgisi Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(olusturmaBaslik, x + 3, y + 3);

        // Harita Oluşturma Bilgisi Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(olusturmaBaslik, x, y);


        if (oyunDurum.equals("giris")) {
            // Buton Algılayıcısı
            MouseListener ml = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(!tiklandiGiris) {
                        int mouseX = e.getX();
                        int mouseY = e.getY();
                        if (835 > mouseX && mouseX > 155 && 670 > mouseY && mouseY > 514) {
                            oyunDurum = "olusturma";
                            fogSize = 9999;
                            karakter.baslangicDegerleriOlustur();
                            g2.dispose();
                            tiklandiGiris = true;
                        }
                    }
                }
            };
            this.addMouseListener(ml);
        }

    }

    // Yazının Ekran için ortalanmaış x kordinatını döndürür
    public int getXOrtalanmisBaslik(String text, Graphics2D g2) {
        int uzunluk = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return screenWidth/2 - uzunluk/2;
    }

    // OLUŞTURMA EKRANI FONKSİYONLARI
    public void olusturmaEkraniCiz(Graphics2D g2) {
        g2.setColor(transparentBlack);
        g2.setFont(purisaB);

        // Arkaplanın Çizimi
        arkaplanCizdir(g2);

        // Karelerin Sınırlarının Çizimi
        kareSinirlariCiz(g2);

        // Karakter Çizimi
        karakter.draw(g2);

        // Engellerin Çizimi
        sabitEngelleriCizdir(g2);

        // Dinamik Engellerin Çizimi
        dinamikEngelleriCizdir(g2);

        // Sandıkların Ekrana Çizimi
        sandiklariCizdir(g2);

        // Oluşturma Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String olusturmaBaslik = "Harita Oluşturma Aşaması";
        int x = getXOrtalanmisBaslik(olusturmaBaslik, g2);
        int y = 60 * 2;

        // Giriş Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(olusturmaBaslik, x + 3, y + 3);

        // Giriş Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(olusturmaBaslik, x, y);



        // Oyun Başlatma Bilgisi Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        String baslatmaBaslik = "Başlatmak için tıklayın";
        x = getXOrtalanmisBaslik(baslatmaBaslik, g2);
        y = 60 * 10;


        // Harita Oluşturma Butonu
        Image butonImage = new ImageIcon("textures/buton.png").getImage();
        g2.drawImage(butonImage, -130, y-100, screenWidth + 250, 185, null);


        // Oyun Başlatma Bilgisi Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(baslatmaBaslik, x + 3, y + 3);

        // Oyun Başlatma Bilgisi Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(baslatmaBaslik, x, y);


        // Mouse Dinlemesi
        if (oyunDurum.equals("olusturma")) {
            // Buton Algılayıcısı
            MouseListener ml = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(!tiklandiOlusturma) {
                        int mouseX = e.getX();
                        int mouseY = e.getY();
                        if (835 > mouseX && mouseX > 155 && 670 > mouseY && mouseY > 514) {
                            oyunDurum = "oyun";
                            fogSize = 7;
                            karakter.baslangicDegerleriOlustur();
                            g2.dispose();
                            tiklandiOlusturma = true;
                        }
                    }
                }
            };
            this.addMouseListener(ml);
        }

    }

    // OYUN EKRANI FONKSİYONLARI
    // Oyun Ekranını Çizen Fonksiyon
    public void oyunEkraniCiz(Graphics2D g2){
        g2.setColor(transparentBlack);

        // Arkaplanın Çizimi
        arkaplanCizdir(g2);

        // Karelerin Sınırlarının Çizimi
        kareSinirlariCiz(g2);

        // Karakter Çizimi
        karakter.draw(g2);

        // Engellerin Çizimi
        sabitEngelleriCizdir(g2);

        // Dinamik Engellerin Çizimi
        dinamikEngelleriCizdir(g2);

        // Sandıkların Ekrana Çizimi
        sandiklariCizdir(g2);

        // Siz Çizimi
        fogManager = new Fog(this, fogSize);
        fogManager.draw(g2);

        // BAŞLIKLAR
        g2.setFont(purisaB);

        // Oyun Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        String olusturmaBaslik = "Oyun Aşaması";
        int x = getXOrtalanmisBaslik(olusturmaBaslik, g2);
        int y = 60 * 2;

        // Oyun Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(olusturmaBaslik, x + 3, y + 3);

        // Oyun Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(olusturmaBaslik, x, y);

        kesfedilenNesnelerinYaziCizdir(g2);

    }

    // OYUN BİTİŞ EKRANI FONKSİYONLARI
    public void oyunBitisCiz(Graphics2D g2){
        g2.setColor(transparentBlack);

        // Arkaplanın Çizimi
        arkaplanCizdir(g2);

        // Karelerin Sınırlarının Çizimi
        kareSinirlariCiz(g2);

        // Karakter Çizimi
        karakter.draw(g2);
        karakter.ilerlenenKareleriCizdir(g2);

        // Engellerin Çizimi
        sabitEngelleriCizdir(g2);

        // Dinamik Engellerin Çizimi
        dinamikEngelleriCizdir(g2);

        // Sandıkların Ekrana Çizimi
        sandiklariCizdir(g2);


        // Siz Çizimi
        fogManager = new Fog(this, fogSize);
        fogManager.draw(g2);


        // BAŞLIKLAR
        g2.setFont(purisaB);

        // Bitiş Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        String olusturmaBaslik = "Bitiş Aşaması";
        int x = getXOrtalanmisBaslik(olusturmaBaslik, g2);
        int y = 60 * 2;

        // Bitiş Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(olusturmaBaslik, x + 3, y + 3);

        // Bitiş Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(olusturmaBaslik, x, y);



        // Oyun Bitmiş Bilgisi Başlığı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        String baslatmaBaslik = "Oyun Bitmiş Bulunmaktadır";
        x = getXOrtalanmisBaslik(baslatmaBaslik, g2);
        y = 60 * 10;

        // Oyun Bitmiş Bilgisi Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(baslatmaBaslik, x + 2, y + 2);

        // Oyun Bitmiş Bilgisi Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(baslatmaBaslik, x, y);


        // Atılan Adım Sayısı Bilgisi
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        String atilanAdimSayi = "Atılan Adım Sayısı: " + karakter.atilanAdimSayisi;
        x = getXOrtalanmisBaslik(atilanAdimSayi, g2);
        y = 60 * 11;

        // Atılan Adım Sayısı Bilgisi Gölgesi
        g2.setColor(Color.black);
        g2.drawString(atilanAdimSayi, x + 2, y + 2);

        // Atılan Adım Sayısı Bilgisini çizme
        g2.setColor(Color.WHITE);
        g2.drawString(atilanAdimSayi, x, y);

        kesfedilenSandiklarinYaziCizdir(g2);
        kesfedilenNesnelerinYaziCizdir(g2);

    }




    // Haritadaki karelerin sınırlarının çizdirilmesini sağlayan fonksiyon
    public void kareSinirlariCiz(Graphics2D g2) {
        // Kareleri Oluşturma
        // Dikey Çizgiler
        int xCurrent;
        int yCurrent;
        for(xCurrent = 0; xCurrent < worldDikeyKareSayisi * tileSize; xCurrent = xCurrent + tileSize){
            int cizgiX = xCurrent - karakter.getWorldX() + karakter.getScreenX();
            int cizgiY = worldDikeyKareSayisi * tileSize - karakter.getWorldY() + karakter.getScreenY();
            Line2D line1 = new Line2D.Float(cizgiX, - karakter.getWorldY() + karakter.getScreenY(), cizgiX, cizgiY);
            g2.draw(line1);
        }

        // Yatay Çizgiler
        for(yCurrent = 0; yCurrent < worldYatayKareSayisi * tileSize; yCurrent = yCurrent + tileSize){
            int cizgiX = worldYatayKareSayisi * tileSize - karakter.getWorldX() + karakter.getScreenX();
            int cizgiY = yCurrent - karakter.getWorldY() + karakter.getScreenY();
            Line2D line1 = new Line2D.Float(- karakter.getWorldX() + karakter.getScreenX(), cizgiY, cizgiX, cizgiY);
            g2.draw(line1);
        }

        // Karelerin kenarlarını tamamlayan kısım
        int cizgiX = xCurrent - karakter.getWorldX() + karakter.getScreenX();
        int cizgiY = yCurrent - karakter.getWorldY() + karakter.getScreenY();
        Line2D edge1 = new Line2D.Float(cizgiX, - karakter.getWorldY() + karakter.getScreenY(), cizgiX, cizgiY);
        Line2D edge2 = new Line2D.Float(- karakter.getWorldX() + karakter.getScreenX(), cizgiY, cizgiX, cizgiY);
        g2.draw(edge1);
        g2.draw(edge2);

    }

    // Arkaplanın çizdirilmesini sağlayan fonksiyon
    public void arkaplanCizdir(Graphics2D g2) {

        // Resim güncellenecek
        Image backgroundImage = new ImageIcon("textures/background.png").getImage();
        g2.drawImage(backgroundImage,
                - karakter.getWorldX() + karakter.getScreenX(),
                - karakter.getWorldY() + karakter.getScreenY(),
                newWorldWidth, newWorldHeight,
                null);

    }

    // Sabit Engellerin çizdirilmesini sağlayan fonksiyon
    public void sabitEngelleriCizdir(Graphics2D g2) {
        // Sabit Engellerin Çizimi
        for (Obstacle engel : engelListesi) {
            Image engelImage = new ImageIcon(engel.getImagePath()).getImage();
            // Çizileceği Konumların Hesaplaması
            int engelWorldX = engel.getKonum().getXCoord() * tileSize - karakter.getWorldX() + karakter.getScreenX();
            int engelWorldY = engel.getKonum().getYCoord() * tileSize - karakter.getWorldY() + karakter.getScreenY();

            // Çizim
            g2.drawImage(engelImage,
                    engelWorldX,
                    engelWorldY,
                    engel.getBoyutX() * tileSize,
                    engel.getBoyutY() * tileSize,
                    null);
        }
    }

    // Dinamik Engellerin çizdirilmesini sağlayan fonksiyon
    public void dinamikEngelleriCizdir(Graphics2D g2) {
        // Dinamik Engellerin Çizimi
        for (DynamicObstacle engel : dinamikEngelListesi) {

            dinamikEngelGuzergahCizdir(g2, engel);

            Image engelImage = new ImageIcon(engel.getImagePath()).getImage();
            // Çizileceği Konumların Hesaplaması
            int engelWorldX = engel.getIlerlenenKareX() * tileSize + engel.getKonum().getXCoord() * tileSize -
                    karakter.getWorldX() + karakter.getScreenX();
            int engelWorldY = engel.getIlerlenenKareY() * tileSize + engel.getKonum().getYCoord() * tileSize -
                    karakter.getWorldY() + karakter.getScreenY();

            // Çizim
            g2.drawImage(engelImage,
                    engelWorldX,
                    engelWorldY,
                    engel.getBoyutX() * tileSize,
                    engel.getBoyutY() * tileSize,
                    null);
        }
    }

    // Dinamik Engelin Güzergahının Çizdirilmesini Sağlar
    public void dinamikEngelGuzergahCizdir(Graphics2D g2, DynamicObstacle engel) {

        // Güzergahın başlangıç konumu
        int guzergahBaslangicX = engel.getKonum().getXCoord() * tileSize -
                karakter.getWorldX() + karakter.getScreenX();
        int guzergahBaslangicY = engel.getKonum().getYCoord() * tileSize -
                karakter.getWorldY() + karakter.getScreenY();

        // Güzergahın Genişlikleri
        int guzergahWidth = tileSize * engel.getIleriMaxKareX() + engel.getBoyutX() * tileSize;
        int guzergahHeight = tileSize * engel.getIleriMaxKareY() + engel.getBoyutY() * tileSize;


        Rectangle2D guzergahArea = new Rectangle2D.Double(guzergahBaslangicX, guzergahBaslangicY,
                guzergahWidth, guzergahHeight);

        // Güzergahın Renginin Ayarlanması
        Color guzergahColor = new Color(1, 0, 0, 0.4f);
        g2.setColor(guzergahColor);

        // Güzergahın Çizdirilmesi
        g2.fill(guzergahArea);

    }

    // Keşfedilen nesnelerin ekrana yazdırılmasını sağlayana metot
    public void kesfedilenNesnelerinYaziCizdir(Graphics2D g2) {
        ArrayList<Obstacle> nesneler = karakter.kesfedilenNesneler;
        int boyut = karakter.kesfedilenNesneler.size();

        // Yazılar
        g2.setFont(purisaB);

        int nesneCounter = 0;
        for(int i = 0; i < boyut; i++) {
            if (nesneCounter < 50) {
                // Oyun Başlığı
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12F));
                String nesneBaslik = karakter.kesfedilenNesneler.get(i).getSinifAd() + " Keşfedildi!";
                int x = screenWidth - 400;
                int y = 20 + nesneler.indexOf(karakter.kesfedilenNesneler.get(i)) * 12;

                // Oyun Başlığı Gölgesi
                g2.setColor(Color.black);
                g2.drawString(nesneBaslik, x + 1, y + 1);

                // Oyun Başlığını Çizme
                g2.setColor(Color.WHITE);
                g2.drawString(nesneBaslik, x, y);
            }
            else {
                // Oyun Başlığı
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12F));
                String nesneBaslik = karakter.kesfedilenNesneler.get(i).getSinifAd() + " Keşfedildi!";
                int x = screenWidth - 200;
                int y = 20 + nesneler.indexOf(karakter.kesfedilenNesneler.get(i)) * 12 - 50 * 12;

                // Oyun Başlığı Gölgesi
                g2.setColor(Color.black);
                g2.drawString(nesneBaslik, x + 1, y + 1);

                // Oyun Başlığını Çizme
                g2.setColor(Color.WHITE);
                g2.drawString(nesneBaslik, x, y);
            }

            nesneCounter++;

        }
    }

    // Keşfedilen nesnelerin ekrana yazdırılmasını sağlayana metot
    public void kesfedilenSandiklarinYaziCizdir(Graphics2D g2) {
        ArrayList<Chest> sandiklar = karakter.siraliToplanmisSandiklar;

        // Yazılar
        g2.setFont(purisaB);

        // Sandık Başlık Yazısı
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        String bulunanSandiklar = "Bulunan Sandıklar ve Konumları";
        int x = 10;
        int y = 100;

        // Sandık Başlığı Gölgesi
        g2.setColor(Color.black);
        g2.drawString(bulunanSandiklar, x + 2, y + 2);

        // Sandık Başlığını Çizme
        g2.setColor(Color.WHITE);
        g2.drawString(bulunanSandiklar, x, y);

        // Sandıkların Yazısı
        for(Chest sandik : sandiklar) {
            // Sandıkların Başlığı
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            String sandikBaslik = sandik.getSinifAd() + " Konum: [" + sandik.getKonum().getXCoord() +
                    "][" + sandik.getKonum().getYCoord() + "]";
            x = 10;
            y = 120 + sandiklar.indexOf(sandik) * 20;

            // Sandıkların Başlığı Gölgesi
            g2.setColor(Color.black);
            g2.drawString(sandikBaslik, x + 1, y + 1);

            // Sandıkların Başlığını Çizme
            g2.setColor(Color.WHITE);
            g2.drawString(sandikBaslik, x, y);
        }
    }


    // Sandıkların çizdirilmesini sağlayan fonksiyon
    public void sandiklariCizdir(Graphics2D g2) {
        for (Obstacle sandik : sandikListesi) {
            Image sandikImage = new ImageIcon(sandik.getImagePath()).getImage();
            // Çizileceği Konumların Hesaplaması
            int sandikWorldX = sandik.getKonum().getXCoord() * tileSize - karakter.getWorldX() + karakter.getScreenX();
            int sandikWorldY = sandik.getKonum().getYCoord() * tileSize - karakter.getWorldY() + karakter.getScreenY();

            // Çizim
            g2.drawImage(sandikImage,
                    sandikWorldX,
                    sandikWorldY,
                    sandik.getBoyutX() * tileSize,
                    sandik.getBoyutY() * tileSize,
                    null);
        }
    }

    // UPDATE Fonksiyonları
    // Dinamik Engellerin Update Fonksiyonu
    public void updateDinamikEngeller() {
        for(DynamicObstacle dinamikEngel : dinamikEngelListesi) {
            dinamikEngel.update();
        }
    }



    // Haritaya yakınlaştırmayı veya uzaklaştırmayı sağlayan fonksiyon
    public void zoomInOut(int zoomMiktari) {

        int oldWorldWidth = tileSize * worldYatayKareSayisi;
        tileSize += zoomMiktari;
        newWorldWidth = tileSize * worldYatayKareSayisi;
        newWorldHeight = tileSize * worldDikeyKareSayisi;

        double multiplier = (double)newWorldWidth/oldWorldWidth;
        double newPlayerWorldX = karakter.worldX * multiplier;
        double newPlayerWorldY = karakter.worldY * multiplier;

        karakter.worldX = newPlayerWorldX;
        karakter.worldY = newPlayerWorldY;

        playerSpeed = playerSpeed * multiplier;
    }


    // MÜZİK FONKSİYONLARI
    // İstenilen Müziğin çalınmasını sağlar
    public void playMusic(int index) {
        sound.setFile(index);
        sound.play();
        sound.loop();
    }

    // Çalan Müziği durdurur
    public void stopMusic() {
        sound.stop();
    }

    // Minik Ses Efektleri için kullanılır
    public void playSE(int index) {
        sound.setFile(index);
        sound.play();
    }

    // Consturctor metodu
    public GuiPanel(Hashtable<String, Location> lokasyonlarHashTable,
                    int yatayKareSayisi, int dikeyKareSayisi,
                    Character karakter, ArrayList<Obstacle> engelListesi,
                    ArrayList<DynamicObstacle> dinamikEngelListesi,
                    ArrayList<Obstacle> sandikListesi) {
        // Tüm Lokasyonların kaydedilmesi
        this.lokasyonlarHashTable = lokasyonlarHashTable;


        // Oyunun Temel Ayarlarının Atanması
        oyunDurum = "giris";

        // Font Ayarları
        try {
            InputStream is = getClass().getResourceAsStream("Font/Purisa_Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Error" + e);
        }

        // Nesne Atamaları
        // 1- Karakter Nesnesinin Atamaları
        this.karakter = karakter;
        this.karakter.setGp(this);
        this.karakter.setKeyH(keyH);
        this.karakter.baslangicDegerleriOlustur();  // Kullanılacak Değerlerin atamasını yapan fonksiyon

        // 2 - Sabit Engel Nesnelerinin Atamaları
        this.engelListesi = engelListesi;

        // 3 - Dinamik Engel Nesnelerinin Atamaları
        this.dinamikEngelListesi = dinamikEngelListesi;
        for(DynamicObstacle dinamikEngel : this.dinamikEngelListesi) {
            dinamikEngel.setGp(this);
        }

        // 4 - Sandık Nesnesinin Atamaları
        this.sandikListesi = sandikListesi;

        // 5 - Dünya Nesnesinni Atamaları
        this.worldYatayKareSayisi = yatayKareSayisi;
        this.worldDikeyKareSayisi = dikeyKareSayisi;
        worldWidth = tileSize * worldYatayKareSayisi;
        worldHeight = tileSize * worldDikeyKareSayisi;
        newWorldWidth = worldWidth;
        newWorldHeight = worldHeight;


        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH); // Tuş Dinleyici
        this.setFocusable(true); // Tuş algılamayı sağlar
    }
}