/*

import cs3500.music.model.IMusicEditorModel;

import cs3500.music.view.IViewModel;

import cs3500.music.model.MusicEditorModel;

import cs3500.music.view.ViewModel;

import cs3500.music.util.CompositionBuilder;

import cs3500.music.util.ModelBuilder;

import cs3500.music.view.MidiViewImpl;

import org.junit.Before;

import org.junit.Test;

import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import static org.junit.Assert.assertEquals;



*/
/**
 * Uses a mock midi device and a mock receiver to test midiview.
 *//*


public class MockMidiTest {
  IMusicEditorModel model;
  IViewModel vm;
  MockReceiver receiver;
  StringBuilder log;
  MockMidiDevice device;
  MidiViewImpl midiView;
  CompositionBuilder builder;
  Sequencer sequencer;


*/
/**
   * Sets the initial conditions.
   *//*


  @Before
  public void initCond() {
    this.model = new MusicEditorModel();
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm);
    this.device = new MockMidiDevice();
    this.sequencer = this.device.getSequencer();

    this.builder = new ModelBuilder();

    MockReceiver temp = null;
    try {
      temp = this.device.getReceiver();
      //this.device.open();
    }
    catch (Exception e) {
      throw new IllegalStateException("Invalid midi device");
    }
    this.midiView = new MidiViewImpl(vm, this.sequencer);
    this.receiver = temp;
    this.log = this.receiver.log;
  }

  // tests if the midiview initially has an empty sequence of commands.
  @Test
  public void testMidi() {
    StringBuffer out = new StringBuffer();
    this.midiView.makeVisible();
    assertEquals("", this.log.toString());
  }

  // Tests if the receiver receives all appropriate messages representing the models notes.
  @Test
  public void testMidi1() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first note: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp
    //note is sent to end.

    this.builder.addNote(0, 2, 1, 64, 72);
    this.model = new MusicEditorModel(this.builder);
    assertEquals(this.model.getAllNotes().size(), 1);
    this.vm = new ViewModel(this.model);
    assertEquals(this.vm.getNoteRange().size(), 1);
    this.midiView = new MidiViewImpl(this.vm, this.sequencer);
    this.midiView.makeVisible();
    System.out.println(this.receiver.log.toString());
    assertEquals(
            "Message 0 123 0 -1\n" +
            "Message 0 64 0 -1\n" +
            "Message 0 121 0 -1\n" +
            "Message 1 123 0 -1\n" +
            "Message 1 64 0 -1\n" +
            "Message 1 121 0 -1\n" +
            "Message 2 123 0 -1\n" +
            "Message 2 64 0 -1\n" +
            "Message 2 121 0 -1\n" +
            "Message 3 123 0 -1\n" +
            "Message 3 64 0 -1\n" +
            "Message 3 121 0 -1\n" +
            "Message 4 123 0 -1\n" +
            "Message 4 64 0 -1\n" +
            "Message 4 121 0 -1\n" +
            "Message 5 123 0 -1\n" +
            "Message 5 64 0 -1\n" +
            "Message 5 121 0 -1\n" +
            "Message 6 123 0 -1\n" +
            "Message 6 64 0 -1\n" +
            "Message 6 121 0 -1\n" +
            "Message 7 123 0 -1\n" +
            "Message 7 64 0 -1\n" +
            "Message 7 121 0 -1\n" +
            "Message 8 123 0 -1\n" +
            "Message 8 64 0 -1\n" +
            "Message 8 121 0 -1\n" +
            "Message 9 123 0 -1\n" +
            "Message 9 64 0 -1\n" +
            "Message 9 121 0 -1\n" +
            "Message 10 123 0 -1\n" +
            "Message 10 64 0 -1\n" +
            "Message 10 121 0 -1\n" +
            "Message 11 123 0 -1\n" +
            "Message 11 64 0 -1\n" +
            "Message 11 121 0 -1\n" +
            "Message 12 123 0 -1\n" +
            "Message 12 64 0 -1\n" +
            "Message 12 121 0 -1\n" +
            "Message 13 123 0 -1\n" +
            "Message 13 64 0 -1\n" +
            "Message 13 121 0 -1\n" +
            "Message 14 123 0 -1\n" +
            "Message 14 64 0 -1\n" +
            "Message 14 121 0 -1\n" +
            "Message 15 123 0 -1\n" +
            "Message 15 64 0 -1\n" +
            "Message 15 121 0 -1\n" +
            "Message 0 64 0 -1\n" +
            "Message 1 123 0 -1\n" +
            "Message 1 64 0 -1\n" +
            "Message 2 123 0 -1\n" +
            "Message 2 64 0 -1\n" +
            "Message 3 123 0 -1\n" +
            "Message 3 64 0 -1\n" +
            "Message 4 123 0 -1\n" +
            "Message 4 64 0 -1\n" +
            "Message 5 123 0 -1\n" +
            "Message 5 64 0 -1\n" +
            "Message 6 123 0 -1\n" +
            "Message 6 64 0 -1\n" +
            "Message 7 123 0 -1\n" +
            "Message 7 64 0 -1\n" +
            "Message 8 123 0 -1\n" +
            "Message 8 64 0 -1\n" +
            "Message 9 123 0 -1\n" +
            "Message 9 64 0 -1\n" +
            "Message 10 123 0 -1\n" +
            "Message 10 64 0 -1\n" +
            "Message 11 123 0 -1\n" +
            "Message 11 64 0 -1\n" +
            "Message 12 123 0 -1\n" +
            "Message 12 64 0 -1\n" +
            "Message 13 123 0 -1\n" +
            "Message 13 64 0 -1\n" +
            "Message 14 123 0 -1\n" +
            "Message 14 64 0 -1\n" +
            "Message 15 123 0 -1\n" +
            "Message 15 64 0 -1\n" +
            "Message 0 1 0 -1\n" +
            "Message 0 0 64 -1\n" +
            "Message 0 64 0 -1\n", this.log.toString());
  }

  // Tests if 2 notes sent to the receiver will be the correct messages.
  @Test
  public void testMidi2() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first message: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp.
    //note is sent to end.
    //fourth message: channel 1 is set to instrument 2.
    this.builder.addNote(0, 2, 1, 64, 72).addNote(3, 4, 2, 54, 90);
    this.model = new MusicEditorModel(this.builder);
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm, this.sequencer);
    this.midiView.makeVisible();
    assertEquals("Message 0 1 0 0\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 400000\n", this.receiver.log.toString());
  }

  // checks if 2 of the same notes are added to the model.
  @Test
  public void testMidi3() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first message: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp.
    //note is sent to end.
    //last two notes repeated.
    this.builder.addNote(0, 1, 1, 64, 72).addNote(0, 1, 1, 64, 72);
    this.model = new MusicEditorModel(this.builder);
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm, this.sequencer);
    this.midiView.makeVisible();
    assertEquals("", this.receiver.log.toString());
  }

  // checks if 2 different notes are written onto the same channel.
  @Test
  public void testMidi4() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first message: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp.
    //note is sent to end.
    //new note start and end are printed.
    this.builder.addNote(0, 1, 1, 64, 72).addNote(3, 4, 1, 65, 72);
    this.model = new MusicEditorModel(this.builder);
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm, this.sequencer);
    this.midiView.makeVisible();
    assertEquals("Message 0 1 0 0\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 200000\n", this.receiver.log.toString());
  }

  // checks if overlapping notes are printed in the correct order.
  // start of the shorter notes should come before the end of the longer note.
  // which is seen in the time stamps.
  @Test
  public void testMidi5() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first message: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp.
    //note is sent to end.
    //new note start and end are printed.
    this.builder.addNote(0, 4, 1, 64, 72).addNote(2, 3, 1, 65, 72);
    this.model = new MusicEditorModel(this.builder);
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm, this.sequencer);
    this.midiView.makeVisible();
    assertEquals("Message 0 1 0 0\n" +
                    "Message 0 64 72 0\n" +
                    "Message 0 64 72 800000\n" +
                    "Message 0 65 72 400000\n" +
                    "Message 0 65 72 600000\n", this.receiver.log.toString());
  }
}

*/
