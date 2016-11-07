package cs3500.music;

import cs3500.music.controller.IMusicEditorController;

import cs3500.music.controller.MusicEditorController;

import cs3500.music.model.*;

import cs3500.music.util.ModelBuilder;

import cs3500.music.util.MusicReader;

import cs3500.music.view.IMusicEditorView;

import cs3500.music.view.MidiViewImpl;

import cs3500.music.view.MusicEditorGuiView;

import cs3500.music.view.MusicEditorTextView;
import cs3500.music.view.MusicEditorViewFactory;

import java.io.FileReader;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;

/**
 * The class from which the program is run.
 */
public final class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    Scanner sc = new Scanner(System.in);
    String file = sc.nextLine();
    String viewType = sc.nextLine();

    ModelBuilder build = new ModelBuilder();
    FileReader reader = new FileReader(file);
    IMusicEditorModel model = MusicReader.parseFile(reader, build);
    ViewModel viewModel = new ViewModel(model);
    IMusicEditorView view = MusicEditorViewFactory.create(viewType, viewModel);
    IMusicEditorController controller = new MusicEditorController(model, view);

    controller.go();

    //IMusicEditorModel model = new MusicEditorModel();

    /*Note g3b0 = new Note(Pitch.G, 5, 0, 7, 1, 64);
    Note c4b4 = new Note(Pitch.C, 4, 4, 2, 1, 64);
    Note g3b16 = new Note(Pitch.G, 3, 16, 10, 1, 64);
    Note g3b24 = new Note(Pitch.G, 3, 24, 2, 1, 64);

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);*/

    //for when we want to play one of the files
   /* ModelBuilder build = new ModelBuilder();
    FileReader reader = new FileReader
            ("mary-little-lamb.txt");
    MusicReader musicReader = new MusicReader();
    musicReader.parseFile(reader, build);
    model = new MusicEditorModel(build);

    ViewModel viewModel = new ViewModel(model);

    //TODO different views to test each of them.
    IMusicEditorView textView = new MusicEditorTextView(viewModel);
    IMusicEditorView guiView = new MusicEditorGuiView(viewModel);
    IMusicEditorView midiView = new MidiViewImpl(viewModel);
    IMusicEditorController controller = new MusicEditorController(model, guiView);*/

    //controller.go();
  }
}
