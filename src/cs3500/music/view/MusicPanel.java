package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Dimension;

import java.util.Collections;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.INote;
import cs3500.music.model.ImmutableNote;

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

    // get the entire range of notes in the song, and reverse it to display the highest pitch first
    List<String> noteRange = this.viewModel.getNoteRange();
    Collections.reverse(noteRange);

    // store the last beat and pitch shown as a local variable
    int lastBeatShown = this.getLastBeatShown();
    int lastPitchShown = this.getLastPitchShown();

    // draw the notes from the first beat to the last beat that fits in the current window width
    for (int i = 0; i <= lastBeatShown - firstBeatShown; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i + firstBeatShown);
      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());

        // only draw the note if it is in the y-range of the current window height
        if ((pitchIndex <= lastPitchShown) && (pitchIndex >= firstPitchShown)) {

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
    int xPos = (this.currentBeat - this.firstBeatShown) * MusicPanel.NOTE_SIZE;
    g2d.drawLine(xPos, MusicPanel.NOTE_SIZE,
            xPos, (lastPitchShown - firstPitchShown + 2) * MusicPanel.NOTE_SIZE);
  }

  @Override
  public Dimension getPreferredSize() {
    int height = Math.min(500, (this.viewModel.getNoteRange().size() + 2) * MusicPanel.NOTE_SIZE);
    int width = Math.min(1000, this.viewModel.length() * MusicPanel.NOTE_SIZE);
    return new Dimension(width, height);
  }

  /**
   * Saves the note at the given position to the view model.
   * @param x the x pos
   * @param y the y pos
   */
  void saveNoteAtPosition(int x, int y) {
    INote toSave = null;

    int beat = x / MusicPanel.NOTE_SIZE + this.firstBeatShown;

    List<String> noteRange = this.viewModel.getNoteRange();

    // Pitch index that the mouse click corresponds to.
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
              ((toSave == null) || (n.getStartBeat() < toSave.getStartBeat()))) {
        toSave = n;
        /*toSave = new Note(n.getPitch(), n.getOctave(), n.getStartBeat(), n.getDuration(),
                n.getInstrument(), n.getVolume());*/
      }
    }

    this.viewModel.setSelectedNote(toSave);
  }

  /**
   * Scrolls this panel left if possible.
   */
  void scrollLeft() {
    if (this.firstBeatShown > 0) {
      this.firstBeatShown -= 1;
    }
  }

  /**
   * Scrolls this panel right if possible.
   */
  void scrollRight() {
    if (this.canScrollRight()) {
      this.firstBeatShown += 1;
    }
  }

  /**
   * Scrolls this panel up if possible.
   */
  void scrollUp() {
    if (this.firstPitchShown > 0) {
      this.firstPitchShown -= 1;
    }
  }

  /**
   * Scrolls this panel down if possible.
   */
  void scrollDown() {
    if (this.viewModel.getNoteRange().size() - 1
            >= this.firstPitchShown + this.getHeight() / MusicPanel.NOTE_SIZE - 1) {
      this.firstPitchShown += 1;
    }
  }

  /**
   * The index of the last pitch that should be drawn based on the current window height.
   * @return the index of the last pitch
   */
  int getLastPitchShown() {
    return Math.min(this.viewModel.getNoteRange().size() - 1,
            this.firstPitchShown + this.getHeight() / MusicPanel.NOTE_SIZE - 1);
  }

  /**
   * TODO : make an update method
   * The last beat that should be shown based on the current window width.
   * @return the last beat
   */
  int getLastBeatShown() {
    return Math.min(this.viewModel.length(),
            this.firstBeatShown + this.getWidth() / MusicPanel.NOTE_SIZE);
  }

  /**
   * Gets the first beat shown of this panel.
   * @return the first beat
   */
  int getFirstBeatShown() {
    return this.firstBeatShown;
  }

  /**
   * Gets the first pitch shown of this panel.
   * @return the first pitch
   */
  int getFirstPitchShown() {
    return this.firstPitchShown;
  }

  /**
   * Sets the current beat of this panel.
   * @param beat the new cur beat
   */
  void setCurrentBeat(int beat) {
    this.currentBeat = beat;
  }

  /**
   * Gets the current beat of this panel.
   * @return the cur beat
   */
  int getCurrentBeat() {
    return this.currentBeat;
  }

  /**
   * Determines whether the panel can still scroll to the right, or if it's at the end of the song.
   * @return true if can scroll, else false
   */
  boolean canScrollRight() {
    return this.viewModel.length() + 1
            >= this.firstBeatShown + this.getWidth() / MusicPanel.NOTE_SIZE;
  }

}
