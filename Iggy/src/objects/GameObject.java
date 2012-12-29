/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import world.Level;

/**
 *
 * @author Peter
 */
public abstract class GameObject {
    public boolean alive;
    public Animation sprite;
    public Vector2 position;
    public Vector2 velocity;
    int depth;
    public Rect boundingBox;
    public GameObject(Animation Sprite,Vector2 Position){
        sprite=Sprite;
        position=Position.clone();
        velocity=new Vector2();
        depth=10;
        alive=true;
        create();
    }
    public abstract void create();
    public void update(Level level, Player player, LinkedList<GameObject> objects){
        position.add(velocity);
        if(sprite!=null)sprite.update();
        updateBoundingBox();
        step(level,player,objects);
    }
    public abstract void step(Level level, Player player, LinkedList<GameObject> objects);

    public GameObject collide(LinkedList<GameObject> objects){
        ListIterator l=objects.listIterator();
        while(l.hasNext()){
            GameObject g=(GameObject)l.next();
            if(g.boundingBox.intersects(boundingBox)){
                return g;
            }
        }
        return null;
    }
    public Bullet hit(LinkedList<GameObject> objects){
        ListIterator l=objects.listIterator();
        while(l.hasNext()){
            GameObject g=(GameObject)l.next();
            if(g.boundingBox.intersects(boundingBox)){
                if(g instanceof Bullet){
                    return (Bullet)g;
                }
            }
        }
        return null;
    }
    
    public void draw(ImageCollection batch){
        if(sprite!=null)sprite.draw(batch, position, depth);
    }
    public void updateBoundingBox(){
         boundingBox=sprite.boundingBox(position);
    }
    public void setSprite(Animation Sprite){
        sprite=Sprite;
    }
}
