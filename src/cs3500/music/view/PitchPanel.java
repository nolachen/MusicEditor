package cs3500.music.view;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.util.Collections;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.ViewModel;

/**
 * This panel represents the range of pitches in a music editor.
 */
public class PitchPanel extends JPanel {
  ViewModel viewModel;

  /**
   * Constructs a PitchPanel with the given ViewModel.
   * @param viewModel the given ViewModel
   */
  PitchPanel(ViewModel viewModel) {
    this.viewModel = viewModel;
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
    int height = noteRange.size();

    for (int i = 0; i < height; i += 1) {
      g2d.drawString(noteRange.get(i), 0, (i + 2) * MusicEditorPanel.NOTE_SIZE);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    int height = (this.viewModel.getNoteRange().size() + 2) * MusicEditorPanel.NOTE_SIZE;
    return new Dimension(50, height);
  }
}
