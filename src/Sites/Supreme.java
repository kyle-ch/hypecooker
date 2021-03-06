package Sites;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
// TODO: 6/5/2017  page monitor 11AM

/**
 * Supreme.
 */
public class Supreme implements WebsiteScript {

  private String name, email, phoneNumber, address, address2, zipcode, ccType, ccNumber, ccMonth,
          ccYear, cvv;
  private List<SupremeItem> items;
  private WebDriver driver;
  private String supremeHome = "https://www.supremenewyork.com/shop/all/";

  /**
   * Constructor for Supreme class with information entered.
   *
   * @param name        Entered name.
   * @param email       Entered email.
   * @param phoneNumber Entered phone number.
   * @param address     Entered address.
   * @param address2    Entered apartment/address line 2.
   * @param zipcode     Entered zip code.
   * @param ccType      Entered credit card type.
   * @param ccNumber    Entered credit card number.
   * @param ccMonth     Entered credit card expiry month.
   * @param ccYear      Entered credit card expiry year.
   * @param cvv         Entered cvv.
   * @param items       List of items
   */
  public Supreme(String name, String email, String phoneNumber, String address, String address2,
                 String zipcode, String ccType, String ccNumber, String ccMonth, String ccYear,
                 String cvv, List<SupremeItem> items) {

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
    this.items = items;
    driver = new ChromeDriver();
  }

  @Override
  public void run() {
    for (SupremeItem s : items) {
      String url = supremeHome + s.getCategory();
      driver.get(url);
      boolean found = false;
      while (!found) {
        List<WebElement> elements = driver.findElements(new By.ByClassName("inner-article"));
        for (int i = 0; i < elements.size() && !found; i++) {
          List<WebElement> nameAndColor = elements.get(i).findElements(new By.ByClassName("name-link"));
          String name = nameAndColor.get(0).getText();
          String itemColor = nameAndColor.get(1).getText();
          if (name.toLowerCase().contains(s.getKeyword())
                  && itemColor.toLowerCase().contains(s.getColor())) {
            nameAndColor.get(0).click();
            found = true;
          }
        }
        driver.navigate().refresh();
      }
      if (s.getSizeRequired()) {
        waitForLoad(() -> {
          try {
            WebElement sizeDropdown = driver.findElement(new By.ById("s"));
            Select sizeSelect = new Select(sizeDropdown);
            sizeSelect.selectByVisibleText(s.getSize());
          } catch (NoSuchElementException e) {
            e.printStackTrace();
          }
        });
      }
      waitForLoad(() -> driver.findElement(new By.ByXPath("//input[@value='add to cart']")).click());
    }

    waitForLoad(() -> driver.findElement(new By.ByClassName("checkout")).click());
    waitForLoad(() -> driver.findElement(new By.ById("order_billing_name")).sendKeys(name));
    driver.findElement(new By.ById("order_email")).sendKeys(email);
    driver.findElement(new By.ById("order_tel")).click();
    driver.findElement(new By.ById("order_tel")).sendKeys(phoneNumber.substring(0, 2));
    driver.findElement(new By.ById("order_tel")).sendKeys(phoneNumber.substring(2));
    driver.findElement(new By.ById("bo")).sendKeys(address);
    driver.findElement(new By.ById("oba3")).sendKeys(address2);
    driver.findElement(new By.ById("order_billing_zip")).sendKeys(zipcode);

    // Select cardSelect = new Select(driver.findElement(new By.ById(
    //       "credit_card_type")));
    //cardSelect.selectByVisibleText(ccType);
    // TODO
    driver.findElement(new By.ById("cnb")).click();
    driver.findElement(new By.ById("cnb")).clear();
    for (int i = 0; i < 16; i++) {
      driver.findElement(new By.ById("cnb")).sendKeys(Character.toString(ccNumber.charAt(i)));
    }
    driver.findElement(new By.ById("cnb")).sendKeys(ccNumber.substring(0, 14));
    driver.findElement(new By.ById("cnb")).sendKeys(ccNumber.substring(14));
    if (ccMonth.length() < 2) {
      ccMonth = "0" + ccMonth;
    }

    Select monthSelect = new Select(driver.findElement(new By.ById("credit_card_month")));
    monthSelect.selectByVisibleText(ccMonth);

    Select yearSelect = new Select(driver.findElement(new By.ById("credit_card_year")));
    yearSelect.selectByVisibleText(ccYear);

    driver.findElement(new By.ById("vval")).sendKeys(cvv);
    Actions action = new Actions(driver);
    WebElement orderTerms = driver.findElement(new By.ById("order_terms"));
    action.moveToElement(orderTerms).click().build().perform();

    //driver.findElement(new By.ByClassName("checkout")).click();
  }

  /**
   * Helper method that waits for the given runnable to execute successfully.
   *
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
