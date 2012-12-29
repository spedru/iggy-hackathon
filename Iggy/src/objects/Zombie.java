/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Rect;
import Utilities.Vector2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import world.Level;

/**
 *
 * @author Sam
 */
public class Zombie extends Enemy{
    private boolean grounded;
    public Zombie(Vector2 pos){
        super(new Animation("testzombie",1,"png"),pos);
        this.health=100;
        this.grounded=false;
    }

    @Override
    public void create() {
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        hit(objects);
        attack(player);
        if(this.position.getX()>player.position.getX()){
            this.position.dX(-2);
        }
        else if(this.position.getX()<player.position.getX()){
            this.position.dX(2);
        }
        position.dY(velocity.getY());
        updateBoundingBox();
        if (level.collide(boundingBox)){
            velocity.setY(0);
        }
        else
             velocity.dY(.2);
        position.dY(velocity.getY());
    }

}


