package com.dacresillvaant.playwright.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.IIOException;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreenshotUtils {

    private static final String SCREENSHOTS_DIR = "target/screenshots";

    public static void takeScreenshot(Page page, String testName) {
        try {
            Path screenshotDir = Paths.get(SCREENSHOTS_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            Path screenshotPath = screenshotDir.resolve(testName + "_failure.png");
            byte[] screenshotAsBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(screenshotPath));

            Allure.addAttachment(testName + "_failure", "image/png", new ByteArrayInputStream(screenshotAsBytes), "png");

            log.info("Screenshot saved to: {}", screenshotPath);
        } catch (IIOException e) {
            throw new RuntimeException("Failed to create screenshot directory", e);
        } catch (Exception e) {
            log.error("An error occurred while taking screenshot", e);
        }
    }
}