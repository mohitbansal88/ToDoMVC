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
* This class test Clear completed functionality
*  Setup. Run BeforeMethod and
*       Add 3 item in list
*       Mark first item as done.
*
* 1. Mark all undone, verify counts, mark all done, verify counts, clear completed and verify counts
* 2. Clean completed, verify counts, mark all done, verify counts, clear completed and verify counts
* 3. Filter completed items, Clean completed, verify counts, mark all done, verify counts, clear completed and verify counts
* */
@Listeners(TakeScreenshot.class)
public class TestClearCompleted extends TestCaseHelper {

    ToDoPage toDoPage;
    int itemAdded;

    @BeforeClass(groups = {"clearCompleted"})
    public void setup() {
        super.setup();
        TestWebDriver.loadPropertiesFile("testData.txt");
        toDoPage = PageObjectFactory.getToDoPage(testWebDriver);
        toDoPage.goToToDoList();
    }

    @BeforeMethod(groups = {"clearCompleted"})
    public void dataSetup() {
        toDoPage.deleteAllItem();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.filterAllItems();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_3);
        itemAdded = 3;
        toDoPage.markDone(0);
    }

    @Test(groups = {"clearCompleted"})
    public void validateClearCompletedWhenNothingCompleted() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        Assert.assertFalse(toDoPage.isClearCompletedPresent());

        toDoPage.markAllDone();
        Assert.assertTrue(toDoPage.isClearCompletedPresent());

        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        Assert.assertFalse(toDoPage.isClearCompletedPresent());
    }

    @Test(groups = {"clearCompleted"})
    public void validateClearCompletedFromAllList() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllDone();
        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
    }

    @Test(groups = {"clearCompleted"})
    public void validateClearCompletedFromActiveList() {
        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(),2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.filterAllItems();
        toDoPage.markAllDone();
        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
    }

    @Test(groups = {"clearCompleted"})
    public void validateClearCompletedFromCompletedList() {
        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllDone();
        toDoPage.clearCompleted();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
    }

    @AfterClass(groups = {"clearCompleted"})
    public void tearDown() {
        tearDownSuite();
        PageObjectFactory.clearAllPageObjects();
    }
}
