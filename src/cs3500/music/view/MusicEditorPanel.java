package cs3500.music.view;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.ImmutableNote;
import cs3500.music.model.ViewModel;

/**
 * This panel represents the region where the notes and measures of a music editor are drawn.
 */
public class MusicEditorPanel extends JPanel {
  public static final int NOTE_SIZE = 20; //width of a whole note in pixels
  public static final int BEATS_PER_MEASURE = 4; //number of beats in a measure
  private final ViewModel viewModel;

  /**
   * Constructs a {@link MusicEditorPanel} given the {@ViewModel}.
   * @param viewModel the given ViewModel to get data from
   */
  public MusicEditorPanel(ViewModel viewModel) {
    super();
    this.viewModel = viewModel;
  }

  /**
   * Draws the notes and measures using data from the {@link ViewModel}.
   * @param g the Graphics object
   */
  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    // cast Graphics object to Graphics2D
    Graphics2D g2d = (Graphics2D) g;

    int length = this.viewModel.length();
    List<String> noteRange = this.viewModel.getNoteRange();
    Collections.reverse(noteRange);

    int height = noteRange.size();

    // draw the notes
    for (int i = 0; i <= length; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i);
      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());

        // draw the note-sustains
        g2d.setColor(new Color(0, 178, 237));
        g2d.fillRect(i * MusicEditorPanel.NOTE_SIZE, (pitchIndex + 1) * MusicEditorPanel.NOTE_SIZE,
                MusicEditorPanel.NOTE_SIZE * n.getDuration(), MusicEditorPanel.NOTE_SIZE);

        // draw the note-heads
        g2d.setColor(new Color(0, 61, 82));
        g2d.fillRect(i * MusicEditorPanel.NOTE_SIZE, (pitchIndex + 1) * MusicEditorPanel.NOTE_SIZE,
                MusicEditorPanel.NOTE_SIZE, MusicEditorPanel.NOTE_SIZE);

      }
    }

    // draw the beat numbers and the measures grid
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < length; i += MusicEditorPanel.BEATS_PER_MEASURE) {
      // only draw the beat numbers every 4 measures
      if ((i % (MusicEditorPanel.BEATS_PER_MEASURE * 4)) == 0) {
        g2d.drawString("" + i, i * MusicEditorPanel.NOTE_SIZE, MusicEditorPanel.NOTE_SIZE - 2);
      }

      // draw the grid of all measures
      for (int j = 0; j < height; j += 1) {
        g2d.drawRect(i * MusicEditorPanel.NOTE_SIZE, (j + 1) * MusicEditorPanel.NOTE_SIZE,
                MusicEditorPanel.BEATS_PER_MEASURE * MusicEditorPanel.NOTE_SIZE,
                MusicEditorPanel.NOTE_SIZE);
      }
    }

  }


}
