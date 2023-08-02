package com.nagp.pages;

import com.nagp.utils.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    final static Logger log = LogManager.getLogger(BasePage.class);
    public WebDriver driver;
    public WebDriverWait wait;
    FileUtils fileUtil=new FileUtils();

    @FindBy(css = "[alt='Loading...']")
    private WebElement loadingIcon;

    @FindBy(linkText = "Sign Out")
    private WebElement signOut;

    @FindBy(linkText = "My Account")
    private WebElement myAccount;

    @FindBy(css = "[data-action='customer-menu-toggle']")
    private WebElement dropDownButton;

    @FindBy(linkText = "Sign In")
    private WebElement signInButton;

    //Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(fileUtil.getProperty("globalWait"))));
        PageFactory.initElements(driver, this);
    }

    public void waitForVisibility(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForVisibilityOfList(List<WebElement> listOfElements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfElements));
    }

    public void waitForVisibility(WebElement webElement, int time) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForInvisibility(WebElement webElement) {
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void waitForLoadingIconToDisappear() {
        waitForInvisibility(loadingIcon);
    }

    public static void setWait(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickLogout() {
        signOut.click();
        setWait(7000);
    }

    public void clickDropdown() {
        dropDownButton.click();
    }

    public void goToMyAccount() {
        dropDownButton.click();
        myAccount.click();
    }

    public boolean isSignInButtonDisplayed() {
        waitForVisibility(signInButton);
        return signInButton.isDisplayed();
    }

    public boolean isSignOutButtonDisplayed() {
        waitForVisibility(signOut);
        return signOut.isDisplayed();
    }

    public boolean isDropdownButtonDisplayed() {
        waitForVisibility(dropDownButton, 2);
        return dropDownButton.isDisplayed();
    }

}
