/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iggy;

import Game.Game;
import Utilities.Vector2;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;
import objects.GameObject;
import objects.Player;
import world.Level;

/**
 *
 * @author Peter
 */
public class GameLoop extends Game{
    Level level;
    Player player;
    LinkedList<GameObject>objects;
    @Override
    public void InitializeAndLoad() {
        player=new Player(new Vector2());
        level=new Level("Levels/Level_Wasteland.txt",player);
        objects=new LinkedList<GameObject>();
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
            if(!o.alive)l.remove();
        }
        player.update(level, player, objects);
        player.move(keyboard.isKeyDown('a'), keyboard.isKeyDown('d'));
        if (keyboard.isKeyDown('w')) {
            player.jump();
        } else {
            player.resetJump();
        }
        player.rotateHead(mouse.location(),viewScreen);
        Vector2 vs=player.position.clone();
        vs.subtract(new Vector2(this.getWidth()/2,this.getHeight()/2));
        viewScreen.set(vs);
    }

    @Override
    public void Draw(Graphics grphcs) {
        level.draw(batch);
        player.draw(batch);
        ListIterator l=objects.listIterator();
        while(l.hasNext()){
            GameObject o=(GameObject)l.next();
            o.draw(batch);
        }
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
