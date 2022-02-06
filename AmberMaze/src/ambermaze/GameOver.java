package ambermaze;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Shakkael
 */
public class GameOver extends JPanel {
    
    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    
    Colliders back;
    String backN = "src/imgs/Buttons/Play_N.png", backH = "src/imgs/Buttons/Play_H.png", backC = "src/imgs/Buttons/Play_C.png";
    String level;
    
    public int w_w, w_h, scale;
    Font defFont;
    Toolkit tool = Toolkit.getDefaultToolkit();
    Image icon =  tool.getImage("src/imgs/Icons/icon.png"), bgPlate = tool.getImage("src/imgs/Menu/menu_plate2.png");
    Color bgColor = new Color(120,150,160);
    
    public GameOver(int num1, int num2, int num3, Font font){
        this.w_w = num1;
        this.w_h = num2;
        this.scale = num3;
        this.defFont = font;

        back = new Colliders((w_w-200)*scale/2, scale*(w_h/2-w_h/12), 100*2*scale, 20*2*scale, scale, backN, backH, backC, "back", defFont);
        back.text = "POWROT";
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
    
    public void draw(Graphics2D graph2D){
        graph2D.setColor(bgColor);
        graph2D.drawImage(bgPlate, 0,0 ,640*scale, 480*scale, this);
        
        graph2D.setFont(defFont.deriveFont(scale*48f));
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString("AMBER MAZE", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("AMBER MAZE")/2+4, scale*(w_h/2-60)+4);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString("AMBER MAZE", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("AMBER MAZE")/2, scale*(w_h/2-60));
        graph2D.drawImage(icon, ((w_w/2-48)*scale), scale*(w_h/2-200), 96*scale, 96*scale, this);        
        
        graph2D.setColor( new Color(55,20,15));
        graph2D.drawString("GAME OVER", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("GAME OVER")/2+4, scale*(w_h/2+70)+4);
        graph2D.setColor( new Color(155,50,45));
        graph2D.drawString("GAME OVER", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("GAME OVER")/2, scale*(w_h/2+70));
        graph2D.setColor( new Color(155,60,60));
        graph2D.drawString("Wynik: "+level, scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Wynik: "+level)/2+4, scale*(w_h/2+110)+4);
        graph2D.setColor( new Color(255,129,140));
        graph2D.drawString("Wynik: "+level, scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Wynik: "+level)/2, scale*(w_h/2+110));;
        
        graph2D.setColor(Color.white);
        back.draw(graph2D);
        addCollisions(back, back.getCollisions());
    }
}
