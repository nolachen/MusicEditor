package cs3500.music.view;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;
import java.util.Objects;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.ImmutableNote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * This class represents an implementation of a music editor view model.
 * Changed in HW07 to implement an interface {@link IViewModel} to provide looser coupling
 * between the view and the view model.
 */
public class ViewModel implements IViewModel {
  /**
   * The model that this class gets information from.
   */
  private final IMusicEditorModel model;

  // TODO: store these notes, have an update viewModel to update data, called whenever model is
  // changed
  //private List<ImmutableNote> allNotes;
  //private List<String> noteRange;

  /**
   * The current selected note in the view.
   */
  private Note selectedNote;

  /**
   * Constructor for ViewModel.
   * @param model model where this class gets its information from.
   */
  public ViewModel(IMusicEditorModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    return this.model.getNotesAtBeat(beat);
  }

  @Override
  public List<ImmutableNote> getAllNotes() {
    return this.model.getAllNotes();
  }

  @Override
  public int length() {
    return this.model.length();
  }

  @Override
  public int getTempo() {
    return this.model.getTempo();
  }

  @Override
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

  @Override
  public void setSelectedNote(Note note) {
    this.selectedNote = note;
  }

  @Override
  public Note getSelectedNote() {
    return this.selectedNote;
  }
}
