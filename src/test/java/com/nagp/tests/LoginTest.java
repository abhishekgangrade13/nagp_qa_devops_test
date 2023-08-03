/**
 * This class contains tests related to the login functionality of the website.
 *
 * @author: Abhishek
 * @version: 1.0
 */

package com.nagp.tests;

import com.nagp.utils.ExcelReader;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class LoginTest extends BaseTest {
    final static Logger log = Logger.getLogger(LoginTest.class);

    @Test(groups = {"Smoke", "Regression"}, description = "Verify all the section links are available on Home page")
    public void verifyHomePageMenuItems() {
        driver.get(testURL);
        List<String> expectedMenuItems = Arrays.asList("What's New", "Women", "Men", "Gear", "Training", "Sale");
        List<String> actualMenuItems = homePage.getMenuItems();
        Assert.assertEquals(actualMenuItems, expectedMenuItems, "Home Page doesn't contain expected menu items");
    }

    // @Test(groups = {"Smoke", "Regression"}, description = "Verify screenshot is captured in extent report for failure scenario", priority = 4)
    // public void verifyScreenshotIsCapturedForFailureScenario() {
    //     driver.get(testURL);
    //     List<String> expectedMenuItems = Arrays.asList("What's", "Women", "Men", "Gear", "Training", "Sale");
    //     log.info("Changed the actual menu item in order to fail the test case");
    //     List<String> actualMenuItems = homePage.getMenuItems();
    //     Assert.assertEquals(actualMenuItems, expectedMenuItems, "Home Page doesn't contain expected menu items");
    // }

    @Test(groups = {"Smoke", "Regression"}, dataProvider = "credentials", description = "Verify login functionality for Valid User", priority = 2)
    public void testLoginFunctionalityForValidUser(String email, String pass) {
        goToUrl(testURL);
        loginPage.navigateToLoginPage();
        log.info("navigated to login page");
        loginPage.setEmail(email);
        loginPage.setPassword(pass);
        log.info("entered email and password");
        loginPage.clickLogin();
        String welcomeText = homePage.getWelcomeText();
        Assert.assertTrue(welcomeText.contains(fileUtils.getProperty("userName")), "Login is not successful");
    }

    @Test(groups = "Regression", dataProvider = "invalidCredentials", description = "Verify login functionality for invalid user", priority = 3)
    public void testLoginFunctionalityForInvalidUser(String email, String pass) {
        goToUrl(testURL);
        loginPage.navigateToLoginPage();
        log.info("navigated to login page");
        loginPage.setEmail(email);
        loginPage.setPassword(pass);
        log.info("entered email and password");
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.isErrorDisplayed(), true, "Error message not displayed for invalid login");
    }

    @Test(groups = "Regression", description = "Verify create Account functionality", dataProvider = "createAccountAttributes", priority = 1)
    public void testCreateAccountFunctionality(String firstName, String lastName, String password) throws InterruptedException {
        String email = firstName + RandomStringUtils.randomAlphabetic(4) + "@gmail.com";
        goToUrl(testURL);
        homePage.clickCreateAccountLink();
        createAccountPage.enterNewUserDetails(firstName, lastName, email, password);
        log.info("Successfully entered all the user details");
        createAccountPage.clickCreateAccountButton();
        log.info("Clicked on Create Account button");
        String welcomeText = homePage.getWelcomeText();
        Assert.assertTrue(welcomeText.contains(fileUtils.getProperty("userName")), "Login is not successful");
        log.info("New Account successfully created");
    }

    @DataProvider(name = "credentials")
    public Object[][] provideCredentials() {
        return new Object[][]{
                {fileUtils.getProperty("userId1"), fileUtils.getProperty("password")}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] provideInvalidCredentials() throws Exception {
        return new Object[][]{
                {excel.getCellData("invalidUsername", 1), excel.getCellData("invalidPassword", 1)}
        };
    }

    @DataProvider(name = "createAccountAttributes")
    public Object[][] getAttribute() throws Exception {
        return new Object[][]{
                {excel.getCellData("FirstName", 2), excel.getCellData("LastName", 2), fileUtils.getProperty("password")}
        };
    }


}
