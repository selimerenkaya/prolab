import java.util.Random;

// KAYA SINIFLARI
public class Rock extends Obstacle implements sabitEngel {
    private int variant;
    private String variantPath;

    // Get-Set Metotları
    // Get Metotları
    int getVariant() {
        return variant;
    }

    String getVariantPath() {
        return variantPath;
    }

    // Set Metotları
    void setVariant(int variant) {
        this.variant = variant;
    }

    void setVariantPath(String variantPath) {
        this.variantPath = variantPath;
    }

    // Constructor Metotları
    Rock() {}

    // Rastgele Boyut Seçen Constructor
    Rock(Location konum) {
        super(konum);

        // Rastgele
        Random randomizer = new Random();
        int size = randomizer.nextInt(2,4);
        setBoyutX(size);
        setBoyutY(size);

        int varyant = randomizer.nextInt(1,8);
        setVariant(varyant);

    }

    // Hazır Boyutlu Constructor
    Rock(Location konum, int boyut) {
        super(konum);
        try {
            // Boyut geçerli aralıklarda mı kontrol eden kısım
            if ( (3 >= boyut) && (boyut >= 2)) {
                setBoyutX(boyut);
                setBoyutY(boyut);

                Random randomizer = new Random();
                int varyant = randomizer.nextInt(1,8);
                setVariant(varyant);

            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Hatalı aralıkta boyut girildi!");
            System.exit(0);
        }
    }

}

// YAZ SINIFINA AİT KAYA SINIFI
class RockSummer extends Rock {
    public String sinifAd = "Yaz Kayası";

    String getSinifAd() { return sinifAd;}

    // Consturctor Metotları
    RockSummer() {}


    // Rastgele Boyut Seçen Constructor
    RockSummer(Location konum) {
        super(konum);

        switch (getVariant()){
            case 1:
                setVariantPath("textures/rock/summer/Model1/Model1_");
                break;
            case 2:
                setVariantPath("textures/rock/summer/Model2/Model2_");
                break;
            case 3:
                setVariantPath("textures/rock/summer/Model3/Model3_");
                break;
            case 4:
                setVariantPath("textures/rock/summer/Model4/Model4_");
                break;
            case 5:
                setVariantPath("textures/rock/summer/Model5/Model5_");
                break;
            case 6:
                setVariantPath("textures/rock/summer/Model6/Model6_");
                break;
            case 7:
                setVariantPath("textures/rock/summer/Model7/Model7_");
                break;
        }

        switch (getBoyutX()) {
            case 2:
                setImagePath(getVariantPath() + "2.png");
                break;

            case 3:
                setImagePath(getVariantPath() + "3.png");
                break;

        }
    }


    // Hazır Boyutlu Constructor
    RockSummer(Location konum, int boyut) {
        super(konum, boyut);

        switch (getVariant()){
            case 1:
                setVariantPath("textures/rock/summer/Model1/Model1_");
                break;
            case 2:
                setVariantPath("textures/rock/summer/Model2/Model2_");
                break;
            case 3:
                setVariantPath("textures/rock/summer/Model3/Model3_");
                break;
            case 4:
                setVariantPath("textures/rock/summer/Model4/Model4_");
                break;
            case 5:
                setVariantPath("textures/rock/summer/Model5/Model5_");
                break;
            case 6:
                setVariantPath("textures/rock/summer/Model6/Model6_");
                break;
            case 7:
                setVariantPath("textures/rock/summer/Model7/Model7_");
                break;
        }

        switch (getBoyutX()) {
            case 2:
                setImagePath(getVariantPath() + "2.png");
                break;

            case 3:
                setImagePath(getVariantPath() + "3.png");
                break;

        }
    }

}


// KIŞ SINIFINA AİT KAYA SINIFI
class RockWinter extends Rock {
    public String sinifAd = "Kış Kayası";

    String getSinifAd() { return sinifAd;}

    // Consturctor Metotları
    RockWinter() {}


    // Rastgele Boyut Seçen Constructor
    RockWinter(Location konum) {
        super(konum);

        switch (getVariant()){
            case 1:
                setVariantPath("textures/rock/winter/Model1/Model1_");
                break;
            case 2:
                setVariantPath("textures/rock/winter/Model2/Model2_");
                break;
            case 3:
                setVariantPath("textures/rock/winter/Model3/Model3_");
                break;
            case 4:
                setVariantPath("textures/rock/winter/Model4/Model4_");
                break;
            case 5:
                setVariantPath("textures/rock/winter/Model5/Model5_");
                break;
            case 6:
                setVariantPath("textures/rock/winter/Model6/Model6_");
                break;
            case 7:
                setVariantPath("textures/rock/winter/Model7/Model7_");
                break;
        }

        switch (getBoyutX()) {
            case 2:
                setImagePath(getVariantPath() + "2.png");
                break;

            case 3:
                setImagePath(getVariantPath() + "3.png");
                break;

        }
    }


    // Hazır Boyutlu Constructor
    RockWinter(Location konum, int boyut) {
        super(konum, boyut);

        switch (getVariant()){
            case 1:
                setVariantPath("textures/rock/winter/Model1/Model1_");
                break;
            case 2:
                setVariantPath("textures/rock/winter/Model2/Model2_");
                break;
            case 3:
                setVariantPath("textures/rock/winter/Model3/Model3_");
                break;
            case 4:
                setVariantPath("textures/rock/winter/Model4/Model4_");
                break;
            case 5:
                setVariantPath("textures/rock/winter/Model5/Model5_");
                break;
            case 6:
                setVariantPath("textures/rock/winter/Model6/Model6_");
                break;
            case 7:
                setVariantPath("textures/rock/winter/Model7/Model7_");
                break;
        }

        switch (getBoyutX()) {
            case 2:
                setImagePath(getVariantPath() + "2.png");
                break;

            case 3:
                setImagePath(getVariantPath() + "3.png");
                break;

        }
    }

}

