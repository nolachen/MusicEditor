import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Tests for {@link IMusicEditorModel}.
 */
public class IMusicEditorModelTest {
  private MusicEditorModel model;

  @Before
  public void setup() {
    this.model = new MusicEditorModel();
  }

  @Test
  public void testGetTextRendering() {
    Note g3b0 = new Note(Pitch.G, 3, 0, 7);
    model.add(g3b0);
    Note c4b4 = new Note(Pitch.C, 4, 4, 2);
    model.add(c4b4);
    Note g3b16 = new Note(Pitch.G, 3, 16, 10);
    model.add(g3b16);

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
            "25  |                           \n", model.getTextRendering());

    Note g3b24 = new Note(Pitch.G, 3, 24, 2);
    model.add(g3b24);

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
            "25  |                           \n", model.getTextRendering());

  }

}