package com.letzautomate.utilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.ConcurrentHashMap;

public class InstancesManager {

    public static ConcurrentHashMap<Long, WebDriver> driverMap = new ConcurrentHashMap<Long, WebDriver>();
    public static ConcurrentHashMap<Long, ExtentTest> testcaseMap = new ConcurrentHashMap<Long, ExtentTest>();


}

