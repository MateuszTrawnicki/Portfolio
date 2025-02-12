package pl.seleniumdemo.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pl.seleniumdemo.models.Customer;
import pl.seleniumdemo.utils.ScreenshotUtil;

import java.io.IOException;

public class AdressDetailsPage {

    @FindBy(id = "billing_first_name")
    private WebElement firstNameInput;

    @FindBy(id = "billing_last_name")
    private WebElement lastNameInput;

    @FindBy(id = "billing_company")
    private WebElement companyNameInput;

    @FindBy(id = "billing_country")
    private WebElement billingCountrySelect;

    @FindBy(id = "billing_address_1")
    private WebElement billingAddressInput;

    @FindBy(id = "billing_address_2")
    private WebElement billingAddressSecondInput;

    @FindBy(id = "billing_postcode")
    private WebElement billingPostCodeInput;

    @FindBy(id = "billing_city")
    private WebElement billingCityInput;

    @FindBy(id = "billing_phone")
    private WebElement billingPhoneInput;

    @FindBy(id = "billing_email")
    private WebElement billingEmailInput;

    @FindBy(id = "order_comments")
    private WebElement orderComentsInput;

    @FindBy(id = "place_order")
    private WebElement placeOrderButton;

    private WebDriver driver;
    private ExtentTest test;
    private static final Logger logger = LogManager.getLogger();

    public AdressDetailsPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public OrderDetailsPage fillAddressDetails(Customer customer, String comments) throws IOException {
        logger.info(("Setting address details"));
        test.log(Status.PASS,"Setting address details");
        firstNameInput.sendKeys(customer.getFirstName());
        lastNameInput.sendKeys(customer.getLastName());
        companyNameInput.sendKeys(customer.getCompanyName());
        Select countrySelect = new Select(billingCountrySelect);
        countrySelect.selectByVisibleText(customer.getCountry());
        billingAddressInput.sendKeys(String.format("%s %s",customer.getStreet(),customer.getFlatNumber()));
        billingPostCodeInput.sendKeys(customer.getZipCode());
        billingCityInput.sendKeys(customer.getCity());
        billingPhoneInput.sendKeys(customer.getPhone());
        billingEmailInput.sendKeys(customer.getEmail());
        ScreenshotUtil.getScreen(driver,"Address details set", test);
        orderComentsInput.sendKeys(comments);
        ScreenshotUtil.getScreen(driver,"Address details set2", test);
        logger.info(("Ordering..."));
        test.log(Status.PASS,"Ordering...");
        placeOrderButton.click();
        ScreenshotUtil.getScreen(driver,"Ordered", test);
        return new OrderDetailsPage(driver);
    }
}
