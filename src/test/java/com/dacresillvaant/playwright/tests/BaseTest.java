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

    @BeforeClass
    public void setUpPlaywright() {
        BrowserFactory.initializePlaywright();
    }

    @BeforeMethod
    @Parameters({"browserType", "headless"})
    public void setUpBrowser(@Optional("chromium") String browserType, @Optional("false") String headless) {
        BrowserFactory.initializeBrowserAndContext(browserType, Boolean.parseBoolean(headless));
        BrowserContext browserContext = BrowserFactory.getBrowserContextFromThreadLocal();
        baseTestPage = browserContext.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        if (baseTestPage != null) baseTestPage.close();
        BrowserFactory.closeBrowserAndContext();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownPlaywright() {
        BrowserFactory.closePlaywright();
    }
}