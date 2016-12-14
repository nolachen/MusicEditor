package cs3500.music.model;


/**
 * Represents a repeat in a piece of music.
 */
public class Repeat implements Comparable<Repeat> {
  // Invariant: end > begin

  // represents the beat that the repeat ends at
  private int end;
  // represents the beat that the repeat begins at
  private int begin;
  // represents if this repeat has been used or not.
  private boolean used;


  /**
   * Constructor.
   * @param end the end beat, where it's repeating from.
   * @param begin the begin beat, where the repeat is at.
   * @param used if this repeat has already been used.
   */
  public Repeat(int end, int begin, boolean used) {
    this.end = end;
    this.begin = begin;
    this.used = used;
  }

  /**
   * Default constructor that sets the begin beat to 0, the start of the piece.
   * @param end the end beat, where the repeat ends.
   * @param used if this repeat has already been used.
   */
  public Repeat(int end, boolean used) {
    this.begin = 0;
    this.end = end;
    this.used = used;
  }

  //TODO idk if any of these are needed.
  /**
   * Return the end beat of this repeat.
   * @return the end of this repeat.
   */
  public int getEnd() {
    return this.end;
  }

  /**
   * Return the begin beat of this repeat.
   * @return the beat index of this repeat.
   */
  public int getBegin() {
    return this.begin;
  }

  /**
   * Return whether this repeat has been used or not.
   * @return if this has been used or not.
   */
  public boolean getUsed() {
    return this.used;
  }

  /**
   *
   * @param used
   */
  public void setUsed(boolean used) {
    this.used = used;
  }

  /**
   * sets this repeat to be used again.
   */
  public void useAgain() {
    this.used = !this.used;
  }


  //todo check with nola i forget how this works
  @Override
  public int compareTo(Repeat other) {
    return this.begin - other.begin;
  }
}
