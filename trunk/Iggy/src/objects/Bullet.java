/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import Utilities.Vector2;
import java.awt.Color;
import java.util.LinkedList;
import particlefx.ParticleManager;
import world.Level;

/**
 *
 * @author Sam
 */
public class Bullet extends GameObject{
    public int damage;
    public Bullet(Vector2 pos, Vector2 vel){
        super(new Animation("bullet", 1, "png"), pos);
        velocity=vel.clone();
    }
    public Bullet(Vector2 pos, double direction, double speed){
        super(new Animation("bullet", 1, "png"), pos);
        velocity=new Vector2(Math.cos(direction)*speed,-Math.sin(direction)*speed);
    }
    @Override
    public void create() {
        this.damage=5;
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        
    }
    public void checkWalls(Level level, ParticleManager shells){
        if(level.collide(this.boundingBox)){
            this.alive=false;
            shells.addExplosion(position, 5, velocity.length()/4);
        }
    }
    @Override
    public void draw(ImageCollection batch){
         batch.fillOval(position,5,5, Color.gray, depth);
    }
    
}
