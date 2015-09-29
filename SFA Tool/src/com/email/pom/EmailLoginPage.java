package com.email.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailLoginPage
{
	private WebDriver driver;
	
	@FindBy(id="input_1")
	private WebElement unTextBox;
	
	@FindBy(id="input_2")
	private WebElement pwTextBox;
	
	@FindBy(xpath="//input[@class='credentials_input_submit']")
	private WebElement loginButton;
	
	public EmailLoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}
	
	public void login(String un,String pw)
	{
		driver.get("https://email.abbvie.com");
		unTextBox.sendKeys(un);
		pwTextBox.sendKeys(pw);
		loginButton.click();
	}
	
}
