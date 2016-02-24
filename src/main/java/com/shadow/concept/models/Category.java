package com.shadow.concept.models;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

import com.shadow.concept.generics.GenericEntity;

@Entity
public class Category extends GenericEntity {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String name;
	@NotBlank
	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
