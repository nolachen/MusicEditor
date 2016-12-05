package cs3500.music.provider;


/**
 * The music util for convience.
 */
public final class MusicUtils {

  /**
   * Return the pitch of the the note.
   *
   * @param noteName the noteName
   * @param octave   the octave
   * @return the pitch number
   */
  public static int toPitch(NoteName noteName, int octave) {
    return noteName.toInt() + (octave * 12);
  }

  /**
   * Returns String representation of the given pitch.
   *
   * @param pitch Pitch to be represented
   * @return String representation
   */
  public static String pitchToString(int pitch) {
    int note = pitch % 12;
    int octave = (pitch / 12) - 1;
    String noteName;
    switch (note) {
      case 0:
        noteName = "C ";
        break;
      case 1:
        noteName = "C♯";
        break;
      case 2:
        noteName = "D ";
        break;
      case 3:
        noteName = "D♯";
        break;
      case 4:
        noteName = "E ";
        break;
      case 5:
        noteName = "F ";
        break;
      case 6:
        noteName = "F♯";
        break;
      case 7:
        noteName = "G ";
        break;
      case 8:
        noteName = "G♯";
        break;
      case 9:
        noteName = "A ";
        break;
      case 10:
        noteName = "A♯";
        break;
      default:
        noteName = "B ";
        break;
    }
    return noteName + octave;
  }

  /**
   * Convert to the correct notename.
   *
   * @param pitch the pitch number
   * @return correct note mane.
   */
  public static NoteName pitchToNoteName(int pitch) {
    int note = pitch % 12;
    String noteName;
    switch (note) {
      case 0:
        return NoteName.C;
      case 1:
        return NoteName.CX;
      case 2:
        return NoteName.D;
      case 3:
        return NoteName.DX;
      case 4:
        return NoteName.E;
      case 5:
        return NoteName.F;
      case 6:
        return NoteName.FX;
      case 7:
        return NoteName.G;
      case 8:
        return NoteName.GX;
      case 9:
        return NoteName.A;
      case 10:
        return NoteName.AX;
      default:
        return NoteName.B;
    }
  }

  public static int getOctave(int pitch) {
    return (pitch / 12) - 1;
  }

}
