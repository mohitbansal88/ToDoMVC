package utils;

import org.openqa.selenium.remote.UnreachableBrowserException;

public class TestCaseHelper {
    private static boolean isSeleniumStarted;
    protected static TestWebDriver testWebDriver;
    private DriverFactory driverFactory = new DriverFactory();

    public void setup() {
        if (!isSeleniumStarted) {
            String browser = "chrome";
            if(System.getProperty("browser") != null) {
                browser = System.getProperty("browser");
            }
            testWebDriver = loadDriver(browser);
            addTearDownShutDownHook();
            isSeleniumStarted = true;
        }
    }

    private void addTearDownShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (testWebDriver != null) {
                    tearDownSuite();
                }
            }
        });
    }

    protected void tearDownSuite() {
        try {
            if (testWebDriver != null) {
                testWebDriver.quitDriver();
            }
            isSeleniumStarted=false;
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        }
    }

    private TestWebDriver loadDriver(String browser) {
        return new TestWebDriver(driverFactory.loadDriver(true, browser));
    }
}
