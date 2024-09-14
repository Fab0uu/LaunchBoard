import javax.sound.midi.*;

public class LaunchpadConnector {
    public static void main(String[] args) {
        try {
            // Récupérer tous les périphériques MIDI disponibles
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            MidiDevice launchpad = null;

            // Parcourir les périphériques pour trouver le Launchpad
            for (MidiDevice.Info info : infos) {
                if (info.getName().contains("Launchpad")) {  // Remplace par le nom exact s'il diffère
                    launchpad = MidiSystem.getMidiDevice(info);
                    break;
                }
            }

            if (launchpad == null) {
                System.out.println("Launchpad non trouvé.");
                return;
            }

            // Ouvrir la connexion avec le Launchpad
            launchpad.open();
            System.out.println("Connexion au Launchpad réussie!");

            // Créer un récepteur pour envoyer des messages MIDI
            Receiver receiver = launchpad.getReceiver();

            // Exemple d'envoi d'un message MIDI pour allumer une LED (Note On)
            ShortMessage message = new ShortMessage();


            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, 127);  // CC 104, vélocité 127 pour allumer en plein
            receiver.send(message, -1);

            Thread.sleep(1000);

            message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, 0);  // CC 104, vélocité 127 pour allumer en plein
            receiver.send(message, -1);

            Thread.sleep(500);

            message.setMessage(ShortMessage.NOTE_ON, 0, 0, 127);
            receiver.send(message, -1);

            Thread.sleep(500);

            message.setMessage(ShortMessage.NOTE_ON, 0, 0, 3);
            receiver.send(message, -1);

            Thread.sleep(500);

            message.setMessage(ShortMessage.NOTE_ON, 0, 0, 48);
            receiver.send(message, -1);

            Thread.sleep(500);

            message.setMessage(ShortMessage.NOTE_OFF, 0, 0);
            receiver.send(message, -1);


            // Fermer la connexion lorsque terminé
            launchpad.close();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
