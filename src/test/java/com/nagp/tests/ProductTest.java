package com.nagp.tests;

import java.util.List;

import com.nagp.utils.ExcelReader;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.testng.Assert;

import java.util.NoSuchElementException;

import static com.nagp.pages.BasePage.setWait;
import static com.nagp.utils.Constants.*;

public class ProductTest extends BaseTest {

    @Test(groups = "Regression", dataProvider = "productDetailsForNavigation", description = "Add to cart functionality using Navigation flow", priority = 2)
    public void addToCartUsingNavigationMenu(String sectionName, String category, String product, String size, String color) {
        login();
        log.info("Login successful");
        productPage.navigateToCart();
        try {
            while (!productPage.getProductQuantityFromCart().equals("0")) {
                productPage.removeItemFromCart();
                if (productPage.isBasketEmpty()) {
                    break;
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("Cart is empty. Please add products");
        }
        homePage.navigateToSection(sectionName);
        log.info("Navigated to " + sectionName + " successfully");
        productPage.selectCategory(category);
        log.info("Selected " + category);
        productPage.selectItem(product);
        log.info("Selected " + product);
        if (!size.equals(null))
            productPage.selectSize(size.toUpperCase());
        if (!color.equals(null))
            productPage.selectColor(color);
        productPage.clickAddToCart();
        log.info("Product added to cart");
        productPage.navigateToCart();
        List<String> cartItems = productPage.getCartItems();
        Assert.assertTrue(cartItems.contains(product), "Item not added in the cart");
    }

    @Test(groups = {"Smoke", "Regression"}, dataProvider = "productDetailsForSearch", description = "Add to cart functionality using Search functionality", priority = 3)
    public void addToCartUsingSearchFunctionality(String product, String size, String color) {
        login();
        log.info("Login successful");
        productPage.enterProductNameInSearchBox(product);
        productPage.selectItem(product);
        log.info("Selected " + product);
        if (!size.isEmpty())
            productPage.selectSize(size.toUpperCase());
        if (!color.isEmpty())
            productPage.selectColor(color);
        productPage.clickAddToCart();
        log.info("Product added to cart");
        productPage.navigateToCart();
        List<String> cartItems = productPage.getCartItems();
        Assert.assertTrue(cartItems.contains(product), "Item not added in the cart");
    }

    @Test(groups = {"Smoke", "Regression"}, dataProvider = "productDetailsForSearch", description = "Remove product from cart functionality", priority = 4)
    public void removeProductFromCart(String product, String size, String color) {
        login();
        productPage.navigateToCart();
        boolean isCartEmpty = false;
        while (!productPage.getProductQuantityFromCart().equals("0")) {
            productPage.removeItemFromCart();
            if (productPage.isBasketEmpty()) {
                isCartEmpty = true;
                break;
            }
        }
        Assert.assertTrue(isCartEmpty, "Products not removed from cart successfully");
    }

    @Test(groups = "Regression", dataProvider = "checkoutAttributes", description = "Checkout Functionality", priority = 1)
    public void verifyCheckoutFunctionality(String product, String size, String color, String company, String street, String city, String state, String postalCode, String country, String phone, String shippingMethod) {
        login();
        log.info("Login successful");
        productPage.enterProductNameInSearchBox(product);
        productPage.selectItem(product);
        log.info("Selected " + product);
        if (!size.isEmpty())
            productPage.selectSize(size.toUpperCase());
        if (!color.isEmpty())
            productPage.selectColor(color);
        productPage.clickAddToCart();
        log.info("Product added to cart");
        productPage.navigateToCart();
        productPage.clickCheckout();
        if (!checkoutPage.isNewAddressPopupVisible()) {
            checkoutPage.setValueInFields(COMPANY, company).
                    setValueInFields(STREET, street).
                    setValueInFields(CITY, city).
                    selectState(state).
                    setValueInFields(POSTAL_CODE, postalCode).
                    selectCountry(country).
                    setValueInFields(TELEPHONE, phone);

            log.info("Entered shipping details");
        }
        checkoutPage.selectShippingMethod(shippingMethod);
        checkoutPage.clickNextButton();
        setWait(5000);
        checkoutPage.clickPlaceOrder();
        Assert.assertTrue(checkoutPage.getTextOnOrderCompletion().contains("Thank you for your purchase"));
        String orderId = checkoutPage.getOrderNumber();
        myAccountPage.goToMyAccount();
        myAccountPage.goToMyOrders();
        List<String> orderList = myAccountPage.getOrdersList();
        Assert.assertTrue(orderList.contains(orderId), "Order not found in the orders list");
        log.info("Order placed successfully");
    }


    @DataProvider(name = "productDetailsForNavigation")
    public Object[][] getProductDetailsForNavigation() throws Exception {
        return new Object[][]{
                {excel.getCellData("SectionName", 3), excel.getCellData("Category", 3),
                        excel.getCellData("ProductName", 3), excel.getCellData("Size", 3), excel.getCellData("Color", 3)}

        };
    }

    @DataProvider(name = "productDetailsForSearch")
    public Object[][] getProductDetailsForSearch() throws Exception {
        return new Object[][]{
                {excel.getCellData("ProductName", 4), excel.getCellData("Size", 4), excel.getCellData("Color", 4)}
        };
    }

    @DataProvider(name = "checkoutAttributes")
    public Object[][] getCheckoutAttribute() throws Exception {
        return new Object[][]{
                {excel.getCellData("ProductName", 5), excel.getCellData("Size", 5), excel.getCellData("Color", 5),
                        excel.getCellData("Company", 5), excel.getCellData("Street", 5), excel.getCellData("City", 5),
                        excel.getCellData("State", 5), excel.getCellData("Postcode", 5), excel.getCellData("Country", 5),
                        excel.getCellData("PhoneNo", 5), excel.getCellData("ShippingMethod", 5)}
        };
    }

}
