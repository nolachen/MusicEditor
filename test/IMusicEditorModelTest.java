//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import cs3500.music.model.IMusicEditorModel;
//import cs3500.music.model.MusicEditorModel;
//import cs3500.music.model.Note;
//import cs3500.music.model.Pitch;
//
///**
// * Tests for {@link IMusicEditorModel}.
// */
//public class IMusicEditorModelTest {
//  private IMusicEditorModel model;
//  Note g3b0 = new Note(Pitch.G, 3, 0, 7);
//  Note c4b4 = new Note(Pitch.C, 4, 4, 2);
//  Note g3b16 = new Note(Pitch.G, 3, 16, 10);
//  Note g3b24 = new Note(Pitch.G, 3, 24, 2);
//
//  @Before
//  public void setup() {
//    this.model = new MusicEditorModel();
//  }
//
//  @Test
//  public void testAdd() {
//    // basic tests for adding notes to the model
//
//    model.add(g3b0);
//
//    assertEquals("   G3 \n" +
//            "0  X  \n" +
//            "1  |  \n" +
//            "2  |  \n" +
//            "3  |  \n" +
//            "4  |  \n" +
//            "5  |  \n" +
//            "6  |  \n", model.getTextRendering());
//
//    List<Note> notes = new ArrayList<>();
//    notes.add(g3b0);
//    assertEquals(notes, model.getAllNotes());
//
//    model.add(c4b4);
//
//    assertEquals("   G3  G#3   A3  A#3   B3   C4 \n" +
//            "0  X                           \n" +
//            "1  |                           \n" +
//            "2  |                           \n" +
//            "3  |                           \n" +
//            "4  |                        X  \n" +
//            "5  |                        |  \n" +
//            "6  |                           \n", model.getTextRendering());
//
//    model.add(g3b16);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0  X                           \n" +
//            " 1  |                           \n" +
//            " 2  |                           \n" +
//            " 3  |                           \n" +
//            " 4  |                        X  \n" +
//            " 5  |                        |  \n" +
//            " 6  |                           \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  |                           \n" +
//            "25  |                           \n", model.getTextRendering());
//
//  }
//
//  @Test
//  public void testAddOverlap() {
//    // test adding a note that overlaps with an existing note (later note added last)
//
//    model.add(g3b0);
//    model.add(c4b4);
//    model.add(g3b16);
//    model.add(g3b24);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0  X                           \n" +
//            " 1  |                           \n" +
//            " 2  |                           \n" +
//            " 3  |                           \n" +
//            " 4  |                        X  \n" +
//            " 5  |                        |  \n" +
//            " 6  |                           \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  X                           \n" +
//            "25  |                           \n", model.getTextRendering());
//  }
//
//  @Test
//  public void testAddOverlap2() {
//    // test adding a note that overlaps with an existing note (later note added first)
//
//    model.add(g3b24);
//    model.add(g3b16);
//
//    assertEquals("    G3 \n" +
//            " 0     \n" +
//            " 1     \n" +
//            " 2     \n" +
//            " 3     \n" +
//            " 4     \n" +
//            " 5     \n" +
//            " 6     \n" +
//            " 7     \n" +
//            " 8     \n" +
//            " 9     \n" +
//            "10     \n" +
//            "11     \n" +
//            "12     \n" +
//            "13     \n" +
//            "14     \n" +
//            "15     \n" +
//            "16  X  \n" +
//            "17  |  \n" +
//            "18  |  \n" +
//            "19  |  \n" +
//            "20  |  \n" +
//            "21  |  \n" +
//            "22  |  \n" +
//            "23  |  \n" +
//            "24  X  \n" +
//            "25  |  \n", model.getTextRendering());
//  }
//
//  @Test
//  public void testRemove() {
//    // basic test from removing notes from the model
//
//    model.add(g3b0);
//    model.add(c4b4);
//    model.add(g3b16);
//    model.add(g3b24);
//
//    model.remove(g3b0);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0                              \n" +
//            " 1                              \n" +
//            " 2                              \n" +
//            " 3                              \n" +
//            " 4                           X  \n" +
//            " 5                           |  \n" +
//            " 6                              \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  X                           \n" +
//            "25  |                           \n", model.getTextRendering());
//
//    assertTrue(model.getAllNotes().contains(c4b4));
//    model.remove(c4b4);
//
//    assertEquals("    G3 \n" +
//            " 0     \n" +
//            " 1     \n" +
//            " 2     \n" +
//            " 3     \n" +
//            " 4     \n" +
//            " 5     \n" +
//            " 6     \n" +
//            " 7     \n" +
//            " 8     \n" +
//            " 9     \n" +
//            "10     \n" +
//            "11     \n" +
//            "12     \n" +
//            "13     \n" +
//            "14     \n" +
//            "15     \n" +
//            "16  X  \n" +
//            "17  |  \n" +
//            "18  |  \n" +
//            "19  |  \n" +
//            "20  |  \n" +
//            "21  |  \n" +
//            "22  |  \n" +
//            "23  |  \n" +
//            "24  X  \n" +
//            "25  |  \n", model.getTextRendering());
//
//    assertFalse(model.getAllNotes().contains(c4b4));
//
//  }
//
//  @Test
//  public void testRemoveOverlap() {
//    // test removing an overlapping note from the model
//
//    model.add(g3b0);
//    model.add(c4b4);
//    model.add(g3b16);
//    model.add(g3b24);
//
//    model.remove(g3b16);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0  X                           \n" +
//            " 1  |                           \n" +
//            " 2  |                           \n" +
//            " 3  |                           \n" +
//            " 4  |                        X  \n" +
//            " 5  |                        |  \n" +
//            " 6  |                           \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16                              \n" +
//            "17                              \n" +
//            "18                              \n" +
//            "19                              \n" +
//            "20                              \n" +
//            "21                              \n" +
//            "22                              \n" +
//            "23                              \n" +
//            "24  X                           \n" +
//            "25  |                           \n", model.getTextRendering());
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testBadRemove() {
//    // test trying to remove a note that doesn't exist in the model
//    model.add(g3b0);
//    model.add(g3b16);
//
//    model.remove(c4b4);
//  }
//
//  @Test
//  public void testNegOctave() {
//    // test adding a note with negative octave
//
//    Note tester = new Note(Pitch.A, -2, 2, 5);
//    model.add(tester);
//
//    assertEquals("  A-2 \n" +
//            "0     \n" +
//            "1     \n" +
//            "2  X  \n" +
//            "3  |  \n" +
//            "4  |  \n" +
//            "5  |  \n" +
//            "6  |  \n", model.getTextRendering());
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testBadStartTime() {
//    // test trying to make a note with an illegal start time < 0
//
//    Note bad = new Note(Pitch.B, 2, -1, 5);
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testBadDuration1() {
//    // test trying to make a note with an illegal duration <= 0
//    Note bad = new Note(Pitch.B, 2, 2, 0);
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testBadDuration2() {
//    // test trying to make an ote with an illegal duration
//
//    Note bad = new Note(Pitch.B, 2, 2, -1);
//  }
//
//  @Test
//  public void testSimultaneous() {
//    // test the playSimultaneously method
//
//    model.add(c4b4);
//    model.add(g3b16);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0                              \n" +
//            " 1                              \n" +
//            " 2                              \n" +
//            " 3                              \n" +
//            " 4                           X  \n" +
//            " 5                           |  \n" +
//            " 6                              \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  |                           \n" +
//            "25  |                           \n", model.getTextRendering());
//
//    IMusicEditorModel model2 = new MusicEditorModel();
//    model2.add(g3b24);
//    model2.add(g3b0);
//
//    model.playSimultaneously(model2);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0  X                           \n" +
//            " 1  |                           \n" +
//            " 2  |                           \n" +
//            " 3  |                           \n" +
//            " 4  |                        X  \n" +
//            " 5  |                        |  \n" +
//            " 6  |                           \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  |                           \n" +
//            "25  |                           \n", model.getTextRendering());
//  }
//
//  @Test
//  public void testConsecutive() {
//    // test playConsecutively method
//
//    model.add(c4b4);
//    model.add(g3b16);
//
//    IMusicEditorModel model2 = new MusicEditorModel();
//    model2.add(g3b0);
//
//    model.playConsecutively(model2);
//
//    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
//            " 0                              \n" +
//            " 1                              \n" +
//            " 2                              \n" +
//            " 3                              \n" +
//            " 4                           X  \n" +
//            " 5                           |  \n" +
//            " 6                              \n" +
//            " 7                              \n" +
//            " 8                              \n" +
//            " 9                              \n" +
//            "10                              \n" +
//            "11                              \n" +
//            "12                              \n" +
//            "13                              \n" +
//            "14                              \n" +
//            "15                              \n" +
//            "16  X                           \n" +
//            "17  |                           \n" +
//            "18  |                           \n" +
//            "19  |                           \n" +
//            "20  |                           \n" +
//            "21  |                           \n" +
//            "22  |                           \n" +
//            "23  |                           \n" +
//            "24  |                           \n" +
//            "25  X                           \n" +
//            "26  |                           \n" +
//            "27  |                           \n" +
//            "28  |                           \n" +
//            "29  |                           \n" +
//            "30  |                           \n" +
//            "31  |                           \n", model.getTextRendering());
//  }
//
//  @Test
//  public void testTextOutput() {
//    // test the text rendering of the model for an empty model
//
//    assertEquals("", model.getTextRendering());
//
//    // the other methods sufficiently test the functionality of the method
//  }
//
//  @Test
//  public void testGetAllNotes() {
//    // test getAllNotes
//
//    model.add(g3b0);
//    model.add(c4b4);
//    model.add(g3b16);
//    model.add(g3b24);
//
//    assertEquals("[G3, C4]", model.getAllNotes().toString());
//  }
//
//
//}