package com.nagp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {


    @FindBy(css = "li.level0 .level-top span:not(.ui-icon)")
    private List<WebElement> sectionList;

    @FindBy(css = ".logged-in")
    private WebElement welcomeText;

    @FindBy(xpath = "//*[text()='Default welcome msg!']")
    private WebElement defaultMsg;

    @FindBy(linkText = "Sign In")
    private WebElement signInButton;

    @FindBy(linkText = "Create an Account")
    private WebElement createAccountLink;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> getMenuItems() {
        waitForVisibilityOfList(sectionList);
        List<String> menuItems = new ArrayList<>();
        for (WebElement e : sectionList) {
            menuItems.add(e.getText());
        }
        return menuItems;
    }

    public String getWelcomeText() {
        wait.until(ExpectedConditions.invisibilityOf(defaultMsg));
        return welcomeText.getText();
    }

    public void clickSignInButton() {
        waitForVisibility(signInButton);
        signInButton.click();
    }

    public void clickCreateAccountLink() {
        waitForVisibility(createAccountLink);
        createAccountLink.click();
    }

    public void navigateToSection(String sectionName) {
        waitForVisibilityOfList(sectionList);
        sectionList.stream().filter(e -> e.getText().equals(sectionName)).limit(1).forEach(WebElement::click);
    }

}
