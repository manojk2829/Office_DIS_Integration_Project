package com.carrieriq.DIS_Integration;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

//import org.openqa.selenium.remote.RemoteWebDriver;
//import java.net.URL;

public class RunAllTestsPKGVal {
    private static Logger LOG = Logger.getLogger(RunAllTestsPKGVal.class);

    static final String METRIC_SEARCH_START_DATE = "10.01.2000";
    static final String METRIC_SEARCH_START_TIME = "00:00";
    static final String METRIC_SEARCH_END_DATE = "01.01.2016";
    static final String METRIC_SEARCH_END_TIME = "23:59";
    static final String METRIC_SEARCH_TRIGGER_2 = "GS01";
    static final String METRIC_SEARCH_TRIGGER_3 = "GS02";
    static final String METRIC_SEARCH_TRIGGER_4 = "GS03";

    private WebDriver driver;
    private WebDriverWait myWait;

    @BeforeSuite
    @Parameters({"browser","local_run"})
    public void setUp(String browser, Boolean isLocalRun) throws Exception {
        driver = WebDriverFactory.getDriver(browser,isLocalRun);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        myWait = new WebDriverWait(driver, 45);
        driver.get(PropertiesLoader.URL);
        driver.findElement(By.id("txtUsernameLogin")).sendKeys(PropertiesLoader.USER);
        driver.findElement(By.name("txtPassword")).sendKeys(PropertiesLoader.PASS);
        driver.findElement(By.className("submit")).click();

    }

    @DataProvider(name="getDomainData")
    public Object[][] getDomainData() throws Exception {
        driver.findElement(By.linkText("Metric/Trigger Search")).click();
        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)
        setStarEndDatesAndTime();
        //Set Metric GS01
        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_2);
        //Set Metric GS02
        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_3);
        //Set Metric GS03
        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_4);
        driver.findElement(By.name("btnSubmit")).click();
        driver.findElement(By.id("selectPkgsAllCheckBox")).click();
        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")));
        driver.findElement(By.xpath("//span[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")).click();

        int index = 0;
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_testCaseAutofill[" + index + "]")));
        List<TestCase> resultList = new ArrayList<TestCase>();
        index = -1;
        while (true) {
            index++;
            if (driver.findElements(By.id("suggItem_testCaseAutofill[" + index + "]")).size() != 0) {
                String testCaseName = driver.findElement(By.id("suggItem_testCaseAutofill[" + index + "]")).getText();
                TestCase testCase = new TestCase(testCaseName, index);
                resultList.add(testCase);
                LOG.info(testCaseName + " " + index);
            } else {
                break;
            }
        }
        int testCaseCount = resultList.size();
        Object[][] data = new Object[testCaseCount][1];
        for (int i = 0; i < testCaseCount; i++) {
            data[i] = new Object[]{resultList.get(i)};
        }
        //driver.navigate().refresh();
        //driver.switchTo().alert().accept();

        return data;

    }


    public void selectValueForMetricSearch(String value) throws Exception {
        driver.findElement(By.id("textInput_metricsAutofill")).sendKeys(value);
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_metricsAutofill[0]")));
        String trigger;
        int index = -1;
        boolean found = true;

        while (found) {
            index++;
            trigger = driver.findElement(By.id("suggItem_metricsAutofill[" + index + "]")).getText();
            if (trigger.startsWith(value)) {
                found = false;
            }
        }

        driver.findElement(By.id("suggItem_metricsAutofill[" + index + "]")).click();
    }

    public void setStarEndDatesAndTime() throws Exception {
        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)
        driver.findElement(By.id("startDate")).clear();
        driver.findElement(By.id("startTime")).clear();
//        driver.findElement(By.id("endDate")).clear();
        driver.findElement(By.id("endTime")).clear();

        driver.findElement(By.id("startDate")).sendKeys(METRIC_SEARCH_START_DATE);
        driver.findElement(By.id("startTime")).sendKeys(METRIC_SEARCH_START_TIME);
//        driver.findElement(By.id("endDate")).sendKeys(METRIC_SEARCH_END_DATE);
        driver.findElement(By.id("endTime")).sendKeys(METRIC_SEARCH_END_TIME);
    }

    @Test(dataProvider = "getDomainData")
    public void test(TestCase testCase) throws Exception {
        int index = testCase.getTestCaseIndex();
        driver.findElement(By.xpath("//*[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")).click();
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_testCaseAutofill[" + index + "]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("suggItem_testCaseAutofill[" + index + "]")));
        WebElement div = driver.findElement((By.id("suggDiv_testCaseAutofill")));
        for(int i=0; i<index; i++)
        {
            div.sendKeys(Keys.DOWN);
        }
        div.sendKeys(Keys.ENTER);
        //driver.findElement(By.id("suggItem_testCaseAutofill[" + index + "]")).click();

        //---------------
        WebElement ValidatePackagesButton=driver.findElement(By.xpath("//input[@value='Validate Package(s)']"));
        myWait.until(ExpectedConditions.visibilityOf(ValidatePackagesButton));
        //new Actions(driver).moveToElement((ValidatePackagesButton)).perform();
        ValidatePackagesButton.click();
        //myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[3]/table[1]/tbody/tr/td/h1")));
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Download reports']")));
//        driver.findElement(By.xpath("//input[@value='Download reports']")).click();
//        /html/body/div/div[3]/table[1]/tbody/tr/td/h1
//        assertTrue(driver.findElement(By.xpath("/html/body/div/div[3]/table[1]/tbody/tr/td/h1")).getText().contains("Package Validator Test Results"));
        assertTrue(driver.getPageSource().contains("Environment"));
        assertTrue(driver.findElement(By.xpath("/html/body/div/div[3]/table[2]/tbody/tr/td/html/div[1]/table/tbody/tr[1]/td[1]")).getText()
                .contains("Run date"));
        //driver.navigate().back();
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

    @AfterMethod
    public void tearDownTest()
    {
        if (!driver.getPageSource().contains("Package Validation Confirm"))
        {
            driver.navigate().back();
        }

    }
//    @AfterMethod
//    public void tearDownTest() throws Exception {
//        driver.findElement(By.linkText("Metric/Trigger Search")).click();
//        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)
//        setStarEndDatesAndTime();
//        //Set Metric GS01
//        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_2);
//        //Set Metric GS02
//        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_3);
//        //Set Metric GS03
//        selectValueForMetricSearch(METRIC_SEARCH_TRIGGER_4);
//        driver.findElement(By.name("btnSubmit")).click();
//        driver.findElement(By.id("selectPkgsAllCheckBox")).click();
//        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();
//        myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")));
//        driver.findElement(By.xpath("//span[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")).click();
//    }
}
