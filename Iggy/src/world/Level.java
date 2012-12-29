/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;
import objects.*;

/**
 *
 * @author Peter
 */
public class Level {
    int[][] walls;
    final int size=64;
    Color front;
    Color side;
    Color back;
    public Level(String level,Player player,LinkedList<GameObject>objects){
        try {
            front=Color.BLACK;
            side=Color.GRAY;
            back=Color.LIGHT_GRAY;
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
            int last=0;
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
                            walls[i][j] = last;       
                            break;
                        case '1':
                            objects.add(new Zombie(new Vector2(i*size+size/2,j*size+size/2)));
                            walls[i][j] = last;
                            break;
                        case '2':
                            objects.add(new Zombie2(new Vector2(i*size+size/2,j*size+size/2)));
                            walls[i][j] = last;
                            System.out.println("Zombie2");
                            break;
                        case '3':
                            objects.add(new FleshZombie(new Vector2(i*size+size/2,j*size+size/2)));
                            walls[i][j]=last;
                            System.out.println("FleshZombie");
                            break;
                        case ' ':
                            walls[i][j] = 0;
                            last=0;
                            break;
                        case '-':
                            walls[i][j] = -1;
                            last=-1;
                            break;
                    }
                }
                j++;
                last=0;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public double width(){
        return walls.length*size;
    }
    public double height(){
        return walls[0].length*size;
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
        else {
            return Math.max(walls[x][y],0);
        }
    }
    public Rect cellBounds(int x,int y){
        return new Rect(x*size,y*size,size,size);
    }

    public void draw(ImageCollection batch, ViewScreen viewscreen,Dimension d){
        int x=(int) -Math.floor((double)viewscreen.GetX()/64);
        int y=(int) -Math.floor((double)viewscreen.GetY()/64);
        int w=(int) Math.ceil((double)d.width/64);
        int h=(int) Math.ceil((double)d.height/64);
        for(int i=x-2; i<x+w; i++){
            for(int j=y-2; j<y+h; j++){
                try{
                if(walls[i][j]==1){
                    if(i==0){
                        drawFront(batch,-1000,j*size,1000+size,size,viewscreen,d);
                    }
                    if(i==walls.length-1){
                        drawFront(batch,i*size,j*size,1000,size,viewscreen,d);
                    }
                    drawFront(batch,i*size,j*size,size,size,viewscreen,d);
                    if(!(walls[i-1][j]==1&&walls[i][j-1]==1&&walls[i+1][j]==1&&walls[i][j+1]==1)){
                        drawCube(batch,i*size,j*size,size,size,viewscreen,d);
                    }
                }
                if(walls[i][j]==-1){
                    drawBack(batch,i*size,j*size,size,size,viewscreen,d);
                }
                }
                catch(Exception e){
                    
                }
            }
        }
        //batch.fillRect(new Vector2(-1000,walls[0].length*size), walls.length*size+2000, 1000, Color.black, 100);
        drawCube(batch,-1000,walls[0].length*size,walls.length*size+2000,1000,viewscreen,d);
    }
    public void drawCube(ImageCollection batch,double X, double Y, double W, double H,ViewScreen vs,Dimension d){
        double depth=1.03;
        double x1;
        double y1;
        for(depth=1.04; depth>=.95; depth-=.01){
            x1=X-(-vs.GetX()+d.getWidth()/2+W/2);
            y1=Y-(-vs.GetY()+d.getHeight()/2+H/2);
            x1*=depth;
            y1*=depth;
            x1+=-vs.GetX()+d.getWidth()/2+W/2;
            y1+=-vs.GetY()+d.getHeight()/2+H/2;
            batch.fillRect(new Vector2(x1,y1), (int)(W*depth)+2, (int)(H*depth)+2, side,(int)(100*depth));
        }
        
    }
    public void drawFront(ImageCollection batch,double X, double Y, double W, double H,ViewScreen vs,Dimension d){
        double depth=1.05;
        double x1;
        double y1;
            x1=X-(-vs.GetX()+d.getWidth()/2+W/2);
            y1=Y-(-vs.GetY()+d.getHeight()/2+H/2);
            x1*=depth;
            y1*=depth;
            x1+=-vs.GetX()+d.getWidth()/2+W/2;
            y1+=-vs.GetY()+d.getHeight()/2+H/2;
            batch.fillRect(new Vector2(x1,y1), (int)(W*depth)+2, (int)(H*depth)+2, front,(int)(100*depth));
    }
    public void drawBack(ImageCollection batch,double X, double Y, double W, double H,ViewScreen vs,Dimension d){
        double depth=.95;
        double x1;
        double y1;
            x1=X-(-vs.GetX()+d.getWidth()/2+W/2);
            y1=Y-(-vs.GetY()+d.getHeight()/2+H/2);
            x1*=depth;
            y1*=depth;
            x1+=-vs.GetX()+d.getWidth()/2+W/2;
            y1+=-vs.GetY()+d.getHeight()/2+H/2;
            batch.fillRect(new Vector2(x1,y1), (int)(W*depth)+2, (int)(H*depth)+2, back,(int)(100*depth));
        
    }
    public void setColors(Color Front, Color Side, Color Back){
        front=Front;
        side=Side;
        back=Back;
    }
}
