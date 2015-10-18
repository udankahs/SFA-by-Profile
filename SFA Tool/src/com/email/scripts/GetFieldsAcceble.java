package com.email.scripts;

import org.testng.annotations.Test;

import java.net.URLDecoder;

import org.testng.Reporter;
import com.email.pom.SFDCLogin;
import com.email.pom.gotoFieldAccebility;
import com.lib.ExcelLib;

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: EAS CRM
 * Organization		: Cognizant Technology Solutions
 */

public class GetFieldsAcceble extends SFASuperTestNG {

	@Test
	public void getFields() throws Exception {
		SFDCLogin loginPage = new SFDCLogin(driver);
		gotoFieldAccebility fieldAccebility = new gotoFieldAccebility(driver);

		String JarPath = GetFieldsAcceble.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String folderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
		String decodedPath = URLDecoder.decode(folderPath, "UTF-8");

		String masterXlPath = decodedPath + "Data Sheet/Data Sheet.xls";
//		String accountXlPath = decodedPath + "Baseline Data/Baseline Excel_Account.xls";
//		String activityXlPath = decodedPath + "Baseline Data/Baseline Excel_Call.xls";
		String sheetName = "Login";

		String uname = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 0);
		String password = ExcelLib.getCellValue(masterXlPath, sheetName, 1, 1);
		String obj = null;
		String fieldXlPath = null;

		loginPage.login(uname, password);

		if (loginPage.verifyLogin()) {
			int objCount = ExcelLib.getRowCount(masterXlPath, sheetName);
			Reporter.log("<table><tr><th><b> Number of Objects taken for this Execution: </b></th><td>" + objCount
					+ "</table></br></br>", true);

			Reporter.log("<table>", true);

			for (int i = 1; i <= objCount; i++) {
				obj = ExcelLib.getCellValue(masterXlPath, sheetName, i, 2);

				Reporter.log("", true);
				Reporter.log("</br><style>table, th, td { border: 1px solid black;    border-collapse: collapse;}</style><table><tr><th><b>OBJECT TESTING: </b></th><td>" + obj + "<td></table></br>", true);
				Reporter.log("", true);

				fieldAccebility.gotoFieldAaccebilty();
				fieldAccebility.gotoObjAaccebilty(obj);

				fieldXlPath = decodedPath+"Baseline Data/Baseline Excel_"+obj+".xls";
				System.out.println("fieldXlPath " + fieldXlPath);
				
					fieldAccebility.getFieldAaccebilty(obj, masterXlPath, fieldXlPath);
					Reporter.log("<table><tr><th>	</th><th><font color='red'><b>ERROR: </b></th><td> Baseline sheet for " + obj
							+ " not found</td></tr></table>", true);
			}
		} else {
			{
				Reporter.log(
						"</br><table><tr><th><b>TEST STATUS</b></th><td> : FAILED -- Please Check Username and Password </td></tr></table>",
						true);
			}
		}
	}
}
