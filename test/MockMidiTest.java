import cs3500.music.model.*;

import cs3500.music.util.CompositionBuilder;

import cs3500.music.util.ModelBuilder;

import cs3500.music.view.MidiViewImpl;

import org.junit.Before;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Uses a mock midi device and a mock receiver to test midiview.
 */
public class MockMidiTest {
  IMusicEditorModel model;
  ViewModel vm;
  MockReceiver receiver;
  StringBuilder log;
  MockMidiDevice device;
  MidiViewImpl midiView;
  CompositionBuilder builder;

  @Before
  public void initCond() {
    this.model = new MusicEditorModel();
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm);
    this.device = new MockMidiDevice();
    this.midiView = new MidiViewImpl(vm, this.device);
    this.builder = new ModelBuilder();

    try {
      this.receiver = this.device.getReceiver();
    }
    catch (Exception e) {
      throw new IllegalStateException("Invalid midi device");
    }
    this.log = this.receiver.log;
  }

  // tests if the midiview initially has an empty sequence of commands.
  @Test
  public void testMidi() {
    StringBuffer out = new StringBuffer();
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
  }

  // Tests if the receiver recieves all appropriate messages representing the models notes.
  @Test
  public void testMidi1() {
    this.midiView.makeVisible();
    assertEquals(this.log.toString(), "");
    //first note: channel 0 is set to instrument 1.
    //note is sent to start. note = channel pitch volume time-stamp
    //note is sent to end.
    this.builder.addNote(0, 2, 1, 64, 72);
    this.model = new MusicEditorModel(this.builder);
    this.vm = new ViewModel(this.model);
    this.midiView = new MidiViewImpl(this.vm, this.device);
    this.midiView.makeVisible();
    assertEquals(this.receiver.log.toString(), "Message 0 1 0 -1\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 400000\n");
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
    this.midiView = new MidiViewImpl(this.vm, this.device);
    this.midiView.makeVisible();
    assertEquals(this.receiver.log.toString(), "Message 0 1 0 -1\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 400000\n" +
            "Message 1 2 0 -1\n" +
            "Message 1 54 90 600000\n" +
            "Message 1 54 90 800000\n");
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
    this.midiView = new MidiViewImpl(this.vm, this.device);
    this.midiView.makeVisible();
    assertEquals(this.receiver.log.toString(), "Message 0 1 0 -1\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 200000\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 200000\n");
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
    this.midiView = new MidiViewImpl(this.vm, this.device);
    this.midiView.makeVisible();
    assertEquals(this.receiver.log.toString(), "Message 0 1 0 -1\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 200000\n" +
            "Message 0 65 72 600000\n" +
            "Message 0 65 72 800000\n");
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
    this.midiView = new MidiViewImpl(this.vm, this.device);
    this.midiView.makeVisible();
    assertEquals(this.receiver.log.toString(), "Message 0 1 0 -1\n" +
            "Message 0 64 72 0\n" +
            "Message 0 64 72 800000\n" +
            "Message 0 65 72 400000\n" +
            "Message 0 65 72 600000\n");
  }
}
