package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;

public class MainApplication {

	private JFrame frame;
	private JTable table;
	private User client;
	private JLabel lblYouAreNot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication window = new MainApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 579, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lblYouAreNot = new JLabel("You are not connected");
		lblYouAreNot.setBounds(10, 7, 132, 14);
		lblYouAreNot.setForeground(Color.RED);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 543, 145);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Description", "Price", "Category", "Owner", "Start date", "Remaining"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Float.class, String.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		retrieveDealList();

		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblYouAreNot);
		frame.getContentPane().add(scrollPane);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(384, 11, 169, 21);
		frame.getContentPane().add(horizontalBox);

		JButton btnLogin = new JButton("Login");
		horizontalBox.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLoginDialog d = new UserLoginDialog(frame);
				client = d.showDialog();
				if (client != null) {
					lblYouAreNot.setForeground(new Color(0xFF00));
					lblYouAreNot.setText("Hello " + client.getFirstName());
				}
			}
		});

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);

		JButton btnRegister = new JButton("Register");
		horizontalBox.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserRegistrationDialog registerDialog = new UserRegistrationDialog(
						frame);
				registerDialog.setVisible(true);
			}
		});
	}

	public void retrieveDealList() {
		DealDAO dealDAO = new DealDAO();
		List<Deal> dealList = dealDAO.retrieveAll();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Deal deal : dealList) {
			model.addRow(new Object[] { deal.getName(), deal.getDesc(),
					deal.getPrice(), deal.getCategory(), deal.getOwnerId(),
					deal.getStartDate().toString(), 0 });
		}
		table.updateUI();
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

}
