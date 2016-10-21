package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * This class represents a music editor model.
 */
public class MusicEditorModel implements IMusicEditorModel {
  //private Music music;
  private TreeMap<Integer, List<Note>> music;
  //private Note lowestNote;
  //private Note highestNote;

  private MusicEditorModel(TreeMap<Integer, List<Note>> music) {
    this.music = music;
    //this.lowestNote = lowestNote;
    //this.highestNote = highestNote;
  }

  public MusicEditorModel() {
    //this(new Music());
    this(new TreeMap<>());
            //new Note(Pitch.B, Integer.MAX_VALUE, 0, 0),
            //new Note(Pitch.C, Integer.MIN_VALUE, 0, 0));
  }

  @Override
  public void add(Note note) {
    //this.music.add(note, beat);
    /*List<Note> curNotes = this.music.get(beat);
    if (curNotes == null) {
      curNotes = new ArrayList<>();
    }
    curNotes.add(note);*/

    note.addTo(this.music);


    // update min and max notes
    /*if (note.compareTo(this.highestNote) > 0) {
      this.highestNote = note;
    }
    if (note.compareTo(this.lowestNote) < 0) {
      this.lowestNote = note;
    }*/

    //this.music.put(beat, curNotes); TODO is this even necessary
  }

  @Override
  // TODO maybe just delete edit from interface entirely
  public void edit(int beat, Note oldNote, Note newNote) {
    List<Note> curNotes = this.music.get(beat);
    if ((curNotes == null) || !curNotes.contains(oldNote)) {
      throw new IllegalArgumentException("Old note doesn't exist");
    }

    //this.music.put(beat)
  }

  @Override
  public void remove(Note note) {
    note.removeFrom(this.music);

    /*
    List<Note> curNotes = this.music.get(beat);
    if ((curNotes == null) || !curNotes.contains(note)) {
      throw new IllegalArgumentException("Note doesn't exist");
    }
    this.music.get(beat).remove(note);*/
  }

  @Override
  public IMusicEditorModel playSimultaneously(IMusicEditorModel other) {
    return null;
  }

  @Override
  public IMusicEditorModel playConsecutively(IMusicEditorModel other) {
    return null;
  }

  @Override
  public String getTextRendering() {
    String output = "";

    if (this.music.isEmpty()) { return output; }

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

  /**
   * A list of all Notes that are currently in this music editor, in chromatic order.
   * @return the list of all notes
   */
  private List<Note> getAllNotes() {
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
   * inclusive
   * @param low
   * @param high
   * @return
   */
  /*private String noterange(Note low, Note high) {
    String output = "";
    Note current = low;

    while (current.compareTo(high) <= 0) {
      output += this.centerString(current.toString(), 5);
      current = current.nextNote();
    }

    return output;
  }*/

  /**
   * inclusive
   * @param low
   * @param high
   * @return
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
