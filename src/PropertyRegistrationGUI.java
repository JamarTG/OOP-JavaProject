import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PropertyRegistrationGUI {
  private static String mainImagePath = "";
  private JFrame addPropertyFrame;
  private JTextField addressField;
  private JSpinner numBedroomsSpinner;
  private JSpinner numBathroomsSpinner;
  private JSpinner squareFootageSpinner;
  private JTextField priceTextField;
  private JTextField ownerNameField;
  private JTextField agentNameField;
  private ArrayList<String> previewImagePaths;
  private JComboBox forSaleRentComboBox;
  protected static final Font FONT = new Font("Sitka Display", Font.PLAIN, 15);
  protected JPanel panel;

  public PropertyRegistrationGUI() {
    previewImagePaths = new ArrayList<>();
    addPropertyFrame = new JFrame("Add Property for Sale/Rent");
    addPropertyFrame.setPreferredSize(new Dimension(500, 550));
    addPropertyFrame.setResizable(false);
    addPropertyFrame.setLocationRelativeTo(null);
    addPropertyFrame.setVisible(true);

    panel = new JPanel(new GridLayout(12, 2));
    panel.setBorder(
      BorderFactory.createTitledBorder(
        new LineBorder(Color.WHITE, 3),
        "Property Form",
        TitledBorder.CENTER,
        TitledBorder.DEFAULT_POSITION
      )
    );
    

    JPanel closePanel = new JPanel();
    
    JMenuBar closeMenuBar = new JMenuBar();
    JMenu closeMenu = new JMenu("Window");
    JMenuItem closeItem = new JMenuItem("Close");

    closeMenuBar.add(closeMenu);
    closeMenu.add(closeItem);
    closeItem.addActionListener(new CloseButtonListener());
    closePanel.add(closeMenuBar);
    panel.add(closePanel,BorderLayout.NORTH);

    
    addFieldToPanelWithMargin(
      "Address:",
      addressField = new JTextField("Address")
    );
    addressField.setPreferredSize(new Dimension(300, 30));
    addFieldToPanelWithMargin(
      "Number of bedrooms:",
      numBedroomsSpinner =
        new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1))
    );
    addFieldToPanelWithMargin(
      "Number of bathrooms:",
      numBathroomsSpinner =
        new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1))
    );
    addFieldToPanelWithMargin(
      "Square footage:",
      squareFootageSpinner =
        new JSpinner(new SpinnerNumberModel(1000, 0, Integer.MAX_VALUE, 1000))
    );
    addFieldToPanelWithMargin(
      "Price:",
      priceTextField = new JTextField("2000000")
    );
    priceTextField.setPreferredSize(new Dimension(300, 30));
    addFieldToPanelWithMargin(
      "Owner name:",
      ownerNameField = new JTextField("Jamari McFarlane")
    );
    ownerNameField.setPreferredSize(new Dimension(300, 30));
    addFieldToPanelWithMargin(
      "Agent name:",
      agentNameField = new JTextField("John Doe")
    );
    agentNameField.setPreferredSize(new Dimension(300, 30));

    JPanel saleRentPanel = new JPanel();
    
    saleRentPanel.add(new JLabel("Sale/Rent Options:"));
    saleRentPanel.add(forSaleRentComboBox = new JComboBox<>(new String[] { "For Sale", "For Rent" }));

    
    final Color BUTTON_GRAY = new Color(210,210,210);

	
    addFieldToPanelWithMargin("", saleRentPanel);

    JButton chooseMainImageButton = new JButton("Choose Main Image");
    chooseMainImageButton.addActionListener(new ChooseMainImageListener());
    chooseMainImageButton.setBackground(BUTTON_GRAY);

    JButton uploadButton = new JButton("Choose Preview Images");
    uploadButton.addActionListener(new UploadButtonListener());
    uploadButton.setBackground(BUTTON_GRAY);
    
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new SubmitButtonListener());
    submitButton.setBackground(BUTTON_GRAY);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(chooseMainImageButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPanel.add(uploadButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPanel.add(submitButton);

    
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(buttonPanel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    

    setFontForAllComponents(panel.getComponents(), FONT);
    
    addPropertyFrame.add(panel);
    addPropertyFrame.pack();
}

  private void addFieldToPanelWithMargin(
    String labelText,
    JComponent component
  ) {
	if(!(component instanceof JSpinner)) {
		  Border line = BorderFactory.createLineBorder(new Color(230,230,230) , 0); 
	      Border padding = BorderFactory.createEmptyBorder(7,7, 7, 7); 
	      Border compound = BorderFactory.createCompoundBorder(line, padding); 
	      component.setBorder(compound);	  
	}else {
		component.setBorder(null);
	}
	
    JPanel fieldPanel = new JPanel(new BorderLayout());
    JLabel label = new JLabel(labelText);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
    fieldPanel.add(label, BorderLayout.WEST);
    fieldPanel.add(component, BorderLayout.CENTER);
    fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel.add(fieldPanel);
    fieldPanel.setPreferredSize(new Dimension(400, 30));
  }

  private void setFontForAllComponents(Component[] components, Font font) {
    for (Component component : components) {
      component.setFont(font);
    }
  }

  private class SubmitButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      String address = addressField.getText();
      int numBedrooms = (int) numBedroomsSpinner.getValue();
      int numBathrooms = (int) numBathroomsSpinner.getValue();
      int squareFootage = (int) squareFootageSpinner.getValue();
      String price = priceTextField.getText();
      String ownerName = ownerNameField.getText();
      String agentName = agentNameField.getText();
      String mainImageSource = mainImagePath;
      ArrayList<String> previewImageSources = new ArrayList<>();

      for (String previewImage : previewImagePaths) {
        previewImageSources.add(previewImage.trim());
      }

      boolean forSale = forSaleRentComboBox
        .getSelectedItem()
        .equals("For Sale");
      boolean isRented = forSaleRentComboBox
        .getSelectedItem()
        .equals("For Rent");

      if (address.isEmpty()) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter an address.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (numBedrooms < 0 || numBedrooms > 10) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter a valid number of bedrooms.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (numBathrooms < 0 || numBathrooms > 10) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter a valid number of bathrooms.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (squareFootage < 0 || squareFootage > 1000000) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter a valid square footage.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }
      
      
      try {
    	  if (Integer.parseInt(price) < 0 || Integer.parseInt(price) > 1000000000) {
    	        JOptionPane.showMessageDialog(
    	          addPropertyFrame,
    	          "Please enter a valid price.",
    	          "Warning",
    	          JOptionPane.WARNING_MESSAGE
    	        );
    	        return;
    	      }  
      } catch(NumberFormatException e1){
    	  JOptionPane.showMessageDialog(
    	          addPropertyFrame,
    	          "Please enter a valid price.",
    	          "Warning",
    	          JOptionPane.WARNING_MESSAGE
    	        );
    	  return;
      }
      
      if (ownerName.matches(".*\\d.*")) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Owner name cannot contain numbers.",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
        return;
      }



      if (ownerName.isEmpty()) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter an owner name.",
          "Warning",
          JOptionPane.ERROR_MESSAGE
        );
        return;
      }

    
      if (agentName.matches(".*\\d.*")) {
          JOptionPane.showMessageDialog(
            addPropertyFrame,
            "Agent name cannot contain numbers.",
            "Error",
            JOptionPane.ERROR_MESSAGE
          );
          return;
        }
      
      if (agentName.isEmpty()) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter an agent name.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (mainImageSource.isEmpty()) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter a main image source.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (previewImageSources.isEmpty()) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Please enter a valid preview image source.",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (forSale && isRented) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "A property cannot be both for sale and for rent.",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } else if (!forSale && !isRented) {
        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "A property must be either for sale or for rent.",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } else {
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveProperty(
          address,
          numBedrooms,
          numBathrooms,
          squareFootage,
          price,
          ownerName,
          agentName,
          mainImageSource,
          previewImagePaths,
          forSale,
          isRented
        );

        JOptionPane.showMessageDialog(
          addPropertyFrame,
          "Property added successfully!",
          "Success",
          JOptionPane.INFORMATION_MESSAGE
        );
      }
    }
  }

  private class ChooseMainImageListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();

      int result = fileChooser.showOpenDialog(panel);
      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        mainImagePath = selectedFile.getPath();
      }
    }
  }

  private class UploadButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();

      fileChooser.setMultiSelectionEnabled(true);
      int result = fileChooser.showOpenDialog(panel);

      if (result == JFileChooser.APPROVE_OPTION) {
        File[] selectedFiles = fileChooser.getSelectedFiles();

        if (selectedFiles.length <= 5) {
          for (File file : selectedFiles) {
            previewImagePaths.add(file.getPath());
          }
        }
      }
    }
  }

  private class CloseButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      addPropertyFrame.setVisible(false);
    }
  }
}
