package tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals;

import java.util.Date;

public class Deal {

	private int id;
	private String name;
	private String desc;
	private float price;
	private String category;
	private final String owner;
	private Date startDate;

	public Deal(String name, String desc, float price, String category, Date startDate, String owner) {
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.category = category;
		this.startDate = startDate;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOwnerId() {
		return owner;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getId() {
		return id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
