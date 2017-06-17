package Sites;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
// TODO: 6/5/2017  page monitor 11AM

/**
 * Supreme.
 */
public class Supreme implements WebsiteScript {

  private String name, email, phoneNumber, address, address2, zipcode, ccType, ccNumber, ccMonth,
          ccYear, cvv, category, keyword, color, size;
  private boolean sizeRequired;
  private WebDriver driver;
  private String supremeHome = "https://www.supremenewyork.com/shop/all/";

  /** Constructor for Supreme class with information entered.
   *
   * @param name  Entered name.
   * @param email Entered email.
   * @param phoneNumber Entered phone number.
   * @param address Entered address.
   * @param address2 Entered apartment/address line 2.
   * @param zipcode Entered zip code.
   * @param ccType Entered credit card type.
   * @param ccNumber Entered credit card number.
   * @param ccMonth Entered credit card expiry month.
   * @param ccYear Entered credit card expiry year.
   * @param cvv Entered cvv.
   * @param category Item category.
   * @param keyword Item keyword.
   * @param color Item color.
   * @param size Item size.
   * @param sizeRequired Is the size required?
   */
  public Supreme(String name, String email, String phoneNumber, String address, String address2,
                 String zipcode, String ccType, String ccNumber, String ccMonth, String ccYear,
                 String cvv, String category, String keyword, String color, String size,
                 boolean sizeRequired) {

    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.address2 = address2;
    this.zipcode = zipcode;
    this.ccType = ccType;
    this.ccNumber = ccNumber;
    this.ccMonth = ccMonth;
    this.ccYear = ccYear;
    this.cvv = cvv;
    this.category = category;
    this.keyword = keyword.toLowerCase();
    this.color = color.toLowerCase();
    this.size = size;
    this.sizeRequired = sizeRequired;
    driver = new ChromeDriver();
    supremeHome += category;
    driver.get(supremeHome);
  }

  @Override
  public void run() {
    boolean found = false;
    while (!found) {
      List<WebElement> elements = driver.findElements(new By.ByClassName("inner-article"));
      for (int i = 0; i < elements.size() && !found; i++) {
        List<WebElement> nameAndColor = elements.get(i).findElements(new By.ByClassName("name-link"));
        String name = nameAndColor.get(0).getText();
        String itemColor = nameAndColor.get(1).getText();
        if (name.toLowerCase().contains(keyword) && itemColor.toLowerCase().contains(color)) {
          nameAndColor.get(0).click();
          found = true;
        }
      }
      driver.navigate().refresh();
    }
    if (sizeRequired) {
      waitForLoad(() -> {
        while (true) {
          try {
            WebElement sizeDropdown = driver.findElement(new By.ById("size"));
            Select sizeSelect = new Select(sizeDropdown);
            sizeSelect.selectByVisibleText(size);
            break;
          } catch (NoSuchElementException e) {
            // do nothing
          }
        }
      });
    }
    waitForLoad(() -> driver.findElement(new By.ByXPath("//input[@value='add to cart']")).click());
    waitForLoad(() -> driver.findElement(new By.ByClassName("checkout")).click());
    waitForLoad(() -> driver.findElement(new By.ById("order_billing_name")).sendKeys(name));
    driver.findElement(new By.ById("order_email")).sendKeys(email);
    driver.findElement(new By.ById("order_tel")).sendKeys(phoneNumber);
    driver.findElement(new By.ById("bo")).sendKeys(address);
    driver.findElement(new By.ById("oba3")).sendKeys(address2);
    driver.findElement(new By.ById("order_billing_zip")).sendKeys(zipcode);

    Select cardSelect = new Select(driver.findElement(new By.ById(
            "credit_card_type")));
    cardSelect.selectByVisibleText(ccType);
    driver.findElement(new By.ById("cnb")).click();
    driver.findElement(new By.ById("cnb")).sendKeys(ccNumber);
    if (ccMonth.length() < 2) {
      ccMonth = "0" + ccMonth;
    }

    Select monthSelect = new Select(driver.findElement(new By.ById("credit_card_month")));
    monthSelect.selectByVisibleText(ccMonth);

    Select yearSelect = new Select(driver.findElement(new By.ById("credit_card_year")));
    yearSelect.selectByVisibleText(ccYear);

    driver.findElement(new By.ById("vval")).sendKeys(cvv);
  }

  /**
   * Helper method that waits for the given runnable to execute successfully.
   * @param r the given runnable
   */
  private void waitForLoad(Runnable r) {
    while (true) {
      try {
        r.run();
        break;
      } catch (Exception e) {
        // do nothing
      }
    }
  }
}
