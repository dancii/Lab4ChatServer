package com.cardan.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cardan.bo.Message;
import com.cardan.bo.Room;
import com.cardan.bo.User;
import com.cardan.db.RoomDb;
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
		HttpSession session = request.getSession();
		
		if(checkReq.equalsIgnoreCase("registerAccWebApp")){
			
			String username = request.getParameter("username");
			int result = User.registerToDbWebApp(username);
			session.setAttribute("loggedIn", username);
			System.out.println("Result: "+result);
			response.sendRedirect("welcome.jsp");
			
		}else if(checkReq.equalsIgnoreCase("registerConvoWebApp")){
			String fromUsername = (String) session.getAttribute("loggedIn");
			String toUsername = request.getParameter("toUsername");
			String result = User.registerConvo(fromUsername, toUsername);
			out.println(result);
			RequestDispatcher rd = request.getRequestDispatcher("/friends.jsp");
			rd.forward(request, response);
		}else if(checkReq.equalsIgnoreCase("getAllConvosWebApp")){
			String username = (String)session.getAttribute("loggedIn");
			ArrayList<User> resultAllConvos = User.getAllConvos(username);
			ArrayList<String> resultPendingFriendReq = User.pendingFriendRequest(username);
			request.setAttribute("allConvos", resultAllConvos);
			request.setAttribute("pendingFriendReq", resultPendingFriendReq);
			RequestDispatcher rd = request.getRequestDispatcher("/friends.jsp");
			rd.forward(request, response);
		}else if(checkReq.equalsIgnoreCase("getPendingFriendRequestWebApp")){
			String username = request.getParameter("username");
			ArrayList<String> result = User.pendingFriendRequest(username);
			if(result.isEmpty()){
				out.print("notfound");
			}else{
				Gson gson = new Gson();
				String json = gson.toJson(result);
				System.out.println(json);
				out.println(json);
			}
		}else if(checkReq.equalsIgnoreCase("acceptPendingFriendWebApp")){
			String username = (String)session.getAttribute("loggedIn");
			String fromEmail = request.getParameter("fromEmail");
			User.acceptPendingFriend(username,fromEmail);
			ArrayList<User> resultAllConvos = User.getAllConvos(username);
			ArrayList<String> resultPendingFriendReq = User.pendingFriendRequest(username);
			request.setAttribute("allConvos", resultAllConvos);
			request.setAttribute("pendingFriendReq", resultPendingFriendReq);
			RequestDispatcher rd = request.getRequestDispatcher("/friends.jsp");
			rd.forward(request, response);
		}else if(checkReq.equalsIgnoreCase("declinePendingFriendWebApp")){
			String username= request.getParameter("username");
			String fromEmail= request.getParameter("fromEmail");
			User.declineFriendRequest(username, fromEmail);
			ArrayList<User> resultAllConvos = User.getAllConvos(username);
			ArrayList<String> resultPendingFriendReq = User.pendingFriendRequest(username);
			request.setAttribute("allConvos", resultAllConvos);
			request.setAttribute("pendingFriendReq", resultPendingFriendReq);
			RequestDispatcher rd = request.getRequestDispatcher("/friends.jsp");
			rd.forward(request, response);
		}else if(checkReq.equalsIgnoreCase("sendMessageWebApp")){
			System.out.println("TRYING TO SEND MESSAGE!!!");
			String message = request.getParameter("message");
			String fromEmail = request.getParameter("fromEmail");
			String toEmail = request.getParameter("toEmail");
			boolean result = Message.saveMessage(message, fromEmail, toEmail);
			if(result){
				out.print("ok");
			}else{
				out.print("error");
			}
		}else if(checkReq.equalsIgnoreCase("getAllMessagesWebApp")){
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
		}else if(checkReq.equalsIgnoreCase("joinRoomWebApp")){
			boolean result = false;
			String roomName= request.getParameter("roomName");
			String username= (String)session.getAttribute("loggedIn");
			result = Room.addRoomMember(roomName,username);
			if(result){
				out.print("Joining");
			}else{
				out.print("roomNotExist");
			}

		}else if(checkReq.equalsIgnoreCase("createRoomWebApp")){
			boolean result = false;
			String roomName= request.getParameter("roomName");
			String username= (String)session.getAttribute("loggedIn");
			result = Room.addRoom(roomName, username);
			if(result){
				result = Room.addRoomMember(roomName, username);
				if(result){
					out.print("roomCreated");
				}
				
			}
			
		}else if(checkReq.equalsIgnoreCase("getAllRoomsWebApp")){
			String username= (String) session.getAttribute("loggedIn");
			ArrayList<String> result = RoomDb.getAllRoomsUserMemberIn(username);
			if(result.isEmpty()){
				result.add("You are not a member in any room");
			}
			request.setAttribute("allRooms", result);
			RequestDispatcher rd = request.getRequestDispatcher("/rooms.jsp");
			rd.forward(request, response);
		}else if(checkReq.equalsIgnoreCase("sendMessageToRoomWebApp")){
			String roomName= request.getParameter("roomName");
			String username= request.getParameter("username");
			String message= request.getParameter("messageToRoom");
			Room.saveRoomMessage(roomName, username, message);
			
		}else if(checkReq.equalsIgnoreCase("getAllRoomMessagesWebApp")){
			String roomName = request.getParameter("roomName");
			ArrayList<Room> roomMessages = Room.getAllRoomMessages(roomName);
			if(roomMessages.isEmpty()){
				out.print("noRoomMessages");
			}else{
				Gson gson = new Gson();
				out.print(gson.toJson(roomMessages));
				
			}
		}else if(checkReq.equalsIgnoreCase("getAllRoomMembersWebApp")){
			String roomName = request.getParameter("roomName");
			ArrayList<User> allRoomMembers = Room.getAllRoomMembers(roomName);
			if(allRoomMembers.isEmpty()){
				out.print("No room members");
			}else{
				Gson gson = new Gson();
				out.print(gson.toJson(allRoomMembers));
			}
			
		}else if(checkReq.equalsIgnoreCase("registerAccAndroid")){ //Mobile server connection
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
		}else if(checkReq.equalsIgnoreCase("getPendingFriendRequest")){
			String username = request.getParameter("username");
			ArrayList<String> result = User.pendingFriendRequest(username);
			if(result.isEmpty()){
				out.print("notfound");
			}else{
				Gson gson = new Gson();
				String json = gson.toJson(result);
				System.out.println(json);
				out.println(json);
			}
		}else if(checkReq.equalsIgnoreCase("acceptPendingFriend")){
			String username = request.getParameter("username");
			String fromEmail = request.getParameter("fromEmail");
			User.acceptPendingFriend(username,fromEmail);
			
		}else if(checkReq.equalsIgnoreCase("declinePendingFriend")){
			String username= request.getParameter("username");
			String fromEmail= request.getParameter("fromEmail");
			User.declineFriendRequest(username, fromEmail);
			
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
		}else if(checkReq.equalsIgnoreCase("createJoinRoom")){
			boolean result = false;
			String roomName= request.getParameter("roomName");
			String username= request.getParameter("username");
			String createJoinState = request.getParameter("createJoinState");
			if(createJoinState.equalsIgnoreCase("join")){
				result = Room.addRoomMember(roomName,username);
				if(result){
					out.print("Joining");
				}else{
					out.print("roomNotExist");
				}
			}else{
				result = Room.addRoom(roomName, username);
				if(result){
					result = Room.addRoomMember(roomName, username);
					if(result){
						out.print("roomCreated");
					}
					
				}
			}

		}else if(checkReq.equalsIgnoreCase("getAllRooms")){
			String username= request.getParameter("username");
			ArrayList<String> result = RoomDb.getAllRoomsUserMemberIn(username);
			if(result.isEmpty()){
				out.print("noRoomsMember");
			}else{
				Gson gson = new Gson();
				out.print(gson.toJson(result));
			}
		}else if(checkReq.equalsIgnoreCase("sendMessageToRoom")){
			String roomName= request.getParameter("roomName");
			String username= request.getParameter("username");
			String message= request.getParameter("messageToRoom");
			Room.saveRoomMessage(roomName, username, message);
			
		}else if(checkReq.equalsIgnoreCase("getAllRoomMessages")){
			String roomName = request.getParameter("roomName");
			ArrayList<Room> roomMessages = Room.getAllRoomMessages(roomName);
			if(roomMessages.isEmpty()){
				out.print("noRoomMessages");
			}else{
				Gson gson = new Gson();
				out.print(gson.toJson(roomMessages));
				
			}
		}
	}

}
