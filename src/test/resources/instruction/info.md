1. Before running any Playwright test, you have to install the browsers that Playwright will use. You can do this by running the following command in your terminal:
```
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```
