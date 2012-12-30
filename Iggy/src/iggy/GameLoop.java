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
    ParticleManager shells;
    ParticleManager debris;
    ParticleManager blood;
    MIDIPlayer bgm;
    LinkedList<GameObject> objects;
    int state;
    boolean dvorak;
    boolean canPause;
    int wheelRot;

    @Override
    public void InitializeAndLoad() {
        player = new Player(new Vector2(32, 32));
        shells = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.yellow);
        debris = new ParticleManager(1000, 400, 0.2, 0.1, .5, Color.DARK_GRAY);
        blood = new ParticleManager(1000, 1600, 0.2, 0, 0, Color.RED);
        objects = new LinkedList<GameObject>();
        level = new Level("Levels/Level_Caves.txt", player, objects);
        level.setColors(new Color(40, 20, 0), new Color(20, 10, 0), new Color(30, 15, 0));
        background1 = new StarBG();
        this.setBackground(new Color(10, 20, 30));
        dvorak = false;
        state = GAME;
        canPause=true;
        bgm=new MIDIPlayer();
    }

    @Override
    public void UnloadContent() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Update() {
        if (state == GAME) {
            try {
                if(!bgm.playing()){
                    bgm.loopSong("bgm.mid");
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
                        l.remove();
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
        }
    }

    @Override
    public void Draw(Graphics grphcs) {
        if (state == GAME||state==PAUSED) {
            level.draw(batch, viewScreen, this.getSize());
            player.draw(batch);
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
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    public static final int MENU = 0;
    public static final int GAME = 1;
    public static final int GAMEEND = 2;
    public static final int PAUSED = 3;
}
