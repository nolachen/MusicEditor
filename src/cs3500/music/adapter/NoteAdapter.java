package cs3500.music.adapter;

import cs3500.music.provider.NoteName;

/**
 * Implements the provided target, their note interface, with our adaptee, INote.
 */
public class NoteAdapter implements cs3500.music.provider.INote {
  private final cs3500.music.model.INote adaptee;

  public NoteAdapter(cs3500.music.model.INote adaptee) {
    this.adaptee = adaptee;
  }


  @Override
  public NoteName getNoteName() {
    return NoteName.toNoteName(adaptee.getPitch().ordinal());
  }

  @Override
  public int getOctave() {
    return adaptee.getOctave();
  }

  @Override
  public int getStartDuration() {
    return adaptee.getStartBeat();
  }

  @Override
  public int getBeat() {
    return adaptee.getDuration();
  }

  @Override
  public int getChannel() {
    return adaptee.getInstrument();
  }

  @Override
  public int getVolume() {
    return adaptee.getVolume();
  }

  @Override
  public boolean isViewNote() {
    return false;
  }
}
