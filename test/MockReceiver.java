import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.io.Reader;

/**
 * Created by Marina on 11/6/2016.
 */
public class MockReceiver implements Receiver {
  StringBuilder log;

  MockReceiver(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    int channel = ((ShortMessage) message).getChannel();
    int pitchOctave = ((ShortMessage) message).getData1();
    int volume = ((ShortMessage) message).getData2();
    this.log.append("Message " + channel + " " + pitchOctave + " " +
            volume + " " + timeStamp+  "\n");
  }

  @Override
  public void close() {
    //does nothing
  }
}
