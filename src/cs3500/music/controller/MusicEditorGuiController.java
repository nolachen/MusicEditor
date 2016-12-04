package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.BiConsumer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.INote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.GuiView;

/**
 * This class represents a music editor controller specifically for GUI views.
 */
public class MusicEditorGuiController implements IMusicEditorController, ActionListener {
  private IMusicEditorModel model;
  private GuiView view;

  /**
   * Constructs a controller given a model and a view.
   * @param model the model
   * @param view the view
   */
  public MusicEditorGuiController(IMusicEditorModel model, GuiView view) {
    this.model = model;
    this.view = view;
    configureKeyboardHandler();
    configureMouseHandler();
    this.view.addActionListener(this);
    //this.keyboardHandler = new KeyboardHandler();
  }

  /**
   * Configures the keyboard handler of the GUI view.
   */
  private void configureKeyboardHandler() {
    Runnable remove  = () -> {
      if (view.getSelectedNote() != null) {
        model.remove(view.getSelectedNote());
        view.resetSelectedNote();
        view.refresh();
      }
    };

    Runnable scrollLeft  = () -> view.scrollLeft();

    Runnable scrollRight  = () -> view.scrollRight();

    Runnable scrollUp  = () -> view.scrollUp();

    Runnable scrollDown  = () -> view.scrollDown();

    Runnable jumpToStart = () -> view.jumpToStart();

    Runnable jumpToEnd = () -> view.jumpToEnd();

    Runnable togglePause = () -> view.togglePause();

    KeyboardHandler k = new KeyboardHandler();
    k.addKeyPressedAction(KeyEvent.VK_LEFT, scrollLeft);
    k.addKeyPressedAction(KeyEvent.VK_RIGHT, scrollRight);
    k.addKeyPressedAction(KeyEvent.VK_UP, scrollUp);
    k.addKeyPressedAction(KeyEvent.VK_DOWN, scrollDown);
    k.addKeyPressedAction(KeyEvent.VK_SPACE, togglePause);

    k.addKeyReleasedAction(KeyEvent.VK_HOME, jumpToStart);
    k.addKeyReleasedAction(KeyEvent.VK_END, jumpToEnd);
    k.addKeyReleasedAction(KeyEvent.VK_R, remove);

    view.addKeyListener(k);
  }

  /**
   * Configures the mouse handler of the GUI view.
   */
  private void configureMouseHandler() {
    BiConsumer<Integer, Integer> getNote = (x, y) -> {
      view.saveNoteAtPosition(x, y);
      view.resetFocus();
      //gui.highlightClickedRegion(x, y);
    };

    MouseHandler m = new MouseHandler();
    m.addClickedAction(getNote);

    view.addMouseListener(m);
  }

  @Override
  public void gooooo() {
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Add note")) {
      String inputNote = this.view.getInputNote();
      INote parsedNote = this.parseNote(inputNote);
      if (parsedNote != null) {
        this.model.add(parsedNote);
      }
      this.view.clearInputString();
      this.view.refresh();
      this.view.resetFocus();
    }
  }

  /**
   * Parses the given input string into a Note, based on the space-separated pitch, octave, start
   * beat, and duration.
   * @param inputNote the string to parse
   * @return the parsed Note
   */
  private INote parseNote(String inputNote) {
    String pitch;
    int octave;
    int startBeat;
    int duration;

    try {
      Scanner sc = new Scanner(inputNote);
      pitch = sc.next();
      octave = sc.nextInt();
      startBeat = sc.nextInt();
      duration = sc.nextInt();
    } catch (InputMismatchException e) {
      this.view.showErrorMessage("Invalid input format");
      return null;
    }

    Pitch newPitch;
    switch (pitch) {
      case "C#": newPitch = Pitch.C_SHARP;
        break;
      case "D#": newPitch = Pitch.D_SHARP;
        break;
      case "F#": newPitch = Pitch.F_SHARP;
        break;
      case "G#": newPitch = Pitch.G_SHARP;
        break;
      case "A#": newPitch = Pitch.A_SHARP;
        break;
      default:
        try {
          newPitch = Pitch.valueOf(pitch);
        } catch (IllegalArgumentException e) {
          this.view.showErrorMessage("Invalid pitch");
          return null;
        }
    }

    try {
      return new Note(newPitch, octave, startBeat, duration);
    } catch (IllegalArgumentException e) {
      if (startBeat < 0) {
        this.view.showErrorMessage("Invalid start beat");
      }
      if (duration < 0) {
        this.view.showErrorMessage("Invalid duration");
      }
      else {
        this.view.showErrorMessage("Invalid");
      }
      return null;
    }
  }
}
