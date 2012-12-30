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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Peter
 */
public class Cutscene {
    boolean finished;
    boolean advance;
    Scanner reader;
    String currentText;
    
    public Cutscene(int level){
        try {
            reader=new Scanner(new File("Cutscenes/Cutscene"+level+".txt"));
            finished=false;
            currentText=reader.nextLine();
        } catch (FileNotFoundException ex) {
            finished=true;
        }
    }
    public boolean finished(){
        return finished;
    }
    public void advance(){
        if(advance){
            advance=false;
            if(reader.hasNextLine()){
                currentText=reader.nextLine();
            }
            else{
                finished=true;
            }
        }
    }
    public void resetAdvance(){
        advance=true;
    }
    public void draw(ImageCollection batch,ViewScreen vs,Dimension d,Level level){
        Vector2 v=new Vector2(-vs.GetX()+100,-vs.GetY()+d.height-205);
        int w=d.width-200;
        int h=100;
        batch.fillRoundRect(v, w, h, 10, 10, new Color(8, 46, 3), 200);
        batch.drawRoundRect(v, w, h, 10, 10, level.getSide(), 200);
        v.add(new Vector2(25,25));
        batch.DrawString(v, currentText, Color.WHITE, 210);
    }
}
