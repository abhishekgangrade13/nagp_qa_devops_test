package com.nagp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    String formField = "input[name*='{attribute}']";

    @FindBy(css = "select[name='region_id']")
    private WebElement state;

    @FindBy(css = "select[name='country_id']")
    private WebElement country;

    @FindBy(xpath = "//*[text()='Next']")
    private WebElement nextButton;

    @FindBy(xpath = "//*[text()='Place Order']")
    private WebElement placeOrder;

    @FindBy(css = ".page-title span")
    private WebElement orderSuccessfulText;

    @FindBy(css = ".order-number strong")
    private WebElement orderNumber;
    @FindBy(css = ".new-address-popup")
    private WebElement newAddressPopup;

    String shippingMethod = "[value*='{method}']";

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CheckoutPage setValueInFields(String attributeName, String value) {
        WebElement e = driver.findElement(By.cssSelector(formField.replace("{attribute}", attributeName.toLowerCase())));
        e.sendKeys(value);
        return this;
    }

    public void selectShippingMethod(String method) {
        String updatedMethod = method.split(" ")[0].toLowerCase();
        WebElement e = driver.findElement(By.cssSelector(shippingMethod.replace("{method}", updatedMethod)));
        e.click();
    }

    public CheckoutPage selectState(String stateName) {
        waitForVisibility(state);
        Select stateDrp = new Select(state);
        stateDrp.selectByVisibleText(stateName);
        return this;
    }

    public CheckoutPage selectCountry(String countryName) {
        waitForVisibility(country);
        Select countryDrp = new Select(country);
        countryDrp.selectByVisibleText(countryName);
        return this;
    }

    public void clickNextButton() {
        waitForVisibility(nextButton);
        nextButton.click();
        try {
            waitForLoadingIconToDisappear();
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("No loading icon appeared");
        }

    }

    public void clickPlaceOrder() {
        waitForElementToBeClickable(placeOrder);
        placeOrder.click();
        try {
            waitForLoadingIconToDisappear();
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("No loading icon appeared");
        }
    }

    public String getTextOnOrderCompletion() {
        waitForVisibility(orderSuccessfulText);
        return orderSuccessfulText.getText();
    }

    public boolean isNewAddressPopupVisible() {
        boolean isnewAddVisible = false;
        try {
            waitForVisibility(newAddressPopup);
            isnewAddVisible = newAddressPopup.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("Entering address for the first time");
        }
        return isnewAddVisible;
    }

    public String getOrderNumber() {
        waitForVisibility(orderNumber);
        return orderNumber.getText();
    }

}
