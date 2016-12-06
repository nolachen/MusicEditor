package cs3500.music.view;

import java.util.HashMap;

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
import javax.sound.midi.Track;

/**
 * View for the MIDI playback.
 * Uses a Sequencer to play the music from the ViewModel.
 */
public class MidiViewImpl implements IMusicEditorView {
  // viewModel that gives access to necessary information in the model.
  private final IViewModel viewModel;
  // Sequencer to store the song
  //TODO check final
  private Sequencer sequencer;
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
    Sequencer tempSequencer;
    try {
      tempSequencer = MidiSystem.getSequencer();
    } catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.sequencer = tempSequencer;
    // tries to open up the sequencer.
    try {
      this.sequencer.open();
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
   * Convenience constructor that takes in a sequencer.
   *
   * @param viewModel viewModel that gives access to necessary model information.
   * @param seq       given sequencer for testing purposes.
   */
  public MidiViewImpl(IViewModel viewModel, Sequencer seq) {
    this.sequencer = seq;
    // tries to open the given sequencer.
    try {
      this.sequencer.open();
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
   * A meta event listener that closes the sequencer when the song is over.
   */
  public class CloseProgram implements MetaEventListener {
    @Override
    public void meta(MetaMessage meta) {
      if (meta.getType() == 0x2f) {
        sequencer.close();
      }
    }
  }

  /**
   * Writes the message to play the given tone.
   *
   * @param note  note being sent to the synthesizers receiver.
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

      // increments the channel count
      channelNum++;
    }

    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channels.get(instrument),
            noteRepresentation, note.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channels.get(instrument),
            noteRepresentation, note.getVolume());

    track.add(new MidiEvent(start, beat * MidiViewImpl.TICKS_PER_BEAT));
    track.add(new MidiEvent(stop, (beat + duration) * MidiViewImpl.TICKS_PER_BEAT));
  }

  /**
   * Updates the sequencer to contain the most updated notes from the view model.
   */
  private void updateSequence() {
    try {
      Sequence sequence = new Sequence(Sequence.PPQ, MidiViewImpl.TICKS_PER_BEAT);
      Track track = sequence.createTrack();
      for (ImmutableNote n : viewModel.getAllNotes()) {
        this.writeNote(n, track);
      }
      this.sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      throw new IllegalStateException("Invalid midi");
    }
  }

  // CHANGED from get notes at beat to get all notes because getNoteAtBeat now returns
  // sustained notes that overlap this beat that may have a different startbeat so we just
  // use the heads of each note to play them in midi

  @Override
  public void makeVisible() {
    try {
      this.sequencer.open();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException("Invalid midi");
    }

    this.updateSequence();

    this.sequencer.setTickPosition(0);
    if (!this.paused) {
      this.sequencer.start();
      this.sequencer.setTempoInMPQ(viewModel.getTempo());
    }
  }

  @Override
  public void togglePause() {
    if (!this.sequencer.isOpen()) {
      try {
        this.sequencer.open();
      } catch (MidiUnavailableException e) {
        throw new IllegalStateException("Midi unavailable");
      }
    }

    if (this.sequencer.isOpen()) {
      if (!this.paused) {
        this.sequencer.stop();
      }
      else {
        this.sequencer.start();
        this.sequencer.setTempoInMPQ(viewModel.getTempo());
      }
    }

    // toggle the pause value
    this.paused = !this.paused;

  }

  /**
   * Return whether this view is paused.
   *
   * @return true if paused, else false
   */
  public boolean getPaused() {
    return this.paused;
  }

  @Override
  public void jumpToStart() {
    this.sequencer.setTickPosition(0);
  }


  @Override
  public void jumpToEnd() {
    this.sequencer.setTickPosition(this.viewModel.length() * MidiViewImpl.TICKS_PER_BEAT);
  }

  @Override
  public void refresh() {
    this.viewModel.updateData();

    int curTick = this.getCurrentBeat();
    this.updateSequence();
    this.setCurrentBeat(curTick);
    this.sequencer.setTempoInMPQ(viewModel.getTempo());
  }

  /**
   * Set the current tick position to match the given beat.
   *
   * @param beat the new current beats
   */
  public void setCurrentBeat(int beat) {
    this.sequencer.setTickPosition(beat * MidiViewImpl.TICKS_PER_BEAT);
  }

  /**
   * Gets the current beat of this view.
   *
   * @return the current beat
   */
  int getCurrentBeat() {
    return Math.toIntExact(this.sequencer.getTickPosition());
  }
}
