package cs3500.music.adapter;

import cs3500.music.provider.IView;

import cs3500.music.view.IMusicEditorView;

/**
 * Implements our view interface, and has the adaptee, the provided view interface.
 * Adapts the provided view interface to conform to our view interface.
 */
public class ViewAdapter implements IMusicEditorView {
  protected final IView adaptee;
  private boolean paused;

  public ViewAdapter(IView adaptee) {
    this.adaptee = adaptee;
    this.paused = false;
  }

  @Override
  public void makeVisible() {
    try {
      adaptee.initialize();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void togglePause() {
    if (this.paused) {
      adaptee.resume();
    }
    else {
      adaptee.pause();
    }
    this.paused = !this.paused;
  }

  @Override
  public void jumpToStart() {
    adaptee.jumpToBeginning();
  }

  @Override
  public void jumpToEnd() {
    adaptee.jumpToEnd();
  }

  @Override
  public void refresh() {
    adaptee.update();
  }
}
