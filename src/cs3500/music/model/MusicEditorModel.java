package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * This class represents a music editor model.
 * TODO : ask TA if we should be storing notes at every beat lol
 * TODO: how test for users shouldnt be able to mutate model
 */
public class MusicEditorModel implements IMusicEditorModel {
  /**
   * A TreeMap represents the mapping between integer beats and the notes playing at that beat.
   */
  private TreeMap<Integer, List<Note>> music;

  /**
   * Constructor for MusicEditorModel.
   * @param music the mapping of music
   */
  private MusicEditorModel(TreeMap<Integer, List<Note>> music) {
    this.music = music;
  }

  /**
   * Default constructor for MusicEditorModel, creates an empty music to start.
   */
  public MusicEditorModel() {
    this(new TreeMap<>());
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
        Note newNote = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration());
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
                n.getDuration());
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

  /**
   * The inclusive list of all notes from the given low note to the high note.
   * @param low lowest note of range
   * @param high highest note of range
   * @return the list of notes between the given ones
   */
  private List<Note> getNoteRange(Note low, Note high) {
    List<Note> notes = new ArrayList<>();
    Note current = low;

    while (current.compareTo(high) <= 0) {
      notes.add(current);
      current = current.nextNote();
    }

    return notes;
  }



}
