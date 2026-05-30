package com.dacresillvaant.playwright.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PageActions {

    public static void click(Locator locator) {
        log.info("Clicking: {}", locator);
        locator.click();
    }

    public static void fill(Locator locator, String text) {
        log.info("Filling: {} with text: {}", locator, text);
        locator.fill(text);
    }

    public static void clear(Locator locator) {
        log.info("Clearing: {}", locator);
        locator.clear();
    }

    public static String getText(Locator locator) {
        log.info("Getting text from: {}", locator);
        return locator.innerText();
    }

    public static boolean isVisible(Locator locator) {
        log.info("Checking visibility of: {}", locator);
        return locator.isVisible();
    }

    public static boolean isEnabled(Locator locator) {
        log.info("Checking if enabled: {}", locator);
        return locator.isEnabled();
    }

    public static void navigate(Page page, String url) {
        log.info("Navigating to: {}", url);
        page.navigate(url);
    }

    public static String getCurrentUrl(Page page) {
        log.info("Getting current URL");
        return page.url();
    }

    public static String getTitle(Page page) {
        log.info("Getting page title");
        return page.title();
    }
}