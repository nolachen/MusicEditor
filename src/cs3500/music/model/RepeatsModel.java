package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.List;

import java.util.TreeMap;

/**
 * A version of Music Editor Model that supports repeats.
 */
public class RepeatsModel extends MusicEditorModel{
  // INVARIANT: repeats will always be stored by their end in repeatsByEnd and by their begin in
  // repeatsByBegin. Adding & removing repeats will always remove from both tree maps.

  // list of repeats in this piece mapped to their end beat.
  TreeMap<Integer, Repeat> repeatsByEnd;

  // list of repeats in this piece mapped to their begin beat.
  TreeMap<Integer, Repeat> repeatsByBegin;

  /**
   * private constructor that initializes all the fields.
   * @param music map of notes in the composition.
   * @param tempo tempo of the composition.
   * @param repeatsByEnd map of repeats by end.
   */
  private RepeatsModel(TreeMap<Integer, List<INote>> music, int tempo,
                       TreeMap<Integer, Repeat> repeatsByEnd) {
    super(music, tempo);
    this.repeatsByEnd = repeatsByEnd;
    this.initializeRepeatsByBegin();
  }

  /**
   * Constructor that uses the composition builder to create the model.
   * @param builder Composition builder that creates the model.
   */
  public RepeatsModel(CompositionBuilder<IMusicEditorModel> builder) {
    super(builder);
    this.repeatsByEnd = new TreeMap<>();
    this.initializeRepeatsByBegin();
  }

  /**
   * Default constructor with no parameters, sets the tempo to 200000.
   */
  public RepeatsModel() {
    this(new TreeMap<>(), 200000, new TreeMap<>());
  }

  /**
   * Initializes the repeatsByBegin map to contain all of the repeats in repeatsByEnd.
   */
  private void initializeRepeatsByBegin() {
    if (this.repeatsByBegin == null) {
      this.repeatsByBegin = new TreeMap<>();
    }
    for (Repeat r : this.repeatsByEnd.values()) {
      this.repeatsByBegin.put(r.getBegin(), r);
    }
  }

  /**
   * Returns the repeat with an end at the given beat, or null if there is none.
   * @param beat beat to retrieve the repeat end at
   * @return the repeat with end at the beat
   */
  public Repeat getRepeatAtEnd(int beat) {
    return this.repeatsByEnd.get(beat);
  }

  /**
   * Returns the repeat with a begin at the given beat, or null if there is none.
   * @param beat beat to retrieve the repeat begin at
   * @return the repeat with begin at the beat
   */
  public Repeat getRepeatAtBegin(int beat) {
    return this.repeatsByBegin.get(beat);
  }

  /**
   * Adds the given repeat to this model's repeats.
   * @param repeat the repeat to add
   */
  public void addRepeat(Repeat repeat) {
    if (this.repeatsByEnd.containsKey(repeat.getEnd()) ||
            this.repeatsByBegin.containsKey(repeat.getBegin())) {
      throw new IllegalArgumentException("A repeat already exists here!");
    }
    else {
      this.repeatsByEnd.put(repeat.getEnd(), repeat);
      this.repeatsByBegin.put(repeat.getBegin(), repeat);
    }
  }

  /**
   * Removes the given repeat from this model, and throws an exception if it doesn't exist.
   * @param repeat the repeat to remove
   */
  public void removeRepeat(Repeat repeat) {
    if (!this.repeatsByEnd.containsValue(repeat) ||
            !this.repeatsByBegin.containsValue(repeat)) {
      throw new IllegalArgumentException("The repeat to remove doesn't exist");
    }
    else {
      this.repeatsByEnd.remove(repeat.getEnd());
      this.repeatsByBegin.remove(repeat.getBegin());
    }
  }

  /**
   * Removes the repeat with an end at the given beat from this model, and throws an exception if
   * no such repeat exists.
   * @param beat the beat at which to remove the repeat
   */
  public void removeRepeatAtEnd(int beat) {
    if (!this.repeatsByEnd.containsKey(beat)) {
      throw new IllegalArgumentException("There is no repeat at the given beat");
    }
    else {
      Repeat removed = this.repeatsByEnd.remove(beat);
      this.repeatsByBegin.remove(removed.getBegin(), removed);
    }
  }

}
