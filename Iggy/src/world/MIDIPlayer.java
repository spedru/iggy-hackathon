/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author Peter
 */
public class MIDIPlayer {
    Sequence song;
    Sequencer sequencer;
    boolean playing;
    public MIDIPlayer(){
        try {
            playing=false;
            sequencer=MidiSystem.getSequencer();
            sequencer.open();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(MIDIPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loopSong(String filename){
        try {
            if(playing)stopSong();
            song= MidiSystem.getSequence(new File("Sounds/"+filename));
            sequencer.setSequence(song);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            playing=true;
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(MIDIPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MIDIPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean playing(){
        return playing;
    }
    public void stopSong(){
        sequencer.setLoopCount(0);
        sequencer.stop();
        playing=false;
    }
}
