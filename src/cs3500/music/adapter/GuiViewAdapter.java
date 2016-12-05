package cs3500.music.adapter;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.INote;
import cs3500.music.provider.IGuiView;
import cs3500.music.view.GuiView;

/**
 * TODO
 * Implements our {@link GuiView} interface, and has the adaptee, the provided {@link IGuiView}
 * interface.
 * Extends the ViewAdapter to provide adapt the functionality of the basic
 * {@link cs3500.music.view.IMusicEditorView} as well.
 * Adapts the provided GUI view interface to conform to our GUI view interface.
 */
public class GuiViewAdapter extends ViewAdapter implements GuiView {
  IGuiView adaptee;

  public GuiViewAdapter(IGuiView adaptee) {
    super(adaptee);
    this.adaptee = adaptee;
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
    return;
  }

  @Override
  public INote getSelectedNote() {
    return null;
  }

  @Override
  public String getInputNote() {
    return null;
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
    return;
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
    return;
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
