**Selenium Java Maven Project with Page Factory Design Pattern**
This is a _Maven project_ that demonstrates how to use the _Page Factory design pattern_ with Selenium WebDriver in Java. The project uses _TestNG_ as the test framework.

**Setup**

**Prerequisites**
* Java 8 or later
* Maven 3 or later
* Chrome, Edge or Firefox web browser

**Installation**
Clone the project or download it from repository or drive.

_To fetch the dependencies from Command line:_
Add maven path to the environment variables. 
Open a terminal or command prompt and navigate to the project directory
Run the following command to download the project dependencies:
Copy code
mvn clean install

_To fetch the dependencies from IDE:_
Open project in the IDE and update/reload project


**Usage**

**_Test classes:_**

**LoginTest:**  This class contains test cases to test functionalities around login and create account. It uses the LoginPage class to define the elements of the login page and the login() method to perform the login action.
It also has a test method "verifyScreenshotIsCapturedForFailureScenario" which is designed to fail intentionally in order to test the screenshot capturing capability of the code in failure cases.

**ProductsPageTest:**  This class contains test cases to test functionalities like "add to cart", "search", "checkout", "remove from cart".
It uses the ProductPage class to define the elements of the products page and various methods to test the functionalities around cart.

**BaseTest:** This class is extended by all the test classes. It contains all the initialization code and code for:
* Reporting(Extent Report)
* Logging (uses log4j.properties file)
* Data driving from _Excel file_ using ExcelReader utility class
* Appropriate before/after annotations
* Environment setup
* teardown

_(Both the LoginTest and ProductTest classes demonstrates how to use the Page Factory design pattern to interact with the elements and how to read the data from data provider which is again integrated with excel.)_ 

**BasePage**
The project contains "BasePage" which is extended by all the other page classes. It provides methods for handling web elements, such as waiting for their visibility and click actions. It also has methods for logging out, accessing the user account page, and checking if certain elements are displayed. The class uses the WebDriver and WebDriverWait classes from Selenium, and LogManager and Logger classes from Log4j.

**Utility classes**
* DriverManger
* ExcelReader
* ExtentReportNG
* FileUtils

(_These classes can be found at path:  src/main/java/com/nagp/utils_)

**Data driving:**
* Data providers
* Excel File
* config.properties file

Data is being read from the config.properties file using FileUtils class.

Integrated excel with DataProviders using ExcelReader Utility class to fetch data in the test cases.


**To run the tests using command line** 
open a terminal or command prompt and navigate to the project directory. Then, run the following command:

mvn clean test -Dsurefire.suiteXmlFiles=src/test/java/com/nagp/tests/resources/suite/{Filename}.xml

_Eg: mvn clean test -Dsurefire.suiteXmlFiles=src/test/java/com/nagp/tests/resources/suite/LoginTest.xml_

**To run the classes in parallel mode:**
Run this: ParallelTest.xml

**To test the grouping functionality, use below XMLs:**
SmokeTest.xml  (This will pick test cases marked as "Smoke")
RegressionTest.xml  (This will pick test cases marked as "Regression")

**To run specific classes, use below XMLs:**
LoginTest.xml
ProductTest.xml

_All the xml files can be found under path: src/test/java/com/nagp/tests/resources/suite_


**Extent Report**: The reports are stored in the Reports at the project path. The Reports folder contains both the "Archived test results" 
and "Current test results"

**Logs**: Logs are stored in the "logs" folder at the project path. We have stored logs in two separate log file, "app.logs" for application logs and
"myLogs.log" contains all the logs.

**Screenshots**: Screenshots are being captured for failures in "Screenshots" folder at the project path. Logic can be found in the "TestListener" class

**Waits**:
Waits have been implemented in the "BasePage" class and being used by all the child classes.

**Global Configurations**:
Below global configurations have been defined in config.properties:
* testURL
* globalWait
* userId
* password
* browser

**Browsers supported**: Logic has been implemented in "DriverManager" class
* Chrome
* Edge
* Firefox

**TestNG Listeners**: Refer class "TestListener.java". 
The TestListener class is responsible for listening to the events that occur during the test execution and generating reports for the same. This class is implemented from the TestNG interface ITestListener, which provides methods to handle the different test events such as test start, test success, test failure, etc.

The TestListener class uses the ExtentReports library to generate detailed HTML reports for the test results. It also takes screenshots of the failed test cases and adds them to the report.



