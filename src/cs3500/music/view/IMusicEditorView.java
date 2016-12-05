package cs3500.music.view;

/**
 * The view interface for the music editor.
 */
public interface IMusicEditorView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Toggle whether the music is paused or not.
   */
  void togglePause();

  /**
   * Jump to the first beat of the music.
   */
  void jumpToStart();

  /**
   * Jump to the last beat of the music.
   */
  void jumpToEnd();

  /**
   * Signals this view to refresh itself.
   */
  void refresh();
}
