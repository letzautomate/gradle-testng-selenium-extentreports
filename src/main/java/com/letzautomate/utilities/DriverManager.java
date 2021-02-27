package com.letzautomate.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

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
        }else if(browserToLaunch.equalsIgnoreCase("dockerChrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
            URL url = null;
            try {
                url = new URL("http://localhost:4444/wd/hub");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver = new RemoteWebDriver(url, desiredCapabilities);
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
