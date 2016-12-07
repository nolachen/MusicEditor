package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

/**
 * Represents a repeat in a piece of music.
 */
public class Repeat implements Comparable{
  // represents the beat that the repeat is repeated from.
  private int head;
  // represents the beat that the repeat is at.
  private int tail;
  // represents if this repeat has been used or not.
  private boolean used;


  /**
   * Constructor.
   * @param head the head beat, where it's repeating from.
   * @param tail the tail beat, where the repeat is at.
   * @param used if this repeat has already been used.
   */
  public Repeat(int head, int tail, boolean used) {
    this.head = head;
    this.tail = tail;
    this.used = used;
  }

  /**
   * Default constructor that sets the head beat to 0, the start of the piece.
   * @param tail the tail beat, where the repeat is at.
   * @param used if this repeat has already been used.
   */
  public Repeat(int tail, boolean used) {
    this.head = 0;
    this.tail = tail;
    this.used = used;
  }

  //TODO idk if any of these are needed.
  /**
   * @return the head of this repeat.
   */
  public int getHead() {
    return this.head;
  }

  /**
   * @return the beat index of this repeat.
   */
  public int getTail() {
    return this.tail;
  }

  /**
   * @return if this has been used or not.
   */
  public boolean hasBeenUsed() {
    return this.used;
  }

  /**
   * sets this repeat to be used again.
   */
  public void useAgain() {
    this.used = !this.used;
  }


  //todo check with nola i forget how this works
  @Override
  public int compareTo(Object other) {
    return this.tail - ((Repeat) other).tail;
  }
}
