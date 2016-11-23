package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a keyboard handler to handle key typed, pressed, and released actions.
 */
public class KeyboardHandler implements KeyListener {
  private final Map<Character, Runnable> keyTypedActions;
  private final Map<Integer, Runnable> keyPressedActions;
  private final Map<Integer, Runnable> keyReleasedActions;

  /**
   * Constructor for the keyboard handler.
   */
  public KeyboardHandler() {
    this.keyTypedActions = new HashMap<>();
    this.keyPressedActions = new HashMap<>();
    this.keyReleasedActions = new HashMap<>();
  }

  /**
   * This is called when the view detects that a key has been typed. Find if anything has been
   * mapped to this key character and if so, execute it.
   * @param e the key event
   */
  @Override
  public void keyTyped(KeyEvent e) {
    Runnable r = this.keyTypedActions.get(e.getKeyChar());
    if (r != null) {
      r.run();
    }
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it.
   * @param e the key event
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
   * @param e the key event
   */
  @Override
  public void keyReleased(KeyEvent e) {
    Runnable r = this.keyReleasedActions.get(e.getKeyCode());
    if (r != null) {
      r.run();
    }
  }

  /**
   * Adds the specified runnable to the given key character in this keyboard handler's mapping for
   * key typed events.
   * @param keyChar the key character to map to the runnable
   * @param r the runnable to add
   */
  public void addKeyTypedAction(char keyChar, Runnable r) {
    this.keyTypedActions.put(keyChar, r);
  }

  /**
   * Adds the specified runnable to the given key-code in this keyboard handler's mapping for
   * key pressed events.
   * @param keyCode the key code to map to the runnable
   * @param r the runnable to add
   */
  public void addKeyPressedAction(int keyCode, Runnable r) {
    this.keyPressedActions.put(keyCode, r);
  }

  /**
   * Adds the specified runnable to the given key-code in this keyboard handler's mapping for
   * key released events.
   * @param keyCode the key code to map to the runnable
   * @param r the runnable to add
   */
  public void addKeyReleasedAction(int keyCode, Runnable r) {
    this.keyReleasedActions.put(keyCode, r);
  }
}
