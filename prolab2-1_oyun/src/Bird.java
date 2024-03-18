// RESİM EKLENECEK
// KUŞ SINIFLARI
public class Bird extends DynamicObstacle implements dinamikEngel {
    private int ileriMaxKareX = 0;
    private int ileriMaxKareY = 7;
    private int animationNum = 1;
    public String sinifAd = "Kuş";

    int getIleriMaxKareX() {
        return ileriMaxKareX;
    }

    int getIleriMaxKareY() {
        return ileriMaxKareY;
    }

    String getSinifAd() { return sinifAd;}

    @Override
    public void update() {
        if (animationCounter > getGp().FPS / 10 - 1) {
            if (getYon() == 1) {
                setIlerlenenKareY(getIlerlenenKareY() + 1);
            }
            else {
                setIlerlenenKareY(getIlerlenenKareY() - 1);
            }

            if(getIlerlenenKareY() == getIleriMaxKareY()) {
                setYon(0);
            }
            else if(getIlerlenenKareY() == 0) {
                setYon(1);
            }
            animationCounter = 0;

            if( getYon() == 1) {
                if (animationNum == 1) {
                    setImagePath("textures/bird/bird2.png");
                    animationNum = 2;
                } else if (animationNum == 2) {
                    setImagePath("textures/bird/bird3.png");
                    animationNum = 3;
                } else if (animationNum == 3) {
                    setImagePath("textures/bird/bird4.png");
                    animationNum = 4;
                } else if (animationNum == 4) {
                    setImagePath("textures/bird/bird5.png");
                    animationNum = 5;
                } else if (animationNum == 5) {
                    setImagePath("textures/bird/bird6.png");
                    animationNum = 6;
                } else if (animationNum == 6) {
                    setImagePath("textures/bird/bird7.png");
                    animationNum = 7;
                } else if (animationNum == 7) {
                    setImagePath("textures/bird/bird8.png");
                    animationNum = 8;
                } else if (animationNum == 8) {
                    setImagePath("textures/bird/bird1.png");
                    animationNum = 1;
                }
            } else {
                if (animationNum == 1) {
                    setImagePath("textures/bird/birdMirror2.png");
                    animationNum = 2;
                }
                else if (animationNum == 2) {
                    setImagePath("textures/bird/birdMirror3.png");
                    animationNum = 3;
                }
                else if (animationNum == 3) {
                    setImagePath("textures/bird/birdMirror4.png");
                    animationNum = 4;
                }
                else if (animationNum == 4) {
                    setImagePath("textures/bird/birdMirror5.png");
                    animationNum = 5;
                }
                else if (animationNum == 5) {
                    setImagePath("textures/bird/birdMirror6.png");
                    animationNum = 6;
                }
                else if (animationNum == 6) {
                    setImagePath("textures/bird/birdMirror7.png");
                    animationNum = 7;
                }
                else if (animationNum == 7) {
                    setImagePath("textures/bird/birdMirror8.png");
                    animationNum = 8;
                }
                else if (animationNum == 8) {
                    setImagePath("textures/bird/birdMirror1.png");
                    animationNum = 1;
                }

            }

        }
        else {
            animationCounter++;
        }
    }

    // Constructor Metotları
    Bird() {}

    Bird(Location konum) {
        super(konum);
        setImagePath("textures/bird/bird1.png");  // GEÇİCİ RESİM

        // Her Zaman 2x2 olurlar
        setBoyutX(2);
        setBoyutY(2);

    }
}