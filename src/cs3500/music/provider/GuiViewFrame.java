package cs3500.music.provider;

import java.awt.ScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import cs3500.music.provider.IBasicMusicEditor;
import cs3500.music.provider.INote;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IGuiView {
  // Define constants for the various dimensions
  private static final int CANVAS_WIDTH = 1400;
  private static final int CANVAS_HEIGHT = 1000;

  private final ConcreteGuiViewPanel displayPanel;
  private final ScrollPane scr;
  /**
   * Creates new GuiView.
   */
  protected GuiViewFrame(IBasicMusicEditor<INote> musicEditor) {
    displayPanel = new ConcreteGuiViewPanel(musicEditor);
    this.scr = new ScrollPane();
    scr.add(displayPanel);
    this.setTitle("Music Editor");
    this.displayPanel.setFocusable(true);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.add(scr);
    this.pack();
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public long getCurrentTick() {
    return 0;
  }

  @Override
  public void move(long tick) {
    if (tick % 50 == 0) {
      scr.setScrollPosition((int)tick * 25, 0);

      System.out.println(scr.getScrollPosition().getX() + " " + scr.getScrollPosition().getY());
    }
    this.displayPanel.paintRec(scr.getBounds());
    this.displayPanel.current(scr.getScrollPosition().getX());
    /*
    System.out.println(scr.getBounds().width + " " + scr.getBounds().height + " "
            + scr.getBounds().getMinX() + " " + scr.getBounds().getMaxX() + " " + scr.getBounds().
            getX());
    */
    this.displayPanel.move(tick);
  }

  @Override
  public void pause() {
    return;
  }

  @Override
  public void resume() {
    return;
  }

  @Override
  public void scrollHorizontal(int unit) {
    this.displayPanel.paintRec(scr.getBounds());
    scr.setScrollPosition(scr.getX() + unit, scr.getY());
  }

  @Override
  public void scrollVertical(int unit) {
    this.displayPanel.paintRec(scr.getBounds());
    scr.setScrollPosition(scr.getX(), scr.getY() + unit);
  }

  @Override
  public void update() {
    this.displayPanel.paintRec(scr.getBounds());
    displayPanel.update();
  }

  @Override
  public void jumpToBeginning() {
    this.displayPanel.paintRec(scr.getBounds());
    displayPanel.jumpToBeginning();
  }

  @Override
  public void jumpToEnd() {
    this.displayPanel.paintRec(scr.getBounds());
    displayPanel.jumpToEnd();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
  }

  @Override
  public void addMouseListener(MouseListener mouseListener)  {
    this.displayPanel.addMouseListener(mouseListener);
  }

  @Override
  public void removeMouseListener(MouseListener l) {
    this.displayPanel.removeMouseListener(l);
  }

  @Override
  public void addActionListener(ActionListener listener)  {
    this.addActionListener(listener);
  }

  @Override
  public void addKeyListener(KeyListener listener)  {
    this.displayPanel.addKeyListener(listener);
  }

}
