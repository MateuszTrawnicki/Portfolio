package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HomePage;

import java.io.IOException;

public class RegisterTest extends BaseTest {

    @Test
    public void registerUserTest() throws IOException {
        int random = (int) (Math.random() * 1000);

        ExtentTest test = extentReports.createTest("Register new user");
        test.log(Status.PASS,"Starting test - Register new user");

        WebElement myAccountText = new HomePage(driver, test)
                .openMyAccountPage()
                .registerNewUser("Mateusz" + random + "@test.pl", "StrongPassword123!@#")
                .getDashboardText();

        Assert.assertEquals(myAccountText.getText(),"Dashboard");
        test.log(Status.PASS,"Test ended - Assertions done");
    }

    @Test
    public void registerUserWithSameEmailTest() throws IOException {
        ExtentTest test = extentReports.createTest("Register user with same email");
        test.log(Status.PASS,"Starting test - Register user with same email");

        WebElement error = new HomePage(driver, test)
                .openMyAccountPage()
                .registerUserWithSameEmail("Mateusz169@test.pl", "StrongPassword123!@#")
                .getErrors();

        Assert.assertTrue(error.getText().contains("Error: An account is already registered with yo"));
        test.log(Status.PASS,"Test ended - Assertions done");
    }
}
