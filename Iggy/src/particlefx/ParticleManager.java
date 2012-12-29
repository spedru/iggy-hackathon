/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package particlefx;

import Utilities.ImageCollection;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.ListIterator;
import world.Level;

/**
 *
 * @author Peter
 */
public class ParticleManager {
    LinkedList<Particle>particles;
    int maxParticles;
    int life;
    double gravity;
    double friction;
    Color color;
    double bounce;
    public ParticleManager(int MaxParticles,int Life,double Gravity, double Friction,double Bounce,Color Color){
        particles=new LinkedList<Particle>();
        maxParticles=MaxParticles;
        life=Life;
        gravity=Gravity;
        friction=Friction;
        color=Color;
        bounce=Bounce;
    }
    public void update(Level level){
        ListIterator l=particles.listIterator();
        while(l.hasNext()){
            Particle p=(Particle) l.next();
            if(!p.alive()||particles.size()>maxParticles){
                l.remove();
            }
            else{
                p.update(friction, gravity);
                
                if(bounce>=0&& p.depth<=1.04){
                    if(level.getCell(p.position)!=0){
                        p.position.subtract(p.velocity);
                        p.position.dX(p.velocity.getX());
                        if(level.getCell(p.position)!=0){
                            p.position.dX(-p.velocity.getX());
                            
                        }
                        
                        p.position.dY(p.velocity.getY());
                        if(level.getCell(p.position)!=0){
                            p.position.dY(-p.velocity.getY());
                            p.position.setY(64*Math.round(p.position.getY()/64));
                        }
                        
                        p.velocity=new Vector2(p.velocity.getX()*-bounce,p.velocity.getY()*-bounce);
                        p.dspeed*=bounce;
                        if(bounce==0)p.falling=false;
                    }
                }
            }

        }
    }
    public void addParticle(Vector2 position, Vector2 velocity){
        particles.add(new Particle(position,velocity, life));
    }
    public void addParticle(Vector2 position, double direction, double speed){
        addParticle(position,new Vector2(Math.cos(direction)*speed,-Math.sin(direction)*speed));
    }
    public void addExplosion(Vector2 position, int size, double radius){
        for(int i=0; i<size; i++){
            addParticle(position,Math.random()*2*Math.PI,Math.sqrt(Math.random())*radius);
        }
    }
    public void coneExplosion(Vector2 position, int size, double direction, double offset, double radius){
        for(int i=0; i<size; i++){
            addParticle(position,direction+offset()*offset,Math.sqrt(Math.random())*radius);
        }
    }
    public void draw(ImageCollection batch,ViewScreen vs, Dimension d){
        ListIterator l=particles.listIterator();
        while(l.hasNext()){
            Particle p=(Particle) l.next();
            double x1=p.position.getX();
            double y1=p.position.getY();
            x1-=(-vs.GetX()+d.getWidth()/2);
            y1-=(-vs.GetY()+d.getHeight()/2);
            x1*=p.depth;
            y1*=p.depth;
            x1+=-vs.GetX()+d.getWidth()/2;
            y1+=-vs.GetY()+d.getHeight()/2;
            batch.fillRect(new Vector2(x1,y1),2, 2, color, (int)(2+p.depth*100));

        }
    }
    public double offset(){
        return Math.random()-0.5;
    }
}
