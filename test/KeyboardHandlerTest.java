/*
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.*;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MusicEditorGuiController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IViewModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.ViewModel;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewImpl;

import static org.junit.Assert.assertEquals;

*/
/**
 * Tests for the keyboard handler.
 *//*

public class KeyboardHandlerTest {
  private IMusicEditorModel model;
  private IViewModel vm;
  private IMusicEditorController controller;
  private GuiView view;

  private KeyboardHandler kbd;
  private String tester;

  */
/**
   * Init cond.
   *//*

  @Before
  public void initCond() {
    this.model = new MusicEditorModel();
    this.vm = new ViewModel(model);
    this.view = new GuiViewImpl(vm);
    this.controller = new MusicEditorGuiController(model, view);

    this.kbd = new KeyboardHandler();
    this.tester = "";
  }

  @Test
  public void testKeyPressedMap() {
    // test that the key pressed map works

    assertEquals("", this.tester);
    Runnable r = new Runnable() {
      @Override
      public void run() {
        tester += "hello";
      }
    };
    this.kbd.addKeyPressedAction(KeyEvent.VK_SPACE, r);
    JButton b = new JButton();
    this.kbd.keyPressed(new KeyEvent(b, 1, 10, KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SPACE, 'a'));
    assertEquals("hello", this.tester);
  }

  @Test
  public void testKeyReleasedMap() {
    // test that the key released map works

    assertEquals("", this.tester);
    Runnable r = new Runnable() {
      @Override
      public void run() {
        tester += "abc";
      }
    };
    this.kbd.addKeyReleasedAction(KeyEvent.VK_R, r);
    JButton b = new JButton();
    this.kbd.keyReleased(new KeyEvent(b, 1, 10, KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_R, 'r'));
    assertEquals("abc", this.tester);
  }

  @Test
  public void testKeyTypedMap() {
    // test that the key typed map works

    assertEquals("", this.tester);
    Runnable r = new Runnable() {
      @Override
      public void run() {
        tester += "Hi";
      }
    };
    this.kbd.addKeyTypedAction('r', r);
    JButton b = new JButton();
    this.kbd.keyTyped(new KeyEvent(b, 1, 10, KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_A, 'r'));
    assertEquals("Hi", this.tester);
  }

}*/
