package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class DealView extends JDialog {

	private static final long serialVersionUID = -4216718099293232520L;

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textFieldDealPrice;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textFieldOwner;
	private JSpinner spinnerDealNewPrice;
	private JTextField textFieldRemainingTime;

	private Deal deal;
	private User loggedInUser;

	private ActionListener remainingTimeActionListener;

	private Timer timer;

	private Socket socket;

	/**
	 * Create the dialog.
	 * 
	 * @param mainFrame
	 * 
	 * @param dealName
	 */
	public DealView(JFrame parent, User loggedInUser, String dealName) {
		super(parent, "Deal View", ModalityType.APPLICATION_MODAL);
		deal = retireveDealByName(dealName);
		this.loggedInUser = loggedInUser;
		initialize();
		remainingTimeActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (deal.enabled()) {
					textFieldRemainingTime.setText(Long.toString(deal
							.getRemainingTime()));
				} else {
					deal = retireveDealByName(deal.getName());
					textFieldRemainingTime.setText("Deal has expired");
					JOptionPane.showMessageDialog(DealView.this, "Bidding is over. This deal now belongs to " + deal.getOwner());
					timer.stop();
					DealView.this.dispose();
				}
			}
		};
		timer = new Timer(1000, remainingTimeActionListener);
		timer.start();
		socket = MainFrame.connectToReportingServer();
		if (socket != null) {
			ServerThread thread = new ServerThread(socket, new Runnable() {

				@Override
				public void run() {
					deal = retireveDealByName(deal.getName());
					textFieldDealPrice.setText(Float.toString(deal.getPrice()));
				}

			});
			thread.start();
		}
	}

	private void initialize() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 450, 437);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
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
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
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
						FormFactory.DEFAULT_ROWSPEC, }));
		{
			JLabel lblNewLabel = new JLabel("Name");
			contentPanel.add(lblNewLabel, "2, 4");
		}
		{
			textField = new JTextField(deal.getName());
			textField.setEditable(false);
			contentPanel.add(textField, "4, 4, 14, 1, fill, default");
			textField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Description");
			contentPanel.add(lblNewLabel_1, "2, 6");
		}
		{
			JTextArea textAreaDealDesc = new JTextArea(deal.getDesc());
			textAreaDealDesc.setEditable(false);
			contentPanel.add(textAreaDealDesc, "4, 6, 14, 1, fill, fill");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Price");
			contentPanel.add(lblNewLabel_2, "2, 8");
		}
		{
			textFieldDealPrice = new JTextField(Float.toString(deal.getPrice()));
			textFieldDealPrice.setEditable(false);
			contentPanel.add(textFieldDealPrice, "4, 8, 14, 1, fill, default");
			textFieldDealPrice.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Category");
			contentPanel.add(lblNewLabel_3, "2, 10");
		}
		{
			textField_2 = new JTextField(deal.getCategory());
			textField_2.setEditable(false);
			contentPanel.add(textField_2, "4, 10, 14, 1, fill, default");
			textField_2.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Creation date");
			contentPanel.add(lblNewLabel_4, "2, 12");
		}
		{
			textField_3 = new JTextField(deal.getStartDate().toString());
			textField_3.setEditable(false);
			contentPanel.add(textField_3, "4, 12, 14, 1, fill, default");
			textField_3.setColumns(10);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("owner");
			contentPanel.add(lblNewLabel_5, "2, 14");
		}
		{
			textFieldOwner = new JTextField(deal.getOwner());
			textFieldOwner.setEditable(false);
			contentPanel.add(textFieldOwner, "4, 14, 14, 1, fill, default");
			textFieldOwner.setColumns(10);
		}
		{
			JLabel lblEnterNewPrice = new JLabel("Enter new price");
			contentPanel.add(lblEnterNewPrice, "2, 16");
		}
		{
			spinnerDealNewPrice = new JSpinner();
			spinnerDealNewPrice.setModel(new SpinnerNumberModel(new Integer(0),
					new Integer(0), null, new Integer(1)));
			contentPanel.add(spinnerDealNewPrice, "4, 16, 14, 1");
		}
		{
			{
				JLabel lblNewLabel_6 = new JLabel("Remaining Time");
				contentPanel.add(lblNewLabel_6, "2, 18, right, default");
			}
		}
		JButton btnBid = new JButton("Bid");
		btnBid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loggedInUser == null) {
					JOptionPane.showMessageDialog(DealView.this, "You have to log in before you can start bidding");
				} else {
					int dealPrice = (int) spinnerDealNewPrice.getValue();
					if (deal.getPrice() >= dealPrice) {
						JOptionPane.showMessageDialog(DealView.this, "Bid value must be higher than " + deal.getPrice());
						return;
					}
					deal.setPrice(dealPrice);
					deal.setOwner(loggedInUser.getLogin());
					DealDAO dealDAO = new DealDAO();
					dealDAO.update(deal);
					textFieldDealPrice.setText(Float.toString(dealPrice));
					textFieldOwner.setText(loggedInUser.getLogin());

					if (socket != null) {
						MainFrame.notifyServer(socket);
					}
					DealView.this.repaint();
				}
			}
		});
		{
			textFieldRemainingTime = new JTextField();
			textFieldRemainingTime.setEditable(false);
			contentPanel.add(textFieldRemainingTime,
					"4, 18, 14, 1, fill, default");
			textFieldRemainingTime.setColumns(10);
		}
		contentPanel.add(btnBid, "4, 20, 14, 1");
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DealView.this.dispose();
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
						DealView.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private Deal retireveDealByName(String dealName) {
		DealDAO dealDAO = new DealDAO();
		List<Deal> dealList = dealDAO.retrieve("name", dealName);
		if (dealList.size() > 0) {
			return dealList.get(0);
		}
		return null;
	}

}
