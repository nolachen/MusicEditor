package cs3500.music.model;

import java.util.List;

/**
 * This is the interface of the music editor model.
 */
public interface IMusicEditorModel {
  /**
   * Adds the given note to this model.
   * @param note the note to add
   */
  void add(Note note);

  /**
   * Removes the given note from this model.
   * @param note the note to remove
   */
  void remove(Note note);

  /**
   * Combines this music model with the given music model such that
   * they play simultaneously.
   * @param other model to add to this one
   */
  void playSimultaneously(IMusicEditorModel other);

  /**
   * Combines this music model with the given music model such that
   * they play consecutively.
   * @param other model to play after this one
   */
  void playConsecutively(IMusicEditorModel other);

  /**
   * Return the present state of the model as a string, formatted with the column of beats on the
   * left, and the range of notes on the top. The note-heads are represented with X and the
   * note-sustains are represented as |.
   * @return String representation of the music model
   */
  //String getTextRendering();

  /**
   * A list of all Notes that are currently in this music editor, in chromatic order.
   * @return the list of all notes
   */
  List<ImmutableNote> getAllNotes();

  /**
   * Returns the list of notes that begin at the given beat.
   * @param beat
   * @return
   */
  List<ImmutableNote> getNotesAtBeat(int beat);

  /**
   * Number of beats that are currently in this model.
   * @return
   */
  int length();
}
