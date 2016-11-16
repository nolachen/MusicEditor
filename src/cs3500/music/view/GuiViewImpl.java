package cs3500.music.view;

import java.awt.BorderLayout;

import java.awt.Color;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.ScrollPaneConstants;

import cs3500.music.model.ViewModel;

/**
 * An implementation of the {@link IMusicEditorView} interface that uses Java Swing to draw the
 * current state of the music editor. It shows the notes as time increases to the right.
 */
public class GuiViewImpl extends JFrame implements IMusicEditorView {
  // These fields are commented out because the style grader doesn't like them.
  //private final ViewModel viewModel;
  //private MusicPanel displayPanel;
  //private PitchPanel pitchPanel;

  // TODO: make pitch panel not scroll horizontally
  // TODO: make static fields in this class instead of musiceditorpanel
  // TODO: make the pitch labels centered in the measure bars

  /**
   * Constructor for the GUI view implementation.
   * @param viewModel the given view model
   */
  public GuiViewImpl(ViewModel viewModel) {
    super();
    //this.viewModel = Objects.requireNonNull(viewModel);

    // set the title, close operation, and background of the frame
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(new Color(235, 239, 240));
    //this.setContentPane(new JLabel(new ImageIcon("lol.jpg")));

    // create the display panel
    MusicPanel displayPanel = new MusicPanel(viewModel);
    displayPanel.setOpaque(false);

    // create the pitches panel
    PitchPanel pitchPanel = new PitchPanel(viewModel);
    pitchPanel.setOpaque(false);

    // create new BorderLayout container for the panels, to add to the scroll pane
    // add the pitches in the west and the display in the center
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(pitchPanel, BorderLayout.WEST);
    panel.add(displayPanel, BorderLayout.CENTER);

    // create the scroll pane
    JScrollPane scrollPane = new JScrollPane(panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // add the scroll pane to the frame
    this.getContentPane().add(scrollPane);

    // size the frame
    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

}
