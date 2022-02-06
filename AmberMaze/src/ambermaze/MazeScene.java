package ambermaze;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Shakkael
 */
public class MazeScene extends JPanel {
    
    // Tablice jednowymiarowe określające domyślne statystyki dla postaci
    // Jeśli nie istnieje obecnie zapis gry dla danej postaci, są one jej przypisywane
    int[] MonicDefStats = {8,12,6,10,7,11}; // Żeby 'wyjąć' 12 trzeba użyć MonicDefStats[1]
    int[] CasperDefStats = {12,8,10,6,11,7}; // Żeby 'wyjąć' 12 trzeba użyć CasperDefStats[0]
    // Obie tablice można zapisać w jednej tablicy dwuwymiarowej
    int[][] CharacterDefStats = {{8,12,6,10,7,11},{12,8,10,6,11,7}}; // Żeby 'wyjąć' 10 trzeba użyć CharacterDefStats[0][3] albo CharacterDefStats[1][2]
    
    int level = 0, potions = 4;
    boolean loaded = false, fought = false;
    ArrayList<String> saveLoad = new ArrayList();
    Map<Colliders, Rectangle> colliders = new HashMap<Colliders, Rectangle>();
    Colliders door1, door2, door3;
    Colliders attack, defence;
    Colliders potion1, potion2, potion3, potion4;
    
    Toolkit tool = Toolkit.getDefaultToolkit();
    Image avatarFrame = tool.getImage("src/imgs/Stats/faceFrame.png");
    Image CasperAvatar = tool.getImage("src/imgs/Splashes/Casper_Na_pixel.png");
    Image MonicAvatar = tool.getImage("src/imgs/Splashes/Pixel_Monica.png");
    
    Image conIcon = tool.getImage("src/imgs/Stats/conIcon.png"), comIcon = tool.getImage("src/imgs/Stats/comIcon.png");
    Image strIcon = tool.getImage("src/imgs/Stats/strIcon.png"), wisIcon = tool.getImage("src/imgs/Stats/wisIcon.png");
    Image agiIcon = tool.getImage("src/imgs/Stats/agiIcon.png"), intIcon = tool.getImage("src/imgs/Stats/intIcon.png");
    Image statPoint  = tool.getImage("src/imgs/Stats/Points.png");
    
    Image avatar = MonicAvatar;
    Image statBar = tool.getImage("src/imgs/Stats/EmptyBar.png"), invSlot = tool.getImage("src/imgs/Stats/inventory.png");
    
    Image room = tool.getImage("src/imgs/Rooms/Room1.png");
    
    Enemy slime, phantom, golem, enemy;
    
    // w_w (window width), w_h (widow height) i scale to zmienne o zakresie widoczności w całej klasie i wszystkich jej funkcjach
    int w_w, w_h, scale;
    Font defFont;
    
    // Info o wybranej postaci
    String name = "Casper";
    int maxhp=100, hp=100, maxsanp=100, sanp=100, maxsp=100, sp=100, energy, maxEnergy, maxmp=100, mp=100;
    int con, com, str, wis, agi, intel;
    boolean Monica = false;
    boolean fight, block = false;
    long randSeed, roomSeed;
    
    BufferedImage hpFull, sanpFull, spFull, mpFull;
    
    Random random = new Random();
    
