package com.email.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class iREPPasswordResetProfile
{
	private WebDriver driver;
	
	@FindBy(id="userNavLabel")
	private WebElement userNavLabel;
	
	@FindBy(id="setupLink")
	private WebElement Setup;
	
	@FindBy(id="Users_font")
	private WebElement ManageUsers;
	
	@FindBy(id="EnhancedProfiles_font")
	private WebElement Profiles;
	
	@FindBy(xpath="//td[@id='topButtonRow']/input[@title='View Users']")
	private WebElement ViewUsers;
	
	@FindBy(xpath="//*[@id='ResetForm']/div[2]/table/tbody/tr[2]/td[6]/img")
	private WebElement ActiveCheckBox;
	
	@FindBy(xpath="//*[@id='ResetForm']/div[2]/table/tbody/tr[2]/td[1]/a[contains(text(),'Edit')]")
	private WebElement ActiveUserEdit;
	
	@FindBy(xpath=".//*[@id='ResetForm']/div[2]/table/tbody/tr[1]/th[7]/a")
	private WebElement ActiveHeader;
	
	@FindBy(id="Email")
	private WebElement Email;
	
	@FindBy(id="Username")
	private WebElement Username;
	
	@FindBy(id="new_password")
	private WebElement PasswordResetCheckbox;
	
	@FindBy(xpath="//a[contains(text(), 'Logout')]")
	private WebElement Logout;
	
	@FindBy(xpath="//*[@id='topButtonRow']/input[@type=('submit') and @title=('Save')]")
	private WebElement Save;
	
	@FindBy(xpath="//*[@id='topButtonRow']/input[@value='Cancel']")
	private WebElement Cancel;
	
	String username;
	
	public iREPPasswordResetProfile(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}
	
	public String reset(String profName, String email) throws InterruptedException
	{
		String title = driver.getTitle();
		
		if (title.equals("salesforce.com - Unlimited Edition")){
		
		//userNavLabel.click();
		Setup.click();
		ManageUsers.click();
		Profiles.click();
		driver.findElement(By.xpath("//span[contains(text(), '"+profName+"')]")).click();
		ViewUsers.click();
		Thread.sleep(5000);
		
		int r=2;
		do{
			String xpath1 = "//*[@id='ResetForm']/div[2]/table/tbody/tr["+r+"]/td[6]/img";
			driver.findElement(By.xpath(xpath1)).click();
			
			if(driver.findElement(By.xpath("//*[@id='ResetForm']/div[2]/table/tbody/tr["+r+"]/td[6]/img")).getAttribute("title").equals("Checked"))
			{
				driver.findElement(By.xpath("//*[@id='ResetForm']/div[2]/table/tbody/tr["+r+"]/td[1]/a[contains(text(),'Edit')]")).click();
//				if (Email.getAttribute("value").equalsIgnoreCase("udanka.h.satyanarayana@abbvie.com"))					
//				{
//					Cancel.click();
//					r++;
//				}
				if (Email.getAttribute("value").equalsIgnoreCase("mraj@abbvie.com") || Email.getAttribute("value").equalsIgnoreCase("suhas.oak@abbvie.com"))					
				{
					Cancel.click();
					r++;
				}
				else
				{
					Email.clear();
					Email.sendKeys(email);
					username = Username.getAttribute("value");
					PasswordResetCheckbox.click();
					Save.click();
					Thread.sleep(6000);
					
					userNavLabel.click();
					Logout.click();
					Thread.sleep(5000);
					r=9;
				}
			}
			else
			{
				ActiveHeader.click();
			}
		}
		while (r<9);
		//System.out.println(username);
		}
		else if (title.equals("salesforce.com - Customer Secure Login Page"))
		{
			username="NA";
			driver.quit();
		}
		return username;
	}
}
