package com.backend.service;

import java.io.IOException;
import java.util.List;

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
import com.backend.model.Transaction;
import com.backend.model.User;

@Path(value="manager")
public class ManagerService {
	
	UserImpl users ;
	LoginImpl logins;
	
	public ManagerService(){
		this.users = UserImpl.getObject();
		this.logins = LoginImpl.getObject();
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
		return this.createUser(email,name, mobile);
	}
	
	public User createUser(String email,String name, String mobile) {
		return users.createUser(email,name, mobile);
	}
	
	@GET
	@Path(value="/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser( @PathParam("email") String email) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = this.users.getUser(email);
		return Response.ok(mapper.writeValueAsString(user)).build();
	}
	
	@GET
	@Path(value="/{email}/all-transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallTransactions( @PathParam("email") String email) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return Response.ok(mapper.writeValueAsString(users.getUser(email).getTransactions())).build();
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
