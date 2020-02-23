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
* This class test
* 1. Add item in list
* 2. Add duplicate item to list
* 3. Delete item from list
* 4. Delete only done item from list
* 5. Delete only un done item from list
* 6. Edit only done item from list
* 7. Edit only un done item from list
*
* Verify - Count of item in list, undone count and values in list in above scenario.
*
* Clear all item in ToDo List in BeforeMethod
* */
@Listeners(TakeScreenshot.class)
public class TestToDoList extends TestCaseHelper {

    ToDoPage toDoPage;

    @BeforeClass(groups = {"toDoList"})
    public void setup() {
        super.setup();
        TestWebDriver.loadPropertiesFile("testData.txt");
        toDoPage = PageObjectFactory.getToDoPage(testWebDriver);
        toDoPage.goToToDoList();
    }

    @BeforeMethod(groups = {"toDoList"})
    public void clearToDoList() {
        toDoPage.deleteAllItem();
    }

    @Test(groups = {"toDoList"})
    public void addFirstItemToTodoList() {
        int toDoItemCount = toDoPage.getCountOfItemsTodo();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getLastAddedElementInList(), TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount+1);
    }

    @Test(groups = {"toDoList"})
    public void addDuplicateItemToTodoList() {
        int toDoItemCount = toDoPage.getCountOfItemsTodo();
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getLastAddedElementInList(), TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount+1);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), toDoItemCount+1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getLastAddedElementInList(), TestData.NEW_TODO_ITEM_1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount+2);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), toDoItemCount+2);
    }

    @Test(groups = {"toDoList"})
    public void deleteOnlyItemInTodoList() {
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.deleteTodoItem(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), 0);
    }

    @Test(groups = {"toDoList"})
    public void deleteOnlyDoneItemInTodoList() {
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        int itemsInList = 2;
        toDoPage.markDone(0);
        int toDoItemCount = toDoPage.getCountOfItemsTodo();
        toDoPage.deleteTodoItem(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), itemsInList-1);
    }

    @Test(groups = {"toDoList"})
    public void deleteOnlyUnDoneItemInTodoList() {
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        toDoPage.markDone(0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 1);
        toDoPage.deleteTodoItem(1);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), 0);
    }

    @Test(groups = {"toDoList"})
    public void editOnlyUnDoneItemInTodoList() {
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        int itemsInList = 2;
        toDoPage.markDone(0);
        int toDoItemCount = toDoPage.getCountOfItemsTodo();
        toDoPage.editTodoItem(1, "a");
        Assert.assertEquals(toDoPage.getItemText(1), TestData.NEW_TODO_ITEM_2+"a");
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemsInList);
    }

    @Test(groups = {"toDoList"})
    public void editOnlyDoneItemInTodoList() {
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_1);
        toDoPage.addNewToDo(TestData.NEW_TODO_ITEM_2);
        int itemsInList = 2;
        toDoPage.markDone(0);
        int toDoItemCount = toDoPage.getCountOfItemsTodo();
        toDoPage.editTodoItem(0, "a");
        Assert.assertEquals(toDoPage.getItemText(0), TestData.NEW_TODO_ITEM_1+"a");
        Assert.assertEquals(toDoPage.getCountOfItemsTodo(), toDoItemCount);
        Assert.assertEquals(toDoPage.getCountOfItemsInList(), itemsInList);
    }

    @AfterClass(groups = {"toDoList"})
    public void tearDown() {
        tearDownSuite();
        PageObjectFactory.clearAllPageObjects();
    }
}
