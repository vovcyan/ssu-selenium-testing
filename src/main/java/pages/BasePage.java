package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Log;

import javax.annotation.Nullable;

public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        wait = new WebDriverWait(driver,20);
    }

    By cartNavBtn = By.cssSelector("a[data-widget=\"cart\"]");
    By cartPageContainer = By.cssSelector("div.b4j6.b5y1");

    public void click(By location) {
        driver.findElement(location).click();
    }

    public void click(WebElement element) {
        element.click();
    }

    public void hover(By location) {
        actions.moveToElement(driver.findElement(location)).build().perform();
    }

    public void hover(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void writeText(By elementLocation, String text) {
        String clearCmd = Keys.chord(Keys.COMMAND, "a", Keys.BACK_SPACE);
        driver.findElement(elementLocation).sendKeys(clearCmd, text);
    }

    public String readText(By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }

    public void waitVisibility(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForHide(By by){
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(by)));
    }

    @Step("Open cart page")
    public CartPage goToCartPage() {
        click(cartNavBtn);
        waitVisibility(cartPageContainer);
        return new CartPage(driver);
    }

    public int parsePrice(String rawPrice, @Nullable String separator) {
        if (separator == null) {
            separator = " ";
        }

        Log.info(rawPrice);

        String[] priceSegments = rawPrice.split(separator);

        // Remove symbol of currency
        priceSegments[priceSegments.length - 1] = "";

        String price = String.join("", priceSegments);
        return Integer.parseInt(price);
    }

    public void wait(int time) {
        try {
            Thread.sleep(1500);
        } catch (Exception ignored) {
        }
    }
}