package ambermaze;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Shakkael
 */

public class ParticleSystem extends JPanel{
    
    Toolkit tool = Toolkit.getDefaultToolkit();
    AffineTransform ATransform = new AffineTransform();
    
    Random random = new Random();
    
    float initVelocity = 2, gravForce = 1.06f;
    float xRandom = 0, yRandom = 0, xDRandom = 0, yDRandom = 0;
    int[] size = {10,10};
    
    Vector2 initPos = new Vector2(0,0), initDirect = new Vector2(0,-20), gravity = new Vector2(0f,2.5f);
    
    ArrayList<Particle> particles;
    int amount = 90, time = 50, tRandomness = 4;
    boolean infinite = true;
    
    Image texture;
    
    public ParticleSystem(float[] startPos, float[] startDirect){
        this.particles = new ArrayList<Particle>();
        this.initPos = new Vector2(startPos);
        this.initDirect = new Vector2(startDirect);
        
        texture = tool.getImage("src/imgs/Particles/icon.png");
        
        for(int i = 0; i<amount; i++){
            int tRandom = random.nextInt(tRandomness)*5;
            particles.add(new Particle(texture, time, tRandom, initPos, initDirect, initVelocity, gravity, gravForce,
            xRandom, yRandom, xDRandom, yDRandom));
        }
    }    
    public ParticleSystem(float[] startPos, float[] startDirect, float xRandom, float yRandom, float xDRandom, float yDRandom){
        this.particles = new ArrayList<Particle>();
        this.initPos = new Vector2(startPos);
        this.initDirect = new Vector2(startDirect);
        
        texture = tool.getImage("src/imgs/Particles/icon.png");
        
        for(int i = 0; i<amount; i++){
            int tRandom = random.nextInt(tRandomness)*5;
            particles.add(new Particle(new Color(220,40,50), time, tRandom, initPos, initDirect, initVelocity, gravity, gravForce,
            xRandom, yRandom, xDRandom, yDRandom));
        }
    }
    
    public void update(double delta){
        for(Particle entry : particles){
            Particle particle = entry;
            if(particle.time > time){
                if(!infinite){
                    break;
                }
                particle.time = 0;
                particle.reset(0);
            }else{
                particle.update(delta);
                particle.time ++;
            }
        }
    }
    
    public void draw(Graphics2D graph2D){
        for(Particle entry : particles){
            Vector2 position = entry.position;
            Image image = entry.getImage();
            if(image != null){
            graph2D.drawImage(image, (int) position.x, (int) position.y, this);
            }else{
                Color color = entry.getColor();
                graph2D.setColor(color);
                Rectangle particle = new Rectangle((int)position.x, (int) position.y, 8, 8);
                graph2D.fill(particle);
            }
        }
    }
    
}
