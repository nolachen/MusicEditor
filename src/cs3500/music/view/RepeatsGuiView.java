package cs3500.music.view;


import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * This class is an extension of {@link GuiViewImpl} to support repeats in the model.
 */
public class RepeatsGuiView extends GuiViewImpl {
  private JButton addRepeatButton;

  /**
   * Constructor for the Repeats GUI view implementation.
   *
   * @param viewModel the given view model
   */
  public RepeatsGuiView(RepeatsViewModel viewModel) {
    super(viewModel);
  }

  @Override
  protected MusicPanel createDisplayPanel(IViewModel viewModel) {
    return new RepeatsMusicPanel((RepeatsViewModel) viewModel);
  }

  @Override
  protected JPanel createInputPanel() {
    JPanel inputPanel = super.createInputPanel();
    addRepeatButton = new JButton("Add repeat");
    inputPanel.add(addRepeatButton);

    return inputPanel;
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    super.addActionListener(actionListener);
    this.addRepeatButton.addActionListener(actionListener);
  }
}
