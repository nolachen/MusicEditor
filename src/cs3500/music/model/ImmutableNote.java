package cs3500.music.model;

/**
 * Created by nolachen on 11/4/16.
 */
public final class ImmutableNote implements Comparable<ImmutableNote> {
  private final Note note;

  public ImmutableNote(Note note) {
    this.note = note;
  }

  public Pitch getPitch() {
    return note.getPitch();
  }

  public int getOctave() {
    return note.getOctave();
  }

  public int getStartBeat() {
    return note.getStartBeat();
  }

  public int getDuration() {
    return note.getDuration();
  }

  @Override
  public int compareTo(ImmutableNote other) {
    return - other.compareTo(this.note);
  }

  private int compareTo(Note other) {
    return this.note.compareTo(other);
  }

  @Override
  public String toString() {
    return this.note.toString();
  }
}
