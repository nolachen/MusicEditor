package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * This class represents a music editor model.
 */
public class MusicEditorModel implements IMusicEditorModel {
  /**
   * A TreeMap represents the mapping between integer beats and the notes playing at that beat.
   */
  private TreeMap<Integer, List<Note>> music;

  /**
   * Constructor for MusicEditorModel.
   * @param music the mapping of music
   */
  private MusicEditorModel(TreeMap<Integer, List<Note>> music) {
    this.music = music;
  }

  /**
   * Default constructor for MusicEditorModel, creates an empty music to start.
   */
  public MusicEditorModel() {
    this(new TreeMap<>());
  }

  @Override
  public void add(Note note) {
    note.addTo(this.music);
  }

  @Override
  public void remove(Note note) {
    note.removeFrom(this.music);
  }

  @Override
  public void playSimultaneously(IMusicEditorModel other) {
    List<Note> otherNotes = other.getAllNotes();
    for (Note n : otherNotes) {
      this.add(n);
    }
  }

  @Override
  public void playConsecutively(IMusicEditorModel other) {
    List<Note> otherNotes = other.getAllNotes();
    int lastBeat = this.music.lastKey();

    for (Note n : otherNotes) {
      n.setStartBeat(lastBeat + n.getStartBeat());
      this.add(n);
    }
  }

  @Override
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
  }

  @Override
  public List<Note> getAllNotes() {
    List<Note> allNotes = new ArrayList<>();

    for (List<Note> list : this.music.values()) {
      for (Note n : list) {
        if (!allNotes.contains(n)) {
          allNotes.add(n);
        }
      }
    }

    Collections.sort(allNotes);

    return allNotes;
  }

  /**
   * The inclusive list of all notes from the given low note to the high note.
   * @param low lowest note of range
   * @param high highest note of range
   * @return the list of notes between the given ones
   */
  private List<Note> getNoteRange(Note low, Note high) {
    List<Note> notes = new ArrayList<>();
    Note current = low;

    while (current.compareTo(high) <= 0) {
      notes.add(current);
      current = current.nextNote();
    }

    return notes;
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


}
