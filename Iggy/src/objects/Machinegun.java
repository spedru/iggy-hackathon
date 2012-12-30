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
public class Machinegun extends GameObject{
    public Machinegun(Vector2 pos){
        super(new Animation("Ammo",1,"png"),pos);
    }
    @Override
    public void create() {
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        if(boundingBox.intersects(player.boundingBox)&&alive){
            player.weapons[3]=true;
            player.currentweapon=3;
            player.ammo[3]+=40;
            this.alive=false;
        }
    } 
}
