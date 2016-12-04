package cs3500.music.provider;

/**
 * The INote. [Need more explanation and design choice]
 */
public interface INote {

  /**
   * Gets the NoteName.
   *
   * @return The NoteName.
   */
  public NoteName getNoteName();

  /**
   * Gets the octave.
   *
   * @return The octave
   */
  public int getOctave();

  /**
   * Gets the startDuration.
   *
   * @return The startDuration.
   */
  public int getStartDuration();

  /**
   * Gets the beats.
   *
   * @return The beats.
   */
  public int getBeat();

  /**
   * Gets the channel.
   *
   * @return The channel
   */
  public int getChannel();

  /**
   * Gets the volume.
   *
   * @return The volume.
   */
  public int getVolume();

  /**
   * Tells whether this is an ViewNote.
   *
   * @return True if this is an ViewNote, false otherwise.
   */
  public boolean isViewNote();


}
