package com.backend.model;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class NetAmount implements Comparable<NetAmount>, Serializable {
	
	private static final long serialVersionUID = -3562329689138530442L;
	private static Logger log = Logger.getLogger(NetAmount.class);

	int year;
	int month;
	
	double income;
	double expenditure;
	double savings;
	
	public NetAmount(int year, int month) {
		super();
		this.year = year;
		this.month = month;
	}

	public NetAmount(Transaction transaction) {
		super();
		this.year = transaction.getDateOfTransaction()[2];
		this.month = transaction.getDateOfTransaction()[1];
		this.addTransaction(transaction);
	}
	
	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

	public double getSavings() {
		return savings;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}
	
	public void addTransaction(Transaction transaction){
		switch(transaction.getType()) {
			case INCOME:
				this.addIncome(transaction.getAmount());
				break;
			case EXPENSE:
				this.addExpense(transaction.getAmount());
				break;
			case INTEREST:
				this.addExpense(transaction.getAmount());
				break;
			default :
				break;
		}
	}
	
	public void addExpense(double amt) {
		this.expenditure += amt;
		this.savings -= amt;
	}
	
	
	public void addIncome(double amt) {
		this.income+=amt;
		this.savings += amt;
	}
	
	@Override
	public int compareTo(NetAmount amt) {
		if(this.getYear() > amt.getYear()) {
			return 1;
		}
		if(this.getYear()<amt.getYear()) {
			return -1;
		}
		if(this.getMonth() > amt.getMonth()) {
			return 1;
		}
		if(this.getMonth() < amt.getMonth()) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		NetAmount amt = (NetAmount) obj;
		return ( this.getYear()==amt.getYear() && amt.getMonth()==this.getMonth());
	}
	
	public void print() {
		log.debug(this.year+" "+this.month+" "+this.income+" "+this.expenditure+" "+this.savings);
	}
	
}
