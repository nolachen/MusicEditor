package cs3500.music.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import cs3500.music.model.ViewModel;

/**
 * An implementation of the {@link IMusicEditorView} interface that uses Java Swing to draw the
 * current state of the music editor. It shows the notes as time increases to the right.
 * // TODO: should this be handling error messages? what errors even are there
 */
public class MusicEditorGuiView extends JFrame implements IMusicEditorView {
  private final ViewModel viewModel;
  private final MusicEditorPanel displayPanel;
  private final PitchPanel pitchPanel;

  /**
   * Constructor for the GUI view implementation.
   * @param viewModel the given view model
   */
  public MusicEditorGuiView(ViewModel viewModel) {
    super();
    this.viewModel = viewModel;

    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(new Color(235, 239, 240));
    //this.setContentPane(new JLabel(new ImageIcon("lol.jpg")));

    // use a BorderLayout with pitches in west, beat numbers in north, and the notes in center
    this.getContentPane().setLayout(new BorderLayout());
    this.setPreferredSize(new Dimension(900, 500));

    // create the display panel
    displayPanel = new MusicEditorPanel(this.viewModel);
    //displayPanel.setPreferredSize(new Dimension(500,500));
    displayPanel.setOpaque(false);

    // create the pitches panel
    pitchPanel = new PitchPanel(this.viewModel);
    pitchPanel.setPreferredSize(new Dimension(50,200));
    pitchPanel.setOpaque(false);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(pitchPanel, BorderLayout.WEST);
    panel.add(displayPanel, BorderLayout.CENTER);

    // add the panels to this frame
    //this.getContentPane().add(pitchPanel, BorderLayout.WEST);
    //this.getContentPane().add(displayPanel, BorderLayout.CENTER);

    // create the scroll pane
    JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants
            .VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.getContentPane().add(scrollPane);

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
