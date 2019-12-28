package com.letzautomate.pages.application;

import com.letzautomate.pages.generic.BasePage;
import org.openqa.selenium.By;

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
        }

    }
}

