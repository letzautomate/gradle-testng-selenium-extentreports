package com.letzautomate.tests;

import com.letzautomate.pages.application.Login;
import com.letzautomate.utilities.TestcaseManager;
import org.testng.annotations.Test;

public class Test002 extends TestcaseManager {

    @Test(groups={"regression", "TC002"})
    public void test002() {
        Login login = new Login();

        setTestcaseName("Login Functionality second time");

        login.login();
    }

}
