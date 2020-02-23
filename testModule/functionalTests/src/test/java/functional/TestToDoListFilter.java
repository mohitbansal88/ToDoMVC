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
* This class test filtering
*  Setup
*       Add 3 item in list
*       Mark first item as done.
* 1. Verify All filter selected and count of item in list and ToDo count
* 2. Select Active filter and verify counts
* 3. Select Completed filter and verify counts
* 4. Mark second item as done and again verify for all filter.
* 5. Mark first item as undone and again verify for all filter.
* 6. Mark all item as undone and again verify for all filter.
* 7. Mark all item as done and again verify for all filter.
* 8. Validate change of status from All filter
* 9. Validate change of status from Active filter
* 10. Validate change of status from Completed filter
*
* Reset ToDo List in AfterMethod
* */
@Listeners(TakeScreenshot.class)
public class TestToDoListFilter extends TestCaseHelper {

    ToDoPage toDoPage;
    int itemAdded;

    @BeforeClass(groups = {"filter"})
    public void setup() {
        super.setup();
        TestWebDriver.loadPropertiesFile("testData.txt");
        toDoPage = PageObjectFactory.getToDoPage(testWebDriver);
        toDoPage.goToToDoList();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_3);
        itemAdded = 3;
        toDoPage.markDone(0);
    }

    @Test(groups = {"filter"}, priority = 1)
    public void validateToDoListFilter() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);
        Assert.assertTrue(toDoPage.allFilterIsSelected());

        toDoPage.filterAllItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateToDoListFilterWhenItemIsMarkedDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markDone(1);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-2);

        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-2);

        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 2);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-2);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateToDoListFilterWhenItemIsMarkedUnDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markUnDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateToDoListFilterWhenAllItemsAreMarkedUnDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateToDoListFilterWhenAllItemsAreMarkedDone() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateChangeStatusFromAllItemList() {
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.markUnDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 1);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateChangeStatusFromActiveItemList() {
        toDoPage.filterActiveItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);
    }

    @Test(groups = {"filter"}, priority = 2)
    public void validateChangeStatusFromCompletedItemList() {
        toDoPage.filterCompletedItems();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded-1);

        toDoPage.markAllUnDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemAdded);

        toDoPage.markAllDone();
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);

        toDoPage.markUnDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemAdded-1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 1);
    }

    @AfterMethod(groups = {"filter"})
    public void resetStatus() {
        toDoPage.filterAllItems();
        toDoPage.markAllUnDone();
        toDoPage.markDone(0);
    }

    @AfterClass(groups = {"filter"})
    public void tearDown() {
        tearDownSuite();
        PageObjectFactory.clearAllPageObjects();
    }
}
