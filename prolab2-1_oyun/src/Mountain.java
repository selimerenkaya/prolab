// DAĞ SINIFLARI
public class Mountain extends Obstacle implements sabitEngel {

    // Constructor Metotları
    Mountain() {}

    // Rastgele Boyut Seçen Constructor
    Mountain(Location konum) {
        super(konum);

        // Her Zaman 15x15 olurlar
        setBoyutX(15);
        setBoyutY(15);

    }
}


// YAZ SINIFINA AİT DAĞ SINIFI
class MountainSummer extends Mountain {
    public String sinifAd = "Yaz Dağı";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    MountainSummer() {}

    MountainSummer(Location konum) {
        super(konum);
        setImagePath("textures/mountain/summer/mountain.png");
    }

}


// KIŞ SINIFINA AİT DAĞ SINIFI
class MountainWinter extends Mountain {
    public String sinifAd = "Kış Dağı";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    MountainWinter() {}

    MountainWinter(Location konum) {
        super(konum);
        setImagePath("textures/mountain/winter/mountain.png");
    }

}
