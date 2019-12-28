package com.letzautomate.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

public class DriverManager {

    @BeforeMethod(alwaysRun=true)
    public void createDriverInstance() {
        WebDriver driver = null;
        String browserToLaunch = System.getProperty("browserToLaunch");
        if(browserToLaunch.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--start-fullscreen");
            options.addArguments("--disable-extensions");

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(desiredCapabilities);
        }
        Long threadID = Thread.currentThread().getId();
        InstancesManager.driverMap.put(threadID, driver);
    }

    public WebDriver getDriver() {
        return InstancesManager.driverMap.get(Thread.currentThread().getId());
    }

    public void quitDriver() {
        WebDriver driver = getDriver();
        if(driver != null){
            driver.close();
            driver.quit();
        }
    }
}
