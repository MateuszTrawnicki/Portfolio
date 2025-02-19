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
        SeleniumHelper.waitForVisibility(By.xpath("//td[contains(@class,'product-name')]"),driver);
        return driver.findElements(By.xpath("//td[contains(@class,'product-name')]"));
    }

    public double getSubtotalAmount() {
        return Double.parseDouble(subtotalAmount.getText().trim().replace(",",".").replace("zł",""));
    }

    public double getTotalAmount() {
        return Double.parseDouble(totalAmount.getText().trim().replace(",",".").replace("zł",""));
    }

    public List<WebElement> getProductsAmount() {
        SeleniumHelper.waitForVisibility(By.xpath("//tbody//span[@class='woocommerce-Price-amount amount']"),driver);
        return driver.findElements(By.xpath("//tbody//span[@class='woocommerce-Price-amount amount']"));
    }

    public double getSumOfProductsAmount() {
        List<WebElement> productAmounts = getProductsAmount();
        return productAmounts.stream()
                .mapToDouble(e -> {
                    try {
                        return Double.parseDouble(e.getText().trim().replace(",",".").replace("zł",""));
                    } catch (StaleElementReferenceException ex) {
                        e = driver.findElement(By.xpath("//tbody//span[@class='woocommerce-Price-amount amount']"));
                        return Double.parseDouble(e.getText().trim().replace(",",".").replace("zł",""));
                    }
                })
                .sum();
    }

//    public List<String> getActualText(){
//        return getProductName().stream().map(element -> element.getText().trim()).filter(text -> !text.isEmpty()).toList();
//    }

    public List<String> getActualText() {
        List<String> actualText = new ArrayList<>();

        // Próbujemy ponownie zlokalizować elementy w przypadku wyjątku StaleElementReferenceException
        try {
            List<WebElement> productNames = getProductName(); // Zaktualizowane elementy
            for (WebElement element : productNames) {
                try {
                    String text = element.getText().trim();
                    if (!text.isEmpty()) {
                        actualText.add(text);
                    }
                } catch (StaleElementReferenceException ex) {
                    // Jeśli wystąpi StaleElementReferenceException, próbujemy ponownie znaleźć element
                    productNames = getProductName(); // Ponowne zlokalizowanie elementów
                    element = productNames.get(productNames.indexOf(element)); // Ponowne przypisanie elementu
                    String text = element.getText().trim();
                    if (!text.isEmpty()) {
                        actualText.add(text);
                    }
                }
            }
        } catch (StaleElementReferenceException ex) {
            // Obsługuje przypadek, gdy cała lista elementów jest stale zaktualizowana
            List<WebElement> productNames = getProductName(); // Ponowne zlokalizowanie elementów
            for (WebElement element : productNames) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    actualText.add(text);
                }
            }
        }

        return actualText;
    }


}
