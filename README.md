# ToDoMVC

### Objective:
Design framework and write UI tests for automating web-app

 ### Tech Stack:
* Java 8
* Selenium WebDriver
* TestNG
* Gradle 4.10
* git
* Chrome
* Firefox

 ### System Requirements to run the tests: ### 
* Mac Os
* Java 8

 ### Architechture: ### 
The framework is using Page Object Model, that is used to segregate page dependent information from test logic. For each pages there should be one pageObject, listing all elements locators and the methods on those elements.

There are two modules under testModule:

#### Under functional module:
* Page objects are grouped at /src/main/java/pageObjects
* The functional tests on these pages are grouped at /src/test/java/functional
* test data for test cases is at /src/test/resources
* TestNG suite file

#### Under TestCore module,
* we have utils, or say helpers, for our test cases, at /src/main/java/utils
* And test environment configs at /src/main/resources 
* All external methods are wrapped under utils method to save rework due to deprecated methods in upgraded versions. 

* Each module has its own build.gradle file. The test tasks are created in build file functionalTests module.

 ### Run Test Scenarios: ### 
For demo, three scenarios are automated.
* Execution
  * To run the test case there are three options,
      * Right click on test file from IDE and run as TestNG (requires TestNG plugin)
      * Run the suite xml file
      * Run via Gradle task, from root directory run the following commands
        * ./gradlew clean build
        * ./gradlew clean testNGTests (Will run on Chrome)
        * ./gradlew clean testNGTests -Dbrowser=firefox (Will run on Firefox)
        * ./gradlew clean testNGTests -Dbrowser=chrome (Will run on Chrome)
* Test Result
  * An HTML report will be generated, example at 'testModule/functionalTests/build/reports/tests/testNGTests/html/index.html'
  * Screenshot for failing tests will be taken, example at 'testModule/functionalTests/verifyUserWithEditRightsCanSeeEditPageButton(functional.TestRestrictionEditByAuthor)-20200105-064523-screenshot.png'
  * In Html report, click on Log Output. It will take to failing test screenshot hyperlink.
  
### Please read Deliverables.txt for 
* If you joined a team of six engineers as the only engineer with testing experience and could implement any process you would like, how would you ensure that the team delivers high quality software?
* If you were tasked with writing testing notes for this acceptance criteria (point 1 and 2), what would you write?
* Tell us what else you might have done for the test if you had more time.