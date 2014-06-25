package tn.edu.esprit.c1info2.codemasters.BestDeal.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlUtilities {
	private static Connection myConnection;
	private static String url = "jdbc:mysql://localhost:3306/bestDealDB";
	private static String user = "root";
	private static String password = "";

	public static Connection giveMeConnectionConfigured() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myConnection;
	}
}
