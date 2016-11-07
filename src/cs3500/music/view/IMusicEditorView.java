package cs3500.music.view;

import java.util.List;

import cs3500.music.model.ImmutableNote;

/**
 * Created by nolachen on 11/2/16.
 */
public interface IMusicEditorView {

  /**
   * TODO Make the view visible.
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
