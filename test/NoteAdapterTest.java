import cs3500.music.adapter.NoteAdapter;

import cs3500.music.model.INote;

import cs3500.music.model.Note;

import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the note adapter class.
 */
public class NoteAdapterTest {
  INote note;
  NoteAdapter adapter;

  @Before
  public void initCond() {
    this.note = new Note(1, 2, 4, 12, 20);
    this.adapter = new NoteAdapter(this.note);
  }

  // tests the get start Duration method.
  @Test
  public void testStartBeat() {
    assertEquals(this.adapter.getStartDuration(), this.note.getStartBeat());
  }

  // tests the get octave method.
  @Test
  public void testOctave() {
    assertEquals(this.adapter.getOctave(), this.note.getOctave());
  }

  // tests the getBeat method.
  @Test
  public void testDuration() {
    assertEquals(this.adapter.getBeat(), this.note.getDuration());
  }

  // tests the getChannel method.
  @Test
  public void testChannel() {
    assertEquals(this.adapter.getChannel(), this.note.getInstrument());
  }

  // tests the getNoteName method.
  @Test
  public void testNoteName() {
    assertEquals(this.adapter.getNoteName().toString(), this.note.getPitch() + " ");
  }

  // tests the getVolume method.
  @Test
  public void testVolume() {
    assertEquals(this.adapter.getVolume(), this.note.getVolume());
  }

  // tests the isViewNote method.
  @Test
  public void testIsViewNote() {
    assertEquals(this.adapter.isViewNote(), false);
  }
}
