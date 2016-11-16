package cs3500.music.view;

/**
 * Remove existing notes from a composition via some combination of keyboard and mouse
 * Add new notes of various lengths to a composition via some combination of keyboard, mouse and user inputs through the GUI
 * Scroll through a composition with the arrow keys (and scrollbars, if you have them)
 * Jump to the beginning or end of the composition, via the Home or End keys
 */
public interface GuiView extends IMusicEditorView {
  // TODO Lol idk if this is right HAHAHAHAH MY LIFE IS IN SHAMBLES
  void scrollLeft();
  void scrollRight();
}
