package tn.edu.esprit.c1info2.codemasters.BestDeal.domain;

import java.util.Date;

public class Deal {

	private int id;
	private String name;
	private String desc;
	private float price;
	private final int owner;
	private Date startDate;

	public Deal(String name, String desc, float price, int owner) {
		this.name = name;
		this.desc = desc;
		this.price = price;
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

	public int getOwnerId() {
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

}
