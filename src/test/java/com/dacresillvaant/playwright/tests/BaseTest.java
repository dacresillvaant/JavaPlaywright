package com.dacresillvaant.playwright.tests;

import com.dacresillvaant.playwright.factory.BrowserFactory;
import com.dacresillvaant.playwright.listeners.TestListener;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

@Slf4j
@Listeners(TestListener.class)
public abstract class BaseTest {

    protected Page baseTestPage;

    @BeforeMethod
    @Parameters({"browserType", "headless"})
    public void setUp(@Optional("chromium") String browserType, @Optional("false") String headless) {
        BrowserFactory.initializePlaywrightAndBrowser(browserType, Boolean.parseBoolean(headless));
        BrowserContext browserContext = BrowserFactory.getBrowserContextFromThreadLocal();
        baseTestPage = browserContext.newPage();
        log.info("Test setup complete");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (baseTestPage != null) baseTestPage.close();
        BrowserFactory.closeBrowserAndPlaywright();
        log.info("Test teardown complete");
    }
}