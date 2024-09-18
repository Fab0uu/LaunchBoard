import javax.sound.midi.*;

public class LaunchpadListener {

    private MidiDevice launchpadDevice;

    public LaunchpadListener() {
        try {
            // Obtenir la liste des périphériques MIDI disponibles
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

            // Parcourir les périphériques pour trouver le Launchpad
            for (MidiDevice.Info info : infos) {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getDeviceInfo().getName().contains("Launchpad")) {
                    launchpadDevice = device;
                    break;
                }
            }

            if (launchpadDevice != null) {
                launchpadDevice.open();
                Transmitter transmitter = launchpadDevice.getTransmitter();
                transmitter.setReceiver(new MyMidiReceiver());
            }

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Classe interne pour gérer les événements MIDI
    private class MyMidiReceiver implements Receiver {
        @Override
        public void send(MidiMessage message, long timeStamp) {
            byte[] data = message.getMessage();
            int note = data[1];  // 2ème byte = le numéro de note
            int velocity = data[2];  // 3ème byte = la vélocité

            // Vérifiez si le bouton 0 du Launchpad est pressé
            if (note == 0 && velocity > 0) {
                System.out.println("Le bouton 0 a été pressé !");
                // Appel à la méthode pour changer la couleur du bouton dans JavaFX
                changeButtonColor();
            }
        }

        @Override
        public void close() {
        }
    }

    // Méthode qui sera appelée lorsqu'on appuie sur le bouton 0
    public void changeButtonColor() {
        // Implémentez la logique pour changer la couleur du bouton "Bu0" ici
        // Cette partie sera liée à l'interface JavaFX
    }
}
