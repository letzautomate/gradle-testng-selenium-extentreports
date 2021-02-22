package com.letzautomate.pages.application;

import com.letzautomate.pages.generic.BasePage;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Login extends BasePage {

    private By txtUserName = By.name("q");
    public void login(){
        try{
            launchApp();
            enterText(txtUserName, "Ram");

            report("PASS", "Login Successful");
        }catch(Exception e){
            report("PASS", "Login UnSuccessful");
            e.printStackTrace();
        } }

        public void saveHtmlFile(){
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            enterTextUsingAutoIt("Save As", "Edit1", "C:\\LetsDoIt\\abcd.html");
            clickUsingAutoIt("Save As", "Button2");
        }


}

