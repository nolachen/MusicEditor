package cs3500.music.model;

/**
 * This enumeration represents the 12 pitches in the chromatic scale.
 * The chromatic scale contains the pitches C, C#, D, D#, E, F, F#, G, G#, A, A#, B.
 */
public enum Pitch implements IPitch {
  C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B;

  /**
   * Returns a string representation of this pitch.
   * @return the string
   */
  @Override
  public String toString() {
    switch (this) {
      case C_SHARP: return "C#";
      case D_SHARP: return "D#";
      case F_SHARP: return "F#";
      case G_SHARP: return "G#";
      case A_SHARP: return "A#";
      default: return this.name();
    }
  }

  @Override
  public Pitch nextPitch() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  @Override
  public int getOrdinal() {
    return this.ordinal();
  }
}
