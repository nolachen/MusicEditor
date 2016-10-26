package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This class represents a musical note.
 */
public class Note implements Comparable<Note> {
  /**
   * The pitch of this Note.
   */
  private Pitch pitch;

  /**
   * The octave of this Note.
   */
  private int octave;

  /**
   * The starting beat of this Note.
   * INVARIANT: startBeat >= 0.
   */
  private int startBeat;

  /**
   * The duration of this note in beats, with only integral durations allowed.
   * INVARIANT: duration > 0.
   */
  private int duration;

  /**
   * Constructs a Note, while ensuring both class invariants.
   * @param pitch the pitch
   * @param octave the octave
   * @param startBeat the start beat
   * @param duration the duration in beats
   */
  public Note(Pitch pitch, int octave, int startBeat, int duration) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be at least 0.");
    }
    if (duration <= 0) {
      throw new IllegalArgumentException("Duration must be greater than 0.");
    }

    this.pitch = pitch;
    this.octave = octave;
    this.startBeat = startBeat;
    this.duration = duration;
  }

  /**
   * The string representation of this {@link Note}, which is its {@code pitch} and {@code octave}
   * number.
   * For example, "C4" or "D#5".
   * @return String representation of this note
   */
  @Override
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  /**
   * Determines whether this {@link Note} is equal to the given object.
   * Two Notes are equal if they have the same pitch and octave.
   * @param obj the object to check equality with
   * @return true if obj is equal to this note, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Note)) {
      return false;
    }
    Note that = (Note) obj;

    return (this.pitch == that.pitch) && (this.octave == that.octave);
  }

  /**
   * Overrides the hashcode for this {@link Note}.
   * @return the new hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave, this.duration);
  }

  /**
   * Compares this {@link Note} with the given {@link Note}, on the chromatic scale
   * (ignoring {@code startBeat} and {@code duration})
   * Returns a negative number if this Note comes before the given Note in the chromatic scale.
   * Returns 0 if this Note and the given Note are the same octave and pitch.
   * Returns a positive number if this Note comes after the given Note in the chromatic scale.
   * @param other the Note to compare this Note with
   * @return the int comparison result as specified
   */
  @Override
  public int compareTo(Note other) {
    if (this.octave == other.octave) {
      return this.pitch.compareTo(other.pitch);
    }
    else {
      return this.octave - other.octave;
    }
  }

  /**
   * Returns a text rendering of this note at the given beat.
   * Each note-head is rendered as an "  X  ", and each note-sustain is rendered as "  |  ".
   * When a note has a duration of 0, five spaces are rendered (as "     ").
   * @param beat the beat at which this note is rendered
   * @return the String rendering of this note
   */
  String render(int beat) {
    if (beat == this.startBeat) {
      return "  X  ";
    }
    else if ((beat > this.startBeat) && (beat < (this.startBeat + this.duration))) {
      return "  |  ";
    }
    else {
      throw new IllegalArgumentException("This note is not played at the given beat.");
    }
  }

  /**
   * Getter for {@code startBeat}.
   * @return the start beat
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * Setter for {@code startBeat}. Ensures that the invariant is held (startBeat >= 0).
   * @param startBeat the new start beat
   */
  public void setStartBeat(int startBeat) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be at least 0.");
    }
    this.startBeat = startBeat;
  }

  /**
   * The next note that comes after this note in the chromatic scale, with the same duration and
   * start beat as this note.
   * @return the next Note
   */
  Note nextNote() {
    int octave = this.octave;
    if (this.pitch == Pitch.B) {
      octave += 1;
    }
    return new Note(this.pitch.nextPitch(), octave, this.startBeat, this.duration);
  }

  /**
   * Adds this note to the given TreeMap.
   * Makes sure to map all beats that play this note to this note.
   * @param music the mapping to add this note to
   */
  void addTo(TreeMap<Integer, List<Note>> music) {
    for (int i = this.startBeat; i < (this.startBeat + this.duration); i += 1) {
      List<Note> currentNotes = music.get(i);
      if (currentNotes == null) {
        currentNotes = new ArrayList<>();
        music.put(i, currentNotes);
      }
      currentNotes.add(this);
    }
  }

  /**
   * Removes this note from the given TreeMap. Throws an exception if this note is not found.
   * Makes sure to remove this note from all beats where it is played.
   * @param music the mapping to remove from
   */
  void removeFrom(TreeMap<Integer, List<Note>> music) {
    for (int i = this.startBeat; i < (this.startBeat + this.duration); i += 1) {
      List<Note> currentNotes = music.get(i);
      if ((currentNotes == null) || !currentNotes.contains(this)) {
        throw new IllegalArgumentException("The note to remove doesn't exist.");
      }
      currentNotes.remove(this);
    }
  }
}
