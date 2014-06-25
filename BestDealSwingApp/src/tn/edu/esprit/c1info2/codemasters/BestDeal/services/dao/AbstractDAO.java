package tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao;

import java.sql.Connection;
import java.util.List;

import tn.edu.esprit.c1info2.codemasters.BestDeal.utilities.MysqlUtilities;

public abstract class AbstractDAO<T> {

	protected Connection connexion;

	public AbstractDAO() {
		connexion = MysqlUtilities.giveMeConnectionConfigured();
	}

	public abstract boolean create(T object);
	public abstract List<T> retrieve(String key, String value);
	public abstract List<T> retrieveAll();
	public abstract boolean update(T object);
	public abstract boolean delete(T object);
	public abstract boolean deleteAll();

}
