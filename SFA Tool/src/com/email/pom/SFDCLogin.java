package com.email.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SFDCLogin
{
	private WebDriver driver;
	
	@FindBy(id="username")
	private WebElement unTextBox;
	
	@FindBy(id="password")
	private WebElement pwTextBox;
	
	@FindBy(id="Login")
	private WebElement loginButton;
	
	public SFDCLogin(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}
	
	public void login(String un,String pw) throws InterruptedException
	{
		driver.get("https://test.salesforce.com");
		unTextBox.sendKeys(un);
		pwTextBox.sendKeys(pw);
		loginButton.click();
	}
}
