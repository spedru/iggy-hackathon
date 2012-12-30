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
public class Flesh extends Enemy{
    private int d;
    public Flesh(Vector2 pos, int d){
        super(new Animation("Flesh", 1, "png"), pos);
        this.d=d;
        this.velocity.setY(-2);
    }
    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        this.updateBoundingBox();
        this.position.dX(this.d);
        this.velocity.dY(.1);
        if(level.collide(this.boundingBox)){
            objects.remove(this);
        }
        if(this.boundingBox.intersects(player.boundingBox)){
            this.attack(player);
            objects.remove(this);
            if (this.boundingBox.intersects(player.boundingBox)) {
            player.health-=10+Math.random()*5;
            if(this.position.getX()>player.position.getX()){
                player.velocity=new Vector2(-5,-1);
            }
            else{
                player.velocity=new Vector2(5,-1);
            }
        }
        }
    }
}
