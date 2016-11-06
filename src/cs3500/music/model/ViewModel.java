package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A view model that is basically a read-only model. Gives the View access to the model's
 * information without allowing the model to be directly accessed.
 */
public class ViewModel {
  private IMusicEditorModel model;

  public ViewModel(IMusicEditorModel model) {
    this.model = model;
  }

  /**
   * A list of all Notes that are currently in this music editor, in chromatic order.
   * @return the list of all notes
   */
  public List<ImmutableNote> getAllNotes() {
    return this.model.getAllNotes();
  }

  /**
   * Returns the list of notes that begin at the given beat.
   * @param beat
   * @return
   */
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    return this.model.getNotesAtBeat(beat);
  }

  /**
   * Number of beats that are currently in this model.
   * @return
   */
  public int length() {
    return this.model.length();
  }

  /**
   * Returns
   * @return
   */
  public List<String> getNoteRange() {
    List<ImmutableNote> allNotes = new ArrayList<>(this.model.getAllNotes());
    Collections.sort(allNotes);

    List<String> noteRange = new ArrayList<>();

    if (allNotes.isEmpty()) {
      return noteRange;
    }

    ImmutableNote min = allNotes.get(0);
    ImmutableNote max = allNotes.get(allNotes.size() - 1);

    Pitch currentPitch = min.getPitch();
    int currentOctave = min.getOctave();

    Pitch maxPitch = max.getPitch();
    int maxOctave = max.getOctave();

    while ((currentOctave < maxOctave) ||
            ((currentOctave == maxOctave) && (currentPitch.compareTo(maxPitch) <= 0))) {
      noteRange.add(currentPitch.toString() + currentOctave);
      if (currentPitch == Pitch.B) {
        currentOctave += 1;
      }
      currentPitch = currentPitch.nextPitch();
    }

    /*while (currentOctave <= maxOctave) {
      if (currentOctave == maxOctave) {
        while (currentPitch.compareTo(maxPitch) <= 0) {
          noteRange.add(currentPitch.toString() + currentOctave);
          currentPitch = currentPitch.nextPitch();
        }
      }
      else {
        while (currentPitch != Pitch.C) {
          noteRange.add(currentPitch.toString() + currentOctave);
          currentPitch = currentPitch.nextPitch();
        }
      }
      currentOctave += 1;
    }*/

    // TODO fix
    /*while (currentOctave < maxOctave) {
      while (currentPitch != Pitch.C) {
        noteRange.add(currentPitch.toString() + currentOctave);
        currentPitch = currentPitch.nextPitch();
      }
      currentOctave += 1;
      noteRange.add(currentPitch.toString() + currentOctave);
      currentPitch = currentPitch.nextPitch();
    }
    while (currentPitch != maxPitch) {
      noteRange.add(currentPitch.toString() + currentOctave);
      currentPitch = currentPitch.nextPitch();
    }*/

    /*int octave = this.octave;
    if (this.pitch == Pitch.B) {
      octave += 1;
    }

    if (this.octave == other.octave) {
      return this.pitch.compareTo(other.pitch);
    }
    else {
      return this.octave - other.octave;
    }

    while (current.compareTo(allNotes.get(allNotes.size() - 1)) <= 0) {
      noteRange.add(current.toString());
      current = current.nextNote();
    }*/

    return noteRange;
  }
}
