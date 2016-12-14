package cs3500.music.view;

import java.util.Objects;


/**
 * This class is an extension of {@link MidiViewImpl} to support repeats in the model.
 */
public class RepeatsMidiView extends MidiViewImpl {
  private final RepeatsViewModel repeatsViewModel;

  public RepeatsMidiView(RepeatsViewModel viewModel) {
    super(viewModel);
    this.repeatsViewModel = Objects.requireNonNull(viewModel);
  }

}
