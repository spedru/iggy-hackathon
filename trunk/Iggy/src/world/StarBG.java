/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Peter
 */
public class StarBG {
    Star[] stars;
    public StarBG(){
        stars=new Star[100];
        for(int i=0; i<100; i++){
            stars[i]=new Star();
        }
    }
    public void draw(ImageCollection batch,ViewScreen vs,Dimension d){
        //batch.Draw(gradient,new Vector2(-vs.GetX()+400,level.height()-gradient.getHeight()/2),2);
        //batch.Draw(stars, new Vector2(-vs.GetX()+stars.getWidth()/2,-vs.GetY()+stars.getHeight()/2),1);
        for(int i=0; i<100; i++){
            stars[i].draw(batch, vs,d);
        }
        
    }
}
