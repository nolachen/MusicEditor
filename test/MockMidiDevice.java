import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import org.junit.Test;

import javax.sound.midi.MidiDevice;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.TreeMap;

/**
 * To test the midiview
 */
public class MockMidiDevice {
  IMusicEditorModel model;
  MusicReader reader;
  ViewModel vm;

  public void initCond() {
    this.model = new MusicEditorModel();
    this.reader = new MusicReader();
  }

  @Test
  public void testMidi() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("tempo 200000\n" +
            "note 0 2 1 64 72\n" +
            "note 0 7 1 55 70\n" +
            "note 2 4 1 62 72\n" +
            "note 4 6 1 60 71\n" +
            "note 6 8 1 62 79\n" +
            "note 8 15 1 55 79\n" +
            "note 8 10 1 64 85\n" +
            "note 10 12 1 64 78\n" +
            "note 12 15 1 64 74\n" +
            "note 16 24 1 55 77\n" +
            "note 16 18 1 62 75\n" +
            "note 18 20 1 62 77\n" +
            "note 20 24 1 62 75\n" +
            "note 24 26 1 55 79\n" +
            "note 24 26 1 64 82\n" +
            "note 26 28 1 67 84\n" +
            "note 28 32 1 67 75\n" +
            "note 32 40 1 55 78\n" +
            "note 32 34 1 64 73\n" +
            "note 34 36 1 62 69\n" +
            "note 36 38 1 60 71\n" +
            "note 38 40 1 62 80\n" +
            "note 40 48 1 55 79\n" +
            "note 40 42 1 64 84\n" +
            "note 42 44 1 64 76\n" +
            "note 44 46 1 64 74\n" +
            "note 46 48 1 64 77\n" +
            "note 48 56 1 55 78\n" +
            "note 48 50 1 62 75\n" +
            "note 50 52 1 62 74\n" +
            "note 52 54 1 64 81\n" +
            "note 54 56 1 62 70\n" +
            "note 56 64 1 52 72\n" +
            "note 56 64 1 60 73\n");
    this.model = reader.parseFile(in, new MusicEditorModel.Builder());
    this.vm = new ViewModel(this.model);
    //new MidiViewImpl(60, this.vm, )
  }


}

