package com.carrieriq.DIS_Integration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class RunAllTestsFactVal {

    private static Logger LOG = Logger.getLogger(RunAllTestsFactVal.class);

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
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        myWait = new WebDriverWait(driver, 45);
        driver.get(PropertiesLoader.URL);
        driver.findElement(By.id("txtUsernameLogin")).sendKeys(PropertiesLoader.USER);
        driver.findElement(By.name("txtPassword")).sendKeys(PropertiesLoader.PASS);
        driver.findElement(By.className("submit")).click();
    }

    @DataProvider
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
        driver.findElement(By.xpath("//*[@id=\"parentoo_testCaseAutofill\"]/table/tbody/tr/td[2]/input")).click();

        int index = 0;
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_factTestCaseAutofill[" + index + "]")));
        List<TestCase> resultList = new ArrayList<TestCase>();
        index = -1;
        while (true) {
            index++;
            if (driver.findElements(By.id("suggItem_factTestCaseAutofill[" + index + "]")).size() != 0) {
                String testCaseName = driver.findElement(By.id("suggItem_factTestCaseAutofill[" + index + "]")).getText();
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

        return data;
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();
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
        driver.findElement(By.id("endDate")).clear();
        driver.findElement(By.id("endTime")).clear();

        driver.findElement(By.id("startDate")).sendKeys(METRIC_SEARCH_START_DATE);
        driver.findElement(By.id("startTime")).sendKeys(METRIC_SEARCH_START_TIME);
        driver.findElement(By.id("endDate")).sendKeys(METRIC_SEARCH_END_DATE);
        driver.findElement(By.id("endTime")).sendKeys(METRIC_SEARCH_END_TIME);
    }

    @Test(dataProvider = "getDomainData")
    public void test(TestCase testCase) {
        int index = testCase.getTestCaseIndex();
        driver.findElement(By.xpath("//*[@id=\"parentoo_factTestCaseAutofill\"]/table/tbody/tr/td[2]/input")).click();
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_factTestCaseAutofill[" + index + "]")));
        driver.findElement(By.id("suggItem_factTestCaseAutofill[" + index + "]")).click();
        driver.findElement(By.id("valFactBut")).click();
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[3]/table[1]/tbody/tr/td/h1")));
        assertTrue(driver.findElement(By.xpath("/html/body/div/div[3]/table[1]/tbody/tr/td/h1")).getText().contains("Fact Validator Test Results"));

        driver.navigate().back();
    }

}
