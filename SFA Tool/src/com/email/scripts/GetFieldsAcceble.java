package com.email.scripts;

import org.testng.annotations.Test;

import com.email.pom.SFDCLogin;
import com.email.pom.gotoFieldAccebility;
import com.lib.ExcelLib;

public class GetFieldsAcceble extends SFASuperTestNG 
{
	@Test
	public void getFields() throws Exception 
	{
		SFDCLogin loginPage = new SFDCLogin(driver);
		gotoFieldAccebility fieldAccebility = new gotoFieldAccebility(driver);
		
//		String iREPUname = DetailFieldValues.adminUname;
//		String iREPpassword = DetailFieldValues.adminPaswd;
//		String iREPeMAil = DetailFieldValues.UserEmail;
//		String eMailUname = DetailFieldValues.EmailUsername;
//		String eMailpassword = DetailFieldValues.EmailPWD;
//		String newPWD = DetailFieldValues.AllUserPWD;

		String xlPath = "src/Data Sheet.xls";
		String sheetName = "Login";
		
		String uname = ExcelLib.getCellValue(xlPath,sheetName,1,0);
		String password = ExcelLib.getCellValue(xlPath,sheetName,1,1);
		String obj = ExcelLib.getCellValue(xlPath,sheetName,1,2);
		String rcdType = ExcelLib.getCellValue(xlPath,sheetName,1,3);
	
		//int rowCount = ExcelLib.getRowCount(xlPath, sheetName);

		
			//String PrfoUname = ExcelLib.getCellValue(xlPath, sheetName, i, 0);

			loginPage.login(uname, password);

			fieldAccebility.gotoFieldAaccebilty(obj, rcdType);
		
		//driver.quit();
	}
}
