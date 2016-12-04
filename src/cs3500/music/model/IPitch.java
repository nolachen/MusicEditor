package cs3500.music.model;

/**
 * This interface represents a pitch in a musical scale.
 */
public interface IPitch {
  /**
   * The next pitch in the chromatic scale. The next pitch of B is C.
   * @return the next pitch.
   */
  Pitch nextPitch();
}
