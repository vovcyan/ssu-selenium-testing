package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    By totalPrice = By.cssSelector("section[data-widget=\"total\"] .a5a6 span");
    By countSelector = By.cssSelector("div.ui-a0l0.ui-a0g5.ui-a0j2.ui-a0l1");

    @Step("Increase number of products ({0}) and check total price")
    public CartPage increaseProductAndCheck(int count) {
        WebElement totalPriceEl = driver.findElements(totalPrice).get(1);
        int totalPriceValue = parsePrice(totalPriceEl.getText(), " ");
        click(countSelector);
        wait(500);

        click(By.cssSelector(String.format(".vue-portal-target div[title=\"%s\"]", count)));
        wait(500);

        int newTotalPriceValue = parsePrice(totalPriceEl.getText(), " ");
        Assert.assertEquals(totalPriceValue * 3, newTotalPriceValue);

        return this;
    }
}
