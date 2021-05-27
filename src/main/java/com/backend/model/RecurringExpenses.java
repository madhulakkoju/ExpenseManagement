package com.backend.model;

import java.util.Date;
import java.util.TreeSet;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.backend.util.Utilities;

public class RecurringExpenses implements Comparable<RecurringExpenses> {
	private static Logger log = Logger.getLogger(RecurringExpenses.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id= (long)0.0;
	
	String name;
	int periodicity;
	String participant;
	
	TreeSet<Transaction> transactions;
	RecurringExpenseCategory category;
	RecurringExpenseType type;
	int[] creationDate;
	String remarks; 
	
	public RecurringExpenses(String name, int periodicity, String participant,RecurringExpenseCategory category, RecurringExpenseType type, String remarks) {
		super();
		this.name = name;
		this.periodicity = periodicity;
		this.participant = participant;
		this.category = category;
		this.type = type;
		this.remarks = remarks;
		transactions = new TreeSet<>();
		log.debug("Created Recurring Expense "+this.name);
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		this.creationDate = new int[3];
		this.creationDate[0] = Integer.parseInt(dt[0]);
		this.creationDate[1] = Integer.parseInt(dt[1]);
		this.creationDate[2] = Integer.parseInt(dt[2]);
	}
	
	public RecurringExpenses(String name, int periodicity, String participant, String remarks) {
		super();
		this.name = name;
		this.periodicity = periodicity;
		this.participant = participant;
		this.category = category;
		this.type = type;
		this.remarks = remarks;
		transactions = new TreeSet<>();
		log.debug("Created Recurring Expense "+this.name);
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		this.creationDate = new int[3];
		this.creationDate[0] = Integer.parseInt(dt[0]);
		this.creationDate[1] = Integer.parseInt(dt[1]);
		this.creationDate[2] = Integer.parseInt(dt[2]);
	}
	
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public Transaction getLatestTransaction() {
		if(transactions.isEmpty())
			return null;
		return transactions.last();
	}
	
	public Transaction[] getAllTransactions(){
		return transactions.toArray(new Transaction[0]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RecurringExpenseCategory getCategory() {
		return category;
	}

	public void setCategory(RecurringExpenseCategory category) {
		this.category = category;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getPeriodicity() {
		return periodicity;
	}

	public RecurringExpenseType getType() {
		return type;
	}

	public int[] getCreationDate() {
		return creationDate;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}
	
	public int getYr() {
		return creationDate[2];
	}
	
	public int getMonth() {
		return creationDate[1];
	}
	
	public int getDate() {
		return creationDate[0];
	}

	@Override
	public int compareTo(RecurringExpenses tr) {
		if(this.getYr() > tr.getYr()) {
			return 1;
		}
		if(this.getYr() < tr.getYr()) {
			return -1;
		}
		if(this.getMonth() > tr.getMonth()) {
			return 1;
		}
		if(this.getMonth() < tr.getMonth()) {
			return -1;
		}
		if(this.getDate() > tr.getDate()) {
			return 1;
		}
		if(this.getDate() < tr.getDate()) {
			return -1;
		}
		return this.getName().compareTo(tr.getName());
	}
	
	public void print() {
		log.debug("Recurring Expense : "+this.name);
		for(Transaction tr: this.getAllTransactions()) {
			tr.print();
		}
	}

	public long getId() {
		return id;
	}

	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;		
	}

	public void setType(RecurringExpenseType type) {
		this.type=type;
	}
	
	
}
