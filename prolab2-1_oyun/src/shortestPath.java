import java.util.ArrayList;
import java.util.Hashtable;

// En Kısa Yolu Bulmaya yaran fonksiyonun kullandığı sınıf
public class shortestPath {

    // Kullanılacak değişkenler
    public int haritaBoyut;
    public int karakterXCord;
    public int karakterYCord;
    public int hedefX;
    public int hedefY;

    // Çözme Durumu
    // sınıf oluşturulunca true olur
    // en kısa yol bulununca tekrardan false olur
    public boolean solving = true;

    // Harita ve algoritma değişkeni
    Node[][] map;
    AStarAlgoritmasi Alg = new AStarAlgoritmasi();


    // Gidilen yolun kordinatlarını düğüm şeklinde tutan arraylist
    ArrayList<Node> gidilecekYol = new ArrayList<>();
    Location hedef;
    Hashtable<String, Location> locationHashtable;
    ArrayList<Location> gidilecekLokasyonlar = new ArrayList<>();

    // Gidilecek En kısa yolu location arraylisti şeklinde döndüren fonksiyon
    public ArrayList<Location> getGidilecekLokasyonlar() {
        for(Node konum : gidilecekYol) {
            gidilecekLokasyonlar.add(locationHashtable.get(konum.getXCord() + "," + konum.getYCord()));
        }
        return gidilecekLokasyonlar;
    }


    // Harita oluşturma fonksiyonu
    public void haritaOlustur(Hashtable<String, Location> locationHashtable) {

        map = new Node[haritaBoyut][haritaBoyut];	// Hashtabledaki konumlara göre matris oluşturur
        for(int xCord = 0; xCord < haritaBoyut; xCord++) {
            for(int yCord = 0; yCord < haritaBoyut; yCord++) {
                // Sandık Nesneleri Engel Olarak Görülmeyecek
                // Konumda engel varsa engelli olarak atar
                if(locationHashtable.get(xCord + "," + yCord).getEngelDurum()) {
                    map[xCord][yCord] = new Node(2, xCord, yCord);
                }
                // Geri kalan tüm kareler boş olarak ayarlanır
                else {
                    map[xCord][yCord] = new Node(3, xCord, yCord);
                }
            }
        }

        map[karakterXCord][karakterYCord] = new Node(0, karakterXCord, karakterYCord);
        map[karakterXCord][karakterYCord].setHops(0);

        // Bitiş Noktasının Ayarlanması
        map[hedefX][hedefY] = new Node(1, hedefX, hedefY);
    }

    // Aramayı Başlatır
    public ArrayList<Node> startSearch() {
        if(solving) {
            return Alg.AStar(); // Hedefe giden en kısa yolun kordinatlarını arraylist<node> şeklinde döndürür.
        }
        return null;
    }


    // Constructor
    public shortestPath(Hashtable<String, Location> locationHashtable, Character karakter, Location hedef, int haritaBoyut) {
        // Başlama ve hedef kordinatlarının atanması
        hedefX = hedef.getXCoord();
        hedefY = hedef.getYCoord();
        karakterXCord = karakter.getKonum().getXCoord();
        karakterYCord = karakter.getKonum().getYCoord();
        this.haritaBoyut = haritaBoyut; // Sadece kare haritalarda çalışır

        // Gerekli değişkenlerin atanması
        this.hedef = hedef;
        this.locationHashtable = locationHashtable;

        // En kısa yolu bulmak için kullanılacak olan matrisin oluşturulması
        haritaOlustur(locationHashtable);

        // En kısa yolun aranmaya başlanması
        startSearch();
    }



    // A Star Algortimasının Sınıfı
    class AStarAlgoritmasi {
        // YAKINDAKİ YOLLAR ÖNCE TARANIR VE YAKINDAKİ SANDIKLAR DAHA HIZLI BULUNUR
        public ArrayList<Node> AStar() {
            ArrayList<Node> oncelikliDugumSiralama = new ArrayList<>();
            oncelikliDugumSiralama.add(map[karakterXCord][karakterYCord]);
            while(solving) {
                if(oncelikliDugumSiralama.size() <= 0) {
                    solving = false;
                    break;
                }
                int hops = oncelikliDugumSiralama.get(0).getHops()+1;
                ArrayList<Node> kontrolEdilmisler = komsuDugumleriArastir(oncelikliDugumSiralama.get(0),hops);
                if(!kontrolEdilmisler.isEmpty()) {
                    oncelikliDugumSiralama.remove(0);
                    oncelikliDugumSiralama.addAll(kontrolEdilmisler);
                } else {
                    oncelikliDugumSiralama.remove(0);
                }
                sortQue(oncelikliDugumSiralama);	// Öncelikli olanların sıralanması - sürekli çağrılır
            }

            return gidilecekYol;
        }

