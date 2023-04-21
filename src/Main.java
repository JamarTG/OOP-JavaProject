import java.awt.*;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    MainFrame frame = new MainFrame();
    
    JPanel buttonPanel = new ButtonPanel();    
    ShowcasePanel showcasePanel = new ShowcasePanel();

    frame.add(buttonPanel, BorderLayout.BEFORE_FIRST_LINE);
    frame.add(showcasePanel, BorderLayout.CENTER);
    frame.setVisible(true);
  }
}