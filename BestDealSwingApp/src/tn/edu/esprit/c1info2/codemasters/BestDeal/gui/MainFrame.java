package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8886499619398791611L;

	private JTable table;
	private User client;
	private JLabel lblYouAreNot;
	private Socket socket;
	private JLabel lblEstablishingConnectionTo;

	private Timer updatingTimer;

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		updatingTimer = new Timer(4000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				connectToReportingServer();
				MainFrame.this.repaint();
			}
		});
		updatingTimer.start();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, 579, 323);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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
		getContentPane().setLayout(null);
		getContentPane().add(lblYouAreNot);
		getContentPane().add(scrollPane);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(384, 11, 169, 21);
		getContentPane().add(horizontalBox);

		JButton btnLogin = new JButton("Login");
		horizontalBox.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLoginDialog d = new UserLoginDialog(MainFrame.this);
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
		
		lblEstablishingConnectionTo = new JLabel("Establishing connection to reporting server...");
		lblEstablishingConnectionTo.setBounds(10, 270, 333, 14);
		getContentPane().add(lblEstablishingConnectionTo);
		
		JButton btnAddDeal = new JButton("Add Deal");
		btnAddDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client != null) {
					AddDealDialog addDealDialog = new AddDealDialog(MainFrame.this, client);
					addDealDialog.setVisible(true);
					retrieveDealList();
					MainFrame.this.repaint();
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "You have to login before you can add a new deal");
				}
			}
		});
		btnAddDeal.setBounds(10, 222, 89, 23);
		getContentPane().add(btnAddDeal);

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserRegistrationDialog registerDialog = new UserRegistrationDialog(MainFrame.this);
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

	public void connectToReportingServer() {
		try {
			if (socket == null || socket.isClosed()) {
				socket = new Socket("127.0.0.1", 125);
				if (socket.isConnected()) {
					lblEstablishingConnectionTo.setText("Connected to reporting server");
				} else {
					lblEstablishingConnectionTo.setText("Could not connect to reporting server");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
