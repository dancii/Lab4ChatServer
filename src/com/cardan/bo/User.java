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
	
}