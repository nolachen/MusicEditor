package cs3500.music.view;

import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.Repeat;
import cs3500.music.model.RepeatsModel;

/**
 * This class represents an implementation of {@link IViewModel}, which extends {@link ViewModel}
 * to support repeats in the model.
 */
public class RepeatsViewModel extends ViewModel {
  private final RepeatsModel repeatsModel;

  /**
   * Constructor for RepeatsViewModel.
   *
   * @param model model where this class gets its information from.
   */
  public RepeatsViewModel(RepeatsModel model) {
    super(model);
    this.repeatsModel = model;
  }

  /**
   * Returns the repeat with end at the given beat, or null if there is none.
   * @param beat the beat to get a repeat at
   * @return the repeat
   */
  public Repeat getRepeatAtEnd(int beat) {
    return this.repeatsModel.getRepeatAtEnd(beat);
  }

  /**
   * Returns the repeat with begin at the given beat, or null if there is none.
   * @param beat the beat to get a repeat at
   * @return the repeat
   */
  public Repeat getRepeatAtBegin(int beat) {
    return this.repeatsModel.getRepeatAtBegin(beat);
  }

  /**
   * Returns the list of all repeats in the model.
   * @return all repeats
   */
  public List<Repeat> getAllRepeats() {
    return this.repeatsModel.getAllRepeats();
  }

}
