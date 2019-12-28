package com.letzautomate.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

public class TestcaseManager extends DriverManager{

    ExtentManager extentManager = new ExtentManager();
    ExtentReports extentReports = extentManager.getExtentInstance();

    public final static Logger LOGGER = Logger.getLogger(TestcaseManager.class);

    public void setTestcaseName(String testcaseName) {
        Long threadID = Thread.currentThread().getId();
        ExtentTest extentTest = extentReports.createTest(testcaseName);
        InstancesManager.testcaseMap.put(threadID, extentTest);
        LOGGER.info("---Execution is started for testcase " + testcaseName + " is started----");
    }
    public ExtentTest getExtentTestcase() {
        return InstancesManager.testcaseMap.get(Thread.currentThread().getId());
    }

    @AfterMethod(alwaysRun=true)
    public void cleanUp(ITestResult iTestResult) {
        LOGGER.info("cleanup activity");
        ExtentTest extentTest = getExtentTestcase();
        String status = null;
        try{
            if(iTestResult.getStatus() == ITestResult.FAILURE) {
                status = "Failed";
                String screenshotPath = extentManager.takeScreenshot(getDriver(), iTestResult.getName());
                extentTest.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getName() + "Testcase is failed due to below Issues :: " , ExtentColor.RED));
                extentTest.fail(iTestResult.getThrowable());
                extentTest.log(Status.FAIL, "Snapshot when exception occurred :: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(iTestResult.getStatus() == ITestResult.SUCCESS){
                status  = "Passed";
                extentTest.pass("Passed");

            }else if(iTestResult.getStatus() == ITestResult.SKIP){
                status  = "Skipped";
                extentTest.pass("Skipped");
            } else {
                status = String.valueOf(iTestResult.getStatus());
                extentTest.error(iTestResult.getThrowable());
            }
        }catch(Exception e) {
            status = "Failed";
            extentTest.log(Status.FAIL, "Exception in cleanup :: " + e.getMessage());

        }finally {
            quitDriver();
        }
        LOGGER.info("The execution is completed for " + iTestResult.getName() + " and the status is " + status);
    }

    @AfterSuite(alwaysRun=true)
    public void clearExtentTest() {
        try{
            extentReports.flush();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }



}
