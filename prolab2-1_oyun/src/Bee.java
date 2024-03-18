// ARI SINIFI
public class Bee extends DynamicObstacle implements dinamikEngel {
    private int ileriMaxKareX = 5;
    private int ileriMaxKareY = 0;
    private int animationNum = 1;
    public String sinifAd = "Arı";

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
            if( getYon() == 1) {
                if (animationNum == 1) {
                    setImagePath("textures/bee/bee1.png");
                    animationNum = 2;
                } else if (animationNum == 2) {
                    setImagePath("textures/bee/bee2.png");
                    animationNum = 3;
                } else if (animationNum == 3) {
                    setImagePath("textures/bee/bee3.png");
                    animationNum = 4;
                } else if (animationNum == 4) {
                    setImagePath("textures/bee/bee4.png");
                    animationNum = 5;
                } else if (animationNum == 5) {
                    setImagePath("textures/bee/bee5.png");
                    animationNum = 6;
                } else if (animationNum == 6) {
                    setImagePath("textures/bee/bee6.png");
                    animationNum = 7;
                } else if (animationNum == 7) {
                    setImagePath("textures/bee/bee7.png");
                    animationNum = 8;
                } else if (animationNum == 8) {
                    setImagePath("textures/bee/bee8.png");
                    animationNum = 9;
                } else if (animationNum == 9) {
                    setImagePath("textures/bee/bee9.png");
                    animationNum = 1;
                }

            } else {
                if (animationNum == 1) {
                    setImagePath("textures/bee/beeMirror1.png");
                    animationNum = 2;
                } else if (animationNum == 2) {
                    setImagePath("textures/bee/beeMirror2.png");
                    animationNum = 3;
                } else if (animationNum == 3) {
                    setImagePath("textures/bee/beeMirror3.png");
                    animationNum = 4;
                } else if (animationNum == 4) {
                    setImagePath("textures/bee/beeMirror4.png");
                    animationNum = 5;
                } else if (animationNum == 5) {
                    setImagePath("textures/bee/beeMirror5.png");
                    animationNum = 6;
                } else if (animationNum == 6) {
                    setImagePath("textures/bee/beeMirror6.png");
                    animationNum = 7;
                } else if (animationNum == 7) {
                    setImagePath("textures/bee/beeMirror7.png");
                    animationNum = 8;
                } else if (animationNum == 8) {
                    setImagePath("textures/bee/beeMirror8.png");
                    animationNum = 9;
                } else if (animationNum == 9) {
                    setImagePath("textures/bee/beeMirror9.png");
                    animationNum = 1;
                }
            }
        }

        if (animationCounter > 6) {
            if (getYon() == 1) {
                setIlerlenenKareX(getIlerlenenKareX() + 1);
            }
            else {
                setIlerlenenKareX(getIlerlenenKareX() - 1);
            }

            if(getIlerlenenKareX() == getIleriMaxKareX()) {
                setYon(0);
            }
            else if(getIlerlenenKareX() == 0) {
                setYon(1);
            }
            animationCounter = 0;

        }
        else {
            animationCounter++;
        }


    }

    // Constructor Metotları
    Bee() {}

    Bee(Location konum) {
        super(konum);
        setImagePath("textures/bee/bee1.png");

        // Her Zaman 2x2 olurlar
        setBoyutX(2);
        setBoyutY(2);

    }
}
