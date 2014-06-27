package tn.edu.esprit.c1info2.codemasters.BestDeal.test;

import junit.framework.Assert;

import org.junit.Test;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.UserDAO;

public class UserDAOTest {

	@Test
	public void basicTest() {
		UserDAO dao = new UserDAO();
		dao.deleteAll();
		// Create a simple user
		User user = new User("Seifeddine", "Dridi", "seif", "password");
		Assert.assertEquals(true, dao.create(user));
		// Query table
		user = dao.retrieve("firstName", "Seifeddine").get(0);
		Assert.assertTrue(user != null);
		// Update row
		user.setFirstName("NewFirstName");
		Assert.assertEquals(true, dao.update(user));
		Assert.assertEquals(true, dao.delete(user));
		dao.deleteAll();
	}

	@Test
	public void testRetrieve() {
		UserDAO dao = new UserDAO();
		dao.deleteAll();
		dao.create(new User("Seifeddine", "Dridi", "seif1", "password"));
		dao.create(new User("Seifeddine", "Dridi", "seif2", "password"));
		dao.create(new User("Seifeddine", "Dridi", "seif3", "password"));
		Assert.assertEquals(3, dao.retrieveAll().size());
		dao.deleteAll();
	}

}
