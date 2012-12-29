/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import Utilities.ImageCollection;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.util.ArrayList;
import java.util.LinkedList;
import particlefx.ParticleManager;
import world.Level;

/**
 *
 * @author Peter
 */
public class Player extends GameObject{
    int jumps;
    int canshoot=0;
    int currentweapon;
    boolean canJump;
    Animation gun;
    Animation head;
    boolean[] weapons;
    public Player(Vector2 pos){
        super(new Animation("legs",10,"png"),pos);
        gun=new Animation("shotgun_temp",2,"png");
        head=new Animation("head",2,"png");
        currentweapon=3;
        canshoot=0;
        weapons=new boolean[4];
        weapons[0]=true;
        weapons[1]=true;
    }
    @Override
    public void create() {
        jumps=0;
        canJump=false;
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        canshoot=Math.max(0,canshoot-1);
        if(level.collide(boundingBox)){
            position.subtract(velocity);
            position.dX(velocity.getX());
            updateBoundingBox();
            if(level.collide(boundingBox)){
                position.dX(-velocity.getX());
                velocity.setX(0);
            }
            position.dY(velocity.getY());
            updateBoundingBox();
            if(level.collide(boundingBox)){
                position.dY(-velocity.getY());
                velocity.setY(0);
            }
        }
        position.dY(1);
        updateBoundingBox();
        if(level.collide(boundingBox)){
            velocity.setY(0);
            jumps=2;
        }
        else{
            velocity.dY(0.2);
            jumps=Math.min(jumps, 1);
        }
        position.dY(-1);

        if(velocity.getX()==0){
            sprite.index=0;
        }
        else{
            sprite.speed=(float) (velocity.getX() / 16);
        }
    }
    public void jump(){
        if(canJump==true){
            if(jumps>0){
                canJump=false;
                velocity.setY(-6);
                jumps--;
                System.out.println(jumps);
            }
        }
    }
    public void resetJump(){
        canJump=true;
    }
    public void rotateHead(Vector2 mouse,ViewScreen viewscreen){
        Vector2 m=mouse.clone();
        m.dX(-viewscreen.GetX());
        m.dY(-viewscreen.GetY());
        m.subtract(position);
        double dir=Math.atan2(m.getY(),m.getX());
        gun.rotate(dir);
        dir=Math.atan2(m.getY()+16,m.getX());
        head.rotate(dir);
        if(m.getX()>0){
            gun.index=0;
            head.index=0;
        }
        else{
            gun.index=1;
            head.index=1;
        }
    }
    public void move(boolean left,boolean right){
        if(left&&!right){
            velocity.setX(Math.max(velocity.getX()-1, -4));
        }
        else if(right && !left){
            velocity.setX(Math.min(velocity.getX()+1, 4));
        }
        else if(velocity.getX()>0){
            velocity.setX(Math.max(velocity.getX()-1,0));
        }
        else{
            velocity.setX(Math.min(velocity.getX()+1, 0));
        }
    }
    @Override
    public void draw(ImageCollection batch){
        gun.draw(batch, position, 120);
        position.dY(-16);
        head.draw(batch, position, 110);
        position.dY(16);
        sprite.draw(batch, position, depth);
    }
    public void shoot(LinkedList<GameObject> objects,Vector2 mouse,ViewScreen viewscreen,ParticleManager shells){
        if(canshoot==0){
            Vector2 m=mouse.clone();
            m.dX(-viewscreen.GetX());
            m.dY(-viewscreen.GetY());
            m.subtract(position);
            double dir=Math.atan2(-m.getY(),m.getX());
            Vector2 pos=position.clone();
            pos.add(new Vector2(Math.cos(dir)*16,-Math.sin(dir)*16));

            switch(currentweapon){
                case FISTS:
                    break;
                case PISTOL:
                    objects.add(new Bullet(pos,dir+offset()*.1,20));
                    shells.addExplosion(position,1,3);
                    canshoot=20;
                    break;
                case SHOTGUN:
                    canshoot=45;
                    shells.addExplosion(position,1,3);
                    for(int i=0; i<6; i++){
                        objects.add(new Bullet(pos,dir+offset()*.3,20));
                    }
                    break;
                case MACHINEGUN:
                    shells.addExplosion(position,1,4);
                    objects.add(new Bullet(pos,dir+offset()*.1,20));
                    canshoot=7;
                    break;
            }
        }
    }
    public double offset(){
        return Math.random()-0.5;
    }
    public static final int FISTS=0;
    public static final int PISTOL=1;
    public static final int SHOTGUN=2;
    public static final int MACHINEGUN=3;

}
