package cs3500.music.provider;

/**
 * The representation just for printing.
 */
public enum NotePlay {
  /**
   * NOTE_PLAY representing then not that started to play.
   */
  NOTE_PLAY("  X  "),

  /**
   * As long as the note continued to play the the pipe will be there.
   */
  NOTE_SUSTAIN("  |  "),

  /**
   * The 5 empty spaces represent the note is at rest.
   */
  NOTE_REST("     ");

  private final String string;

  /**
   * Construct NotePlay.
   *
   * @param string the string
   */
  NotePlay(String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return this.string;
  }

}
