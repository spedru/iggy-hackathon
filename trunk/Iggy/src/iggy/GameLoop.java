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
import objects.Animation;
import objects.Bullet;
import objects.Corpse;
import objects.Enemy;
import objects.GameObject;
import objects.Player;
import particlefx.ParticleManager;
import world.Cutscene;
import world.Level;
import world.MIDIPlayer;
import world.StarBG;

/**
 *
 * @author Peter
 */
public class GameLoop extends Game {

    Level level;
    Player player;
    StarBG background1;
    Cutscene cutscene;
    ParticleManager shells;
    ParticleManager debris;
    ParticleManager blood;
    MIDIPlayer bgm;
    LinkedList<GameObject> objects;
    Animation titleScreen;
    int state;
    boolean dvorak;
    boolean canPause;
    int wheelRot;
    int currentLevel;
    int timer;
    @Override
    public void InitializeAndLoad() {
        player = new Player(new Vector2(32, 32));
        shells = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.yellow);
        debris = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.DARK_GRAY);
        blood = new ParticleManager(1000, 1600, 0.2, 0, 0, Color.RED);
        objects = new LinkedList<GameObject>();
        level = new Level("Levels/Level1.txt", player, objects);
        currentLevel=1;
        background1 = new StarBG();
        this.setBackground(new Color(30, 10, 20));
        dvorak = false;
        state = MENU;
        canPause=true;
        bgm=new MIDIPlayer();
        level.setColors(currentLevel);
        cutscene=new Cutscene(currentLevel);
        titleScreen=new Animation("title",1,"png");
    }

    @Override
    public void UnloadContent() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Update() {
        if (state == GAME) {
            try {
                timer=0;
                if(!bgm.playing()){
                    bgm.loopSong("requiem.mid");
                }
                ListIterator l = objects.listIterator();
                while (l.hasNext()) {
                    GameObject o = (GameObject) l.next();
                    o.update(level, player, objects);
                    if (o instanceof Enemy) {
                        Enemy e = (Enemy) o;
                        e.enemyUpdate(level, objects, blood);
                    }
                    if (o instanceof Bullet) {
                        Bullet b = (Bullet) o;
                        b.checkWalls(level, debris);
                    }
                    if (!o.alive) {
                        Corpse corpse=null;
                        if(o instanceof Enemy){
                            corpse=new Corpse(new Animation("dicks",1,"png"),o.position);
                        }
                        l.remove();
                        if(corpse!=null){
                            l.add(corpse);
                        }
                    }
                }
                player.update(level, player, objects);
                if (player.health <= 0) {
                    state = GAMEEND;
                }
                player.move(keyboard.isKeyDown('a'), keyboard.isKeyDown('d'));
                if (keyboard.isKeyDown('w')) {
                    player.jump();
                } else {
                    player.resetJump();
                }
                if (keyboard.isKeyDown('e') || mouse.isPressed(mouse.RIGHT_BUTTON)) {
                    player.switchUp();
                } else if (keyboard.isKeyDown('q')) {
                    player.switchDown();

                } else {
                    player.resetSwitch();
                }
                if(keyboard.isKeyDown('1')&&player.weapons[0])player.currentweapon=0;
                if(keyboard.isKeyDown('2')&&player.weapons[1])player.currentweapon=1;
                if(keyboard.isKeyDown('3')&&player.weapons[2])player.currentweapon=2;
                if(keyboard.isKeyDown('4')&&player.weapons[3])player.currentweapon=3;
                if(keyboard.isKeyDown('5')&&player.weapons[4])player.currentweapon=4;
                if(player.position.getX()>=level.width()-64){
                    objects=new LinkedList<GameObject>();
                    shells.clearAll();
                    debris.clearAll();
                    blood.clearAll();
                    currentLevel++;
                    level=new Level("Levels/Level"+currentLevel+".txt", player, objects);
                    level.setColors(currentLevel);
                    cutscene=new Cutscene(currentLevel);
                    state=CUTSCENE;
                }
                if(keyboard.isKeyDown('p')){
                    if(canPause)state=PAUSED;
                    canPause=false;
                }
                else canPause=true;
                wheelRot = mouse.getWheelScroll();
                if (mouse.isPressed(mouse.LEFT_BUTTON)) {
                    player.shoot(objects, mouse.location(), viewScreen, shells);
                }
                player.rotateHead(mouse.location(), viewScreen);
                Vector2 vs = player.position.clone();
                vs.subtract(new Vector2(this.getWidth() / 2, this.getHeight() / 2));
                vs.dY(-100);
                viewScreen.set(vs);
                shells.update(level);
                debris.update(level);
                blood.update(level);
                level.resetMap();
            } catch (Exception e) {
                //Well, fuck.
            }
        }
        if(state==PAUSED){
            if(keyboard.isKeyDown('p')){
                if(canPause)state=GAME;
                canPause=false;
            }
            else canPause=true;
        }
        if(state==GAMEEND){
            bgm.stopSong();
            timer++;
            if(timer>500)state=MENU;
            
        }
        if(state==CUTSCENE){
            if(cutscene.finished()){
                state=GAME;
            }
            if(keyboard.isKeyDown(KeyEvent.VK_ENTER)||keyboard.isKeyDown(KeyEvent.VK_SPACE)){
                cutscene.advance();
            }
            else{
                cutscene.resetAdvance();
            }
        }
        if(state==MENU){
            viewScreen.set(new Vector2());
            titleScreen.update();
            if(keyboard.isKeyDown(KeyEvent.VK_ENTER)||keyboard.isKeyDown(KeyEvent.VK_SPACE)){
                player = new Player(new Vector2(32, 32));
                shells = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.yellow);
                debris = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.DARK_GRAY);
                blood = new ParticleManager(1000, 1600, 0.2, 0, 0, Color.RED);
                objects = new LinkedList<GameObject>();
                level = new Level("Levels/Level1.txt", player, objects);
                currentLevel=1;
                background1 = new StarBG();
                this.setBackground(new Color(30, 10, 20));
                dvorak = false;
                state = CUTSCENE;
                canPause=true;
                level.setColors(currentLevel);
                cutscene=new Cutscene(currentLevel);
            }
        }
    }

    @Override
    public void Draw(Graphics grphcs) {
        if (state == GAME||state==PAUSED||state==CUTSCENE) {
            level.draw(batch, viewScreen, this.getSize());
            player.draw(batch);
            Vector2 v=new Vector2(-viewScreen.GetX()+8,-viewScreen.GetY()+16);
            batch.DrawString(v,"Health: "+Math.round(player.health), Color.white, 250);
            v.dY(16);
            batch.DrawString(v, player.getWeapon(), Color.white, 250);
            v.dY(16);
            batch.DrawString(v,"Ammo: "+player.currentAmmo(), Color.white, 250);
            ListIterator l = objects.listIterator();
            while (l.hasNext()) {
                GameObject o = (GameObject) l.next();
                o.draw(batch);
            }
            shells.draw(batch, viewScreen, this.getSize());
            debris.draw(batch, viewScreen, this.getSize());
            blood.draw(batch, viewScreen, this.getSize());
            background1.draw(batch, viewScreen, this.getSize());
            if(state==GAME)batch.fillRect(new Vector2(-viewScreen.GetX()+5, -viewScreen.GetY()+5), (int) player.health*2, 15, Color.RED, 200);
        }
        if(state==GAMEEND){
            batch.DrawString(new Vector2(-viewScreen.GetX()+20,-viewScreen.GetY()+20), "YOU LOSE", Color.red, 100);
        }
        if(state==PAUSED){
            level.drawMiniMap(batch, player, viewScreen);
        }
        if(state==CUTSCENE){
            cutscene.draw(batch, viewScreen,this.getSize(),level);
        }
        if(state==MENU){
            titleScreen.draw(batch, new Vector2(this.getSize().width/2,this.getSize().height/2), 200);
        }
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    public static final int MENU = 0;
    public static final int GAME = 1;
    public static final int GAMEEND = 2;
    public static final int PAUSED = 3;
    public static final int CUTSCENE=4;
}
