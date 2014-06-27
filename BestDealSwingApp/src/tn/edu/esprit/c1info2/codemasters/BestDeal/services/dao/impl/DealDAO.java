package tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.AbstractDAO;

public class DealDAO extends AbstractDAO<Deal> {

	@Override
	public boolean create(Deal object) {
		try {
			String sql = "insert into deal(`name`, `desc`, `price`, `category`, `owner`, `startDate`)" +
					"values(?, ?, ?, ?, ?, ?)";
			PreparedStatement prepared = connexion.prepareStatement(sql);
			prepared.setString(1, object.getName());
			prepared.setString(2, object.getDesc());
			prepared.setFloat(3, object.getPrice());
			prepared.setString(4, object.getCategory());
			prepared.setString(5, object.getOwnerId());
			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
			java.util.Date date = new java.util.Date();
			prepared.setString(6, dateFormat.format(date));
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Deal> retrieve(String key, String value) {
		List<Deal> dealList = new ArrayList<Deal>();
		try {
			PreparedStatement prepared =
					connexion.prepareStatement("select * from deal where " + key + " = ?");
			prepared.setString(1, value);
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				dealList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return dealList;
	}

	@Override
	public List<Deal> retrieveAll() {
		List<Deal> dealList = new ArrayList<Deal>();
		try {
			PreparedStatement prepared = connexion.prepareStatement("select * from deal");
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				dealList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return dealList;
	}

	private Deal mapResultSet(ResultSet resultSet) throws SQLException {
		return new Deal(resultSet.getString("name"),
				resultSet.getString("desc"),
				resultSet.getFloat("price"),
				resultSet.getString("category"),
				stringToJavaDate(resultSet.getString("startDate")),
				resultSet.getString("owner"));
	}

	private Date stringToJavaDate(String source) {
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Deal object) {
		try {
			String sql = "update deal set name = ?, desc = ?, price = ?, category = ?, owner = ? where id = ?";
			PreparedStatement prepared  = connexion.prepareStatement(sql);
			prepared.setString(1, object.getName());
			prepared.setString(2, object.getDesc());
			prepared.setFloat(3, object.getPrice());
			prepared.setString(4, object.getCategory());
			prepared.setString(5, object.getOwnerId());
			prepared.setInt(6, object.getId());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Deal object) {
		try {
			PreparedStatement prepared  = connexion.prepareStatement("delete from deal where id = ?");
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
			PreparedStatement prepared  = connexion.prepareStatement("delete from deal");
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
