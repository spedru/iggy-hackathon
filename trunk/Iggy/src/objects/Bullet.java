/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Vector2;
import java.util.LinkedList;
import world.Level;

/**
 *
 * @author Sam
 */
public class Bullet extends GameObject{
    
    public Bullet(Vector2 pos, Vector2 vel){
        super(new Animation("bullet", 1, "png"), pos);
        velocity=vel.clone();
    }
    @Override
    public void create() {
        
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        if(level.collide(this.boundingBox)){
            this.alive=false;
        }
    }
    
}
