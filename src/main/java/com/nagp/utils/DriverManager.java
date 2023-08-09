package com.nagp.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverManager {

    public static Logger log = LogManager.getLogger(DriverManager.class);

    private DriverManager() {
        log.info("This class contains DriverManager values.");
    }

    private static final String OPT_REMOTE = "--remote-allow-origins=*";
    private static final String OPT_HEADLESS = "--headless";

    public static WebDriver getDriver(String browser) {
        WebDriver driver = null;

        switch (browser.toLowerCase()) {
            case "chrome":
                //To establish websocket connection
                ChromeOptions options = new ChromeOptions();
                options.addArguments(OPT_REMOTE);
                options.addArguments(OPT_HEADLESS);
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                FirefoxOptions options1 = new FirefoxOptions();
                options1.addArguments(OPT_REMOTE);
                options1.addArguments(OPT_HEADLESS);
                driver = new FirefoxDriver(options1);
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "edge":
                EdgeOptions options3 = new EdgeOptions();
                options3.addArguments(OPT_REMOTE);
                options3.addArguments(OPT_HEADLESS);
                driver = new EdgeDriver();
                break;
            default:
               log.error("Invalid browser");
                System.exit(0);
        }
        return driver;
    }
}
