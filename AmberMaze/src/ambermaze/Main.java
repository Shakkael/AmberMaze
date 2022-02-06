package ambermaze;

// Importing modules;
import java.awt.*;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Shakkael & Blue the Panda
 */
public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("Ładowanie elementów gry");
        
        // Tworzenie okienka dla programu
        JFrame window = new JFrame();
        
        // Ustawianie właściwości ekranu
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Amber Maze");
        
        // Tworzymy obiekt ImageIcon, którego użyjemy jako ikonę dla okna
        ImageIcon icon = new ImageIcon("src/imgs/Icons/icon.png");
        
        // Użyjemu modułu toolkit do zmiany kursora myszki
        Toolkit tool = Toolkit.getDefaultToolkit();
        Image img = tool.getImage("src/imgs/Cursors/temp_cursor.png");
        Cursor cursor = tool.createCustomCursor(img, new Point(0,0), "DefaultCursor");
        // Ustawianie kursora i ikony w oknie
        window.setCursor(cursor);
        window.setIconImage(icon.getImage());
        
        // Dodajemy panel gry gdzie będzie się działa cała reszta
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        Scanner answer;
        
        do{
        System.out.println("Jesteśmy gotowi zacząć? [y]");
        answer = new Scanner(System.in);
        } while (answer.nextLine().contentEquals("y") == false);
        System.out.println("Proszę czekać");
        // Korzystanie z funkcji matematycznych
        Random random = new Random();
        float liczbaBezZnaczenia = 0;
        // Pętla for
        for(int x = 0; x <= 10; x++){
            liczbaBezZnaczenia += random.nextInt(100);
            // Konwersja typu z integer do string po rzutowaniu z float do integer
            String text = Integer.toString((int) liczbaBezZnaczenia);
            System.out.println(text);
        }
        System.out.println(0+".00"+(Math.round(Math.pow(5, 2)))+"s");
        window.pack();
        
        // Ustalamy lokalizację uruchamianego programu na erkanie, null -> środek
        window.setLocationRelativeTo(null);
        // Ustawiamy okno jako widoczne
        window.setVisible(true);
        
        // Rozpoczynamy pętlę gry;
        gamePanel.startThread_Game();
        
    }
    
}
