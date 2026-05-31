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

    private static void initializePlaywright() {
        log.info("Initializing Playwright");
        long start = System.currentTimeMillis();
        try {
            Playwright playwright = Playwright.create();
            playwrightThreadLocal.set(playwright);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Playwright", e);
        }
        log.info("Playwright initialized in {} ms", System.currentTimeMillis() - start);
    }

    public static void initializePlaywrightAndBrowser(String browserType, boolean headless) {
        initializePlaywright();
        log.info("Initializing browser: {} with headless mode set to: {}", browserType, headless);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(headless);

        long start = System.currentTimeMillis();
        Browser browser = switch (browserType.toLowerCase()) {
            case "chromium" -> playwrightThreadLocal.get().chromium().launch(launchOptions);
            case "firefox" -> playwrightThreadLocal.get().firefox().launch(launchOptions);
            case "webkit" -> playwrightThreadLocal.get().webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        };

        browserThreadLocal.set(browser);

        BrowserContext context = browser.newContext();
        browserContextThreadLocal.set(context);

        log.info("{} browser initialized in {} ms",  browserType,  System.currentTimeMillis() - start);
    }

    public static void closeBrowserAndPlaywright() {
        log.info("Closing browser and Playwright");
        try {
            if (browserContextThreadLocal.get() != null) browserContextThreadLocal.get().close();
            if (browserThreadLocal.get() != null) browserThreadLocal.get().close();
            if (playwrightThreadLocal.get() != null) playwrightThreadLocal.get().close();
        } finally {
            browserContextThreadLocal.remove();
            browserThreadLocal.remove();
            playwrightThreadLocal.remove();
        }
        log.info("Browser and Playwright closed");
    }
}