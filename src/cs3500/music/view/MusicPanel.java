package cs3500.music.view;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.util.Collections;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.IViewModel;
import cs3500.music.model.ImmutableNote;

import cs3500.music.model.Note;
import cs3500.music.model.ViewModel;

/**
 * This panel represents the region where the notes and measures of a music editor are drawn.
 */
public class MusicPanel extends JPanel {
  static final int NOTE_SIZE = 20; //width of a note of duration 1, in pixels
  static final int BEATS_PER_MEASURE = 4; //number of beats in a measure
  private final IViewModel viewModel;

  /**
   * Constructs a {@link MusicPanel} given the {@link IViewModel}.
   * @param viewModel the given ViewModel to get data from
   */
  public MusicPanel(IViewModel viewModel) {
    super();
    this.viewModel = viewModel;
  }

  /**
   * Draws the notes and measures using data from the {@link IViewModel}.
   * @param g the Graphics object
   */
  @Override
  protected void paintComponent(Graphics g) {
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

        /*TODO maybe make rectangles instead of just filling them, and then store like a treemap
        of rectangles to notes. then i can check if a rectangle contains a posn, and find it in
        the map ????
        Tell the tree map to sort the rectangles based on x value?
         */

        // set the color of the note-head
        if (i == n.getStartBeat()) {
          g2d.setColor(new Color(0, 61, 82));
        }

        // set the color of the note-sustain
        else {
          g2d.setColor(new Color(0, 178, 237));
        }

        // draw the note at the beat
        g2d.fillRect(i * MusicPanel.NOTE_SIZE, (pitchIndex + 1) * MusicPanel.NOTE_SIZE,
                MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE);

        /*
        // draw the note-sustains
        g2d.setColor(new Color(0, 178, 237));
        g2d.fillRect(i * MusicPanel.NOTE_SIZE, (pitchIndex + 1) * MusicPanel.NOTE_SIZE,
                MusicPanel.NOTE_SIZE * n.getDuration(), MusicPanel.NOTE_SIZE);

        // draw the note-heads
        g2d.setColor(new Color(0, 61, 82));
        g2d.fillRect(i * MusicPanel.NOTE_SIZE, (pitchIndex + 1) * MusicPanel.NOTE_SIZE,
                MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE);
                */

      }
    }

    // draw the beat numbers and the measures grid
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < length; i += MusicPanel.BEATS_PER_MEASURE) {
      // only draw the beat numbers every 4 measures
      if ((i % (MusicPanel.BEATS_PER_MEASURE * 4)) == 0) {
        g2d.drawString("" + i, i * MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE - 2);
      }

      // draw the grid of all measures
      for (int j = 0; j < height; j += 1) {
        g2d.drawRect(i * MusicPanel.NOTE_SIZE, (j + 1) * MusicPanel.NOTE_SIZE,
                MusicPanel.BEATS_PER_MEASURE * MusicPanel.NOTE_SIZE,
                MusicPanel.NOTE_SIZE);
      }
    }

  }

  @Override
  public Dimension getPreferredSize() {
    int height = (this.viewModel.getNoteRange().size() + 2) * MusicPanel.NOTE_SIZE;
    int width = this.viewModel.length() * MusicPanel.NOTE_SIZE;
    return new Dimension(width, height);
  }


  // TODO: can we return null, is that ok ;(
  public Note getNoteAtPosition(int x, int y) {
    int beat = x / MusicPanel.NOTE_SIZE;

    List<String> noteRange = this.viewModel.getNoteRange();

    int pitchIndex = noteRange.size() - ((y - MusicPanel.NOTE_SIZE) / MusicPanel.NOTE_SIZE);
    String pitch = noteRange.get(pitchIndex);

    List<ImmutableNote> notesAtBeat = this.viewModel.getNotesAtBeat(beat);
    Note toReturn = null;
    for (ImmutableNote n : notesAtBeat) {
      if (n.toString().equals(pitch) &&
              ((toReturn == null) || (n.getStartBeat() < toReturn.getStartBeat()))) {
        toReturn = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration(),
                n.getInstrument(), n.getVolume());
      }
    }

    return toReturn;
  }
}
