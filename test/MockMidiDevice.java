import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

import java.util.List;


/**
 * Mock sequencer for testing.
 */
public class MockMidiDevice implements MidiDevice {
  // log of messages.
  Sequencer sequencer;
  StringBuilder log;
  Receiver receiver;
  Transmitter transmitter;

  /**
   * constructor.
   */
  MockMidiDevice() {
    this.log = new StringBuilder();
    //this.receiver = new MockReceiver(log);
    Sequencer tempSeq = null;
    Transmitter tempTrans = null;
    Receiver tempRec = null;

    try {
      tempSeq = MidiSystem.getSequencer();
      tempTrans = tempSeq.getTransmitter();
      tempTrans.setReceiver(new MockReceiver(log));
      tempRec = tempTrans.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    this.sequencer = tempSeq;
    this.transmitter = tempTrans;
    this.receiver = tempRec;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    this.sequencer.open();
  }

  @Override
  public void close() {
    //empty body.
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public MockReceiver getReceiver() throws MidiUnavailableException {
    return (MockReceiver) this.receiver;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return transmitter;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  /**
   * returns the sequencer of this device.
   * @return the sequencer of this mock device.
   */
  public Sequencer getSequencer() {
    return this.sequencer;
  }

}