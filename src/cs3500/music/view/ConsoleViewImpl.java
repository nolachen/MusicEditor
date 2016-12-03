package cs3500.music.view;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import cs3500.music.model.ImmutableNote;

/**
 * Creates a text view of the music editor.
 * Represents the present state of the model as a string, formatted with the column of beats on the
 * left, and the range of notes on the top. The note-heads are represented with X and the
 * note-sustains are represented as |.
 */
public class ConsoleViewImpl implements IMusicEditorView {
  // ViewModel that gives access to necessary information from the model.
  private final IViewModel viewModel;
  // Appendable output of this view.
  private final Appendable ap;

  /**
   * Constructor of a ConsoleViewImpl.
   * @param viewModel viewModel that gives access to information in the model.
   * @param ap the appendable output
   */
  public ConsoleViewImpl(IViewModel viewModel, Appendable ap) {
    this.viewModel = Objects.requireNonNull(viewModel);
    this.ap = Objects.requireNonNull(ap);
  }

  @Override
  public void makeVisible() {
    int length = this.viewModel.length();

    List<String> noteRange = this.viewModel.getNoteRange();
    int width = noteRange.size();

    int padding = ((int) Math.log10(length)) + 1;

    // create the string of note ranges, with each note taking up 5 spaces
    String noteRangeString = "";
    for (String s : noteRange) {
      noteRangeString += this.centerString(s, 5);
    }
    noteRangeString += "\n";

    // pad the note range at the top and append it to the rendering
    this.appendOutput(String.format("%" + (padding + noteRangeString.length()) + "s",
            noteRangeString));

    // initialize the String of notes to be all empty
    List<List<String>> notesGrid = new ArrayList<>();
    for (int i = 0; i < length; i += 1) {
      List<String> temp = new ArrayList<>();
      for (int j = 0; j < width; j += 1) {
        temp.add("     ");
      }
      notesGrid.add(temp);
    }

    // set values in notesGrid if there is a note
    for (int i = 0; i < length; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i);

      for (ImmutableNote n : currentNotes) {
        if (n.getStartBeat() == i) {
          int pitchIndex = noteRange.indexOf(n.toString());
          notesGrid.get(i).set(pitchIndex, "  X  ");
          for (int j = 0; j < n.getDuration() - 1; j += 1) {
            notesGrid.get(n.getStartBeat() + j).set(pitchIndex, "  |  ");
          }
        }
      }
    }

    // append strings in notesGrid to this rendering
    for (int i = 0; i < notesGrid.size(); i += 1) {
      this.appendOutput((String.format("%" + padding + "d", i)));
      for (String s : notesGrid.get(i)) {
        this.appendOutput(s);
      }

      this.appendOutput("\n");
    }
  }

  /**
   * Doesn't do anything in text view.
   */
  @Override
  public void togglePause() {
    return;
  }

  @Override
  public void jumpToStart() {
    return;
  }

  @Override
  public void jumpToEnd() {
    return;
  }

  /**
   * Pads the given String with spaces so that it is centered in the given width.
   * @param s the string to center
   * @param width the total width in chars
   * @return the centered string
   */
  private String centerString(String s, int width) {

    int totalPad = width - s.length();
    int leftPad = s.length() + ((totalPad + 1) / 2);

    String output = String.format("%" + leftPad + "s", s);
    output = String.format("%-" + width + "s", output);

    return output;
  }

  /**
   * Appends the given string to the Appendable output of this controller.
   * Adds a newline as well, if specified.
   * Changed in HW04 to throw an IllegalArgumentException in the case of IOException, instead of
   * printing to system.out
   * @param s the string to add to the Appendable output
   */
  private void appendOutput(String s) {
    try {
      this.ap.append(s);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid output.");
    }
  }
}
