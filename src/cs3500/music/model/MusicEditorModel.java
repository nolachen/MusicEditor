package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.Set;
import java.util.TreeMap;

/**
 * This class represents a music editor model.
 */
public final class MusicEditorModel implements IMusicEditorModel {
  /**
   * A TreeMap represents the mapping between integer beats and the notes playing at that beat.
   * Class invariant: Notes will only ever be added at beats in the range [startBeat, endBeat).
   *
   * Changed in HW07 to store notes at every beat they are playing, not just the start beat.
   */
  private TreeMap<Integer, List<Note>> music;
  /**
   * The tempo of this model represented in Beats per Millisecond.
   */
  private int tempo;

  /**
   * Constructor for MusicEditorModel.
   * @param music the mapping of music
   */
  private MusicEditorModel(TreeMap<Integer, List<Note>> music, int tempo) {
    this.music = music;
    this.tempo = tempo;
  }

  /**
   * Constructor using the Builder pattern.
   * @param builder to construct this model.
   */
  public MusicEditorModel(CompositionBuilder<IMusicEditorModel> builder) {
    this.music = new TreeMap<>();
    for (Note n : builder.getMusic()) {
      this.add(n);
    }
    this.tempo = builder.getTempo();
  }

  /**
   * Default constructor for MusicEditorModel, creates an empty music to start.
   */
  public MusicEditorModel() {
    this(new TreeMap<>(), 200000);
  }

  @Override
  public void add(Note note) {
    for (int i = note.getStartBeat(); i < note.getEndBeat(); i += 1) {
      if (!this.music.containsKey(i)) {
        this.music.put(i, new ArrayList<>());
      }
      this.music.get(i).add(note);
    }
  }

  @Override
  public void remove(Note note) {
    for (int i = note.getStartBeat(); i < note.getEndBeat(); i += 1) {
      if (!this.music.containsKey(i) || !this.music.get(i).contains(note)) {
        throw new IllegalArgumentException("The note to remove doesn't exist.");
      }
      this.music.get(i).remove(note);
    }
  }

  @Override
  public void playSimultaneously(IMusicEditorModel other) {
    for (int i = 0; i < other.length(); i += 1) {
      for (ImmutableNote n : other.getNotesAtBeat(i)) {
        Note newNote = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration(),
                n.getInstrument(), n.getVolume());
        this.add(newNote);
      }
    }
  }

  @Override
  public void playConsecutively(IMusicEditorModel other) {
    int currentLastBeat = this.length();
    for (int i = 0; i < other.length(); i += 1) {
      for (ImmutableNote n : other.getNotesAtBeat(i)) {
        Note newNote = new Note(n.getPitch(), n.getOctave(), n.getStartBeat() + currentLastBeat,
                n.getDuration(), n.getInstrument(), n.getVolume());
        this.add(newNote);
      }
    }
  }

  @Override
  public List<ImmutableNote> getAllNotes() {
    List<ImmutableNote> allNotes = new ArrayList<>();

    for (int i = 0; i < this.music.lastKey(); i += 1) {
      for (Note n : this.music.get(i)) {
        if (n.getStartBeat() == i) {
          allNotes.add(new ImmutableNote(n));
        }
      }
    }

    return Collections.unmodifiableList(allNotes);
  }

  @Override
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    List<Note> notes = this.music.get(beat);
    List<ImmutableNote> notesCopy = new ArrayList<>();

    if (notes == null) {
      return new ArrayList<>();
    }

    for (Note n : notes) {
      notesCopy.add(new ImmutableNote(n));
    }
    return Collections.unmodifiableList(notesCopy);
  }

  @Override
  public int length() {
    return this.music.lastKey();
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

}
