package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenshot extends TestListenerAdapter {

    Date dObjNew = new Date();
    SimpleDateFormat formatterNew = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);

        WebDriver driver = TestWebDriver.getDriver();
        Date dObj = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String time = formatter.format(dObj);
        String testMethodAndTestClass = testResult.getMethod().getMethodName() + "(" + testResult.getTestClass().getName() + ")";
        String filename = testMethodAndTestClass + "-"
                + time + "-screenshot"
                + ".png";

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            File outputFile = new File(filename);
            FileUtils.copyFile(scrFile, outputFile);
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            String path = "file://" + System.getProperty("user.dir") + "/" + filename;

            Reporter.log("Failing Test : " + testMethodAndTestClass + "<br>");
            Reporter.log("<a href='"+ path + "'> " + path + " </a>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
