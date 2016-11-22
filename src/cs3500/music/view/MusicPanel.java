package cs3500.music.view;

import java.awt.*;

import java.util.Collections;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.IViewModel;
import cs3500.music.model.ImmutableNote;

import cs3500.music.model.Note;

/**
 * This panel represents the region where the notes and measures of a music editor are drawn.
 */
public class MusicPanel extends JPanel {
  static final int NOTE_SIZE = 20; //width of a note of duration 1, in pixels
  static final int BEATS_PER_MEASURE = 4; //number of beats in a measure
  private final IViewModel viewModel;
  private int firstBeatShown; //the first beat shown in the view
  private int firstPitchShown; //the index of the first pitch shown
  private int currentBeat;

  /**
   * Constructs a {@link MusicPanel} given the {@link IViewModel}.
   * @param viewModel the given ViewModel to get data from
   */
  public MusicPanel(IViewModel viewModel) {
    this.viewModel = viewModel;
    this.firstBeatShown = 0;
    this.firstPitchShown = 0;
    this.currentBeat = 0;
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

    // get the number of beats in the entire song
    int length = this.viewModel.length();

    // get the entire range of notes in the song, and reverse it to display the highest pitch first
    List<String> noteRange = this.viewModel.getNoteRange();
    Collections.reverse(noteRange);

    int lastBeatShown = this.getLastBeatShown();
    int lastPitchShown = this.lastPitchShown();

    // draw the notes from the first beat to the last beat that fits in the current window width
    for (int i = 0; i <= lastBeatShown - firstBeatShown; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i + firstBeatShown);
      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());

        // only draw the note if it is in the y-range of the current window height
        if ((pitchIndex <= lastPitchShown) && (pitchIndex >= firstPitchShown)) {

          /*TODO maybe make rectangles instead of just filling them, and then store like a treemap
          of rectangles to notes. then i can check if a rectangle contains a posn, and find it in
          the map ????
          Tell the tree map to sort the rectangles based on x value?
          */

          // set the color of the note-head
          if (i + firstBeatShown == n.getStartBeat()) {
            g2d.setColor(new Color(0, 61, 82));
          }

          // set the color of the note-sustain
          else {
            g2d.setColor(new Color(0, 178, 237));
          }

          // draw the note at the beat
          g2d.fillRect(i * MusicPanel.NOTE_SIZE,
                  (pitchIndex - firstPitchShown + 1) * MusicPanel.NOTE_SIZE,
                  MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE);

          // TODO: give precedence to noteheads if there are multiple notes at a spot
          // Maybe with the treemap. check if already contains, etc


          /*if (i + firstBeatShown == n.getStartBeat()) {
            // draw the note-sustains
            g2d.setColor(new Color(0, 178, 237));
            g2d.fillRect(i * MusicPanel.NOTE_SIZE, (pitchIndex + 1) * MusicPanel.NOTE_SIZE,
                    MusicPanel.NOTE_SIZE * n.getDuration(), MusicPanel.NOTE_SIZE);

            // draw the note-heads
            g2d.setColor(new Color(0, 61, 82));
            g2d.fillRect(i * MusicPanel.NOTE_SIZE, (pitchIndex + 1) * MusicPanel.NOTE_SIZE,
                    MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE);
          }*/


        }
      }
    }


    // draw the beat numbers and the measures grid
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2.0f));
    for (int i = 0; i <= lastBeatShown - firstBeatShown; i += 1) {
      int curBeat = i + firstBeatShown;

      // only draw the beat numbers every 4 measures
      if ((curBeat % (MusicPanel.BEATS_PER_MEASURE * 4)) == 0) {
        g2d.drawString(curBeat + "", i * MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE - 2);
      }

      if (i == 0 || (curBeat % MusicPanel.BEATS_PER_MEASURE) == 0) {
        // draw the grid of all measures
        for (int j = 0; j <= lastPitchShown - firstPitchShown; j += 1) {
          int widthInBeats = MusicPanel.BEATS_PER_MEASURE -
                  (curBeat % MusicPanel.BEATS_PER_MEASURE);

          g2d.drawRect(i * MusicPanel.NOTE_SIZE, (j + 1) * MusicPanel.NOTE_SIZE,
                  widthInBeats * MusicPanel.NOTE_SIZE, MusicPanel.NOTE_SIZE);
        }
      }
    }

    // draw the red line at the current beat
    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(2.0f));
    int xPos = (currentBeat - this.getFirstBeatShown()) * MusicPanel.NOTE_SIZE;
    g2d.drawLine(xPos, MusicPanel.NOTE_SIZE, xPos, this.getHeight());
  }

  @Override
  public Dimension getPreferredSize() {
    int height = Math.min(500, (this.viewModel.getNoteRange().size() + 2) * MusicPanel.NOTE_SIZE);
    int width = Math.min(1000, this.viewModel.length() * MusicPanel.NOTE_SIZE);
    return new Dimension(width, height);
  }


  // TODO: can we return null, is that ok
  void saveNoteAtPosition(int x, int y) {
    Note toReturn = null;

    int beat = x / MusicPanel.NOTE_SIZE + this.firstBeatShown;

    List<String> noteRange = this.viewModel.getNoteRange();

    // Pitch index of that the mouse click corresponds to.
    // Note: noteRange.size() returns the size (not last index), and there is a gap of width
    // MusicPanel.NOTE_SIZE before the pitches begin on screen, but these two facts cancel
    // each other out.
    int pitchIndex = noteRange.size() - (y / MusicPanel.NOTE_SIZE + this.firstPitchShown);

    if (beat < 0 || beat > this.viewModel.length()
            || pitchIndex < 0 || pitchIndex > noteRange.size() - 1) {
      return;
    }

    String pitch = noteRange.get(pitchIndex);

    List<ImmutableNote> notesAtBeat = this.viewModel.getNotesAtBeat(beat);

    for (ImmutableNote n : notesAtBeat) {
      if (n.toString().equals(pitch) &&
              ((toReturn == null) || (n.getStartBeat() < toReturn.getStartBeat()))) {
        toReturn = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration(),
                n.getInstrument(), n.getVolume());
      }
    }

    this.viewModel.setSelectedNote(toReturn);
  }

  void scrollLeft() {
    if (this.firstBeatShown > 0) {
      this.firstBeatShown -= 1;
      this.currentBeat -= 1;
    }
  }

  void scrollRight() {
    if (this.viewModel.length() >= this.firstBeatShown + this.getWidth() / MusicPanel.NOTE_SIZE) {
      this.firstBeatShown += 1;
      this.currentBeat += 1;
    }
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

  /*public void nextPage() {
    this.firstBeatShown += this.getWidth() / MusicPanel.NOTE_SIZE;
  }*/

  /**
   * The index of the last pitch that should be drawn based on the current window height.
   * @return the index of the last pitch
   */
  private int lastPitchShown() {
    return Math.min(this.viewModel.getNoteRange().size() - 1,
            this.firstPitchShown + this.getHeight() / MusicPanel.NOTE_SIZE - 1);
  }

  /**
   * The last beat that should be shown based on the current window width.
   * @return the last beat
   */
  public int getLastBeatShown() {
    return Math.min(this.viewModel.length(),
            this.firstBeatShown + this.getWidth() / MusicPanel.NOTE_SIZE);
  }

  /**
   * Gets the first beat shown of this panel.
   * @return the first beat
   */
  public int getFirstBeatShown() {
    return this.firstBeatShown;
  }

  public void setCurrentBeat(int beat) {
    this.currentBeat = beat;
  }

  public int getCurrentBeat() { return this.currentBeat; }

}
