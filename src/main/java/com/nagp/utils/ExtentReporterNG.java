package com.nagp.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ExtentReporterNG {

    public static Logger log = LogManager.getLogger(ExtentReporterNG.class);

    private ExtentReporterNG() {
        log.info("This class contains ExtentReporterNG values.");
    }

    final static FileUtils fileUtils = new FileUtils();
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
