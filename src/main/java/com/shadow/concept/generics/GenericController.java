package com.shadow.concept.generics;

import java.beans.Introspector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.shadow.concept.models.Category;

@SuppressWarnings("unchecked")
public abstract class GenericController<T extends GenericEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private T entidade;

	@Inject
	protected GenericDAO<T> dao;

	protected String formUrl, tableUrl;

	private List<T> list = new ArrayList<>();

	@PostConstruct
	private void postConstruct() {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("entity");
		if (object != null)
			setEntidade((T) object);
		else
			getList().addAll(dao.findAll());

		setUrls();
	}

	private void setUrls() {
		String className = Introspector.decapitalize(entidade.getClass().getSimpleName());
		formUrl = String.format("/app/%sForm.xhtml?faces-redirect=true", className);
		tableUrl = String.format("/app/%sList.xhtml?faces-redirect=true", className);
	}

	public String show(Category c) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("entity", c);
		return formUrl;
	}

	public String save() {
		if (getEntidade().getId() == null)
			dao.save(getEntidade());
		else
			dao.update(getEntidade());
		return tableUrl;
	}

	public String update() {
		dao.update(getEntidade());
		return formUrl;
	}

	public String remove(Long id) {
		setEntidade(dao.getById(id));
		dao.delete(getEntidade());
		return tableUrl;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}
}
