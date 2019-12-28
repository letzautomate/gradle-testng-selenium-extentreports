package com.letzautomate.utilities;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ExtentManager {
    private static final Logger LOGGER = Logger.getLogger(ExtentManager.class);
    private static ExtentReports extentReports;
    private static ExtentHtmlReporter extentHtmlReporter;
    private static String extentPropertiesPath = "src\\test\\resources\\extent.properties";

    public static ExtentReports getExtentInstance() {
        if(extentReports == null){
            try{
                extentReports = createExtentInstance();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return extentReports;

    }

    public static ExtentReports createExtentInstance() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(extentPropertiesPath);
            properties.load(inputStream);
            String reportPath = properties.getProperty("reportPath");
            String reportTitle = properties.getProperty("reportTitle");
            String reportHeading = properties.getProperty("reportHeading");

            extentHtmlReporter = new ExtentHtmlReporter(reportPath);
            extentReports = new ExtentReports();
            File file = new File(System.getProperty("user.dir")+"\\build\\extent-reports");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            extentReports.attachReporter(extentHtmlReporter);

            extentHtmlReporter.setAnalysisStrategy(AnalysisStrategy.CLASS);
            extentHtmlReporter.config().setDocumentTitle(reportTitle);
            extentHtmlReporter.config().setReportName(reportHeading);
            extentHtmlReporter.config().setTheme(Theme.DARK);
            extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
            extentHtmlReporter.config().setChartVisibilityOnOpen(true);
            extentHtmlReporter.config().setEncoding("UTF-");
            extentHtmlReporter.config().setProtocol(Protocol.HTTPS);
            //extentHtmlReporter.config().setCSS();


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(inputStream!=null){
                try{
                    inputStream.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return extentReports;
    }

    public String takeScreenshot(WebDriver driver, String screenshotName) {
        String destination = null;
        String imgPath = null;
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSSS").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try{
            imgPath = "TestsScreenshots\\" + screenshotName + dateName + ".png";
            destination = System.getProperty("user.dir") + "\\build\\extent-reports\\" + imgPath;
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            LOGGER.info("Screenshot destination :: " + destination);
            return imgPath;
        }catch(Exception e){
            LOGGER.error("Take screenshot exception :: " + e.getMessage());

        }
        LOGGER.info("Destination after Exception " + destination);
        return imgPath;
    }

}
