# JavaPlaywright

A test automation framework built with **Microsoft Playwright** and **Java**, designed for testing of web applications. 
This project uses **TestNG** for test orchestration and **Allure** for test reporting.

Tests are performed on the [Sauce Demo](https://www.saucedemo.com/) website, which provides a sample e-commerce platform for testing purposes.

## 🎯 Key Features

- **Cross-Browser Testing**: Support for Chromium, Firefox, and WebKit browsers
- **Page Object Model (POM)**: Well-organized page classes for maintainability
  - PageActions class for common page interactions
- **Test Coverage**:
  - Authentication & Login validation
  - Product catalog browsing
  - Product details verification
  - Shopping cart operations
- **Reporting**: Allure test reports
- **Test Retry Mechanism**: Automatic retry for flaky tests
- **Screenshot Capture**: Automatic screenshots on test failures
- **Structured Logging**: Detailed logs using Logback and SLF4J
- **Multiple Test Suites**: Organized test execution profiles
  - headless mode supported for all suites
- **Parallel Test Execution**: Run tests concurrently
  - default-suite.xml - 6 threads -> 3 browsers in parallel and each browser uses 2 threads
  - other suite files - 3 threads -> 1 thread per browser

## 🏗️ Project Structure

```
JavaPlaywright/
├── src/
│   ├── test/java/com/dacresillvaant/playwright/
│   │   ├── tests/              # Test classes
│   │   ├── pages/              # Page Object Model classes
│   │   ├── factory/            # Browser factory for initialization
│   │   ├── utils/              # Utility classes (WaitUtils, ScreenshotUtils, PageActions)
│   │   ├── listeners/          # TestNG listeners for event handling
│   │   ├── retryanalyzer/      # Retry logic for failed tests
│   │   └── testdata/           # Test data (user credentials)
│   └── test/resources/
│       ├── suites/             # TestNG suite XML files
│       └── logback-test.xml    # Logging configuration
├── target/
│   ├── allure-report/          # Generated Allure reports
│   ├── allure-results/         # Test results in JSON format
│   └── screenshots/            # Failure screenshots
└── pom.xml                     # Maven configuration
```

## 📋 Test Suites

The project includes multiple test suites for organized execution:

- **default-suite.xml** - Runs all tests
- **login-suite.xml** - Authentication and login tests
- **cart-suite.xml** - Shopping cart functionality tests
- **product-catalog-suite.xml** - Product listing and browsing tests
- **product-details-suite.xml** - Individual product details tests

## 🚀 Prerequisites

Before running tests, ensure you have:

- **Java 21** or higher
- **Maven 3.6** or higher
- **Git** (for version control)

## 📦 Installation

1. Clone the repository:

2. Install Playwright browsers:
```
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```
3. Build the project and download dependencies:
```
mvn clean install
```

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn clean test -Dsuite.file=src/test/resources/suites/login-suite.xml
```

Supported browser types: `chromium`, `firefox`, `webkit`

## 📊 Viewing Test Reports

### Generate Allure Report
After test execution, generate the Allure report:
```bash
mvn allure:report
```

The report will be generated in `target/allure-report/`

### Open Allure Report
```bash
mvn allure:serve
```

This command opens the interactive Allure report in your default browser.

## 🛠️ Key Technologies & Versions

| Technology | Version |
|-----------|---------|
| Playwright | 1.60.0 |
| TestNG | 7.12.0 |
| Allure | 2.35.1 |
| Java | 21 |
| Maven Surefire | 3.5.5 |
| Logback | 1.5.32 |
| Lombok | 1.18.46 |
---

**Project by**: Dacresillvaant  
**Last Updated**: may 2026

