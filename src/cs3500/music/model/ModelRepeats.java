package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.Collections;
import java.util.List;

import java.util.TreeMap;

/**
 * A version of Music Editor Model that supports repeats.
 */
public class ModelRepeats extends MusicEditorModel{

  // list of repeats in this piece.
  List<Repeat> repeats;

  /**
   * private constructor that initializes all the fields.
   * @param music list of notes in the composition.
   * @param tempo tempo of the composition.
   * @param repeats list of repeats in this composition.
   */
  private ModelRepeats(TreeMap<Integer, List<INote>> music, int tempo, List<Repeat> repeats) {
    super(music, tempo);
    this.repeats = repeats;
  }

  // todo do we want to add a reapeats builder to an extended version of the composition builder
  /**
   * Constructor that uses the composition builder to create the model.
   * @param builder Composition builder that creates the model.
   * @param repeats List of repeats in this composition.
   */
  public ModelRepeats(CompositionBuilder<IMusicEditorModel> builder, List<Repeat> repeats) {
    super(builder);
    this.repeats = repeats;
  }

  /**
   * Returns a sorted list of repeats.
   * @return a list of the repeats sorted by their location.
   */
  public List<Repeat> getRepeats() {
    Collections.sort(this.repeats);
    return this.repeats;
  }



}
