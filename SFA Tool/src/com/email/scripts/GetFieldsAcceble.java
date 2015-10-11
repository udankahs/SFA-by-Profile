package com.email.scripts;

import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.email.pom.SFDCLogin;
import com.email.pom.gotoFieldAccebility;
import com.lib.ExcelLib;

public class GetFieldsAcceble extends SFASuperTestNG {
	@Test
	public void getFields() throws Exception {
		SFDCLogin loginPage = new SFDCLogin(driver);
		gotoFieldAccebility fieldAccebility = new gotoFieldAccebility(driver);

		String masterXlPath = "D:/SFA Selenium Utility/Data Sheet/Data Sheet.xls";
		String accountXlPath = "D:/SFA Selenium Utility/Baseline Data/Baseline Excel_Account.xls";
		String activityXlPath = "D:/SFA Selenium Utility/Baseline Data/Baseline Excel_Call.xls";
		String sheetName = "Login";

		String uname = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 0);
		String password = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 1);
		String obj = null;
		String fieldXlPath = null;

		loginPage.login(uname, password);

		if (loginPage.verifyLogin()) {
			int objCount = ExcelLib.getRowCount(masterXlPath, sheetName);
			Reporter.log("<table><tr><th><b> Number of Objects taken for this Execution: </b></th><td>"+ objCount+"</table></br></br>", true);
			
			Reporter.log("<table>", true);			
			
			for (int i = 1; i <= objCount; i++) {
				obj = ExcelLib.getCellValue(masterXlPath, sheetName, i, 2);

				Reporter.log("", true);
				Reporter.log("<tr><th><b>OBJECT TESTING: </b></th><td>" + obj+"<td></table></br>", true);
				Reporter.log("", true);

				fieldAccebility.gotoFieldAaccebilty();
				fieldAccebility.gotoObjAaccebilty(obj);

				if (obj.equals("Account")) {
					fieldXlPath = accountXlPath;
				} else if (obj.equals("Call")) {
					fieldXlPath = activityXlPath;
				} else {
					fieldXlPath = null;
				}
				System.out.println("fieldXlPath " + fieldXlPath);
				if (fieldXlPath != null) {
					fieldAccebility.getFieldAaccebilty(obj, masterXlPath, fieldXlPath);
				} else {
					Reporter.log("<table><tr><th><b>ERROR: </b></th><td> Baseline sheet for " + obj + " not found</td></table>", true);
				}
			}
		} else {
			{
				Reporter.log("</br><table><tr><th><b>TEST STATUS</b></th><td> : Please Check Username and Password </td></table>", true);
			}
		}
	}
}
