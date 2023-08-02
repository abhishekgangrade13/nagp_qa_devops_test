package com.nagp.tests.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.nagp.tests.BaseTest;
import com.nagp.utils.ExtentReporterNG;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TestListener implements ITestListener{

    ExtentReports extent = ExtentReporterNG.extentReportGenerator();
    ExtentTest test;


    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        System.out.println("Test method " + result.getMethod().getMethodName() + " started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test is successfully passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        WebDriver driver = ((BaseTest) result.getInstance()).driver;
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" + result.getMethod().getMethodName() + ".png";
        try {
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
            System.out.println("Screenshot taken at " + screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test method " + result.getMethod().getMethodName() + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test suite " + context.getName() + " started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test suite " + context.getName() + " finished");
        extent.flush();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());
        String currentTestResultsPath = System.getProperty("user.dir") + "/Reports/Current test results/" + dateTime;
        String reportsPath = System.getProperty("user.dir") + "/Reports/ExtentReports/";


        File currentTestResultsFolder = new File(currentTestResultsPath);
        File reportPath = new File(reportsPath);

        try {
            org.apache.commons.io.FileUtils.copyDirectory(reportPath, currentTestResultsFolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




