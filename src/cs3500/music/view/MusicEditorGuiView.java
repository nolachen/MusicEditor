package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.ImmutableNote;
import cs3500.music.model.ViewModel;

/**
 * An implementation of the {@link IMusicEditorView} interface that uses Java Swing to draw the
 * current state of the music editor. It shows
 * A skeleton Frame (i.e., a window) in Swing
 * // TODO: should this be handling error messages? what errors even are there
 */
public class MusicEditorGuiView extends JFrame implements IMusicEditorView {
  private final ViewModel viewModel;
  private final MusicEditorPanel displayPanel; // You may want to refine this to a subtype of
  // JPanel
  //private final PitchPanel pitchesPanel;
  //private final BeatPanel beatsPanel;

  /**
   * Creates new GuiView.
   */
  public MusicEditorGuiView(ViewModel viewModel) {
    super(); //TODO do i need this?
    this.viewModel = viewModel;

    this.setTitle("fuck this");
    //this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setIconImage(); TODO

    // use a BorderLayout with pitches in west, beat numbers in north, and the notes in center
    //this.setLayout(new BorderLayout());

    // initialize the String of notes to be all empty
    /*List<List<String>> notesGrid = new ArrayList<>();
    for (int i = 0; i < length; i += 1) {
      java.util.List<String> temp = new ArrayList<>();
      for (int j = 0; j < width; j += 1) {
        temp.add("     ");
      }
      notesGrid.add(temp);
    }*/
    this.displayPanel = new MusicEditorPanel(this.viewModel);

    /*int length = this.viewModel.length();
    List<String> noteRange = this.viewModel.getNoteRange();
    int width = noteRange.size();

    List<List<JPanel>> notesGrid = new ArrayList<>();
    for (int i = 0; i < length; i += 1) {
      List<JPanel> temp = new ArrayList<>();
      for (int j = 0; j < width; j += 1) {
        temp.add(new JPanel());
      }
      notesGrid.add(temp);
    }

    // set values in notesGrid
    for (int i = 0; i <= length; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i);

      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());
        notesGrid.get(i).set(pitchIndex, new NoteHeadPanel());
        for (int j = 1; j < n.getDuration(); j += 1) {
          notesGrid.get(n.getStartBeat() + j).set(pitchIndex, new NoteSustainPanel());
        }
      }
    }

    // create and add the music editor notes panel
    //displayPanel = new MusicEditorPanel(this.viewModel);
    displayPanel = new JPanel();
    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
    //displayPanel.setPreferredSize(new Dimension(500, 500));
    for (List<JPanel> list : notesGrid) {
      Box temp = Box.createVerticalBox();
      for (JPanel j : list) {
        temp.add(j);
      }
      displayPanel.add(temp);
    }
    this.add(displayPanel, BorderLayout.CENTER);
   */

    // create and add the pitches panel
    /*pitchesPanel = new JPanel();
    pitchesPanel.setLayout(new BoxLayout(pitchesPanel, BoxLayout.PAGE_AXIS));
    for (String s : this.viewModel.getNoteRange()) {
      System.out.println(s);
      pitchesPanel.add(new JLabel(s));
    }
    this.add(pitchesPanel, BorderLayout.WEST);*/

    // create and add the beats panel
    //beatsPanel = new JPanel();
    //beatsPanel.setLayout(new BoxLayout(beatsPanel, BoxLayout.LINE_AXIS));

    // size the frame
    this.pack();
  }

  @Override
  public void makeVisible(){
    this.setVisible(true);
  }

  /*@Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }*/

  @Override
  public void refresh() {
    //this.repaint();
  }
}
