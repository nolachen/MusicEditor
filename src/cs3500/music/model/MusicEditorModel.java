package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.TreeMap;

/**
 * This class represents a music editor model.
 */
public final class MusicEditorModel implements IMusicEditorModel {
  /**
   * A TreeMap represents the mapping between integer beats and the notes playing at that beat.
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
    int beat = note.getStartBeat();
    List<Note> currentNotes = this.music.get(beat);
    if (currentNotes == null) {
      currentNotes = new ArrayList<>();
      music.put(beat, currentNotes);
    }
    currentNotes.add(note);
  }

  @Override
  public void remove(Note note) {
    int beat = note.getStartBeat();
    List<Note> currentNotes = music.get(beat);
    if ((currentNotes == null) || !currentNotes.contains(note)) {
      throw new IllegalArgumentException("The note to remove doesn't exist.");
    }
    currentNotes.remove(note);
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

    for (List<Note> list : this.music.values()) {
      for (Note n : list) {
        allNotes.add(new ImmutableNote(n));
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
    if (this.music.isEmpty()) {
      return 0;
    }
    int lastBeat = this.music.lastKey();
    for (List<Note> list : this.music.values()) {
      for (Note n : list) {
        if (n.getEndBeat() > lastBeat) {
          lastBeat = n.getEndBeat();
        }
      }
    }
    return lastBeat;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

}
