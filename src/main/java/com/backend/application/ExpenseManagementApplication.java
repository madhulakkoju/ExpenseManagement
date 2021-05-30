package com.backend.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.backend.service.LoginService;
import com.backend.service.ManagerService;

@ApplicationPath("/app")
public class ExpenseManagementApplication extends Application
{
	// this singletons set contains all the Resources created in the aplication
	// we need to add objects of all the resources 
	private Set<Object> singletons = new HashSet<>();
	public ExpenseManagementApplication()
	{
		// This is initial creation of application. 
		// So, add all the resources to the set.
		singletons.add(ManagerService.getInstance());
		singletons.add(new LoginService());
	}
	@Override
	public Set<Object> getSingletons()
	{
		return this.singletons;
	}
}