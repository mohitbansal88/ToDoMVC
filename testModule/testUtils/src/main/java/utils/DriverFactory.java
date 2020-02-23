package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class DriverFactory {
    private String input = System.getProperty("user.dir");
    private String projectRoot = input.substring(0, input.indexOf("ToDoMVC") + "ToDoMVC".length());

    protected WebDriver loadDriver(boolean enableJavascript, String browser) {
        switch (browser) {
            case "firefox":
                return createFirefoxDriver(enableJavascript);

            default:
                return createChromeDriver(enableJavascript);
        }

    }

    private WebDriver createFirefoxDriver(boolean enableJavascript) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("javascript.enabled", enableJavascript);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        profile.setPreference("browser.download.dir", new File(projectRoot).getPath() + "\\Downloads");
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.download.panel.shown", false);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        //options.setHeadless(true);
        return new FirefoxDriver(options);
    }

    private WebDriver createChromeDriver(boolean enableJavascript) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities chrome = DesiredCapabilities.chrome();

        Map<String, String> prefs = new Hashtable<>();
        prefs.put("download.prompt_for_download", "false");
        prefs.put("download.default_directory", new File(projectRoot).getPath() + "\\Downloads");

        String[] switches = {"--start-maximized", "--ignore-certificate-errors"};
        options.addArguments("disable-infobars");
        if (enableJavascript) {
            options.addArguments("enable-javascript");
        }
        chrome.setCapability("chrome.prefs", prefs);
        chrome.setCapability("chrome.switches", Arrays.asList(switches));

        options.setCapability(ChromeOptions.CAPABILITY, chrome);

        return new ChromeDriver(options);
    }
}
