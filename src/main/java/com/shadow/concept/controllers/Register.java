package com.shadow.concept.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Messages;

import com.shadow.concept.models.User;
import com.shadow.concept.service.UserService;

@Named
@RequestScoped
public class Register {

	private User user;

	@EJB
	private UserService service;

	@PostConstruct
	public void init() {
		user = new User();
	}

	public void submit() {
		try {
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		    service.create(user);
			Messages.addGlobalInfo("Registration suceed, new user ID is: {0}", user.getId());
		} catch (RuntimeException e) {
			Messages.addGlobalError("Registration failed: {0}", e.getMessage());
			e.printStackTrace(); // TODO: logger.
		}
	}

	public User getUser() {
		return user;
	}

}