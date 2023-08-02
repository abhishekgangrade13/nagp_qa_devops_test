package com.nagp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class MyAccountPage extends BasePage {

    public MyAccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "My Orders")
    private WebElement myOrders;

    @FindBy(css = ".col.id:not([scope='col'])")
    private List<WebElement> ordersList;

    public void goToMyOrders() {
        waitForVisibility(myOrders);
        myOrders.click();
    }

    public List<String> getOrdersList() {
        waitForVisibilityOfList(ordersList);
        List<String> orderList = new ArrayList<>();
        for (WebElement e : ordersList) {
            orderList.add(e.getText());
        }
        return orderList;
    }


}
