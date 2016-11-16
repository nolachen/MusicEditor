package cs3500.music.model;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;
import java.util.Objects;

/**
 * A view model that is basically a read-only model. Gives the View access to the model's.
 * information without allowing the model to be directly accessed.
 */
public class ViewModel {
  // model that this class gets information from.
  private final IMusicEditorModel model;

  /**
   * Constructor.
   * @param model model where this class gets it's information from.
   */
  public ViewModel(IMusicEditorModel model) {
    this.model = Objects.requireNonNull(model);
  }

  /**
   * Returns the list of notes that begin at the given beat.
   * @param beat int representation of the beat we're retrieving notes at.
   * @return the list of Immutable notes at the given beat.
   */
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    return this.model.getNotesAtBeat(beat);
  }

  /**
   * Number of beats that are currently in this model.
   * @return int length of the model.
   */
  public int length() {
    return this.model.length();
  }

  /**
   * Tempo of the model.
   * @return int tempo of the model.
   */
  public int getTempo() {
    return this.model.getTempo();
  }

  /**
   * Returns a list of the pitches and their octaves in the range of this model.
   * @return a list of Strings of the range of notes.
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
    return noteRange;
  }
}
