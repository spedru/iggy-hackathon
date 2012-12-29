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
public class FleshZombie extends Enemy{
    private int t;
    public FleshZombie(Vector2 pos){
        super(new Animation("fleshzombie", 1, "png"), pos);
        this.health=100;
        this.t=0;
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        t+=1;
        this.attack(player);
        this.updateBoundingBox();
        if(level.collide(this.boundingBox)){
            this.position.subtract(this.velocity);
            this.velocity=new Vector2();
        }
        if((distanceToObject(player) < 450||health<80)){
            if(this.position.getX()>player.position.getX()){
                this.position.dX(-2);
                this.updateBoundingBox();
                if(level.collide(this.boundingBox)){
                    this.position.dX(2);
                }
            }
            else if(this.position.getX()<player.position.getX()){
                this.position.dX(2);
                this.updateBoundingBox();
                if(level.collide(this.boundingBox)){
                    this.position.dX(-2);
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
        if(this.distanceToObject(player)<400&&this.distanceToObject(player)>50){
            if(t>100){
                if(this.position.getX()>player.position.getX()){
                    objects.add(new Flesh(this.position, -8));
                    t=0;
                }
                else if(this.position.getX()<player.position.getX()){
                    objects.add(new Flesh(this.position, 8));
                    t=0;
                }
            }
        }
    }
}
