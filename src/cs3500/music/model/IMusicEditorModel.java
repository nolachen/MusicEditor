package cs3500.music.model;

/**
 * This is the interface of the music editor model.
 */
public interface IMusicEditorModel {
  void add(Note note);

  void edit(int beat, Note oldNote, Note newNote);

  void remove(Note note);

  /**
   * Creates a new music model that combines this music model with the given music model such that
   * they play simultaneously.
   * @param other
   * @return
   */
  IMusicEditorModel playSimultaneously(IMusicEditorModel other);

  /**
   * Creates a new music model that combines this music model with the given music model such that
   * they play consecutively.
   * @param other
   * @return
   */
  IMusicEditorModel playConsecutively(IMusicEditorModel other);

  /**
   * Return the present state of the model as a string, formatted as follows:
   * TODO
   * @return String representation of the music model
   */
  String getTextRendering();
}
