package com.nagp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage {
    @FindBy(css = "span[itemprop='name']")
    private WebElement productName;

    @FindBy(css = ".product-info-price span.price")
    private WebElement productPrice;

    @FindBy(xpath = "//button[@title='Add to Cart']")
    WebElement addToCartButton;

    @FindBy(css = ".categories-menu .title")
    List<WebElement> categories;

    @FindBy(css = ".showcart")
    WebElement myCart;

    @FindBy(css = "[title='Proceed to Checkout']")
    WebElement checkoutButton;

    @FindBy(css = ".minicart-items-wrapper .product-item-name a")
    List<WebElement> cartProducts;

    @FindBy(css = "[title='Remove item']")
    private WebElement removeItemButton;

    @FindBy(css = ".action-accept")
    private WebElement confirmButton;

    @FindBy(css = ".subtitle.empty")
    private WebElement emptyBasketText;

    @FindBy(css = ".counter-number")
    private WebElement productQty;

    @FindBy(css = "[id=search]")
    private WebElement searchBox;

    @FindBy(css = "block-minicart")
    private WebElement miniCart;

    private String subCategoryStr = "{subCategory}";

    private String productLink = "{productLink}";

    private String size = "[aria-label='{size}']";

    private String color = "[aria-label='{color}']";

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProductPage clickAddToCart() {
        waitForVisibility(addToCartButton);
        addToCartButton.click();
        return this;
    }


    public void selectCategory(String subCategory) {
        WebElement e = driver.findElement(By.linkText(subCategoryStr.replace("{subCategory}", subCategory)));
        e.click();
    }

    public void selectItem(String product) {
        WebElement e = driver.findElement(By.linkText(productLink.replace("{productLink}", product)));
        e.click();
    }

    public void selectSize(String sizeValue) {
        WebElement e = driver.findElement(By.cssSelector(size.replace("{size}", sizeValue)));
        e.click();
    }

    public void selectColor(String colorValue) {
        WebElement e = driver.findElement(By.cssSelector(color.replace("{color}", colorValue)));
        e.click();
    }


    public void navigateToCart() {
        waitForLoadingIconToDisappear();
        waitForVisibility(myCart);
        setWait(2000);
        myCart.click();
    }

    public void clickCheckout() {
        waitForVisibility(checkoutButton);
        checkoutButton.click();
        try {
            waitForLoadingIconToDisappear();
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("No loading icon appeared");
        }

    }

    public List<String> getCartItems() {
        List<String> cartList = new ArrayList<>();
        try {
            waitForVisibilityOfList(cartProducts);
            for (WebElement e : cartProducts) {
                cartList.add(e.getText());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("No items present in the cart");
            e.printStackTrace();
        }
        return cartList;
    }

    public void removeItemFromCart() {
        try {
            if (!miniCart.isDisplayed()) {
                myCart.click();
            }
        } catch (TimeoutException | NoSuchElementException e) {

        }
        waitForVisibility(removeItemButton);
        removeItemButton.click();
        waitForVisibility(confirmButton);
        setWait(2000);
        confirmButton.click();
    }

    public String getProductQuantityFromCart() {
        waitForVisibility(productQty);
        return productQty.getText();
    }

    public boolean isBasketEmpty() {
        boolean isEmpty = false;
        try {
            waitForVisibility(emptyBasketText);
            isEmpty = true;
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("Item still present in the cart");
        }
        return isEmpty;
    }

    public void enterProductNameInSearchBox(String product) {
        waitForVisibility(searchBox);
        searchBox.sendKeys(product, Keys.ENTER);
    }

}

