package com.dacresillvaant.playwright.tests;

import com.dacresillvaant.playwright.pages.LoginPage;
import com.dacresillvaant.playwright.pages.ProductCatalogPage;
import com.dacresillvaant.playwright.retryanalyzer.RetryAnalyzer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

@Epic("Product Catalog")
@Feature("Inventory")
public class ProductCatalogTests extends BaseTest{

    private LoginPage loginPage;
    private ProductCatalogPage productCatalogPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(baseTestPage);
        productCatalogPage = new ProductCatalogPage(baseTestPage);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product display")
    public void testProductCountIsCorrect() {
//      when
        loginPage.open().loginWithStandardUser();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
        Assert.assertTrue(productCatalogPage.hasExpectedProductCount());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product display")
    public void testAllProductNamesAreDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        List<String> names = productCatalogPage.getProductNames();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());

        Assert.assertEquals(names.size(), ProductCatalogPage.getEXPECTED_PRODUCT_COUNT());
        Assert.assertTrue(names.contains("Sauce Labs Backpack"));
        Assert.assertTrue(names.contains("Sauce Labs Bike Light"));
        Assert.assertTrue(names.contains("Sauce Labs Bolt T-Shirt"));
        Assert.assertTrue(names.contains("Sauce Labs Fleece Jacket"));
        Assert.assertTrue(names.contains("Sauce Labs Onesie"));
        Assert.assertTrue(names.contains("Test.allTheThings() T-Shirt (Red)"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product display")
    public void testAllProductPricesAreDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        List<String> prices = productCatalogPage.getProductPrices();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
        Assert.assertEquals(prices.size(), ProductCatalogPage.getEXPECTED_PRODUCT_COUNT());
        prices.forEach(price -> Assert.assertTrue(price.startsWith("$")));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Sorting")
    public void testSortByNameAscending() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.sortBy("az");
        List<String> names = productCatalogPage.getProductNames();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
        Assert.assertEquals(names, names.stream().sorted().toList());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Sorting")
    public void testSortByNameDescending() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.sortBy("za");
        List<String> names = productCatalogPage.getProductNames();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
        Assert.assertEquals(names, names.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart")
    public void testAddProductToCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productCatalogPage.isCartBadgeVisible());
        Assert.assertEquals(productCatalogPage.getCartBadgeCount(), "1");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart")
    public void testAddMultipleProductsToCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.addToCart("Sauce Labs Bike Light");

//      expect
        Assert.assertTrue(productCatalogPage.isCartBadgeVisible());
        Assert.assertEquals(productCatalogPage.getCartBadgeCount(), "2");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart")
    public void testRemoveProductFromCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.removeFromCart("Sauce Labs Backpack");

//      expect
        Assert.assertFalse(productCatalogPage.isCartBadgeVisible());
    }
}