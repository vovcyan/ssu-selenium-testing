package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.Log;
import java.util.List;

public class CatalogResultPage extends BasePage {
    public CatalogResultPage(WebDriver driver) {
        super(driver);
    }

    By priceRangeFieldFrom = By.cssSelector("input[qa-id=\"range-from\"]");
    By priceRangeFieldTo = By.cssSelector("input[qa-id=\"range-to\"]");
    By productItem = By.xpath("//*[@id=\"__ozon\"]/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[1]/div/div/div");
    By header = By.cssSelector("div[data-widget=\"catalogResultsHeader\"]");
    By sortSelector = By.xpath("//*[@id=\"__ozon\"]/div/div[1]/div[3]/div[2]/div[2]/div[2]/div[1]/div/div/div[1]");
    By loadingIndicator = By.cssSelector(".dots.dots-blue");
    By filterBlock = By.cssSelector("div.filter-block");

    @Step("Set price range {0} - {1}")
    public CatalogResultPage setPriceRange(int from, int to) {
        writeText(priceRangeFieldTo, Integer.toString(to));
        applyFilters();

        writeText(priceRangeFieldFrom, Integer.toString(from));
        applyFilters();

        return this;
    }

    @Step("Check if products exists in the list")
    public CatalogResultPage checkIfProductListNotEmpty() {
        List<WebElement> productItemList = driver.findElements(productItem);
        Assert.assertTrue(productItemList.size() > 0);
        return this;
    }

    @Step("Check if products are in price range")
    public CatalogResultPage checkIfProductInPriceRange(int from, int to) {
        List<WebElement> productItemsList = driver.findElements(productItem);

        for (WebElement productItem : productItemsList) {
            WebElement productPrice = productItem.findElement(By.cssSelector("div.b5v4.a5d2 > span.b5v6"));

            String rawPrice = productPrice.getText();
            int parsedPrice = parsePrice(rawPrice, null);

            boolean isPriceInRange = parsedPrice >= from && parsedPrice <= to;

            if (!isPriceInRange) {
                // To focus on the element
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].scrollIntoView(true);", productItem);
                wait(500);
            }

            Assert.assertTrue(isPriceInRange, rawPrice);
        }

        return this;
    }

    @Step("Select sort from low to high")
    public CatalogResultPage sortFromLowToHigh() {
        click(sortSelector);

        wait(1500);

        click(By.cssSelector(".vue-portal-target div[title=\"Сначала дешевые\"]"));
        applyFilters();

        return this;
    }

    @Step("Add product into cart")
    public CatalogResultPage addProductIntoCartById(int index) {
        List<WebElement> productItemsList = driver.findElements(productItem);
        WebElement selectedProductItem = productItemsList.get(index);
        WebElement addToCartBtn = selectedProductItem.findElement(By.cssSelector(".a2j0.a3x5 button.ui-k6"));

        Log.info(addToCartBtn.getText());

        addToCartBtn.click();
        applyFilters();

        return this;
    }

    @Step("Append filter")
    public CatalogResultPage addFilter(String name, String value) {
        List<WebElement> filterList = driver.findElements(filterBlock);

        for (WebElement filter : filterList) {
            WebElement title = filter.findElement(By.cssSelector("div"));

            if (title.getText().contains(name)) {
                List<WebElement> filterOptionsList = filter.findElements(By.cssSelector("span.c0y6"));

                for (WebElement option : filterOptionsList) {
                    Log.info(option.getText());

                    if (option.getText().contains(value)) {
                        option.click();
                        break;
                    }
                }

                wait(10000);

                break;
            }
        }

        return this;
    }

    @Step("Add product into favorites")
    public CatalogResultPage addProductIntoFavoritesById(int index) {
        List<WebElement> productItemsList = driver.findElements(productItem);
        WebElement selectedProductItem = productItemsList.get(index);
        WebElement addToFavoritesBtn = selectedProductItem.findElement(By.cssSelector(".ui-k4 > button.ui-k6"));

        addToFavoritesBtn.click();
        applyFilters();

        return this;
    }

    private void applyFilters() {
        click(header);
        wait(3000);

        //waitVisibility(loadingIndicator);
        //waitForHide(loadingIndicator);
    }
}
