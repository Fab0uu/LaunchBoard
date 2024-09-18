import javax.sound.midi.*;

public class LaunchpadListenerWithoutTransmitter {
    public static void main(String[] args) {
        try {
            // Récupérer les périphériques MIDI disponibles
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            MidiDevice launchpad = null;

            // Chercher le Launchpad MK1 parmi les périphériques MIDI
            for (MidiDevice.Info info : infos) {
                if (info.getName().contains("Launchpad")) {
                    launchpad = MidiSystem.getMidiDevice(info);
                    break;
                }
            }

            if (launchpad == null) {
                System.out.println("Launchpad non trouvé.");
                return;
            }

            // Ouvrir la connexion avec le Launchpad MK1
            launchpad.open();

            // Vérifier si le Launchpad accepte un Receiver (MIDI IN)
            if (launchpad.getMaxReceivers() != 0) {
                Receiver receiver = launchpad.getReceiver();

                // Créer un Receiver pour écouter les événements MIDI
                Receiver myReceiver = new Receiver() {
                    @Override
                    public void send(MidiMessage message, long timeStamp) {
                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            int note = sm.getData1();  // Numéro de la note (bouton)
                            int velocity = sm.getData2();  // Vélocité (appui ou relâchement)

                            // Si la vélocité est > 0, cela signifie que la touche est appuyée
                            if (velocity > 0) {
                                System.out.println("Touche appuyée : " + note + " avec vélocité : " + velocity);
                                // Déclencher une action spécifique ici
                            }
                        }
                    }

                    @Override
                    public void close() {
                        System.out.println("Receiver fermé.");
                    }
                };

                // Envoyer des messages au Receiver du Launchpad
                receiver.send(new ShortMessage(), -1);
            } else {
                System.out.println("Ce périphérique MIDI ne peut pas recevoir de messages.");
            }

            // Boucle pour maintenir le programme en cours d'exécution
            System.out.println("Écoute des événements MIDI du Launchpad MK1...");
            Thread.sleep(Long.MAX_VALUE);  // Le programme continue d'écouter indéfiniment

        } catch (MidiUnavailableException | InterruptedException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
