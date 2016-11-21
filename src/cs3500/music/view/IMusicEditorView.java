package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * The view interface for the music editor.
 */
public interface IMusicEditorView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  void togglePause();

  void jumpToStart();

  void jumpToEnd();

  //void pausePlayback();

  //void resumePlayback();

  //TODO: just skip to beginning?
  //void restartPlayback();

}
