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
        super(new Animation("testzombie", 1, "png"), pos);
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
        if (distanceToObject(player) < 450) {
            if (this.position.getX() > player.position.getX()) {
                this.position.dX(-4);
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(4);
                }
            } else if (this.position.getX() < player.position.getX()) {
                this.position.dX(4);
                updateBoundingBox();
                if (level.collide(boundingBox)) {
                    this.position.dX(-4);
                }
            }
        }
        if(distanceToObject(player)<50){
            if(this.position.getX()>player.position.getX()){
                this.position.dX(-6);
                updateBoundingBox();
                if(level.collide(this.boundingBox)){
                    this.position.dX(6);
                    this.velocity.setY(-3);
                }
            }
            else if(this.position.getX()<player.position.getX()){
                this.position.dX(6);
                updateBoundingBox();
                if(level.collide(this.boundingBox)){
                    this.position.dX(-6);
                    this.velocity.setY(-3);
                }
            }
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
