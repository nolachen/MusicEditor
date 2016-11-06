package cs3500.music.model;

import java.util.ArrayList;
import java.util.Comparator;
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
  private final Pitch pitch;

  /**
   * The octave of this Note.
   */
  private final int octave;

  /**
   * Integer representation of the pitch and octave
   */
  private final int pitchOctave;

  /**
   * The starting beat of this Note.
   * INVARIANT: startBeat >= 0.
   */
  private int startBeat;

  /**
   * The duration of this note in beats, with only integral durations allowed.
   * INVARIANT: duration >= 0.
   * TODO: might have to let note of duration 0's exist
   */
  private int duration;

  /**
   * the integer representation of the instrument.
   */
  private int instrument;

  /**
   * that integer representation of the volume.
   */
  private int volume;

  /**
   * Constructs a Note, while ensuring both class invariants in MIDI form.
   * @param startBeat the start beat
   * @param endBeat the end of the beat
   * @param instrument the instrument playing this note
   * @param pitchOctave the pitch combined with the octave
   * @param volume the volume of the note
   */
  public Note(int startBeat, int endBeat, int instrument, int pitchOctave, int volume) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be at least 0.");
    }
    if (startBeat < endBeat) {
      throw new IllegalArgumentException("End beat must be after the start beat.");
    }
    this.duration = endBeat - startBeat;
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be greater than 0.");
    }
    this.pitchOctave = pitchOctave;
    this.pitch = this.getPitchEnumValue(this.pitchOctave % 12);
    this.octave = this.pitchOctave / 12;
    this.startBeat = startBeat;
    this.instrument = instrument;
    this.volume = volume;
    //tODO should i store the end beat
  }

  /**
   * @param pitch the pitch
   * @param octave the octave
   * @param startBeat the startbeat
   * @param duration the duration of the note
   */
  // Constructor with a given Pitch and Octave
  public Note(Pitch pitch, int octave, int startBeat, int duration) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be at least 0.");
    }
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be greater than 0.");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.pitchOctave = this.pitch.ordinal() + 12 * this.octave;
    this.startBeat = startBeat;
    this.duration = duration;
  }

  private Pitch getPitchEnumValue(int pitch) {
    Pitch p = Pitch.C;
    while(p.ordinal() != pitch) {
      p = p.nextPitch();
    }
    return p;
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
   * Two Notes are equal if they have the same pitch, octave, duration, and start beat.
   * @param obj the object to check equality with
   * @return true if obj is equal to this note, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Note)) {
      return false;
    }
    Note that = (Note) obj;

    return (this.pitch == that.pitch) && (this.octave == that.octave) &&
            (this.startBeat == that.startBeat) && (this.duration == that.duration);
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

  public Pitch getPitch() { return this.pitch; }

  public int getOctave() { return this.octave; }

  /**
   * Getter for {@code startBeat}.
   * @return the start beat
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  public int getDuration() { return this.duration; }

  public int getEndBeat() { return this.startBeat + this.duration; }

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

/*  *//**
   * The next note that comes after this note in the chromatic scale, with the same duration and
   * start beat as this note.
   * @return the next Note
   *//*
  Note nextNote() {
    int octave = this.octave;
    if (this.pitch == Pitch.B) {
      octave += 1;
    }
    return new Note(this.pitch.nextPitch(), octave, this.startBeat, this.duration);
  }*/

  public class PitchComparator implements Comparator<Note> {

    @Override
    public int compare(Note n1, Note n2) {
      if (n1.octave == n2.octave) {
        return n1.pitch.compareTo(n2.pitch);
      }
      else {
        return n1.octave - n2.octave;
      }
    }
  }

}
