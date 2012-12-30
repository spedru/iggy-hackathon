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
public class AmmoBox extends GameObject{
    public AmmoBox(Vector2 pos){
        super(new Animation("Ammo",1,"png"),pos);
    }
    @Override
    public void create() {
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        if(boundingBox.intersects(player.boundingBox)&&alive){
            for(int i=0; i<player.weapons.length; i++){
                if(player.weapons[i]){
                    player.ammo[i]+=amounts[i];
                }
            }
            alive=false;
        }
    }
    public static final int[] amounts=new int[]{0,40,8,40,5};
    
}
