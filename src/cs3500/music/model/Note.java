package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This class represents a musical note.
 */
public class Note implements Comparable<Note> {
  private Pitch pitch;
  private int octave;
  private int startBeat;

  /**
   * The duration of this note in beats, with only integral durations allowed.
   */
  private int duration; // TODO maybe make invariant that its duration is greater than 0?

  public Note(Pitch pitch, int octave, int startBeat, int duration) {
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
    if (this.duration == 0) {
      return "     ";
    }
    else if (beat == this.startBeat) {
      return "  X  ";
    }
    else if ((beat > this.startBeat) && (beat < (this.startBeat + this.duration))) {
      return "  |  ";
    }
    else {
      throw new IllegalArgumentException("This note is not played at the given beat.");
    }
  }

  public int getStartBeat() {
    return this.startBeat;
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

  void removeFrom(TreeMap<Integer, List<Note>> music) {
    for (int i = this.startBeat; i < (this.startBeat + this.duration); i += 1) {
      List<Note> currentNotes = music.get(i);
      if ((currentNotes == null) || !currentNotes.contains(this)) {
        throw new IllegalArgumentException("The note to remove doesn't exist.");
      }
      currentNotes.remove(i);
    }

  }
}
