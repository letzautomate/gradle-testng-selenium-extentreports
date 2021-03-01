package com.letzautomate.tests;

import com.letzautomate.pages.CommonLogin;
import com.letzautomate.pages.application.Login;
import com.letzautomate.utilities.TestcaseManager;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Test001 extends TestcaseManager {

    @Test(groups={"regression", "TC0001"})
    public void test001() {
        Login login = new Login();
        CommonLogin commonLogin = new CommonLogin();

        setTestcaseName("Login Functionality");

        login.login();
        commonLogin.login();
        Actions actions;

    }

}
