package pl.seleniumdemo.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HomePage;

public class RegisterTest extends BaseTest {

    @Test
    public void registerUserTest() {
        int random = (int) (Math.random() * 1000);

        WebElement myAccountText = new HomePage(driver)
                .openMyAccountPage()
                .registerNewUser("Mateusz" + random + "@test.pl", "StrongPassword123!@#")
                .getDashboardText();

        Assert.assertEquals(myAccountText.getText(),"Dashboard");

    }

    @Test
    public void registerUserWithSameEmailTest() {
        WebElement error = new HomePage(driver)
                .openMyAccountPage()
                .registerUserWithSameEmail("Mateusz@test.pl", "StrongPassword123!@#")
                .getErrors();

        Assert.assertTrue(error.getText().contains("Error: An account is already registered with yo"));

    }
}
