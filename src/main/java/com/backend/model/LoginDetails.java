package com.backend.model;

import org.apache.log4j.Logger;

public class LoginDetails {
	
	private static Logger log = Logger.getLogger(LoginDetails.class);
	String email;
	String password;
	
	public LoginDetails(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void print() {
		log.debug("Email : "+this.email+" "+"password : "+this.password);
	}
}
