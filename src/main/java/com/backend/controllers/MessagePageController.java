package com.backend.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;


@WebServlet("/MessagePageController")
public class MessagePageController extends HttpServlet {
	private static final long serialVersionUID = 8461572945832764280L;
	static String email = "email";
	static String userEmail = "userEmail";
	private static Logger log =  Logger.getLogger(MessagePageController.class);
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		log.debug(request.getParameter(email));
		session.setAttribute(email,request.getParameter(email));
			
		if(request.getParameter(email).equals("ADMIN_USER"))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/adminCheck.html");
			dispatcher.forward(request,response);
		}
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
			dispatcher.forward(request,response);
		}
		
	}
}
