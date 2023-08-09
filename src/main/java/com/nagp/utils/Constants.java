package com.nagp.utils;

import com.nagp.pages.BasePage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Constants {
    public static Logger log = LogManager.getLogger(Constants.class);

    private Constants() {
        log.info("This class contains constant values.");
    }

    public static final String FIRST_NAME = "First Name";
    public static final String LAST_NAME = "Last Name";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String CONFIRM_PASSWORD = "Confirm Password";
    public static final String COMPANY = "Company";
    public static final String STREET = "Street";
    public static final String CITY = "City";
    public static final String POSTAL_CODE = "Postcode";
    public static final String TELEPHONE = "Telephone";
    public static final String SHIPPING_METHOD = "Shipping Method";

}
