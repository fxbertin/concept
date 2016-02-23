package com.shadow.concept.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import com.shadow.concept.daos.CategoryDao;
import com.shadow.concept.models.Category;

@Named
@ViewScoped
@Transactional
public class CategoryController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CategoryDao categoryDao;
	private Category category = new Category();
	private List<Category> categoryList = new ArrayList<>();

	public List<Category> getCategoryList() {
		return this.categoryList;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return this.category;
	}

	@PostConstruct
	private void postConstruct() {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("entity");
		if (object != null)
			category = (Category) object;
		else 
			categoryList.addAll(categoryDao.findAll());
	}

	public String show(Category c){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("entity", c);
		return "/app/categoryForm.xhtml?faces-redirect=true";
	}

	public String save() {
		if (category.getId() == null)
			categoryDao.save(category);
		else 
			categoryDao.update(category);
		return "/app/categoryList.xhtml?faces-redirect=true";
	}

	public String update(Integer id) {
		category.setId(id);
		categoryDao.update(category);
		return "/category/list?faces-redirect=true";
	}

	public String remove(Integer id) {
		Category category = categoryDao.getById(id);
		categoryDao.delete(category);
		return "/app/categoryList.xhtml?faces-redirect=true";
	}

}
