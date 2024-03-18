import java.util.Random;

// DUVAR SINIFLARI
public class Wall extends Obstacle implements sabitEngel {
    private int variant;
    public String sinifAd = "Duvar";

    // Get-Set Metotları
    // Get Metotları
    int getVariant() {
        return variant;
    }

    String getSinifAd() { return sinifAd;}

    // Set Metotları
    void setVariant(int variant) {
        this.variant = variant;
    }

    // Constructor Metotları
    Wall() {}

    // Rastgele Boyut Seçen Constructor
    Wall(Location konum) {
        super(konum);

        // Rastgele
        Random randomizer = new Random();
        setVariant(randomizer.nextInt(0,2)); // 1 ise dikey, 0 ise yatay
        if (getVariant() == 1){
            setBoyutX(1);
            setBoyutY(10);

            setImagePath("textures/wall/brickWallDikey.png");
        }
        else {
            setBoyutX(10);
            setBoyutY(1);

            setImagePath("textures/wall/brickWallYatay.png");
        }

    }
}