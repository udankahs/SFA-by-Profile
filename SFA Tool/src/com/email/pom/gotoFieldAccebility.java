package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class gotoFieldAccebility
{
	private WebDriver driver;
	
	@FindBy(id="setupLink")
	private WebElement Setup;
	
	@FindBy(id="userNavLabel")
	private WebElement UserName;
	
	@FindBy(xpath="//div[@id='userNav-menuItems']/a[contains (text(), 'Setup')]")
	private WebElement Setup2;
	
	@FindBy(id="Security_icon")
	private WebElement Security;
	
	@FindBy(id="FieldAccessibility_font")
	private WebElement FieldAccessibility;
	
	@FindBy(xpath="//*[@id='bodyCell']//a[text()='View by Record Types']")
	private WebElement ViewByRecordTypes;
	
	@FindBy(id= "zSelect")
	private WebElement RecordType;
	
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
	
	public gotoFieldAccebility(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}
	
	public void gotoFieldAaccebilty(String Obj, String rcdType) throws InterruptedException
	{
		
		if(driver.findElements(By.id("setupLink")).size()!=0)
		{
			Setup.click();
		}
		else
		{
			UserName.click();
			Setup2.click();
		}
		
		Security.click();
		FieldAccessibility.click();
		driver.findElement(By.xpath("//*[@id='bodyCell']/ul//a[text()='"+Obj+"']")).click();
		ViewByRecordTypes.click();
		Thread.sleep(5000);
		
		new Select(RecordType).selectByVisibleText(rcdType);
		Thread.sleep(5000);
	}
}
