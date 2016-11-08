package cs3500.music;

import sun.awt.ModalityListener;

import cs3500.music.controller.IMusicEditorController;

import cs3500.music.controller.MusicEditorController;

import cs3500.music.model.IMusicEditorModel;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.ViewModel;

import cs3500.music.util.ModelBuilder;

import cs3500.music.util.MusicReader;

import cs3500.music.view.IMusicEditorView;

import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorViewFactory;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;

/**
 * The class from which the program is run.
 */
public final class MusicEditor {
  /**
   * Takes in command-line input that specifies the file you want to read in and the view name
   * you want to use.
   * @param args the args
   */
  public static void main(String[] args) throws FileNotFoundException {
    Scanner sc = new Scanner(System.in);

    // try to get the file
    FileReader reader = null;
    while (reader == null) {
      String file = sc.nextLine();
      try {
        reader = new FileReader(file);
      } catch (FileNotFoundException e) {
        System.err.print("File not found, try again.");
      }
    }

    // create the builder, the model from the builder and the file, and the view model
    ModelBuilder build = new ModelBuilder();
    IMusicEditorModel model = MusicReader.parseFile(reader, build);
    ViewModel viewModel = new ViewModel(model);

    // try to get the view type and create the view
    IMusicEditorView view = null;
    while (view == null) {
      String viewType = sc.nextLine();
      try {
        view = MusicEditorViewFactory.create(viewType, viewModel);
      } catch (IllegalArgumentException e) {
        System.err.print("Incorrect view type, try again.");
      }
    }

    // create the controller and start the program
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.gooooo();
  }
}
