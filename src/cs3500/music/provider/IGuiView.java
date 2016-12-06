package cs3500.music.provider;

import java.awt.event.ActionListener;

import java.awt.event.MouseListener;

/**
 * For the purposes of representing a GUI based view.
 */
public interface IGuiView extends IView {

  /**
   * Adds an action listener to this view.
   * @param listener Action listener to be added
   */
  void addActionListener(ActionListener listener);

  /**
   * Adds a mouse listener to the view.
   * @param l mouse listener to be added
   */
  void addMouseListener(MouseListener l);

  /**
   * Removes the mouse listener from the view if there is one.
   * @param l Mouse listener to be removed
   */
  void removeMouseListener(MouseListener l);
}
