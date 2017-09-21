package Sites;

/**
 * Item on Supreme.
 */
public class SupremeItem {
  private SupremeCategory category;
  private String keyword;
  private String color;
  private Size size;

  public SupremeItem(SupremeCategory category, String keyword, String color, Size size) {
    this.category = category;
    this.keyword = keyword;
    this.color = color;
    this.size = size;
  }
}

enum SupremeCategory {
  JACKETS, SHIRTS, TOPSSWEATERS, SWEATSHIRTS, PANTS, SHORTS, TSHIRTS, HATS, BAGS, ACCESSORIES,
  SHOES, SKATE;

  SupremeCategory() {
  }

  public String categoryToURL() {
    String base = "https://www.supremenewyork.com/shop/all/";

    return base;
  }
}

enum Size {
  SMALL, MEDIUM, LARGE, XLARGE
}
