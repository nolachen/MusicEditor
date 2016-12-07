package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.TreeMap;

/**
 * This class represents a music editor model.
 * TODO does this have to be final
 */
public class MusicEditorModel implements IMusicEditorModel {
  /**
   * A TreeMap represents the mapping between integer beats and the notes playing at that beat.
   * INVARIANT: Notes will only ever be added at beats in the range [startBeat, endBeat).
   * INVARIANT: The list of notes at a beat will always be sorted by ascending start beat.
   * Changed in HW07 to store notes at every beat they are playing, not just the start beat.
   */
  protected TreeMap<Integer, List<INote>> music;
  /**
   * The tempo of this model represented in Beats per Millisecond.
   */
  protected int tempo;

  /**
   * Constructor for MusicEditorModel.
   * @param music the mapping of music
   */
  protected MusicEditorModel(TreeMap<Integer, List<INote>> music, int tempo) {
    this.music = music;
    this.tempo = tempo;
  }

  /**
   * Constructor using the Builder pattern.
   * @param builder to construct this model.
   */
  public MusicEditorModel(CompositionBuilder<IMusicEditorModel> builder) {
    this.music = new TreeMap<>();
    for (INote n : builder.getMusic()) {
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
  public void add(INote note) {
    // add the note at all the beats at which it is playing
    // a do-while loop is necessary to ensure that notes of duration 0 are added

    int i = note.getStartBeat();
    do {

      // if there are no notes at the beat, create a new empty list of notes
      if (!this.music.containsKey(i)) {
        this.music.put(i, new ArrayList<>());
      }

      // add the note at the correct index to maintain sorted order by ascending start beat
      int indexToAdd = 0;
      List<INote> currentNotes = this.music.get(i);
      for (int j = 0; j < currentNotes.size(); j += 1) {
        if (i >= currentNotes.get(j).getStartBeat()) {
          indexToAdd = j;
          break;
        }
      }

      // actually add the note at the correct index, and increase the counter
      this.music.get(i).add(indexToAdd, note);
      i += 1;
    } while (i < note.getEndBeat());
  }

  @Override
  public void remove(INote note) {
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
        if (n.getStartBeat() == i) {
          INote newNote = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration(),
                  n.getInstrument(), n.getVolume());
          this.add(newNote);
        }
      }
    }
  }

  @Override
  public void playConsecutively(IMusicEditorModel other) {
    int currentLastBeat = this.length();
    for (int i = 0; i < other.length(); i += 1) {
      for (ImmutableNote n : other.getNotesAtBeat(i)) {
        if (n.getStartBeat() == i) {
          INote newNote = new Note(n.getPitch(), n.getOctave(), n.getStartBeat() + currentLastBeat,
                  n.getDuration(), n.getInstrument(), n.getVolume());
          this.add(newNote);
        }
      }
    }
  }

  @Override
  public List<ImmutableNote> getAllNotes() {
    List<ImmutableNote> allNotes = new ArrayList<>();

    if (this.music.isEmpty()) {
      return allNotes;
    }
    for (int i = 0; i < this.music.lastKey(); i += 1) {
      if (this.music.containsKey(i)) {
        for (INote n : this.music.get(i)) {
          if (n.getStartBeat() == i) {
            allNotes.add(new ImmutableNote(n));
          }
        }
      }
    }

    Collections.sort(allNotes);

    return Collections.unmodifiableList(allNotes);
  }

  @Override
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    List<INote> notes = this.music.get(beat);
    List<ImmutableNote> notesCopy = new ArrayList<>();

    if (notes == null) {
      return new ArrayList<>();
    }

    for (INote n : notes) {
      notesCopy.add(new ImmutableNote(n));
    }
    return Collections.unmodifiableList(notesCopy);
  }

  @Override
  public int length() {
    if (this.music.isEmpty()) {
      return 0;
    }
    return this.music.lastKey();
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

}
