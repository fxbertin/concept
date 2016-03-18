/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadow.concept.controllers;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.shadow.concept.daos.PaisDao;
import com.shadow.concept.generics.GenericController;
import com.shadow.concept.models.Estado;
import com.shadow.concept.models.Pais;

/**
 *
 * @author Shadow
 */
@Named
@ViewScoped
@Transactional
public class EstadoController extends GenericController<Estado> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Pais> paises;
    
    @Inject
    private PaisDao paisDao;

    public List<Pais> getPaises() {
        if (paises == null) {
            paises = paisDao.findAll();
        }
        return paises;
    }
}
