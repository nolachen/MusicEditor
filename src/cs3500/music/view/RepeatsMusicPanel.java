package cs3500.music.view;

import java.awt.*;

import cs3500.music.model.Repeat;

/**
 * This class is an extension of {@link MusicPanel} to support repeats in the model.
 * This panel renders the repeats from the {@link RepeatsViewModel} as purple lines.
 */
public class RepeatsMusicPanel extends MusicPanel {
  private final RepeatsViewModel repeatsViewModel;

  /**
   * Constructs a {@link RepeatsMusicPanel} given the {@link RepeatsViewModel}.
   *
   * @param viewModel the given ViewModel to get data from
   */
  public RepeatsMusicPanel(RepeatsViewModel viewModel) {
    super(viewModel);
    this.repeatsViewModel = viewModel;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    int firstBeatShown = this.getFirstBeatShown();
    int lastBeatShown = this.getLastBeatShown();

    int firstPitchShown = this.getFirstPitchShown();
    int lastPitchShown = this.getLastPitchShown();


    for (int i = 0; i <= lastBeatShown - firstBeatShown; i += 1) {
      Repeat repeat = this.repeatsViewModel.getRepeatAtBegin(i);
      if (repeat != null) {
        // set the color (purple) and stroke
        g2d.setColor(new Color(125, 75, 205));
        g2d.setStroke(new BasicStroke(2.0f));

        // the y-position of the bottom of the music panel
        int yPosBottom = (lastPitchShown - firstPitchShown + 2) * MusicPanel.NOTE_SIZE;

        // draw the purple repeat line at the begin beat of the repeat
        // only draw the repeat begin if it is greater than 0
        if (repeat.getBegin() > 0) {
          int xPosBegin = (repeat.getBegin() - firstBeatShown) * MusicPanel.NOTE_SIZE;
          g2d.fillRect(xPosBegin, MusicPanel.NOTE_SIZE, 5, yPosBottom - MusicPanel.NOTE_SIZE);
          g2d.drawLine(xPosBegin + 12, MusicPanel.NOTE_SIZE, xPosBegin + 12, yPosBottom);
        }

        // draw the purple repeat line at the end beat of the repeat, if it is on the screen
        if (repeat.getEnd() >= firstBeatShown && repeat.getEnd() <= lastBeatShown) {
          int xPosEnd = (repeat.getEnd() - firstBeatShown) * MusicPanel.NOTE_SIZE;
          g2d.fillRect(xPosEnd - 5, MusicPanel.NOTE_SIZE, 5, yPosBottom - MusicPanel.NOTE_SIZE);
          g2d.drawLine(xPosEnd - 12, MusicPanel.NOTE_SIZE, xPosEnd - 12, yPosBottom);
        }
      }
    }
  }
}
