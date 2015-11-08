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

	String profile = null;
	String rcdType = null;
	String field = null;
	String targetState = null;
	String BaselineProfile = null;
	String SourceState = null;
	String Result, Color = null;

	int baselineProfileIndex = 0;
	int noOfColumns = 0;
	int passCount, failCount;

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
		boolean validObject;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean objExists = driver.findElements(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).size() > 0;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		if (objExists) {
			driver.findElement(By.xpath("//*[@id='bodyCell']/ul//a[text()='" + Obj + "']")).click();
			ViewByRecordTypes.click();
			Thread.sleep(5000);
			validObject = true;
		}

		else {
			validObject = false;
		}
		return validObject;
	}

	public void getFieldAaccebilty(String obj, String objxlPath, String fieldXlpath)
			throws InterruptedException, InvalidFormatException, FileNotFoundException, IOException {

		int recTypeCount = ExcelLib.getRowCountofColumn(objxlPath, obj, 0);
		Reporter.log("<table>", true);
		for (int i = 1; i < recTypeCount; i++) {
			Reporter.log("<tr><td>", true);
			rcdType = ExcelLib.getCellValue(objxlPath, obj, i, 0);
			Reporter.log("<table><tr><th>RECORD TYPE: " + rcdType + "</th></tr><tr><td>", true);
			try {

				new Select(RecordType).selectByVisibleText(rcdType);
				Thread.sleep(5000);

				noOfColumns = WorkbookFactory.create(new FileInputStream(fieldXlpath)).getSheet(rcdType).getRow(0)
						.getPhysicalNumberOfCells();
				int profileCount = ExcelLib.getRowCountofColumn(objxlPath, obj, 1);
				for (int k = 1; k < profileCount; k++) {

					profile = ExcelLib.getCellValue(objxlPath, obj, k, 1);
					Reporter.log("<table><tr><th><b>PROFILE: " + profile + "</b></th></tr>", true);
					for (int l = 1; l < noOfColumns; l++) {
						if (ExcelLib.getCellValue(fieldXlpath, rcdType, 0, l).equals(profile)) {
							BaselineProfile = profile;
							baselineProfileIndex = l;
							break;
						} else {
						}

					}

					List<WebElement> allProfile = driver.findElements(By.xpath("//table/tbody/tr[5]/th"));
					if (driver.findElements(By.xpath("//table/tbody/tr[5]/th[text()='" + profile + "']")).size() > 0) {
						WebElement option = driver
								.findElement(By.xpath("//table/tbody/tr[5]/th[text()='" + profile + "']"));

						if (allProfile.contains(option)) {
							int profileIndex = allProfile.indexOf(option);

							try {
								int fieldCount = ExcelLib.getRowCount(fieldXlpath, rcdType);

								Reporter.log(
										"<table><tr bgcolor='#2EB8E6'><th>Fields</th><th>Source (Baseline Excel)  </th><th>Target (Application)  </th><th>Result</th></tr>");

								passCount = 0;
								failCount = 0;
								for (int j = 1; j < fieldCount; j++) {
									field = ExcelLib.getCellValue(fieldXlpath, rcdType, j, 0);
									try {
										targetState = driver
												.findElement(By.xpath(
														"//th[text()='" + field + "']/../td[" + profileIndex + "]/a"))
												.getText();

										SourceState = ExcelLib.getCellValue(fieldXlpath, rcdType, j,
												baselineProfileIndex);
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

								Reporter.log("<table><tr><th>Pass Count : " + passCount + "</th><th>Fail Count : "
										+ failCount + "</th></tr></table></br>");

							} catch (NullPointerException e) {
								Reporter.log("", true);
								Reporter.log(
										"<table><tr><th>	</th><th><font color='red'><b>ERROR: </b></th><td> Sheet ("
												+ rcdType
												+ ") not found in the Data Sheet for the selected Object </td></tr></table>",
										true);
								Reporter.log("</td></tr></table>", true);
							}
						}
					}

					else {
						Reporter.log("", true);
						Reporter.log("<table><tr><th><font color='red'><b>ERROR: </b></th><td> Profile ("
								+ profile + ") not found in the Application </td></tr></table>", true);
						Reporter.log("</td></tr></table>", true);

					}
				}

			} catch (NoSuchElementException e) {
				Reporter.log("", true);
				Reporter.log("<table><tr><th><font color='red'><b>ERROR: </b></th><td> Record Type ("
						+ rcdType + ") not found in the Application </td></tr></table></br>", true);
				Reporter.log("</td></tr></table>", true);
			}
		}
		Reporter.log("</table>", true);
	}
}
