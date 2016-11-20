package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.ViewModel;
import cs3500.music.view.GuiView;
import cs3500.music.view.IMusicEditorView;

/**
 * This class represents a music editor controller.
 */
public class MusicEditorController implements IMusicEditorController {
  private IMusicEditorModel model;
  private IMusicEditorView view;
  private GuiView gui; // TODO ?????
  //private ViewModel viewModel; //TODO idk
  //private KeyboardHandler keyboardHandler;

  /**
   * Constructs a controller given a model and a view.
   * @param model the model
   * @param view the view
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
    configureKeyboardHandler();
    configureMouseHandler();
    //this.keyboardHandler = new KeyboardHandler();
  }

  private void configureKeyboardHandler() {
    Runnable remove  =
            new Runnable() {
              Note currentNote;

              @Override
              public void run() {
                if (this.currentNote != null) {
                  model.remove(this.currentNote);
                }
              }
            };

    Runnable scrollLeft  =
            new Runnable() {
              @Override
              public void run() {
                gui.scrollLeft();
              }
            };

    KeyboardHandler k = new KeyboardHandler();
    k.addKeyReleasedAction(KeyEvent.VK_LEFT, scrollLeft);

    gui.addKeyListener(k);
  }

  private void configureMouseHandler() {
    MouseHandler m = new MouseHandler();

    gui.addMouseListener(m);
  }

  @Override
  public void gooooo() {
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
