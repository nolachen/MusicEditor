import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.ViewModel;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import org.junit.Test;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Created by Marina on 11/6/2016.
 */
public class MockMidiTest {
  IMusicEditorModel model;
  MusicReader reader;
  ViewModel vm;
  MockReceiver receiver;
  StringBuilder build;
  MockMidiDevice device;
  MidiViewImpl midiView;

  public void initCond() {
    this.model = new MusicEditorModel();
    this.reader = new MusicReader();
    this.build = new StringBuilder();
    this.midiView = new MidiViewImpl(vm);
  }

/*  @Test
  public void testMidi() {
    StringBuffer out = new StringBuffer();
    File file = new File("mary-little-lamb.txt");
    Scanner scan;
    try {
      scan = new Scanner(file);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Not a valid file");
    }
    this.model = reader.parseFile(in, new MusicEditorModel.Builder());
    this.vm = new ViewModel(this.model);
    System.out.println(model.getTempo());
    System.out.println(vm.getNoteRange());
    MidiViewImpl view = new MidiViewImpl(this.vm);
    view.makeVisible();
  }*/
}
