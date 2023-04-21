import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {


  final static Color BUTTON_GRAY = new Color(210,210,210);
  
  public ButtonPanel() {
    super((LayoutManager) new FlowLayout(FlowLayout.CENTER, 10, 10));
    setBackground(Color.white);
    setAlignmentY(0.5f);

    JMenuBar menuBar = new JMenuBar();

    JMenu propertyMenu = new JMenu("\ud83c\udfe0  Menu");
    propertyMenu.setSize(
      new Dimension(200, propertyMenu.getPreferredSize().height)
    );

    JMenuItem viewListingMenuItem = new JMenuItem("View Properties");
    JMenuItem addPropertyMenuItem = new JMenuItem("Register Property");
    JMenuItem managePropertyMenuItem = new JMenuItem("Manage Properties");

    viewListingMenuItem.addActionListener(new ViewPropertyListingListener());
    addPropertyMenuItem.addActionListener(new PropertyRegistrationListener());
    managePropertyMenuItem.addActionListener(new ManagePropertyListener());

    propertyMenu.add(addPropertyMenuItem);
    propertyMenu.add(managePropertyMenuItem);
    propertyMenu.add(viewListingMenuItem);

    JMenu userDocMenu = new JMenu("\u2753  Help");
    userDocMenu.setSize(
      new Dimension(100, userDocMenu.getPreferredSize().height)
    );

    JMenuItem viewDocumentationMenuItem = new JMenuItem(
      "\ud83d\udcd4 View Documentation"
    );
    JMenuItem contactMeMenuItem = new JMenuItem("\ud83d\udcde Contact Creator");

    viewDocumentationMenuItem.addActionListener(
      new ViewDocumentationListener()
    );

    contactMeMenuItem.addActionListener(new ContactMeListener());

    userDocMenu.add(viewDocumentationMenuItem);
    userDocMenu.add(contactMeMenuItem);

    menuBar.add(propertyMenu);
    menuBar.add(userDocMenu);

    add(menuBar, BorderLayout.NORTH);
  }

  private static class PropertyRegistrationListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      new PropertyRegistrationGUI();
    }
  }

  private static class ManagePropertyListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      new PropertyManagerGUI();
    }
  }

  private static class ViewPropertyListingListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      new ViewPropertyGUI();
    }
  }

  private static class ViewDocumentationListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      try {
        File file = new File(
          System.getProperty("user.dir") + "\\user-doc\\documentation.docx"
        );
        Desktop.getDesktop().open(file);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private static class ContactMeListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JFrame frame = new JFrame("Contact Me");
      frame.setSize(500, 200);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      JPanel panel = new JPanel(new FlowLayout());
      frame.add(panel);

      JLabel githubLabel = new JLabel("");
      ImageIcon githubIcon = new ImageIcon(
        System.getProperty("user.dir") +
        "\\resources\\contact-icons\\github.png"
      );
      JLabel githubIconLabel = new JLabel(githubIcon);
      JLabel githubUrlLabel = new JLabel(
        "<html><a href='https://github.com/JamarTG'>https://github.com/JamarTG</a></html>"
      );
      githubUrlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
      githubUrlLabel.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            try {
              Desktop
                .getDesktop()
                .browse(new URI("https://github.com/JamarTG"));
            } catch (IOException | URISyntaxException ex) {
              ex.printStackTrace();
            }
          }
        }
      );

      JPanel githubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      githubPanel.add(githubLabel);
      githubPanel.add(githubIconLabel);
      githubPanel.add(githubUrlLabel);
      panel.add(githubPanel);

      JLabel phoneLabel = new JLabel("");
      ImageIcon phoneIcon = new ImageIcon(
        System.getProperty("user.dir") + "\\resources\\contact-icons\\phone.png"
      );
      JLabel phoneIconLabel = new JLabel(phoneIcon);
      JLabel phoneNumLabel = new JLabel("+1 (876) 548-7437");

      JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      phonePanel.add(phoneLabel);
      phonePanel.add(phoneIconLabel);
      phonePanel.add(phoneNumLabel);
      panel.add(phonePanel);

      JPanel emptyPanel = new JPanel(); 
      panel.add(emptyPanel);

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      JButton copyBtn = new JButton("Copy #");
      
      copyBtn.setBackground(BUTTON_GRAY);
      copyBtn.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            StringSelection selection = new StringSelection(
              "+1 (876) 548-7437"
            );
            Clipboard clipboard = Toolkit
              .getDefaultToolkit()
              .getSystemClipboard();
            clipboard.setContents(selection, null);
          }
        }
      );

      JButton okBtn = new JButton("OK");
      okBtn.setBackground(BUTTON_GRAY);
      okBtn.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            frame.dispose();
          }
        }
      );
      buttonPanel.add(copyBtn);
      buttonPanel.add(okBtn);
      panel.add(buttonPanel);

      frame.setVisible(true);
    }
  }
}
