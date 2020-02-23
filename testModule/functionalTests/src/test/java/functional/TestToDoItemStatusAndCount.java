package functional;

import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.PageObjectFactory;
import pageObjects.TestData;
import pageObjects.ToDoPage;
import utils.TakeScreenshot;
import utils.TestCaseHelper;
import utils.TestWebDriver;

/*
* This class test status and count
*  Setup
*       Add 3 item in list
*
*  1. Verify Counts when item is Marked done
*  2. Verify Counts when item is Marked undone
*  3. Verify Counts when all item are Marked done
*  4. Verify Counts when all item are Marked undone
* */
@Listeners(TakeScreenshot.class)
public class TestToDoItemStatusAndCount extends TestCaseHelper {

    ToDoPage toDoPage;
    int itemAdded;

    @BeforeClass(groups = {"status"})
    public void setup() {
        super.setup();
        TestWebDriver.loadPropertiesFile("testData.txt");
        toDoPage = PageObjectFactory.getToDoPage(testWebDriver);
        toDoPage.goToToDoList();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_3);
        itemAdded = 3;
    }

    @Test(groups = {"status"})
    public void validateCountWhenItemIsMarkedDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);
        toDoPage.markDone(1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-2);
        toDoPage.markDone(2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-3);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
    }

    @Test(groups = {"status"})
    public void validateCountWhenItemIsMarkedUnDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.markUnDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 1);
        toDoPage.markUnDone(1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 2);
        toDoPage.markUnDone(2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 3);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
    }

    @Test(groups = {"status"})
    public void validateCountWhenAllItemsAreMarkedDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
    }

    @Test(groups = {"status"})
    public void validateCountWhenAllItemsAreMarkedUnDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
    }

    @AfterMethod(groups = {"status"})
    public void resetStatus() {
        toDoPage.markAllUnDone();
    }

    @AfterClass(groups = {"status"})
    public void tearDown() {
        tearDownSuite();
        PageObjectFactory.clearAllPageObjects();
    }
}
