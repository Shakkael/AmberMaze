/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ambermaze;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Shakkael
 */
class GamePanel extends JPanel implements Runnable{
    
    // Większość zmiannych ma modyfikator dostępu public, część - nie ma zapisanego
    
    // Będziemy potrzebowali przechowywać położenie obiektów do interakcji z myszką
    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    
    /**
     * Ustalimy tutaj najważniejsze dla nas elementy dla okna:
     * FPS - z jaką częstotliwością ma się aktualizować gra
     * width i height - jakie rozmiary ma mieć okno w skali 1
     * scale - w jakiej skali ma się pokazywać okno
     */
    int FPS = 60, width = 640, height = 480, scale = 2;

    Point p = new Point(0,0);
    
    // Tryb debug pozwoli na testowanie gry
    boolean debugMode = true;
    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    // gameScene określa domyślną scenę gry, którą zobaczymy po uruchomieniu okna. 0 - main menu
    public int gameScene = 0;
    
    public MouseInputHandler mouseInput = new MouseInputHandler();
    public KeyboardInputHandler keyInput = new KeyboardInputHandler();
    
    public SoundManager SManager =  new SoundManager();
    
    public MainMenu MMenu;
    public MazeScene MScene;
    public CharacterSelection CSelect;
    public OptionMenu OMenu;
    public GameOver GOver;
    // Thread - po polsku "wątek" - w uproszczeniu: umożliwia działanie kilku funkcji równolegle
    Thread thread_Game;
    Font defFont;
    
    public GamePanel(){
        try{
            InputStream istream = getClass().getResourceAsStream("/fonts/alagard.ttf");
            defFont = Font.createFont(Font.TRUETYPE_FONT, istream).deriveFont(64f);
            } catch(FontFormatException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
        }
        MMenu = new MainMenu(width, height, scale, defFont);
        MScene  = new MazeScene(width, height, scale, defFont);
        CSelect = new CharacterSelection(width, height, scale, defFont);
        OMenu = new OptionMenu(width, height, scale, defFont);
        GOver = new GameOver(width, height, scale, defFont);
                
        this.setPreferredSize(new Dimension(width*scale, height*scale));
        this.setBackground(new Color(10,20,30));
        this.setDoubleBuffered(true);
        //this.addKeyListener(keyInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
        MScene.StartUpdate();
    }
    
    public void startThread_Game(){
        
        thread_Game = new Thread(this);
        thread_Game.start();
        SManager.playSound(SManager.bgMusic);
        
    }
    
    @Override
    public void run() {
        
        // Zaimplementowanie "Runnable" pozwala na tworzenie okienka, które stale się aktualizuje
        // Ale chcemy, żeby aktualizowało się tylko 60 razy na sekundę (60 FPS)
        // Wykorzystamy do tego metodę delty
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawIter = 0;
        // Pętla while
        while(thread_Game != null){
            // Wszystko, co tu jest umieszczone, powtarza się w pętli gry
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            
            lastTime = currentTime;
            
            if(delta >= 1){
            /**
             * Potrzebujemy wywołać tutaj funkcje, które:
             * Aktualizują stan gry
             * Rysują obraz na ekranie
             */
                update(delta);
                repaint();
                delta--;
                drawIter++;
            }
            
        }
        
    }
    
