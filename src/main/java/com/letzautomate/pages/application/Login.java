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
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(3);
            enterTextUsingAutoIt("Save As", "Edit1", "Hello");
            report("PASS", "Login Successful");
        }catch(Exception e){
            report("PASS", "Login UnSuccessful");
            e.printStackTrace();
        }

    }
}

