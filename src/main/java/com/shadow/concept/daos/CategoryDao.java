package com.shadow.concept.daos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.shadow.concept.models.Category;

public class CategoryDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	public List<Category> all() {
		return manager.createQuery("select c from Category c", Category.class).getResultList();
	}

	public void save(Category category) {
		manager.persist(category);
	}

	public Category findById(Integer id) {
		return manager.find(Category.class, id);
	}

	public void remove(Category category) {
		manager.remove(category);
	}

	public void update(Category category) {
		manager.merge(category);
	}

}
