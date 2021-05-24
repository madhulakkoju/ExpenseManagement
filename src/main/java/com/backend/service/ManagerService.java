package com.backend.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.backend.impl.LoginImpl;
import com.backend.impl.UserImpl;
import com.backend.model.LoginDetails;
import com.backend.model.User;

@Path(value = "manager")
public class ManagerService {

	UserImpl users;
	LoginImpl logins;

	private static ManagerService instance;

	private ManagerService() {
		this.users = UserImpl.getObject();
		this.logins = LoginImpl.getObject();
	}

	public static ManagerService getInstance() {
		if (instance == null) {
			instance = new ManagerService();
		}
		return instance;
	}

	public User createNewUser(String email, String mobile, String password) {
		logins.createLogin(email, password);
		return this.createUser(email, mobile);
	}

	public User createUser(String email, String mobile) {
		return users.createUser(email, mobile);
	}

	public User createNewUser(String email, String name, String mobile, String password) {
		logins.createLogin(email, password);
		return this.createUser(email, name, mobile);
	}

	public User createUser(String email, String name, String mobile) {
		return users.createUser(email, name, mobile);
	}

	public boolean authenticateUser(String email, String password) {
		LoginDetails details = this.logins.getLoginDetails(email);
		return (details == null) ? false : details.getPassword().equals(password);
	}

	@GET
	@Path(value = "/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = this.users.getUser(email);
		return Response.ok(mapper.writeValueAsString(user)).build();
	}

	@GET
	@Path(value = "/{email}/all-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getTransactions())).build();
	}

	@GET
	@Path(value = "/{email}/all-open-recurring-expenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallOpenRecurringExpenses(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getAllRecurringExpenses())).build();
	}

	@GET
	@Path(value = "/{email}/all-closed-recurring-expenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallClosedRecurringExpenses(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getClosedRecurringExpenses())).build();
	}

	@GET
	@Path(value = "/{email}/recurring-open-expense/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOpenRecurringExpense(@PathParam("email") String email, @PathParam("name") String name)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getRecurringExpense(name))).build();
	}

	@GET
	@Path(value = "/{email}/recurring-closed-expense/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClosedRecurringExpense(@PathParam("email") String email, @PathParam("name") String name)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getClosedRecurringExpense(name))).build();
	}

	@GET
	@Path(value = "/{email}/current-month")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentMonth(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).currentMonth())).build();
	}

	@GET
	@Path(value = "/{email}/all-net-month-amount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMonths(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getMonthly().getAllMonths())).build();
	}

	@GET
	@Path(value = "/{email}/net-month/{month}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMonths(@PathParam("email") String email, @PathParam("month") int month,@PathParam("year") int year) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getMonthly().getNetAmount(year, month)))
				.build();
	}

	@GET
	@Path(value = "/{email}/current-month-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentMonthTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getCurrentMonthTransactions())).build();
	}

	@GET
	@Path(value = "/{email}/current-year-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentYearTransactions(@PathParam("email") String email)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getCurrentMonthTransactions())).build();
	}

	@GET
	@Path(value = "/{email}/year-transactions/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getYearTransactions(@PathParam("email") String email, @PathParam("year") int year)throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getYearTransactions(year))).build();
	}

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
