package ambermaze;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Shakkael & Panda
 */
public class MainMenu extends JPanel{
    
    // Dodamy tu własną czcionkę, która będzie wyglądać lepiej niż domyślne
    // Dzięki dodaniu czcionki do plików gry, będzie ona działać na każdym komputerze
    Font defFont;
    Toolkit tool = Toolkit.getDefaultToolkit();
    Color bgColor = new Color(120,150,160);
    Image icon =  tool.getImage("src/imgs/Icons/icon.png"), bgPlate = tool.getImage("src/imgs/Menu/menu_plate2.png");
    
    // Będziemy potrzebowali przechowywać położenie obiektów do interakcji z myszką
    // colliders to odpowiednik pythonowego 'słownika' - jest w typie "Map" który umożliwia przechowywanie danych o różnych typach
    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    
    // Przydadzą nam się też wymiary okna
    public int w_w, w_h, scale;
    
    Colliders Start, Options;
    
    public MainMenu(int num1, int num2, int num3, Font font){
        this.w_w = num1;
        this.w_h = num2;
        this.scale = num3;
        this.defFont = font;
        Start = new Colliders((w_w-200)*scale/2, scale*(w_h/2-w_h/12), 200*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "play", defFont);
        Options = new Colliders((w_w-200)*scale/2, scale*(w_h/2+w_h/12), 200*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "options", defFont);

        
    }
    
    // Funkcja draw którą przekażemy do GamePanelu do rysowania widoku
    public void update(double delta){
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
        
        Start.draw(graph2D);
        Start.text = "START";
        Options.draw(graph2D);
        Options.text = "CREDITS";
        addCollisions(Start,Start.getCollisions());
        addCollisions(Options,Options.getCollisions());
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
