package com.shadow.concept.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.shadow.concept.generics.GenericEntity;

@Entity
public class Product extends GenericEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private Category category2;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory2() {
		return category2;
	}

	public void setCategory2(Category category2) {
		this.category2 = category2;
	}

}
