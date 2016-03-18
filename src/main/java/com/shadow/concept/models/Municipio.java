/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadow.concept.models;

import com.shadow.concept.generics.GenericEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Shadow
 */
@Entity
public class Municipio extends GenericEntity {

    private String nome;

    @ManyToOne
    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
