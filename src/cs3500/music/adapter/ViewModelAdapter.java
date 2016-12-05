package cs3500.music.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import cs3500.music.model.ImmutableNote;
import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.INote;
import cs3500.music.provider.MusicUtils;
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
    return;
  }

  @Override
  public SortedMap<Integer, List<INote>> getAllNotesAt(int beatNum) {
    // converts a list of immutable notes at the given beat to a map of Pitch -> List<INote>
    List<ImmutableNote> notes = this.adaptee.getNotesAtBeat(beatNum);
    SortedMap<Integer, List<INote>> map = new TreeMap<>();
    for (ImmutableNote n : notes) {
      int pitch = n.getPitch().ordinal() + 12 + (n.getOctave() * 12);
      INote note = new NoteAdapter(n);

      if (map.containsKey(pitch)) {
        map.get(pitch).add(note);
      }
      else {
        ArrayList<cs3500.music.provider.INote> pitchNotes = new ArrayList<>();
        pitchNotes.add(note);
        map.put(pitch, pitchNotes);
      }
    }

    return map;
  }

  @Override
  public SortedMap<Integer, SortedMap<Integer, List<INote>>> composition() {
    SortedMap<Integer, SortedMap<Integer, List<INote>>> map = new TreeMap<>();
    for (int i = 0; i < adaptee.length(); i += 1) {
      SortedMap<Integer, List<INote>> notesAtBeat = this.getAllNotesAt(i);
      if (!notesAtBeat.isEmpty()) {
        map.put(i, this.getAllNotesAt(i))
      }
    }
    return map;
  }

  @Override
  public int getMinPitch() {
    List<String> noteRange = adaptee.getNoteRange();
    if (noteRange.isEmpty()) {
      return 0;
    }

    String minPitch = noteRange.get(0);

    return MusicUtils.toPitch();
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
