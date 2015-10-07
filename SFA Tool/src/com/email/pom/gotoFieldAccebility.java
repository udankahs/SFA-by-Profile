package com.email.pom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.lib.ExcelLib;

public class gotoFieldAccebility {
	private WebDriver driver;

	@FindBy(id = "setupLink")
	private WebElement Setup;

	@FindBy(id = "userNavLabel")
	private WebElement UserName;

	@FindBy(xpath = "//div[@id='userNav-menuItems']/a[contains (text(), 'Setup')]")
	private WebElement Setup2;

	@FindBy(id = "Security_icon")
	private WebElement Security;

	@FindBy(id = "FieldAccessibility_font")
	private WebElement FieldAccessibility;

	@FindBy(xpath = "//*[@id='bodyCell']//a[text()='View by Record Types']")
	private WebElement ViewByRecordTypes;

	@FindBy(id = "zSelect")
	private WebElement RecordType;

	@FindBy(xpath = "//*[@id='topButtonRow']/input[@type=('button') and @name='edit']")
	private WebElement Edit;

	@FindBy(id = "Email")
	private WebElement Email;

	@FindBy(id = "new_password")
	private WebElement PasswordResetCheckbox;

	@FindBy(id = "userNavLabel")
	private WebElement Menu;

	@FindBy(xpath = "//a[contains(text(), 'Logout')]")
	private WebElement Logout;

	@FindBy(xpath = "//*[@id='topButtonRow']/input[@type=('submit') and @title=('Save')]")
	private WebElement Save;

	String profile = null;
	String rcdType = null;
	String field = null;
	String targetState = null;
	String BaselineProfile = null;
	String SourceState = null;

	int baselineProfileIndex = 0;
	int noOfColumns = 0;

	public gotoFieldAccebility(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void gotoFieldAaccebilty() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean setupLinkExists = driver.findElements(By.id("setupLink")).size() > 0;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		if (setupLinkExists) {
			Setup.click();
		} else {
			UserName.click();
			Setup2.click();
		}

		Security.click();
		FieldAccessibility.click();
	}

	public boolean gotoObjAaccebilty(String Obj) throws InterruptedException {
		boolean reached;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean objExists = driver.findElements(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).size() > 0;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		if (objExists) {
			driver.findElement(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).click();
			ViewByRecordTypes.click();
			Thread.sleep(5000);
			reached = true;
		}

		else {
			Reporter.log("Object :(" + Obj + ")not found in the Application", true);
			reached = true;
		}
		return reached;
	}

	public void getFieldAaccebilty(String obj, String objxlPath, String fieldXlpath)
			throws InterruptedException, InvalidFormatException, FileNotFoundException, IOException {

		int recTypeCount = ExcelLib.getRowCount(objxlPath, obj);
		System.out.println("recTypeCount :" + recTypeCount);
		for (int i = 1; i <= recTypeCount; i++) {
			rcdType = ExcelLib.getCellValue(objxlPath, obj, i, 0);
			try {

				new Select(RecordType).selectByVisibleText(rcdType);
				Thread.sleep(5000);

				try{
				noOfColumns = WorkbookFactory.create(new FileInputStream(fieldXlpath)).getSheet(rcdType).getRow(0)
						.getPhysicalNumberOfCells();
				System.out.println("noOfColumns :" + noOfColumns);
				}
				catch (NullPointerException e)
				{
					System.out.println("Invalid Record Type");
				}
				for (int k = 1; k <= recTypeCount; k++) {

					profile = ExcelLib.getCellValue(objxlPath, obj, k, 1);

					try{
					for (int l = 0; l <= noOfColumns; l++) {
						if (ExcelLib.getCellValue(fieldXlpath, rcdType, 0, l).equals(profile)) {
							BaselineProfile = profile;
							baselineProfileIndex = l;
							break;
						}
					}}
					catch (NullPointerException e)
					{
						Reporter.log("Invalid Profile", true);
					}

					System.out.println("BaselineProfile :" + BaselineProfile);
					System.out.println("baselineProfileIndex :" + baselineProfileIndex);

					List<WebElement> allProfile = driver.findElements(By.xpath("//table/tbody/tr[5]/th"));
					if (driver.findElements(By.xpath("//table/tbody/tr[5]/th[text()='" + profile + "']")).size() > 0) {
						WebElement option = driver
								.findElement(By.xpath("//table/tbody/tr[5]/th[text()='" + profile + "']"));

						System.out.println("option " + option);

						if (allProfile.contains(option)) {
							int profileIndex = allProfile.indexOf(option);
							System.out.println("ProfileIndex " + profileIndex);
							try {
								int fieldCount = ExcelLib.getRowCount(fieldXlpath, rcdType);

								System.out.println("rowcount " + fieldCount);

								for (int j = 1; j <= fieldCount; j++) {
									field = ExcelLib.getCellValue(fieldXlpath, rcdType, j, 0);
									try {
										targetState = driver
												.findElement(By.xpath(
														"//th[text()='" + field + "']/../td[" + profileIndex + "]/a"))
												.getText();

										SourceState = ExcelLib.getCellValue(fieldXlpath, rcdType, j,
												baselineProfileIndex);

										System.out.println("field ("+field + ") : SourceState ("+SourceState+") : targetState(" + targetState+")");
										
										if (SourceState.equals(targetState))
										{
											Reporter.log("PASS", true);
										}
										else
										{
											Reporter.log("FAIL", true);
										}
										
									} catch (NoSuchElementException e) {
										Reporter.log("HTML report for field (" + field + ") not exist", true);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							} catch (NullPointerException e) {
								Reporter.log("HTML report for sheet (" + rcdType + ") not exist", true);
							}
						}
					}

					else {
						Reporter.log("HTML Report for Profile (" + profile + ") not exist", true);
					}
				}

			} catch (NoSuchElementException e) {
				Reporter.log("HTML Report for Record Type (" + rcdType + ")not found", true);
			}
		}
	}
}
