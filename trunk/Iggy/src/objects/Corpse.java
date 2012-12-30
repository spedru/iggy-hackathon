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
 * @author Peter
 */
public class Corpse extends GameObject{
    public Corpse(Animation sprite,Vector2 pos){
        super(sprite,pos);
    }
    @Override
    public void create() {
        
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        position.dY(1);
        updateBoundingBox();
        if (level.collide(boundingBox)){
            velocity.setY(0);
        }
        else
             velocity.dY(.2);
        position.dY(-1);
    }
    
}
