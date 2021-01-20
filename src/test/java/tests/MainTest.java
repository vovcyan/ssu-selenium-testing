package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Listeners.TestListener;

@Listeners({ TestListener.class })
@Feature("Main Test")
public class MainTest extends BaseTest {

    int priceFrom = 10000;
    int priceTo = 11000;
    
    String category = "Бытовая техника";
    String subCategory = "Кофеварки и кофемашины";

    @Test (priority = 0, description="Navigate to product catalog and add the cheapest product to cart")
    @Severity(SeverityLevel.BLOCKER)
    public void productCatalog_BasicScenario () {
        homePage
            .goToIndex()
            .openSiteCatalogWith(category, subCategory)
            .setPriceRange(priceFrom, priceTo)
            .checkIfProductListNotEmpty()
            .checkIfProductInPriceRange(priceFrom, priceTo)
            .sortFromLowToHigh()
            .addProductIntoCartById(0)
            .goToCartPage()
            .increaseProductAndCheck(3);
    }

    @Test (priority = 0, description="Navigate to product catalog and add the cheapest product to cart with extra filters")
    @Severity(SeverityLevel.BLOCKER)
    public void productCatalog_ExtendedScenario () {
        homePage
                .reset()
                .goToIndex()
                .openSiteCatalogWith(category, subCategory)
                .setPriceRange(priceFrom, priceTo)
                .addFilter("Тип кофеварки", "Рожковая")
                .addFilter("Приготовление напитка", "Подогрев чашек")
                .checkIfProductListNotEmpty()
                .checkIfProductInPriceRange(priceFrom, priceTo)
                .sortFromLowToHigh()
                .addProductIntoCartById(0)
                .goToCartPage()
                .increaseProductAndCheck(3);
    }

    @Test (priority = 0, description="Navigate to product catalog and add the cheapest product to favorites")
    @Severity(SeverityLevel.BLOCKER)
    public void productCatalog_Favorites () {
        homePage
                .reset()
                .goToIndex()
                .openSiteCatalogWith(category, subCategory)
                .setPriceRange(priceFrom, priceTo)
                .checkIfProductListNotEmpty()
                .checkIfProductInPriceRange(priceFrom, priceTo)
                .sortFromLowToHigh()
                .addProductIntoFavoritesById(0);
    }
}