package com.letzautomate.tests;

import com.letzautomate.pages.application.Login;
import com.letzautomate.utilities.TestcaseManager;
import org.testng.annotations.Test;

public class Test001 extends TestcaseManager {

    @Test(groups={"regression"})
    public void test001() {
        Login login = new Login();

        setTestcaseName("Login Functionality");

        login.login();
    }

}
