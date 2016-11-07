import org.junit.Before;
import org.junit.Test;

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
 * Created by nolachen on 11/5/16.
 */
public class MusicEditorTextViewTest {
  private IMusicEditorModel model;
  private ViewModel viewModel;
  private IMusicEditorView view;
  Note g3b0 = new Note(Pitch.G, 3, 0, 7, 1, 64);
  Note c4b4 = new Note(Pitch.C, 4, 4, 2, 1, 64);
  Note g3b16 = new Note(Pitch.G, 3, 16, 10, 1, 64);
  Note g3b24 = new Note(Pitch.G, 3, 24, 2, 1, 64);

  @Before
  public void setup() {
    this.model = new MusicEditorModel();
    this.viewModel = new ViewModel(model);
    this.view = new MusicEditorGuiView(viewModel);
  }

  @Test
  public void testStuff() {
    model.add(g3b0);
    model.add(c4b4);
    model.add(g3b16);
    model.add(g3b24);

    view.makeVisible();
    //assertEquals(view.print);
  }
  // TODO: test combining compositions (consecutive + simultaneous)

}