import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice.Info;

public class MidiDeviceFinder {
    public static void main(String[] args) {
        Info[] midiDevices = MidiSystem.getMidiDeviceInfo();
        for (Info info : midiDevices) {
            System.out.println("MIDI Device: " + info.getName() + " - " + info.getDescription());
        }
    }
}