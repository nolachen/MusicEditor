package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

/**
 * This class represents a music editor controller.
 */
public class MusicEditorController implements IMusicEditorController {
  IMusicEditorModel model;
  IMusicEditorView view;

  /**
   * Constructs a controller given a model and a view.
   * @param model the model
   * @param view the view
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void gooooo() {
    this.view.makeVisible();
  }
}
