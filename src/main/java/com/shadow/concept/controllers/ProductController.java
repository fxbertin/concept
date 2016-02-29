package com.shadow.concept.controllers;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;

import com.shadow.concept.generics.GenericController;
import com.shadow.concept.generics.GenericLazyDataModel;
import com.shadow.concept.models.Product;

@Named
@ViewScoped
@Transactional
public class ProductController extends GenericController<Product> implements Serializable {

	private static final long serialVersionUID = 1L;

	private LazyDataModel<Product> players;

	public LazyDataModel<Product> getPlayers() {
		if (players == null) {
			players = new GenericLazyDataModel<Product>(dao, "name,category");
		}

		return players;
	}

	public void setPlayers(LazyDataModel<Product> players) {
		this.players = players;
	}

}
