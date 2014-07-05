package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;
import java.awt.Canvas;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -7379992836666197521L;

	private JPanel contentPane;
	private JTable table;
	private User client;
	private Socket socket;

	public static final String serverAddress = "127.0.0.1";
	public static final int port = 125;

	private JLabel lblServerStatus;
	private JLabel lblUserStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		super("BestDeal - Best deals for everyone!");
		initialize();
		socket = connectToReportingServer();
		if (socket != null) {
			lblServerStatus.setText("Connected to reporting server");
			ServerThread thread = new ServerThread(socket, new Runnable() {

				@Override
				public void run() {
					MainFrame.this.retrieveDealList();
				}
			});
			thread.start();
		} else {
			lblServerStatus.setText("Could not connect to reporting server");
		}
		retrieveDealList();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.dispose();
			}
			
		});
		mnNewMenu.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {

			private Object aboutMessage = "This project is an assignment in a course\n"
					+ "entitled \"Mini projets Java\" at ESPRIT and\n"
					+ "was developed conjointly by:\n\n"
					+ "Seifeddine DRIDI\n Mohamed Wertani\n Elyes Zakraoui\nOmar Najjar";

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainFrame.this, aboutMessage);
			}
			
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.NORTH);
		
		lblUserStatus = new JLabel("You are not logged in");
		lblUserStatus.setForeground(Color.RED);
		horizontalBox.add(lblUserStatus);
		
		Component glue = Box.createGlue();
		horizontalBox.add(glue);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLoginDialog d = new UserLoginDialog(MainFrame.this);
				User user = d.showDialog();
				if (user != null) {
					client = user;
					lblUserStatus.setForeground(Color.GREEN);
					lblUserStatus.setText("Hello " + client.getFirstName());
				} else if (user == client) {
					lblUserStatus.setForeground(Color.RED);
					lblUserStatus.setText("You are not logged in");
				}
			}
		});
		horizontalBox.add(btnLogin);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserRegistrationDialog registerDialog = new UserRegistrationDialog(MainFrame.this);
				registerDialog.setVisible(true);
			}
		});
		horizontalBox.add(btnRegister);
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		verticalBox.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String dealName = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
				DealView dealView = new DealView(MainFrame.this, client, dealName);
				dealView.setVisible(true);
				// Update deal list
				retrieveDealList();
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Owner"
			}
		));
		scrollPane.setViewportView(table);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		contentPane.add(horizontalBox_1, BorderLayout.SOUTH);
		
		JButton btnAddDeal = new JButton("Add Deal");
		horizontalBox_1.add(btnAddDeal);
		btnAddDeal.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnAddDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client != null) {
					AddDealDialog addDealDialog = new AddDealDialog(MainFrame.this, client);
					addDealDialog.setVisible(true);
					retrieveDealList();
					if (socket != null) {
						notifyServer(socket);
					}
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "You have to login first before you can add a new deal");
				}
			}
		});
		btnAddDeal.setHorizontalAlignment(SwingConstants.LEFT);
		
		Component glue_1 = Box.createGlue();
		horizontalBox_1.add(glue_1);
		
		lblServerStatus = new JLabel("Establishing connection to reporting server...");
		horizontalBox_1.add(lblServerStatus);
	}

	public static void notifyServer(Socket socket) {
		if (socket.isConnected()) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				writer.println("dbchanged");
			} catch (IOException e) {
				System.out.println("An error occured while trying to notify reporting server");
			}
		}
	}

	private void retrieveDealList() {
		DealDAO dealDAO = new DealDAO();
		List<Deal> dealList = dealDAO.retrieveAll();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rows = model.getRowCount();
		for(int i = rows - 1; i >= 0; i--) {
		   model.removeRow(i);
		}
		for (Deal deal : dealList) {
			model.addRow(new Object[] { deal.getName(), deal.getOwner()});
		}
		repaint();
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public static Socket connectToReportingServer() {
		try {
			Socket socket = new Socket(serverAddress, port);
			return socket;
		} catch (IOException e) {
			System.out.println("Could not connect to reporting server (" + serverAddress + ":" + port + ")");
		}
		return null;
	}

}
