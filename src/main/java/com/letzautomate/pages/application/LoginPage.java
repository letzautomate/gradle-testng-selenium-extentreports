package com.letzautomate.pages.application;

import com.letzautomate.pages.generic.BasePage;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoginPage extends BasePage {

    private By txtUserName = By.name("j_username");
    private By txtPassword = By.name("j_password");
    private By btnLogin = By.name("Submit");


    public LoginPage enterUserName(String userName){
        launchApp();
        enterText(txtUserName, userName);
        return new LoginPage();
    }

    public LoginPage enterPassword(String password){
        enterText(txtUserName, password);
        return new LoginPage();
    }

    public LoginPage clickLogin(){
        buttonClick(btnLogin);
        return new LoginPage();
    }

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

