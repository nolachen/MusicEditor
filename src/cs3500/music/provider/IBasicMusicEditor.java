package cs3500.music.provider;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The Music editor.
 */
public interface IBasicMusicEditor<K> {

  /**
   * EFFECT: Add a note to a the editor.
   *
   * @param note the note
   * @return true if the beat is successfully added
   */
  public boolean add(K note);

  /**
   * EFFECT: Remove will remove all the note at start at a starting Duraiton.
   * This position is suppose to allow the client to remove a note at a in the stack of
   * same starting point. This simple model will not support that. Therefore position
   * field will be ignored at this point.
   *
   * @param note the noteFields
   * @return the list of notes are being removed
   */
  public boolean remove(K note);

  /**
   * EFFECT: Change the duration of the note.
   * Return true if the operation is completed successfully.
   *
   * @param note     the note
   * @param duration the the duration of the beat
   * @param volume   the volume  the note
   * @return return the true if the beat is successfully added
   */
  public boolean edit(K note, int duration, int volume);

  /**
   * EFFECT: Merge the other piece into one piece.
   *
   * @param piece         the IBasicMusic editor
   * @param isConsecutive play consecutive with the
   */
  public void merge(IBasicMusicEditor<K> piece, boolean isConsecutive);

  SortedMap<Integer, List<INote>> getAllNotesAt(int beatNum);

  /**
   * Integer -> is the beat number.
   * SortedMap Integer -> the pitch number.
   *
   * @return the whole map of notes
   */
  public SortedMap<Integer, SortedMap<Integer, List<INote>>> composition();

  //not rdy for use (null)
//  public SortedMap<Integer, SortedMap<Integer, List<Integer>>> getDrawComposition();

  /**
   * Return the minimum Pitch.
   *
   * @return min pitch
   */
  int getMinPitch();

  /**
   * Return the maximun Pitch.
   *
   * @return the max pitch
   */
  int getMaxPitch();

  /**
   * Return the Tempo in MPQ.
   *
   * @return tempo in MPQ
   */
  int getTempo();

  /**
   * Return the last starting beat.
   *
   * @return the beat
   */
  int getLastStartBeat();

  /**
   * Return the last beat in the composition.
   *
   * @return the last beat in int composition.
   */
  int getLastBeat();

  /**
   * True if the type viewEditor. Safe casting.
   *
   * @return true if an ViewEditor
   */
  boolean isViewEditor();
}
