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
public class Zombie2 extends Enemy{
    public Zombie2(Vector2 pos){
        super(new Animation("zamby", 7, "png"), pos);
        this.health=100;
        
    }

    @Override
    public void create() {
      //throw new UnsupportedOperationException("Not supported yet.");
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
                this.position.dX(-4);
                if(sprite.sprite!="zambyflip"){
                sprite=new Animation("zambyflip",7,"png");
                }
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(4);
                    if(this.position.getY()>player.position.getY()-16){
                        velocity=new Vector2();
                        this.position.dY(-2);
                        updateBoundingBox();
                        if (level.collide(boundingBox))this.position.dY(2);
                        
                    }
                }
            } else if (this.position.getX() < player.position.getX()-6) {
                this.position.dX(4);
                if(sprite.sprite!="zamby"){
                sprite=new Animation("zamby",7,"png");
                }
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(-4);
                    if(this.position.getY()>player.position.getY()-16){
                        velocity=new Vector2();
                        this.position.dY(-2);
                        updateBoundingBox();
                        if (level.collide(boundingBox))this.position.dY(2);
                    }
                }
            }
        }
        position.dY(1);
        updateBoundingBox();
        if (level.collide(boundingBox)){
            velocity.setY(0);
        }
        else{
            velocity.dY(.2);
            if(this.position.getY()>player.position.getY()||distanceToObject(player)<100){
                if(level.collide(this.boundingBox)){
                velocity.setY(-6);
                }    
            }
        }
        position.dY(-1);
    }
}
