package pl.seleniumdemo.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HomePage;

public class LogInTest extends BaseTest {

    @Test
    public void logInValidDataTest() {
        WebElement dashboardLink = new HomePage(driver)
                .openMyAccountPage()
                .loginUserValidData("Mateusz@test.pl","StrongPassword123!@#")
                .getDashboardText();

        Assert.assertEquals(dashboardLink.getText(),"Dashboard");
    }

    @Test
    public void logInInvalidDataTest() {
        WebElement error = new HomePage(driver)
                .openMyAccountPage()
                .loginUserInValidData("Mateusz@test.pl","InvalidPassword")
                .getErrors();

        Assert.assertTrue(error.getText().contains("Incorrect username"));
    }
}
