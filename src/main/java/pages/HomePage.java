package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    /**Constructor*/
    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**Variables*/
    String baseURL = "https://ozon.ru";

    /**Page Methods*/
    //Go to Homepage
    @Step("Open Index page")
    public IndexPage goToIndex() {
        driver.manage().window().maximize();
        driver.get(baseURL);

        wait(2000);

        WebElement promoModalCloseBtn = driver.findElement(By.xpath("//*[@id=\"__ozon\"]/div/div[1]/div[3]/div[4]/div/div/div/div[2]/div/div/div/div/div[3]/div/button"));

        if (promoModalCloseBtn.isDisplayed()) {
            promoModalCloseBtn.click();
        }

        return new IndexPage(driver);
    }

    public HomePage reset() {
        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);

        return this;
    }
}