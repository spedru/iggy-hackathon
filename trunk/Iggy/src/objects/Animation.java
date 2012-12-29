/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;

/**
 *
 * @author Peter
 */
public class Animation{

    Image2D[] frames;
    public float index;
    public float speed;
    public String sprite;
    public float direction;
    public float scaleX;
    public float scaleY;
    public Animation(String Name, int Frames, String Type) {
        sprite=Name;
        frames = new Image2D[Frames];
        if (Frames == 1) {
            frames[0]=new Image2D("Sprites/"+Name+"."+Type);
        }
        else {
            for (int i = 0; i < Frames; i++) {
                frames[i] = new Image2D("Sprites/"+Name+(i+1)+"."+Type);
            }
            index = 0;
            speed = 1;
        }
        direction=0;
        scaleX=1;
        scaleY=1;
    }
    public Rect boundingBox(Vector2 position){
        int x=(int)position.getX()-frames[(int)Math.floor(index)].getWidth()/2;
        int y=(int)position.getY()-frames[(int)Math.floor(index)].getHeight()/2;
        return new Rect(x,y,frames[(int)Math.floor(index)].getWidth(),
                frames[(int)Math.floor(index)].getHeight());
    }

    public void update() {
        index = (index + speed) % frames.length;
    }

    public void draw(ImageCollection batch, Vector2 position, int depth) {
        int i=(int) Math.floor(index);
        batch.Draw(frames[i], position, direction,scaleX,scaleY,depth);
    }
    public void rotate(double amount){
        direction=(float) amount;
    }
    public void scale(double x,double y){
        scaleX=(float) x;
        scaleY=(float) y;
    }
}
