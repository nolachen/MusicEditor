package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.ImmutableNote;
import cs3500.music.model.ViewModel;

/**
 * A dummy view that simply draws a string 
 */
public class MusicEditorPanel extends JPanel {
  private final ViewModel viewModel;
  //private final int beat;
  //private final List<ImmutableNote> notes;

  public MusicEditorPanel(ViewModel viewModel) {
    super(); //TODO ???
    //this.setBackground(Color.WHITE);
    //this.viewModel = viewModel;
    //this.beat = beat;
    //this.notes = notes;

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    /*if ((this.beat % 16) == 0) {
      this.add(new JLabel("" + this.beat));
    }*/
  }

  @Override
  protected void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    //g.drawString("Hello World", 25, 25);
    // cast Graphics object to Graphics2D
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);

    /*
    the origin of the panel is top left. In order
    to make the origin bottom left, we must "flip" the
    y coordinates so that y = height - y

    We do that by using an affine transform. The flip
    can be specified as scaling y by -1 and then
    translating by height.
     */

    AffineTransform originalTransform = g2d.getTransform();

    //the order of transforms is bottom-to-top
    //so as a result of the two lines below,
    //each y will first be scaled, and then translated
    g2d.translate(0, this.getPreferredSize().getHeight());
    g2d.scale(1, -1);

    /*int length = this.viewModel.length();

    List<String> noteRange = this.viewModel.getNoteRange();
    int width = noteRange.size();

    for (int i = 0; i < length; i += 1) {
      //JPanel currentBeat = new JPanel();
      //currentBeat.setLayout(new BoxLayout(currentBeat, BoxLayout.PAGE_AXIS));
      for (int j = 0; j < width; j += 1) {
        g2d.drawLine(50*i, 50*j, 50*i + 50, 50*j);
        g2d.drawLine(50*i, 50*j + 50, 50*i + 50, 50*j + 50);
      }
    }

    for (int i = 0; i <= length; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i);

      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());
        g2d.setColor(Color.BLACK);
        g2d.fillRect(50*i, 50*pitchIndex, 50, 50);
        for (int j = 1; j < n.getDuration(); j += 1) {
          g2d.setColor(Color.GREEN);
          g2d.fillRect(50*i, 50*pitchIndex + 50*j, 50, 50);
        }
      }
    }*/

    //reset the transform to what it was!
    g2d.setTransform(originalTransform);

  }


}
