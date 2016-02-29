package com.shadow.concept.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Messages;

import com.shadow.concept.daos.UserDao;
import com.shadow.concept.models.User;

@Named
@RequestScoped
@Transactional
public class Register {

	private User user;

	private UserDao dao;
	
	public Register() {
	}

	@Inject
	public Register(User user, UserDao dao) {
		this.user = user;
		this.dao = dao;
	}

	public void submit() {
		try {
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
			dao.save(user);
			Messages.addGlobalInfo("Usuário cadastrado com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Falha ao salvar: {0}", e.getMessage());
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

}