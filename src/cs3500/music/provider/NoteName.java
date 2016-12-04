package cs3500.music.provider;

import cs3500.music.provider.Utils;

/**
 * Names of all base note in any octave.
 */
public enum NoteName {

  /**
   * These are letter that an be a note.
   */
  C("C ", 12),
  CX("C♯", 13),
  D("D ", 14),
  DX("D♯", 15),
  E("E ", 16),
  F("F ", 17),
  FX("F♯", 18),
  G("G ", 19),
  GX("G♯", 20),
  A("A ", 21),
  AX("A♯", 22),
  B("B ", 23);

  private final String noteName;
  private final int base;

  /**
   * Construct a static class of noteName.
   *
   * @param noteName name of the note
   */
  NoteName(String noteName, int base) {
    this.noteName = noteName;
    this.base = base;
  }

  @Override
  public String toString() {
    return this.noteName;
  }

  /**
   * Getter of integer representation of note.
   *
   * @return int rep of note
   */
  public int toInt() {
    return this.base;
  }

  /**
   * The enum NoteName.
   *
   * @param string string representation of NoteName
   * @return NoteName
   */
  public static NoteName toNoteName(String string) {
    if (string == null) {
      throw new IllegalArgumentException("Not a representation of "
              + "cs3500.model.model.NoteName");
    }
    switch (string) {
      case "C ":
        return C;
      case "C♯":
        return CX;
      case "D ":
        return D;
      case "D♯":
        return DX;
      case "E ":
        return E;
      case "F":
        return F;
      case "F♯":
        return FX;
      case "G ":
        return G;
      case "G♯":
        return GX;
      case "A ":
        return A;
      case "A♯":
        return AX;
      default:
        return B;
    }
  }

  /**
   * The enum NoteName.
   *
   * @param base the int rep of noteName
   * @return NoteName
   */
  public static NoteName toNoteName(int base) {
    int toNote = Utils.toPosMod(base, 12);
    switch (toNote) {
      case 0:
        return C;
      case 1:
        return CX;
      case 2:
        return D;
      case 3:
        return DX;
      case 4:
        return E;
      case 5:
        return F;
      case 6:
        return FX;
      case 7:
        return G;
      case 8:
        return GX;
      case 9:
        return A;
      case 10:
        return AX;
      default:
        return B;
    }
  }

}
