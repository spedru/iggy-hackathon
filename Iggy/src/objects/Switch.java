/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Vector2;
import java.util.LinkedList;
import world.Level;

/**
 *
 * @author Sam
 */
public class Switch extends GameObject{
    public boolean touched;
    public Switch(Vector2 pos){
        super(new Animation("switch", 1, "png"), pos);
        this.touched=false;
    }
    @Override
    public void create() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        this.updateBoundingBox();
        if(this.boundingBox.intersects(player.boundingBox)){
            this.touched=true;
        }
    }
}
