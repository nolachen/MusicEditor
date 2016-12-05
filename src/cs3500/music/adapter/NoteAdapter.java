package cs3500.music.adapter;

import cs3500.music.provider.NoteName;

/**
 * Implements the provided target {@link cs3500.music.provider.INote} interface,
 * and has the adaptee: our {@link cs3500.music.model.INote} interface.
 */
public class NoteAdapter implements cs3500.music.provider.INote {
  cs3500.music.model.INote adaptee;

  NoteAdapter(cs3500.music.model.INote adaptee) {
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
