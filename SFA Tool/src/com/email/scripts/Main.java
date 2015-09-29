package com.email.scripts;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Main {
	public static void main(String[] args) {
		final JFrame frame = new JFrame("SPURP Tool");
		JPanel nested1 = new JPanel(new GridLayout(0,1));
		JPanel nested2 = new JPanel(new GridLayout(1,1));
		
		final JLabel textLabel = new JLabel("Welcome to SPURP Tool", SwingConstants.CENTER);
		final JButton btnLogin = new JButton("Click to login");

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDialog loginDlg = new LoginDialog(frame);
				loginDlg.setVisible(true);
				// if logon successfully
				if (loginDlg.isSucceeded()) {
					textLabel.setText("In Progress.. Dont close the Window..!");
					btnLogin.setText("Click Here to Re-Login");

					DetailFieldValues fieldValue = new DetailFieldValues(frame);
					fieldValue.setVisible(true);
					// if logon successfully
					if (fieldValue.isSucceeded()) {
						
						btnLogin.setText("Click Here to Re-Login");

						TestListenerAdapter tla = new TestListenerAdapter();
						TestNG testng = new TestNG();
						testng.setTestClasses(new Class[] { PasswordReset.class });
						testng.addListener(tla);
						testng.run();
					}
				}
			}
		});
		
		JPanel outer = new JPanel(new BorderLayout());  
		outer.add(nested1, BorderLayout.CENTER);  
		outer.add(nested2, BorderLayout.SOUTH); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		nested1.add(textLabel);
		frame.setLocationRelativeTo(null); 
		nested2.add(btnLogin, JButton.CENTER);
		frame.setVisible(true);
		frame.setContentPane(outer);
	}
}