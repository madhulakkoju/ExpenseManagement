package com.backend.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value="loginService/")
public class LoginService {
	@POST
	@Path(value="/")
	@Produces(MediaType.APPLICATION_JSON)

	public Response authenticate( @FormParam("userEmail") String email,
									@FormParam("password") String password) {
		System.out.println("called"+email+"  "+password);
		return Response.ok().build();
	}
	
	@POST
	@Path(value="login/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser( @HeaderParam("email") String email, @HeaderParam("password") String password) {
		System.out.println("called"+email+"  "+password);
		
		if(ManagerService.getInstance().authenticateUser(email, password))
			return Response.status(200).build();
		return Response.status(400).build();
	}
}
