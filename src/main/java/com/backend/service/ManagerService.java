package com.backend.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.backend.impl.LoginImpl;
import com.backend.impl.UserImpl;
import com.backend.model.LoginDetails;
import com.backend.model.PaymentMode;
import com.backend.model.RecurringExpenseCategory;
import com.backend.model.RecurringExpenseType;
import com.backend.model.RecurringExpenses;
import com.backend.model.Transaction;
import com.backend.model.TransactionCategory;
import com.backend.model.TransactionType;
import com.backend.model.User;

@Path(value = "manager")
public class ManagerService {
	private static Logger log = Logger.getLogger(ManagerService.class);
	UserImpl users;
	LoginImpl logins;

	// Singleton Instance to be stored.
	private static ManagerService instance;

	private ManagerService() {
		this.users = UserImpl.getObject();
		this.logins = LoginImpl.getObject();
	}
	// Instance getter method
	public static ManagerService getInstance() {
		if (instance == null) {
			instance = new ManagerService();
		}
		return instance;
	}

	// New User Creating Support Methods
	public User createNewUser(String email, String mobile, String password) {
		logins.createLogin(email, password);
		return this.createUser(email, mobile);
	}

	public User createUser(String email, String mobile) {
		return users.createUser(email, mobile);
	}

	public User createNewUser(String email, String name, String mobile, String password) {
		if(name == null) 
			return this.createNewUser(email, mobile, password);
		
		logins.createLogin(email, password);
		return this.createUser(email, name, mobile);
	}

	public User createUser(String email, String name, String mobile) {
		return users.createUser(email, name, mobile);
	}
	
	// Authentication Support Methods
	public boolean authenticateUser(String email, String password) {
		LoginDetails details = this.logins.getLoginDetails(email);
		return (details == null) ? false : details.getPassword().equals(password);
	}
	
	// RESTful Web Service for Profile 
	@GET
	@Path(value = "/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = this.users.getUser(email);
		log.debug("sending user object : "+ user.getEmail());
		return Response.ok(mapper.writeValueAsString(user)).build();
	}

	// RESTful Web Service for All Transactions
	@GET
	@Path(value = "/{email}/all-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending all transactions of user : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getTransactions())).build();
	}
	
	// RESTful Web Service for All Open Recurring Expenses
	@GET
	@Path(value = "/{email}/all-open-recurring-expenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallOpenRecurringExpenses(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending all Active Recurring Expense : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getAllRecurringExpenses())).build();
	}

	// RESTful Web Service for All Closed Recurring Expenses 
	@GET
	@Path(value = "/{email}/all-closed-recurring-expenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallClosedRecurringExpenses(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Closed Recurring Expenses : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getClosedRecurringExpenses())).build();
	}

	// RESTful Web Service for Recurring Expense by Name
	@GET
	@Path(value = "/{email}/recurring-open-expense/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOpenRecurringExpense(@PathParam("email") String email, @PathParam("name") String name)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Recurring Expense of name : "+name+"to User : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getRecurringExpense(name))).build();
	}
	
	// RESTful Web Service for Closed Recurring Expense by Name
	@GET
	@Path(value = "/{email}/recurring-closed-expense/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClosedRecurringExpense(@PathParam("email") String email, @PathParam("name") String name)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Closed Recurrence Expense "+name+" to user : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getClosedRecurringExpense(name))).build();
	}

