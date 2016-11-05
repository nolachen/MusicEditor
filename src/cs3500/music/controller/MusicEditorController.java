package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

/**
 * Created by nolachen on 11/3/16.
 */
public class MusicEditorController implements IMusicEditorController {
  IMusicEditorModel model;
  IMusicEditorView view;

  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() {

    // TODO for loop; need to make copy of notes so that it is not mutable
    /*for (int i = 0; i < model.length(); i += 1) {
      this.view.setNotes(model.getNotesAtBeat(i));
    }
    this.view.setNotes(model.getAllNotes());*/

    this.view.makeVisible();
  }
}
