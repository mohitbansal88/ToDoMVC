package pageObjects;

import utils.TestConstants;
import utils.TestWebDriver;

public class Page {
    TestWebDriver testWebDriver;

    Page(TestWebDriver testWebDriver) {
        this.testWebDriver = testWebDriver;
    }

    public String getUrl() {
        return testWebDriver.getCurrentUrl();
    }

    public String getBaseUrl() {
        return TestConstants.BASE_URL;
    }
}