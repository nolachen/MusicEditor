package cs3500.music.view;

import java.util.ArrayList;

import java.util.List;

import cs3500.music.model.ImmutableNote;

import cs3500.music.model.Note;

import cs3500.music.model.ViewModel;

/**
 * View for the TextView class.
 * Creates a text representation of the models music.
 */
public class MusicEditorTextView implements IMusicEditorView {
  // viewModel that gives access to necessary information from the model.
  private final ViewModel viewModel;
  // Rendering of this view.
  private String rendering;

  // TODO : readable & appendable
  private Readable read;
  private Appendable append;

  /**
   * Constructor of a MusicEditorTextView.
   * @param viewModel viewModel that gives access to information in the model.
   */
  public MusicEditorTextView(ViewModel viewModel) {
    this.viewModel = viewModel;
    this.rendering = "";
  }

  @Override
  public void makeVisible() {
    int length = this.viewModel.length();

    List<String> noteRange = this.viewModel.getNoteRange();
    int width = noteRange.size();

    int padding = ((int) Math.log10(length)) + 1;

    String noteRangeString = "";
    for (String s : noteRange) {
      noteRangeString += this.centerString(s, 5);
    }
    noteRangeString += "\n";

    this.rendering += String.format("%" + (padding + noteRangeString.length()) + "s",
            noteRangeString);

    // initialize the String of notes to be all empty
    List<List<String>> notesGrid = new ArrayList<>();
    for (int i = 0; i < length; i += 1) {
      List<String> temp = new ArrayList<>();
      for (int j = 0; j < width; j += 1) {
        temp.add("     ");
      }
      notesGrid.add(temp);
    }

    // set values in notesGrid
    for (int i = 0; i <= length; i += 1) {
      List<ImmutableNote> currentNotes = this.viewModel.getNotesAtBeat(i);

      for (ImmutableNote n : currentNotes) {
        int pitchIndex = noteRange.indexOf(n.toString());
        notesGrid.get(i).set(pitchIndex, "  X  ");
        for (int j = 1; j < n.getDuration(); j += 1) {
          notesGrid.get(n.getStartBeat() + j).set(pitchIndex, "  |  ");
        }
      }
    }

    // append strings in notesGrid to this rendering
    for (int i = 0; i < notesGrid.size(); i += 1) {
      this.rendering += (String.format("%" + padding + "d", i));
      for (String s : notesGrid.get(i)) {
        this.rendering += s;
      }

      this.rendering += "\n";
    }
    System.out.println(this.rendering);
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

  @Override
  public void refresh() {
  }
}
