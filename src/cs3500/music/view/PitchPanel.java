package cs3500.music.view;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.util.Collections;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.IViewModel;

/**
 * This panel represents the range of pitches in a music editor.
 * TODO: ABstract MusicPanel & PitchPanel
 */
public class PitchPanel extends JPanel {
  private final IViewModel viewModel;
  private int firstPitchShown;

  /**
   * Constructs a PitchPanel with the given ViewModel.
   * @param viewModel the given ViewModel
   */
  public PitchPanel(IViewModel viewModel) {
    this.viewModel = viewModel;
    this.firstPitchShown = 0;
  }

  /**
   * Draws the range of pitches with higher pitches being higher vertically.
   * @param g the Graphics object
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    List<String> noteRange = this.viewModel.getNoteRange();
    Collections.reverse(noteRange);

    int lastPitchShown = this.lastPitchShown();

    for (int i = 0; i <= lastPitchShown - firstPitchShown; i += 1) {
      g2d.drawString(noteRange.get(i + firstPitchShown), 0, (i + 2) * MusicPanel.NOTE_SIZE);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    int height = Math.min(500, (this.viewModel.getNoteRange().size() + 2) * MusicPanel.NOTE_SIZE);
    return new Dimension(50, height);
  }

  void scrollUp() {
    if (this.firstPitchShown > 0) {
      this.firstPitchShown -= 1;
    }
  }

  void scrollDown() {
    if (this.viewModel.getNoteRange().size() >=
            this.firstPitchShown + this.getHeight() / MusicPanel.NOTE_SIZE - 1) {
      this.firstPitchShown += 1;
    }
  }

  private int lastPitchShown() {
    return Math.min(this.viewModel.getNoteRange().size() - 1,
            this.firstPitchShown + this.getHeight() / MusicPanel.NOTE_SIZE - 1);
  }
}
