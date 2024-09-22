package fr.fabou.launchboard;

import javax.sound.midi.*;

import static fr.fabou.launchboard.Main.displayer;

public class initMidi {
    // Déclaration des variables de périphérique MIDI
    MidiDevice launchpadDevice;
    Receiver launchpadReceiver;
    Transmitter launchpadTransmitter;

    public void init() {
        try {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

            for (MidiDevice.Info info : infos) {
                if (info.getName().contains("Launchpad")) {
                    launchpadDevice = MidiSystem.getMidiDevice(info);
                    break;
                }
            }
            launchpadDevice.open();

            launchpadTransmitter = launchpadDevice.getTransmitter();
            System.out.println(launchpadTransmitter);

            launchpadTransmitter.setReceiver(new Receiver() {
                public void send(MidiMessage message, long timeStamp) {
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        int command = sm.getCommand();

                        // Gérer les messages NOTE_ON et NOTE_OFF
                        if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
                            int note = sm.getData1();
                            int velocity = sm.getData2();
                            String type = "note";

                            if (velocity > 0) {
                                // Si velocity > 0, c'est un NOTE_ON
                                displayer.button_showing(note, velocity, type);
                            } else {
                                // Si velocity = 0, c'est un NOTE_OFF
                                displayer.button_showing(note, velocity, type);
                            }

                            // Gérer les messages CONTROL_CHANGE
                        } else if (command == ShortMessage.CONTROL_CHANGE) {
                            int control = sm.getData1();
                            int value = sm.getData2();
                            String type = "CC";
                            displayer.button_showing(control + 1000, value, type);
                        }
                    }
                }

                @Override
                public void close() {
                    System.out.println("Fermeture du Receiver.");
                }
            });

            // Obtenir le Receiver pour envoyer des messages MIDI au Launchpad
            launchpadTransmitter.setReceiver(launchpadReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour envoyer un message NOTE_ON au Launchpad
    public void sendNoteOn(int note, int velocity) {
        sendMidiMessage(ShortMessage.NOTE_ON, note, velocity);
    }

    // Méthode pour envoyer un message NOTE_OFF au Launchpad
    public void sendNoteOff(int note, int velocity) {
        sendMidiMessage(ShortMessage.NOTE_OFF, note, velocity);
    }

    // Méthode pour envoyer un message CONTROL_CHANGE au Launchpad
    public void sendControlChange(int control, int value) {
        sendMidiMessage(ShortMessage.CONTROL_CHANGE, control, value);
    }

    // Méthode générique pour envoyer des messages MIDI au Launchpad
    private void sendMidiMessage(int command, int data1, int data2) {
        try {
            if (launchpadReceiver != null) {
                // Créer un message MIDI
                ShortMessage message = new ShortMessage();
                message.setMessage(command, 0, data1, data2); // Channel 0, data1 (note/control), data2 (velocity/value)
                launchpadReceiver.send(message, -1);  // Envoyer le message immédiatement
            } else {
                System.out.println("Receiver non disponible.");
            }
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
