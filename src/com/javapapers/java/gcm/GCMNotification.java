package com.javapapers.java.gcm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cardan.bo.Room;
import com.cardan.bo.User;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@WebServlet("/GCMNotification")
public class GCMNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyDVB_QDsJn8WYFNbAgN2B78aNph6WxIjcI";
	static final String MESSAGE_KEY = "message";	

	public GCMNotification() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Result result = null;

		String share = request.getParameter("shareRegId");

		String checkMessageType = request.getParameter("messageType");
		
		if(checkMessageType == null){
			
		}else{
			if(checkMessageType.equalsIgnoreCase("room")){
				System.out.println("ROOOM MESSAGE EXECUTEEEEEEEEEEEEEEEEEED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				ArrayList<User> allRoomUsers = new ArrayList<User>();
				String roomName = request.getParameter("roomName");
				String roomMessage = "<>!?/* ";
						
				roomMessage +=request.getParameter("roomMessage");
				String regId = "";
				
				allRoomUsers = Room.getAllRoomMembers(roomName);
				
				for (User user : allRoomUsers) {
					regId = user.getAndroidChatId();
					System.out.println("SEND ROOM MESSAGE TO: regId = "+regId+" NAME = "+user.getEmail());
					try {
						Sender sender = new Sender(GOOGLE_SERVER_KEY);
						Message message = new Message.Builder().timeToLive(30)
								.delayWhileIdle(true).addData(MESSAGE_KEY, roomMessage).build();
						System.out.println("regId: " + regId);
						result = sender.send(message, regId, 1);
						request.setAttribute("pushStatus", result.toString());
					} catch (IOException ioe) {
						ioe.printStackTrace();
						request.setAttribute("pushStatus",
								"RegId required: " + ioe.toString());
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("pushStatus", e.toString());
					}
				}
			}else{
				// GCM RedgId of Android device to send push notification
				String regId = "";
				String fromUsername="";
					regId = request.getParameter("regId");
					fromUsername = request.getParameter("sendFromUsername");

					try {
						String userMessage = request.getParameter("message");
						Sender sender = new Sender(GOOGLE_SERVER_KEY);
						Message message = new Message.Builder().timeToLive(30)
								.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
						System.out.println("regId: " + regId);
						result = sender.send(message, regId, 1);
						request.setAttribute("pushStatus", result.toString());
					} catch (IOException ioe) {
						ioe.printStackTrace();
						request.setAttribute("pushStatus",
								"RegId required: " + ioe.toString());
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("pushStatus", e.toString());
					}
					request.getRequestDispatcher("index.jsp")
							.forward(request, response);
			}
		}
		
		
		
	}
}
