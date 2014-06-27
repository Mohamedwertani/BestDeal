package tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Category;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.AbstractDAO;

public class CategoryDAO extends AbstractDAO<Category> {

	@Override
	public boolean create(Category object) {
		try {
			String sql = "insert into category(`name`) values(?)";
			PreparedStatement prepared = connexion.prepareStatement(sql);
			prepared.setString(1, object.getName());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Category> retrieve(String key, String value) {
		List<Category> categoryList = new ArrayList<Category>();
		try {
			PreparedStatement prepared =
					connexion.prepareStatement("select * from category where " + key + " = ?");
			prepared.setString(1, value);
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				categoryList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return categoryList;
	}

	@Override
	public List<Category> retrieveAll() {
		List<Category> categoryList = new ArrayList<Category>();
		try {
			PreparedStatement prepared = connexion.prepareStatement("select * from category");
			ResultSet resultSet = prepared.executeQuery();
			while (resultSet.next()) {
				categoryList.add(mapResultSet(resultSet));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return categoryList;
	}

	private Category mapResultSet(ResultSet resultSet) throws SQLException {
		return new Category(resultSet.getString("name"));
	}

	@Override
	public boolean update(Category object) {
		try {
			String sql = "update category set name = ? where name = ?";
			PreparedStatement prepared  = connexion.prepareStatement(sql);
			prepared.setString(1, object.getName());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Category object) {
		try {
			PreparedStatement prepared  = connexion.prepareStatement("delete from category where name = ?");
			prepared.setString(1, object.getName());
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAll() {
		try {
			PreparedStatement prepared  = connexion.prepareStatement("delete from category");
			return prepared.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
