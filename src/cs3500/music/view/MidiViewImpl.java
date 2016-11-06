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
  // used to set a tempo for the sequence
  // PPQ, ticks per quarter note
  // each duration unit is one quarter note, 1 duration = tick PPQ
  private final int tick;
  private final float tempoBPM;
  private ViewModel viewModel;
  private List<Track> tracks;
  private final int timingResolution;

  // Cosntructor
  public MidiViewImpl(int tick, ViewModel viewModel, int timingResolution, float tempoBPM) throws MidiUnavailableException {
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
    this.timingResolution = timingResolution;
    this.tempoBPM = tempoBPM;
    this.seqr.setTempoInBPM(this.tempoBPM);
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

  // makes the message to play the given note
  //tODO fix the 64
  public void writeNote(ImmutableNote note, Track track) throws InvalidMidiDataException {
    int octave = note.getOctave();
    //TODO GET PITCH OCTAVE
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + octave*12;
    // number of quarter notes this note is
    int duration = note.getDuration();
    // TODO check if this casting is wrong
    long ticksPerSecond = (long)(this.timingResolution * (this.tempoBPM / 60.0));
    // a single tick in second representation
    long tickSize = 1 / ticksPerSecond;
    // TODO check if this is right
    // quarter note size in milliseconds
    long quarterNoteSize = tickSize * tick * 1000;
    // TODO check what the 64 is
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, noteRepresentation, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, noteRepresentation, 64);
    track.add(new MidiEvent(start, -1));
    //TODO IMPORTANT fix the timing cause who knows what the fuck that is
    // 200000 represents the duration time
    track.add(new MidiEvent(stop, this.synth.getMicrosecondPosition() + (quarterNoteSize * duration)));
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

  //TODO probably wrong
  @Override
  public void refresh() {
    seqr.setTickPosition(0);
  }
}
