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

    int health;
    boolean onGround;

    public Enemy(Animation a, Vector2 pos) {
        super(a, pos);
        this.health = 100;
        this.onGround = false;
    }

    public void attack(Player p) {
        if (this.boundingBox.intersects(p.boundingBox)) {
            //p.setSprite(new Animation("hittest",1, "png"));
        }
    }

    public void enemyUpdate(Level level, LinkedList<GameObject> objects, ParticleManager blood) {
        ListIterator l = objects.listIterator();
        while (l.hasNext()) {
            GameObject o = (GameObject) l.next();
            if (o.alive) {
                if (o instanceof Bullet) {
                    if (o.boundingBox.intersects(boundingBox)) {
                        health -= ((Bullet) o).damage;
                        blood.coneExplosion(o.position, 3, o.direction(), 0.5, 10);
                        o.alive = false;
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
