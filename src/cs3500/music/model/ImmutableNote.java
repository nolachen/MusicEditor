package cs3500.music.model;

/**
 * Creates a version of a note class that can't be altered by a user.
 */
public final class ImmutableNote implements Comparable<ImmutableNote> {
  // Note where this gets it's data from.
  private final Note note;

  /**
   * Constructor
   * @param note where this gets it's data from.
   */
  public ImmutableNote(Note note) {
    this.note = note;
  }

  /**
   * gets the pitch value.
   * @return the pitch value of this class's note.
   */
  public Pitch getPitch() {
    return note.getPitch();
  }

  /**
   * gets the octave value.
   * @return the octave value of this class's note.
   */
  public int getOctave() {
    return note.getOctave();
  }

  /**
   * gets the startbeat value.
   * @return the startBeat of this class's note.
   */
  public int getStartBeat() {
    return note.getStartBeat();
  }

  /**
   * gets the duration value.
   * @return the duration value of this class's note.
   */
  public int getDuration() {
    return note.getDuration();
  }

  /**
   * gets the instrument value.
   * @return the instrument value of this class's note.
   */
  public int getInstrument() {
    return note.getInstrument();
  }

  /**
   * gets the volume value.
   * @return the volume value of this class's note.
   */
  public int getVolume() {
    return note.getVolume();
  }

  @Override
  public int compareTo(ImmutableNote other) {
    return - other.compareToNote(this.note);
  }

  /**
   * @param other note we're comparing to.
   * @return the int comparison to the given note.
   */
  private int compareToNote(Note other) {
    return this.note.compareTo(other);
  }

  @Override
  public String toString() {
    return this.note.toString();
  }
}
