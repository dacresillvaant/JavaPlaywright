package com.dacresillvaant.playwright.tests;

import com.dacresillvaant.playwright.pages.CartPage;
import com.dacresillvaant.playwright.pages.LoginPage;
import com.dacresillvaant.playwright.pages.ProductCatalogPage;
import com.dacresillvaant.playwright.retryanalyzer.RetryAnalyzer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Cart")
@Feature("Shopping Cart")
public class CartTests extends BaseTest {

    private LoginPage loginPage;
    private ProductCatalogPage productCatalogPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(baseTestPage);
        productCatalogPage = new ProductCatalogPage(baseTestPage);
        cartPage = new CartPage(baseTestPage);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart display")
    public void testCartPageIsOpened() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart display")
    public void testSingleItemIsDisplayedInCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        Assert.assertEquals(cartPage.getCartItemCount(), 1);
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Backpack"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart display")
    public void testMultipleItemsAreDisplayedInCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.addToCart("Sauce Labs Bike Light");
        productCatalogPage.goToCart();

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        Assert.assertEquals(cartPage.getCartItemCount(), 2);
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Backpack"));
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Bike Light"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart display")
    public void testCartItemPriceIsDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();
        List<String> prices = cartPage.getCartItemPrices();

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        prices.forEach(price -> Assert.assertTrue(price.startsWith("$")));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Remove from cart")
    public void testRemoveItemFromCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();
        cartPage.removeFromCart("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        Assert.assertEquals(cartPage.getCartItemCount(), 0);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Remove from cart")
    public void testRemoveOneOfMultipleItemsFromCart() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.addToCart("Sauce Labs Bike Light");
        productCatalogPage.goToCart();
        cartPage.removeFromCart("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        Assert.assertEquals(cartPage.getCartItemCount(), 1);
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Bike Light"));
        Assert.assertFalse(cartPage.getCartItemNames().contains("Sauce Labs Backpack"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Navigation")
    public void testContinueShoppingNavigatesToCatalog() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();
        cartPage.continueShopping();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Navigation")
    public void testCheckoutButtonIsVisible() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.addToCart("Sauce Labs Backpack");
        productCatalogPage.goToCart();

//      expect
        Assert.assertTrue(cartPage.isCartPageOpened());
        Assert.assertTrue(cartPage.isCheckoutButtonVisible());
        Assert.assertTrue(cartPage.isContinueShoppingButtonVisible());
    }
}