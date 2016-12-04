package cs3500.music.model;

/**
 * Creates a version of a {@link INote} that can't be altered by a user.
 */
public final class ImmutableNote implements INote {
  // Note where this gets its data from.
  private final INote note;

  /**
   * Constructor
   * @param note where this gets its data from.
   */
  public ImmutableNote(INote note) {
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
  @Override
  public int getDuration() {
    return note.getDuration();
  }

  /**
   * gets the end beat.
   * @return end beat of this class's note.
   */
  @Override
  public int getEndBeat() {
    return note.getEndBeat();
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

  /**
   * Compares this class's note to the given note.
   * @param other the INote to compare
   * @return the int comparison value
   */
  @Override
  public int compareTo(INote other) {
    return this.note.compareTo(other);
  }

  /**
   * String representation of this class's note.
   * @return the string
   */
  @Override
  public String toString() {
    return this.note.toString();
  }
}
