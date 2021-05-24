package com.backend.model;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class MonthlyAmounts {
	static NetAmount dummyMonth = new NetAmount(1,1);
	TreeSet<NetAmount> monthlyNetAmounts;
	private static Logger log = Logger.getLogger(MonthlyAmounts.class);
	
	public MonthlyAmounts() {
		this.monthlyNetAmounts = new TreeSet<>();
	}
	
	public void addTransaction(Transaction transaction) {
		Iterator<NetAmount> it = monthlyNetAmounts.descendingIterator();
		NetAmount month ;
		boolean found = false;
		while(it.hasNext()) {
			month = it.next();
			if(month.getYear()==transaction.getDateOfTransaction()[2] && month.getMonth() == transaction.getDateOfTransaction()[1] ) {
				found = true;
				month.addTransaction(transaction);
				break;
			}
		}
		if(!found){
			month = new NetAmount(transaction);
			monthlyNetAmounts.add(month);
		}
		
	}
	
	public NetAmount[] getAllMonths() {
		return this.monthlyNetAmounts.toArray(new NetAmount[0]);
	}
	
	public NetAmount latestMonth() {	
		if(monthlyNetAmounts.isEmpty())
			return dummyMonth;
		log.debug("First "+ monthlyNetAmounts.first().getYear()+" "+monthlyNetAmounts.first().getMonth());
		log.debug("First "+ monthlyNetAmounts.last().getYear()+" "+monthlyNetAmounts.last().getMonth());
		return monthlyNetAmounts.first();
	}
	
	public boolean isPresent(int yr, int month) {
		dummyMonth.year = yr;
		dummyMonth.month = month;
		return monthlyNetAmounts.contains(dummyMonth);
	}
	
	public NetAmount getNetAmount(int yr,int month) {
		if(isPresent(yr,month))
			return monthlyNetAmounts.ceiling(dummyMonth);
		NetAmount thisMonth = new NetAmount(yr,month);
		monthlyNetAmounts.add(thisMonth);
		return thisMonth;
	}
	
	public void print() {
		log.debug("---------------MONTHLY NET AMOUNTS----------------");
		Iterator<NetAmount> it = monthlyNetAmounts.descendingIterator();
		while(it.hasNext()) {
			it.next().print();
		}
		log.debug("MONTHLY NET DONE");
	}
	
	
}