    // Tworzymy dwie funkcje. Pierwszą będzie update do aktualizacji stanu gry
    public void update(double delta){
        
        /** Tutaj program będzie sprawdzał, w jakiej scenie znajduje się gra
        * 0 - Main Menu
        * 1 - Character Selection
        * 2 - Gameplay
        */
        p = mouseInput.mouseCoor;
        if(p==null){p=new Point(0,0);}
        // Switch Expressions
        switch(gameScene){            
            case 0:
                MMenu.update(delta);
                colliders = MMenu.getCollisions();
                for(Map.Entry<Colliders, Rectangle> entry : colliders.entrySet()){
                    
                    if(entry.getValue().contains(p)){
                    }
                    
                    if(!entry.getValue().contains(p)){
                        entry.getKey().unhover();
                    }else if(mouseInput.pressedLeft){
                        entry.getKey().clicked();
                    }else if(entry.getKey().clicked && mouseInput.releasedLeft && entry.getValue().contains(p)){
                        if(entry.getKey().action == "play"){
                            SManager.playSound(SManager.casket);
                            gameScene = 1;
                        }else if(entry.getKey().action == "options"){
                            SManager.playSound(SManager.casket);
                            gameScene = 3;
                        }
                        entry.getKey().clicked = false;
                        }else{
                            entry.getKey().hover();
                        }
                }
                
                break;
                // Operatory logiczne określają która scena gry ma być obecnie przetwarzana
            case 1:
                colliders = CSelect.getCollisions();
                // Pętla for each
                for(Map.Entry<Colliders, Rectangle> entry : colliders.entrySet()){
                    
                    if(entry.getValue().contains(p)){
                    }
                    
                    if(!entry.getValue().contains(p)){
                        entry.getKey().unhover();
                    }else if(mouseInput.pressedLeft){
                        entry.getKey().clicked();
                    }else if(entry.getKey().clicked && mouseInput.releasedLeft && entry.getValue().contains(p)){
                        if(entry.getKey().action == "back"){
                            SManager.playSound(SManager.casket);
                            gameScene = 0;
                        } else if(entry.getKey().action == "monic"){
                            SManager.playSound(SManager.startMonic);
                            MScene.level = 0;
                            MScene.name = "Monic";
                            MScene.avatar = MScene.MonicAvatar;
                            MScene.loaded = true;
                            gameScene = 2;
                        } else if(entry.getKey().action == "casper"){
                            SManager.playSound(SManager.startCasper);
                            MScene.level = 0;
                            MScene.name = "Casper";
                            MScene.avatar = MScene.CasperAvatar;
                            MScene.loaded = true;
                            gameScene = 2;
                        }
                        MScene.StartUpdate();
                        entry.getKey().clicked = false;
                        }else{
                            entry.getKey().hover();
                        }
                }
                
                break;
            case 2:
                colliders = MScene.getCollisions();
                // Pętla for each
                for(Map.Entry<Colliders, Rectangle> entry : colliders.entrySet()){
                    
                    if(entry.getValue().contains(p)){
                    }
                    
                    if(!entry.getValue().contains(p)){
                        entry.getKey().unhover();
                    }else if(mouseInput.pressedLeft){
                        entry.getKey().clicked();
                    }else if(entry.getKey().clicked && mouseInput.releasedLeft && entry.getValue().contains(p)){
                        if(entry.getKey().action == "attack"){
                            SManager.playSound(SManager.hit);
                            if(MScene.energy > 1){
                            MScene.enemy.current = MScene.enemy.splashDead;
                            MScene.enemy.hit(MScene.str, MScene.name);
                            MScene.energy -= new Random().nextInt(2,6);}
                            if(MScene.energy <1) MScene.energy = 1;
                        if(MScene.enemy.hp > 1){
                            MScene.attack.disabled = true;
                            MScene.defence.disabled = true;
                            exec.schedule(new Runnable() {
                            public void run() {
                            MScene.enemy.current = MScene.enemy.splashAttack;
                            MScene.hit(MScene.enemy.type, MScene.enemy.dmg);
                            SManager.playSound(SManager.hit2);
                            exec.schedule(new Runnable() {
                            public void run() {
                            MScene.enemy.current = MScene.enemy.splash;
                            MScene.attack.disabled = false;
                            MScene.defence.disabled = false;
                            }
                       }, 1, TimeUnit.SECONDS);
                            }
                       }, 1, TimeUnit.SECONDS);
                        }
                        }
                        if(entry.getKey().action == "defence"){
                            MScene.block = true;
                        if(MScene.enemy.hp > 1){
                            MScene.attack.disabled = true;
                            MScene.defence.disabled = true;
                            exec.schedule(new Runnable() {
                            public void run() {
                            MScene.hit(MScene.enemy.type, MScene.enemy.dmg);
                            SManager.playSound(SManager.hit2);
                            MScene.attack.disabled = false;
                            MScene.defence.disabled = false;
                            MScene.block = false;
                            }
                       }, 1, TimeUnit.SECONDS);
                        }
                        }
                        else if(entry.getKey().action == "Heal"){
                            MScene.hp = MScene.maxhp;
                            MScene.sanp = MScene.maxsanp;
                            MScene.mp = MScene.maxmp;
                            MScene.sp = MScene.maxsp;
                            MScene.energy = MScene.maxEnergy;
                            MScene.potions--;
                        }
                        else if(entry.getKey().action.startsWith("Door")){
                            SManager.playSound(SManager.casket);
                            MScene.newRoom();
                        }
                        entry.getKey().clicked = false;
                        }else{
                            entry.getKey().hover();
                        }
                }
                MScene.update();
                if(debugMode){
                    if(keyInput.debug_HealthDown && MScene.hp > 1){
                        MScene.hp --;
                    }else if(keyInput.debug_save){
                        MScene.save();
                        keyInput.debug_save = false;
                    }else if(keyInput.debug_saveDelete){
                        MScene.deleteSave();
                        gameScene = 0;
                        keyInput.debug_saveDelete = false;
                    }else if(keyInput.debug_Fight){
                        MScene.fight = !MScene.fight;
                        keyInput.debug_Fight = false;
                    }
                    if(MScene.hp <= 1){
                        MScene.hp = MScene.maxhp;
                        GOver.level = Integer.toString(MScene.level);
                        MScene.deleteSave();
                        gameScene = 4;
                    }
                    if(keyInput.debug_EnemyHealthDown){
                        MScene.enemy.hp = 1;
                        MScene.fight = false;
                        MScene.fought = true;
                    }
                }
                if(MScene.enemy.hp <= 1){
                    MScene.fight = false;
                    MScene.fought = true;
                    MScene.enemy.hp = MScene.enemy.maxhp;
                    MScene.con++;
                    MScene.com++;
                    MScene.agi++;
                    MScene.str++;
                    MScene.agi++;
                    MScene.wis++;
                    MScene.intel++;
                    MScene.potions++;
                    if(MScene.potions > 4) MScene.potions = 4;
                    MScene.update();
                }
                if(keyInput.escButton){
                    if(!MScene.fight){
                        gameScene = 0;
                        MScene.save();
                    }else{
                        System.out.println("Can't leave & save during fight");
                    }
                    keyInput.escButton = false;
                }
                break;
            case 3:
                colliders = OMenu.getCollisions();
                // Pętla for each
                for(Map.Entry<Colliders, Rectangle> entry : colliders.entrySet()){
                    
                    if(entry.getValue().contains(p)){
                    }
                    
                    if(!entry.getValue().contains(p)){
                        entry.getKey().unhover();
                    }else if(mouseInput.pressedLeft){
                        entry.getKey().clicked();
                    }else if(entry.getKey().clicked && mouseInput.releasedLeft && entry.getValue().contains(p)){
                        if(entry.getKey().action == "back"){
                            SManager.playSound(SManager.casket);
                            gameScene = 0;
                        }
                        entry.getKey().clicked = false;
                        }else{
                            entry.getKey().hover();
                        }
                }
                //OMenu.update();
                break;
            case 4:
                //GOver
                colliders = GOver.getCollisions();
                for(Map.Entry<Colliders, Rectangle> entry : colliders.entrySet()){
                    
                    if(entry.getValue().contains(p)){
                    }
                    
                    if(!entry.getValue().contains(p)){
                        entry.getKey().unhover();
                    }else if(mouseInput.pressedLeft){
                        entry.getKey().clicked();
                    }else if(entry.getKey().clicked && mouseInput.releasedLeft && entry.getValue().contains(p)){
                        if(entry.getKey().action == "back"){
                            SManager.playSound(SManager.casket);
                            gameScene = 0;
                        }
                        entry.getKey().clicked = false;
                        }else{
                            entry.getKey().hover();
                        }
                }
                break;
            default:
                gameScene = 0;
        }
        colliders.clear();
    }
    
    //Drugą funkcją paintComponent do rysowania aktualnego widoku na ekranie gry
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        Graphics2D graph2D = (Graphics2D)g;
        
        /** Tutaj program będzie sprawdzał, w jakiej scenie znajduje się gra
        * 0 - Main Menu
        * 1 - Character Selection
        * 2 - Gameplay
        */
        switch(gameScene){
            case 0:
                MMenu.draw(graph2D);
                break;
            case 1:
                CSelect.draw(graph2D);
                break;
            case 2:
                MScene.draw(graph2D);
                break;
            case 3:
                OMenu.draw(graph2D);
                break;
            case 4:
                GOver.draw(graph2D);
                break;
            default:
                break;
        }
        
        // Gdy skończymy rysować widok, trzeba "wyczyścić pamięć" która była używana
        graph2D.dispose();        
    }
    
}
