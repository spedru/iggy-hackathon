/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;
import objects.GameObject;
import objects.Player;
import objects.Zombie;

/**
 *
 * @author Peter
 */
public class Level {
    int[][] walls;
    final int size=64;
    public Level(String level,Player player,LinkedList<GameObject>objects){
        try {
            String line;
            int i = 0;
            int j = 0;
            Scanner reader = new Scanner(new File(level));
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                i = Math.max(i, line.length());
                j++;
            }
            walls = new int[i][j];
            i = 0;
            j = 0;
            reader = new Scanner(new File(level));
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                for (i = 0; i < line.length(); i++) {
                    switch(line.charAt(i)){
                        case 'x':
                            walls[i][j] = 1;
                            break;
                        case '@':
                            player.position=new Vector2(i*size+size/2,j*size+size/2);
                            walls[i][j] = 0;       
                            break;
                        case '1':
                            objects.add(new Zombie(new Vector2(i*64,j*64)));
                            System.out.print("zombie");
                            walls[i][j] = 0;
                            break;
                        case ' ':
                            walls[i][j] = 0;
                            break;
                    }
                }
                j++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public boolean collide(Rect rect){
        int x=(int)Math.floor(rect.UpperLeftCorner().getX()/size);
        int y=(int)Math.floor(rect.UpperLeftCorner().getY()/size);
        boolean hit=false;
        if (getCell(x,y) != 0 && rect.intersects(cellBounds(x, y))) {
            hit = true;
        }
        if (getCell(x+1,y) != 0 && rect.intersects(cellBounds(x + 1, y))) {
            hit = true;
        }
        if (getCell(x,y+1) != 0 && rect.intersects(cellBounds(x, y + 1))) {
            hit = true;
        }
        if (getCell(x+1,y+1) != 0 && rect.intersects(cellBounds(x + 1, y + 1))) {
            hit = true;
        }
        return hit;
    }

    public int getCell(Vector2 Position){
        int x=(int)Math.floor(Position.getX()/size);
        int y=(int)Math.floor(Position.getY()/size);
        return getCell(x,y);
    }
    public int getCell(int x,int y){
        if(x<0||y<0||x>walls.length-1||y>walls[0].length-1){
            return 1;
        }
        else return walls[x][y];
    }
    public Rect cellBounds(int x,int y){
        return new Rect(x*size,y*size,size,size);
    }

    public void draw(ImageCollection batch){
        for(int i=0; i<walls.length; i++){
            for(int j=0; j<walls[0].length; j++){
                if(walls[i][j]==1){
                    //batch.drawRect(cellBounds(i,j), Color.black, 100);
                    if(i==0){
                        batch.fillRect(new Vector2(-1000,j*size),1000+size,size, Color.black, 100);
                    }
                    if(i==walls.length-1){
                        batch.fillRect(new Vector2(i*size,j*size),1000,size, Color.black, 100);
                    }
                    batch.fillRect(new Vector2(i*size,j*size),size,size, Color.black, 100);
                    //System.out.println(i +","+j);
                }
            }
        }
        batch.fillRect(new Vector2(-1000,walls[0].length*size), walls.length*size+2000, 1000, Color.black, 100);
    }
}
