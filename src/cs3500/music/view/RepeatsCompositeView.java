package cs3500.music.view;

import cs3500.music.model.Repeat;

/**
 * This class is an extension of {@link CompositeViewImpl} to support repeats in the model.
 */
public class RepeatsCompositeView extends CompositeViewImpl {
  private final RepeatsGuiView repeatsGuiView;
  private final RepeatsMidiView repeatsMidiView;
  private final RepeatsViewModel repeatsViewModel;

  /**
   * Constructs a composite view.
   *
   * @param guiView   the gui
   * @param midiView  the midi
   * @param viewModel the view model
   */
  public RepeatsCompositeView(RepeatsGuiView guiView, RepeatsMidiView midiView,
                              RepeatsViewModel viewModel) {
    super(guiView, midiView, viewModel);
    this.repeatsGuiView = guiView;
    this.repeatsMidiView = midiView;
    this.repeatsViewModel = viewModel;
  }

  @Override
  protected void updateCurrentBeat() {
    // process repeats at this beat
    Repeat repeat = this.repeatsViewModel.getRepeatAtEnd(this.getCurrentBeat());
    if (repeat != null && !repeat.getUsed()) {
      this.setCurrentBeat(repeat.getBegin());
      repeat.setUsed(true);
      this.repeatsMidiView.refresh();
    }

    super.updateCurrentBeat();
  }

  @Override
  public void jumpToStart() {
    for (Repeat repeat : this.repeatsViewModel.getAllRepeats()) {
      repeat.setUsed(false);
    }

    super.jumpToStart();
  }

}
