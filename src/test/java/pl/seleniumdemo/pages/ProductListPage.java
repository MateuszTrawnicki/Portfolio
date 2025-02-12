package pl.seleniumdemo.pages;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.utils.ScreenshotUtil;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.io.IOException;

public class ProductListPage {
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
        ScreenshotUtil.getScreen(driver,"Product picked", test);
        return new ProductPage(driver, test);
    }
}
