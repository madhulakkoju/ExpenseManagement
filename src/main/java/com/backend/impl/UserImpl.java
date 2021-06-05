package com.backend.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.backend.model.PaymentMode;
import com.backend.model.Transaction;
import com.backend.model.TransactionCategory;
import com.backend.model.TransactionType;
import com.backend.model.User;

public class UserImpl {
	private static Logger log = Logger.getLogger(UserImpl.class);
	Map<String,User> users;
	private static UserImpl impl ;
	
	private UserImpl() {
		this.users = new HashMap<>();
		this.users.put("madhu@gmail.com", new User("madhu@gmail.com","Madhu Lakkoju", "8686156086"));
	}
	// Single Instance returning method
	public static UserImpl getObject() {
		if(impl == null)
			impl = new UserImpl();
		return impl;
	}
	// User Creating Support Methods
	public User createUser(String email, String mobile) {
		User user = users.get(email);
		if(user == null) {
			user = new User(email,mobile);
			users.put(email, user);
		}
		return user;
	}
	
	public User createUser(String email, String name, String mobile) {
		User user = users.get(email);
		if(user == null) {
			user = new User(email,name,mobile);
			users.put(email, user);
		}
		return user;
	}
	
	public User getUser(String email){
		return users.get(email);
	}
	
	public User removeUser(String email) {
		return users.remove(email);
	}
	
	
	public void print() {
		log.debug("-----------------ALL USERS--------------");
		for(User user:users.values()) {
			user.print();
		}
		log.debug("---------------DONE USERS---------------");
	}
	
	
	
	
	
}
