package com.shadow.concept.controllers;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.shadow.concept.generics.GenericController;
import com.shadow.concept.models.Category;

@Named
@ViewScoped
@Transactional
public class CategoryController extends GenericController<Category> implements Serializable {

    private static final long serialVersionUID = 1L;

}
