package cs3500.music.model;

import java.util.List;

/**
 * The model interface for the music editor.
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
   * A list of all Notes that are currently in this music editor.
   ** @return the list of all notes
   */
  List<ImmutableNote> getAllNotes();

  /**
   * Returns the list of notes at the given beat.
   * Changed in HW07 to get all notes playing at the beat, not just ones that start.
   * @param beat int of beat we're retrieving.
   * @return the List of ImmutableNotes at the given beat.
   */
  List<ImmutableNote> getNotesAtBeat(int beat);

  /**
   * Number of beats that are currently in this model.
   * @return the int length of this model. Gets the last beat regardless of what beat its at.
   */
  int length();

  /**
   * Gets the tempo of this model.
   * @return the tempo
   */
  int getTempo();
}
