/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadow.concept.controllers;

import com.shadow.concept.generics.GenericController;
import com.shadow.concept.models.Pais;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 *
 * @author Shadow
 */
@Named
@ViewScoped
@Transactional
public class MunicipioController extends GenericController<Pais>{
    
}
