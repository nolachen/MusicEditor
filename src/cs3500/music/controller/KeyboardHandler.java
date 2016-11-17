package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class KeyboardHandler implements KeyListener {
  private final Map<Character, Runnable> keyTypedActions;
  private final Map<Integer, Runnable> keyPressedActions;
  private final Map<Integer, Runnable> keyReleasedActions;

  public KeyboardHandler() {
    this.keyTypedActions = new HashMap<>();
    this.keyPressedActions = new HashMap<>();
    this.keyReleasedActions = new HashMap<>();
  }

  /**
   * This is called when the view detects that a key has been typed. Find if anything has been
   * mapped to this key character and if so, execute it.
   */
  @Override
  // TODO: why does keyTyped event not have keycode? do we need map<Character, Runnable>
  public void keyTyped(KeyEvent e) {
    Runnable r = this.keyTypedActions.get(e.getKeyChar());
    if (r != null) {
      r.run();
    }
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    Runnable r = this.keyPressedActions.get(e.getKeyCode());
    if (r != null) {
      r.run();
    }
  }

  /**
   * This is called when the view detects that a key has been released. Find if anything has been
   * mapped to this key code and if so, execute it.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    Runnable r = this.keyReleasedActions.get(e.getKeyCode());
    if (r != null) {
      r.run();
    }
    else {
      // TODO: BOING or SAVE ME
    }
  }

  // TODO: should there be 3 separate methods for adding?

  /**
   * Adds the specified runnable to the given key character in this keyboard handler's mapping for
   * key typed events.
   * @param keyChar
   * @param r
   */
  public void addKeyTypedAction(char keyChar, Runnable r) {
    this.keyTypedActions.put(keyChar, r);
  }

  /**
   * Adds the specified runnable to the given key-code in this keyboard handler's mapping for
   * key pressed events.
   * @param keyCode
   * @param r
   */
  public void addKeyPressedAction(int keyCode, Runnable r) {
    this.keyPressedActions.put(keyCode, r);
  }

  /**
   * Adds the specified runnable to the given key-code in this keyboard handler's mapping for
   * key released events.
   * @param keyCode
   * @param r
   */
  public void addKeyReleasedAction(int keyCode, Runnable r) {
    this.keyReleasedActions.put(keyCode, r);
  }
}
