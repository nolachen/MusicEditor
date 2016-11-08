package cs3500.music.view;

import cs3500.music.model.ViewModel;

/**
 * This class represents a factory for the multiple views that implement the IMusicEditorView
 * interface.
 */
public class MusicEditorViewFactory {

  /**
   * A static factory method that creates a view specified by the string, with the given view model.
   * @param view the type of view to create
   * @param viewModel the view model to give the view
   * @return the view
   */
  public static IMusicEditorView create(String view, ViewModel viewModel) {
    view = view.toLowerCase();

    switch (view) {
      case "console":
        return new MusicEditorTextView(viewModel);
      case "visual":
        return new MusicEditorGuiView(viewModel);
      case "midi":
        return new MidiViewImpl(viewModel);
      default:
        throw new IllegalArgumentException("Not a correct view type.");
    }
  }

}
