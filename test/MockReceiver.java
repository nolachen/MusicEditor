import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import java.io.Reader;

/**
 * Created by Marina on 11/6/2016.
 */
public class MockReceiver implements Receiver {
  StringBuilder in;

  MockReceiver(StringBuilder in) {
    this.in = in;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    this.in.append(message);
  }

  @Override
  public void close() {
    //does nothing
  }
}
