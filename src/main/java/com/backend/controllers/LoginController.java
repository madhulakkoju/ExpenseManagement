package com.backend.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.backend.service.ManagerService;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 3358786867422744662L;
	public static Logger log = Logger.getLogger(LoginController.class);
	public boolean authenticate(String id, String password)
	{
		log.debug(id + " " + password);
		return ManagerService.getInstance().authenticateUser(id, password);
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		if(authenticate(request.getParameter("userEmail") , request.getParameter("password")))
		{
			HttpSession session = request.getSession();
			session.setAttribute("loggedIn", true);
			System.out.println("User Authenticated");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.html");
			dispatcher.forward(request,response);
		}
	}
}