package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.ModelBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGuiView;
import cs3500.music.view.MusicEditorTextView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicEditorModel model = new MusicEditorModel();

/*    Note g3b0 = new Note(Pitch.A, 5, 0, 7, 1, 64);
    Note c4b4 = new Note(Pitch.C, 4, 4, 2, 1, 64);
    Note g3b16 = new Note(Pitch.G, 3, 16, 10, 1, 64);
    Note g3b24 = new Note(Pitch.G, 3, 24, 2, 1, 64);

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);*/
    IMusicEditorModel modelBuild = new MusicEditorModel(new ModelBuilder());
    ModelBuilder build = new ModelBuilder();
    // TODO fix the file path
    FileReader reader = new FileReader("C:\\Users\\Marina\\Documents\\GitHub\\MusicEditor\\df-ttfaf.txt");
    MusicReader musicReader = new MusicReader();
    musicReader.parseFile(reader, build);
    model = new MusicEditorModel(build);
    ViewModel viewModel = new ViewModel(model);

    //MusicEditorGuiView view = new MusicEditorGuiView();
    //MidiView midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
    IMusicEditorView textView = new MusicEditorTextView(viewModel);
    IMusicEditorView guiView = new MusicEditorGuiView(viewModel);
    IMusicEditorView midiView = new MidiViewImpl(viewModel);
    IMusicEditorController controller = new MusicEditorController(model, midiView);

    controller.go();
  }
}
