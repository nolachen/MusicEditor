package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;

import cs3500.music.model.INote;
import cs3500.music.model.MusicEditorModel;

import cs3500.music.model.Note;

import java.util.ArrayList;

/**
 * Builder for the music editor model.
 */
public class ModelBuilder implements CompositionBuilder<IMusicEditorModel> {
  // list of notes that have to be added to the model.
  private ArrayList<INote> music;
  // tempo of the model.
  private int tempo;

  /**
   * constructor of builder.
   */
  public ModelBuilder() {
    // arbitrary tempo
    this.tempo = 200000;
    this.music = new ArrayList<>();
  }

  @Override
  public IMusicEditorModel build() {
    return new MusicEditorModel(this);
  }

  @Override
  public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
    this.tempo = tempo;
    return this;
  }

  @Override
  public CompositionBuilder<IMusicEditorModel> addNote(int s, int e, int i, int p, int v) {
    INote note = new Note(s, e, i, p, v);
    music.add(note);
    return this;
  }

  @Override
  public ArrayList<INote> getMusic() {
    return this.music;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }
}
