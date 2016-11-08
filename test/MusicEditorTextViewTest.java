import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.ViewModel;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicEditorGuiView;
import cs3500.music.view.MusicEditorTextView;

import static org.junit.Assert.*;

/**
 * Tests for {@link MusicEditorTextView}.
 */
public class MusicEditorTextViewTest {
  private IMusicEditorModel model;
  private ViewModel viewModel;
  private IMusicEditorView view;
  private Appendable out;

  Note g3b0 = new Note(Pitch.G, 3, 0, 7);
  Note c4b4 = new Note(Pitch.C, 4, 4, 2);
  Note g3b16 = new Note(Pitch.G, 3, 16, 10);
  Note g3b24 = new Note(Pitch.G, 3, 24, 2);

  @Before
  public void setup() {
    this.out = new StringBuilder();
    this.model = new MusicEditorModel();
    this.viewModel = new ViewModel(model);
    this.view = new MusicEditorTextView(viewModel, out);
  }

  @Test
  public void testAdd() {
    // basic tests for adding notes to the model

    model.add(g3b0);
    view.makeVisible();

    assertEquals("   G3 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n" +
            "4  |  \n" +
            "5  |  \n" +
            "6  |  \n", out.toString());

    model.add(c4b4);
    out = new StringBuilder();
    view = new MusicEditorTextView(viewModel, out);
    view.makeVisible();

    assertEquals("   G3  G#3   A3  A#3   B3   C4 \n" +
            "0  X                           \n" +
            "1  |                           \n" +
            "2  |                           \n" +
            "3  |                           \n" +
            "4  |                        X  \n" +
            "5  |                        |  \n" +
            "6  |                           \n", out.toString());

    model.add(g3b16);
    out = new StringBuilder();
    view = new MusicEditorTextView(viewModel, out);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0  X                           \n" +
            " 1  |                           \n" +
            " 2  |                           \n" +
            " 3  |                           \n" +
            " 4  |                        X  \n" +
            " 5  |                        |  \n" +
            " 6  |                           \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  |                           \n" +
            "25  |                           \n", out.toString());

  }

  @Test
  public void testAddOverlap() {
    // test adding a note that overlaps with an existing note (later note added last)

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0  X                           \n" +
            " 1  |                           \n" +
            " 2  |                           \n" +
            " 3  |                           \n" +
            " 4  |                        X  \n" +
            " 5  |                        |  \n" +
            " 6  |                           \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  X                           \n" +
            "25  |                           \n", out.toString());
  }

  @Test
  public void testAddOverlap2() {
    // test adding a note that overlaps with an existing note (later note added first)

    model.add(g3b24);
    model.add(g3b16);
    view.makeVisible();

    assertEquals("    G3 \n" +
            " 0     \n" +
            " 1     \n" +
            " 2     \n" +
            " 3     \n" +
            " 4     \n" +
            " 5     \n" +
            " 6     \n" +
            " 7     \n" +
            " 8     \n" +
            " 9     \n" +
            "10     \n" +
            "11     \n" +
            "12     \n" +
            "13     \n" +
            "14     \n" +
            "15     \n" +
            "16  X  \n" +
            "17  |  \n" +
            "18  |  \n" +
            "19  |  \n" +
            "20  |  \n" +
            "21  |  \n" +
            "22  |  \n" +
            "23  |  \n" +
            "24  X  \n" +
            "25  |  \n", out.toString());
  }

  @Test
  public void testRemove() {
    // basic test from removing notes from the model

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);

    model.remove(g3b0);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0                              \n" +
            " 1                              \n" +
            " 2                              \n" +
            " 3                              \n" +
            " 4                           X  \n" +
            " 5                           |  \n" +
            " 6                              \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  X                           \n" +
            "25  |                           \n", out.toString());

    model.remove(c4b4);
    out = new StringBuilder();
    view = new MusicEditorTextView(viewModel, out);
    view.makeVisible();

    assertEquals("    G3 \n" +
            " 0     \n" +
            " 1     \n" +
            " 2     \n" +
            " 3     \n" +
            " 4     \n" +
            " 5     \n" +
            " 6     \n" +
            " 7     \n" +
            " 8     \n" +
            " 9     \n" +
            "10     \n" +
            "11     \n" +
            "12     \n" +
            "13     \n" +
            "14     \n" +
            "15     \n" +
            "16  X  \n" +
            "17  |  \n" +
            "18  |  \n" +
            "19  |  \n" +
            "20  |  \n" +
            "21  |  \n" +
            "22  |  \n" +
            "23  |  \n" +
            "24  X  \n" +
            "25  |  \n", out.toString());

    assertFalse(model.getAllNotes().contains(c4b4));

  }

  @Test
  public void testRemoveOverlap() {
    // test removing an overlapping note from the model

    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);

    model.remove(g3b16);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0  X                           \n" +
            " 1  |                           \n" +
            " 2  |                           \n" +
            " 3  |                           \n" +
            " 4  |                        X  \n" +
            " 5  |                        |  \n" +
            " 6  |                           \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16                              \n" +
            "17                              \n" +
            "18                              \n" +
            "19                              \n" +
            "20                              \n" +
            "21                              \n" +
            "22                              \n" +
            "23                              \n" +
            "24  X                           \n" +
            "25  |                           \n", out.toString());
  }


  @Test
  public void testSimultaneous() {
    // test the playSimultaneously method

    model.add(c4b4);
    model.add(g3b16);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0                              \n" +
            " 1                              \n" +
            " 2                              \n" +
            " 3                              \n" +
            " 4                           X  \n" +
            " 5                           |  \n" +
            " 6                              \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  |                           \n" +
            "25  |                           \n", out.toString());

    IMusicEditorModel model2 = new MusicEditorModel();
    model2.add(g3b24);
    model2.add(g3b0);

    model.playSimultaneously(model2);
    out = new StringBuilder();
    view = new MusicEditorTextView(viewModel, out);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0  X                           \n" +
            " 1  |                           \n" +
            " 2  |                           \n" +
            " 3  |                           \n" +
            " 4  |                        X  \n" +
            " 5  |                        |  \n" +
            " 6  |                           \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  X                           \n" +
            "25  |                           \n", out.toString());
  }

  @Test
  public void testConsecutive() {
    // test playConsecutively method

    model.add(c4b4);
    model.add(g3b16);

    IMusicEditorModel model2 = new MusicEditorModel();
    model2.add(g3b0);

    model.playConsecutively(model2);

    out = new StringBuilder();
    view = new MusicEditorTextView(viewModel, out);
    view.makeVisible();

    assertEquals("    G3  G#3   A3  A#3   B3   C4 \n" +
            " 0                              \n" +
            " 1                              \n" +
            " 2                              \n" +
            " 3                              \n" +
            " 4                           X  \n" +
            " 5                           |  \n" +
            " 6                              \n" +
            " 7                              \n" +
            " 8                              \n" +
            " 9                              \n" +
            "10                              \n" +
            "11                              \n" +
            "12                              \n" +
            "13                              \n" +
            "14                              \n" +
            "15                              \n" +
            "16  X                           \n" +
            "17  |                           \n" +
            "18  |                           \n" +
            "19  |                           \n" +
            "20  |                           \n" +
            "21  |                           \n" +
            "22  |                           \n" +
            "23  |                           \n" +
            "24  |                           \n" +
            "25  |                           \n" +
            "26  X                           \n" +
            "27  |                           \n" +
            "28  |                           \n" +
            "29  |                           \n" +
            "30  |                           \n" +
            "31  |                           \n" +
            "32  |                           \n", out.toString());
  }

  @Test
  public void testEmptyModel() {
    // test the text rendering of the model for an empty model

    assertEquals("", out.toString());

  }


}