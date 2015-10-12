package com.email.scripts;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.lib.ExcelLib;

public class SFA_Main 
{
	public static void main(String[] args) 
	{
		
		try 
		{
			String JarPath = GetFieldsAcceble.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String folderPath = JarPath.substring(0, JarPath.lastIndexOf("/") + 1);
			String decodedPath = URLDecoder.decode(folderPath, "UTF-8");
			
			System.out.println("decodedPath"+decodedPath);
			ExcelLib.writeExcel("D:/SFA Selenium Utility/Output/Output.xls", "Output", 0, 0, decodedPath);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { GetFieldsAcceble.class });

		testng.setTestClasses(new Class[] { GetFieldsAcceble.class });
		testng.addListener(tla);
		testng.run();
	}
}