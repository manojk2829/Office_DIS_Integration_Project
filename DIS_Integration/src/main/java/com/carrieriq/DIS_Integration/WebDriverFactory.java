package com.carrieriq.DIS_Integration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WebDriverFactory {

    public static WebDriver getDriver(String browser, boolean isLocalRun) throws MalformedURLException {
        /*if(browser.equalsIgnoreCase(Constants.FIREFOX)) {
            File file = new File(PropertiesLoader.GECKO_DRIVER_PATH);
            System.setProperty(Constants.WEBDRIVER_GECKO_DRIVER, file.getAbsolutePath());
            return getLocalDriver(Constants.FIREFOX);
        }
        if (browser.equalsIgnoreCase(Constants.INTERNET_EXPLORER)) {
            File file = new File(PropertiesLoader.IE_DRIVER_PATH);
            System.setProperty(Constants.WEBDRIVER_IE_DRIVER, file.getAbsolutePath());
        }*/
    	
    	if(browser.equalsIgnoreCase(Constants.CHROME)) {
            File file = new File(PropertiesLoader.CHROME_DRIVER_PATH);
            System.setProperty(Constants.WEBDRIVER_CHROME_DRIVER, file.getAbsolutePath());
            return getLocalDriver(Constants.CHROME);
        }
    	
    	
    	
        if (isLocalRun)
            return getLocalDriver(browser);
        else
            return getRemoteDriver(browser);
    }

    private static WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        URL hubUrl = new URL(PropertiesLoader.HUB_HOST);
        if (browser.equalsIgnoreCase(Constants.INTERNET_EXPLORER)) {
            return new RemoteWebDriver(hubUrl, DesiredCapabilities.internetExplorer());
        }
        else
            return new RemoteWebDriver(hubUrl, DesiredCapabilities.firefox());
    }

    private static WebDriver getLocalDriver(String browser) {
        if (browser.equalsIgnoreCase(Constants.INTERNET_EXPLORER)) {
            return new InternetExplorerDriver();
        }
        else
            return new FirefoxDriver();
    }
}
