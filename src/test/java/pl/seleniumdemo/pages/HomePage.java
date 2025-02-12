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

public class HomePage {
    @FindBy(xpath = "//span[text()='My account']")
    private WebElement myAccountLink;

    @FindBy(xpath = "//span[text()='Shop']")
    private WebElement shopLink;

    @FindBy(xpath = "//span[text()='Cart']")
    private WebElement cartLink;

    private WebDriver driver;

    private ExtentTest test;

    private static final Logger logger = LogManager.getLogger();

    public HomePage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public MyAccountPage openMyAccountPage() throws IOException {
        logger.info("Home Page opened");
        ScreenshotUtil.getScreen(driver,"Home Page opened", test);
        logger.info("Opening My Account Page");
        test.log(Status.PASS, "Opening My Account Page");
        myAccountLink.click();
        logger.info("My Account Page opened");
        ScreenshotUtil.getScreen(driver,"My Account Page opened", test);
        return new MyAccountPage(driver, test);
    }

    public ProductListPage openShopPage() {
        shopLink.click();
        return new ProductListPage(driver, test);
    }
}
