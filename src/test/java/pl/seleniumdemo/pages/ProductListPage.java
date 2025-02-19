package pl.seleniumdemo.pages;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.utils.ScreenshotUtil;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.io.IOException;

public class ProductListPage {

    @FindBy(xpath = "(//i[@class='icn-shoppingcart'])[1]")
    private WebElement viewCartButton;

    @FindBy(xpath = "//a[@class='button wc-forward']")
    private WebElement getViewCartButton;

    private WebDriver driver;
    private ExtentTest test;
    private static final Logger logger = LogManager.getLogger();

    public ProductListPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public ProductPage openProduct(String title) throws IOException {
        By productXpath = (By.xpath("//h2[text()='" + title + "']"));
        SeleniumHelper.waitForClickable(productXpath,driver);
        driver.findElement(productXpath).click();
        logger.info("Product picked");
        ScreenshotUtil.getScreen(driver,"Product picked", test);
        return new ProductPage(driver, test);
    }

    public ProductListPage selectProduct(String title) throws IOException {
        By productXpath = (By.xpath("//a[@aria-label='Add “" + title + "” to your cart']"));
        SeleniumHelper.waitForClickable(productXpath,driver);
        driver.findElement(productXpath).click();
        ScreenshotUtil.getScreen(driver,title + " picked", test);
        return this;
    }

    public CartPage viewCart() throws IOException {
        logger.info("Opening view cart");
        viewCartButton.click();
        logger.info("Going to view cart");
        SeleniumHelper.waitForClickable(getViewCartButton,driver);
        getViewCartButton.click();
        ScreenshotUtil.getScreen(driver,"View cart showed", test);
        return new CartPage(driver, test);
    }
}
