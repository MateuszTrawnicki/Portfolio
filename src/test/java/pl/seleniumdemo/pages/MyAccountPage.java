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

public class MyAccountPage {

    @FindBy(id = "reg_email")
    private WebElement emailAdressInput;

    @FindBy(id = "reg_password")
    private WebElement regPasswordInput;

    @FindBy(name = "register")
    private WebElement registerButton;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(name = "login")
    private WebElement loginButton;

    @FindBy(xpath = "//ul[@class='woocommerce-error']//li")
    private WebElement errors;

    private WebDriver driver;

    private ExtentTest test;

    private static final Logger logger = LogManager.getLogger();

    public MyAccountPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.test = test;
    }

    public LoggedUserPage registerNewUser(String email, String password) throws IOException {
        registerUser(email, password);
        return new LoggedUserPage(driver);
    }

    public MyAccountPage registerUserWithSameEmail(String email, String password) throws IOException {
        registerUser(email, password);
        return this;
    }

    private void registerUser(String email, String password) throws IOException {
        logger.info("Setting email: " + email);
        emailAdressInput.sendKeys(email);
        ScreenshotUtil.getScreen(driver,"Setting email", test);
        logger.info("Setting password: " + password);
        regPasswordInput.sendKeys(password);
        ScreenshotUtil.getScreen(driver,"Setting password", test);
        logger.info("Register perform");
        test.log(Status.PASS,"Register performing");
        registerButton.click();
        logger.info("Register performed");
        ScreenshotUtil.getScreen(driver,"Register performed", test);
    }

    public MyAccountPage setUserValidData(String email, String password) throws IOException {
        logIn(email,password);
        new LoggedUserPage(driver);
        return this;
    }

    public void logIn(String username, String password) throws IOException {
        logger.info("Setting login: " + username);
        test.log(Status.PASS,"Setting login: " + username);
        usernameInput.sendKeys(username);
        ScreenshotUtil.getScreen(driver,"setting login", test);
        logger.info(("Setting password: " + password));
        test.log(Status.PASS,"Setting password: " + password);
        passwordInput.sendKeys(password);
        ScreenshotUtil.getScreen(driver,"setting password", test);
        logger.info(("Setting data done"));
        test.log(Status.PASS,"Setting data done");
        ScreenshotUtil.getScreen(driver,"setting data done", test);
    }

    public LoggedUserPage logInPerform() throws IOException {
        logger.info("Logging in");
        test.log(Status.PASS,"Logging in");
        loginButton.click();
        logger.info(("Logged done"));
        test.log(Status.PASS,"Logged done");
        ScreenshotUtil.getScreen(driver,"Logged done", test);
        return new LoggedUserPage(driver);
    }

    public WebElement getErrors() {
        return errors;
    }


    public MyAccountPage loginUserInValidData(String email, String password) throws IOException {
        logIn(email,password);
        logger.info("Try to log in");
        test.log(Status.PASS,"Try to log in");
        loginButton.click();
        logger.info(("Log in button clicked"));
        test.log(Status.PASS,"Log in button clicked");
        ScreenshotUtil.getScreen(driver,"Log in button clicked", test);
        return this;
    }
}
