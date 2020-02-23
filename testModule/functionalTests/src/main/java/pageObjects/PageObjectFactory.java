package pageObjects;

import utils.TestWebDriver;

public class PageObjectFactory {
    private static Page instanceOfPage;
    private static ToDoPage instanceOfToDoPage;

    public static Page getPage(TestWebDriver testWebDriver) {
        if(instanceOfPage == null) {
            instanceOfPage = new Page(testWebDriver);
        }
        return instanceOfPage;
    }

    public static ToDoPage getToDoPage(TestWebDriver testWebDriver) {
        if(instanceOfToDoPage == null) {
            instanceOfToDoPage = new ToDoPage(testWebDriver);
        }
        return instanceOfToDoPage;
    }

    public static void clearAllPageObjects() {
        instanceOfPage=null;
        instanceOfToDoPage = null;
    }
}