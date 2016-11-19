package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

// TODO this import will be gone later i just didnt like the bug from pause not being
// in the other views
import cs3500.music.view.MidiViewImpl;

/**
 * this is bad and wrong but i didnt know what to do
 */
public class pauseRunnable implements Runnable {

  //TODO fix to be IView
  private final MidiViewImpl view;

  public pauseRunnable(MidiViewImpl view) {
    this.view = view;
  }

  @Override
  public void run() {
    this.view.pause();
  }
}
