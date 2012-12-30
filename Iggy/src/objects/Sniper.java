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
 * @author Peter
 */
public class Sniper extends GameObject{
    public Sniper(Vector2 pos){
        super(new Animation("Ammo",1,"png"),pos);
    }
    @Override
    public void create() {
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        if(boundingBox.intersects(player.boundingBox)&&alive){
            player.weapons[4]=true;
            player.currentweapon=4;
            player.ammo[4]+=5;
            this.alive=false;
        }
    } 
}
