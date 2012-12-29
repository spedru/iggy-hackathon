/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iggy;

import Game.Game;
import Utilities.Vector2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import objects.Bullet;
import objects.Enemy;
import objects.GameObject;
import objects.Player;
import particlefx.ParticleManager;
import world.Level;
import world.StarBG;

/**
 *
 * @author Peter
 */
public class GameLoop extends Game{
    Level level;
    Player player;
    StarBG background1;
    ParticleManager shells;
    ParticleManager blood;
    LinkedList<GameObject>objects;
    boolean dvorak;
    @Override
    public void InitializeAndLoad() {
        player=new Player(new Vector2());
        shells=new ParticleManager(1000,100,0.2,0.1,.5,Color.DARK_GRAY);
        blood=new ParticleManager(1000,400,0.2,0,0,Color.RED);
        objects=new LinkedList<GameObject>();
        level=new Level("Levels/Level_Wasteland.txt",player,objects);
        background1=new StarBG();
        this.setBackground(new Color(10,20,30));
        dvorak=false;
    }

    @Override
    public void UnloadContent() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Update() {
        ListIterator l=objects.listIterator();
        while(l.hasNext()){
            GameObject o=(GameObject)l.next();
            o.update(level, player, objects);
            if(o instanceof Enemy){
                Enemy e=(Enemy)o;
                e.enemyUpdate(level,objects,blood);
            }
            if(o instanceof Bullet){
                Bullet b=(Bullet)o;
                b.checkWalls(level, shells);
            }
            if(!o.alive)l.remove();
        }
        player.update(level, player, objects);
        
        player.move(keyboard.isKeyDown('a'), keyboard.isKeyDown('d'));
        if (keyboard.isKeyDown('w')) {
            player.jump();
        } else {
            player.resetJump();
        }
        if(keyboard.isKeyDown('e')){
            player.switchUp();
        }
        else if(keyboard.isKeyDown('q')){
            player.switchDown();
        
        }
        else player.resetSwitch();
        
        if(mouse.isPressed(mouse.LEFT_BUTTON)){
            player.shoot(objects, mouse.location(), viewScreen,shells);
        }
        player.rotateHead(mouse.location(),viewScreen);
        Vector2 vs=player.position.clone();
        vs.subtract(new Vector2(this.getWidth()/2,this.getHeight()/2));
        viewScreen.set(vs);
        shells.update(level);
        blood.update(level);
    }

    @Override
    public void Draw(Graphics grphcs) {
        level.draw(batch,viewScreen,this.getSize());
        player.draw(batch);
        ListIterator l=objects.listIterator();
        while(l.hasNext()){
            GameObject o=(GameObject)l.next();
            o.draw(batch);
        }
        shells.draw(batch);
        blood.draw(batch);
        background1.draw(batch, viewScreen,this.getSize());
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
