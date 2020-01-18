package com.ProjectOne.ProjectOne.Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

public class BrowserFactory {
	//Factory to initialize driver object
	private static WebDriver driver;
	private static String SysPath;
	
	public static WebDriver getDriver(String browser){
		//Getting project's relative path
		SysPath = System.getProperty("user.dir") + "//Driver//";
		
		//Initializing the driver with passed browser string
		try{
			if(browser.toLowerCase().equals("chrome")){
				System.setProperty("webdriver.chrome.driver", SysPath + "chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			}else if(browser.toLowerCase().equals("firefox")){
				System.setProperty("webdriver.gecko.driver", SysPath + "geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			}else if(browser.toLowerCase().equals("ie")){
				System.setProperty("webdriver.ie.driver",SysPath + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
			}
			Assert.assertTrue(true,"Browser window opened.");
		}
		catch(Exception e){
			Assert.assertTrue(false, "Problem loading browser window");
		}
		//returning back the driver object
		return driver;
	}
	
	//common method for closing 
	public static void closeDriver(WebDriver driver){
		driver.close();
		try {
            driver.getTitle();
            Assert.assertFalse(false, "Browser is not closed properly.");
        } catch (Exception e) {
            Assert.assertTrue(true, "Browser window closed.");
        }
	}
			
	//Load application for the execution
	public static void loadApplication(){
		String appURL;
		try {
			appURL = ExcelFactory.getValueOf("URL");
			if(!appURL.equals("")){
				driver.get(appURL);
				Assert.assertTrue(true, "Provided URL opened in the Browser window.");
			}else{
				Assert.assertTrue(false, "Not able to find a valid application URL.");
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Facing issue while reading URL value from Data source");
			e.printStackTrace();
		}
		
		
	}
}
