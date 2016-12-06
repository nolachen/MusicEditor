package cs3500.music.provider;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/**
 * Console View.
 */
public class ConsoleView implements IView {

  private final IBasicMusicEditor<INote> musicEditor;
  private List<List<String>> view = new ArrayList<>();
  private Appendable appendable = new StringBuilder("");
  private Readable readable;

  /**
   * Construct an instance of ConsoleView.
   *
   * @param musicEditor the music model
   */
  public ConsoleView(IBasicMusicEditor<INote> musicEditor,
                     Readable readable, Appendable appendable) {
    this.musicEditor = musicEditor;
    this.appendable = appendable;
    this.readable = readable;
  }

  /**
   * Get the redable.
   *
   * @return the readable.
   */
  private Readable readable() {
    return readable;
  }

  /**
   * The note relative position in the array.
   *
   * @param x note in string format
   * @return the number where the note belong in the array
   */
  private int notePosition(String x) {
    return (NoteName.toNoteName(x.substring(0, 2)).toInt()
            + (((Integer.parseInt(x.substring(2)))) * 12)) - musicEditor.getMinPitch();
  }

  /**
   * Produce the name for all in range.
   *
   * @return the lists of notes name in range of displaying.
   */
  private List<String> produceName() {

    int column = (musicEditor.getMaxPitch() - musicEditor.getMinPitch() + 2);
    List<String> str = new ArrayList<>();
    str.add(0, "  ");
    for (int i = 1; i < column; i++) {
      StringBuilder builder = new StringBuilder("");
      builder.append(removeSpace(NoteName.toNoteName(
              intNote(musicEditor.getMinPitch() + i - 1)).toString()));
      builder.append(octave(musicEditor.getMinPitch() + i - 1));
      str.add(i, Utils.stringCenter(builder.toString(), 5));
    }
    return str;
  }

  /**
   * Calculate the octave number based on the note in the column of the rendered grid.
   *
   * @param position the x position in the grid
   * @return the octave
   */
  private int octave(int position) {
    return (int) (Math.ceil(((double) (position - intNote(position)) / 12))) - 1;
  }

  /**
   * Calculate the NoteName in term of number based on the position in the grid.
   *
   * @param position the x position in the grid
   * @return the NoteName representation of number
   */
  private int intNote(int position) {
    return (position % 12);
  }

  /**
   * Remove the empty space place holder of natural note.
   *
   * @param string the note name in string
   * @return the string without " " placeholder
   */
  private String removeSpace(String string) {
    if (string.substring(1, 2).equals(" ")) {
      return string.substring(0, 1);
    }
    return string;
  }

  //@Todo write the getState method
  @Override
  public void initialize() throws Exception {
    int maxBeat = musicEditor.getLastBeat();
    int minPitch = musicEditor.getMinPitch();
    int maxPitch = musicEditor.getMaxPitch();
    this.view = initView(maxBeat, minPitch, maxPitch);

    for (int i = 0; i <= maxBeat; i++) {
      final int TEMP = i;
      try {
        musicEditor.getAllNotesAt(i).values()
                .forEach(x -> x.forEach(note -> {
                  int notePos = this.notePosition(note.toString());
                  this.view.get(TEMP).remove(notePos);
                  this.view.get(TEMP).add(notePos, NotePlay.NOTE_PLAY.toString());
                  for (int duration = 1; duration < note.getBeat(); duration++) {
                    if (this.view.get(TEMP + duration).get(notePos)
                            .equals(NotePlay.NOTE_REST.toString())
                            && !this.view.get(TEMP + duration).get(notePos)
                            .equals(NotePlay.NOTE_PLAY.toString())) {
                      this.view.get(TEMP + duration).remove(notePos);
                      this.view.get(TEMP + duration).add(notePos, NotePlay.NOTE_SUSTAIN.toString());
                    }
                  }
                }));

      }
      catch (Exception e) {
        continue;
      }
    }
    tryCatchAppendableIO(appendable, toString());
  }

  @Override
  public long getCurrentTick() {
    return -1;
  }

  @Override
  public void move(long tick) {
    return;
  }

  @Override
  public void pause() {
    return;
  }

  @Override
  public void resume() {
    return;
  }

  @Override
  public void scrollHorizontal(int unit) {
    return;
  }

  @Override
  public void scrollVertical(int unit) {
    return;
  }

  @Override
  public void update() {
    try {
      this.initialize();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    return;
  }

  @Override
  public void addMouseListener(MouseListener mouseListener) {
    return;
  }

  @Override
  public void jumpToBeginning() {
    return;
  }

  @Override
  public void jumpToEnd() {
    return;
  }

  protected Appendable getAppendable() {
    return appendable;
  }

  List<List<String>> initView(int maxBeat, int minPitch, int maxPitch) {
    //System.out.println(maxBeat);
    List<List<String>> temp = new ArrayList<>();
    for (int beat = 0; beat < maxBeat; beat++) {
      List<String> strings = new ArrayList<>();
      //System.out.println(maxPitch - minPitch);
      for (int pitch = 0; pitch <= (maxPitch - minPitch); pitch++) {
        //System.out.print(maxBeat);
        strings.add(pitch, NotePlay.NOTE_REST.toString());
      }
      temp.add(beat, strings);
    }
    return temp;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("");
    produceName().forEach(x -> builder.append(x));
    builder.append("\n");
    //System.out.println(this.view.size());
    for (int i = 0; i < this.view.size(); i++) {
      List<String> x = this.view.get(i);
      builder.append(Utils.padding(i));
      x.forEach(str -> builder.append(str));
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * EFFECT: append an message to appendable.
   * A method to try catch IOException for calling appendable.
   *
   * @param appendable the appendable object
   * @param msg        the msg appending to appendable
   */
  private void tryCatchAppendableIO(Appendable appendable, String msg) {
    try {
      String temp = "\n" + msg;
      appendable.append(temp);

//      FileOutputStream oS = new FileOutputStream(new File("console.txt"));
//      oS.write(appendable.toString().getBytes());

//      File file = new File("console.txt");
//
//      // if file doesnt exists, then create it
//      if (!file.exists()) {
//        final boolean newFile = file.createNewFile();
//      }
//
//      FileWriter fw = new FileWriter(file);
//      BufferedWriter bw = new BufferedWriter(fw);
//      bw.write(this.appendable.toString());
//      bw.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

}
