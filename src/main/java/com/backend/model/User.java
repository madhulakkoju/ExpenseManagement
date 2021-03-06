package com.backend.model;
import java.io.Serializable;
import java.util.Date;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import com.backend.util.Utilities;

public class User implements Serializable {
	private static final long serialVersionUID = -7696472153609164923L;
	private static Logger log = Logger.getLogger(User.class);
	String email, name, mobile;
	Date accountCreationDate;
	TreeSet<Transaction> transactions;
	TreeSet<RecurringExpenses> recurringExpenses;
	TreeSet<RecurringExpenses> closedRecurringExpenses;
	MonthlyAmounts monthly;
	NetAmount currentMonth;
	//Constructors
	public User(){}
	public User(String email, String name, String mobile){
		this.email = email;
		this.name = name;
		this.mobile = mobile;
		this.accountCreationDate = new Date();
		this.transactions = new TreeSet<>();
		this.monthly = new MonthlyAmounts();
		recurringExpenses = new TreeSet<>();
		closedRecurringExpenses = new TreeSet<>();
		log.debug("New User Created "+email);
	}
	public User(String email, String mobile) {
		super();
		this.email = email;
		this.mobile = mobile;
		this.name = email;
		this.accountCreationDate = new Date();
		this.transactions = new TreeSet<>();
		this.monthly = new MonthlyAmounts();
		recurringExpenses = new TreeSet<>();
		closedRecurringExpenses = new TreeSet<>();
		log.debug("New User Created "+email);
	}	
	// Support Methods for Transactions
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
		monthly.addTransaction(transaction);
	}
	public void addTransaction(Transaction transaction, String nameOfRecurringExpense) {
		this.transactions.add(transaction);
		monthly.addTransaction(transaction);
		this.getRecurringExpense(nameOfRecurringExpense).addTransaction(transaction);
	}
	public NetAmount currentMonth() {
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		int cYr =  Integer.parseInt(dt[2]);
		int cMn = Integer.parseInt(dt[1]);
		NetAmount current = monthly.latestMonth();
		if(current == null || current.getYear() != cYr || current.getMonth() != cMn ) {
			current = MonthlyAmounts.dummyMonth;
			current.year = cYr;
			current.month = cMn;
		}
		return current;
	}
	// Support Methods for Recurring Expenses
	public RecurringExpenses[] getAllRecurringExpenses() {
		return recurringExpenses.toArray(new RecurringExpenses[0]);
	}
	public TreeSet<RecurringExpenses> getRecurringExpenses() {
		return recurringExpenses;
	}
	public RecurringExpenses getRecurringExpense(String name) {
		for(RecurringExpenses exp: recurringExpenses.toArray(new RecurringExpenses[0])) {
			if(exp.getName().equals(name))
				return exp;
		}
		return null;
	}
	public RecurringExpenses getRecurringExpense(long id) {
		for(RecurringExpenses exp: recurringExpenses.toArray(new RecurringExpenses[0])) {
			if(exp.getId()==id)
				return exp;
		}
		return null;
	}
	public RecurringExpenses getClosedRecurringExpense(String name) {
		for(RecurringExpenses exp: closedRecurringExpenses.toArray(new RecurringExpenses[0])) {
			if(exp.getName().equals(name))
				return exp;
		}
		return null;
	}
	public RecurringExpenses addRecurringExpense(RecurringExpenses exp) {
		recurringExpenses.add(exp);
		return exp;
	}
	public Transaction getTransaction(long id) {
		return transactions.ceiling( new Transaction(id) );
	}
	public RecurringExpenses[] getClosedRecurringExpenses() {
		return closedRecurringExpenses.toArray(new RecurringExpenses[0]);
	}
	public RecurringExpenses closeRecurringExpense(String name) {
		RecurringExpenses exp = this.getRecurringExpense(name);
		this.recurringExpenses.remove(exp);	
		this.closedRecurringExpenses.add(exp);
		return exp;
	}
	public Transaction[] getCurrentMonthTransactions() {
		Transaction t = new Transaction();
		return transactions.tailSet(t, true).toArray(new Transaction[0]);
	}
	public Transaction[] getMonthTransactions(int month,int year) {
		Transaction t = new Transaction(month,year);
		return transactions.tailSet(t, true).toArray(new Transaction[0]);
	}
	public Transaction[] getCurrentYearTransactions() {
		String[] dt = Utilities.dateFormat.format(new Date()).split("-");
		Transaction t = new Transaction(0,Integer.parseInt(dt[2]));
		return transactions.tailSet(t, true).toArray(new Transaction[0]);
	}
	public Transaction[] getYearTransactions(int year) {
		return transactions.subSet(new Transaction(year), new Transaction(year+1)).toArray(new Transaction[0]);
	}
	public void closeRecurringExpense(long id) {
		RecurringExpenses rec = this.getRecurringExpense(id);
		this.recurringExpenses.remove(rec);
		this.closedRecurringExpenses.add(rec);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public Date getAccountCreationDate() {
		return accountCreationDate;
	}
	public Transaction[] getTransactions() {
		return transactions.toArray(new Transaction[0]);
	}
	public MonthlyAmounts getMonthly() {
		return monthly;
	}
	public NetAmount getCurrentMonth() {
		return currentMonth;
	}
	public void print() {
		log.debug("--------------------USER---------------");
		log.debug( email + " "+ name+" "+mobile );
		monthly.print();
		for(Transaction t: transactions) {
			t.print();
		}
		log.debug("Active Recurring expenses");
		for(RecurringExpenses exp: recurringExpenses.toArray(new RecurringExpenses[0])) {
			exp.print();
		}
		log.debug("Closed Recurring Expenses");
		
		for(RecurringExpenses exp: closedRecurringExpenses.toArray(new RecurringExpenses[0])) {
			exp.print();
		}
		log.debug("------------------------DONE USER-------------");
	}
	
}
