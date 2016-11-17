package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * Remove existing notes from a composition via some combination of keyboard and mouse
 * Add new notes of various lengths to a composition via some combination of keyboard, mouse and user inputs through the GUI
 * Scroll through a composition with the arrow keys (and scrollbars, if you have them)
 * Jump to the beginning or end of the composition, via the Home or End keys
 * TODO better comment
 */
public interface GuiView extends IMusicEditorView {
  // TODO Lol idk if this is right
  //void scrollLeft();
  //void scrollRight();

  void addKeyListener(KeyListener listener);

  void addMouseListener(MouseListener listener);

  void removeMouseListener(MouseListener listener);

  // TODO: let this return null?
  Note getNoteAtPosition(int x, int y);

  /**
   * Get the note typed by the user.
   * @return the note as a string
   */
  String getInputNote();

  /**
   * Signal the view to draw itself.
   */
  void refresh();
}
