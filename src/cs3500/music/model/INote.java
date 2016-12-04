package cs3500.music.model;

/**
 * This interface represents a musical note.
 */
public interface INote extends Comparable<INote> {

  /**
   * gets the instrument value of this note.
   * @return the instrument value of this Note.
   */
  int getInstrument();

  /**
   * gets the volume value of this note.
   * @return the volume value of this Note.
   */
  int getVolume();

  /**
   * gets the pitch value of this note.
   * @return returns the pitch value of this note.
   */
  Pitch getPitch();

  /**
   * gets the octave value of this note.
   * @return returns the octave value of this note.
   */
  int getOctave();

  /**
   * Getter for {@code startBeat}.
   * @return the start beat.
   */
  int getStartBeat();

  /**
   * gets the duration of this note.
   * @return returns the duration of this note.
   */
  int getDuration();

  /**
   * gets the end beat of this note.
   * @return returns the end beat.
   */
  int getEndBeat();

}
