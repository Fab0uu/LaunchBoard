import javax.sound.midi.*;

public class LaunchpadButtonListener {
    public static void main(String[] args) {
        try {
            // Récupérer les périphériques MIDI disponibles
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            MidiDevice inputDevice = null;

            // Chercher le périphérique d'entrée MIDI pour le Launchpad MK1
            for (MidiDevice.Info info : infos) {
                if (info.getName().contains("Launchpad")) {
                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if (device.getMaxTransmitters() != 0) {  // Vérifier s'il a des Transmitters
                        inputDevice = device;
                        break;
                    }
                }
            }

            if (inputDevice == null) {
                System.out.println("Aucun périphérique Launchpad MK1 avec Transmitter trouvé.");
                return;
            }

            // Ouvrir la connexion avec le Launchpad MK1
            inputDevice.open();

            // Créer un Receiver personnalisé pour écouter les événements MIDI
            Receiver receiver = new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        int command = sm.getCommand();  // Type de commande MIDI

                        // Vérifier si c'est un message de note ou de contrôle
                        if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
                            int note = sm.getData1();  // Numéro de la note (bouton)
                            int velocity = sm.getData2();  // Vélocité (force d'appui ou relâchement)

                            if (velocity > 0) {
                                System.out.println("Note On: Bouton appuyé (ID) : " + note + " avec vélocité : " + velocity);
                            } else {
                                System.out.println("Note Off: Bouton relâché (ID) : " + note);
                            }

                        } else if (command == ShortMessage.CONTROL_CHANGE) {
                            int control = sm.getData1();  // Numéro du contrôleur (bouton de contrôle)
                            int value = sm.getData2();  // Valeur de contrôle (vélocité)

                            // Messages personnalisés pour les boutons spéciaux
                            switch (control) {
                                case 104:
                                    System.out.println("Bouton flèche haut (Arrow Up) détecté.");
                                    break;
                                case 105:
                                    System.out.println("Bouton flèche bas (Arrow Down) détecté.");
                                    break;
                                case 106:
                                    System.out.println("Bouton flèche gauche (Arrow Left) détecté.");
                                    break;
                                case 107:
                                    System.out.println("Bouton flèche droite (Arrow Right) détecté.");
                                    break;
                                case 108:
                                    System.out.println("Bouton 'Session' détecté.");
                                    break;
                                case 109:
                                    System.out.println("Bouton 'User 1' détecté.");
                                    break;
                                case 110:
                                    System.out.println("Bouton 'User 2' détecté.");
                                    break;
                                case 111:
                                    System.out.println("Bouton 'Mixer' détecté.");
                                    break;
                                default:
                                    System.out.println("Control Change: Bouton de contrôle (ID) : " + control + " avec valeur : " + value);
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void close() {
                    System.out.println("Fermeture du Receiver.");
                }
            };

            // Attacher le Transmitter du périphérique d'entrée au Receiver
            Transmitter transmitter = inputDevice.getTransmitter();
            transmitter.setReceiver(receiver);

            // Boucle pour maintenir le programme en cours d'exécution
            System.out.println("Écoute des événements MIDI du Launchpad MK1...");
            Thread.sleep(Long.MAX_VALUE);  // Le programme continue d'écouter indéfiniment

        } catch (MidiUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
