package cs3500.music;

import cs3500.music.adapter.GuiViewAdapter;
import cs3500.music.controller.IMusicEditorController;

import cs3500.music.controller.MusicEditorController;

import cs3500.music.controller.MusicEditorGuiController;
import cs3500.music.model.IMusicEditorModel;

import cs3500.music.adapter.ViewModelAdapter;
import cs3500.music.model.Repeat;
import cs3500.music.model.RepeatsModel;
import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.IGuiView;
import cs3500.music.provider.INote;
import cs3500.music.provider.IView;
import cs3500.music.provider.ViewFactory;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.RepeatsModelBuilder;
import cs3500.music.view.IViewModel;

import cs3500.music.view.RepeatsViewModel;
import cs3500.music.view.ViewModel;

import cs3500.music.util.ModelBuilder;

import cs3500.music.util.MusicReader;

import cs3500.music.view.GuiView;
import cs3500.music.view.IMusicEditorView;

import cs3500.music.view.MusicEditorViewFactory;

import java.io.FileNotFoundException;

import java.io.FileReader;


/**
 * The class from which the program is run.
 */
public final class MusicEditor {
  /**
   * Takes in command-line input that specifies the file you want to read in and the view name
   * you want to use (midi, visual, or console).
   * @param args the args
   */
  public static void main(String[] args) {

    // try to get the file
    FileReader reader;

    // store the arguments (file name and view type)
    String file = args[0];
    String viewType = args[1].toLowerCase();

    try {
      reader = new FileReader(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found, try again.");
    }

    // create the builder, the model from the builder and the file, and the view model
    CompositionBuilder<IMusicEditorModel> build;
    IMusicEditorModel model;
    IViewModel viewModel;

    if (viewType.equals("repeats")) {
      build = new RepeatsModelBuilder();
      model = MusicReader.parseFile(reader, build);
      Repeat r = new Repeat(10, false);
      ((RepeatsModel) model).addRepeat(r);
      viewModel = new RepeatsViewModel((RepeatsModel) model);
    }
    else {
      build = new ModelBuilder();
      model = MusicReader.parseFile(reader, build);
      viewModel = new ViewModel(model);
    }

    // try to create the view
    IMusicEditorView view;

    try {
      if (viewType.equals("provider")) {
        IBasicMusicEditor<INote> vmAdapter = new ViewModelAdapter(viewModel);
        IView providerView = ViewFactory.viewFactory("composite", vmAdapter);
        view = new GuiViewAdapter((IGuiView) providerView, viewModel);
      }
      else {
        view = MusicEditorViewFactory.create(viewType, viewModel);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Incorrect view type, try again.");
    }

    // create the controller and start the program
    IMusicEditorController controller;
    if (viewType.equals("visual") || viewType.equals("composite") || viewType.equals("provider")
            || viewType.equals("repeats")) {
      controller = new MusicEditorGuiController(model, (GuiView) view);
    }
    else {
      controller = new MusicEditorController(model, view);
    }

    controller.gooooo();
  }
}
