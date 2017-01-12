package com.test.mongoconnection.models;

public class CupDO extends BaseMongoDO {

	private float ounces;
	private String name;

	public float getOunces() {
		return ounces;
	}

	public void setOunces(float ounces) {
		this.ounces = ounces;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
