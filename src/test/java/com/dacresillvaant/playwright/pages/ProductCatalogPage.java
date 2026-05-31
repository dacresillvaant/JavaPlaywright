package com.dacresillvaant.playwright.pages;

import com.dacresillvaant.playwright.utils.PageActions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProductCatalogPage {

    private static final String URL = "https://www.saucedemo.com/inventory.html";
    @Getter
    private static final int EXPECTED_PRODUCT_COUNT = 6;

    private final Page page;

    public ProductCatalogPage(Page page) {
        this.page = page;
    }

    private Locator productItems() {
        return page.locator("[data-test='inventory-item']");
    }

    private Locator productNames() {
        return page.locator("[data-test='inventory-item-name']");
    }

    private Locator productPrices() {
        return page.locator("[data-test='inventory-item-price']");
    }

    private Locator sortDropdown() {
        return page.locator("[data-test='product-sort-container']");
    }

    private Locator cartBadge() {
        return page.locator("[data-test='shopping-cart-badge']");
    }

    private Locator cartIcon() {
        return page.locator("[data-test='shopping-cart-link']");
    }

    private Locator addToCartButton(String productName) {
        return page.locator(String.format("[data-test='add-to-cart-%s']",
                productName.toLowerCase().replace(" ", "-")));
    }

    private Locator removeButton(String productName) {
        return page.locator(String.format("[data-test='remove-%s']",
                productName.toLowerCase().replace(" ", "-")));
    }

    public ProductCatalogPage open() {
        log.info("Opening product catalog page");
        PageActions.navigate(page, URL);
        return this;
    }

    public int getProductCount() {
        return productItems().count();
    }

    public boolean hasExpectedProductCount() {
        return getProductCount() == EXPECTED_PRODUCT_COUNT;
    }

    public List<String> getProductNames() {
        return productNames().allInnerTexts();
    }

    public List<String> getProductPrices() {
        return productPrices().allInnerTexts();
    }

    public void sortBy(String option) {
        log.info("Sorting products by: {}", option);
        sortDropdown().selectOption(option);
    }

    public void addToCart(String productName) {
        log.info("Adding product to cart: {}", productName);
        PageActions.click(addToCartButton(productName));
    }

    public void removeFromCart(String productName) {
        log.info("Removing product from cart: {}", productName);
        PageActions.click(removeButton(productName));
    }

    public String getCartBadgeCount() {
        return PageActions.getText(cartBadge());
    }

    public boolean isProductCatalogPageOpened() {
        return PageActions.getCurrentUrl(page).equals(URL);
    }

    public boolean isCartBadgeVisible() {
        return PageActions.isVisible(cartBadge());
    }

    public void goToCart() {
        log.info("Navigating to cart");
        PageActions.click(cartIcon());
    }
}
