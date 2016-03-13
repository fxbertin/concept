package com.shadow.concept.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.shadow.concept.daos.CategoryDao;
import com.shadow.concept.generics.GenericController;

import com.shadow.concept.models.Category;
import com.shadow.concept.models.Product;

@Named
@ViewScoped
@Transactional
public class ProductController extends GenericController<Product> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoryDao categoryDao;

    public List<Category> getCategories() {
        return categoryDao.findAll();
    }

}
