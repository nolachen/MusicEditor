package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.util.InputMismatchException;
import java.util.Scanner;

import cs3500.music.model.INote;
import cs3500.music.model.Repeat;
import cs3500.music.model.RepeatsModel;
import cs3500.music.view.GuiView;
import cs3500.music.view.RepeatsGuiView;

/**
 * This class is an extension of {@link MusicEditorGuiController} to support repeats in the model.
 * This extended controller allows for creating repeat sections.
 */
public class RepeatsGuiController extends MusicEditorGuiController {
  private RepeatsModel repeatsModel;
  private GuiView view;

  /**
   * Constructs a controller given a model and a view.
   *
   * @param model the model
   * @param view  the view
   */
  public RepeatsGuiController(RepeatsModel model, GuiView view) {
    super(model, view);
    this.repeatsModel = model;
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Add repeat")) {
      String inputRepeat = this.view.getInput();
      Repeat parsedRepeat = this.parseRepeat(inputRepeat);
      if (parsedRepeat != null) {
        try {
          this.repeatsModel.addRepeat(parsedRepeat);
        } catch (IllegalArgumentException err) {
          this.view.showErrorMessage(err.getMessage());
        }
      }
      this.view.clearInputString();
      this.view.refresh();
      this.view.resetFocus();
    }
  }

  /**
   * Parses the given input into a {@link Repeat}, if possible, otherwise return null.
   * Format of the string should be (begin) (end)
   * @param input string to parse
   * @return the parsed repeat or null
   */
  private Repeat parseRepeat(String input) {
    int begin;
    int end;

    try {
      Scanner sc = new Scanner(input);
      begin = sc.nextInt();
      end = sc.nextInt();
    } catch (InputMismatchException e) {
      this.view.showErrorMessage("Input repeat as: (begin) (end)");
      return null;
    }

    try {
      return new Repeat(end, begin);
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage(e.getMessage());
    }

    // if the repeat can't be parsed, return null
    return null;
  }
}
