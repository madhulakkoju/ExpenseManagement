package tests;

import com.backend.service.ManagerService;
import com.backend.model.PaymentMode;
import com.backend.model.RecurringExpenseCategory;
import com.backend.model.RecurringExpenseType;
import com.backend.model.RecurringExpenses;
import com.backend.model.Transaction;
import com.backend.model.TransactionCategory;
import com.backend.model.TransactionType;
import com.backend.model.User;

public class Main {
	public static void main(String[] args) {
		ManagerService app = new ManagerService();
		User user = app.createNewUser("madhu@gmail.com", "8686156086", "pass");
		//System.out.print("sdfsdf");
		user.addTransaction(new Transaction("ECG",TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Monthly Pay" ));
		user.addTransaction(new Transaction("ECG",12,06,2020,TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,5000.0,"Periodic Pay" ));
		user.addTransaction(new Transaction("ECG",12,05,2020,TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,5000.0,"Month- Pay" ));
		
		user.addRecurringExpense(new RecurringExpenses("repayPinky1", 1, "pinky", RecurringExpenseCategory.MEDICINES, RecurringExpenseType.LOAN_INTEREST_PAYMENT, "loan repayment for medicines"));
		
		user.addTransaction(new Transaction("pinky", TransactionType.INTEREST, TransactionCategory.SERVICES, PaymentMode.CASH , 1000, "paid"), "repayPinky1");
		user.addTransaction(new Transaction("pinky",11,1,2021, TransactionType.INTEREST, TransactionCategory.SERVICES, PaymentMode.CASH , 1000, "paid"), "repayPinky1");
		user.addTransaction(new Transaction("pinky",11,2,2021, TransactionType.INTEREST, TransactionCategory.SERVICES, PaymentMode.CASH , 1000, "paid"), "repayPinky1");
		user.addTransaction(new Transaction("pinky",11,3,2021, TransactionType.INTEREST, TransactionCategory.SERVICES, PaymentMode.CASH , 1000, "paid"), "repayPinky1");
		
		
		user.addTransaction(new Transaction("pinky",19,1,2021, TransactionType.INTEREST, TransactionCategory.SERVICES, PaymentMode.CASH , 1000, "paid"), "repayPinky1");
		
		user.addTransaction(new Transaction("Raja depo",12,06,2020,TransactionType.EXPENSE, TransactionCategory.FOOD_BEVERAGES, PaymentMode.CASH,500.0,"Rice" ));
		user.addTransaction(new Transaction("More supermarket",12,05,2020,TransactionType.EXPENSE, TransactionCategory.FOOD_BEVERAGES, PaymentMode.UPI,500.0,"Dal" ));
		
		user = app.createNewUser("madhu123@gmail.com","Madhu Lakkoju", "8686156086", "pass");
		
		user.addTransaction(new Transaction("BAB",TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,12345.0,"Pay" ));
		user.addTransaction(new Transaction("BAB",12,06,2020,TransactionType.INCOME, TransactionCategory.JOB, PaymentMode.NET_BANKING,5000.0,"Month Pay" ));
		user.addTransaction(new Transaction("HOME",12,05,2020,TransactionType.INCOME, TransactionCategory.SERVICES , PaymentMode.CASH,5000.0,"Help" ));
		
		user.addTransaction(new Transaction("Raja depo",12,06,2020,TransactionType.EXPENSE, TransactionCategory.FOOD_BEVERAGES, PaymentMode.CASH,500.0,"Rice" ));
		user.addTransaction(new Transaction("More supermarket",12,05,2020,TransactionType.EXPENSE, TransactionCategory.FOOD_BEVERAGES, PaymentMode.UPI,500.0,"Dal" ));
		app.printUsers();
		app.printLoginDetails();
	}
}
