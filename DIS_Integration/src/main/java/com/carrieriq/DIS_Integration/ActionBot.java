package com.carrieriq.DIS_Integration;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ActionBot {
    private static Logger LOG = Logger.getLogger(ActionBot.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public ActionBot(String browser, boolean isLocalRun) throws MalformedURLException {
        driver = WebDriverFactory.getDriver(browser,isLocalRun);
        initDriver();
    }


    private void initDriver() {
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 45);
        actions = new Actions(driver);
    }

//    public void login() {
//        LOG.info("Attempting to login to host : " + PropertiesLoader.HOST);
//        driver.get(PropertiesLoader.HOST);
//        waitForElement(By.name("submit"));
//        driver.findElement(By.name("j_username")).sendKeys(PropertiesLoader.USER);
//        driver.findElement(By.name("j_password")).sendKeys(PropertiesLoader.PASS);
//        driver.findElement(By.name("submit")).click();
//    }
//
//    void waitForLoad() {
//        ExpectedCondition<Boolean> pageLoadCondition = new
//                ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver driver) {
//                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//                    }
//                };
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(pageLoadCondition);
//    }
//
//    public void selectTrial() {
//        List<String> trials = getTrialLinks();
//        LOG.info("Trials : " + trials);
//        driver.findElement(By.linkText(PropertiesLoader.TRIAL)).click();
//    }
//
//    public List<String> getTrialLinks() {
//        waitForElement(By.id("factTable"));
//        int flowsAvailable = driver.findElements(By.xpath("//table[@id='factTable']/tbody/tr")).size();
//        List<String> links = new ArrayList<String>();
//        for (int i = 1; i <= flowsAvailable; i++) {
//            WebElement link = driver.findElement(By.xpath("//table[@id='factTable']/tbody/tr[" + i + "]/td[1]/span/a"));
//            links.add(link.getText());
//        }
//        return links;
//    }
//
//    public void moveToElement(By locator) {
//        actions.moveToElement(getElement(locator));
//        actions.perform();
//    }
//
//    public WebElement getElement(By locator) {
//        return driver.findElement(locator);
//    }
//
//    public WebElement waitForPresenceOfElementLocated(final By locator) {
//        return  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//    }
//
//    public WebElement waitForVisibilityOfElementLocated(final By locator) {
//        return  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//
//    public WebElement waitForElementToBeClickable(final By locator) {
//        return wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//
//    public WebElement waitForElement(final By locator) {
//        Wait<WebDriver> wait = new FluentWait<>(driver)
//                .withTimeout(30, TimeUnit.SECONDS)
//                .pollingEvery(5, TimeUnit.SECONDS)
//                .ignoring(NoSuchElementException.class);
//
//        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
//            public WebElement apply(WebDriver driver) {
//                return driver.findElement(locator);
//            }
//        });
//
//        return element;
//    }
//
//
//    public void waitForJQuery() {
//        (new WebDriverWait(driver,45))
//                .pollingEvery(5, TimeUnit.SECONDS)
//                .withMessage("JavaScript not NULL")
//                .ignoring(TimeoutException.class)
//                .until(new ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver d) {
//                        JavascriptExecutor js = (JavascriptExecutor) d;
//                        return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
//                    }
//                });
//    }
//
//    public void executeScript(String script) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript(script);
//    }
//
//    public ArrayList<String> getJavaScriptValues(String script) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        return  (ArrayList<String>) js.executeScript(script);
//    }
//
//
//    public void scrollIntoView (By locator) {
//        WebElement element = driver.findElement(locator);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//    }
//
//    public void clickElement(By locator) {
//        driver.findElement(locator).click();
//    }
//
//    public void clearElement(By locator) {
//        driver.findElement(locator).clear();
//    }
//
//    public boolean isDisplayed(By locator) {
//        if (driver.findElement(locator).isDisplayed())
//            return true;
//        else
//            return false;
//    }
//
//    public boolean verifyElementPresent(By locator){
//        return getElementSize(locator) > 0;
//    }
//
//    public int getElementSize(By locator) {
//        return driver.findElements(locator).size();
//    }
//
//    public void closeBrowser() {
//        driver.quit();
//    }
//
//    public void refreshPage() {
//        driver.navigate().refresh();
//    }
//
//    public String getElementText(By locator) {
//        return driver.findElement(locator).getText();
//    }
//
//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    public void hoverOver(By locator) {
//        Actions performAction = new Actions(driver);
//        performAction.moveToElement(driver.findElement(locator)).perform();
//    }
//
//    public void write(By locator, String text) {
//        clearElement(locator);
//        driver.findElement(locator).sendKeys(text);
//    }
//
//    public void select(By locator, String value ) {
//        new Select(driver.findElement(locator)).selectByVisibleText(value);
//    }
//
//    public String getFirstValueDropDown(By locator) {
//        return new Select(driver.findElement(locator)).getFirstSelectedOption().getText();
//    }
//
//    public List<String> getAllValuesDropDown(By locator) {
//        List<WebElement> allValues = new Select(driver.findElement(locator)).getOptions();
//        List<String> values = new ArrayList<String>();
//        for (WebElement value :allValues) {
//            values.add(value.getText());
//        }
//        return values;
//    }
//
//    public String getAttribute(By locator, String attribute){
//        return driver.findElement(locator).getAttribute(attribute);
//    }
//
//    public  String getAlertText () {
//        return driver.switchTo().alert().getText();
//    }
//
//    public  void acceptAlert () {
//        driver.switchTo().alert().accept();
//    }
//
//    public  void dismissAlert () {
//        driver.switchTo().alert().dismiss();
//    }
//
//    public  boolean alertIsPresent() {
//        boolean foundAlert;
//        WebDriverWait wait = new WebDriverWait(driver, 2);
//        try {
//            wait.until(ExpectedConditions.alertIsPresent());
//            foundAlert = true;
//        } catch (TimeoutException eTO) {
//            foundAlert = false;
//        }
//        return foundAlert;
//    }
//
//    public  boolean alertIsPresentNoWait() {
//        try {
//            driver.switchTo().alert();
//            return true;
//        }
//        catch (Exception e) {
//            return false;
//        }
//    }
//
//    public void checkBox(By locator) {
//        if ( !driver.findElement(locator).isSelected() )
//        {
//            driver.findElement(locator).click();
//        }
//    }
//
//    public void uncheckBox(By locator) {
//        if ( driver.findElement(locator).isSelected() )
//        {
//            driver.findElement(locator).click();
//        }
//    }


}
