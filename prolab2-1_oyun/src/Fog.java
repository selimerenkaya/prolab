import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

// Karakterin Etrafına sis yaratmayı sağlayan sınıf
public class Fog {
    GuiPanel gp;
    BufferedImage fogFilter;
    int fogRadius;

    // Constructor
    public Fog(GuiPanel gp, int fogRadius) {
        this.gp = gp;
        this.fogRadius = fogRadius;

        fogFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)fogFilter.getGraphics();


        // OYUN EKRANINDAKİ SİS
        if (gp.oyunDurum.equals("oyun")) {
            // Ekran boyutunda bir kare oluşturur
            Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

            // Sisin başlayacağı alanın merkezini alır
            int centerX = gp.karakter.getScreenX() + (gp.tileSize) / 2;
            int centerY = gp.karakter.getScreenY() + (gp.tileSize) / 2;

            // Görebileceğimiz yerin köşelerini alır
            double x = centerX - (double) fogRadius / 2 * gp.tileSize;
            double y = centerY - (double) fogRadius / 2 * gp.tileSize;

            Shape kare = new Rectangle2D.Double(x, y,
                    fogRadius * gp.tileSize, fogRadius * gp.tileSize);
            Area nofogArea = new Area(kare);

            // Görebileceğimiz alanın sisten çıkartılması
            screenArea.subtract(nofogArea);

            // Sise renk verecek rengin değişkenleri
            float redVal = 0.05f;
            float greenVal = 0.05f;
            float blueVal = 0.05f;

            // Yavaştan Sis oluşma efekti
            Color[] color = new Color[5];
            float[] fraction = new float[5];

            color[0] = new Color(redVal, greenVal, blueVal, 0f);
            color[1] = new Color(redVal, greenVal, blueVal, 0.25f);
            color[2] = new Color(redVal, greenVal, blueVal, 0.5f);
            color[3] = new Color(redVal, greenVal, blueVal, 0.75f);
            color[4] = new Color(redVal, greenVal, blueVal, 0.92f);

            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = 1f;

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY,
                    (fogRadius + 2) / 2 * gp.tileSize,
                    fraction, color);


            g2.setPaint(gPaint);
            g2.fill(nofogArea);

            // Karanlık bölgeyi çizer
            g2.fill(screenArea);

            g2.dispose();
        }

        // OYUN BİTİŞ EKRANINDAKİ SİS
        else if (gp.oyunDurum.equals("bitis")) {
            // Ekran boyutunda bir kare oluşturur
            Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

            // Sisin başlayacağı alanın merkezini alır
            int centerX = gp.karakter.getScreenX() + (gp.tileSize) / 2;
            int centerY = gp.karakter.getScreenY() + (gp.tileSize) / 2;

            // Daha önceden gezilmiş karelerin çizilmsi
            for(Location gezilmisKareKonum : gp.karakter.getIlerlenenKonumlar()) {
                Shape gezilmisKare = new Rectangle2D.Double((gezilmisKareKonum.getXCoord() - 3) * gp.tileSize - gp.karakter.getWorldX() + gp.karakter.getScreenX()
                        , (gezilmisKareKonum.getYCoord() - 3) * gp.tileSize - gp.karakter.getWorldY() + gp.karakter.getScreenY(),
                        (fogRadius) * gp.tileSize,
                        (fogRadius) * gp.tileSize);
                Area nofogAreaGezilmis = new Area(gezilmisKare);
                // Görebileceğimiz alanın sisten çıkartılması\n" +
                screenArea.subtract(nofogAreaGezilmis);
            }


            // Sise renk verecek rengin değişkenleri
            float redVal = 0.05f;
            float greenVal = 0.05f;
            float blueVal = 0.05f;

            // Yavaştan Sis oluşma efekti
            Color[] color = new Color[5];
            float[] fraction = new float[5];

            color[0] = new Color(redVal, greenVal, blueVal, 0f);
            color[1] = new Color(redVal, greenVal, blueVal, 0.25f);
            color[2] = new Color(redVal, greenVal, blueVal, 0.5f);
            color[3] = new Color(redVal, greenVal, blueVal, 0.75f);
            color[4] = new Color(redVal, greenVal, blueVal, 0.92f);

            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = 1f;

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY,
                    (fogRadius + 2) / 2 * gp.tileSize,
                    fraction, color);


            // Karanlık bölgeyi çizer
            g2.setPaint(gPaint);
            g2.fill(screenArea);

            g2.dispose();
        }

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(fogFilter, 0, 0, null);

    }

}
