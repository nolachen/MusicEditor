package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.IViewModel;
import cs3500.music.model.Note;

/**
 * An implementation of the {@link IMusicEditorView} interface that takes in both a
 * {@link GuiViewImpl} and {@link MidiViewImpl}. It can start, pause, and resume playback of sound
 * and draws a red line that sweeps across, marking the current beat.
 */
public class CompositeViewImpl implements GuiView {
  private final GuiViewImpl guiView;
  private final MidiViewImpl midiView;
  private final IViewModel viewModel;

  public CompositeViewImpl(GuiViewImpl guiView, MidiViewImpl midiView, IViewModel viewModel) {
    this.guiView = Objects.requireNonNull(guiView);
    this.midiView = Objects.requireNonNull(midiView);
    this.viewModel = viewModel;

  }


  @Override
  public void makeVisible() {
    this.midiView.togglePause();
    this.guiView.makeVisible();
    this.midiView.makeVisible();

    while (this.getCurrentBeat() <= this.viewModel.length()) {
      this.updateCurrentBeat();
      if (this.getCurrentBeat() == this.guiView.getLastBeatShown()) {
        this.guiView.nextPage();
      }
      this.refresh();
    }
    this.setCurrentBeat(-1);
  }

  @Override
  public void togglePause() {
    if (this.getCurrentBeat() == -1) {
      this.setCurrentBeat(0);
      this.makeVisible();
    }
    else {
      this.guiView.togglePause();
      this.midiView.togglePause();
      this.refresh();
    }
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.guiView.addKeyListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.guiView.addMouseListener(listener);
  }

  @Override
  public void removeMouseListener(MouseListener listener) {
    this.guiView.removeMouseListener(listener);
  }

  @Override
  public void saveNoteAtPosition(int x, int y) {
    this.guiView.saveNoteAtPosition(x, y);
  }

  @Override
  public Note getSelectedNote() {
    return this.guiView.getSelectedNote();
  }

  @Override
  public String getInputNote() {
    return this.guiView.getInputNote();
  }

  @Override
  public void refresh() {
    this.guiView.refresh();
  }

  @Override
  public void scrollLeft() {
    this.guiView.scrollLeft();
  }

  @Override
  public void scrollRight() {
    this.guiView.scrollRight();
  }

  @Override
  public void scrollUp() {
    this.guiView.scrollUp();
  }

  @Override
  public void scrollDown() {
    this.guiView.scrollDown();
  }

  @Override
  public void nextPage() {
    this.guiView.nextPage();
  }

  @Override
  public void jumpToStart() {
    this.guiView.jumpToStart();
    this.midiView.jumpToStart();
  }

  @Override
  public void jumpToEnd() {
    this.guiView.jumpToEnd();
    this.midiView.jumpToEnd();
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    this.guiView.addActionListener(actionListener);
  }

  @Override
  public void clearInputString() {
    this.guiView.clearInputString();
  }

  @Override
  public void resetFocus() {
    this.guiView.resetFocus();
  }

  @Override
  public void showErrorMessage(String error) {
    this.guiView.showErrorMessage(error);
  }

  @Override
  public void resetSelectedNote() {
    this.guiView.resetSelectedNote();
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    this.guiView.setCurrentBeat(currentBeat);
  }

  @Override
  public int getCurrentBeat() {
    return this.midiView.getCurrentBeat();
  }

  public void updateCurrentBeat() {
    this.setCurrentBeat(this.getCurrentBeat());
  }
}
