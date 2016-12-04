package cs3500.music.view;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import cs3500.music.model.ViewModelAdapter;
import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.INote;

/**
 * This class represents a factory for the multiple views that implement the IMusicEditorView
 * interface.
 */
public class MusicEditorViewFactory {

  /**
   * A static factory method that creates a view specified by the string, with the given view model.
   * The types of view are "console", "visual", and "midi".
   * @param view the type of view to create
   * @param viewModel the view model to give the view
   * @return the view
   */
  public static IMusicEditorView create(String view, IViewModel viewModel) {
    view = view.toLowerCase();

    switch (view) {
      case "console":
        return new ConsoleViewImpl(viewModel, System.out);
      case "visual":
        return new GuiViewImpl(viewModel);
      case "midi":
        return new MidiViewImpl(viewModel);
      case "composite":
        return new CompositeViewImpl(new GuiViewImpl(viewModel), new MidiViewImpl(viewModel),
                viewModel);
      default:
        throw new IllegalArgumentException("Not a correct view type.");
    }
  }

}
