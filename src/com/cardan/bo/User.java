package com.cardan.bo;

import java.util.ArrayList;

import com.cardan.db.UserDb;

public class User {

	private int id;
	private String email;
	private String androidChatId;
	
	public User(int id, String email, String androidChatId){
		this.id=id;
		this.email=email;
		this.androidChatId=androidChatId;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAndroidChatId() {
		return androidChatId;
	}

	public void setAndroidChatId(String androidChatId) {
		this.androidChatId = androidChatId;
	}



	public static int registerToDbAndroid(String username, String androidChatId){
		int result = UserDb.registerToDbAndroid(username, androidChatId);
		return result;
	}
	
	public static String registerConvo(String fromUsername, String toUsername){
		String result = UserDb.registerConvo(fromUsername, toUsername);
		return result;
	}
	
	public static ArrayList<User> getAllConvos(String username){
		return UserDb.getAllConvos(username);
	}
	
	public static ArrayList<String> pendingFriendRequest(String email){
		return UserDb.pendingFriendRequest(email);
	}
	
	public static void acceptPendingFriend(String email, String friendEmail){
		UserDb.acceptPendingFriend(email, friendEmail);
	}
	
	public static void declineFriendRequest(String email, String friendEmail){
		UserDb.declineFriendRequest(email, friendEmail);
	}
	
}