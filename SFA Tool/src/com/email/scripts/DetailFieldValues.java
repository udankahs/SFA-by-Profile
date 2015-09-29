package com.email.scripts;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class DetailFieldValues extends JDialog
{
	public static String adminUname;
	public static String adminPaswd;
	public static String UserEmail;	
	public static String EmailUsername;
	public static String EmailPWD;
	public static String AllUserPWD;
	
	private JTextField adminUsername;
	private JTextField adminPwd;
	private JTextField userEmail;
	private JTextField emailLoginUserName;
	private JPasswordField emailLoginPwdrName;	
	private JTextField allUserPassword;
	
	private JLabel topMessage;
	private JLabel adUsernameLable;
	private JLabel adPwdLable;
	private JLabel userEmailLable;
	private JLabel emailLoginUserNameLable;
	private JLabel emailLoginPwdrNameLable;
	private JLabel allUserPasswordLable;
	
	private JButton btnSubmit;
	private JButton btnReset;
	private boolean succeeded;

	public DetailFieldValues(Frame parent) {
		super(parent, "Required Details Collection", true);
		//
		JPanel smallPanel = new JPanel(new GridBagLayout());
		JPanel panel = new JPanel(new GridBagLayout());

		
		GridBagConstraints cs1 = new GridBagConstraints();
		
		cs1.fill = GridBagConstraints.NORTH;
		
		topMessage = new JLabel("Top Lable");
		topMessage.setText("Please enter the following required details. Make sure all the details entered are correct before pressing Submit button!");
		topMessage.setHorizontalAlignment(JLabel.CENTER);
		topMessage.setVerticalAlignment(JLabel.CENTER);
		topMessage.setFont(new Font("Courier New", Font.ITALIC, 14));
		topMessage.setForeground(Color.RED);
//		cs.gridx = 1;
//		cs.gridy = 0;
//		cs.gridwidth = 5;
		smallPanel.add(topMessage, cs1);
		
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		adUsernameLable = new JLabel("Enter Admin Username:");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(adUsernameLable, cs);

		adminUsername = new JTextField(20);
		cs.gridx = 1;
		//cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(adminUsername, cs);

		adPwdLable = new JLabel("Enter Admin Password:");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		panel.add(adPwdLable, cs);

		adminPwd = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		panel.add(adminPwd, cs);

		userEmailLable = new JLabel("Enter the Email Address:");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		panel.add(userEmailLable, cs);

		userEmail = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(userEmail, cs);
		
		emailLoginUserNameLable = new JLabel("Enter the Abbvie Email User Name:");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(emailLoginUserNameLable, cs);

		emailLoginUserName = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(emailLoginUserName, cs);
		
		emailLoginPwdrNameLable = new JLabel("Enter the Abbvie Email Password: ");
		cs.gridx = 0;
		cs.gridy = 5;
		cs.gridwidth = 1;
		panel.add(emailLoginPwdrNameLable, cs);

		emailLoginPwdrName = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 5;
		cs.gridwidth = 2;
		panel.add(emailLoginPwdrName, cs);
		panel.setBorder(new LineBorder(Color.GRAY));
		
		allUserPasswordLable = new JLabel("Enter the Standard Password for all the Users: ");
		cs.gridx = 0;
		cs.gridy = 6;
		cs.gridwidth = 1;
		panel.add(allUserPasswordLable, cs);

		allUserPassword = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 6;
		cs.gridwidth = 2;
		panel.add(allUserPassword, cs);
		
		btnSubmit = new JButton("Submit");

		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				adminUname = getadminUserName();	
				adminPaswd = getadminPwd();
				UserEmail = getUserEmailID();	
				EmailUsername = getEmailUsername();
				EmailPWD = getEmailPassword();
				AllUserPWD = getAllUserPwd();	
				if (FieldVerification.verify(adminUname, adminPaswd, UserEmail, EmailUsername, EmailPWD, AllUserPWD )) 
				{
					JOptionPane.showMessageDialog(DetailFieldValues.this, "All the Field values were entered", "Submit",
							JOptionPane.INFORMATION_MESSAGE);
					succeeded = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(DetailFieldValues.this,
							"All fields values were not entered! Please recheck and enter again.", "Submit",JOptionPane.ERROR_MESSAGE);
					
					// reset all the fields
					adminUsername.setText("");
					adminPwd.setText("");
					userEmail.setText("");
					emailLoginUserName.setText("");
					emailLoginPwdrName.setText("");
					allUserPassword.setText("");
					succeeded = false;
				}
			}
		});
		
		btnReset = new JButton("Cancel");
		btnReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnSubmit);
		bp.add(btnReset);

		getContentPane().add(smallPanel, BorderLayout.PAGE_START);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	public String getadminUserName() {
		return adminUsername.getText().trim();
	}

	public String getadminPwd() {
		return adminPwd.getText().trim();
	}

	public String getUserEmailID() {
		return userEmail.getText().trim();
	}

	public String getEmailUsername() {
		return emailLoginUserName.getText().trim();
	}

	public String getAllUserPwd() {
		return allUserPassword.getText().trim();
	}

	public String getEmailPassword() {
		return new String(emailLoginPwdrName.getPassword());
	}

	public boolean isSucceeded() {
		return succeeded;
	}
}