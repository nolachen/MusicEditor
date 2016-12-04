package cs3500.music.provider;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.INote;

/**
 * Represents a View.
 */
public interface IView {

  /**
   * Initialize View.
   *
   * @throws Exception if view can't not be initiallize.
   */
  public void initialize() throws Exception;

  /**
   * Updates the IView after an action has taken place.
   */
  public void update();

  /**
   * Adds the key listener if possible
   *
   * @param keyListener the key listener to be added
   */
  public void addKeyListener(KeyListener keyListener);

  /**
   * Adds the key listener if possible.
   * @param mouseListener the mouse listener to be used.
   */
  public void addMouseListener(MouseListener mouseListener);

  /**
   * Gets the current tick of the view, if possible.
   * @return the current tick
   */
  public long getCurrentTick();

  /**
   * Jumps to beginning, if applicable.
   */
  public void jumpToBeginning();

  /**
   * Jumps to end, if possible.
   */
  public void jumpToEnd();

  /**
   * Moves to tick location.
   * @param tick tick location
   */
  public void move(long tick);

  /**
   * Pauses view, if applicable.
   */
  public void pause();

  /**
   * Resumes view, if applicable.
   */
  public void resume();

  /**
   * Scrolls horizontally.
   * @param unit  Amount to be scrolled.
   */
  public void scrollHorizontal(int unit);

  /**
   * Scrolls vertically.
   * @param unit  Amount to be scrolled
   */
  public void scrollVertical(int unit);
}
