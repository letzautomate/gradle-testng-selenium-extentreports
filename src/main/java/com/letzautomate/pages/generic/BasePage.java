package com.letzautomate.pages.generic;

//import autoitx4java.AutoItX;
import autoitx4java.AutoItX;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.jacob.com.LibraryLoader;
import com.letzautomate.utilities.ExtentLink;
import com.letzautomate.utilities.ExtentManager;
import com.letzautomate.utilities.InstancesManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import static com.letzautomate.common.CommonConstants.*;
//import autoitx4java.AutoItX;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.security.SecureRandom;

public class BasePage {

    private static final Logger LOGGER = Logger.getLogger(BasePage.class);
    ExtentTest extentTest;
    ExtentLink extentLink;
    ExtentManager extentManager = new ExtentManager();
    AutoItX autoItX;

    public WebDriver getDriver() {
        return InstancesManager.driverMap.get(Thread.currentThread().getId());
    }

    public WebElement waitForElement(By locator){
        WebElement element = null;
        element = getDriver().findElement(locator);
        return element;
    }

    public void launchApp(){
        getDriver().get("https://google.com");
    }

    public void buttonClick(By locator) {
        WebElement element = waitForElement(locator);
        LOGGER.info("Before Clicking the Button");
        element.click();
        LOGGER.info("After Clicking the Button");
    }

    public void enterText(By locator, String textToEnter){
        WebElement element = getDriver().findElement(locator);
        LOGGER.info("Before Enter Text");
        element.sendKeys(textToEnter);
        LOGGER.info("After Enter Text");
        System.out.println("test");
    }

    public void clearAndEnterText(By locator, String textToEnter){
        WebElement element = getDriver().findElement(locator);
        LOGGER.info("Before Enter Text");
        element.clear();
        element.sendKeys(textToEnter);
        LOGGER.info("After Enter Text");
    }


   public ExtentTest getExtentTest() {
        Long threadID = Thread.currentThread().getId();
        return InstancesManager.testcaseMap.get(threadID);
   }
    public void report(String status, String message){

        SoftAssert softAssert = new SoftAssert();
        extentTest = getExtentTest();

        try{
            if(status.equalsIgnoreCase("info")){
                String screenshotPath = extentManager.takeScreenshot(getDriver(), "INFO_" + randomString(5) + "_");
                extentTest.log(Status.INFO, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("pass")){
                String screenshotPath = extentManager.takeScreenshot(getDriver(), "PASS_" + randomString(5) + "_");
                extentTest.log(Status.PASS, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("fail")){
                String screenshotPath = extentManager.takeScreenshot(getDriver(), "FAIL_" + randomString(5) + "_");
                extentTest.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else if(status.equalsIgnoreCase("skip")){
                String screenshotPath = extentManager.takeScreenshot(getDriver(), "SKIP_" + randomString(5) + "_");
                extentTest.log(Status.SKIP, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }else {
                String screenshotPath = extentManager.takeScreenshot(getDriver(), "FAIL_" + randomString(5) + "_");
                extentTest.log(Status.FAIL, message,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                softAssert.fail(message, new Throwable());
            }
        }catch(Exception e){
            LOGGER.error("Report Method Exception :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reportLink(String status, String linkText, String linkUrl) {
        extentTest = getExtentTest();
        extentLink = new ExtentLink();
        if(linkUrl.contains("extent-reports")){
            linkUrl = linkUrl.split("extent-reports\\\\")[1];
        }
        extentLink.setLink(linkText, linkUrl);

        if (status.equalsIgnoreCase("info")){
            extentTest.log(Status.INFO, extentLink);
        }else if(status.equalsIgnoreCase("error")){
            extentTest.log(Status.ERROR, extentLink);
            Assert.assertTrue(false, linkText);
        }else {
            extentTest.log(Status.DEBUG, extentLink);
        }


    }

    public String randomString(int length){
        LOGGER.info("Before Random String creation");
        String str = "012345679ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i< length; i++ ){
            sb.append(str.charAt(rnd.nextInt(str.length())));
        }
        LOGGER.info("After Random String creation :: " + sb.toString());
        return sb.toString();
    }

    public void enterTextUsingAutoIt(String winName, String controlID, String textToEnter) {
        autoItX.controlSend(winName, controlID, "", textToEnter);
    }

    private AutoItX getAutoItX(){
        String jacobDLLVersion = JACOBDLL_64;
        if(System.getProperty(MACHINE_BIT).contains(BIT32)){
            jacobDLLVersion = JACOBDLL_32;
        }
        File jacobFile = new File(AUTOIT_LIB_DIR, jacobDLLVersion);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobFile.getAbsolutePath());
        LibraryLoader.loadJacobLibrary();
        return new AutoItX();
    }


}



