/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ambermaze;

import static ambermaze.MazeScene.cropImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Shakkael
 */
public class Enemy extends JPanel {
    
    private Toolkit tool = Toolkit.getDefaultToolkit();
    double hp = 20, defhp, maxhp = 20;
    String name = "Enemy";
    Image splash, splashAttack, splashDead, current;
    Image statBar = tool.getImage("src/imgs/Stats/EmptyBar.png");
    int x, y, width, height, scale, type; // type 0 - fizyczny, 1 - magiczny
    BufferedImage fullHp;
    int dmg = 4;
    
    public Enemy(int x, int y, int width, int height, int scale, int type, int hp, String splasher){

        try {
            this.fullHp = ImageIO.read(new File("src/imgs/Stats/HP_full.png"));
        } catch (IOException ex) {
            Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.type = type;
        this.maxhp = hp;
        this.defhp = hp;
        this.hp = this.maxhp;

        this.splash = tool.getImage("src/imgs/Splashes/"+splasher+"_N.png");
        this.splashAttack = tool.getImage("src/imgs/Splashes/"+splasher+"_A.png");
        this.splashDead = tool.getImage("src/imgs/Splashes/"+splasher+"_D.png");
        this.current = this.splash;
}
    public void respawn(int level){
        if(name.contains("Golem")) this.maxhp = this.defhp + level*3;
        else this.maxhp = this.defhp + level*2;
        
        this.hp = this.maxhp;
        this.dmg += 0.1;
        current = splash;
    }
    
    public void draw(Graphics2D graph2D){
        double hpPer = hp*172/maxhp;
        graph2D.drawImage(current, x, y, width, height, this);
        graph2D.drawImage(statBar, x-width/2, y-20*scale, 180*scale, 20*scale, this);
        Image hpCur = cropImage(fullHp, 0, 0, (int) hpPer, 6);
        graph2D.drawImage(hpCur, (x-width/2+4*scale), y-9*scale, (int) (scale*hpPer), 6*scale, this);
    }
    
    public void hit(int strength, String name){
        int dmg;
        double baseDmg;
        
        if((name.equals("Shakkael") && type == 0) || name.equals("Monica") && type ==1){
            baseDmg = 1.25;
        }else baseDmg = 1;
        Random kosc = new Random();
        double atak = (kosc.nextInt(1, 12) + strength/4) * baseDmg;
        if(strength == 1) atak = 1*baseDmg;
        hp -= atak;
    }
    
}
