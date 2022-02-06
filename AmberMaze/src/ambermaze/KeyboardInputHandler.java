package ambermaze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Shakkael
 */
public class KeyboardInputHandler implements KeyListener{

    public void KeyboardInputHandler(){};
    
    /**
    * W tej tablicy będą się mieścić dwa wektory: x i y.
    * Będą miały wartości -1, 0 albo 1 w zależności od wciśniętych klawiszy
    * Wartość 1 będzie oznaczać poruszanie się do przodu dla osi y/w prawo dla osi x
    * Wartość -1 oznacza poruszanie się do tyłu dla osi y/w lewo dla osi x
    */
    public int[] vectors = {0,0};
    boolean upButton, downButton, leftButton, rightButton;
    boolean escButton;
    boolean debug_HealthDown, debug_EnemyHealthDown, debug_save, debug_saveDelete, debug_Fight;
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        switch(code){
            case KeyEvent.VK_H:
                debug_HealthDown = true;
                break;
            case KeyEvent.VK_N:
                debug_EnemyHealthDown = true;
                break;
            case KeyEvent.VK_O:
                debug_save = true;
                break;
            case KeyEvent.VK_P:
                debug_saveDelete = true;
                break;
            //przycisk escape do wychodzenia do menu i z gry
            case KeyEvent.VK_ESCAPE:
                escButton = true;
                break;
            case KeyEvent.VK_F:
                debug_Fight = true;
                break;
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        switch(code){
            //wcześniej przydawaliśmy wartość true dla wciśniętych przycisków
            //teraz damy wartość false dla puszczonych przycisków
            case KeyEvent.VK_A:
                leftButton = false;
                break;
            case KeyEvent.VK_D:
                rightButton = false;
                break;
            case KeyEvent.VK_W:
                upButton = false;
                break;
            case KeyEvent.VK_S:
                downButton = false;
                break;
            case KeyEvent.VK_H:
                debug_HealthDown = false;
                break;
            case KeyEvent.VK_N:
                debug_EnemyHealthDown = false;
                break;
            case KeyEvent.VK_O:
                debug_save = false;
                break;
            case KeyEvent.VK_P:
                debug_saveDelete = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escButton = false;
                break;
            case KeyEvent.VK_F:
                debug_Fight = false;
                break;
        }
        
    }
}
