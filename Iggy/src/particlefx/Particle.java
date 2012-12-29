/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package particlefx;

import Utilities.Vector2;

/**
 *
 * @author Peter
 */
public class Particle {
    Vector2 position;
    Vector2 velocity;
    int life;
    public Particle(Vector2 Position, Vector2 Velocity,int Life){
        position=Position.clone();
        velocity=Velocity.clone();
        life=Life;
    }
    public boolean alive(){
        return (life>0);
    }
    public void update(double friction, double gravity){
        life--;
        position.add(velocity);
        velocity.dY(gravity);
        if(friction>0){
            double v=velocity.length()-friction;
            if(v>0){
                v/=velocity.length();
                velocity=new Vector2(velocity.getX()*v,velocity.getY()*v);
            }
            else velocity=new Vector2();
        }
    }
}
