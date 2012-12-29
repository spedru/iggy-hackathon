/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iggy;

import Game.GameBase;



/**
 *
 * @author Peter
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GameBase(new GameLoop(),"Iggy").Run();
    }

}
