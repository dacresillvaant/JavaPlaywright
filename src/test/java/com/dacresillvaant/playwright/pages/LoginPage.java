package com.dacresillvaant.playwright.pages;

import com.dacresillvaant.playwright.utils.PageActions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage {

    private static final String URL = "https://www.saucedemo.com/";

    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    private Locator usernameInput() {
        return page.locator("[data-test='username']");
    }

    private Locator passwordInput() {
        return page.locator("[data-test='password']");
    }

    private Locator loginButton() {
        return page.locator("[data-test='login-button']");
    }

    private Locator errorMessage() {
        return page.locator("[data-test='error']");
    }

    public LoginPage open() {
        log.info("Opening login page");
        PageActions.navigate(page, URL);
        return this;
    }

    public void login(String username, String password) {
        log.info("Logging in with username: {}", username);
        PageActions.fill(usernameInput(), username);
        PageActions.fill(passwordInput(), password);
        PageActions.click(loginButton());
    }

    public String getErrorMessage() {
        return PageActions.getText(errorMessage());
    }

    public boolean isErrorMessageVisible() {
        return PageActions.isVisible(errorMessage());
    }
}