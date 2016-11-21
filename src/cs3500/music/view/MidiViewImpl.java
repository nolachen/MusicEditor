package cs3500.music.view;

import cs3500.music.model.IViewModel;

import cs3500.music.model.ImmutableNote;

import javax.sound.midi.*;

import java.util.*;

// TODO WHAT IF MAKEVISIBLE CREATED A MAP OF MESSAGES TO THEIR BEATS THEN WE CAN PAUSE EASILY
/**
 * View for the MIDI playback.
 * Uses a Synthesizer to play the music from the ViewModel.
 */
public class MidiViewImpl implements IMusicEditorView {
  // viewModel that gives access to necessary information in the model.
  private final IViewModel viewModel;
  // Synthesizer for the view.
  private final Synthesizer synth;
  // Receiver for the synthesizer
  private final Receiver receiver;
  // mapping of instruments to it's channel.
  private HashMap<Integer, Integer> channels;
  // number of channels. Increments for every instrument this view encounters.
  // INVARIANT: channelNum will always be incremented by 1 only.
  private int channelNum;
  // hashmap of int beats to messages sent at that beat
  private List<MidiEvent> messages;
  // int current beat
  private int currentBeat;
  // TODO int beat paused at or should i use a boolean
  //private int beatPausedAt;
  private boolean paused;

  /**
   * Constructor for the MidiView implementation.
   *
   * @param viewModel viewModel that gives access to necessary model information.
   */
  public MidiViewImpl(IViewModel viewModel) {
    // tries to initialize everything.
    Synthesizer tempSynth;
    Receiver tempReceiver;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempReceiver = tempSynth.getReceiver();
    } catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.synth = tempSynth;
    this.receiver = tempReceiver;
    // tries to open up the synthesizer.
    try {
      this.synth.open();
    } catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
    this.messages = new ArrayList<>();
    this.currentBeat = 0;
    //this.beatPausedAt = this.currentBeat;
    this.paused = false;
  }

  /**
   * Convenience constructor that takes in a synthesizer.
   *
   * @param viewModel viewModel that gives access to necessary model information.
   * @param sy        given synthesizer for testing purposes.
   */
  public MidiViewImpl(IViewModel viewModel, Synthesizer sy) {
    this.synth = sy;
    // tries to open the given synthesizer.
    try {
      this.synth.open();
    } catch (Exception me) {
      throw new IllegalStateException("Midi Unavailable.");
    }
    Receiver tempReceiver;
    try {
      tempReceiver = synth.getReceiver();
    } catch (Exception e) {
      throw new IllegalStateException("MidiSystem unavailable.");
    }
    this.receiver = tempReceiver;
    this.viewModel = viewModel;
    channels = new HashMap<>();
    channelNum = -1;
    this.messages = new ArrayList<>();
    this.currentBeat = 0;
    this.paused = false;
  }

  /**
   * Writes the message to play the given tone.
   *
   * @param note note being sent to the synthesizers receiver.
   * @throws InvalidMidiDataException when the midi is invalid.
   */
  private void writeNote(ImmutableNote note) throws InvalidMidiDataException {
    int octave = note.getOctave();
    int pitch = note.getPitch().ordinal();
    int noteRepresentation = pitch + (octave + 1) * 12;
    int duration = note.getDuration();
    int instrument = note.getInstrument();
    int beat = note.getStartBeat();
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

    // will send the start message without a time stamp then the stop message is sent
    // timing might not work when starting from a different beat but who knows lets find out
    this.messages.add(new MidiEvent(start, beat * viewModel.getTempo()));
    this.messages.add(new MidiEvent(stop, (beat + duration) * viewModel.getTempo()));

    /*receiver.send(start, viewModel.getTempo() * note.getStartBeat());
    receiver.send(stop,
            (viewModel.getTempo() * duration) + (viewModel.getTempo() * note.getStartBeat()));*/
  }

  // CHANGED from get notes at beat to get all notes because getNoteAtBeat now returns
  // sustained notes that overlap this beat that may have a different startbeat so we just
  // use the heads of each note to play them in midi

  // creates the hashmap of beats and plays from the beginning
  @Override
  public void makeVisible() {
    /*List<ImmutableNote> notes = viewModel.getAllNotes();
    for (ImmutableNote n : notes) {
      try {
        this.writeNote(n);
      } catch (Exception midiE) {
        throw new IllegalStateException("Invalid midi data exception.");
      }
    }*/

    int length = viewModel.length();
    for (int i = 0; i < length; i++) {
      List<ImmutableNote> notes = viewModel.getNotesAtBeat(i);
      for (ImmutableNote n : notes) {
        if (n.getStartBeat() == i) {
          try {
            this.writeNote(n);
          } catch (Exception midiE) {
            //midiE.printStackTrace();
            throw new IllegalStateException("Invalid midi data exception.");
          }
        }
      }
    }

    this.play();

/*
    try {
      Thread.sleep(viewModel.getTempo() * length / 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.receiver.close();*/
  //TODO
    //this.play();
  }


  @Override
  public void togglePause() {
    if (this.paused) {
      this.play();
    }
    else {
      this.pause();
    }
    this.paused = !this.paused;
  }

  @Override
  public void jumpToStart() {
    //TODO
  }

  @Override
  public void jumpToEnd() {
    //TODO
  }


  /**
   * Sends the messages from the hashmap
   */
  private void play() {
    System.out.println("Played.");
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException("Synthesizer is not valid.");
    }
    //System.out.println(this.messages.size());

    for (int i = 0; i < this.messages.size(); i += 1) {
      this.receiver.send(this.messages.get(i).getMessage(), this.messages.get(i).getTick());
    }

   //while (this.synth.getMicrosecondPosition() <= viewModel.length() * viewModel.getTempo()) {

     //}



    /*
    //todo check with nola
    while (this.synth.getMicrosecondPosition() <= viewModel.length() * viewModel.getTempo()) {
      //System.out.println("cur beat: " +currentBeat);
      long time = this.synth.getMicrosecondPosition();
      //will only send messages on each beat calculated by the tempo
      // TODO check this integer division
      //System.out.println(( (1.0 / this.viewModel.getTempo()) * 1000));
      //if (time % ((1.0 / this.viewModel.getTempo()) * 1000) == 0) {
      if (time % this.viewModel.getTempo() == 0) {
        if (this.messages.get(this.currentBeat) != null) {
          List<MidiEvent> currentMessages = this.messages.get(this.currentBeat);
          for (int i = 0; i < currentMessages.size(); i += 1) {
            this.receiver.send(currentMessages.get(i).getMessage(),
                    currentMessages.get(i).getTick());
          }
        }
        //System.out.println(currentBeat);

      }
      //increments the beat count
      this.currentBeat += 1;
    }*/


    this.synth.close();
  }

  //TODO not right, should it be private?
  public void pause() {
    System.out.println("Paused.");
    try {
      MidiMessage stop = new ShortMessage(ShortMessage.STOP);
      this.receiver.send(stop, this.currentBeat * viewModel.getTempo());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    //this.receiver.close();
    //this.beatPausedAt = currentBeat;
  }

  public int getCurrentBeat() {
    return this.currentBeat;
  }
}
