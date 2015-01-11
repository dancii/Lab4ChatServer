package com.cardan.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cardan.bo.Message;
import com.cardan.bo.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String checkReq = request.getParameter("checkReq");
		PrintWriter out = response.getWriter();
		
		
		if(checkReq.equalsIgnoreCase("registerAccAndroid")){
			String username = request.getParameter("username");
			String androidChatId = request.getParameter("androidChatId");
			System.out.println("Username: "+username+" ID: "+androidChatId);
			int result = User.registerToDbAndroid(username,androidChatId);
			out.println(result);
		}else if(checkReq.equalsIgnoreCase("registerConvo")){
			String fromUsername = request.getParameter("fromUsername");
			String toUsername = request.getParameter("toUsername");
			String result = User.registerConvo(fromUsername, toUsername);
			out.println(result);
		}else if(checkReq.equalsIgnoreCase("getAllConvos")){
			String username = request.getParameter("username");
			ArrayList<User> result = User.getAllConvos(username);
			if(result.isEmpty()){
				out.print("notfound");
			}else{
				Gson gson = new Gson();
				String json = gson.toJson(result);
				System.out.println(json);
				out.println(json);
			}
		}else if(checkReq.equalsIgnoreCase("sendMessage")){
			String message = request.getParameter("message");
			String fromEmail = request.getParameter("fromEmail");
			String toEmail = request.getParameter("toEmail");
			boolean result = Message.saveMessage(message, fromEmail, toEmail);
			if(result){
				out.print("ok");
			}else{
				out.print("error");
			}
		}else if(checkReq.equalsIgnoreCase("getAllMessages")){
			String fromEmail = request.getParameter("fromEmail");
			String toEmail = request.getParameter("toEmail");
			ArrayList<Message> allMessages = Message.getAllMessages(fromEmail, toEmail);
			if(allMessages.isEmpty()){
				out.print("notfound");
			}else{
				Gson gson = new Gson();
				String json = gson.toJson(allMessages);
				out.print(json);
			}
		}else if(checkReq.equalsIgnoreCase("addRoom")){
			
		}
	}

}
