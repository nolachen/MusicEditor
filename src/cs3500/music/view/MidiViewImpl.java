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
  private ViewModel viewModel;
  private final Synthesizer synth;
  private final Receiver receiver;

  // Cosntructor
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
    //might have an error with the sequence methods later
    this.synth = tempSynth;
    this.receiver = tempReceiver;
    try {
      this.synth.open();
    }
    catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    this.viewModel = viewModel;
  }

  MidiViewImpl(ViewModel viewModel, Synthesizer sy) {
    this.synth = sy;
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
    //might have an error with the sequence methods later
    this.receiver = tempReceiver;
    this.viewModel = viewModel;

  }

  // makes the message to play the given note
  //tODO fix the 64
  public void writeNote(ImmutableNote note) throws InvalidMidiDataException {
    int octave = note.getOctave();
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + octave*12;
    int duration = note.getDuration();
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, note.getInstrument(), noteRepresentation, note.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, note.getInstrument(), noteRepresentation, note.getVolume());
    receiver.send(start, viewModel.getTempo() * note.getStartBeat());
    receiver.send(stop, (viewModel.getTempo() * duration) + (viewModel.getTempo() * note.getStartBeat()));
    //todo this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  // TODO NOT RIGHT
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

  /*// creates a track and adds it to the sequence
  // TODO deal with the exception
  private void makeTrack(){
    Track track = seq.createTrack();
    int length = viewModel.length();
    for(int i = 0; i < length; i++) {
      List<ImmutableNote> notes = viewModel.getNotesAtBeat(i);
      for(ImmutableNote n: notes) {
        try {
          this.writeNote(n, track);
        }
        catch (Exception midiE) {
          throw new IllegalStateException("Invalid midi data exception.");
        }
      }
    }
  }*/

  //TODO probably wrong
  @Override
  public void refresh() {
    //seqr.setTickPosition(0);
  }
}
