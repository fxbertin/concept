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

}