	// RESTful Web Service for Current Month Data
	@GET
	@Path(value = "/{email}/current-month")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentMonth(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Current Month Stats to User : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).currentMonth())).build();
	}
	
	// RESTful Web Service for All Net Monthly Amounts
	@GET
	@Path(value = "/{email}/all-net-month-amount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMonths(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending all Net Month Statistics to User : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getMonthly().getAllMonths())).build();
	}

	// RESTful Web Service for Net Month by Month and Year
	@GET
	@Path(value = "/{email}/net-month/{month}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMonths(@PathParam("email") String email, @PathParam("month") int month,@PathParam("year") int year) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Month Statistics to user : "+email+ " Month "+month+" Year "+year);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getMonthly().getNetAmount(year, month)))
				.build();
	}

	// RESTful Web Service for Current Month Transactions
	@GET
	@Path(value = "/{email}/current-month-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentMonthTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Current Month Transactions to User : "+ email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getCurrentMonthTransactions())).build();
	}

	// RESTful Web Service for Current Year Transactions
	@GET
	@Path(value = "/{email}/current-year-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentYearTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Current Year Transactions to User : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getCurrentYearTransactions())).build();
	}

	// RESTful Web Service for Year Transactions by Year
	@GET
	@Path(value = "/{email}/year-transactions/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getYearTransactions(@PathParam("email") String email, @PathParam("year") int year)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Sending Year "+year+" Transactions to User : "+email);
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getYearTransactions(year))).build();
	}
	
	// RESTful Web Service for Sign up
	@POST
	@Path(value="/sign-up")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signUpUser( @HeaderParam("email") String email, @HeaderParam("name") String name, @HeaderParam("mobile") String mobile, @HeaderParam("password") String password) throws JsonGenerationException, JsonMappingException, IOException{
		log.debug("Sign Up Requested from email : "+email);
		if(this.users.getUser(email) != null) {
			log.info(email+" already has an account");
			return Response.status(500, "already-signed-up").build();
		}
		User createdUser = this.createNewUser(email, name, mobile, password);
		ObjectMapper mapper = new ObjectMapper();
		log.debug("User Account Creation Successful");
		return Response.ok(mapper.writeValueAsString(createdUser)).build();
	}
	
	// RESTful Web Service for Creating a Recurring Expense 
	@POST
	@Path(value="/{email}/create-recurring")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRecurringExpenses( @PathParam("email") String email, @HeaderParam("name") String name, @HeaderParam("periodicity") int periodicity, @HeaderParam("participant") String participant, @HeaderParam("category") RecurringExpenseCategory category, @HeaderParam("type") RecurringExpenseType type, @HeaderParam("remarks") String remarks) throws JsonGenerationException, JsonMappingException, IOException{
		RecurringExpenses exp = new RecurringExpenses(name,periodicity,participant,category,type,remarks);
		users.getUser(email).addRecurringExpense(exp);
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Recurring Expense Created to user : "+email+"with name "+name);
		return Response.ok(mapper.writeValueAsString(exp)).build();
	}
	
	// RESTful Web Service for Creating a Basic Recurring Expense
	@POST
	@Path(value="/{email}/create-recurring-basic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBasicRecurringExpenses( @PathParam("email") String email, @HeaderParam("name") String name, @HeaderParam("periodicity") int periodicity, @HeaderParam("participant") String participant,  @HeaderParam("remarks") String remarks) throws JsonGenerationException, JsonMappingException, IOException{
		RecurringExpenses exp = new RecurringExpenses(name,periodicity,participant,remarks);
		users.getUser(email).addRecurringExpense(exp);
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(exp)).build();
	}
	
	// RESTful Web Service for Creating a Transaction
	@POST
	@Path(value="/{email}/create-transaction")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTransaction(@PathParam("email") String email, @HeaderParam("participant") String participant,@HeaderParam("date") int dd,@HeaderParam("month") int mm,@HeaderParam("year") int yy,@HeaderParam("type") TransactionType type,@HeaderParam("category") TransactionCategory category, @HeaderParam("mode") PaymentMode mode, @HeaderParam("amount") double amount, @HeaderParam("remarks") String remarks ) throws JsonGenerationException, JsonMappingException, IOException {
		Transaction trans = new Transaction(participant,dd,mm,yy,type,category,mode,amount,remarks);
		users.getUser(email).addTransaction(trans);
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Transaction Created to User : "+email);
		return Response.ok(mapper.writeValueAsString(trans)).build();
	}

	// RESTful Web Service for Creating a Transaction under Recurrence Expense
	@POST
	@Path(value="/{email}/create-rec-transaction/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTransactionForRecExp(@PathParam("email") String email,@HeaderParam("rec-name") String recName, @HeaderParam("participant") String participant,@HeaderParam("date") int dd,@HeaderParam("month") int mm,@HeaderParam("year") int yy,@HeaderParam("type") TransactionType type,@HeaderParam("category") TransactionCategory category, @HeaderParam("mode") PaymentMode mode, @HeaderParam("amount") double amount, @HeaderParam("remarks") String remarks ) throws JsonGenerationException, JsonMappingException, IOException {
		Transaction trans = new Transaction(participant,dd,mm,yy,type,category,mode,amount,remarks);
		//users.getUser(email).getRecurringExpense(recName).addTransaction(trans);
		users.getUser(email).addTransaction(trans, recName);
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Transaction created under Recurrence Expression "+recName+" to User : "+email);
		return Response.ok(mapper.writeValueAsString(trans)).build();
	}
	
	// RESTful Web Service for updating a Transaction
	@PUT
	@Path(value="/{email}/transaction/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyTransaction(@PathParam("email") String email, @PathParam("id") long id, @HeaderParam("participant") String participant,@HeaderParam("date") int dd,@HeaderParam("month") int mm,@HeaderParam("year") int yy,@HeaderParam("type") TransactionType type,@HeaderParam("category") TransactionCategory category, @HeaderParam("mode") PaymentMode mode, @HeaderParam("amount") double amount, @HeaderParam("remarks") String remarks ) throws JsonGenerationException, JsonMappingException, IOException {
		Transaction trans = users.getUser(email).getTransaction(id);
		trans.setAmount(amount);
		trans.setCategory(category);
		trans.setDateOfTransaction(dd, mm, yy);
		trans.setMode(mode);
		trans.setRemarks(remarks);
		trans.setType(type);
		ObjectMapper mapper = new ObjectMapper();
		log.debug("Transaction data modified for User "+email);
		return Response.ok(mapper.writeValueAsString(trans)).build();
	}
	
	// RESTful Web Service for updating a Recurring Expense
	@PUT
	@Path(value="/{email}/modify-recurring/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyRecurringExpense( @PathParam("email") String email, @PathParam("id") long id , @HeaderParam("name") String name, @HeaderParam("periodicity") int periodicity, @HeaderParam("participant") String participant, @HeaderParam("category") RecurringExpenseCategory category, @HeaderParam("type") RecurringExpenseType type, @HeaderParam("remarks") String remarks) throws JsonGenerationException, JsonMappingException, IOException{
		RecurringExpenses rec = users.getUser(email).getRecurringExpense(id);
		rec.setCategory(category);
		rec.setName(name);
		rec.setParticipant(participant);
		rec.setRemarks(remarks);
		rec.setPeriodicity(periodicity);
		rec.setType(type);
		log.debug("Updated Recurring Expense "+name+" of User : "+email);
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(rec)).build();
	}
	
	// RESTful Web Service for Closing a Recurring Expense on ID
	@PUT
	@Path(value="/{email}/close-recurring/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response closeRecurringExpense(@PathParam("email") String email, @PathParam("id") long id ) {
		users.getUser(email).closeRecurringExpense(id);
		return Response.ok().build();
	}
	
	// RESTful Web Service for Closing a Recurrence Expense by Name
	@PUT
	@Path(value="/{email}/close-recurring-name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response closeRecurringExpense(@PathParam("email") String email, @PathParam("name") String name ) {
		RecurringExpenses exp = users.getUser(email).closeRecurringExpense(name);
		log.debug("Closing Recurring Expense "+name+" of User "+email);
		return Response.ok(exp).build();
	}
	
	// Support Method for Removing User
	public User removeUser(String email) {
		return users.removeUser(email);
	}

	public void printUsers() {
		users.print();
	}

	public void printLoginDetails() {
		logins.print();
	}
}
