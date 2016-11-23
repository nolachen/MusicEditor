package cs3500.music.view;

import java.util.HashMap;

import cs3500.music.model.IViewModel;

import cs3500.music.model.ImmutableNote;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * View for the MIDI playback.
 * Uses a Sequencer to play the music from the ViewModel.
 */
public class MidiViewImpl implements IMusicEditorView {
  // viewModel that gives access to necessary information in the model.
  private final IViewModel viewModel;
  // Synthesizer for the view.
  private final Synthesizer synth;
  // Mock Receiver for the synthesizer for testing
  //private final Receiver receiver;
  // Sequencer to store the song
  private final Sequencer sequencer;
  // mapping of instruments to it's channel.
  private HashMap<Integer, Integer> channels;
  // number of channels. Increments for every instrument this view encounters.
  // INVARIANT: channelNum will always be incremented by 1 only.
  private int channelNum;
  // whether this piece is paused
  private boolean paused;
  // the number of ticks per beat to pass to the sequencer
  private static final int TICKS_PER_BEAT = 1;

  /**
   * Constructor for the MidiView implementation.
   *
   * @param viewModel viewModel that gives access to necessary model information.
   */
  public MidiViewImpl(IViewModel viewModel) {
    // tries to initialize everything.
    Synthesizer tempSynth;
    //Receiver tempReceiver;
    Sequencer tempSequencer;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      //tempReceiver = tempSynth.getReceiver();
      tempSequencer = MidiSystem.getSequencer();
    } catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.synth = tempSynth;
    this.synth.loadAllInstruments(this.synth.getDefaultSoundbank());
    //this.receiver = tempReceiver;
    this.sequencer = tempSequencer;
    // tries to open up the synthesizer.
    try {
      this.sequencer.open();
      this.synth.open();
    } catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
    this.paused = false;

    this.sequencer.addMetaEventListener(new CloseProgram());
  }

  /**
   * Convenience constructor that takes in a synthesizer.
   *
   * @param viewModel viewModel that gives access to necessary model information.
   * @param sy        given synthesizer for testing purposes.
   */
  public MidiViewImpl(IViewModel viewModel, Synthesizer sy) {
    this.synth = sy;
    this.synth.loadAllInstruments(this.synth.getDefaultSoundbank());
    // tries to open the given synthesizer.
    try {
      this.synth.open();
    } catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    Sequencer tempSequencer;
    //Receiver tempReceiver;
    try {
      //tempReceiver = synth.getReceiver();
      tempSequencer = MidiSystem.getSequencer();
    } catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.sequencer = tempSequencer;
    try {
      this.sequencer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    //this.receiver = tempReceiver;
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
    this.paused = false;

    this.sequencer.addMetaEventListener(new CloseProgram());

  }

  /**
   * A meta event listener that closes the sequencer when the song is over.
   */
  public class CloseProgram implements MetaEventListener {
    @Override
    public void meta(MetaMessage meta) {
      if (meta.getType() == 0x2f) {
        sequencer.close();
        paused = true;
      }
    }
  }

  /**
   * Writes the message to play the given tone.
   *
   * @param note note being sent to the synthesizers receiver.
   * @param track the track to write the note to
   * @throws InvalidMidiDataException when the midi is invalid.
   */
  private void writeNote(ImmutableNote note, Track track) throws InvalidMidiDataException {
    int octave = note.getOctave();
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + (octave + 1) * 12;
    int duration = note.getDuration();
    int instrument = note.getInstrument();
    int beat = note.getStartBeat();

    // creates a new channel for this instrument if it doesn't yet exist.
    if (!channels.containsKey(instrument)) {
      channels.put(instrument, this.channelNum + 1);
      MidiMessage instr = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channels.get(instrument),
              instrument, 0);
      track.add(new MidiEvent(instr, beat * MidiViewImpl.TICKS_PER_BEAT));
      //this.receiver.send(instr, viewModel.getTempo() * note.getStartBeat());

      // increments the channel count
      channelNum++;
    }

    //synth.getChannels()[0].programChange(synth.getChannels()[0].getProgram());

    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channels.get(instrument),
            noteRepresentation, note.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channels.get(instrument),
            noteRepresentation, note.getVolume());

    // will send the start message without a time stamp then the stop message is sent
    track.add(new MidiEvent(start, beat * MidiViewImpl.TICKS_PER_BEAT));
    track.add(new MidiEvent(stop, (beat + duration) * MidiViewImpl.TICKS_PER_BEAT));

    //this.receiver.send(start, viewModel.getTempo() * note.getStartBeat());
    //this.receiver.send(stop,
    //        (viewModel.getTempo() * duration) + (viewModel.getTempo() * note.getStartBeat()));
  }

  // CHANGED from get notes at beat to get all notes because getNoteAtBeat now returns
  // sustained notes that overlap this beat that may have a different startbeat so we just
  // use the heads of each note to play them in midi

  @Override
  public void makeVisible() {
    try {
      this.sequencer.open();
      Sequence sequence = new Sequence(Sequence.PPQ, MidiViewImpl.TICKS_PER_BEAT);
      Track track = sequence.createTrack();
      for (ImmutableNote n : viewModel.getAllNotes()) {
        this.writeNote(n, track);
      }
      this.sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException | MidiUnavailableException e) {
      throw new IllegalStateException("Invalid midi");
    }

    if (!this.paused) {
      this.sequencer.start();
      this.sequencer.setTempoInMPQ(viewModel.getTempo());
    }
  }

  @Override
  public void togglePause() {
    if (this.paused) {
      if (this.getCurrentBeat() == 0) {
        this.makeVisible();
      }
      try {
        this.sequencer.open();
      } catch (MidiUnavailableException e) {
        throw new IllegalStateException("Midi unavailable");
      }
      this.sequencer.start();
      this.sequencer.setTempoInMPQ(viewModel.getTempo());
    }
    else {
      this.sequencer.stop();
    }

    // toggle whether the view is paused
    this.paused = !this.paused;
  }

  @Override
  public void jumpToStart() {
    this.sequencer.setTickPosition(0);
  }

  @Override
  public void jumpToEnd() {
    this.sequencer.setTickPosition(this.viewModel.length() * MidiViewImpl.TICKS_PER_BEAT);
  }

  /**
   * Gets the current beat of this view.
   * @return the current beat
   */
  int getCurrentBeat() {
    return Math.toIntExact(this.sequencer.getTickPosition());
  }
}
