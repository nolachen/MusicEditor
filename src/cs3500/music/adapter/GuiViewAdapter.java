package cs3500.music.adapter;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.INote;
import cs3500.music.model.ImmutableNote;
import cs3500.music.provider.IGuiView;
import cs3500.music.view.GuiView;
import cs3500.music.view.IViewModel;

/**
 * Implements our {@link GuiView} interface, and has the adaptee, the provided {@link IGuiView}
 * interface.
 * Extends the ViewAdapter to provide adapt the functionality of the basic
 * IMusicEditorView as well.
 * Adapts the provided GUI view interface to conform to our GUI view interface.
 */
public class GuiViewAdapter extends ViewAdapter implements GuiView {
  IGuiView adaptee;
  IViewModel viewModel;
  KeyboardHandler keyboardHandler;

  /**
   * Constructor for GuiViewAdapter.
   * @param adaptee the adaptee
   * @param viewModel the view model
   */
  public GuiViewAdapter(IGuiView adaptee, IViewModel viewModel) {
    super(adaptee);
    this.viewModel = viewModel;
    this.adaptee = adaptee;
    this.keyboardHandler = new KeyboardHandler();
    this.addKeyListener(this.keyboardHandler);
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.adaptee.addKeyListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.adaptee.addMouseListener(listener);
  }

  @Override
  public void saveNoteAtPosition(int x, int y) {
    INote toSave = null;

    int beat = (x - 50) / 25;

    List<String> noteRange = this.viewModel.getNoteRange();

    int pitchIndex = noteRange.size() - 1 - ((y - 40) / 25);

    if (beat < 0 || beat > this.viewModel.length()
            || pitchIndex < 0 || pitchIndex > noteRange.size() - 1) {
      return;
    }

    String pitch = noteRange.get(pitchIndex);

    List<ImmutableNote> notesAtBeat = this.viewModel.getNotesAtBeat(beat);

    for (ImmutableNote n : notesAtBeat) {
      if (n.toString().equals(pitch) &&
              ((toSave == null) || (n.getStartBeat() < toSave.getStartBeat()))) {
        toSave = n;
      }
    }

    this.viewModel.setSelectedNote(toSave);
  }

  @Override
  public INote getSelectedNote() {
    return this.viewModel.getSelectedNote();
  }

  @Override
  public String getInputNote() {
    return this.keyboardHandler.getInput().toString();
  }

  @Override
  public void scrollLeft() {
    this.adaptee.scrollHorizontal(-25);
  }

  @Override
  public void scrollRight() {
    this.adaptee.scrollHorizontal(25);
  }

  @Override
  public void scrollUp() {
    this.adaptee.scrollVertical(-25);
  }

  @Override
  public void scrollDown() {
    this.adaptee.scrollHorizontal(25);
  }

  @Override
  public void nextPage() {
    return;
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    this.adaptee.addActionListener(actionListener);
  }

  @Override
  public void clearInputString() {
    this.keyboardHandler.clearInput();
  }

  @Override
  public void resetFocus() {
    return;
  }

  @Override
  public void showErrorMessage(String error) {
    return;
  }

  @Override
  public void resetSelectedNote() {
    this.viewModel.setSelectedNote(null);
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    return;
  }

  @Override
  public int getCurrentBeat() {
    return -1;
  }
}
