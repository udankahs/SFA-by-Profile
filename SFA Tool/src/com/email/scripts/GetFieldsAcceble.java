package com.email.scripts;

import org.testng.annotations.Test;

import java.net.URLDecoder;

import org.testng.Reporter;
import com.email.pom.SFDCLogin;
import com.email.pom.gotoFieldAccebility;
import com.lib.ExcelLib;

/* 
 * Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class GetFieldsAcceble extends SFASuperTestNG {

	@Test
	public void getFields() throws Exception {
		SFDCLogin loginPage = new SFDCLogin(driver);
		gotoFieldAccebility fieldAccebility = new gotoFieldAccebility (driver);

		String JarPath = GetFieldsAcceble.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String folderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
		String decodedPath = URLDecoder.decode(folderPath, "UTF-8");

		String dataSheetPath = decodedPath + "Data Sheet/Data Sheet.xls";
		String sheetName = "Login";

		String uname = ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 0);
		String password = ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 1);
		String URL = "https://test.salesforce.com";
		if (ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 4).equalsIgnoreCase("Sandbox"))
		{
			URL="https://test.salesforce.com";
		}
		else if (ExcelLib.getCellValue(dataSheetPath, sheetName, 1, 4).equalsIgnoreCase("Production"))
		{
			URL="https://login.salesforce.com";
		}
		String obj = null;
		String baselineSheetPath = null;

		Reporter.log(
				"<html><head><style>table, th, td { border: 1px solid black; border-collapse: collapse;}</style></head><body>",
				true);
		loginPage.login(uname, password, URL);

		if (loginPage.verifyLogin()) {
			int objCount = ExcelLib.getRowCountofColumn(dataSheetPath, sheetName, 2);

			Reporter.log("<table><tr><th bgcolor='#00b2b3'><b> Number of Objects taken for this Execution: </b></th><td>  " + --objCount
					+ "  </td></tr></table></br>", true);

			for (int i = 1; i <=objCount; i++) {
				obj = ExcelLib.getCellValue(dataSheetPath, sheetName, i, 2);

				Reporter.log("</br><table>", true);
				Reporter.log("<tr><th bgcolor='#d57676'><b>OBJECT TESTING: </b>" + obj + "</th><td>", true);

				fieldAccebility.gotoFieldAaccebilty();
				if (fieldAccebility.gotoObjAaccebilty(obj)) {

					baselineSheetPath = decodedPath + "Baseline Data/Baseline Excel_" + obj + ".xls";

					if (ExcelLib.isFileExists(decodedPath + "/Baseline Data/", "Baseline Excel_" + obj + ".xls")) {
						fieldAccebility.getFieldAaccebilty(obj, dataSheetPath, baselineSheetPath);

					} else {

						Reporter.log(
								"<table><tr><th><font color='red'><b>ERROR: </b></th><td> Baseline sheet for "
										+ obj + " not found</td></tr></table>",
								true);
					}
					Reporter.log("</td></table>", true);

				}
				else{
					Reporter.log("<table><tr><th><font color='red'><b>ERROR: </b></th><td> Object (" + obj
							+ ") not found in the Application </td></tr></table> ", true);
				}
					
				Reporter.log("</td></table>", true);

			}
		} else {

			Reporter.log(
					"</br><table><tr><th><b>TEST STATUS :</b></th><td> FAILED -- Incorrect Username or Password </td></tr></table>",
					true);
		}
		Reporter.log("</body></html>", true);
	}
}
