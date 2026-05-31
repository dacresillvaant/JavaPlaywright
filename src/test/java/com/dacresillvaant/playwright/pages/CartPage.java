package com.dacresillvaant.playwright.pages;

import com.dacresillvaant.playwright.utils.PageActions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartPage {

    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    private Locator cartItems() {
        return page.locator("[data-test='inventory-item']");
    }

    private Locator cartItemNames() {
        return page.locator("[data-test='inventory-item-name']");
    }

    private Locator cartItemPrices() {
        return page.locator("[data-test='inventory-item-price']");
    }

    private Locator removeButton(String productName) {
        return page.locator(String.format("[data-test='remove-%s']",
                productName.toLowerCase().replace(" ", "-")));
    }

    private Locator continueShoppingButton() {
        return page.locator("[data-test='continue-shopping']");
    }

    private Locator checkoutButton() {
        return page.locator("[data-test='checkout']");
    }

    public boolean isCartPageOpened() {
        return page.url().contains("/cart.html");
    }

    public int getCartItemCount() {
        return cartItems().count();
    }

    public java.util.List<String> getCartItemNames() {
        return cartItemNames().allInnerTexts();
    }

    public java.util.List<String> getCartItemPrices() {
        return cartItemPrices().allInnerTexts();
    }

    public void removeFromCart(String productName) {
        log.info("Removing product from cart: {}", productName);
        PageActions.click(removeButton(productName));
    }

    public void continueShopping() {
        log.info("Clicking continue shopping");
        PageActions.click(continueShoppingButton());
    }

    public void proceedToCheckout() {
        log.info("Clicking checkout");
        PageActions.click(checkoutButton());
    }

    public boolean isCheckoutButtonVisible() {
        return PageActions.isVisible(checkoutButton());
    }

    public boolean isContinueShoppingButtonVisible() {
        return PageActions.isVisible(continueShoppingButton());
    }
}