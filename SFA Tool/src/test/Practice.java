package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.lib.ExcelLib;

public class Practice {

	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException {
		
		System.out.println(ExcelLib.getRowCountofColumn("D:/SFA Selenium Utility/Baseline Data/Baseline Excel_Account.xls", "Pharmacist", 1));
		System.out.println(WorkbookFactory.create(new FileInputStream("D:/SFA Selenium Utility/Baseline Data/Baseline Excel_Account.xls")).getSheet("Pharmacist").getRow(0)
		.getPhysicalNumberOfCells());
	}

}
