package fr.fabou.launchboard;

import javax.sound.midi.MidiUnavailableException;

public class Main {
    public static void main(String[] args) throws MidiUnavailableException {
        System.out.println("main");

        Display_Controller displayer = new Display_Controller();
        displayer.display();

    }
}