package fr.fabou.launchboard;

import javax.sound.midi.*;
import java.util.Arrays;
import java.util.Objects;

import static fr.fabou.launchboard.Main.displayer;

public class Launchpad_Controller {
    public MidiDevice launchpadDevice;
    public boolean first_init = false;
    public ShortMessage message = new ShortMessage();
    private Receiver receiver;
    private Transmitter transmitter;

    protected void status() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
        while (true) {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (MidiDevice.Info info : infos) {

                if (info.getName().contains("Launchpad")) {
                    launchpadDevice = MidiSystem.getMidiDevice(info);
                }
            }
            if (!Arrays.toString(infos).contains("Launchpad")){
                launchpadDevice = null;
            }

            if (launchpadDevice != null) {
                Display_Controller.connexion_status = true;
            }
            else {
                Display_Controller.connexion_status = false;
            }
            Thread.sleep(500);
        }
    }

    protected void startup() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info info : infos) {
            if (info.getName().contains("Launchpad")) {
                launchpadDevice = MidiSystem.getMidiDevice(info);
                break;
            }
        }

        launchpadDevice.open();
        Receiver receiver = launchpadDevice.getReceiver();

        if (first_init) {
            for (int note = 0; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 19);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 19);
                receiver.send(message, -1);
                Thread.sleep(10);
            }

            for (int note = 0; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 1);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 1);
                receiver.send(message, -1);
                Thread.sleep(10);
            }

            for (int note = 0; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 48);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 48);
                receiver.send(message, -1);
                Thread.sleep(10);
            }

            Thread.sleep(1000);

            message.setMessage(ShortMessage.NOTE_ON, 0, 0, 0);
            receiver.send(message, -1);
            Thread.sleep(10);

            for (int note = 1; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 0);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 0);
                receiver.send(message, -1);
                Thread.sleep(10);
            }

            this.first_init = false;
        } else {
            for (int note = 0; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 1);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 1);
                receiver.send(message, -1);
            }

            Thread.sleep(100);

            message.setMessage(ShortMessage.NOTE_ON, 0, 0, 0);
            receiver.send(message, -1);
            for (int note = 1; note < 121; note++) {
                message.setMessage(ShortMessage.NOTE_ON, 0, note, 0);
                receiver.send(message, -1);
                message.setMessage(ShortMessage.CONTROL_CHANGE, 0, note, 0);
                receiver.send(message, -1);
            }
        }
        launchpadDevice.close();
        new Thread(this::listener).start();

    }

    public void listener () {
        try {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

            for (MidiDevice.Info info : infos) {
                if (info.getName().contains("Launchpad")) {
                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if (device.getMaxTransmitters() != 0) {  // Vérifier s'il a des Transmitters
                        launchpadDevice = device;
                        break;
                    }
                }
            }
            launchpadDevice.open();

            this.receiver = new Receiver() {
                public void send(MidiMessage message, long timeStamp) {
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        int command = sm.getCommand();

                        if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
                            int note = sm.getData1();
                            int velocity = sm.getData2();
                            String type = "note";

                            displayer.button_showing(note, velocity, type);

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

            };

            this.transmitter = launchpadDevice.getTransmitter();
            transmitter.setReceiver(receiver);

            System.out.println("Écoute des événements MIDI du Launchpad MK1...");
            Thread.sleep(Long.MAX_VALUE);

        } catch (MidiUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void User_Choice(String to_user) throws InvalidMidiDataException, MidiUnavailableException {
        if (Objects.equals(to_user, "user1")) {
            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 109, 3);
            receiver.send(message, -1);
            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 110, 0);
            receiver.send(message, -1);
        } else if (Objects.equals(to_user, "user2")) {
            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 109, 0);
            receiver.send(message, -1);
            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 110, 3);
            receiver.send(message, -1);
        }
    }
}