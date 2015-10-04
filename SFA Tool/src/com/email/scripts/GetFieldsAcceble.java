package com.email.scripts;

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

		String masterXlPath = "src/Data Sheet.xls";
		String accountXlPath = "src/Baseline Excel_Account.xls";
		String activityXlPath = "src/Baseline Excel_Call.xls";
		String sheetName = "Login";

		String uname = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 0);
		String password = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 1);
		String obj = null;
		String fieldXlPath = null;

		loginPage.login(uname, password);

		if (loginPage.verifyLogin()) {
			int objCount = ExcelLib.getRowCount(masterXlPath, sheetName);
			System.out.println("objCount :" + objCount);
			for (int i = 1; i <= objCount; i++) {
				obj = ExcelLib.getCellValue(masterXlPath, sheetName, i, 2);

				System.out.println("obj " + obj);
				fieldAccebility.gotoFieldAaccebilty();
				fieldAccebility.gotoObjAaccebilty(obj);

				if (obj.equals("Account")) {
					fieldXlPath = accountXlPath;
				} else if (obj.equals("Call")) {
					fieldXlPath = activityXlPath;
				}
				else
				{
					fieldXlPath = null;
				}
				System.out.println("fieldXlPath " + fieldXlPath);
				if (fieldXlPath!=null){
					fieldAccebility.getFieldAaccebilty(obj, masterXlPath, fieldXlPath);
				}
				else
				{
					Reporter.log("Base Line sheet for "+obj+ " not found.", false);
				}
			}
		} else {
			{
				Reporter.log("Test Failed : Please Check Username and Password", false);
			}
		}
	}
}
