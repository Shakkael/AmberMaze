package ambermaze;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Shakkael
 */
public class MouseInputHandler implements MouseListener, MouseMotionListener{
    
    boolean pressedLeft, pressedRight, releasedLeft, releasedRight;
    Point mouseCoor;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /** Lewy przycisk - 1
         * Środkowy przycisk - 2
         * Prawy przycisk - 3
         * Kolejne przyciski (np. boczne) - 4 i w górę
         */
        if(e.getButton() == MouseEvent.BUTTON1) {
            pressedLeft = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            pressedRight = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            releasedLeft = true;
            pressedLeft = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            releasedRight = true;
            pressedRight = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // W razie potrzeby wyczytywania położenia myszki, odkomentujemy ten kod
//        PointerInfo a = MouseInfo.getPointerInfo();
//        Point b = a.getLocation();
//        int x = (int) b.getX();
//        int y = (int) b.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(e.getPoint()!=null){
        mouseCoor = e.getPoint();
        }
    }
    
}
