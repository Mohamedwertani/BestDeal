package tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.AbstractDAO;

public final class UserDAO extends AbstractDAO<User> {

	public UserDAO() {
		super();
	}

	@Override
	public boolean create(User object) {
		try {
			String sql = "insert into user(`firstName`, `lastName`, `login`, `pwd`) values(?, ?, ?, ?)";
			PreparedStatement prepared = connexion.prepareStatement(sql);
			prepared.setString(1, object.getFirstName());
			prepared.setString(2, object.getLastName());
			prepared.setString(3, object.getLogin());
			prepared.setString(4, object.getPwd());
			return prepared.executeUpdate() > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> retrieve(String key, String value) {
		List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement prepared =
					connexion.prepareStatement("select * from user where " + key + " = ?");
			prepared.setString(1, value);
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				userList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userList;
	}

	@Override
	public List<User> retrieveAll() {
		List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement prepared = connexion.prepareStatement("select * from user");
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				userList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userList;
	}

	private User mapResultSet(ResultSet resultSet) throws SQLException {
		return new User(resultSet.getInt("id"),
				resultSet.getString("firstName"),
				resultSet.getString("lastName"),
				resultSet.getString("login"),
				resultSet.getString("pwd"));
	}

	@Override
	public boolean update(User object) {
		try {
			String sql = "update user set firstName = ?, lastName = ?, login = ?, pwd = ? where id = ?";
			PreparedStatement prepared  = connexion.prepareStatement(sql);
			prepared.setString(1, object.getFirstName());
			prepared.setString(2, object.getLastName());
			prepared.setString(3, object.getLogin());
			prepared.setString(4, object.getPwd());
			prepared.setInt(5, object.getId());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(User object) {
		try {
			PreparedStatement prepared  = connexion.prepareStatement("delete from user where id = ?");
			prepared.setInt(1, object.getId());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAll() {
		try {
			PreparedStatement prepared  = connexion.prepareStatement("delete from user");
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
