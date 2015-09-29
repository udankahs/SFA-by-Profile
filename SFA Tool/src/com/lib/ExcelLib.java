package com.lib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLib
{
	public static int getRowCount(String xlPath,String sheetName)
	{
		try
		{
			FileInputStream fis=new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s1 = wb.getSheet(sheetName);
			int rc=s1.getLastRowNum();
			return rc;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	
	public static String getCellValue(String xlPath,String sheetName,int rowNum,int cellNum)
	{
		try
		{
			FileInputStream fis=new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s1 = wb.getSheet(sheetName);
			String v=s1.getRow(rowNum).getCell(cellNum).getStringCellValue();
			return v;
		}
		catch(Exception e)
		{
		System.out.println(e);
			return "";
		}
	}
	
	public static void writeExcel(String output_Path,String sheetName, int roww, int coll, String Valuee)throws Exception
	{	
		FileInputStream fis = new FileInputStream(output_Path);
		Workbook wb = WorkbookFactory.create(fis);
		FileOutputStream file1 = new FileOutputStream(output_Path);
		Sheet s1 = wb.getSheet(sheetName);
		org.apache.poi.ss.usermodel.Row rownum1 = s1.getRow(roww);
		rownum1.createCell(coll).setCellValue(Valuee);
		wb.write(file1);
		file1.close();
	}
	public static int getColCount(String xlPath,String sheetName, int row)
	{
		try
		{
			FileInputStream fis=new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s1 = wb.getSheet(sheetName);
			int rc=s1.getRow(row).getPhysicalNumberOfCells();
			return rc;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
}
