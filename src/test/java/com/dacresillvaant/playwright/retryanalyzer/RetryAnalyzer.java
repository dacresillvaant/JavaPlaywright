package com.dacresillvaant.playwright.retryanalyzer;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.error("Test failed due to: {}", result.getThrowable().getMessage());
            log.warn("Retrying test '{}' ({} out of {})", result.getName(), retryCount, MAX_RETRY_COUNT);
            return true; // Retry the test
        }
        return false; // Do not retry
    }
}