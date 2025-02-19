package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.models.Customer;
import pl.seleniumdemo.pages.HomePage;
import pl.seleniumdemo.pages.OrderDetailsPage;
import pl.seleniumdemo.utils.ScreenshotUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        List<String> actualText = orderDetailsPage.getProductName().stream().map(element -> element.getText().replaceAll("\\s+"," ").trim()).filter(text -> !text.isEmpty()).toList();
        List<String> expectedText = Arrays.asList("Java Selenium WebDriver × 1");

        Assert.assertEquals(orderDetailsPage.getOrderNotice().getText(),"Thank you. Your order has been received.");
        Assert.assertEquals(actualText,expectedText);
        ScreenshotUtil.getScreen(driver, "Assertions checked", test);
        test.log(Status.PASS,"Test ended - assertions done");
    }

    @Test
    public void checkoutWithAllProductTest() throws IOException {
        ExtentTest test = extentReports.createTest("Checkout with all product test");
        test.log(Status.PASS,"Starting test - Checkout test");
        Customer customer = new Customer();

        OrderDetailsPage orderDetailsPage = new HomePage(driver, test)
                .openShopPage()
                .selectProduct("BDD Cucumber")
                .selectProduct("GIT basics")
                .selectProduct("Java Selenium WebDriver")
                .viewCart()
                .openAdressDetails()
                .fillAddressDetails(customer,"Some random comment");

        double sumOfAllProducts = orderDetailsPage.getSumOfProductsAmount();
        List<String> actualText = orderDetailsPage.getActualText();
        List<String> expectedText = Arrays.asList("BDD Cucumber × 1","GIT basics × 1","Java Selenium WebDriver × 1");

        Assert.assertEquals(orderDetailsPage.getOrderNotice().getText(),"Thank you. Your order has been received.");
        Assert.assertEquals(actualText,expectedText);
        Assert.assertEquals(sumOfAllProducts,orderDetailsPage.getTotalAmount(),"Kwoty są rozne");
        Assert.assertEquals(sumOfAllProducts,orderDetailsPage.getSubtotalAmount(),"Kwoty są rozne");

        ScreenshotUtil.getScreen(driver, "Assertions checked", test);
        test.log(Status.PASS,"Test ended - assertions done");
    }
}
