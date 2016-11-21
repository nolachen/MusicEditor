package cs3500.music.view;

import java.awt.*;

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.IViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.ViewModel;

/**
 * An implementation of the {@link IMusicEditorView} interface that uses Java Swing to draw the
 * current state of the music editor. It shows the notes as time increases to the right.
 */
public class GuiViewImpl extends JFrame implements GuiView {
  private final IViewModel viewModel;
  private MusicPanel displayPanel;
  private PitchPanel pitchPanel;
  private JPanel inputPanel;

  private JButton addButton;
  private JTextField input;

  private boolean paused;
  private int currentBeat;

  // TODO: make static fields in this class instead of musiceditorpanel
  // TODO: make the pitch labels centered in the measure bars

  /**
   * Constructor for the GUI view implementation.
   * @param viewModel the given view model
   */
  public GuiViewImpl(IViewModel viewModel) {
    super();
    this.viewModel = Objects.requireNonNull(viewModel);
    this.paused = true;
    this.currentBeat = 0;

    // set the title, close operation, and background of the frame
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(new Color(235, 239, 240));

    // create the display panel
    this.displayPanel = new MusicPanel(viewModel);
    displayPanel.setOpaque(false);

    // create the pitches panel
    this.pitchPanel = new PitchPanel(viewModel);
    pitchPanel.setOpaque(false);

    // create the input panel
    this.inputPanel = new JPanel();
    input = new JTextField("Input note as (pitch) (octave) (start beat) (duration). Ex: C# 4 2 4");
    addButton = new JButton("Add note");
    inputPanel.add(input);
    inputPanel.add(addButton);

    // create new BorderLayout container for the panels, to add to the scroll pane
    // add the pitches in the west and the display in the center
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(pitchPanel, BorderLayout.WEST);
    panel.add(displayPanel, BorderLayout.CENTER);
    panel.add(inputPanel, BorderLayout.SOUTH);

    // create the scroll pane
    /*JScrollPane scrollPane = new JScrollPane(panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);*/

    // add the scroll pane to the frame
    //this.getContentPane().add(scrollPane);

    // add the whole panel to the frame
    this.getContentPane().add(panel);

    // reset the focus for this frame
    this.resetFocus();

    // size the frame
    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Stops the red line.
   */
  @Override
  public void togglePause() {
    this.paused = !this.paused;
  }

  public void updateRedLine() {
    int curBeat = this.getCurrentBeat();
    if (this.paused) {
      this.displayPanel.setCurrentBeat(curBeat);
    }
    else {
      this.displayPanel.setCurrentBeat(curBeat + 1);
    }
  }

  /**
   * Doesn't do anything in just the GUI view.
   */
 /* @Override
  public void pausePlayback() {
    return;
  }*/

  /**
   * Doesn't do anything in just the GUI view.
   */
 /* @Override
  public void resumePlayback() {
    return;
  }*/

  @Override
  public void scrollLeft() {
    this.displayPanel.scrollLeft();
    this.refresh();
  }

  @Override
  public void scrollRight() {
    this.displayPanel.scrollRight();
    this.refresh();
  }

  @Override
  public void scrollUp() {
    this.displayPanel.scrollUp();
    this.pitchPanel.scrollUp();
    this.refresh();
  }

  @Override
  public void scrollDown() {
    this.displayPanel.scrollDown();
    this.pitchPanel.scrollDown();
    this.refresh();
  }

  @Override
  public void nextPage() {
    int currentLastBeat = this.displayPanel.getLastBeatShown();

    while (this.displayPanel.getFirstBeatShown() < currentLastBeat
            && currentLastBeat != this.viewModel.length()) {
      this.displayPanel.scrollRight();
    }
    this.refresh();
  }

  @Override
  public void jumpToStart() {
    while (this.displayPanel.getFirstBeatShown() > 0) {
      this.displayPanel.scrollLeft();
    }
    this.refresh();
  }

  @Override
  public void jumpToEnd() {
    while (this.viewModel.length() > this.displayPanel.getLastBeatShown()) {
      this.displayPanel.scrollRight();
    }
    this.displayPanel.scrollRight();

    this.refresh();
  }

  // TODO
  @Override
  public void saveNoteAtPosition(int x, int y) {
    displayPanel.saveNoteAtPosition(x - displayPanel.getX(),
            y - displayPanel.getY() - this.getInsets().top);
  }

  @Override
  public Note getSelectedNote() {
    return this.viewModel.getSelectedNote();
  }

  // TODO
  @Override
  public String getInputNote() {
    return this.input.getText();
  }

  @Override
  public void refresh() { this.repaint(); }

  @Override
  public void addActionListener(ActionListener actionListener) {
    this.addButton.addActionListener(actionListener);
  }

  @Override
  public void clearInputString() {
    this.input.setText("Input note as (pitch) (octave) (start beat) (duration). Ex: C# 4 2 4");
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void resetSelectedNote() {
    this.viewModel.setSelectedNote(null);
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    this.currentBeat = currentBeat;
  }

  @Override
  public int getCurrentBeat() {
    return this.currentBeat;
  }
}
