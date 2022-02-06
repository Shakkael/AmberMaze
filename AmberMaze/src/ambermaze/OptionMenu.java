package ambermaze;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Shakkael
 */
public class OptionMenu extends JPanel {
    
    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    
    Colliders back;
    String backN = "src/imgs/Buttons/Play_N.png", backH = "src/imgs/Buttons/Play_H.png", backC = "src/imgs/Buttons/Play_C.png";
    
    public int w_w, w_h, scale;
    Font defFont;
    Toolkit tool = Toolkit.getDefaultToolkit();
    Image icon =  tool.getImage("src/imgs/Icons/icon.png"), bgPlate = tool.getImage("src/imgs/Menu/menu_plate2.png");
    Color bgColor = new Color(120,150,160);
    
    public OptionMenu(int num1, int num2, int num3, Font font){
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
        
        graph2D.setColor( new Color(60,60,155));
        graph2D.drawString("Monika Telus", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Monika Telus")/2+4, scale*(w_h/2+60)+4);
        graph2D.setColor( new Color(129,129,255));
        graph2D.drawString("Monika Telus", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Monika Telus")/2, scale*(w_h/2+60));
        graph2D.setColor( new Color(155,60,60));
        graph2D.drawString("Kacper Piatek", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Kacper Piatek")/2+4, scale*(w_h/2+110)+4);
        graph2D.setColor( new Color(255,129,140));
        graph2D.drawString("Kacper Piatek", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Kacper Piatek")/2, scale*(w_h/2+110));
        
        graph2D.setColor(bgColor);
        graph2D.fillRect((w_w/10)*scale, scale*(w_h/2+142) , scale*(w_w*8/10), scale*39);
        
        graph2D.setFont(defFont.deriveFont(scale*24f));
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString("Dzwieki:", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Dzwieki:")/2+4, scale*(w_h/2+140)+4);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString("Dzwieki:", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Dzwieki:")/2, scale*(w_h/2+140));
        
        graph2D.setFont(defFont.deriveFont(scale*12f));
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString("Mixkit.co/free-sound-effects", scale*(w_w/4) - graph2D.getFontMetrics().stringWidth("Mixkit.co/free-sound-effects")/2+1, scale*(w_h/2+155)+4);
        graph2D.drawString("SoundBible.com", scale*(w_w*3/4) - graph2D.getFontMetrics().stringWidth("SoundBible.com")/2+1, scale*(w_h/2+155)+4);
        graph2D.drawString("Danny Baranowsky", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Danny Baranowsky")/2+1, scale*(w_h/2+155)+4);
        graph2D.drawString("Szymon Piatek", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Szymon Piatek")/2+1, scale*(w_h/2+172)+4);

        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString("Mixkit.co/free-sound-effects", scale*(w_w/4) - graph2D.getFontMetrics().stringWidth("Mixkit.co/free-sound-effects")/2, scale*(w_h/2+155));
        graph2D.drawString("SoundBible.com", scale*(w_w*3/4) - graph2D.getFontMetrics().stringWidth("SoundBible.com")/2, scale*(w_h/2+155));
        graph2D.drawString("Danny Baranowsky", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Danny Baranowsky")/2+1, scale*(w_h/2+155));
        graph2D.drawString("Szymon Piatek", scale*(w_w/2) - graph2D.getFontMetrics().stringWidth("Szymon Piatek")/2+1, scale*(w_h/2+172));
        
        graph2D.setColor(Color.white);
        back.draw(graph2D);
        addCollisions(back, back.getCollisions());
    }
}
