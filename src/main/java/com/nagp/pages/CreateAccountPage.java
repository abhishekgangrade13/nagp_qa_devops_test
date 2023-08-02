package com.nagp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.nagp.utils.Constants.*;
public class CreateAccountPage extends BasePage {

    @FindBy(css = ".submit")
    private WebElement createAccount;


    String formField = "input[title='{attribute}']";

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CreateAccountPage setValueInFields(String attributeName, String value) {
        WebElement e = driver.findElement(By.cssSelector(formField.replace("{attribute}", attributeName)));
        e.sendKeys(value);
        return this;
    }

    public void clickCreateAccountButton() {
        waitForVisibility(createAccount);
        createAccount.click();
    }

    public void enterNewUserDetails(String firstName, String lastName, String email, String password)
    {
        setValueInFields(FIRST_NAME, firstName);
        log.info("Entered First name");
        setValueInFields(LAST_NAME,lastName);
        log.info("Entered Last name");
        setValueInFields(EMAIL, email);
        log.info("Entered Email id");
        setValueInFields(PASSWORD, password);
        setValueInFields(CONFIRM_PASSWORD, password);
    }


}
