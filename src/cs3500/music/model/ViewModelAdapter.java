package cs3500.music.model;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.INote;
import cs3500.music.view.IViewModel;

/**
 * TODO
 * Implements the provided target model, and has the adaptee: our model.
 */

public class ViewModelAdapter implements IBasicMusicEditor<INote> {
  private final IViewModel adaptee;

  public ViewModelAdapter(IViewModel adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public boolean add(INote note) {
    return false;
  }

  @Override
  public boolean remove(INote note) {
    return false;
  }

  @Override
  public boolean edit(INote note, int duration, int volume) {
    return false;
  }

  @Override
  public void merge(IBasicMusicEditor<INote> piece, boolean isConsecutive) {

  }

  @Override
  public SortedMap<Integer, List<INote>> getAllNotesAt(int beatNum) {
    return null;
  }

  @Override
  public SortedMap<Integer, SortedMap<Integer, List<INote>>> composition() {
    return null;
  }

  @Override
  public int getMinPitch() {
    return 0;
  }

  @Override
  public int getMaxPitch() {
    return 0;
  }

  @Override
  public int getTempo() {
    return 0;
  }

  @Override
  public int getLastStartBeat() {
    return 0;
  }

  @Override
  public int getLastBeat() {
    return 0;
  }

  @Override
  public boolean isViewEditor() {
    return false;
  }
}
