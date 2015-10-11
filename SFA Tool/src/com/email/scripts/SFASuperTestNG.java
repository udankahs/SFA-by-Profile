package com.email.scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.lib.ExcelLib;

public class SFASuperTestNG 
{
	public WebDriver driver;
	
	@BeforeMethod
	public void preCondition()
	{
		String browser = ExcelLib.getCellValue("D:/SFA Selenium Utility/Data Sheet/Data Sheet.xls", "Login", 1, 3);
		
		if (browser.equals("Firefox"))
		{
				driver=new FirefoxDriver();
		}
		else if (browser.equals("Internet Explorer"))
		{
			System.setProperty("webdriver.ie.driver", "D:/SFA Selenium Utility/Browser Drivers/IEDriverServer_64.exe");
			driver=new InternetExplorerDriver();
		}
		
//		else if (browser.equals("Internet Explorer_64 Bit"))
//		{
//			System.setProperty("webdriver.ie.driver", "D:/SFA Selenium Utility/Browser Drivers/IEDriverServer_32.exe");
//			driver=new InternetExplorerDriver();
//		}
		else if (browser.equals("Chrome"))
		{
			System.setProperty("webdriver.ie.driver", "D:/SFA Selenium Utility/Browser Drivers/chromedriver.exe");
			driver=new InternetExplorerDriver();
		}
		
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	@AfterMethod
	public void postCondition()
	{
		driver.quit();
		
//		driver.get("file:///C:/Users/udankahs/git/SFA%20Tool/SFA%20Tool/test-output/index.html#");
//	   //driver.get("file:///D:/SFA%20Selenium%20Utility/test-output/index.html#");
//	   driver.findElement(By.xpath("//span[text()='Reporter output']")).click();
	}
}
