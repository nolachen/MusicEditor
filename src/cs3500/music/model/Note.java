package cs3500.music.model;

import java.util.Objects;

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
   * The starting beat of this Note.
   * INVARIANT: startBeat >= 0.
   */
  private int startBeat;

  /**
   * The duration of this note in beats, with only integral durations allowed.
   * INVARIANT: duration >= 0.
   */
  private int duration;

  /**
   * The integer representation of the instrument.
   */
  private int instrument;

  /**
   * that integer representation of the volume.
   */
  private int volume;

  /**
   * Constructor for Note given all the fields.
   * @param pitch the pitch
   * @param octave the octave number
   * @param startBeat the start beat
   * @param duration the duration
   * @param instrument the instrument
   * @param volume the volume
   */
  public Note(Pitch pitch, int octave, int startBeat, int duration, int instrument, int volume) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be at least 0.");
    }
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be at least 0.");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.startBeat = startBeat;
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Constructs a Note, while ensuring both class invariants in MIDI form.
   * @param startBeat the start beat.
   * @param endBeat the end of the beat.
   * @param instrument the instrument playing this note.
   * @param pitchOctave the pitch combined with the octave, in the range [0, 127] where 60 is
   *                    middle C (C4)
   * @param volume the volume of the note.
   */
  public Note(int startBeat, int endBeat, int instrument, int pitchOctave, int volume) {
    this(Pitch.values()[pitchOctave % 12], (pitchOctave / 12) - 1, startBeat,
            endBeat - startBeat, instrument, volume);
  }

  /**
   * Convenience constructor with a given pitch, octave, start beat, and duration.
   * Sets the instrument to 1 and the volume to 127.
   * @param pitch the pitch
   * @param octave the octave
   * @param startBeat the start beat
   * @param duration the duration of the note
   */
  public Note(Pitch pitch, int octave, int startBeat, int duration) {
    this(pitch, octave, startBeat, duration, 1, 127);
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
   * gets the instrument value of this note.
   * @return the instrument value of this Note.
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * gets the volume value of this note.
   * @return the volume value of this Note.
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Determines whether this {@link Note} is equal to the given object.
   * Two Notes are equal if they have the same fields.
   * @param obj the object to check equality with
   * @return true if obj is equal to this note, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Note)) {
      return false;
    }
    Note that = (Note) obj;

    return (this.pitch == that.pitch)
            && (this.octave == that.octave)
            && (this.startBeat == that.startBeat)
            && (this.duration == that.duration)
            && (this.instrument == that.instrument)
            && (this.volume == that.volume);
  }

  /**
   * Overrides the hashcode for this {@link Note}.
   * @return the new hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave, this.duration,
            this.instrument, this.volume);
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
   * returns the pitch value.
   * @return returns the pitch value of this note.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * returns the octave value.
   * @return returns the octave value of this note.
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Getter for {@code startBeat}.
   * @return the start beat.
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * gets the duration of this note.
   * @return returns the duration of this note.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * gets the end beat of this note.
   * @return returns the end beat.
   */
  public int getEndBeat() {
    return this.startBeat + this.duration;
  }

}
