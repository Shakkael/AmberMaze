/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ambermaze;

/**
 *
 * @author Shakkael
 */
public class Vector2 {

    float x = 0;
    float y = 0;
    public Vector2(){
        this.x = 0;
        this.y = 0;
    }
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(float[] array) {
        this.x = array[0];
        this.y = array[1];
    }


    public Vector2 normalize() {
    // sets length to 1
    //
    double length = Math.sqrt(x*x + y*y);

    if (length != 0.0) {
        float s = 1.0f / (float)length;
        x = x*s;
        y = y*s;
    }
    Vector2 result;
    result = new Vector2(x, y);

    return result;
}

    public void move(Vector2 direction){
        Vector2 dirNorm = direction.normalize();
        this.x += dirNorm.x;
        this.y += dirNorm.y;
    }

    void multiply(float multi) {
        this.x *= multi;
        this.y *= multi;
    }
    
    public String getVel(){
        return "x: "+x+"/y: "+y;
    }
    
    }