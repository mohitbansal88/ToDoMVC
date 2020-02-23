package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class TestWebDriver {
    private static int DEFAULT_WAIT_TIME;
    private static WebDriver webDriver;
    private static Properties prop = new Properties();
    private static JavascriptExecutor js;

    public TestWebDriver(WebDriver driver) {
        webDriver = driver;
        loadPropertiesFile("config.txt");
        DEFAULT_WAIT_TIME = Integer.parseInt(TestConstants.DEFAULT_WAIT);
        maximizeWindows();
        js = (JavascriptExecutor) driver;
    }

    public static void setWebDriver(WebDriver newWebDriver) {
        webDriver = newWebDriver;
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void loadPropertiesFile(String propertyFileName) {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName);
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void goToBaseUrl() {
        webDriver.manage().deleteAllCookies();
        navigateTo(TestConstants.BASE_URL);
    }

    public void maximizeWindows() {
        webDriver.manage().window().maximize();
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public String getAttribute(WebElement element, String attributeName) {
        waitForElementToAppear(element);
        return element.getAttribute(attributeName);
    }

    public void waitForElementToAppear(final WebElement element) {
        (new WebDriverWait(webDriver, DEFAULT_WAIT_TIME)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (element.isDisplayed());
            }
        });
    }

    public void waitForElementToDisappear(final WebElement element, int waitTime) {
        (new WebDriverWait(webDriver, waitTime)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (!element.isDisplayed());
            }
        });
    }

    public boolean isPresent(By selector) {
        return findElements(selector).size()!=0;
    }

    public void waitForElementToDisappear(final String cssSelector) {
        (new WebDriverWait(webDriver, DEFAULT_WAIT_TIME)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return findElements(By.cssSelector(cssSelector)).size()==0;
            }
        });
    }


    public void enterInput(final WebElement element, String input) {
        waitForElementToAppear(element);
        element.sendKeys(input);
        element.sendKeys(Keys.ENTER);
    }

    public void enterInput(final WebElement element, Keys input) {
        waitForElementToAppear(element);
        element.clear();
        element.sendKeys(input);
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    public List<WebElement> findElements(By by, WebElement parentElement) {
        return parentElement.findElements(by);
    }

    public WebElement findElement(By by, WebElement parentElement) {
        return parentElement.findElement(by);
    }

    public void quitDriver() {
        try {
            webDriver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        return webDriver;
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnElement(WebElement element) {
        waitForElementToAppear(element);
        element.click();
    }

    public void clickOnJsElement(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public String getText(WebElement element) {
        return getAttribute(element, "textContent").trim();
    }

    public Boolean isSelected(WebElement element) {
        waitForElementToAppear(element);
        return element.isSelected();
    }

    public void hoverOnElement(WebElement element) {
        Actions action = new Actions(webDriver);
        action.moveToElement(element).perform();
    }

    public void doubleClickOnElement(WebElement element) {
        Actions action = new Actions(webDriver);
        action.doubleClick(element).perform();
    }
}