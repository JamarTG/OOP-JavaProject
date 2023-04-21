import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ViewPropertyGUI extends JFrame {

  private static JSpinner numBedroomsMinSpinner;
  private static JSpinner numBedroomsMaxSpinner;
  private static JSpinner numBathroomsMinSpinner;
  private static JSpinner numBathroomsMaxSpinner;
  private static JSpinner minSquareFootageSpinner;
  private static JSpinner maxSquareFootageSpinner;
  private static JSpinner minPriceSpinner;
  private static JSpinner maxPriceSpinner;
  private static JCheckBox forSaleCheckBox;
  private static JCheckBox isRentedCheckBox;
  private JComboBox<String> sortDropdown;
  private static JComboBox<String> statusDropdown;
  
  
  private ViewPropertyGUI propertySearchGuiReference = this;
  
  final Color BUTTON_GRAY = new Color(210,210,210);

  private JButton searchButton;
  


  private static ArrayList<Integer> integerSearchQuery = new ArrayList<>();
  private static ArrayList<Boolean> booleanSearchQuery = new ArrayList<>();
  private static ArrayList<String> stringSearchQuery = new ArrayList<>();

  private static ArrayList<Integer> idOrder = new ArrayList<>();
  private static boolean isInOrder;
  private static String status;

  private JPanel imageGridPanel;
  private static JPanel panel;
  private JPanel contentPane;

  private Dimension LONG_SPINNER_SIZE = new Dimension(100, 20);
  private Dimension SHORT_SPINNER_SIZE = new Dimension(50, 20);

  private static int CURR_MIN_NUM_BEDROOMS = 0;
  private static int CURR_MAX_NUM_BEDROOMS = 10;
  private static int CURR_MIN_NUM_BATHROOMS = 0;
  private static int CURR_MAX_NUM_BATHROOMS = 10;
  private static int CURR_MIN_SQUARE_FOOTAGE = 100;
  private static int CURR_MAX_SQUARE_FOOTAGE = 1000000;
  private static int CURR_MIN_PRICE = 0;
  private static int CURR_MAX_PRICE = 1000000000;

  public ViewPropertyGUI() {
    addGridFields();
  }

  private static JPanel createImageGridPanel() {
	  

    ArrayList<Property> properties = readPropertiesFromFile("properties.txt");
    
    if (properties.isEmpty()) {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setPreferredSize(new Dimension(1000,500));
        emptyPanel.setBorder(BorderFactory.createTitledBorder(null, "No Properties Found", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        emptyPanel.setBackground(new Color(240, 240, 240));
        
        JLabel messageLabel = new JLabel("No properties found. Please add properties");
        messageLabel.setFont(new Font("Sitka Display", Font.BOLD, 35));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        emptyPanel.add(messageLabel, BorderLayout.CENTER);
        
        return emptyPanel;
    }

    panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 3 ), "Available Property Listing"));
    panel.setBackground(new Color(236,236,236));
    panel.setPreferredSize(new Dimension(1200,500));


    for (int i = 1; i <= properties.size(); i++) {
      idOrder.add(i);
    }

    if (isInOrder) {
      Collections.sort(properties);
    } else {
      Collections.sort(properties, Collections.reverseOrder());
    }

    for (Property property : properties) {
      JPanel propertyPanelContainer = new JPanel();
      propertyPanelContainer.setBorder(new LineBorder(new Color(245,245,245), 1));

      JPanel propertyInfoPanel = new JPanel();

      propertyInfoPanel.setLayout(new GridLayout(6, 2));
      propertyInfoPanel.setFont(new Font("Sitka Display", Font.BOLD, 35));

      if (!idOrder.isEmpty()) {
        idOrder.remove(0);
      }

      JLabel propertyType = new JLabel(
        property.isForSale() ? "For Sale" : "For Rent"
      );
      propertyType.setForeground(Color.darkGray);
      propertyType.setFont(new Font("Sitka Display", Font.PLAIN, 35));

      JPanel addressPanel = createPanelWithEmojiAndText(
        "\uD83D\uDCCC ",
        property.getAddress()
      );
      JPanel bedroomsPanel = createPanelWithEmojiAndText(
        "\uD83D\uDECF",
        " " + property.getNumBedrooms() + " bedroom" + 
        (property.getNumBathrooms() > 1 ? "s" : "")
      );
      JPanel bathroomsPanel = createPanelWithEmojiAndText(
        "\uD83D\uDEBF",
        " " + property.getNumBathrooms() + " bathroom" + 
                (property.getNumBathrooms() > 1 ? "s" : "")
      );

      JPanel sqftPanel = createPanelWithEmojiAndText(
        "\uD83D\uDCCD ",
        property.getSquareFootage() + " square feet"
      );
      JPanel pricePanel = createPanelWithEmojiAndText(
        "\uD83D\uDCB5 ",
        property.getPrice() + " JMD"
      );
      JPanel agentPanel = createPanelWithEmojiAndText(
    	        "\uD83D\uDC64 ",
    	        property.getAgentName()
    	       
    	      );
    	JPanel ownerPanel = createPanelWithEmojiAndText(
    	        "\uD83D\uDC68 ",
    	        property.getOwnerName()
    	      );


      propertyInfoPanel.add(propertyType);
      propertyInfoPanel.add(new JLabel(""));
      propertyInfoPanel.add(addressPanel);
      propertyInfoPanel.add(bedroomsPanel);
      propertyInfoPanel.add(bathroomsPanel);
      propertyInfoPanel.add(sqftPanel);
      propertyInfoPanel.add(pricePanel);
      propertyInfoPanel.add(agentPanel);
      propertyInfoPanel.add(ownerPanel);

      for (Component component : propertyInfoPanel.getComponents()) {
        if (component.getForeground().equals(Color.darkGray)) {
          continue;
        }

        component.setFont(new Font("Sitka Display", Font.PLAIN, 15));
      }

      JPanel ImagePanel = new JPanel();
      ArrayList<String> sources = property.getPreviewImageSources();
      sources.add(0,property.getMainImageSource());
      int delay = 3000; 
      Timer timer = new Timer(
        delay,
        new ActionListener() {
          int index = 0;

          public void actionPerformed(ActionEvent e) {
            ImageIcon image = new ImageIcon(sources.get(index));
            JLabel label = new JLabel(image);
            label.setBorder(new LineBorder(new Color(0, 0, 0), 4));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setPreferredSize(new Dimension(250, 250));
            ImagePanel.removeAll();
            ImagePanel.add(label);
            panel.revalidate();
            panel.repaint();
            index = (index + 1) % sources.size();
          }
        }
      );

      timer.start();

      ImageIcon mainImage = new ImageIcon(sources.get(0));
      JLabel mainLabel = new JLabel(mainImage);
      mainLabel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
      mainLabel.setHorizontalAlignment(JLabel.CENTER);
      mainLabel.setVerticalAlignment(JLabel.CENTER);
      mainLabel.setPreferredSize(new Dimension(250, 250));
      ImagePanel.add(mainLabel);
      propertyPanelContainer.add(ImagePanel);
      propertyPanelContainer.add(propertyInfoPanel);
      panel.add(propertyPanelContainer);
    }
    return panel;
  }

  private static ArrayList<Property> readPropertiesFromFile(String filename) {
    ArrayList<Property> properties = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(new File(filename));

      int dynamicId = 1;
      while (scanner.hasNextLine()) {
        String[] parts = scanner.nextLine().split(",");
        String address = parts[0];

        int numBedrooms = Integer.parseInt(parts[1]);
        int numBathrooms = Integer.parseInt(parts[2]);
        int squareFootage = Integer.parseInt(parts[3]);
        int price = Integer.parseInt(parts[4]);
        String agentName = parts[5];
        String ownerName = parts[6];
        String mainImageSource = parts[7];
        ArrayList<String> previewImageSourcesList = new ArrayList<String>(
          Arrays.asList(parts[8].split("\\*"))
        );

        boolean forSale = Boolean.parseBoolean(parts[9]);
        boolean isRented = Boolean.parseBoolean(parts[10]);

        Property property = new Property(
          address,
          numBedrooms,
          numBathrooms,
          squareFootage,
          price,
          agentName,
          ownerName,
          mainImageSource,
          previewImageSourcesList,
          forSale,
          isRented
        );

        boolean shouldAddProperty = false;

        idOrder = new ArrayList<>();
        if (!isInitialGridPaint()) {
          shouldAddProperty = propertyMeetsRequirements(property);

          if (shouldAddProperty) {
            properties.add(property);
            idOrder.add(0, dynamicId);
          }
        } else {
          properties.add(property);
          idOrder.add(0, dynamicId);
        }

        dynamicId++;
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return properties;
  }

  private JPanel createSearchPanel() {
    JPanel panel = new JPanel(new FlowLayout());

    panel.setPreferredSize(new Dimension(200, 100));
    panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 3 ), "Advanced Search"));

    panel.add(new JLabel("Bedroom Range:"));
    SpinnerNumberModel numBedroomsMinModel = new SpinnerNumberModel(
      CURR_MIN_NUM_BEDROOMS,
      0,
      100,
      1
    );
    
    numBedroomsMinSpinner = new JSpinner(numBedroomsMinModel);
    numBedroomsMinSpinner.setPreferredSize(SHORT_SPINNER_SIZE);
    panel.add(numBedroomsMinSpinner);
    panel.add(new JLabel("-"));
    SpinnerNumberModel numBedroomsMaxModel = new SpinnerNumberModel(
      CURR_MAX_NUM_BEDROOMS,
      0,
      100,
      1
    );
    
    numBedroomsMaxSpinner = new JSpinner(numBedroomsMaxModel);
    numBedroomsMaxSpinner.setPreferredSize(SHORT_SPINNER_SIZE);
    panel.add(numBedroomsMaxSpinner);

    panel.add(new JLabel("Bathroom Range:"));
    SpinnerNumberModel numBathroomsMinModel = new SpinnerNumberModel(
      CURR_MIN_NUM_BATHROOMS,
      0,
      100,
      1
    );
    numBathroomsMinSpinner = new JSpinner(numBathroomsMinModel);
    numBathroomsMinSpinner.setPreferredSize(SHORT_SPINNER_SIZE);
    panel.add(numBathroomsMinSpinner);
    panel.add(new JLabel("-"));
    SpinnerNumberModel numBathroomsMaxModel = new SpinnerNumberModel(
      CURR_MAX_NUM_BATHROOMS,
      0,
      100,
      1
    );
    numBathroomsMaxSpinner = new JSpinner(numBathroomsMaxModel);
    numBathroomsMaxSpinner.setPreferredSize(SHORT_SPINNER_SIZE);
    panel.add(numBathroomsMaxSpinner);

    panel.add(new JLabel("Square Footage Range:"));
    SpinnerNumberModel squareFootageMinModel = new SpinnerNumberModel(
      CURR_MIN_SQUARE_FOOTAGE,
      0,
      1000000,
      1000
    );
    minSquareFootageSpinner = new JSpinner(squareFootageMinModel);
    minSquareFootageSpinner.setPreferredSize(LONG_SPINNER_SIZE);
    panel.add(minSquareFootageSpinner);
    panel.add(new JLabel("-"));
    SpinnerNumberModel squareFootageMaxModel = new SpinnerNumberModel(
      CURR_MAX_SQUARE_FOOTAGE,
      0,
      1000000,
      1000
    );
    maxSquareFootageSpinner = new JSpinner(squareFootageMaxModel);
    maxSquareFootageSpinner.setPreferredSize(LONG_SPINNER_SIZE);
    panel.add(maxSquareFootageSpinner);

    panel.add(new JLabel("Price Range:"));
    SpinnerNumberModel priceMinModel = new SpinnerNumberModel(
      CURR_MIN_PRICE,
      0,
      1000000000,
      1000
    );
    minPriceSpinner = new JSpinner(priceMinModel);
    minPriceSpinner.setPreferredSize(LONG_SPINNER_SIZE);
    panel.add(minPriceSpinner);
    panel.add(new JLabel("-"));
    SpinnerNumberModel priceMaxModel = new SpinnerNumberModel(
      CURR_MAX_PRICE,
      0,
      1000000000,
      1000
    );
    maxPriceSpinner = new JSpinner(priceMaxModel);
    maxPriceSpinner.setPreferredSize(LONG_SPINNER_SIZE);

    String[] statusOptions = { "For Sale", "For Rent", "Any" };
    statusDropdown = new JComboBox<>(statusOptions);

    String[] sortOptions = {
      "Sort Ascending Based on Price",
      "Sort Descending based on Price",
    };
    sortDropdown = new JComboBox<>(sortOptions);


    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(new CloseButtonListener());

    closeButton.setBackground(BUTTON_GRAY);
    searchButton.setBackground(BUTTON_GRAY);

    panel.add(maxPriceSpinner);

    panel.add(statusDropdown);
    panel.add(sortDropdown);
    panel.add(searchButton);
    panel.add(closeButton);
    
    searchButton.addActionListener(e -> performSearch());
    return panel;
  }

  private void addGridFields() {
    new JTextField();
    minSquareFootageSpinner =
      new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    maxSquareFootageSpinner =
      new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    new JTextField();
    minPriceSpinner =
      new JSpinner(new SpinnerNumberModel(0, 0, 10000000, 1000));
    maxPriceSpinner =
      new JSpinner(new SpinnerNumberModel(0, 0, 10000000, 1000));
    new JTextField();
    new JTextField();
    forSaleCheckBox = new JCheckBox("For Sale");
    isRentedCheckBox = new JCheckBox("Is Rented");
    searchButton = new JButton("Search");

    imageGridPanel = new JPanel();
    imageGridPanel.setLayout(new GridLayout(3, 3, 5, 5));

    contentPane = new JPanel();

    contentPane.setLayout(new BorderLayout());
    contentPane.setBackground(new Color(236,236,236));
    contentPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    

    JPanel imageGridPanel = createImageGridPanel();
    JPanel searchPanel = createSearchPanel();

    contentPane.add(searchPanel, BorderLayout.NORTH);
    contentPane.add(imageGridPanel, BorderLayout.CENTER);


    setContentPane(contentPane);

    setTitle("Property Search");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void setSearchQueryValues() {
    int minNumBedrooms = (int) numBedroomsMinSpinner.getValue();
    int maxNumBedrooms = (int) numBedroomsMaxSpinner.getValue();
    int minNumBathrooms = (int) numBathroomsMinSpinner.getValue();
    int maxNumBathrooms = (int) numBathroomsMaxSpinner.getValue();
    int minSquareFootage = (int) minSquareFootageSpinner.getValue();
    int maxSquareFootage = (int) maxSquareFootageSpinner.getValue();

    int minPrice = (int) minPriceSpinner.getValue();
    int maxPrice = (int) maxPriceSpinner.getValue();

    boolean forSale = forSaleCheckBox.isSelected();
    boolean isRented = isRentedCheckBox.isSelected();

    status = (String) statusDropdown.getSelectedItem();

    if (
      sortDropdown.getSelectedItem().equals("Sort Ascending Based on Price")
    ) {
      isInOrder = true;
    } else {
      isInOrder = false;
    }

    if (
      this.sortDropdown.getSelectedItem()
        .equals("Sort Ascending Based on Price")
    ) {
      isInOrder = true;
    } else {
      isInOrder = false;
    }

    integerSearchQuery.add(minNumBedrooms);
    integerSearchQuery.add(maxNumBedrooms);
    integerSearchQuery.add(minNumBathrooms);
    integerSearchQuery.add(maxNumBathrooms);
    integerSearchQuery.add(minSquareFootage);
    integerSearchQuery.add(maxSquareFootage);
    integerSearchQuery.add(minPrice);
    integerSearchQuery.add(maxPrice);

    booleanSearchQuery.add(forSale);
    booleanSearchQuery.add(isRented);
    booleanSearchQuery.add(isInOrder);

    stringSearchQuery.add(status);
  }

  private void performSearch() {
    clearSearchQueryValues();
    setSearchQueryValues();
    addGridFields();
  }

  private static boolean propertyMeetsRequirements(
    Property propertyToBeValidated
  ) {
    int minNumBedrooms = integerSearchQuery.get(0);
    int maxNumBedrooms = integerSearchQuery.get(1);
    int minNumBathrooms = integerSearchQuery.get(2);
    int maxNumBathrooms = integerSearchQuery.get(3);
    int minSquareFootage = integerSearchQuery.get(4);
    int maxSquareFootage = integerSearchQuery.get(5);
    int minPrice = integerSearchQuery.get(6);
    int maxPrice = integerSearchQuery.get(7);
    isInOrder = booleanSearchQuery.get(2);

    String status = stringSearchQuery.get(0);

    if (isInitialGridPaint()) {
      return true;
    }

    if (
      propertyToBeValidated.getNumBedrooms() < minNumBedrooms ||
      propertyToBeValidated.getNumBedrooms() > maxNumBedrooms
    ) {
      return false;
    }

    if (
      propertyToBeValidated.getNumBathrooms() < minNumBathrooms ||
      propertyToBeValidated.getNumBathrooms() > maxNumBathrooms
    ) {
      return false;
    }

    if (
      propertyToBeValidated.getSquareFootage() < minSquareFootage ||
      propertyToBeValidated.getSquareFootage() > maxSquareFootage
    ) {
      return false;
    }

    if (
      propertyToBeValidated.getPrice() < minPrice ||
      propertyToBeValidated.getPrice() > maxPrice
    ) {
    	System.out.println("this one is" + propertyToBeValidated.getPrice());
      return false;
    }

    if (status.equals("Any")) {
      return true;
    }

    if (propertyToBeValidated.isForSale() && status.equals("For Sale")) {
      return true;
    } else if (propertyToBeValidated.isRented() && status.equals("For Rent")) {
      return true;
    }

    return false;
  }

  private static JPanel createPanelWithEmojiAndText(
    String emoji,
    String text
  ) {
    JLabel emojiLabel = new JLabel(emoji);
    emojiLabel.setFont(new Font("Helvetica", Font.PLAIN, 25));
    JLabel textLabel = new JLabel(text);
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(emojiLabel, BorderLayout.WEST);
    panel.add(textLabel, BorderLayout.CENTER);
    return panel;
  }

  private static boolean isInitialGridPaint() {
    return (
      integerSearchQuery.isEmpty() &&
      booleanSearchQuery.isEmpty() &&
      stringSearchQuery.isEmpty()
    );
  }

  private void clearSearchQueryValues() {
    integerSearchQuery.clear();
    booleanSearchQuery.clear();
    stringSearchQuery.clear();
  }

  private class CloseButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      propertySearchGuiReference.setVisible(false);
    }
  }
}
