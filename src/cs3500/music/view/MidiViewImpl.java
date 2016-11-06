package cs3500.music.view;

import cs3500.music.model.ImmutableNote;
import cs3500.music.model.Pitch;
import cs3500.music.model.ViewModel;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicEditorView {
  private final Synthesizer synth;
  // receiver for the synthesizer.
  private final Receiver receiver;
  private final Sequencer seqr;
  // receiver for the sequencer.
  private final Receiver seqrReceiver;
  // transmitter for the sequencer.
  private final Transmitter seqrTrans;
  private Sequence seq;
  // represents tick, number of pulses per quarter note
  // TODO do i need this
  private final int tick;
  private ViewModel viewModel;
  private List<Track> tracks;

  // Cosntructor
  public MidiViewImpl(int tick, ViewModel viewModel) throws MidiUnavailableException {
    // tries to initialize everything.
    Synthesizer tempSynth;
    Sequencer tempSeqr;
    Sequence tempSeq;
    Transmitter tempSeqrTrans;
    Receiver tempReceiver;
    Receiver tempSeqrReceiver;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempSeqr = MidiSystem.getSequencer();
      tempSeq = new Sequence(Sequence.PPQ, 10);
      tempSeqr.setSequence(tempSeq);
      tempSeqrTrans = tempSeqr.getTransmitter();
      tempReceiver = tempSynth.getReceiver();
      tempSeqrReceiver = tempSeqr.getReceiver();
    }
    catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }

    //might have an error with the sequence methods later
    this.synth = tempSynth;
    this.seq = tempSeq;
    this.seqr = tempSeqr;
    this.seqrTrans = tempSeqrTrans;
    this.receiver = tempReceiver;
    this.seqrReceiver = tempSeqrReceiver;
    this.seqrTrans.setReceiver(receiver);
    this.synth.open();
    this.tick = tick;
    this.viewModel = viewModel;
    this.tracks = new ArrayList<>();
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

/*  //PLAYING A SEQUENCE IS DONE BY VOID START AND VOID STOP

  public void oldPlayNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    //this.receiver.close(); // Only call this once you're done playing *all* notes
  }*/

  // makes the message to play the given note
  //tODO fix the 64
  public void writeNote(ImmutableNote note, Track track) throws InvalidMidiDataException {
    int octave = note.getOctave();
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + octave*12;
    int duration = note.getDuration();
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, noteRepresentation, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, noteRepresentation, 64);
    track.add(new MidiEvent(start, -1));
    //TODO IMPORTANT fix the timing cause who knows what the fuck that is
    track.add(new MidiEvent(stop, this.synth.getMicrosecondPosition() + 200000));
    //todo this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  // TODO NOT RIGHT
  @Override
  public void makeVisible() {
    this.seqr.start();
  }

  // creates a track and adds it to the sequence
  // TODO deal with the exception
  private void makeTrack() throws InvalidMidiDataException{
    Track track = seq.createTrack();
    int length = viewModel.length();
    for(int i = 0; i < length; i++) {
      List<ImmutableNote> notes = viewModel.getNotesAtBeat(i);
      for(ImmutableNote n: notes) {
        this.writeNote(n, track);
      }
    }
  }

  @Override
  public void refresh() {

  }
}
