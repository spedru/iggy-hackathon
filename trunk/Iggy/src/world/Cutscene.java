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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void draw(ImageCollection batch,ViewScreen vs,Dimension d){
        Vector2 v=new Vector2(-vs.GetX()+10,-vs.GetY()+d.height-205);
        int w=d.width-20;
        int h=200;
        batch.fillRoundRect(v, w, h, 10, 10, Color.lightGray, 200);
        batch.drawRoundRect(v, w, h, 10, 10, Color.BLACK, 200);
        v.add(new Vector2(25,25));
        batch.DrawString(v, currentText, Color.black, 210);
    }
}
