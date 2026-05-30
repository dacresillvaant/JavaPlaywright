1. Before running any Playwright test, you have to install the browsers that Playwright will use. You can do this by running the following command in your terminal:
```
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```
2. Test can be run from maven with the following sample command:
```
mvn test -Dsuite.file=src/test/resources/suites/login-tests.xml
```