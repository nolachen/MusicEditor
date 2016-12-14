package cs3500.music.view;

import java.util.Objects;

import cs3500.music.model.Repeat;

/**
 * This class is an extension of {@link MidiViewImpl} to support repeats in the model.
 */
public class RepeatsMidiView extends MidiViewImpl {
  private final RepeatsViewModel repeatsViewModel;

  public RepeatsMidiView(RepeatsViewModel viewModel) {
    super(viewModel);
    this.repeatsViewModel = Objects.requireNonNull(viewModel);
  }

  @Override
  public void makeVisible() {
    super.makeVisible();

    while (this.getCurrentBeat() <= this.repeatsViewModel.length()) {
      Repeat repeat = this.repeatsViewModel.getRepeatAtEnd(this.getCurrentBeat());
      if (repeat != null && !repeat.getUsed()) {
        this.setCurrentBeat(repeat.getBegin());
        repeat.setUsed(true);
        this.refresh();
      }
    }
  }


}
