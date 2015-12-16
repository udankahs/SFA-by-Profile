package com.email.pom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class gotoFieldAccebilityTest {
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

	@FindBy(xpath = "//*[@id='bodyCell']//a[text()='View by Profiles']")
	private WebElement ViewByProfiles;

	@FindBy(id = "zSelect")
	private WebElement SelectProfile;

	String profile = null;
	String rcdType = null;
	String field = null;
	String targetState = null;
	String BaselinercdType = null;
	String SourceState = null;
	String Result, Color = null;

	int baselinercdTypeIndex = 0;
	int noOfColumnsInBaselineSheet = 0;
	int passCount, failCount;

	public gotoFieldAccebilityTest(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void gotoFieldAaccebilty() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		boolean validObject;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		boolean objExists = driver.findElements(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).size() > 0;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		if (objExists) {
			driver.findElement(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).click();
			ViewByProfiles.click();
			Thread.sleep(5000);
			validObject = true;
		}

		else {
			validObject = false;
		}
		return validObject;
	}

	public void getFieldAaccebilty(String obj, String dataSheetPath, String baselineSheetPath)
			throws InterruptedException, InvalidFormatException, FileNotFoundException, IOException {

		int profileCount = ExcelLib.getRowCountofColumn(dataSheetPath, obj, 1);
		Reporter.log("<table>", true);
		for (int i = 1; i < profileCount; i++) {
			Reporter.log("<tr><td>", true);
			profile = ExcelLib.getCellValue(dataSheetPath, obj, i, 1);
			Reporter.log("<table><tr><th bgcolor='#76a1ef'>PROFILE: " + profile + "</th></tr><tr><td>", true);
			try {

				new Select(SelectProfile).selectByVisibleText(profile);
				Thread.sleep(5000);

				noOfColumnsInBaselineSheet = WorkbookFactory.create(new FileInputStream(baselineSheetPath)).getSheet(profile).getRow(0)
						.getPhysicalNumberOfCells();
				int RecordTypeCount = ExcelLib.getRowCountofColumn(dataSheetPath, obj, 0);
				for (int k = 1; k < RecordTypeCount; k++) {

					rcdType = ExcelLib.getCellValue(dataSheetPath, obj, k, 0);
					Reporter.log("<table><tr><th bgcolor='#ff6a33'><b>RECORD TYPE: " + rcdType + "</b></th></tr>", true);
					for (int l = 1; l < noOfColumnsInBaselineSheet; l++) {
						if (ExcelLib.getCellValue(baselineSheetPath, profile, 0, l).equals(rcdType)) {
							BaselinercdType = rcdType;
							baselinercdTypeIndex = l;
							break;
						} else {
						}

					}

					List<WebElement> allrcdType = driver.findElements(By.xpath("//table/tbody/tr[5]/th"));
					if (driver.findElements(By.xpath("//table/tbody/tr[5]/th[text()='" + rcdType + "']")).size() > 0) {
						WebElement option = driver
								.findElement(By.xpath("//table/tbody/tr[5]/th[text()='" + rcdType + "']"));

						if (allrcdType.contains(option)) {
							int rcdTypeIndex = allrcdType.indexOf(option);

							try {
								int fieldCount = ExcelLib.getRowCount(baselineSheetPath, profile);
								System.out.println(fieldCount);
								Reporter.log(
										"<table><tr bgcolor='#2EB8E6'><th>Fields</th><th>Source (Baseline Excel)  </th><th>Target (Application)  </th><th>Result</th></tr>");

								passCount = 0;
								failCount = 0;
								for (int j = 1; j < fieldCount; j++) {
									field = ExcelLib.getCellValue(baselineSheetPath, profile, j, 0);
									try {
										String xpath=null;
										driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
										boolean DevEdition = driver.findElements(By.xpath("//title[contains(text(),'Developer Edition')]")).size() > 0;
										driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
										if (DevEdition)
										{
											xpath="//div[@style='display: block;']//th[text()='" + field + "']/../td[" + rcdTypeIndex + "]/a";
										}
										else{
											xpath="//th[text()='" + field + "']/../td[" + rcdTypeIndex + "]/a";
										}
									
										targetState = driver.findElement(By.xpath(xpath)).getText();

										SourceState = ExcelLib.getCellValue(baselineSheetPath, profile, j,
												baselinercdTypeIndex);
										if (SourceState.equals(targetState)) {
											Result = "PASS";
											Color = "green";
											passCount++;
										} else {
											Result = "FAIL";
											Color = "red";
											failCount++;
										}

										Reporter.log("<tr><th bgcolor='gray'><b>" + field + "</b></th><td> "
												+ SourceState + "</td><td>" + targetState + "</td><th> <font color='"
												+ Color + "'><b> " + Result + " </b></font><br/></th></tr>", true);

									} catch (NoSuchElementException e) {
										Reporter.log(
												"<table><tr><th><font color='red'><b>ERROR: </b></th><td> Field ("
														+ field + ") not found in the Application </td></tr></table>",
												true);
									}
								}

								Reporter.log("</tr></table></br>");

								Reporter.log("<table><tr><th bgcolor='green' >Pass Count : " + passCount + "</th><th bgcolor='red'>Fail Count : "
										+ failCount + "</th></tr></table></br>");

							} catch (NullPointerException e) {
								Reporter.log("", true);
								Reporter.log(
										"<table><tr><th>	</th><th><font color='red'><b>ERROR: </b></th><td> Sheet ("
												+ profile
												+ ") not found in the Data Sheet for the selected Object </td></tr></table>",
										true);
								Reporter.log("</td></tr></table>", true);
							}
						}
					}

					else {
						Reporter.log("", true);
						Reporter.log("<table><tr><th><font color='red'><b>ERROR: </b></th><td> Record Tpe ("
								+ rcdType + ") not found in the Application </td></tr></table>", true);
						Reporter.log("</td></tr></table>", true);

					}
				}

			} catch (NoSuchElementException e) {
				Reporter.log("", true);
				Reporter.log("<table><tr><th><font color='red'><b>ERROR: </b></th><td> Profile ("
						+ profile + ") not found in the Application </td></tr></table></br>", true);
				Reporter.log("</td></tr></table>", true);
			}
		}
		Reporter.log("</table>", true);
	}
}
