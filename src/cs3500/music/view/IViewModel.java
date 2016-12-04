package cs3500.music.view;

import java.util.List;

import cs3500.music.model.INote;
import cs3500.music.model.ImmutableNote;

/**
 * The view model interface for the music editor.
 * A view model that is basically a read-only model. Gives the View access to the model's.
 * information without allowing the model to be directly accessed.
 * This interface was added in HW07 to decouple the views from a concrete view model class.
 */
public interface IViewModel {

  /**
   * Returns the list of notes at the given beat.
   * @param beat int representation of the beat we're retrieving notes at.
   * @return the list of Immutable notes at the given beat.
   */
  List<ImmutableNote> getNotesAtBeat(int beat);

  /**
   * A list of all Notes that are currently in the model.
   * @return the list of all notes
   */
  List<ImmutableNote> getAllNotes();

  /**
   * Number of beats that are currently in this model.
   * @return int length of the model.
   */
  int length();

  /**
   * Tempo of the model in microseconds per beat.
   * @return int tempo of the model.
   */
  int getTempo();

  /**
   * Returns a list of the pitches and their octaves in the range of this model.
   * @return a list of Strings of the range of notes.
   */
  List<String> getNoteRange();

  /**
   * Stores the given selected note in this viewmodel.
   * @param note the note to store
   */
  void setSelectedNote(INote note);

  /**
   * Retrieve the selected note.
   * @return the stored note
   */
  INote getSelectedNote();
}
