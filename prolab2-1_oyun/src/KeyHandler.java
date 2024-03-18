import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GuiPanel gp;

    public KeyHandler(GuiPanel gp) {
        this.gp = gp;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Hareket Tuşları
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }

        // Zoom Tuşları
        if(code == KeyEvent.VK_UP && (gp.oyunDurum.equals("olusturma") || gp.oyunDurum.equals("oyun") || gp.oyunDurum.equals("bitis"))){
            gp.zoomInOut(1);
        }
        if(code == KeyEvent.VK_DOWN && (gp.oyunDurum.equals("olusturma") || gp.oyunDurum.equals("oyun") || gp.oyunDurum.equals("bitis")) ){
            gp.zoomInOut(-1);
        }


        // Oyun Haritası Oluşturma Tuşu
        if(code == KeyEvent.VK_ENTER && gp.oyunDurum.equals("giris")){
            gp.oyunDurum = "olusturma";
            gp.fogSize = 9999;
            gp.karakter.baslangicDegerleriOlustur();

        } // Oyun Başlatma Tuşu
        else if(code == KeyEvent.VK_ENTER && gp.oyunDurum.equals("olusturma")){
            gp.oyunDurum = "oyun";
            gp.fogSize = 7;
            gp.karakter.baslangicDegerleriOlustur();

        }
        else if(code == KeyEvent.VK_ENTER && gp.oyunDurum.equals("oyun")){
            gp.oyunDurum = "bitis";
            gp.fogSize = 7;

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

        // Oyun Müziği Açma Kapama Tuşları
        if(code == KeyEvent.VK_P){
            gp.playMusic(0);
        }
        if(code == KeyEvent.VK_O) {
            gp.stopMusic();
        }
    }
}
