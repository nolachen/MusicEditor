package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.INote;

/**
 * This interface represents a GUI view.
 * Remove existing notes from a composition via some combination of keyboard and mouse.
 * Add new notes of various lengths to a composition via some combination of keyboard, mouse and
 * user inputs through the GUI.
 * Scroll through a composition with the arrow keys (and scrollbars, if you have them).
 * Jump to the beginning or end of the composition, via the Home or End keys.
 */
public interface GuiView extends IMusicEditorView {

  /**
   * Adds the given key listener to this view.
   * @param listener the key listener
   */
  void addKeyListener(KeyListener listener);

  /**
   * Adds the given mouse listener to this view.
   * @param listener the mouse listener
   */
  void addMouseListener(MouseListener listener);

  /**
   * Saves the note at the given position to the view model.
   * @param x the x
   * @param y the y
   */
  void saveNoteAtPosition(int x, int y);

  /**
   * Gets the saved note.
   * @return the saved note
   */
  INote getSelectedNote();

  /**
   * Get the note typed by the user.
   * @return the note as a string
   */
  String getInputNote();

  /**
   * Scroll left in this view, beat by beat.
   */
  void scrollLeft();

  /**
   * Scroll right in this view, beat by beat.
   */
  void scrollRight();

  /**
   * Scroll up in this view, pitch by pitch.
   */
  void scrollUp();

  /**
   * Scroll down in this view, pitch by pitch.
   */
  void scrollDown();

  /**
   * Advance to the next page of this view.
   */
  void nextPage();

  /**
   * Add the given action listener to this view.
   * @param actionListener the action listener
   */
  void addActionListener(ActionListener actionListener);

  /**
   * Clear the input string from the text input of this view.
   */
  void clearInputString();

  /**
   * Reset the focus of this view.
   */
  void resetFocus();

  /**
   * Show a popup error message.
   * @param error the error
   */
  void showErrorMessage(String error);

  /**
   * Reset the saved note to null.
   */
  void resetSelectedNote();

  /**
   * Set the current beat of this view.
   * @param currentBeat the beat
   */
  void setCurrentBeat(int currentBeat);

  /**
   * Get the current beat of this view.
   * @return the beat
   */
  int getCurrentBeat();
}
