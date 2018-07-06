package com.carrieriq.DIS_Integration;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseTest {
	public static final String METRIC_SEARCH_START_DATE = "10.01.1999";
	public static final String METRIC_SEARCH_START_TIME = "00:00";
	public static final String METRIC_SEARCH_END_DATE = "01.01.2016";
	public static final String METRIC_SEARCH_END_TIME = "23:59";
	
	public static final String TEST_TO_BE_VALIDATE_WITH_1 = "7_104_MT_VoLTE_Call";
	public static final String METRIC_SEARCH_TRIGGER_1 = "UPTR";
	public static final String METRIC_SEARCH_TRIGGER_5 = "TION";
	public static final String METRIC_SEARCH_TRIGGER_2 = "GS01";
	public static final String METRIC_SEARCH_TRIGGER_3 = "GS02";
	public static final String METRIC_SEARCH_TRIGGER_4 = "GS03";
	public static final String TEST_TO_BE_VALIDATE_WITH_2 = "7_90_Removable_Battery";
	
	public WebDriver driver;
	public WebDriverWait myWait;
	public Properties pro;
	public Logger log=Logger.getLogger(BaseTest.class.getName());
	
	public BaseTest(){
		init();
	    PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\main\\java\\config\\Log4j_Pro.properties");
	}

     public void init(){
		if(pro==null){
			pro=new Properties();
			try{
				FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\config\\or.properties");
			    pro.load(fis);
			   }
			catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		System.out.println(pro.getProperty("Browser"));
      }
	
     public void wait(int s) {
    	 try {
    		 Thread.sleep(s*1000);
    	 }catch(Exception ex){
    		 System.out.println(ex.getMessage());
    	 }
     }
     
	public void openBro(String b){
		if(b.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "D://MANOJ//chromedriver.exe");
			driver=new ChromeDriver();
		}else{
			driver=new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1920, 1080));
        myWait = new WebDriverWait(driver, 45);
		log.info("URL entered in Browser -- > " + pro.getProperty("Browser"));
	}
	
	public void navigate(String url){
		driver.get(url);
		log.info("URL entered in Browser -- > " + pro.getProperty("URL"));
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
    public void selectValueFromTriggerSearch(String value) throws Exception {
        driver.findElement(By.id("textInput_triggersAutofill")).sendKeys(value);
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_triggersAutofill[0]")));
        String trigger;
        int index = -1;
        boolean found = true;
        while (found) {
            index++;
            trigger = driver.findElement(By.id("suggItem_triggersAutofill[" + index + "]")).getText();
            if (trigger.startsWith(value)) {
                found = false;
            }
        }
        WebElement element = driver.findElement(By.id("suggItem_triggersAutofill[" + index + "]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
        //driver.findElement(By.id("suggItem_triggersAutofill[" + index + "]")).click();
    }

    public void selectValueForTestCaseSearch(String value) throws Exception {
        driver.findElement(By.id("textInput_testCaseAutofill")).sendKeys(value);
        myWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suggItem_testCaseAutofill[0]")));
        int index = -1;
        boolean found = true;
        while (found) {
            index++;
            String trigger = driver.findElement(By.id("suggItem_testCaseAutofill[" + index + "]")).getText();
            if (trigger.startsWith(value)) {
                found = false;
            }
        }
        driver.findElement(By.id("suggItem_testCaseAutofill[" + index + "]")).click();
    }


    public void setStarEndDatesAndTime() throws Exception {
        driver.findElement(By.id("startDate")).clear();
        driver.findElement(By.id("startTime")).clear();
        driver.findElement(By.id("endTime")).clear();
        driver.findElement(By.id("startDate")).sendKeys(METRIC_SEARCH_START_DATE);
        driver.findElement(By.id("startTime")).sendKeys(METRIC_SEARCH_START_TIME);
        driver.findElement(By.id("endTime")).sendKeys(METRIC_SEARCH_END_TIME);
    }
	
/*	public void screenshot(){
		Date d=new Date();
		String FN=d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		File srcFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(srcFile, new File(System.getProperty("user.dir")+"report"+FN));
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		log.info("Screen shot capture done.");
		//test.log(LogStatus.INFO, "Screenshot taken -- > " + test.addScreenCapture(System.getProperty("user.dir")+"report"+FN));
		System.out.println("Screen shot Done .....");
	}
*/}
