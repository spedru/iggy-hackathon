/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import Utilities.Vector2;

/**
 *
 * @author Sam
 */
public abstract class Enemy extends GameObject{
    int health;
    boolean onGround;
    public Enemy(Animation a, Vector2 pos){
        super(a, pos);
        this.health=100;
        this.onGround=false;
    }
    public void attack(Player p){
        if(this.boundingBox.intersects(p.boundingBox)){
            p.setSprite(new Animation("hittest",1, "png"));
        }
    }
    public void create(){
        
    }
    @Override
    public void draw(ImageCollection batch){
        
    }
}
