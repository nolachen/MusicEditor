package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.RepeatsModel;

/**
 *  Builder for the music editor model, with repeats.
 */
public class RepeatsModelBuilder extends ModelBuilder {
  @Override
  public IMusicEditorModel build() {
    return new RepeatsModel(this);
  }
}
