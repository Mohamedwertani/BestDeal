package tn.edu.esprit.c1info2.codemasters.BestDeal.domain.deals;

import java.util.Date;

public class Deal {

	private int id;
	private String name;
	private String desc;
	private float price;
	private String category;
	private String owner;
	private Date startDate;
	private int duration;

	public Deal(int id, String name, String desc, float price, String category, Date startDate, int duration, String owner) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.category = category;
		this.startDate = startDate;
		this.duration = duration;
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

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
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

	public int getDealDuration() {
		return duration;
	}

	public void setDealDuration(int dealDuration) {
		this.duration = dealDuration;
	}

	public boolean enabled() {
		Date expirationDate = new Date(startDate.getTime() + duration);
		Date currentDate = new Date();
		return expirationDate.after(currentDate);
	}

	public long getRemainingTime() {
		Date expirationDate = new Date(startDate.getTime() + duration);
		Date currentDate = new Date();
		return (expirationDate.getTime() - currentDate.getTime()) / 1000;
	}

}
