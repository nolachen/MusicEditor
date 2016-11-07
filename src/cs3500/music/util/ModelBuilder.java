package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Marina on 11/6/2016.
 */
public class ModelBuilder implements CompositionBuilder {
  private ArrayList<Note> music;
  private int tempo;

  // constructor of builder
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

  //TODO
  @Override
  public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument, int pitch, int volume) {
    Note note = new Note(start, end, instrument, pitch, volume);
    music.add(note);
    return this;
  }

  @Override
  public ArrayList<Note> getMusic() {
    return this.music;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }
}
