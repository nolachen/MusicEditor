package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A class to handle mouse events.
 */
public class MouseHandler implements MouseListener {
  // map positions to bi-consumers
  // ask the view what to do with the 2 coordinates
  // pass those actions into the mouse handler
  List<BiConsumer<Integer, Integer>> clickedActions;

  /**
   * Constructor for a mouse handler.
   */
  public MouseHandler() {
    this.clickedActions = new ArrayList<>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    for (BiConsumer<Integer, Integer> action : this.clickedActions) {
      action.accept(e.getX(), e.getY());
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // no actions
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // no actions
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // no actions
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // no actions
  }

  /**
   * Adds the specified biconsumer to the clicked actions in this mouse handler's list.
   * @param action the biconsumer to add
   */
  public void addClickedAction(BiConsumer<Integer, Integer> action) {
    this.clickedActions.add(action);
  }
}