    public MazeScene(int wi, int he, int sc, Font font){
        this.w_w = wi;
        this.w_h = he;
        this.scale = sc;
        this.defFont = font;
        // wi, he, sc ooraz font to zmienne o zakresie widoczności tylko i wyłącznie w obecnej funkcji (MazeScene)
        try {
            this.hpFull = ImageIO.read(new File("src/imgs/Stats/HP_full.png"));
            this.sanpFull = ImageIO.read(new File("src/imgs/Stats/SanP_full.png"));
            this.spFull = ImageIO.read(new File("src/imgs/Stats/SP_full.png"));
            this.mpFull = ImageIO.read(new File("src/imgs/Stats/MP_full.png"));
        } catch (IOException ex) {
            Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        door1 = new Colliders((w_w-600)*scale/2, scale*(w_h/2-w_h/12), 100*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "play", defFont);
        door2 = new Colliders((w_w-100)*scale/2, scale*(w_h/2-w_h/12), 100*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "play", defFont);
        door3 = new Colliders((w_w+400)*scale/2, scale*(w_h/2-w_h/12), 100*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "play", defFont);
        this.attack = new Colliders((w_w+400)*scale/2, scale*(w_h/2-w_h/12), 100*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "hit", defFont);
        this.defence = new Colliders((w_w+400)*scale/2, scale*(w_h/2-w_h/6), 100*scale, 40*scale, scale,"src/imgs/Buttons/Play_N.png","src/imgs/Buttons/Play_H.png","src/imgs/Buttons/Play_C.png", "defence", defFont);

        potion1 = new Colliders((w_w-45-(0))*scale, (w_h-95+(50))*scale, 40*scale, 40*scale, scale,"src/imgs/Items/HPPotion_N.png","src/imgs/Items/HPPotion_H.png","src/imgs/Items/HPPotion_C.png", "potion", defFont);
        potion2 = new Colliders((w_w-45-(50))*scale, (w_h-95+(50))*scale, 40*scale, 40*scale, scale,"src/imgs/Items/HPPotion_N.png","src/imgs/Items/HPPotion_H.png","src/imgs/Items/HPPotion_C.png", "potion", defFont);
        potion3 = new Colliders((w_w-45-(0))*scale, (w_h-95)*scale, 40*scale, 40*scale, scale,"src/imgs/Items/HPPotion_N.png","src/imgs/Items/HPPotion_H.png","src/imgs/Items/HPPotion_C.png", "potion", defFont);
        potion4 = new Colliders((w_w-45-(50))*scale, (w_h-95)*scale, 40*scale, 40*scale, scale,"src/imgs/Items/HPPotion_N.png","src/imgs/Items/HPPotion_H.png","src/imgs/Items/HPPotion_C.png", "potion", defFont);
        
        this.slime = new Enemy(w_w*scale/2-200, scale*(w_h/2 - 100), 200*scale, 200*scale, scale, 0, 20+(level+1)*2, "Cube");
        this.golem = new Enemy(w_w*scale/2-140, scale*(w_h/2 - 100), 100*scale, 140*scale, scale, 0, 20+(level+1)*3, "Golem");
        this.phantom = new Enemy(w_w*scale/2-140, scale*(w_h/2 - 100), 100*scale, 140*scale, scale, 1, 20+(level+1)*2, "Phantom");
        
        this.enemy = this.slime;
    }
    public void StartUpdate(){
        
        int[] DefStats = CharacterDefStats[0];
        
        if(saveLoad.isEmpty()==false){
            saveLoad.clear();
        }
        File save = new File("saves/"+name+".save");
        if(save.exists()){
            try {
                
                System.out.println("Save for char: "+name+" found");
                
                BufferedReader saveFr = new BufferedReader(new FileReader(save));
                String content = saveFr.readLine();
                while(content != null){
                    saveLoad.add(content);
                    content = saveFr.readLine();
                }
                name = saveLoad.get(0);
                level =  Integer.parseInt(saveLoad.get(1));
                con = Integer.parseInt(saveLoad.get(2));
                com = Integer.parseInt(saveLoad.get(3));
                str = Integer.parseInt(saveLoad.get(4));
                wis = Integer.parseInt(saveLoad.get(5));
                agi = Integer.parseInt(saveLoad.get(6));
                intel = Integer.parseInt(saveLoad.get(7));
                maxhp = 24+(con/2);
                hp = Integer.parseInt(saveLoad.get(8));
                maxsp = 24+(agi+str/3);
                sp = Integer.parseInt(saveLoad.get(9));
                maxsanp = 24+(com/2);
                sanp = Integer.parseInt(saveLoad.get(10));
                maxmp = 24+(wis+intel/3);
                mp = Integer.parseInt(saveLoad.get(11));
                potions = Integer.parseInt(saveLoad.get(12));
                randSeed = Integer.parseInt(saveLoad.get(13));
                random.setSeed(randSeed);
                roomSeed = Integer.parseInt(saveLoad.get(14));
                if(name.equals("Casper")){ 
                energy = sp;
                maxEnergy = maxsp;
                }
                else{
                energy = mp;
                maxEnergy = maxmp;
                }
                
                
                saveFr.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        update();}
        else{ if(name.equals("Casper")){
                DefStats = CharacterDefStats[1];
                randSeed = random.nextInt();
                roomSeed = random.nextInt(100);
                con = DefStats[0];
                com = DefStats[1];
                str = DefStats[2];
                wis = DefStats[3];
                agi = DefStats[4];
                intel = DefStats[5];
                maxhp = 24+(con/2);
                hp = maxhp;
                maxsp = 24+(agi+str/3);
                sp = maxsp;
                maxmp = 24+(wis+intel/3);
                mp = maxmp;
                maxsanp = 24+(com/2);
                sanp = maxsanp;
                energy = sp;
                maxEnergy = maxsp;
        }else if(name.equals("Monic")){
                DefStats = CharacterDefStats[0];
                randSeed = random.nextInt();
                roomSeed = random.nextInt(100);
                con = DefStats[0];
                com = DefStats[1];
                str = DefStats[2];
                wis = DefStats[3];
                agi = DefStats[4];
                intel = DefStats[5];
                maxhp = 24+(con/2);
                hp = maxhp;
                maxsp = 24+(agi+str/3);
                sp = maxsp;
                maxmp = 24+(wis+intel/3);
                mp = maxmp;
                maxsanp = 24+(com/2);
                sanp = maxsanp;
                energy = mp;
                maxEnergy = maxmp;
                }
        save();
        StartUpdate();
        }
    }
    public void update(){
        if(name.equals("Casper")){
            maxhp = 24+(con/2);
            maxsp = 24+(agi+str/3);
            maxmp = 24+(wis+intel/3);
            maxsanp = 24+(com/2);
        }else if(name.equals("Monic")){
            maxhp = 24+(con/2);
            maxsp = 24+(agi+str/3);
            maxmp = 24+(wis+intel/3);
            maxsanp = 24+(com/2);
        }
        
    }
    
    public void save(){
            try {
                BufferedWriter write = new BufferedWriter(new FileWriter("saves/"+name+".save", false));
                write.write(name+"\n"+level+"\n"+con+"\n"+com+"\n"+str+"\n"+wis+"\n"+agi+"\n"+intel+"\n"+hp+"\n"+sp+"\n"+sanp+"\n"+mp+"\n"+potions+"\n"+randSeed+"\n"+roomSeed);
                write.close();
            } catch (IOException ex) {
                Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("Saved");
    }
    
    public void deleteSave(){
        try {
            File file = new File("saves/"+name+".save");
            Files.deleteIfExists(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(MazeScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Save Deleted");
    }
    
    public void newRoom(){
        level++;
        roomSeed = random.nextInt(100);
        fought = false;
    }
    
    public void draw(Graphics2D graph2D){
        if(name.equals("Casper")){
            sp = energy;
            maxsp = maxEnergy;
        }
        else{
            mp = energy;
            maxmp = maxEnergy;
        }
        defFont = defFont.deriveFont(scale*20f);
        graph2D.setColor(new Color(110,120,140));
        graph2D.fillRect(0, (w_h-100)*scale, w_w*scale, 100*scale);
        
        int hpPer = hp*172/maxhp, sanpPer = sanp*172/maxsanp, spPer = sp*172/maxsp, mpPer = mp*172/maxmp;
        graph2D.drawImage(avatar, 0, (w_h-100)*scale, 100*scale, 100*scale, this);
        graph2D.drawImage(avatarFrame, 0, (w_h-100)*scale, 100*scale, 100*scale, this);
        graph2D.setFont(defFont);
        graph2D.setColor(new Color(191, 54, 12));
        graph2D.drawString(name, 107*scale , (w_h-82)*scale);
        graph2D.setColor(new Color(255, 179, 0));
        graph2D.drawString(name, 105*scale , (w_h-84)*scale);
        
        graph2D.drawImage(statBar, 100*scale, (w_h-80)*scale, 180*scale, 20*scale, this);
        graph2D.setFont(defFont.deriveFont(scale*10f));
        Image hpCur = cropImage(hpFull, 0, 0, hpPer, 6);
        graph2D.drawImage(hpCur, 104*scale, (w_h-69)*scale, (int) (scale*hpPer), 6*scale, this);
        
        graph2D.drawImage(statBar, 100*scale, (w_h-60)*scale, 180*scale, 20*scale, this);
        Image sanpCur = cropImage(sanpFull, 0, 0, sanpPer, 6);
        graph2D.drawImage(sanpCur, 104*scale, (w_h-49)*scale, (int) (scale*sanpPer), 6*scale, this);
        
        graph2D.drawImage(statBar, 100*scale, (w_h-40)*scale, 180*scale, 20*scale, this);
        Image spCur = cropImage(spFull, 0, 0, spPer, 6);
        graph2D.drawImage(spCur, 104*scale, (w_h-29)*scale, (int) (scale*spPer), 6*scale, this);
        
        graph2D.drawImage(statBar, 100*scale, (w_h-20)*scale, 180*scale, 20*scale, this);
        Image mpCur = cropImage(mpFull, 0, 0, mpPer, 6);
        graph2D.drawImage(mpCur, 104*scale, (w_h-9)*scale, (int) (scale*mpPer), 6*scale, this);
        
        for(int i=0;i<=1;i++){
            for(int j=0; j<=2;j++){    
                graph2D.drawImage(invSlot, (w_w-45-(50*i))*scale, (w_h-95+(50*j))*scale, 40*scale, 40*scale, this);
            }
        }
        
        graph2D.setFont(defFont.deriveFont(scale*15f));
        
        graph2D.drawImage(statPoint, 310*scale, (w_h-75)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(conIcon, 285*scale, (w_h-75)*scale, 20*scale, 20*scale, this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+con, 316*scale, (w_h-59)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+con, 315*scale, (w_h-60)*scale);
        
        graph2D.drawImage(statPoint, 370*scale, (w_h-75)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(comIcon, 345*scale, (w_h-75)*scale, 20*scale, 20*scale, this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+com, 376*scale, (w_h-59)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+com, 375*scale, (w_h-60)*scale);
        
        graph2D.drawImage(statPoint, 310*scale, (w_h-50)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(strIcon, 285*scale, (w_h-50)*scale, 20*scale, 20*scale,  this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+str, 316*scale, (w_h-34)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+str, 315*scale, (w_h-35)*scale);
        
        graph2D.drawImage(statPoint, 370*scale, (w_h-50)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(wisIcon, 345*scale, (w_h-50)*scale, 20*scale, 20*scale,  this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+wis, 376*scale, (w_h-34)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+wis, 375*scale, (w_h-35)*scale);
        
        graph2D.drawImage(statPoint, 310*scale, (w_h-25)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(agiIcon, 285*scale, (w_h-25)*scale, 20*scale, 20*scale,  this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+agi, 316*scale, (w_h-9)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+agi, 315*scale, (w_h-10)*scale);
        
        graph2D.drawImage(statPoint, 370*scale, (w_h-25)*scale, 20*scale, 20*scale, this);
        graph2D.drawImage(intIcon, 345*scale, (w_h-25)*scale, 20*scale, 20*scale,  this);
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+intel, 376*scale, (w_h-9)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+intel, 375*scale, (w_h-10)*scale);
        
        graph2D.setFont(defFont.deriveFont(scale*10f));
        graph2D.setColor(new Color(191, 54, 12));
        graph2D.drawString("Health Points", 105*scale, (w_h-71)*scale);
        graph2D.setColor(new Color(91, 84, 12));
        graph2D.drawString("Sanity Points", 105*scale, (w_h-51)*scale);
        graph2D.setColor(new Color(11, 95, 12));
        graph2D.drawString("Stamina Points", 105*scale, (w_h-31)*scale);
        graph2D.setColor(new Color(11, 54, 192));
        graph2D.drawString("Mana Points", 105*scale, (w_h-11)*scale);
        //NIE SKALOWAĆ W KODZIE. PASKI MUSZĄ MIEĆ DOMYŚLNIE ODPOWIEDNIE ROZMIARY DO CROPOWANIA
        
        //Maze Level
        graph2D.drawImage(room, 0, 0, scale*640, scale*380, this);
        
        graph2D.setFont(defFont.deriveFont(scale*25f));
        graph2D.setColor( new Color(189,78,0));
        graph2D.drawString(""+level, 621*scale, (29)*scale);
        graph2D.setColor( new Color(249,178,0));
        graph2D.drawString(""+level, 620*scale, (30)*scale);        
        
        
        graph2D.setColor(Color.red);
        if(!loaded && !fought && roomSeed>=25 && level > 0){
            if(fight == false){
                    if(random.nextInt(20)<10) enemy = phantom;
                    else if(random.nextInt(20)<15) enemy = slime;
                    else enemy = golem;
            enemy.respawn(level);
            }
            fight = true;
        }else{
            fight = false;
            fought = true;
        }
        
        if(fight == false){
            
        
        if(roomSeed>=29){
        door1.draw(graph2D);
        door1.text = "Door1";
        door1.action = "Door1";
        }
        door2.draw(graph2D);
        door2.text = "Door2";
        door2.action = "Door2";
        if(roomSeed>=85){
        door3.draw(graph2D);
        door3.text = "Door3";
        door3.action = "Door3";
        }
            
        if(roomSeed>=29){
        addCollisions(door1,door1.getCollisions());}
        addCollisions(door2,door2.getCollisions());
        if(roomSeed>=85){
        addCollisions(door3,door3.getCollisions());}
        }else{
            //Enemies there
            enemy.draw(graph2D);
            attack.draw(graph2D);
            attack.text = "Attack";
            attack.action = "attack";
            addCollisions(attack,attack.getCollisions());
            defence.draw(graph2D);
            defence.text = "Block";
            defence.action = "defence";
            addCollisions(defence,defence.getCollisions());
        }
        loaded = false;
        
    if(potions > 0){
        potion1.draw(graph2D);
        potion1.action = "Heal";
        addCollisions(potion1,potion1.getCollisions());
        if(potions > 1){
            potion2.draw(graph2D);
            potion2.action = "Heal";
            addCollisions(potion2,potion2.getCollisions());
            if(potions > 2){
                potion3.draw(graph2D);
                potion3.action = "Heal";
                addCollisions(potion3,potion3.getCollisions());
                if(potions > 3){
                    potion4.draw(graph2D);
                    potion4.action = "Heal";
                    addCollisions(potion4,potion4.getCollisions());
                    }
                }
            }
        }
    }
    
    public static BufferedImage cropImage(BufferedImage bufferedImage, int x, int y, int width, int height){
        BufferedImage originalImage = bufferedImage;
        BufferedImage SubImage = originalImage.getSubimage(x, y, width, height);
        return SubImage;   }

    
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
    
    public void hit(int type, int dmg){
        float multi = 1;
        if((type == 0 && name == "Casper") || type ==  1 && name == "Monica"){
            multi = 1.1f;
        }
        if(block){
            multi *= random.nextFloat(0.5f,0.9f);
            energy += random.nextInt(1,4);
            if(energy >= maxEnergy)energy = maxEnergy;
        }
        hp -= random.nextInt(0,(dmg*(1+level/3))/2)*multi;
    }
    
}
