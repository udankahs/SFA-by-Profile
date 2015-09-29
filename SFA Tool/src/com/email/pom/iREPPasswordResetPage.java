package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class iREPPasswordResetPage
{
	private WebDriver driver;
	
	@FindBy(id="setupLink")
	private WebElement Setup;
	
	@FindBy(id="Users_font")
	private WebElement ManageUsers;
	
	@FindBy(id="ManageUsers_font")
	private WebElement Users;
	
	@FindBy(xpath="//*[@id='topButtonRow']/input[@type=('button') and @name='edit']")
	private WebElement Edit;
	
	@FindBy(id="Email")
	private WebElement Email;
	
	@FindBy(id="new_password")
	private WebElement PasswordResetCheckbox;
	
	@FindBy(id="userNavLabel")
	private WebElement Menu;
	
	@FindBy(xpath="//a[contains(text(), 'Logout')]")
	private WebElement Logout;
	
	@FindBy(xpath="//*[@id='topButtonRow']/input[@type=('submit') and @title=('Save')]")
	private WebElement Save;
	
	public iREPPasswordResetPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}
	
	public void reset(String un, String email) throws InterruptedException
	{
		Setup.click();
		ManageUsers.click();
		Users.click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[contains(text(), '"+un+"')]")).click();
		Edit.click();
		Email.clear();
		Email.sendKeys(email);
		PasswordResetCheckbox.click();
		Save.click();
		Menu.click();
		Logout.click();
		Thread.sleep(5000);
	}
}
