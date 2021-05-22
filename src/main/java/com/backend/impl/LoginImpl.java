package com.backend.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.backend.model.LoginDetails;

public class LoginImpl {
	
	private static Logger log = Logger.getLogger(LoginImpl.class);
	
	Map<String,LoginDetails> logins;
	private static LoginImpl impl;
	private LoginImpl(){
		this.logins = new HashMap<>(10);
		logins.put("madhu@gmail.com", new LoginDetails("madhu@gmail.com","madhuPassword"));
		logins.put("madhu1@gmail.com", new LoginDetails("madhu1@gmail.com","madhu1Password"));
		logins.put("madhu2@gmail.com", new LoginDetails("madhu2@gmail.com","madhu2Password"));
	}
	
	public static LoginImpl getObject() {
		if(impl == null)
			impl = new LoginImpl();
		return impl;
	}
	
	public LoginDetails createLogin(String email, String password) {
		LoginDetails login = logins.get(email);
		if(login == null) {
			login = new LoginDetails(email,password);
			logins.put(email, login);
		}
		return login;
	}
	
	public LoginDetails getLoginDetails(String email) {
		return logins.get(email);
	}
	
	public LoginDetails removeDetails(String email) {
		return logins.remove(email);
	}
	
	
	public void print() {
		log.debug("--------------------LOGINS--------------");
		for(LoginDetails login: logins.values()) {
			login.print();
		}
	}
	
	
	
}
