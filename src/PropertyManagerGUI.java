import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PropertyManagerGUI extends JFrame {

  private JPanel panel;
  private PropertyManagerGUI propertyManagerGUIReference = this;
  protected String mainImageSource = "";

  public PropertyManagerGUI() {
    super("Property Manager");
    setMinimumSize(new Dimension(800, 800));
    
    
    panel = new JPanel();

    List<Property> properties = new FileHandler().loadProperties("properties.txt");

    JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    cardsPanel.setPreferredSize(new Dimension(800, 600));

    if (properties.isEmpty()) {
      JPanel noPropertiesPanel = new JPanel(new BorderLayout());
      
      JLabel messageLabel = new JLabel("No properties meeting requirements found");
      
      messageLabel.setFont(new Font("Sitka Display", Font.BOLD, 35));
      messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      
      noPropertiesPanel.add(messageLabel, BorderLayout.CENTER);
      
      cardsPanel.add(noPropertiesPanel, "noProperties");
    }

    for (int propertyId = 0; propertyId < properties.size(); propertyId++) {
      Property property = properties.get(propertyId);

      JPanel cardPanel = new JPanel(new BorderLayout());
      cardPanel.setPreferredSize(new Dimension(250, 350));
   
      
      cardPanel.setBorder(
    	      BorderFactory.createTitledBorder(
    	        new LineBorder(Color.WHITE, 3),
    	        "Property " +  (property.isForSale() == true ? "on sale" : "for rent"),
    	        TitledBorder.CENTER,
    	        TitledBorder.DEFAULT_POSITION
    	      )
    	    );

      JPanel detailsPanel = new JPanel(new GridLayout(8, 2, 5, 5));
   

      JLabel addressLabel = new JLabel("Address:");
      detailsPanel.add(addressLabel);
      JLabel addressValueLabel = new JLabel(property.getAddress());
      detailsPanel.add(addressValueLabel);

      JLabel bedroomsLabel = new JLabel("Bedroom" + (property.getNumBedrooms() > 1 ? "s" : "") + ":");
      detailsPanel.add(bedroomsLabel);
      JLabel bedroomsValueLabel = new JLabel(
        Integer.toString(property.getNumBedrooms())
      );
      detailsPanel.add(bedroomsValueLabel);

      JLabel bathroomsLabel = new JLabel("Bathroom"  + (property.getNumBathrooms() > 1 ? "s" : "") + ":");
      detailsPanel.add(bathroomsLabel);
      JLabel bathroomsValueLabel = new JLabel(
        Integer.toString(property.getNumBathrooms())
      );
      detailsPanel.add(bathroomsValueLabel);

      JLabel sqftLabel = new JLabel("Square Footage:");
      detailsPanel.add(sqftLabel);
      JLabel sqftValueLabel = new JLabel(
        Integer.toString(property.getSquareFootage())
      );
      detailsPanel.add(sqftValueLabel);

      JLabel priceLabel = new JLabel("Price:");
      detailsPanel.add(priceLabel);
      JLabel priceValueLabel = new JLabel(
        "$" + Integer.toString(property.getPrice())
      );
      detailsPanel.add(priceValueLabel);

      JLabel propertyTypeLabel = new JLabel("Type:");
      detailsPanel.add(propertyTypeLabel);
      JLabel propertyTypeValueLabel = new JLabel(property.getPropertyType());
      detailsPanel.add(propertyTypeValueLabel);

      JLabel agentNameLabel = new JLabel("Agent:");
      detailsPanel.add(agentNameLabel);
      JLabel agentNameValueLabel = new JLabel(property.getAgentName());
      detailsPanel.add(agentNameValueLabel);

      
      JLabel ownerNameLabel = new JLabel("Owner:");
      detailsPanel.add(ownerNameLabel);
      JLabel ownerNameValueLabel = new JLabel(property.getOwnerName());
      detailsPanel.add(ownerNameValueLabel);
      
      detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      cardPanel.add(detailsPanel, BorderLayout.CENTER);

      
      Border line = BorderFactory.createLineBorder(new Color(230,230,230) , 0); 
      Border padding = BorderFactory.createEmptyBorder(7,7, 7, 7); // create a 5-pixel empty border
      Border compound = BorderFactory.createCompoundBorder(line, padding); // create a compound border
      
      JButton editButton = new JButton("Edit");
      editButton.setPreferredSize(new Dimension(80, 25));
      editButton.setBorder(compound);	 
      editButton.setBackground(new Color(210,210,210));
      editButton.addActionListener(
        new EditPropertyListener(property, propertyId)
      );

      JButton sellOrRentButton = new JButton(
        (property.isForSale() ? "Sell" : "Rent")
      );

      
      sellOrRentButton.setBorder(compound);	 
      sellOrRentButton.setBackground(new Color(210,210,210));
      sellOrRentButton.setPreferredSize(new Dimension(80, 25));
      
      sellOrRentButton.addActionListener(
        new SellOrRentPropertyListener(propertyId)
      );

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
      buttonPanel.add(editButton);
      buttonPanel.add(sellOrRentButton);
      cardPanel.add(buttonPanel, BorderLayout.SOUTH);

      cardsPanel.add(cardPanel);
    }

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Window");
    JMenuItem closeMenuItem = new JMenuItem("Close");
    closeMenuItem.addActionListener(new CloseButtonListener());
    menu.add(closeMenuItem);
    menuBar.add(menu);
    setJMenuBar(menuBar);

    add(cardsPanel, BorderLayout.CENTER);
    add(menuBar, BorderLayout.NORTH);
    setVisible(true);
  }

  private class CloseButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      propertyManagerGUIReference.setVisible(false);
    }
  }

  private class UploadButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();

      fileChooser.setMultiSelectionEnabled(true);
      ArrayList<String> previewImagePaths = new ArrayList<>();

      int result = fileChooser.showOpenDialog(panel);

      if (result == JFileChooser.APPROVE_OPTION) {
        File[] selectedFiles = fileChooser.getSelectedFiles();
        if (selectedFiles.length <= 5) {
          for (File file : selectedFiles) {
            previewImagePaths.add(file.getPath());
          }
        } else {
          JOptionPane.showMessageDialog(null, "Please select up to 5 images");
        }
      }
    }
  }

  private class SellOrRentPropertyListener implements ActionListener {
    private int propertyId;

    public SellOrRentPropertyListener(int propertyId) {
      this.propertyId = propertyId;
    }

    public void actionPerformed(ActionEvent e) {
      boolean success = new FileHandler().removeProperty(propertyId);
      if (success) {
        JOptionPane.showMessageDialog(
          null,
          "Property was removed successfully."
        );
      } else {
        JOptionPane.showMessageDialog(null, "Property could not be removed.");
      }
      ((JButton) e.getSource()).setEnabled(false);
    }
  }

  private class EditPropertyListener implements ActionListener {

    private static final Color BUTTON_GRAY = new Color(210,210,210);
	private Property property;
    protected int propertyPosition;

    public EditPropertyListener(Property property, int propertyPosition) {
      this.property = property;
      this.propertyPosition = propertyPosition;
    }

    public void actionPerformed(ActionEvent e) {
      
      Border line = BorderFactory.createLineBorder(new Color(230,230,230) , 0); 
      Border padding = BorderFactory.createEmptyBorder(7,7, 7, 7); 
      Border compound = BorderFactory.createCompoundBorder(line, padding); 
        
      JFrame addPropertyFrame = new JFrame("Edit Property");
      addPropertyFrame.setPreferredSize(new Dimension(500, 550));

      JPanel panel = new JPanel(new GridLayout(12, 2));

      panel.setBorder(
        BorderFactory.createTitledBorder(
          new LineBorder(Color.WHITE, 3),
          "Edit Property Form"
        )
      );
      JTextField addressField = new JTextField(property.getAddress());
      addressField.setBorder(compound); 

      JSpinner numBedroomsSpinner = new JSpinner(
        new SpinnerNumberModel(
          property.getNumBedrooms(),
          0,
          Integer.MAX_VALUE,
          1
        )
      );
      numBedroomsSpinner.setBorder(null);

      JSpinner numBathroomsSpinner = new JSpinner(
        new SpinnerNumberModel(
          property.getNumBathrooms(),
          0,
          Integer.MAX_VALUE,
          1
        )
      );
      numBathroomsSpinner.setBorder(null);

      JSpinner squareFootageSpinner = new JSpinner(
        new SpinnerNumberModel(
          property.getSquareFootage(),
          0,
          Integer.MAX_VALUE,
          1
        )
      );
      squareFootageSpinner.setBorder(null);

      JTextField priceTextField = new JTextField(
        Integer.toString(property.getPrice())
      );
      priceTextField.setBorder(compound); 

      JTextField ownerNameField = new JTextField(property.getOwnerName());
      ownerNameField.setBorder(compound); 

      
      JTextField agentNameField = new JTextField(property.getAgentName());
      agentNameField.setBorder(compound); 

      
      JButton chooseMainImageButton = new JButton("Choose Main Image");
      mainImageSource = property.getMainImageSource();
      chooseMainImageButton.setBorder(null);
      chooseMainImageButton.setBackground(BUTTON_GRAY);
      chooseMainImageButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              mainImageSource = selectedFile.getPath();
            }
          }
        }
      );

      JLabel previewImageField = new JLabel("");
      JButton uploadButton = new JButton("Choose Preview Images");
      uploadButton.addActionListener(new UploadButtonListener());
      previewImageField.setBorder(null);
      uploadButton.setBackground(BUTTON_GRAY);
      
      
      String[] options = { "For Sale", "For Rent" };
      JComboBox<String> saleOrRentComboBox = new JComboBox<>(options);
      saleOrRentComboBox.setBackground(BUTTON_GRAY);
      
      saleOrRentComboBox.setMaximumSize(new Dimension(100, 100));
      if (property.isForSale()) {
        saleOrRentComboBox.setSelectedItem("For Sale");
      } else if (property.isRented()) {
        saleOrRentComboBox.setSelectedItem("For Rent");
      }

      JButton saveButton = new JButton("Save");
      saveButton.setBackground(BUTTON_GRAY);

      saveButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String address = addressField.getText();
            int numBedrooms = (int) numBedroomsSpinner.getValue();
            int numBathrooms = (int) numBathroomsSpinner.getValue();
            int squareFootage = (int) squareFootageSpinner.getValue();
            String price = priceTextField.getText();
            String ownerName = ownerNameField.getText();
            String agentName = agentNameField.getText();

            ArrayList<String> previewImageSources = new ArrayList<>();
            String[] previewImageArray = previewImageField.getText().split(",");
            for (String previewImage : previewImageArray) {
              previewImageSources.add(previewImage.trim());
            }

            boolean forSale = saleOrRentComboBox
              .getSelectedItem()
              .equals("For Sale");
            boolean isRented = saleOrRentComboBox
              .getSelectedItem()
              .equals("For Rent");

            if (address.isEmpty()) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter an address."
              );
              return;
            }
            if (numBedrooms < 0 || numBedrooms > 10) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter a valid number of bedrooms."
              );
              return;
            }
            if (numBathrooms < 0 || numBathrooms > 10) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter a valid number of bathrooms."
              );
              return;
            }
            if (squareFootage < 0 || squareFootage > 1000000) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter a valid square footage."
              );
              return;
            }
            try {
            	  int parsedPrice = Integer.parseInt(price);
            	  if (parsedPrice < 0 || parsedPrice > 1000000000) {
            	    JOptionPane.showMessageDialog(
            	      addPropertyFrame,
            	      "Please enter a valid price."
            	    );
            	    return;
            	  }
            	} catch (NumberFormatException e1) {
            	  JOptionPane.showMessageDialog(
            	    addPropertyFrame,
            	    "Please enter a valid integer price.",
            	    "Error",
            	    JOptionPane.ERROR_MESSAGE
            	  );
            	  return;
            	}

            if (ownerName.isEmpty()) {
                JOptionPane.showMessageDialog(
                    addPropertyFrame,
                    "Please enter an owner name.",
                    "Error",
            	    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (ownerName.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(
                    addPropertyFrame,
                    "Owner name should not contain any numbers.",
                    "Error",
            	    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (agentName.isEmpty()) {
                JOptionPane.showMessageDialog(
                    addPropertyFrame,
                    "Please enter an agent name.",
                    "Error",
            	    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (agentName.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(
                    addPropertyFrame,
                    "Agent name should not contain any numbers."
                );
                return;
            }

            if (mainImageSource.isEmpty()) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter a main image source."
              );
              return;
            }

            if (previewImageSources.isEmpty()) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Please enter a valid preview image source."
              );
              return;
            }

            if (forSale && isRented) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "A property cannot be both for sale and for rent."
              );
            } else if (!forSale && !isRented) {
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "A property must be either for sale or for rent."
              );
            } else {
              FileHandler fileHandler = new FileHandler();

              String previewString = property.getPreviewImageSources().get(0);
              ArrayList<String> previewList = new ArrayList<>(
                Arrays.asList(previewString.split("\\*"))
              );

              fileHandler.saveProperty(
                propertyPosition,
                address,
                numBedrooms,
                numBathrooms,
                squareFootage,
                price,
                ownerName,
                agentName,
                mainImageSource,
                previewList,
                forSale,
                isRented
              );
              JOptionPane.showMessageDialog(
                addPropertyFrame,
                "Property edited successfully!"
              );

              addPropertyFrame.dispose();
            }
          }
        }
      );

      addFieldToPanelWithMargin("Address:", addressField, panel);
      addFieldToPanelWithMargin(
        "Number of Bedrooms:",
        numBedroomsSpinner,
        panel
      );
      addFieldToPanelWithMargin(
        "Number of Bathrooms:",
        numBathroomsSpinner,
        panel
      );
      addFieldToPanelWithMargin("Square Footage:", squareFootageSpinner, panel);
      addFieldToPanelWithMargin("Price:", priceTextField, panel);
      addFieldToPanelWithMargin("Owner Name:", ownerNameField, panel);
      addFieldToPanelWithMargin("Agent Name:", agentNameField, panel);
      addFieldToPanelWithMargin("Main Image:", chooseMainImageButton, panel);
      addFieldToPanelWithMargin("", saleOrRentComboBox, panel);
      addFieldToPanelWithMargin("", uploadButton, panel);
      addFieldToPanelWithMargin("", saveButton, panel);

      addPropertyFrame.add(panel);
      addPropertyFrame.pack();
      addPropertyFrame.setLocationRelativeTo(null);
      addPropertyFrame.setVisible(true);
    }

    private void addFieldToPanelWithMargin(
      String labelText,
      JComponent component,
      JPanel panel
    ) {
      JPanel fieldPanel = new JPanel(new BorderLayout());
      JLabel label = new JLabel(labelText);
      label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
      fieldPanel.add(label, BorderLayout.WEST);
      fieldPanel.add(component, BorderLayout.CENTER);
      fieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
      panel.add(fieldPanel);
      fieldPanel.setPreferredSize(new Dimension(400, 50));
      fieldPanel.setMaximumSize(new Dimension(400, 40));
    }
  }
  
  
}
