import java.util.ArrayList;

public class Property implements Comparable<Property> {
  private String address;
  private int numBedrooms;
  private int numBathrooms;
  private int squareFootage;
  private int price;
  private String ownerName;
  private String agentName;
  private String mainImageSource;
  private ArrayList<String> previewImageSources;
  private boolean forSale;
  private boolean isRented;

  public Property(
    String address,
    int numBedrooms,
    int numBathrooms,
    int squareFootage,
    int price,
    String agentName,
    String ownerName,
    String mainImageSource,
    ArrayList<String> previewImageSources,
    boolean forSale,
    boolean isRented
  ) {
    this.address = address;
    this.numBedrooms = numBedrooms;
    this.numBathrooms = numBathrooms;
    this.squareFootage = squareFootage;
    this.price = price;
    this.agentName = agentName;
    this.ownerName = ownerName;
    this.mainImageSource = mainImageSource;
    this.previewImageSources = previewImageSources;
    this.forSale = forSale;
    this.isRented = isRented;
  }

  public boolean isForSale() {
    return forSale;
  }

  public void setForSale(boolean forSale) {
    this.forSale = forSale;
  }

  public boolean isRented() {
    return isRented;
  }

  public void setRented(boolean rented) {
    isRented = rented;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getNumBedrooms() {
    return numBedrooms;
  }

  public void setNumBedrooms(int numBedrooms) {
    this.numBedrooms = numBedrooms;
  }

  public int getNumBathrooms() {
    return numBathrooms;
  }

  public void setNumBathrooms(int numBathrooms) {
    this.numBathrooms = numBathrooms;
  }

  public int getSquareFootage() {
    return squareFootage;
  }

  public void setSquareFootage(int squareFootage) {
    this.squareFootage = squareFootage;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getMainImageSource() {
    return mainImageSource;
  }

  public void setMainImageSource(String mainImageSource) {
    this.mainImageSource = mainImageSource;
  }

  public ArrayList<String> getPreviewImageSources() {
    return previewImageSources;
  }

  public void setPreviewImageSources(ArrayList<String> previewImageSources) {
    this.previewImageSources = previewImageSources;
  }

  public String getPropertyType() {
    if (forSale) {
      return "For Sale";
    } else if (isRented) {
      return "For Rent";
    } else {
      return "Unknown";
    }
  }

  public int compareTo(Property other) {
    return this.price - other.price;
  }
}
