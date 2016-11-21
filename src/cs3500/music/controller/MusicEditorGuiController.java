package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.BiConsumer;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.GuiView;

/**
 * This class represents a music editor controller specifically for GUI views.
 */
public class MusicEditorGuiController implements IMusicEditorController, ActionListener {
  private IMusicEditorModel model;
  private GuiView view;

  //private Note selectedNote;
  //private ViewModel viewModel; //TODO idk
  //private KeyboardHandler keyboardHandler;

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

  private void configureKeyboardHandler() {
    Runnable remove  =
            new Runnable() {
              @Override
              public void run() {
                if (view.getSelectedNote() != null) {
                  model.remove(view.getSelectedNote());
                  view.resetSelectedNote();
                  view.refresh();
                }
              }
            };

    Runnable scrollLeft  =
            new Runnable() {
              @Override
              public void run() {
                view.scrollLeft();
              }
            };

    Runnable scrollRight  =
            new Runnable() {
              @Override
              public void run() {
                view.scrollRight();
              }
            };

    Runnable scrollUp  =
            new Runnable() {
              @Override
              public void run() {
                view.scrollUp();
              }
            };

    Runnable scrollDown  =
            new Runnable() {
              @Override
              public void run() {
                view.scrollDown();
              }
            };

    Runnable jumpToStart =
            new Runnable() {
              @Override
              public void run() {
                view.jumpToStart();
              }
            };

    Runnable jumpToEnd =
            new Runnable() {
              @Override
              public void run() {
                view.jumpToEnd();
              }
            };

    Runnable togglePause =
            new Runnable() {
              @Override
              public void run() {
                view.togglePause();
              }
            };

    KeyboardHandler k = new KeyboardHandler();
    k.addKeyPressedAction(KeyEvent.VK_LEFT, scrollLeft);
    k.addKeyPressedAction(KeyEvent.VK_RIGHT, scrollRight);
    k.addKeyPressedAction(KeyEvent.VK_UP, scrollUp);
    k.addKeyPressedAction(KeyEvent.VK_DOWN, scrollDown);

    k.addKeyReleasedAction(KeyEvent.VK_HOME, jumpToStart);
    k.addKeyReleasedAction(KeyEvent.VK_END, jumpToEnd);
    k.addKeyReleasedAction(KeyEvent.VK_R, remove);
    k.addKeyReleasedAction(KeyEvent.VK_SPACE, togglePause);

    view.addKeyListener(k);
  }

  private void configureMouseHandler() {
    BiConsumer<Integer, Integer> getNote =
            new BiConsumer<Integer, Integer>() {
              @Override
              public void accept(Integer x, Integer y) {
                view.saveNoteAtPosition(x, y);
                view.resetFocus();

                System.out.println("Selection: " + view.getSelectedNote());

                // TODO: how and why
                //gui.highlightClickedRegion(x, y);
              }
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
    switch (e.getActionCommand()) {
      case "Add note":
        String inputNote = this.view.getInputNote();
        Note parsedNote = this.parseNote(inputNote);
        if (parsedNote != null) {
          this.model.add(parsedNote);
        }
        this.view.clearInputString();
        this.view.refresh();
        this.view.resetFocus();

        break;
      default:
        throw new IllegalArgumentException("Invalid action event");
    }
  }

  private Note parseNote(String inputNote) {
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

    Note parsedNote = new Note(newPitch, octave, startBeat, duration);
    return parsedNote;
  }
}