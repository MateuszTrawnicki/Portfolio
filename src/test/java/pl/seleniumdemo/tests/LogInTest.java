package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HomePage;

import java.io.IOException;

public class LogInTest extends BaseTest {

    @Test
    public void logInValidDataTest() throws IOException {

        ExtentTest test = extentReports.createTest("Log in with valid data");
        test.log(Status.PASS,"Starting test - Log in valid data");

        WebElement dashboardLink = new HomePage(driver, test)
                .openMyAccountPage()
                .setUserValidData("Mateusz827@test.pl","StrongPassword123!@#")
                .logInPerform()
                .getDashboardText();

        Assert.assertEquals(dashboardLink.getText(),"Dashboard");
        test.log(Status.PASS,"Test ended - assertions done");
    }

    @Test
    public void logInInvalidDataTest() throws IOException {
        ExtentTest test = extentReports.createTest("Log in with invalid data");
        test.log(Status.PASS,"Starting test - Log in invalid data");

        WebElement error = new HomePage(driver, test)
                .openMyAccountPage()
                .loginUserInValidData("Mateusz@test.pl","InvalidPassword")
                .getErrors();

        Assert.assertTrue(error.getText().contains("Incorrect username"));
        test.log(Status.PASS,"Test ended - assertions done");
    }
}
