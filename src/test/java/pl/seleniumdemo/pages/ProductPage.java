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

import java.io.IOException;

public class ProductPage {
    @FindBy(name = "add-to-cart")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='woocommerce-message']//a[text()='View cart']")
    private WebElement viewCartButton;

    private WebDriver driver;
    private ExtentTest test;
    private static final Logger logger = LogManager.getLogger();

    public ProductPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public ProductPage addProductToCart() throws IOException {
        logger.info("Adding product to cart");
        test.log(Status.PASS, "Adding product to cart");
        addToCartButton.click();
        ScreenshotUtil.getScreen(driver,"Product added to cart", test);
        return this;
    }

    public CartPage viewCart() throws IOException {
        logger.info("Going to view cart");
        test.log(Status.PASS, "Going to view cart");
        viewCartButton.click();
        ScreenshotUtil.getScreen(driver,"Cart view", test);
        return new CartPage(driver, test);
    }
}
