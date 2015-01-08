package com.cardan.bo;

import java.util.ArrayList;

import com.cardan.db.MessageDb;

public class Message {

	private int id;
	private String message;
	private String fromEmail;
	private String toEmail;
	
	public Message(int id, String message, String fromEmail, String toEmail) {
		this.id = id;
		this.message = message;
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	
	public static boolean saveMessage(String message, String fromEmail, String toEmail){
		return MessageDb.saveMessage(message, fromEmail, toEmail);
	}
	
	public static ArrayList<Message> getAllMessages(String fromEmail, String toEmail){
		return MessageDb.getAllMessages(fromEmail, toEmail);
	}
}
