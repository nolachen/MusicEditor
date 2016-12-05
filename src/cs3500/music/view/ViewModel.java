package cs3500.music.view;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Objects;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import cs3500.music.model.ImmutableNote;

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
  /**
   * The cached list of all notes in the model, maintained in sorted order by ascending
   * pitch/octave.
   */
  private List<ImmutableNote> allNotes;
  //private List<String> noteRange;

  /**
   * The current selected note in the view.
   */
  private INote selectedNote;

  /**
   * Constructor for ViewModel.
   * @param model model where this class gets its information from.
   */
  public ViewModel(IMusicEditorModel model) {
    this.model = Objects.requireNonNull(model);
    this.updateData();
  }

  @Override
  public List<ImmutableNote> getNotesAtBeat(int beat) {
    return this.model.getNotesAtBeat(beat);
  }

  @Override
  public List<ImmutableNote> getAllNotes() {
    return this.allNotes;
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
    List<String> noteRange = new ArrayList<>();

    if (allNotes.isEmpty()) {
      return noteRange;
    }

    IPitch curPitch = this.getMinPitch();
    IPitch maxPitch = this.getMaxPitch();
    int curOctave = this.getMinOctave();
    int maxOctave = this.getMaxOctave();

    while ((curOctave < maxOctave) ||
            ((curOctave == maxOctave) && (curPitch.getOrdinal() <= maxPitch.getOrdinal()))) {
      // add the current pitch and octave to the list
      noteRange.add(curPitch.toString() + curOctave);

      // increment the pitch
      curPitch = curPitch.nextPitch();

      // increment the octave if the current pitch has looped back to the first pitch in the enum
      if (curPitch.getOrdinal() == 0) {
        curOctave += 1;
      }
    }

    return noteRange;

    /*
    ImmutableNote min = allNotes.get(0);
    ImmutableNote max = allNotes.get(allNotes.size() - 1);

    IPitch currentPitch = min.getPitch();
    int currentOctave = min.getOctave();

    IPitch maxPitch = max.getPitch();
    int maxOctave = max.getOctave();

    while ((currentOctave < maxOctave) ||
            ((currentOctave == maxOctave) && (currentPitch.compareTo(maxPitch) <= 0))) {
      noteRange.add(currentPitch.toString() + currentOctave);
      if (currentPitch == Pitch.B) {
        currentOctave += 1;
      }
      currentPitch = currentPitch.nextPitch();
    }
    return noteRange;*/
  }

  @Override
  public IPitch getMinPitch() {
    if (allNotes.isEmpty()) {
      return null;
    }
    return allNotes.get(0).getPitch();
  }

  @Override
  public IPitch getMaxPitch() {
    if (allNotes.isEmpty()) {
      return null;
    }
    return allNotes.get(allNotes.size() - 1).getPitch();
  }

  @Override
  public int getMinOctave() {
    if (allNotes.isEmpty()) {
      return 0;
    }
    return allNotes.get(0).getOctave();
  }

  @Override
  public int getMaxOctave() {
    if (allNotes.isEmpty()) {
      return 0;
    }
    return allNotes.get(allNotes.size() - 1).getOctave();
  }

  @Override
  public void setSelectedNote(INote note) {
    this.selectedNote = note;
  }

  @Override
  public INote getSelectedNote() {
    return this.selectedNote;
  }

  @Override
  public void updateData() {
    this.allNotes = new ArrayList<>(this.model.getAllNotes());
  }
}
