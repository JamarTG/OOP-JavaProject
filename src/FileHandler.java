import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

  public void saveProperty(
    String address,
    int numBedrooms,
    int numBathrooms,
    int squareFootage,
    String price,
    String ownerName,
    String agentName,
    String mainImageSource,
    ArrayList<String> previewImageSources,
    boolean forSale,
    boolean isRented
  ) {
    try {
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("properties.txt", true)
      );

      writer.write(address + ",");
      writer.write(numBedrooms + ",");
      writer.write(numBathrooms + ",");
      writer.write(squareFootage + ",");
      writer.write(price + ",");
      writer.write(ownerName + ",");
      writer.write(agentName + ",");
      writer.write(mainImageSource + ",");
      writer.write(String.join("*", previewImageSources) + ",");
      writer.write(forSale + ",");
      writer.write(isRented + "\n");

      writer.close();
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  public boolean removeProperty(int position) {
    try {
      List<String> lines = Files.readAllLines(
        Paths.get("properties.txt"),
        StandardCharsets.UTF_8
      );

      lines.remove(position);

      BufferedWriter writer = new BufferedWriter(
        new FileWriter("properties.txt")
      );
      for (String line : lines) {
        writer.write(line + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
      return false;
    }
    return true;
  }

  public void saveProperty(
    int position,
    String address,
    int numBedrooms,
    int numBathrooms,
    int squareFootage,
    String price,
    String ownerName,
    String agentName,
    String mainImageSource,
    ArrayList<String> previewImageSources,
    boolean forSale,
    boolean isRented
  ) {
    try {
      List<String> lines = Files.readAllLines(
        Paths.get("properties.txt"),
        StandardCharsets.UTF_8
      );

      String modifiedLine =
        address +
        "," +
        numBedrooms +
        "," +
        numBathrooms +
        "," +
        squareFootage +
        "," +
        price +
        "," +
        ownerName +
        "," +
        agentName +
        "," +
        mainImageSource +
        "," +
        String.join("*", previewImageSources) +
        "," +
        forSale +
        "," +
        isRented;
      lines.set(position, modifiedLine);

      BufferedWriter writer = new BufferedWriter(
        new FileWriter("properties.txt")
      );
      for (String line : lines) {
        writer.write(line + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  public List<Property> loadProperties(String filename) {
    List<Property> properties = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        String address = fields[0];

        int numBedrooms = Integer.parseInt(fields[1]);
        int numBathrooms = Integer.parseInt(fields[2]);
        int squareFootage = Integer.parseInt(fields[3]);
        int price = Integer.parseInt(fields[4]);
        String ownerName = fields[5];
        String agentName = fields[6];
        String mainImageSource = fields[7];
        ArrayList<String> previewImageSources = new ArrayList<>();
        String[] previewSources = fields[8].split(",");

        for (String source : previewSources) {
          previewImageSources.add(source);
        }
        boolean forSale = Boolean.parseBoolean(fields[9]);
        boolean isRented = Boolean.parseBoolean(fields[10]);

        Property property = new Property(
          address,
          numBedrooms,
          numBathrooms,
          squareFootage,
          price,
          agentName,
          ownerName,
          mainImageSource,
          previewImageSources,
          forSale,
          isRented
        );

        properties.add(property);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }
}
