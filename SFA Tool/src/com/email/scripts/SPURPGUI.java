package com.email.scripts;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SPURPGUI extends JFrame {
	private JTextField adminUser;
	private JTextField adminPwd;
	private JTextField emailID;
	private JTextField loginName;
	private JTextField newPasswprd;
	private JPasswordField passwordfield;
	
	JLabel userLabel = new JLabel("Admin Username: ");
	JLabel PwdLable = new JLabel("Admin Password: ");
	JLabel eMailLable = new JLabel("Email Id of the Tester: ");
	JLabel LoginLable = new JLabel("Login Username: ");
	JLabel NewPwdLable = new JLabel("New Password: ");
	JLabel EmailPwdLable = new JLabel("Email Password: ");

	public SPURPGUI() {
		super("SPURP Tool");
		setLayout(new FlowLayout());
		
		adminUser = new JTextField(20);
		userLabel.setLabelFor(adminUser);
		add(userLabel);
		add(adminUser);
		
		adminPwd = new JTextField(10);
		PwdLable.setLabelFor(adminPwd);
		add(PwdLable);
		add(adminPwd);

		emailID = new JTextField(20);
		eMailLable.setLabelFor(emailID);
		add(eMailLable);
		add(emailID);

		loginName = new JTextField(20);
		LoginLable.setLabelFor(loginName);
		add(LoginLable);
		add(loginName);

		passwordfield = new JPasswordField(20);
		EmailPwdLable.setLabelFor(passwordfield);
		add(EmailPwdLable);
		add(passwordfield);

		newPasswprd = new JTextField(20);
		NewPwdLable.setLabelFor(newPasswprd);
		add(NewPwdLable);
		add(newPasswprd);
		
		thehandler handler = new thehandler();

		adminUser.addActionListener(handler);
		adminPwd.addActionListener(handler);
		emailID.addActionListener(handler);
		loginName.addActionListener(handler);
		passwordfield.addActionListener(handler);
		newPasswprd.addActionListener(handler);
	}

	private class thehandler implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent event) 
		{
			String string = "";
			String adminPwd1="";
			String emailID1="";
			String loginName1="";
			String passwordfield1="";
			String newPasswprd1="";
			
			if (event.getSource() == adminUser)
				string = String.format("%s", event.getActionCommand());

			else if (event.getSource() == adminPwd)
				string = String.format("%s", event.getActionCommand());

			else if (event.getSource() == emailID)
				string = String.format("%s", event.getActionCommand());

			else if (event.getSource() == loginName)
				string = String.format("%s", event.getActionCommand());

			else if (event.getSource() == passwordfield)
				string = String.format("%s", event.getActionCommand());

			else if (event.getSource() == newPasswprd)
				string = String.format("%s", event.getActionCommand());
			
			JOptionPane.showMessageDialog(null, string);
			
			System.out.println(string);
			System.out.println(adminPwd1);
			System.out.println(emailID1);
			System.out.println(loginName1);
			System.out.println(passwordfield1);
			System.out.println(newPasswprd1);
		}
	}
}