package ambermaze;

/**
 *
 * @author Shakkael
 */


import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class CharacterSelection extends JPanel{

    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    
    String backN = "src/imgs/Buttons/Play_N.png", backH = "src/imgs/Buttons/Play_H.png", backC = "src/imgs/Buttons/Play_C.png";
    
    int w_w, w_h, scale;
    Font defFont;
    Colliders back, monic, casper;
    Toolkit tool = Toolkit.getDefaultToolkit();
    String monicN = "src/imgs/Splashes/Monica_N.png", monicH = "src/imgs/Splashes/Monica_H.png",
            casperN = "src/imgs/Splashes/Casper_N.png", casperH = "src/imgs/Splashes/Casper_H.png";
    
    public CharacterSelection(int width, int height, int scale, Font font){
        this.w_w = width;
        this.w_h = height;
        this.scale = scale;
        this.defFont = font;
        
        back = new Colliders((w_w-200)*scale/2, (40)*scale, 100*2*scale, 20*2*scale, scale, backN, backH, backC, "back", defFont);
        back.text = "POWROT";
        monic = new Colliders(40*scale, (w_h/2-113)*scale, 240*scale, 339*scale, scale, monicN, monicH, monicH, "monic", defFont);
        casper = new Colliders((w_w-266)*scale, (w_h/2-113)*scale, 240*scale, 339*scale, scale, casperN, casperH, casperH, "casper", defFont);
        
    }
    
    public void draw(Graphics2D graph2D){
        
        graph2D.setColor(Color.white);
        back.draw(graph2D);
        monic.draw(graph2D);
        casper.draw(graph2D);
        graph2D.setColor(new Color(30,40,60));
        // Funkcje matematyczne określają położenie i rozmiary elementu na ekranie
        graph2D.fillRect(0, w_h*scale*6/7, w_w*scale, w_h*scale/7);
        graph2D.setColor(new Color(120,130,140));
        graph2D.drawString("Wybierz postac", scale*(2+(w_w-graph2D.getFontMetrics().stringWidth("Wybierz postac")/2)/2), (w_h-26)*scale);
        graph2D.setColor(new Color(180,190,220));
        graph2D.drawString("Wybierz postac", scale*((w_w-graph2D.getFontMetrics().stringWidth("Wybierz postac")/2)/2), (w_h-28)*scale);
        addCollisions(back, back.getCollisions());
        addCollisions(casper, casper.getCollisions());
        addCollisions(monic, monic.getCollisions());
        
    }
    
    public void addCollisions(Colliders object, Rectangle infos){
    // Będziemy tu dodawać obiekty do zbioru obiektów do interakcji z myszką
    // Funkcja zostanie wywołana raz przy tworzeniu się obiektu
        colliders.put(object, infos);
    }
    
    // Funkcja getCollisions przekaże nam obiekty do interakcji z myszką do GamePanelu
    // Funkcja będzie wywoływana wielokrotnie, przy każdym powrocie do MainMenu
    public Map<Colliders, Rectangle> getCollisions(){
        
        return colliders;
    }
   
}
