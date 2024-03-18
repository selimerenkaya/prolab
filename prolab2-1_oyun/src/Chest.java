// SANDIK SINIFLARI
public class Chest extends Obstacle implements sabitEngel {
    protected boolean bulunmaDurum = false;

    // Get - Set Metotları
    boolean getBulunmaDurum() {
        return bulunmaDurum;
    }

    void setBulunmaDurum(boolean bulunmaDurum) {
        this.bulunmaDurum = bulunmaDurum;
    }

    // Constructor Metotları
    Chest() {}

    Chest(Location konum) {
        super(konum);

        // Sandıkların Boyutu 2x2 olacak
        setBoyutX(2);
        setBoyutY(2);

    }
}


// ALTIN SANDIK SINIFI
class ChestGold extends Chest {
    public String sinifAd = "Altın Sandık";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    ChestGold() {}

    ChestGold(Location konum) {
        super(konum);
        setImagePath("textures/chest/chestGold.png");
    }
}

// GÜMÜŞ SANDIK SINIFI
class ChestSilver extends Chest {
    public String sinifAd = "Gümüş Sandık";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    ChestSilver() {}

    ChestSilver(Location konum) {
        super(konum);
        setImagePath("textures/chest/chestSilver.png");
    }
}

// ZÜMRÜT SANDIK SINIFI
class ChestEmerald extends Chest {
    public String sinifAd = "Zümrüt Sandık";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    ChestEmerald() {}

    ChestEmerald(Location konum) {
        super(konum);
        setImagePath("textures/chest/chestEmerald.png");
    }
}

// BAKIR SANDIK SINIFI
class ChestCopper extends Chest {
    public String sinifAd = "Bakır Sandık";

    String getSinifAd() { return sinifAd;}

    // Constructor Metotları
    ChestCopper() {}

    ChestCopper(Location konum) {
        super(konum);
        setImagePath("textures/chest/chestCopper.png");
    }
}
