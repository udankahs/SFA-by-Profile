package com.email.scripts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.lib.ExcelLib;

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: EAS CRM
 * Organization		: Cognizant Technology Solutions
 */

public class SFASuperTestNG {
	public WebDriver driver;

	@BeforeMethod
	public void preCondition() throws UnsupportedEncodingException {
		String JarPath = GetFieldsAcceble.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String folderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
		String decodedPath = URLDecoder.decode(folderPath, "UTF-8");

		String browser = ExcelLib.getCellValue(decodedPath + "/Data Sheet/Data Sheet.xls", "Login", 1, 3);

		if (browser.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Internet Explorer")) {
			System.setProperty("webdriver.ie.driver", "D:/SFA Selenium Utility/Browser Drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.ie.driver", "D:/SFA Selenium Utility/Browser Drivers/chromedriver.exe");
			driver = new InternetExplorerDriver();
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void postCondition() {
		driver.quit();
	}
}
