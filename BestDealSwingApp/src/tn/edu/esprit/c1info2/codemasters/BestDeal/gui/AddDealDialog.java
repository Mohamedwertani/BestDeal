package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Category;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.CategoryDAO;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AddDealDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldDealName;
	private JTextField textFieldDealDesc;
	private JSpinner spinnerDealPrice;
	private JComboBox comboBoxDealCategory;
	private User connectedUser;
	private JSpinner spinnerDealDuration;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 */
	public AddDealDialog(JFrame parent, User connectedUser) {
		super(parent, ModalityType.APPLICATION_MODAL);
		this.connectedUser = connectedUser;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Name");
			contentPanel.add(lblNewLabel, "8, 6");
		}
		{
			textFieldDealName = new JTextField();
			contentPanel.add(textFieldDealName, "12, 6, fill, default");
			textFieldDealName.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Description");
			contentPanel.add(lblNewLabel_1, "8, 8");
		}
		{
			textFieldDealDesc = new JTextField();
			contentPanel.add(textFieldDealDesc, "12, 8, fill, default");
			textFieldDealDesc.setColumns(10);
		}
		{
			JLabel lblPrice = new JLabel("Price");
			contentPanel.add(lblPrice, "8, 10");
		}
		{
			spinnerDealPrice = new JSpinner();
			contentPanel.add(spinnerDealPrice, "12, 10");
		}
		{
			JLabel lblCategory = new JLabel("Category");
			contentPanel.add(lblCategory, "8, 12");
		}
		{
			comboBoxDealCategory = new JComboBox();
			CategoryDAO categoryDAO = new CategoryDAO();
			List<Category> categoryList = categoryDAO.retrieveAll();
			for (Category c : categoryList) {
				comboBoxDealCategory.addItem(c.getName());
			}

			contentPanel.add(comboBoxDealCategory, "12, 12, fill, default");
		}
		{
			JLabel lblDuration = new JLabel("Duration (minutes)");
			contentPanel.add(lblDuration, "8, 14");
		}
		{
			spinnerDealDuration = new JSpinner();
			contentPanel.add(spinnerDealDuration, "12, 14");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String dealName = textFieldDealName.getText();
						if (dealName.isEmpty() == false) {
							String dealDesc = textFieldDealDesc.getText();
							Integer dealPrice = (Integer) spinnerDealPrice
									.getValue();
							String dealCategory = (String) comboBoxDealCategory.getSelectedItem();
							int dealDuration = (int) spinnerDealDuration.getValue();
							DealDAO dealDAO = new DealDAO();
							dealDAO.create(new Deal(0, dealName, dealDesc,
									dealPrice, dealCategory, null, dealDuration * 60 * 1000,
									AddDealDialog.this.connectedUser.getLogin()));
							AddDealDialog.this.dispose();

						} else {
							JOptionPane.showMessageDialog(AddDealDialog.this,
									"Please enter a deal name");
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
					public void actionPerformed(ActionEvent arg0) {
						AddDealDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
