package com.email.scripts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.lib.ExcelLib;

public class SFA_Main {
	public static void main(String[] args) throws UnsupportedEncodingException {

		WebDriver driver = null;

		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { GetFieldsAcceble.class });
		testng.addListener(tla);
		testng.run();

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
		driver.manage().window().maximize();
		driver.get("file://" + decodedPath + "test-output/index.html");
		driver.findElement(By.xpath("//span[text()='Reporter output']")).click();
	}
}