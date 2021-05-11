package com.letzautomate.tests;

import com.letzautomate.pages.CommonLogin;
import com.letzautomate.pages.application.LoginPage;
import com.letzautomate.utilities.TestcaseManager;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Test001 extends TestcaseManager {

    @Test(groups={"regression", "TC0001"})
    public void test001() {
        LoginPage login = new LoginPage();
        CommonLogin commonLogin = new CommonLogin();

        setTestcaseName("Login Functionality");

        login.enterUserName("admin").enterPassword("admin").clickLogin();

    }

}
