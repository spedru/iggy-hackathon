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
    public Zombie(Vector2 pos){
        super(new Animation("zamby",7,"png"),pos);
        this.health=100;

    }

    @Override
    public void create() {
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        attack(player);
        updateBoundingBox();
        if(level.collide(boundingBox)){
            position.subtract(velocity);
            velocity=new Vector2();
        }
        if ((distanceToObject(player) < 650||health<80)) {
            if (this.position.getX() > player.position.getX()+6) {
                this.position.dX(-2);
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(2);
                }
                if(sprite.sprite!="zambyflip"){
                sprite=new Animation("zambyflip",7,"png");
                }
            } else if (this.position.getX() < player.position.getX()-6) {
                this.position.dX(2);
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(-2);
                }
                if(sprite.sprite!="zamby"){
                sprite=new Animation("zamby",7,"png");
                }
            }
        }
        else{
            sprite.index=0;
        }
        position.dY(1);
        updateBoundingBox();
        if (level.collide(boundingBox)){
            velocity.setY(0);
        }
        else
             velocity.dY(.2);
        position.dY(-1);
        if(this.position.getY()>player.position.getY()){
            if(level.collide(this.boundingBox)){
                velocity.setY(-6);
            }    
        }
    }
}


