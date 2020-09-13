package uzayoyunu;

import java.awt.HeadlessException;
import javax.swing.JFrame;

public class OyunEkrani extends JFrame{
    
    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    
    public static void main(String[] args) {
        OyunEkrani ekran =new OyunEkrani("Space Game");
        
        ekran.setResizable(false);//pencerenin boyutlanabilir özelliğni false yaptık
        
        ekran.setFocusCycleRoot(false);
        
        ekran.setSize(800,600);//pencerenin boyutunu ayarladık
        
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Kapama butonuna basıldığında pencerenin durumunu belirliyoruz
        
        Oyun oyun=new Oyun();
        
        oyun.requestFocus();//Klavye işlemlerini anlaması için yazılır.
        
        oyun.addKeyListener(oyun);//Klavyeden işlemleri almaya yarıyor.
        
        oyun.setFocusable(true);//Odağı JPanele verdik.
        
        oyun.setFocusTraversalKeysEnabled(false);//Klavye işlemlerini JPanelin anlaması için gereken methot.
        
        ekran.add(oyun);//JPanele JFreme eklemiş olduk.
        
        ekran.setVisible(true);//JFrame JPanele direk olarak eklensin. --Prnceremizi görünür yaptık.
    }

}
