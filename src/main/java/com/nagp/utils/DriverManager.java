package com.nagp.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverManager {

    public static WebDriver getDriver(String browser) {
        WebDriver driver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
                //To establish websocket connection
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                FirefoxOptions options1 = new FirefoxOptions();
                options1.addArguments("--remote-allow-origins=*");
                driver = new FirefoxDriver(options1);
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "edge":
                EdgeOptions options3 = new EdgeOptions();
                options3.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Invalid browser");
                System.exit(0);
        }
        return driver;
    }
}
