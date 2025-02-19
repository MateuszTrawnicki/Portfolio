package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HomePage;

import java.io.IOException;

public class SendMessageByContactUsTest extends BaseTest {

    @Test
    public void sendMessageTest() throws IOException {
        ExtentTest test = extentReports.createTest("Send Message by contact us");
        test.log(Status.PASS,"Starting test - send message");

        WebElement messageInfo = new HomePage(driver, test)
                .sendMessageByContactUs("Mateusz", "mateusz.testowy@testowy.pl","Some random message")
                .messageSendInfo();

        Assert.assertTrue(messageInfo.getText().contains("It is demo page! We are not sending emails!"));
        test.log(Status.PASS,"Test ended - assertions done");
    }
}
