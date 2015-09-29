package com.email.scripts;

public class FieldVerification {

	public static boolean verify(String adminUname1, String adminPaswd1,String userEmail1, String emailUsername1, String emailPWD1,	String allUserPWD1) {
		if (adminUname1.equals("") || adminPaswd1.equals("") || userEmail1.equals("")|| emailUsername1.equals("")|| emailPWD1.equals("")|| allUserPWD1.equals("") ) 
		{
			return false;
		}
		return true;
	}
}