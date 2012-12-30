/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import Utilities.Vector2;
import java.util.LinkedList;
import java.util.ListIterator;
import particlefx.ParticleManager;
import world.Level;

/**
 *
 * @author Sam
 */
public abstract class Enemy extends GameObject {
    int cooldown;
    int health;
    boolean onGround;

    public Enemy(Animation a, Vector2 pos) {
        super(a, pos);
        this.health = 100;
        this.onGround = false;
        cooldown=0;
    }

    public void attack(Player player) {
        cooldown=Math.max(cooldown-1,0);
        if (this.boundingBox.intersects(player.boundingBox)&&cooldown==0) {
            player.health-=20+Math.random()*10;
            cooldown+=30;
            if(this.position.getX()>player.position.getX()){
                player.velocity=new Vector2(-5,-1);
            }
            else{
                player.velocity=new Vector2(5,-1);
            }
        }
    }

    public void enemyUpdate(Level level, LinkedList<GameObject> objects, ParticleManager blood) {
        ListIterator l = objects.listIterator();
        while (l.hasNext()) {
            GameObject o = (GameObject) l.next();
            if (o.alive) {
                if (o instanceof Bullet) {
                    if (o.boundingBox.intersects(boundingBox)) {
                        Bullet b=(Bullet)o;
                        if (o.position.getY() < position.getY() - 5) {
                            
                            health -= 2 * (b).damage;
                            if(b.type==-1){
                                blood.coneExplosion(b.position, b.damage/2, b.direction(), 2, 12);
                            }
                            else {
                                blood.coneExplosion(b.position, b.damage/2, b.direction(), 0.25, 12);
                            }
                        } else {
                            health -= b.damage;
                            blood.coneExplosion(b.position, b.damage/4, b.direction(), 0.5, 10);
                        }
                        b.alive = false;
                        if (health <= 0) {
                            alive = false;
                        }
                    }
                }
                if (o instanceof Enemy) {
                    if (o.boundingBox.intersects(boundingBox)) {
                        if (this.position.getX() < o.position.getX()) {
                            this.position.dX(-2);
                            updateBoundingBox();
                            if (level.collide(boundingBox)) {
                                this.position.dX(2);
                            }
                        } else if (this.position.getX() > o.position.getX()) {
                            this.position.dX(2);
                            updateBoundingBox();
                            if (level.collide(boundingBox)) {
                                this.position.dX(-2);
                            }
                        }
                    }
                }
            }
        }
    }

    public void create() {
    }
}
