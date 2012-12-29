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
    double depth;
    double dspeed;
    boolean falling;
    public Particle(Vector2 Position, Vector2 Velocity,int Life){
        position=Position.clone();
        velocity=Velocity.clone();
        life=Life;
        depth=1;
        dspeed=(Math.random()-0.5)*0.005;
        falling=true;
    }
    public boolean alive(){
        return (life>0);
    }
    public void update(double friction, double gravity){
        depth+=dspeed;
        life--;
        position.add(velocity);
        if(falling)velocity.dY(gravity);
        else velocity.dY(0.05);
        if(depth<0.5)life=-10000;
        if(friction>0){
            double v=velocity.length()-friction;
            if(v>0){
                v/=velocity.length();
                velocity=new Vector2(velocity.getX()*v,velocity.getY()*v);
            }
            else velocity=new Vector2();
            //if(dspeed>0)dspeed=Math.max(0, dspeed-friction/100);
            //if(dspeed<0)dspeed=Math.min(0, dspeed+friction/100);
            
        }
    }
}
