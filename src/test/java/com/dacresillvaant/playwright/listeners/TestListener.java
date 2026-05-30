package com.dacresillvaant.playwright.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("========== STARTING TEST: {} ==========", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("========== FAILED: {} ==========", result.getName());
        log.error("Failure reason: {}", result.getThrowable().getMessage());
    }
}