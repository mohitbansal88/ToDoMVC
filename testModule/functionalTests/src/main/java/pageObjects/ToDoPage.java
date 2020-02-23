package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utils.TestWebDriver;

import java.util.List;

import static org.openqa.selenium.support.How.*;

public class ToDoPage extends Page {
    @FindBy(how = CLASS_NAME, using = "new-todo")
    private static WebElement newToDo = null;

    @FindBy(how = ID, using = "toggle-all")
    private static WebElement toggleAllButton = null;

    @FindBy(how = CSS, using = "ul.todo-list li")
    private static List<WebElement> toDoList = null;

    private static By destroyButtonSelector = By.className("destroy");

    @FindBy(how = CSS, using = "li .toggle")
    private static List<WebElement> toggleInput = null;

    private static By editInputSelector = By.className("edit");

    @FindBy(how = CSS, using = "footer .todo-count")
    private static WebElement toDoCount = null;

    @FindBy(how = CSS, using = "footer ul.filters li:nth-child(1) a")
    private static WebElement allFilters = null;

    @FindBy(how = CSS, using = "footer ul.filters li:nth-child(3) a")
    private static WebElement activeFilters = null;

    @FindBy(how = CSS, using = "footer ul.filters li:nth-child(5) a")
    private static WebElement completedFilters = null;

    @FindBy(how = CSS, using = "footer .clear-completed")
    private static WebElement clearCompletedButton = null;

    private static By clearCompletedButtonSelector = By.cssSelector("footer .clear-completed");
    private static By toggleAll = By.id("toggle-all");


    ToDoPage(TestWebDriver testWebDriver) {
        super(testWebDriver);
        PageFactory.initElements(new AjaxElementLocatorFactory(TestWebDriver.getDriver(), 1), this);
    }

    public void goToToDoList() {
        testWebDriver.navigateTo(super.getBaseUrl());
    }

    public void addNewToDo(String newToDoText) {
        testWebDriver.enterInput(newToDo, newToDoText);
    }

    public String getItemText(int index) {
        WebElement addedElement = toDoList.get(index);
        return testWebDriver.getText(addedElement);
    }

    public String getLastAddedElementInList() {
        return getItemText(toDoList.size() - 1);
    }

    public int getCountOfItemsTodo() {
        try {
            return Integer.parseInt(toDoCount.getText().split(" ")[0]);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return 0;
        }
    }

    public void deleteTodoItem(int index) {
        WebElement itemToDelete = toDoList.get(index);
        testWebDriver.hoverOnElement(itemToDelete);
        WebElement destroyButton = testWebDriver.findElement(destroyButtonSelector, itemToDelete);
        testWebDriver.clickOnElement(destroyButton);
    }

    public void deleteAllItem() {
        int size = toDoList.size();
        for (int i = 0 ; i < size; i++) {
            deleteTodoItem(0);
        }
//        markAllDone();
//        clearCompleted();
    }

    public void editTodoItem(int index, String newContent) {
        WebElement itemToEdit = toDoList.get(index);
        testWebDriver.doubleClickOnElement(itemToEdit);
        WebElement editInput = testWebDriver.findElement(editInputSelector, itemToEdit);
        testWebDriver.enterInput(editInput, newContent);
    }

    public void toggleAll() {
        if (testWebDriver.isPresent(toggleAll)) {
            testWebDriver.clickOnJsElement(toggleAllButton);
        }
    }

    public void markAllDone() {
        toggleAll();
        if (toDoList.size() > 0 && !toDoList.get(0).getAttribute("class").contains("completed")) {
            toggleAll();
        }
    }

    public void markAllUnDone() {
        toggleAll();
        if (toDoList.size() > 0 && toDoList.get(0).getAttribute("class").contains("completed")) {
            toggleAll();
        }
    }

    public void markDone(int index) {
        WebElement itemToMark = toDoList.get(index);
        if (!toDoList.get(index).getAttribute("class").contains("completed")) {
            testWebDriver.clickOnJsElement(toggleInput.get(index));
        }
    }

    public void markUnDone(int index) {
        WebElement itemToMark = toDoList.get(index);
        if (toDoList.get(index).getAttribute("class").contains("completed")) {
            testWebDriver.clickOnJsElement(toggleInput.get(index));
        }
    }

    public void filterAllItems() {
        testWebDriver.clickOnElement(allFilters);
    }

    public void filterActiveItems() {
        testWebDriver.clickOnElement(activeFilters);
    }

    public void filterCompletedItems() {
        testWebDriver.clickOnElement(completedFilters);
    }

    public int getCountOfItemsInList() {
        return toDoList.size();
    }

    public boolean allFilterIsSelected() {
        return allFilters.getAttribute("class").contains("selected");
    }

    public void clearCompleted() {
        if(isClearCompletedPresent()) {
            testWebDriver.clickOnElement(clearCompletedButton);
        }
    }

    public boolean isClearCompletedPresent() {
        return testWebDriver.isPresent(clearCompletedButtonSelector);
    }
}
