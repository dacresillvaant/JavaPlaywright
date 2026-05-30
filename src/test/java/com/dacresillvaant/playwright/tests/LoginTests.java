package com.dacresillvaant.playwright.tests;

import com.dacresillvaant.playwright.listeners.TestListener;
import com.dacresillvaant.playwright.pages.LoginPage;
import com.dacresillvaant.playwright.retryanalyzer.RetryAnalyzer;
import com.dacresillvaant.playwright.testdata.UserCredentials;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Epic("Authentication")
@Feature("Login")
public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(baseTestPage);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Valid login")
    public void testLoginWithStandardUser() {
//      when
        loginPage.open().login(
                UserCredentials.STANDARD_USER.getUsername(),
                UserCredentials.STANDARD_USER.getPassword()
        );

//      expect
        Assert.assertTrue(baseTestPage.url().contains("/inventory.html"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Valid login")
    public void testLoginWithProblemUser() {
//      when
        loginPage.open().login(
                UserCredentials.PROBLEM_USER.getUsername(),
                UserCredentials.PROBLEM_USER.getPassword()
        );

//      expect
        Assert.assertTrue(baseTestPage.url().contains("/inventory.html"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Valid login")
    public void testLoginWithPerformanceGlitchUser() {
//      when
        loginPage.open().login(
                UserCredentials.PERFORMANCE_GLITCH_USER.getUsername(),
                UserCredentials.PERFORMANCE_GLITCH_USER.getPassword()
        );

//      expect
        Assert.assertTrue(baseTestPage.url().contains("/inventory.html"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Invalid login")
    public void testNotLoginWithLockedOutUser() {
//      when
        loginPage.open().login(
                UserCredentials.LOCKED_OUT_USER.getUsername(),
                UserCredentials.LOCKED_OUT_USER.getPassword()
        );

//      expect
        Assert.assertTrue(loginPage.isErrorMessageVisible());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Invalid login")
    public void testNotLoginWithInvalidCredentials() {
//      when
        loginPage.open().login("invalid_user", "wrong_password");

//      expect
        Assert.assertTrue(loginPage.isErrorMessageVisible());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match any user"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Field validation")
    public void testShowErrorWhenUsernameIsEmpty() {
//      when
        loginPage.open().login("", UserCredentials.STANDARD_USER.getPassword());

//      expect
        Assert.assertTrue(loginPage.isErrorMessageVisible());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Field validation")
    public void testShowErrorWhenPasswordIsEmpty() {
//      when
        loginPage.open().login(UserCredentials.STANDARD_USER.getUsername(), "");

//      expect
        Assert.assertTrue(loginPage.isErrorMessageVisible());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Field validation")
    public void testShowErrorWhenBothFieldsAreEmpty() {
//      when
        loginPage.open().login("", "");

//      expect
        Assert.assertTrue(loginPage.isErrorMessageVisible());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }
}