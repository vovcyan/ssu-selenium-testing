package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

public class IndexPage extends BasePage {

    /**Constructor*/
    public IndexPage(WebDriver driver) {
        super(driver);
    }

    /**Web Elements*/
    By mainCatalogBtn = By.cssSelector("div[data-widget=\"catalogMenu\"]");
    By mainCatalog = By.xpath("//*[@id=\"__ozon\"]/div/div[1]/header/div[1]/div[2]/div/div[2]");
    By mainCatalogCategory = By.xpath("//*[@id=\"__ozon\"]/div/div[1]/header/div[1]/div[2]/div/div[2]/div/div[1]/div/a");
    By mainCatalogSubCategoryContainer = By.xpath("//*[@id=\"__ozon\"]/div/div[1]/header/div[1]/div[2]/div/div[2]/div/div[2]");
    By catalogPageHeader = By.cssSelector("div[data-widget=\"catalogResultsHeader\"]");

    /**Page Methods*/
    @Step("Open site catalog")
    public CatalogResultPage openSiteCatalogWith(String category, @Nullable String subcategory) {
        click(mainCatalogBtn);
        waitVisibility(mainCatalog);

        List<WebElement> categoryList = driver.findElements(mainCatalogCategory);

        for (WebElement categoryEl : categoryList) {
            if (categoryEl.getText().contains(category)) {
                if (subcategory == null) {
                    click(categoryEl);
                    return new CatalogResultPage(driver);
                }

                hover(categoryEl);

                By subCategoryLink = By.tagName("a");
                List<WebElement> subCategoryElList = driver
                        .findElement(mainCatalogSubCategoryContainer)
                        .findElements(subCategoryLink);

                for (WebElement subCategoryEl : subCategoryElList) {
                    if (subCategoryEl.getText().contains(subcategory)) {
                        click(subCategoryEl);
                        break;
                    }
                }

                break;
            }
        }

        waitVisibility(catalogPageHeader);
        return new CatalogResultPage(driver);
    }
}