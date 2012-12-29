/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Utilities.ImageCollection;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Peter
 */
public class Star {
    double x;
    double y;
    double d;
    public Star(){
        x=Math.random()*10000;
        y=Math.random()*10000;
        d=0.01+Math.random()*0.04;
    }
    public void draw(ImageCollection batch,ViewScreen vs,Dimension screen){
        double a=x+(d)*vs.GetX();
        double b=y+(d)*vs.GetY();
        a%=screen.getWidth();
        a-=vs.GetX();
        b%=screen.getHeight();
        b-=vs.GetY();
        batch.fillRect(new Vector2(a,b),1,1,Color.WHITE,1);
    }
}
