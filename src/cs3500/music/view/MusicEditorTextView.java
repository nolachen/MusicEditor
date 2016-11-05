package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.ImmutableNote;
import cs3500.music.model.Note;
import cs3500.music.model.ViewModel;

/**
 * Created by nolachen on 11/3/16.
 */
public class MusicEditorTextView implements IMusicEditorView {
  private ViewModel viewModel;
  private String rendering;
  private ArrayList<ImmutableNote> notes;
  private Note min;
  private Note max;

  // TODO : readable & appendable
  private Readable read;
  private Appendable append;

  public MusicEditorTextView(ViewModel viewModel) {
    this.viewModel = viewModel;
    this.rendering = "";
    //this.notes = new ArrayList<Note>();
    // TODO figure out min and max
  }

  @Override
  public void makeVisible() {
    //this.makeString();
    // instead of printing, append onto appendable
    int length = this.viewModel.length();
    if (length == 0) {
      return;
    }

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

  //@Override
  // converts list of notes into a string
  public void setNotes(List<ImmutableNote> notes) {
    //this.notes = new ArrayList<Note>(notes);
    // store notes as field
  }


  // TODO : the min and max should be calculated by the view

  /*private void makeString() {

  }*/


  @Override
  public void refresh() {

  }

  /*@Override
  public String getTextRendering() {
    String output = "";

    if (this.music.isEmpty()) {
      return output;
    }

    List<Note> allNotes = this.getAllNotes();

    int maxBeat = this.music.lastKey();
    int padding = ((int) Math.log10(maxBeat)) + 1;


    List<Note> noteRange = this.getNoteRange(allNotes.get(0), allNotes.get(allNotes.size() - 1));
    String noteRangeString = "";
    for (Note n : noteRange) {
      noteRangeString += this.centerString(n.toString(), 5);
    }
    noteRangeString += "\n";

    output += String.format("%" + (padding + noteRangeString.length()) + "s", noteRangeString);

    for (int i = 0; i <= maxBeat; i += 1) {
      output += (String.format("%" + padding + "d", i));
      List<Note> currentNotes = this.music.get(i);

      for (Note n : noteRange) {
        if ((currentNotes == null) || !currentNotes.contains(n)) {
          output += "     ";
        }
        else {
          int index = currentNotes.indexOf(n);
          for (int j = 0; j < currentNotes.size(); j += 1) {
            Note curNote = currentNotes.get(j);
            if (curNote.equals(n)
                    && (curNote.getStartBeat() > currentNotes.get(index).getStartBeat())) {
              index = j;
            }
          }
          output += currentNotes.get(index).render(i);
        }
      }
      output += "\n";
    }

    return output;
  }*/
}