        // Öncelik Sırasına göre sıralama
        public ArrayList<Node> sortQue(ArrayList<Node> dugumListe) {
            int sira = 0;
            while(sira < dugumListe.size()) {
                int yeniSira = sira;
                // Mevcut düğümden sonraki düğümleri kontrol eder
                for(int index = sira + 1; index < dugumListe.size(); index++) {
                    if(dugumListe.get(index).getHedefUzaklik() + dugumListe.get(index).getHops()
                            <
                            dugumListe.get(yeniSira).getHedefUzaklik() + dugumListe.get(yeniSira).getHops())
                        yeniSira = index;
                }
                // Mesafeye göre düğümlerin yerinin listedeki değiştirilmesi
                if(sira != yeniSira) {
                    Node temp = dugumListe.get(sira);
                    dugumListe.set(sira, dugumListe.get(yeniSira));
                    dugumListe.set(yeniSira, temp);
                }
                sira++;
            }
            return dugumListe;
        }

        // Komşu Düğümlerin kontrolü
        public ArrayList<Node> komsuDugumleriArastir(Node current, int hops) {
            ArrayList<Node> explored = new ArrayList<>();	// kesfedilmiş düğümleri tutan arraylist
            for(int sutun = -1; sutun <= 1; sutun++) {
                for(int satir = -1; satir <= 1; satir++) {

                    // Sol Düğümü Kontrol etme metodu
                    if (sutun == -1 && satir == 0) {
                        int xbound = current.getXCord() + sutun;
                        int ybound = current.getYCord() + satir;
                        // Düğüm harita sınırları içerisinde mi kontrol eder
                        if((xbound > -1 && xbound < haritaBoyut) && (ybound > -1 && ybound < haritaBoyut)) {
                            Node neighbor = map[xbound][ybound];
                            // Kontrol edilecek düğüm daha önce kontrol edilmediyse ve engel değilse çalışan kısım
                            if((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getKonumDurum() != 2) {
                                arastir(neighbor, current.getXCord(), current.getYCord(), hops);
                                explored.add(neighbor);	// Düğümü keşfedilmiş düğümleri tutan arrayliste ekler
                            }
                        }
                    }
                    // Üst Düğümü Kontrol etme metodu
                    else if (sutun == 0 && satir == -1) {
                        int xbound = current.getXCord() + sutun;
                        int ybound = current.getYCord() + satir;
                        // Düğüm harita sınırları içerisinde mi kontrol eder
                        if((xbound > -1 && xbound < haritaBoyut) && (ybound > -1 && ybound < haritaBoyut)) {
                            Node neighbor = map[xbound][ybound];
                            // Kontrol edilecek düğüm daha önce kontrol edilmediyse ve engel değilse çalışan kısım
                            if((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getKonumDurum() != 2) {
                                arastir(neighbor, current.getXCord(), current.getYCord(), hops);
                                explored.add(neighbor);	// Düğümü keşfedilmiş düğümleri tutan arrayliste ekler
                            }
                        }
                    }
                    // Alt Düğümü Kontrol etme metodu
                    else if (sutun == 0 && satir == 1) {
                        int xbound = current.getXCord() + sutun;
                        int ybound = current.getYCord() + satir;
                        // Düğüm harita sınırları içerisinde mi kontrol eder
                        if((xbound > -1 && xbound < haritaBoyut) && (ybound > -1 && ybound < haritaBoyut)) {
                            Node neighbor = map[xbound][ybound];
                            // Kontrol edilecek düğüm daha önce kontrol edilmediyse ve engel değilse çalışan kısım
                            if((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getKonumDurum() != 2) {
                                arastir(neighbor, current.getXCord(), current.getYCord(), hops);
                                explored.add(neighbor);	// Düğümü keşfedilmiş düğümleri tutan arrayliste ekler
                            }
                        }
                    }
                    // Sağ Düğümü Kontrol etme metodu
                    else if (sutun == 1 && satir == 0) {
                        int xbound = current.getXCord() + sutun;
                        int ybound = current.getYCord() + satir;
                        // Düğüm harita sınırları içerisinde mi kontrol eder
                        if((xbound > -1 && xbound < haritaBoyut) && (ybound > -1 && ybound < haritaBoyut)) {
                            Node neighbor = map[xbound][ybound];

                            // Kontrol edilecek düğüm daha önce kontrol edilmediyse ve engel değilse çalışan kısım
                            if((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getKonumDurum() != 2) {
                                arastir(neighbor, current.getXCord(), current.getYCord(), hops);
                                explored.add(neighbor);	// Düğümü keşfedilmiş düğümleri tutan arrayliste ekler
                            }
                        }
                    }

                }
            }
            return explored;
        }

        // Araştırma fonksiyonu - Düğümü kontrol eder
        public void arastir(Node simdikiDugum, int lastx, int lasty, int hops) {
            // Düğüm başlangıç ya da hedef mi diye kontrol eder
            if(simdikiDugum.getKonumDurum()!=0 && simdikiDugum.getKonumDurum() != 1)
                simdikiDugum.setKonumDurum(4);	// Düğümü Keşfedilmiş olarak ayarlar
            simdikiDugum.setLastNode(lastx, lasty);	// Bu düğümden önceki düğümün bilgilerini kaydeder
            simdikiDugum.setHops(hops);
            // Eğer Hedefe ulaştıysa en kısa yol bilgilerini döndürür
            if(simdikiDugum.getKonumDurum() == 1) {
                backtrack(simdikiDugum.getLastX(), simdikiDugum.getLastY(),hops);
            }
        }

        // Final Yolunu En sondan başa bulan fonksiyon
        public void backtrack(int lastX, int lastY, int hops) {
            gidilecekYol.add(0, map[hedefX][hedefY]); // Karakterin Gideceği hedefi gidilecek yola ekleme

            // Sondan başa doğru gidilen yolu gidilecekYol arraylistinin sonunaekler
            // Böylece yol doğru sırayla sıralanmış olur
            while(hops > 1) {
                Node current = map[lastX][lastY];
                gidilecekYol.add(0, map[lastX][lastY]);
                current.setKonumDurum(5);
                lastX = current.getLastX();
                lastY = current.getLastY();
                hops--;
            }

            solving = false;
        }
    }

    // Düğüm Sınıfı
    class Node {

        // 0 = başlangıç konumu, 1 = hedef, 2 = engel, 3 = boş, 4 = kontrol edildi, 5 = final yolu
        private int konumDurum;
        private int hops;
        private int xCord;
        private int yCord;
        private int lastX;
        private int lastY;
        private double mesafe = 0;

        // Fonksiyonlar
        // 2 nokta arası uzaklık fonksiyonu
        public double getHedefUzaklik() {		// Hedefe olan uzaklığı hesaplar - trigonometri ile
            int uzunluk = Math.abs(xCord - hedefX);
            int yukseklik = Math.abs(yCord - hedefY);
            mesafe = Math.sqrt( (uzunluk*uzunluk) + (yukseklik*yukseklik) );
            return mesafe;
        }


        // Get - Set Metotları
        // Get Metotları
        public int getXCord() {
            return xCord;
        }

        public int getYCord() {
            return yCord;
        }

        public int getLastX() {
            return lastX;
        }

        public int getLastY() {
            return lastY;
        }

        public int getKonumDurum() {
            return konumDurum;
        }

        public int getHops() {
            return hops;
        }


        // Set Metotları
        public void setKonumDurum(int konumDurum) {
            this.konumDurum = konumDurum;
        }

        public void setLastNode(int x, int y) {
            lastX = x; lastY = y;
        }

        public void setHops(int hops) {
            this.hops = hops;
        }

        // Constructor Metodu
        public Node(int konumDurum, int x, int y) {
            this.konumDurum = konumDurum;
            this.xCord = x;
            this.yCord = y;
            hops = -1;
        }

    }
}
