package com.dacresillvaant.playwright.tests;

import com.dacresillvaant.playwright.pages.LoginPage;
import com.dacresillvaant.playwright.pages.ProductCatalogPage;
import com.dacresillvaant.playwright.pages.ProductDetailPage;
import com.dacresillvaant.playwright.retryanalyzer.RetryAnalyzer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Product Detail")
@Feature("Product Detail Page")
public class ProductDetailsTests extends BaseTest {

    private LoginPage loginPage;
    private ProductCatalogPage productCatalogPage;
    private ProductDetailPage productDetailPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(baseTestPage);
        productCatalogPage = new ProductCatalogPage(baseTestPage);
        productDetailPage = new ProductDetailPage(baseTestPage);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product info")
    public void testProductDetailPageIsOpened() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product info")
    public void testProductNameIsDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened());
        Assert.assertEquals(productDetailPage.getProductName(), "Sauce Labs Backpack");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product info")
    public void testProductPriceIsDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened());
        Assert.assertTrue(productDetailPage.getProductPrice().startsWith("$"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product info")
    public void testProductDescriptionIsDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened());
        Assert.assertFalse(productDetailPage.getProductDescription().isEmpty());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Product info")
    public void testProductImageIsDisplayed() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");

//      expect
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened());
        Assert.assertTrue(productDetailPage.isProductImageVisible());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart")
    public void testAddToCartFromDetailPage() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");
        productDetailPage.addToCart();

//      expect
        Assert.assertTrue(productDetailPage.isRemoveButtonVisible());
        Assert.assertFalse(productDetailPage.isAddToCartButtonVisible());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Cart")
    public void testRemoveFromCartFromDetailPage() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");
        productDetailPage.addToCart();
        productDetailPage.removeFromCart();

//      expect
        Assert.assertTrue(productDetailPage.isAddToCartButtonVisible());
        Assert.assertFalse(productDetailPage.isRemoveButtonVisible());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Navigation")
    public void testBackButtonNavigatesToCatalog() {
//      when
        loginPage.open().loginWithStandardUser();
        productCatalogPage.clickOnProduct("Sauce Labs Backpack");
        productDetailPage.goBackToProducts();

//      expect
        Assert.assertTrue(productCatalogPage.isProductCatalogPageOpened());
    }
}