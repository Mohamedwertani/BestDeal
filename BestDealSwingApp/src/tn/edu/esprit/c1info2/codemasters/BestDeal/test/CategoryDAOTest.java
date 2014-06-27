package tn.edu.esprit.c1info2.codemasters.BestDeal.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals.Category;
import tn.edu.esprit.c1info2.codemasters.BestDeal.services.dao.impl.CategoryDAO;

public class CategoryDAOTest {

	private CategoryDAO dao = new CategoryDAO();

	@Test
	public void test() {
		Category c = new Category("TestCategory");
		dao.create(c);
		List<Category> listCategory = dao.retrieve("name", "TestCategory");
		Assert.assertTrue(listCategory.size() == 1);
		Assert.assertEquals("TestCategory", listCategory.get(0).getName());
	}

	@After
	public void cleanUp() {
		dao.deleteAll();
	}
}
