package ambermaze;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

public class Particle {
    
    Random random = new Random();
    Image texture = null;
    int time;
    Vector2 position = new Vector2(), direction = new Vector2(), gravityDir = new Vector2(0,1);
    Vector2 initPosition = new Vector2(), initDirection = new Vector2(), initGravityDir = new Vector2(0,1);
    float velocity = 10, gravityForce = 1.06f;
    float initVelocity = 10, initGravityForce = 1.06f;
    float xRandom, yRandom, xDRandom, yDRandom;

    Color color = new Color(0,0,0);
    
    Particle(Image texture, int time, int tRandom, Vector2 initPos, Vector2 initDirect, float initVelocity, Vector2 gravityDir, float gravityForce,
            float xRandom, float yRandom, float xDRandom, float yDRandom) {
        this.texture = texture;
        this.time = tRandom;
        
        this.initPosition = initPos;
        this.initDirection = initDirect.normalize();
        this.initVelocity = initVelocity;
        this.initGravityDir = gravityDir.normalize();
        this.initGravityForce = gravityForce;
        this.xRandom = xRandom;
        this.yRandom = yRandom;
        this.xDRandom = xDRandom;
        this.yDRandom = yDRandom;
        
        reset(tRandom);
    }
    Particle(Color color, int time, int tRandom, Vector2 initPos, Vector2 initDirect, float initVelocity, Vector2 gravityDir, float gravityForce,
            float xRandom, float yRandom, float xDRandom, float yDRandom) {
        this.color = color;
        this.time = tRandom;
        
        this.initPosition = initPos;
        this.initDirection = initDirect.normalize();
        this.initVelocity = initVelocity;
        this.initGravityDir = gravityDir.normalize();
        this.initGravityForce = gravityForce;
        this.xRandom = xRandom;
        this.yRandom = yRandom;
        this.xDRandom = xDRandom;
        this.yDRandom = yDRandom;
        
        reset(tRandom);
    }
    
    public Image getImage(){
        if(this.texture != null){
        return this.texture;
        }else{
            return null;
        }
    }
    public Color getColor(){
        return this.color;
    }
    
    public void reset(int time){
        this.texture = texture;
        this.time = time;
        
        Vector2 rpos = new Vector2(random.nextFloat(-xRandom/2, xRandom/2)*5, random.nextFloat(-yRandom/2, yRandom/2)*5);
        this.position = new Vector2(initPosition.x + rpos.x, initPosition.y + rpos.y);
        Vector2 rdir = new Vector2(random.nextFloat(-xDRandom/2, xDRandom/2)*5, random.nextFloat(-yDRandom/2, yDRandom/2)*5);
        this.direction = new Vector2(initDirection.x + rdir.x,initDirection.y + rdir.y).normalize();
        this.velocity = initVelocity;
        this.gravityDir = gravityDir.normalize();
        this.gravityForce = gravityForce;    
    }
    
    public void update(double delta){
        position.x += velocity*direction.x*delta-gravityDir.x*delta;
        position.y += velocity*direction.y*delta-gravityDir.y*delta;
        gravityDir.multiply(gravityForce);
    }
    
}
// Somhehow shape????