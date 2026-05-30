package com.dacresillvaant.playwright.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WaitUtils {

    public static void waitForVisible(Locator locator, int timeoutMs) {
        log.info("Waiting for element to be visible: {} with timeout: {}ms", locator, timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
    }

    public static void waitForEnabled(Locator locator, int timeoutMs) {
        log.info("Waiting for element to be enabled: {} with timeout: {}ms", locator, timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(timeoutMs));
    }

    public static void waitForNetworkIdle(Page page) {
        log.info("Waiting for network idle");
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public static void waitForUrl(Page page, String urlPattern) {
        log.info("Waiting for URL to match: {}", urlPattern);
        page.waitForURL(urlPattern);
    }
}