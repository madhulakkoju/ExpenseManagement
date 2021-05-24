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
		
		users.put("madhu@gmail.com", new User("madhu@gmail.com","madhu","8686156086"));
		users.put("madhu1@gmail.com", new User("madhu1@gmail.com","madhu_1","8686156087"));
		users.put("madhu2@gmail.com", new User("madhu2@gmail.com","madhu_2","8686156088"));
		
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECG",TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Monthly Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("EsdfsfCG",TransactionType.EXPENSE , TransactionCategory.JOB, PaymentMode.NET_BANKING,125.0,"Monthly Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECGsdf",TransactionType.INTEREST, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Monthly Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECfffG",TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Monthly Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECsfsfsG",TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Monthly Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECG",12,06,2020,TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,5000.0,"Periodic Pay" ));
		users.get("madhu@gmail.com").addTransaction(new Transaction("ECG",12,05,2020,TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,5000.0,"Month- Pay" ));
		
	}
	
	public static UserImpl getObject() {
		if(impl == null)
			impl = new UserImpl();
		return impl;
	}
	
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
