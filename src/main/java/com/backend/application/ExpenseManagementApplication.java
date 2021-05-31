package com.backend.application;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.backend.service.LoginService;
import com.backend.service.ManagerService;

// Creating an Application for creating RESTful Web Services
@ApplicationPath("/app")
public class ExpenseManagementApplication extends Application
{
	// This singletons set contains all the RESTful Web Service Resources created in the application
	// We need to add objects of all the resources 
	private Set<Object> singletons = new HashSet<>();
	
	// Constructor for Application
	// This object is created by the Server
	public ExpenseManagementApplication()
	{
		// This is initial creation of application. 
		// So, add all the resources to the set.
		singletons.add(ManagerService.getInstance());
		singletons.add(new LoginService());
	}
	
	//The singletons set contains the REST service objects.
	@Override
	public Set<Object> getSingletons()
	{
		return this.singletons;
	}
}