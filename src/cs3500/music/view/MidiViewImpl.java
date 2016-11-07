package cs3500.music.view;

import cs3500.music.model.ImmutableNote;

import cs3500.music.model.ViewModel;

import javax.sound.midi.*;

import java.util.HashMap;

import java.util.List;

/**
 * View for the MIDI playback.
 * Uses a Synthesizer to play the music from the ViewModel.
 */
public class MidiViewImpl implements IMusicEditorView {
  // viewModel that gives access to necessary information in the model.
  private ViewModel viewModel;
  // Synthesizer for the view.
  private final Synthesizer synth;
  // Receiver for the synthesizer
  private final Receiver receiver;
  // mapping of instruments to it's channel.
  private HashMap<Integer, Integer> channels;
  // number of channels. Increments for every instrument this view encounters.
  // INVARIANT: channelNum will always be incremented by 1 only.
  private int channelNum;

  /**
   * Constructor for the MidiView implementation.
   * @param viewModel viewModel that gives access to necessary model information.
   */
  public MidiViewImpl(ViewModel viewModel) {
    // tries to initialize everything.
    Synthesizer tempSynth;
    Receiver tempReceiver;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempReceiver = tempSynth.getReceiver();
    }
    catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.synth = tempSynth;
    this.receiver = tempReceiver;
    // tries to open up the synthesizer.
    try {
      this.synth.open();
    }
    catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
  }

  /**
   * @param viewModel viewModel that gives access to necessary model information.
   * @param sy given synthesizer for testing purposes.
   */
  public MidiViewImpl(ViewModel viewModel, Synthesizer sy) {
    this.synth = sy;
    // tries to open the given synthesizer.
    try {
      this.synth.open();
    }
    catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    Receiver tempReceiver;
    try {
      tempReceiver = synth.getReceiver();
    }
    catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.receiver = tempReceiver;
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
  }

  /**
   * Writes the message to play the given tone.
   * @param note note being sent to the synthesizers recevier.
   * @throws InvalidMidiDataException
   */
  private void writeNote(ImmutableNote note) throws InvalidMidiDataException {
    int octave = note.getOctave();
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + (octave + 1) * 12;
    int duration = note.getDuration();
    int instrument = note.getInstrument();
    // creates a new channel for this instrument if it doesn't yet exist.
    if (!channels.containsKey(instrument)) {
      channels.put(instrument, this.channelNum + 1);
      receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channels.get(instrument),
              instrument, 0), -1);
      // increments the channel count
      channelNum++;
    }
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channels.get(instrument),
            noteRepresentation, note.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channels.get(instrument),
            noteRepresentation, note.getVolume());
    receiver.send(start, viewModel.getTempo() * note.getStartBeat());
    receiver.send(stop,
            (viewModel.getTempo() * duration) + (viewModel.getTempo() * note.getStartBeat()));
  }

  @Override
  public void makeVisible() {
    int length = viewModel.length();
    for (int i = 0; i < length; i++) {
      List<ImmutableNote> notes = viewModel.getNotesAtBeat(i);
      for (ImmutableNote n : notes) {
        try {
          this.writeNote(n);
        } catch (Exception midiE) {
          throw new IllegalStateException("Invalid midi data exception.");
        }
      }
    }
    this.receiver.close();
  }

  @Override
  public void refresh() {
    //nothing
  }

}
