package cs3500.music.view;

/**
 * The view interface for the music editor.
 */
public interface IMusicEditorView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  //void setNotes(List<ImmutableNote> notes);

  //void setNotesAtBeat(int beat, List<Note> notes);


  /**
   * TODO
   * @param error
   */
  //void showErrorMessage(String error);

  /**
   * TODO all of the refreshes
   */
  void refresh();

}
