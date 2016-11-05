package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.ViewModel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorTextView;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicEditorModel model = new MusicEditorModel();
    ViewModel viewModel = new ViewModel(model);
    //GuiViewFrame view = new GuiViewFrame();
    //MidiView midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
    IMusicEditorView textView = new MusicEditorTextView(viewModel);
    IMusicEditorController controller = new MusicEditorController(model, textView);
    controller.go();
  }
}
