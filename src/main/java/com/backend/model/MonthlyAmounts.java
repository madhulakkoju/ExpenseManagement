package com.backend.model;

import java.util.Iterator;
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
	
	public NetAmount latestMonth() {		
		log.debug("First "+ monthlyNetAmounts.first().getYear()+" "+monthlyNetAmounts.first().getMonth());
		log.debug("First "+ monthlyNetAmounts.last().getYear()+" "+monthlyNetAmounts.last().getMonth());
		return monthlyNetAmounts.first();
	}
	
	public boolean isPresent(int yr, int month) {
		dummyMonth.year = yr;
		dummyMonth.month = month;
		return monthlyNetAmounts.contains(dummyMonth);
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
