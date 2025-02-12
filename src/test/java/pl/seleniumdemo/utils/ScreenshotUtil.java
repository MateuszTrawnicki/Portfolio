package pl.seleniumdemo.utils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String directoryPath = "src/test/resources/screenshots/";
        String filePath = directoryPath + screenshotName + "_" + timestamp + ".png";

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileUtils.copyFile(srcFile, new File(filePath));
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getScreen(WebDriver driver, String screenName, ExtentTest test) throws IOException {
        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, screenName);
        if (screenshotPath != null) {
            test.log(Status.PASS, screenName,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.log(Status.WARNING, "Nie udało się zapisać zrzutu ekranu dla: " + screenName);
        }
    }

}

