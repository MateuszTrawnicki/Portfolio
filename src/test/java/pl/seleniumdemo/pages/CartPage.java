package pl.seleniumdemo.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.utils.ScreenshotUtil;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.io.IOException;

public class CartPage {

    @FindBy(partialLinkText = "Proceed to checkout")
    private WebElement proceedToCheckoutButton;

    private WebDriver driver;
    private ExtentTest test;
    private static final Logger logger = LogManager.getLogger();

    public CartPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public AdressDetailsPage openAdressDetails() throws IOException {
        logger.info("Going to proceed");
        test.log(Status.PASS,"Going to proceed");
        SeleniumHelper.waitForClickable(proceedToCheckoutButton,driver);
        proceedToCheckoutButton.click();
        logger.info("Proceed view");
        ScreenshotUtil.getScreen(driver,"Proceed view", test);
        return new AdressDetailsPage(driver, test);
    }
}
