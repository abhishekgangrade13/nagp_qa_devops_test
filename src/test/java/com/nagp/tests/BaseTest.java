package com.nagp.tests;


import com.nagp.pages.*;
import com.nagp.tests.resources.TestListener;
import com.nagp.utils.DriverManager;
import com.nagp.utils.ExcelReader;
import com.nagp.utils.ExtentReporterNG;
import com.nagp.utils.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver driver;
    protected String testURL;
    protected BasePage basePage;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected CreateAccountPage createAccountPage;
    protected ProductPage productPage;
    protected CheckoutPage checkoutPage;
    protected MyAccountPage myAccountPage;
    protected DriverManager driverManager;
    public FileUtils fileUtils = new FileUtils();
    final static Logger log = LogManager.getLogger(BaseTest.class);
    public ExcelReader excel = new ExcelReader();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        testURL = fileUtils.getProperty("url");
        driver = driverManager.getDriver(fileUtils.getProperty("browser"));
        driver.manage().timeouts().implicitlyWait(Long.parseLong(fileUtils.getProperty("globalWait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        initActions(driver);
        ExtentReporterNG.extentReportGenerator();
        PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\main\\java\\com\\nagp\\resources\\log4j.properties");
        BasicConfigurator.configure();
        String currentTestResultsPath = System.getProperty("user.dir") + "/Reports/Current test results/";
        String archivedTestResultsPath = System.getProperty("user.dir") + "/Reports/Archived test results/";

        File currentTestResultsFolder = new File(currentTestResultsPath);
        File archivedTestResultsFolder = new File(archivedTestResultsPath);

        try {
            org.apache.commons.io.FileUtils.copyDirectory(currentTestResultsFolder, archivedTestResultsFolder);
            org.apache.commons.io.FileUtils.cleanDirectory(currentTestResultsFolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Set excel file for data driving
        try {
            excel.setExcelFile(System.getProperty("user.dir") + "/src/test/java/com/nagp/tests/resources/TestData/TestData.xlsx", "Sheet1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initActions(WebDriver driver) {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        createAccountPage = new CreateAccountPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        myAccountPage = new MyAccountPage(driver);
    }

    public void login() {
        goToUrl(testURL);
        loginPage.navigateToLoginPage();
        log.info("navigated to login page");
        loginPage.setEmail(fileUtils.getProperty("userId"));
        loginPage.setPassword(fileUtils.getProperty("password"));
        log.info("entered email and password");
        loginPage.clickLogin();

    }

    public void logout() {
        try {
            if (basePage.isDropdownButtonDisplayed()) {
                basePage.clickDropdown();
                basePage.clickLogout();
                log.info("Successfully logged out");
            }
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void signOut() {
        logout();
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        driver.quit();
    }

}
