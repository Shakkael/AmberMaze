package ambermaze;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Shakkael
 */
public class Colliders extends JPanel{
    
    int x=0, y=0, width=0, height=0, scale=0;
    Image normal, hover, click, current;
    // Toolkit ma modyfikator dostępu private, czyli można z niego korzystać tylko wewnątrz obecnej klasy
    private Toolkit tool = Toolkit.getDefaultToolkit();
    boolean clicked = false, disabled;
    public String action = "null";
    public String text;
    Font defFont;
    
    public Colliders(int x, int y, int width, int height, int scale,String normal, String hover, String click, String action, Font font){
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        
        this.normal = tool.getImage(normal);
        this.hover = tool.getImage(hover);
        this.click = tool.getImage(click);
        
        this.current = this.normal;
        this.action = action;
        this.defFont = font;
    }
    public Rectangle getCollisions(){
        Rectangle collision = new Rectangle(x,y,width,height);
        if(disabled) return new Rectangle(-20, -20, 1, 1);
        return collision;
    }
    
    public void draw(Graphics2D graph2D){
        graph2D.drawImage(current, x, y, width, height, this);
        graph2D.setFont(defFont.deriveFont(scale*36f));
        if(text != null){graph2D.drawString(text, x+width/2-graph2D.getFontMetrics().stringWidth(text)/2, y+scale*32);}
    }
    
    public void hover(){
        current = hover;
    }
    public void unhover(){
        current = normal;
        clicked = false;
    }
    public void clicked(){
        current = click;
        clicked = true;
    }
    
}
