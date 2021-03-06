package com.carrieriq.DIS_Integration;import static org.testng.Assert.assertTrue;import org.openqa.selenium.Alert;import org.openqa.selenium.By;import org.testng.annotations.AfterMethod;import org.testng.annotations.BeforeMethod;import org.testng.annotations.Test;public class ValidatePackage extends BaseTest{    @BeforeMethod    public void setUp() throws Exception {    	openBro(pro.getProperty("Browser"));    	navigate(pro.getProperty("URL"));        //driver.findElement(By.id("txtUsernameLogin")).sendKeys(PropertiesLoader.USER);        //driver.findElement(By.name("txtPassword")).sendKeys(PropertiesLoader.PASS);        //driver.findElement(By.className("submit")).click();    }    @Test    public void testTestSummaryForNoMappings() throws Exception {        //click on "Metric/Trigger Search" tab        driver.findElement(By.linkText("Metric/Trigger Search")).click();        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)        setStarEndDatesAndTime();        //click on '*' of triggers, then find trigger UPTR        //driver.findElement(By.xpath("//span[@id='parentoo_triggersAutofill']/table/tbody/tr/td[2]/input")).click();        selectValueFromTriggerSearch(METRIC_SEARCH_TRIGGER_1);        driver.findElement(By.name("btnSubmit")).click();        driver.findElement(By.id("selectPkgsAllCheckBox")).click();        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        selectValueForTestCaseSearch(TEST_TO_BE_VALIDATE_WITH_1);        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        assertTrue(driver.findElement(By.xpath("//div[@class='val_list']/table/tbody/tr/td[1]")).getText()                .contains("Run date"));        assertTrue(driver.findElement(By.xpath("//div[@class='content']/table/tbody/tr/td/html/h1[2]")).getText()                .contains("Test Summary"));    }    //T_01 - Verify that the user can get to package Validation from search results    @Test    public void testPackageValidationFromSearch() throws Exception {        //click on "Metric/Trigger Search" tab        Thread.sleep(5000);        driver.findElement(By.linkText("Metric/Trigger Search")).click();        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)        setStarEndDatesAndTime();        selectValueFromTriggerSearch(METRIC_SEARCH_TRIGGER_1);        driver.findElement(By.name("btnSubmit")).click();        driver.findElement(By.id("selectPkgsAllCheckBox")).click();        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        assertTrue(driver.findElement(By.xpath("//div[@class='content']/table/tbody/tr/td/h1")).getText()                .contains("Package Validation Confirm"));    }    //T_02 - Verify the functionality of Download reports    @Test    public void testPackageValidationForResults() throws Exception {        //click on "Metric/Trigger Search" tab        driver.findElement(By.linkText("Metric/Trigger Search")).click();        //after content is loaded, type mdn, start/end date/time in text fields, then click on submit (Search)        setStarEndDatesAndTime();        //click on '*' of triggers, then find trigger UPTR        selectValueFromTriggerSearch(METRIC_SEARCH_TRIGGER_1);        driver.findElement(By.name("btnSubmit")).click();        driver.findElement(By.id("selectPkgsAllCheckBox")).click();        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        selectValueForTestCaseSearch(TEST_TO_BE_VALIDATE_WITH_1);        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        assertTrue(driver.findElement(By.xpath("//div[@class='val_list']/table/tbody/tr/td[1]")).getText()                .contains("Run date"));        driver.findElement(By.xpath("//input[@value='Download reports']")).click();        Alert myAlert = driver.switchTo().alert(); //Switch to alert pop-up        myAlert.accept(); //accept the alert - equivalent of pressing OK    }    //T_02 - Verify that the Test Summary details are present in page if there are no mapped packages    //T_03 - Verify that the Test Summary details are present in page if there are mapped packages    @Test    public void testTestSummaryForMappings() throws Exception {        driver.findElement(By.linkText("Metric/Trigger Search")).click();        setStarEndDatesAndTime();        selectValueFromTriggerSearch(METRIC_SEARCH_TRIGGER_5);        driver.findElement(By.name("btnSubmit")).click();        Thread.sleep(5000);        driver.findElement(By.id("selectPkgsAllCheckBox")).click();        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        selectValueForTestCaseSearch(TEST_TO_BE_VALIDATE_WITH_2);        driver.findElement(By.xpath("//input[@value='Validate Package(s)']")).click();        driver.findElement(By.xpath("/html/body/div/div[3]/table[2]/tbody/tr/td/html/div[4]/table/tbody/tr/td/table/tbody/tr[1]/td[10]/a")).click();        assertTrue(driver.findElement(By.xpath("/html/body/div/div[3]/table[2]/tbody/tr/td/html/h1[2]")).getText()                .contains("Package summary"));       }        @AfterMethod    public void tearDown() {        driver.quit();    }           //@Parameters({"url", "user", "password"})    //@Parameters({"browser","local_run"})    /*public void setUp(String browser, Boolean isLocalRun) throws Exception {        driver = WebDriverFactory.getDriver(browser,isLocalRun);        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);        driver.manage().window().setSize(new Dimension(1920, 1080));        myWait = new WebDriverWait(driver, 45);        driver.get(PropertiesLoader.URL);        driver.findElement(By.id("txtUsernameLogin")).sendKeys(PropertiesLoader.USER);        driver.findElement(By.name("txtPassword")).sendKeys(PropertiesLoader.PASS);        driver.findElement(By.className("submit")).click();    }*/}