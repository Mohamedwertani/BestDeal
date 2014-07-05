package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.UserDAO;

public class UserRegistrationDialog extends JDialog {

	private static final long serialVersionUID = 2474226617609877731L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;

	/**
	 * Create the dialog.
	 */
	public UserRegistrationDialog(JFrame parent) {
		super(parent, "Register", Dialog.ModalityType.DOCUMENT_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("First name");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 2;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textFieldFirstName = new JTextField();
			GridBagConstraints gbc_textFieldFirstName = new GridBagConstraints();
			gbc_textFieldFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldFirstName.gridx = 4;
			gbc_textFieldFirstName.gridy = 2;
			contentPanel.add(textFieldFirstName, gbc_textFieldFirstName);
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Last name");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 2;
			gbc_lblNewLabel_1.gridy = 3;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			textFieldLastName = new JTextField();
			GridBagConstraints gbc_textFieldLastName = new GridBagConstraints();
			gbc_textFieldLastName.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldLastName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldLastName.gridx = 4;
			gbc_textFieldLastName.gridy = 3;
			contentPanel.add(textFieldLastName, gbc_textFieldLastName);
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblLogin = new JLabel("Login");
			GridBagConstraints gbc_lblLogin = new GridBagConstraints();
			gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
			gbc_lblLogin.gridx = 2;
			gbc_lblLogin.gridy = 4;
			contentPanel.add(lblLogin, gbc_lblLogin);
		}
		{
			textFieldLogin = new JTextField();
			GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
			gbc_textFieldLogin.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldLogin.gridx = 4;
			gbc_textFieldLogin.gridy = 4;
			contentPanel.add(textFieldLogin, gbc_textFieldLogin);
			textFieldLogin.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 2;
			gbc_lblPassword.gridy = 5;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 5);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 4;
			gbc_passwordField.gridy = 5;
			contentPanel.add(passwordField, gbc_passwordField);
		}
		{
			JLabel lblRepeatPassword = new JLabel("Repeat Password");
			GridBagConstraints gbc_lblRepeatPassword = new GridBagConstraints();
			gbc_lblRepeatPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblRepeatPassword.gridx = 2;
			gbc_lblRepeatPassword.gridy = 6;
			contentPanel.add(lblRepeatPassword, gbc_lblRepeatPassword);
		}
		{
			passwordFieldRepeat = new JPasswordField();
			GridBagConstraints gbc_passwordFieldRepeat = new GridBagConstraints();
			gbc_passwordFieldRepeat.insets = new Insets(0, 0, 5, 5);
			gbc_passwordFieldRepeat.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordFieldRepeat.gridx = 4;
			gbc_passwordFieldRepeat.gridy = 6;
			contentPanel.add(passwordFieldRepeat, gbc_passwordFieldRepeat);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Register");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String login = textFieldLogin.getText();
						UserDAO userDAO = new UserDAO();
						if (userDAO.retrieve("login", login).size() > 0) {
							JOptionPane.showMessageDialog(null, "Login is already chosen. Please choose another one");
						} else {
							String firstName = textFieldFirstName.getText();
							String lastName = textFieldLastName.getText();
							String pwd = String.valueOf(passwordField.getPassword());
							String pwdRepeated = String.valueOf(passwordFieldRepeat.getPassword());
							if (firstName.equals("") || lastName.equals("") ||
									pwd.equals("") || pwdRepeated.equals("")) {
								JOptionPane.showMessageDialog(null, "Please complete all required field");
							}
							if (pwd.equals(pwdRepeated)) {
								if (userDAO.create(new User(firstName, lastName, login, pwd)) == false) {
									JOptionPane.showMessageDialog(null, "Could not access database." +
											"Please make sure the SQL server is running");
								} else {
									UserRegistrationDialog.this.dispose();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Passwords do not match");
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 UserRegistrationDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
