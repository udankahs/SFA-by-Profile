package com.email.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GotoUnreadMail {

	private WebDriver driver;

	@FindBy(xpath = "//span[@id=('_ariaId_68')]")
	private WebElement unread;

	@FindBy(xpath = "//span[contains(text(), 'Subject Sandbox: Salesforce.com password confirmation')]/../../..")
	private WebElement FirstMail;

	@FindBy(xpath = "//span[@autoid='_o365c_4' and contains(text(), 'mark as read')]")
	private WebElement MarkAsRead;

	@FindBy(xpath = "//a[1]")
	private WebElement pswRestLink;

	@FindBy(id = "p5")
	private WebElement enterPWD;

	@FindBy(id = "p6")
	private WebElement reEnterPWD;

	@FindBy(xpath = "html/body/div[2]/div/div[2]/div[1]/div[2]/div[2]/div/div[1]/div/table/tbody/tr/td[1]/button")
	private WebElement Menu;

	@FindBy(xpath = "//span[text()='Sign out']")
	private WebElement Logout;

	@FindBy(xpath = "//input[@type=('submit') and @title=('Save')]")
	private WebElement Save;

	@FindBy(xpath = "//a")
	private WebElement Continue;
	
	@FindBy(xpath = "//div[contains(text(),'You cannot reuse this old password')]")
	private WebElement Error;
	
	public GotoUnreadMail(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public String gotoUnreadMail(String pwd, String newUser) throws InterruptedException 
	{
		unread.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);

		wait.until(ExpectedConditions.visibilityOf(FirstMail));
		FirstMail.click();
		String url = pswRestLink.getText();

		Actions action = new Actions(driver);
		action.moveToElement(FirstMail).contextClick().build().perform();

		MarkAsRead.click();
		Thread.sleep(5000);
		Menu.click();
		Logout.click();

		driver.get(url);

		String title = driver.getTitle();
		System.out.println(title);

		if (title.equals("salesforce.com - Customer Secure Login Page")) 
		{
			System.out.println("The Link Expired.");
			newUser = "NA";
		} 
		else if (title.equals("Scheduled Improvements @ salesforce.com")) 
		{
			Continue.click();
			enterPWD.sendKeys(pwd);
			reEnterPWD.sendKeys(pwd);
			Save.click();
			Thread.sleep(6000);
			if (Error.isDisplayed())
			{
				enterPWD.sendKeys("TemporaryPassword1");
				reEnterPWD.sendKeys("TemporaryPassword1");
				Save.click();
			}
			else
			{
				
			}
			
			System.out.println("Login succesfull. Password has been reset succesfully for "+ newUser);
		} 
		else if (title.equals("salesforce.com - Change Password")) 
		{
			enterPWD.sendKeys(pwd);
			reEnterPWD.sendKeys(pwd);
			Save.click();
			if (Error.isDisplayed())
			{
				enterPWD.sendKeys("TemporaryPassword1");
				reEnterPWD.sendKeys("TemporaryPassword1");
				Save.click();
			}
			else
			{
				
			}
			Thread.sleep(6000);
			System.out.println("Login succesfull. Password has been reset succesfully for "+ newUser);
			
		}
		return newUser;
	}
}
