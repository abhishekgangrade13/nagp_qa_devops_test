package com.nagp.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    public static FileUtils fileUtils = new FileUtils();
    static ExtentReports extent = new ExtentReports();

    public static ExtentReports extentReportGenerator() {
        String path = System.getProperty("user.dir") + "/Reports/ExtentReports/ExtentResults.html";
        String strUserName = fileUtils.getProperty("userName");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        extent.setSystemInfo("Tester", strUserName);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;
    }
}
