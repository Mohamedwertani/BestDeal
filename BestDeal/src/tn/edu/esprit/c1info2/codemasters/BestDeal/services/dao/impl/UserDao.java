package tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.utilities.MysqlUtilities;

public class UserDao {
	private Statement statement;

	public UserDao() {
		try {
			statement = MysqlUtilities.giveMeConnectionConfigured()
					.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addUser(User user) {
		boolean b = false;
		try {
			String sql = "insert into user(firstName , lastName) values ('"
					+ user.getFirstName() + "','" + user.getLastName() + "')";
			statement.executeUpdate(sql);
			b = true;
		} catch (Exception e) {
		}
		return b;
	}

	public boolean removeUser(User user) {
		try {
			statement.executeUpdate("remove from user where id=" + user.getId());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
