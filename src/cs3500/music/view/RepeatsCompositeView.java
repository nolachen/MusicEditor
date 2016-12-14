package cs3500.music.view;

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
}
