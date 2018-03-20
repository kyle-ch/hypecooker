package Sites;

/**
 * Item on Supreme.
 */
public class SupremeItem {
  private String category;
  private String keyword;
  private String color;
  private String size;
  private Boolean sizeRequired;

  public SupremeItem(String category, String keyword, String color, String size, Boolean sizeRequired) {
    this.category = category;
    this.keyword = keyword.toLowerCase();
    this.color = color.toLowerCase();
    this.size = size;
    this.sizeRequired = sizeRequired;
  }

  public String toString() {
    return category + "  " + keyword + "  " + color + "  " + size + "  " + sizeRequired;
  }

  public String getCategory() {
    return category;
  }

  public String getKeyword() {
    return this.keyword;
  }

  public String getColor() {
    return this.color;
  }
  public String getSize() {
    return this.size;
  }
  public boolean getSizeRequired() {
    return this.sizeRequired;
  }
}

enum SupremeCategory {
  JACKETS("https://www.supremenewyork.com/shop/all/jackets"),
  SHIRTS("https://www.supremenewyork.com/shop/all/shirts"),
  TOPSSWEATERS("https://www.supremenewyork.com/shop/all/tops_sweaters"),
  SWEATSHIRTS("https://www.supremenewyork.com/shop/all/sweatshirts"),
  PANTS("https://www.supremenewyork.com/shop/all/pants"),
  SHORTS("https://www.supremenewyork.com/shop/all/shorts"),
  TSHIRTS("https://www.supremenewyork.com/shop/all/tshirts"),
  HATS("https://www.supremenewyork.com/shop/all/hats"),
  BAGS("https://www.supremenewyork.com/shop/all/hats"),
  ACCESSORIES("https://www.supremenewyork.com/shop/all/accessories"),
  SHOES("https://www.supremenewyork.com/shop/all/shoes"),
  SKATE("https://www.supremenewyork.com/shop/all/skate");

  private String url;
  SupremeCategory(String url) {
    this.url = url;
  }

  public String categoryToURL() {
    return this.url;
  }
}

enum Size {
  SMALL, MEDIUM, LARGE, XLARGE;
}
