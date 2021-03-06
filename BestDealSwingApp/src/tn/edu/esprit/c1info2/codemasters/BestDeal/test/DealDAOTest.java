package tn.edu.esprit.c1info2.codemasters.BestDeal.test;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Deal;
import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.users.User;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.DealDAO;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.UserDAO;

public class DealDAOTest {

	@Test
	public void test() {
		DealDAO dao = new DealDAO();
		UserDAO userDAO = new UserDAO();
		List<User> userList = userDAO.retrieveAll();
		if (userList.size() == 0) {
			fail("Cannot insert new deal if table user is empty");
		}
		for (User user : userList) {
			dao.create(new Deal(0, "TV", "A television", 1000, "Electronics", new Date(), 300000, user.getLogin()));
		}
		dao.deleteAll();
	}

}