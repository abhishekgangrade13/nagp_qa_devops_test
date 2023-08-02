package com.nagp.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    WebDriver driver;
    final static Logger log = Logger.getLogger(LoginPage.class);
    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "pass")
    WebElement passwordField;

    @FindBy(id = "send2")
    WebElement loginButton;

    @FindBy(css = ".message-error")
    WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage navigateToLoginPage() {
        driver.findElement(By.linkText("Sign In")).click();
        return new LoginPage(driver);
    }

    public void setEmail(String email) {
        waitForVisibility(emailField);
        emailField.sendKeys(email);
    }

    public void setPassword(String password) {
        waitForVisibility(passwordField);
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        waitForVisibility(loginButton);
        loginButton.click();
    }

    public String getErrorMessage() {
        waitForVisibility(errorMessage);
        return errorMessage.getText();
    }

    public boolean isErrorDisplayed() {
        boolean isError = false;
        try {
            isError = errorMessage.isDisplayed();
            log.info("error displayed as expected");
        } catch (NoSuchElementException | TimeoutException e) {
            e.getStackTrace();
            log.error("error not found");
        }
        return isError;
    }

}
