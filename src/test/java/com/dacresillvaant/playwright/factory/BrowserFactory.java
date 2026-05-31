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

    public static void initializePlaywright() {
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

    public static void initializeBrowserAndContext(String browserType, boolean headless) {
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

    public static void closeBrowserAndContext() {
        if (browserContextThreadLocal.get() != null) {
            try {
                String browserName = browserContextThreadLocal.get().browser().browserType().name();
                log.info("Closing {} Browser Context",  browserName);

                long start = System.currentTimeMillis();

                browserContextThreadLocal.get().close();
                browserContextThreadLocal.remove();
                log.info("{} BrowserContext closed in {} ms", browserName, System.currentTimeMillis() - start);
            } catch (Exception e) {
                throw new RuntimeException("Failed to close BrowserContext", e);
            }
        }

        if (browserThreadLocal.get() != null) {
            try {
                String browserName = browserThreadLocal.get().browserType().name();
                log.info("Closing {} Browser", browserName);
                long start = System.currentTimeMillis();

                browserThreadLocal.get().close();
                browserThreadLocal.remove();
                log.info("{} Browser closed in {} ms", browserName, System.currentTimeMillis() - start);
            } catch (Exception e) {
                throw new RuntimeException("Failed to close Browser", e);
            }
        }
    }

    public static void closePlaywright() {
        if (playwrightThreadLocal.get() != null) {
            try {
                log.info("Closing Playwright");
                long start = System.currentTimeMillis();

                playwrightThreadLocal.get().close();
                playwrightThreadLocal.remove();
                log.info("Playwright closed in {} ms", System.currentTimeMillis() - start);
            } catch (Exception e) {
                throw new RuntimeException("Failed to close Playwright", e);
            }
        }
    }
}