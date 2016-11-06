package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.ViewModel;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicEditorGuiView;
import cs3500.music.view.MusicEditorTextView;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicEditorModel model = new MusicEditorModel();
    ViewModel viewModel = new ViewModel(model);
    Note g3b0 = new Note(Pitch.G, 3, 0, 7);
    Note c4b4 = new Note(Pitch.C, 4, 4, 2);
    Note g3b16 = new Note(Pitch.G, 3, 16, 10);
    Note g3b24 = new Note(Pitch.G, 3, 24, 2);

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);
    //MusicEditorGuiView view = new MusicEditorGuiView();
    //MidiView midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
    IMusicEditorView textView = new MusicEditorTextView(viewModel);
    IMusicEditorView guiView = new MusicEditorGuiView(viewModel);
    IMusicEditorController controller = new MusicEditorController(model, guiView);



    controller.go();
  }
}
