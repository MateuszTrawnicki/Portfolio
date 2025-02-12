package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.models.Customer;
import pl.seleniumdemo.pages.HomePage;
import pl.seleniumdemo.pages.OrderDetailsPage;
import pl.seleniumdemo.utils.ScreenshotUtil;

import java.io.IOException;

public class CheckoutTest extends BaseTest {

    @Test
    public void checkoutTest() throws IOException {
        ExtentTest test = extentReports.createTest("Checkout test");
        test.log(Status.PASS,"Starting test - Checkout test");
        Customer customer = new Customer();

        OrderDetailsPage orderDetailsPage = new HomePage(driver, test)
                .openShopPage()
                .openProduct("Java Selenium WebDriver")
                .addProductToCart()
                .viewCart()
                .openAdressDetails()
                .fillAddressDetails(customer,"Some random comment");

        Assert.assertEquals(orderDetailsPage.getOrderNotice().getText(),"Thank you. Your order has been received.");
        Assert.assertEquals(orderDetailsPage.getProductName().getText(),"Java Selenium WebDriver × 1");
        ScreenshotUtil.getScreen(driver, "Assertions checked", test);
        test.log(Status.PASS,"Test ended - assertions done");
    }
}
