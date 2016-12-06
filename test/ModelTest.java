import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.ImmutableNote;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Tests for {@link IMusicEditorModel}.
 */
public class ModelTest {
  private IMusicEditorModel model;

  private Note g5b0 = new Note(Pitch.G, 5, 0, 7);
  private Note c4b4 = new Note(Pitch.C, 4, 4, 2);
  private Note g3b16 = new Note(Pitch.G, 3, 16, 10);
  private Note g3b24 = new Note(Pitch.G, 3, 24, 2);
  private Note f3b0 = new Note(Pitch.F, 3, 0, 4);

  private ImmutableNote ic4b4 = new ImmutableNote(c4b4);

  @Before
  public void setup() {
    this.model = new MusicEditorModel();
  }

  @Test
  public void testTempo() {
    // tests for getting the tempo
    assertEquals(200000, model.getTempo());
  }

  @Test
  public void testAdd() {
    // basic tests for adding notes to the model

    model.add(g5b0);

    assertEquals(1 , model.getAllNotes().size());
    assertEquals(6, model.length());
    assertEquals("[G5]", model.getAllNotes().toString());
    assertEquals("[G5]", model.getNotesAtBeat(0).toString());

    model.add(c4b4);

    assertEquals(2, model.getAllNotes().size());
    assertEquals(6, model.length());
    assertEquals("[C4, G5]", model.getAllNotes().toString());
    assertEquals("[C4, G5]", model.getNotesAtBeat(4).toString());

    model.add(g3b16);

    assertEquals(25, model.length());
    assertEquals("[G3, C4, G5]", model.getAllNotes().toString());
    assertEquals("[G5]", model.getNotesAtBeat(0).toString());
    assertEquals("[G3]", model.getNotesAtBeat(16).toString());
  }

  @Test
  public void testAddOverlap() {
    // test adding a note that starts at the same time as an existing note

    model.add(g5b0);
    model.add(f3b0);

    assertEquals(6, model.length());
    assertEquals("[F3, G5]", model.getAllNotes().toString());
    assertEquals("[F3, G5]", model.getNotesAtBeat(0).toString());
  }

  @Test
  public void testRemove() {
    // basic test from removing notes from the model

    model.add(g5b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);
    model.add(f3b0);

    assertEquals("[F3, G5]", model.getNotesAtBeat(0).toString());
    assertEquals(5, model.getAllNotes().size());
    assertEquals(25, model.length());
    assertEquals("[F3, G3, G3, C4, G5]", model.getAllNotes().toString());

    model.remove(g5b0);

    assertEquals("[F3]", model.getNotesAtBeat(0).toString());
    assertEquals(4, model.getAllNotes().size());
    assertEquals(25, model.length());
    assertEquals("[F3, G3, G3, C4]", model.getAllNotes().toString());

    assertTrue(model.getAllNotes().toString().contains(ic4b4.toString()));
    model.remove(c4b4);
    assertFalse(model.getAllNotes().toString().contains(ic4b4.toString()));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadRemove() {
    // test trying to remove a note that doesn't exist in the model
    model.add(g5b0);
    model.add(g3b16);

    model.remove(c4b4);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadStartTime() {
    // test trying to make a note with an illegal start time < 0

    Note bad = new Note(Pitch.B, 2, -1, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadDuration2() {
    // test trying to make an note with an illegal duration

    Note bad = new Note(Pitch.B, 2, 2, -1);
  }

  @Test
  public void testSimultaneous() {
    // test the playSimultaneously method

    model.add(c4b4);
    model.add(g3b16);
    model.add(f3b0);

    assertEquals(3, model.getAllNotes().size());
    assertEquals(25, model.length());

    IMusicEditorModel model2 = new MusicEditorModel();
    model2.add(g3b24);
    model2.add(c4b4);
    model2.add(g5b0);

    model.playSimultaneously(model2);

    assertEquals(6, model.getAllNotes().size());
    assertEquals("[F3, G3, G3, C4, C4, G5]", model.getAllNotes()
            .toString());
    assertEquals(25, model.length());
    assertEquals("[G5, F3]", model.getNotesAtBeat(0).toString());
  }

  @Test
  public void testConsecutive() {
    // test playConsecutively method

    model.add(c4b4);
    model.add(g3b16);

    assertEquals(2, model.getAllNotes().size());
    assertEquals(25, model.length());

    IMusicEditorModel model2 = new MusicEditorModel();
    model2.add(g5b0);

    model.playConsecutively(model2);

    assertEquals(25 + model2.length(), model.length());
    assertEquals(3, model.getAllNotes().size());
    assertEquals("[G3, C4, G5]", model.getAllNotes().toString());
  }

  @Test
  public void testEmptyMOdel() {
    // test an empty model

    assertEquals(0, model.length());
    assertEquals(0, model.getAllNotes().size());
  }

  @Test
  public void testGetAllNotes() {
    // test getAllNotes

    model.add(g3b24);
    model.add(g5b0);
    model.add(c4b4);
    model.add(g3b16);

    assertEquals("[G3, G3, C4, G5]", model.getAllNotes().toString());
  }


}