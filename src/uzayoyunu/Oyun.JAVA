package uzayoyunu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {

 private int x;
 private int y;

 public Ates(int x, int y) {
 this.x = x;
 this.y = y;
 }

 public int getX() {
 return x;
 }

 public void setX(int x) {
 this.x = x;
 }

 public int getY() {
 return y;
 }

 public void setY(int y) {
 this.y = y;
 }
 }

 class Meteor {

 private int x;
 private int y;

 public Meteor(int x, int y) {
 this.x = x;
 this.y = y;
 }

 public int getX() {
 return x;
 }

 public void setX(int x) {
 this.x = x;
 }

 public int getY() {
 return y;
 }

 public void setY(int y) {
 this.y = y;
 }

 }
public class Oyun extends JPanel implements KeyListener, ActionListener {
//Klavyede tuşa basıldığı zaman gerekli metotları çalıştırır.İkincisi ise hareket ettirmek için kullanılan interface.

    
    Timer tmr=new Timer(10,this); //İlk değer kaç saniyede çalışacağını gösterir.
    private int gecen_sure = 0;
    private int harcanan_ates = 0;
    Random rndm = new Random();
    int random = rndm.nextInt(800);

    private BufferedImage image;
    private BufferedImage image2;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private ArrayList<Meteor> meteorlar = new ArrayList<Meteor>();

    private int atesdirY = 1;//Atesin hızı

    private int topX = 0;//topun sağa sola gitmesini sağlayacak

    private int topdirX = 1;//topun hızı

    private int uzayGemisiX = 0;

    private int dirUzayX = 20;//uzay gemisinin hızı

    private int meteorX = random;

    private int dirMeteorY = 1;//meteorun hızı

    private int meteorY = 1;

    public boolean kontrolEt() {
        for (Ates ates : atesler) {
            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20))) {
                return true;
            }

        }
        return false;

    }

    public boolean MkontrolEt() {
        for (Ates ates : atesler) {
            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(meteorX, meteorY, 20, 20))) {
                return true;
            }

        }
        return false;

    }

    public Oyun() {
        try {
            image = ImageIO.read(new File("uzaygemisi.png"));
            image2 = ImageIO.read(new File("meteor.png"));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);

        tmr.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);// graphics sınıfına ait g nesnesini kullanmamızı sağlıyor.
        gecen_sure += 1;

        g.setColor(Color.red);

        g.fillOval(topX, 0, 20, 20);//Oval çiziyor yani topu çiziyor

        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);

        g.drawImage(image2, meteorX, meteorY, image2.getWidth() / 5, image2.getHeight() / 4, this);

        for (Ates ates : atesler) {
            if (ates.getY() < 0) {
                atesler.remove(ates);
            }
        }
        g.setColor(Color.GRAY);
        for (Ates ates : atesler) {
            g.fillRect(ates.getX(), ates.getY(), 10, 20);

        }

        for (Meteor meteor : meteorlar) {
            if (meteor.getY() > 600 && meteor.getX() > 800) {
                meteorlar.remove(meteor);
            }

        }

        if (kontrolEt() == true) {
            tmr.stop();
            String message = "Kazandınız..\n"
                    + "Harcanan ates: " + harcanan_ates
                    + "\nGeçen süre: " + gecen_sure/100
                    + " saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
        if (MkontrolEt() == true) {
            
            Random rndm=new Random();
            int random=rndm.nextInt(750);
            meteorX=0;
            meteorX=random;
            meteorY=-1;
            for(Ates ates : atesler){
                atesler.remove(ates);
            }
            
            
        }        
    }

    /*
     public void removeImage(Image img, int id, width w, height h){
         
         image.removeTileObserver(image);
     }*/
    @Override
    public void repaint() {
        super.repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {//Klavyede basılan tuşu göstermeye yarayan metottur.

    }

    @Override
    public void keyPressed(KeyEvent e) {//Klavye hareketleri
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0;
            } else {
                uzayGemisiX -= dirUzayX;
            }
        } else if (c == KeyEvent.VK_RIGHT) {
            if (uzayGemisiX >= 750) {
                uzayGemisiX = 750;
            } else {
                uzayGemisiX += dirUzayX;
            }
        } else if (c == KeyEvent.VK_SPACE) {
            atesler.add(new Ates(uzayGemisiX + 15, 470));
            harcanan_ates++;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//Klavyeden tuşun bırakılmasını kontrol eder.
    }

    @Override
    public void actionPerformed(ActionEvent e) {//Klavyede tuşa basıldığında nesneleri hareket ettirmek için kullanırız.
        for (Ates ates : atesler) {
            ates.setY(ates.getY() - atesdirY);
        }

        topX += topdirX;

        for (Meteor meteor : meteorlar) {
            meteor.setY(meteor.getY() + dirMeteorY);
        }
        meteorY += dirMeteorY;

        if (topX >= 750) {
            topdirX = -topdirX;
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }
        repaint();
    }
}
