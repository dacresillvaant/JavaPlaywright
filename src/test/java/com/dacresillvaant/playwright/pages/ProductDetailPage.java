package com.dacresillvaant.playwright.pages;

import com.dacresillvaant.playwright.utils.PageActions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductDetailPage {

    private final Page page;

    public ProductDetailPage(Page page) {
        this.page = page;
    }

    private Locator productName() {
        return page.locator("[data-test='inventory-item-name']");
    }

    private Locator productDescription() {
        return page.locator("[data-test='inventory-item-desc']");
    }

    private Locator productPrice() {
        return page.locator("[data-test='inventory-item-price']");
    }

    private Locator productImage() {
        return page.locator(".inventory_details_img");
    }

    private Locator addToCartButton() {
        return page.locator("[data-test^='add-to-cart']");
    }

    private Locator removeButton() {
        return page.locator("[data-test^='remove']");
    }

    private Locator backButton() {
        return page.locator("[data-test='back-to-products']");
    }

    public boolean isProductDetailPageOpened() {
        return page.url().contains("/inventory-item.html");
    }

    public String getProductName() {
        return PageActions.getText(productName());
    }

    public String getProductDescription() {
        return PageActions.getText(productDescription());
    }

    public String getProductPrice() {
        return PageActions.getText(productPrice());
    }

    public boolean isProductImageVisible() {
        return PageActions.isVisible(productImage());
    }

    public boolean isAddToCartButtonVisible() {
        return PageActions.isVisible(addToCartButton());
    }

    public boolean isRemoveButtonVisible() {
        return PageActions.isVisible(removeButton());
    }

    public void addToCart() {
        log.info("Adding product to cart from detail page");
        PageActions.click(addToCartButton());
    }

    public void removeFromCart() {
        log.info("Removing product from cart from detail page");
        PageActions.click(removeButton());
    }

    public void goBackToProducts() {
        log.info("Navigating back to products");
        PageActions.click(backButton());
    }
}