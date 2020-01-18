package com.ProjectOne.ProjectOne;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import com.ProjectOne.ProjectOne.Utilities.BrowserFactory;
import com.ProjectOne.ProjectOne.Utilities.ExcelFactory;


public class OneTest {
	//webDriver Object declaration
	WebDriver driver = null;
	
	@BeforeTest
	public void Setup(){
		driver = BrowserFactory.getDriver("chrome");
		ExcelFactory.getWorkbook("TestData");
	}
	
	
	@Test
	public void TestCase1() throws InterruptedException {
		
		ExcelFactory.getTestDataLoaded("Sheet1", "TC_01");
		BrowserFactory.loadApplication();
		

		WebElement element = driver.findElement(By.name("q"));
		try {
			element.sendKeys(ExcelFactory.getValueOf("Search_text"));
			element.sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}
	
	@AfterTest
	public void TearUp(){
		BrowserFactory.closeDriver(driver);
	}
}

	
