package pl.seleniumdemo.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPage {
    @FindBy(xpath = "//div[@class='woocommerce-order']//p")
    private WebElement orderNotice;

    @FindBy(xpath = "(//tfoot//span[@class='woocommerce-Price-amount amount'])[1]")
    private WebElement subtotalAmount;

    @FindBy(xpath = "(//tfoot//span[@class='woocommerce-Price-amount amount'])[2]")
    private WebElement totalAmount;

    private WebDriver driver;
    private ExtentTest test;

    public OrderDetailsPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public WebElement getOrderNotice() {
        return orderNotice;
    }

    public List<WebElement> getProductName() {
        SeleniumHelper.waitForPageToBeStable(driver);
        try {
            SeleniumHelper.waitForVisibility(By.xpath("//td[contains(@class,'product-name')]"), driver);
            return driver.findElements(By.xpath("//td[contains(@class,'product-name')]"));
        } catch (StaleElementReferenceException e) {
            SeleniumHelper.waitForVisibility(By.xpath("//td[contains(@class,'product-name')]"), driver);
            return driver.findElements(By.xpath("//td[contains(@class,'product-name')]"));
        }
    }



    public double getSubtotalAmount() {
        SeleniumHelper.waitForVisibility(By.xpath("//tfoot//span[@class='woocommerce-Price-amount amount']"),driver);
        return Double.parseDouble(subtotalAmount.getText().trim().replace(",",".").replace("zł",""));
    }

    public double getTotalAmount() {
        SeleniumHelper.waitForVisibility(By.xpath("//tfoot//span[@class='woocommerce-Price-amount amount']"),driver);
        return Double.parseDouble(totalAmount.getText().trim().replace(",",".").replace("zł",""));
    }

    public List<WebElement> getProductsAmount() {
        SeleniumHelper.waitForVisibility(By.xpath("//tbody//span[@class='woocommerce-Price-amount amount']"),driver);
        return driver.findElements(By.xpath("//tbody//span[@class='woocommerce-Price-amount amount']"));
    }

    public double getSumOfProductsAmount() {
        try {
            return getProductsAmount().stream()
                    .map(WebElement::getText)
                    .map(text -> text.trim().replace(",", ".").replace("zł", ""))
                    .mapToDouble(Double::parseDouble)
                    .sum();
        } catch (StaleElementReferenceException e) {
            return getProductsAmount().stream()
                    .map(WebElement::getText)
                    .map(text -> text.trim().replace(",", ".").replace("zł", ""))
                    .mapToDouble(Double::parseDouble)
                    .sum();
        }
    }

    public List<String> getActualText() {
        return getProductName().stream()
                .map(element -> element.getText().replaceAll("\\s+", " ").trim())
                .filter(text -> !text.isEmpty())
                .toList();
    }

}
