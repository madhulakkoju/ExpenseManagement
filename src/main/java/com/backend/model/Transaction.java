package com.backend.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.backend.util.Utilities;

public class Transaction implements Comparable<Transaction>, Serializable {

	private static final long serialVersionUID = -6809231470598901956L;

	private static Logger log = Logger.getLogger(Transaction.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id = (long) 0;
	
	int[] dateOfTransaction; //date-month-yr
	TransactionType type;
	TransactionCategory category;
	PaymentMode mode;
	double amount;
	String remarks;
	String participant;
	
	public Transaction(String participant,TransactionType type, TransactionCategory category, PaymentMode mode, double amount,
			String remarks) {
		super();
		this.type = type;
		this.category = category;
		this.mode = mode;
		this.amount = amount;
		this.remarks = remarks;
		this.participant = participant;
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		this.dateOfTransaction = new int[3];
		this.dateOfTransaction[0] = Integer.parseInt(dt[0]);
		this.dateOfTransaction[1] = Integer.parseInt(dt[1]);
		this.dateOfTransaction[2] = Integer.parseInt(dt[2]);
	}

	public Transaction() {
		super();
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		this.dateOfTransaction = new int[3];
		this.dateOfTransaction[0] = Integer.parseInt(dt[0]);
		this.dateOfTransaction[1] = Integer.parseInt(dt[1]);
		this.dateOfTransaction[2] = Integer.parseInt(dt[2]);
	}
	
	public Transaction(int mon,int yr) {
		super();
		
		this.dateOfTransaction = new int[3];
		this.dateOfTransaction[0] = 0;
		this.dateOfTransaction[1] = mon;
		this.dateOfTransaction[2] = yr;
	}
	
	public Transaction(long id) {
		this.id = id;
	}
	
	public Transaction(int yr) {
		super();
		this.dateOfTransaction = new int[3];
		this.dateOfTransaction[0] = 0;
		this.dateOfTransaction[1] = 0;
		this.dateOfTransaction[2] = yr;
	}
	
	public Transaction(String participant, int dd,int mm,int yy, TransactionType type, TransactionCategory category, PaymentMode mode,
			double amount, String remarks) {
		super();
		this.dateOfTransaction = new int[3];
		this.dateOfTransaction[0]=dd;
		this.dateOfTransaction[1]=mm;
		this.dateOfTransaction[2]=yy;
		this.type = type;
		this.category = category;
		this.mode = mode;
		this.amount = amount;
		this.remarks = remarks;
		this.participant = participant;
	}

	public int[] getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(int[] dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	
	public void setDateOfTransaction(int dd,int mm,int yy) {
		this.dateOfTransaction[0]=dd;
		this.dateOfTransaction[1]=mm;
		this.dateOfTransaction[2]=yy;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}

	public PaymentMode getMode() {
		return mode;
	}

	public void setMode(PaymentMode mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public int getYr() {
		return this.getDateOfTransaction()[2];
	}
	
	public int getMonth() {
		return this.getDateOfTransaction()[1];
	}
	
	public int getDate() {
		return this.getDateOfTransaction()[0];
	}
	
	public void print() {
		log.debug("TRANSACT "+Arrays.toString(dateOfTransaction)+" "+amount+" "+participant+" "+category+" "+type+" "+mode +" "+remarks);
	}
	
	public long getId() {
		return this.id;
	}
	
	@Override
	public int compareTo(Transaction tr) {
		return -1;
	}
	@Override
	public boolean equals(Object obj) {
		return false;
	}

	public String getParticipant() {
		return participant;
	}
	
}
