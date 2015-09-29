package com.email.scripts;

public class Login {

	public static boolean authenticate(String username, String password) 
	{
		// hardcoded username and password
		if (username.equals("spurp") && password.equals("secret")) 
		{
			return true;
		}
		return false;
	}
}