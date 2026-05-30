package com.dacresillvaant.playwright.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrowserFactory {

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();

    public static BrowserContext getBrowserContextFromThreadLocal() {
        return browserContextThreadLocal.get();
    }

    public static void initializePlaywrightAndBrowser(String browserType, boolean headless) {
        log.info("Initializing browser: {} with headless mode set to: {}", browserType, headless);
        Playwright playwright = Playwright.create();
        playwrightThreadLocal.set(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(headless);

        Browser browser = switch (browserType.toLowerCase()) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        };

        browserThreadLocal.set(browser);

        BrowserContext context = browser.newContext();
        browserContextThreadLocal.set(context);

        log.info("{} browser initialized",  browserType);
    }

    public static void closeBrowserAndPlaywright() {
        log.info("Closing browser");
        try {
            if (browserContextThreadLocal.get() != null) browserContextThreadLocal.get().close();
            if (browserThreadLocal.get() != null) browserThreadLocal.get().close();
            if (playwrightThreadLocal.get() != null) playwrightThreadLocal.get().close();
        } finally {
            browserContextThreadLocal.remove();
            browserThreadLocal.remove();
            playwrightThreadLocal.remove();
        }
        log.info("Browser closed");
    }
}