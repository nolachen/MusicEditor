package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

/**
 * A class to handle mouse events.
 * TODO: if I don't need to override all 5 methods, just extend MouseAdapter
 */
public class MouseHandler implements MouseListener {
  // map positions to runnables/bi-consumers?
  // ask the view what to do with the 2 coordinates
  // pass those actions into the mouse handler
  BiConsumer<Integer, Integer> mousePosition;

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
