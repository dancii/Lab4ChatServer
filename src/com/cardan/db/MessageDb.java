package com.cardan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.cardan.bo.Message;

public class MessageDb extends Message{

	static Connection con = null;
	static ResultSet rs = null;
	static DbManager dbManager=null;
	
	public MessageDb(int id, String message, String fromEmail, String toEmail) {
		super(id, message, fromEmail, toEmail);
	}

	public static boolean saveMessage(String message, String fromEmail, String toEmail){
		
		boolean result = false;
		Statement stmt=null;
		String searchQuery="INSERT INTO Messages(message,fromEmail,toEmail) VALUES(?,?,?)";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			PreparedStatement preStmt=con.prepareStatement(searchQuery);
			preStmt.setString(1, message);
			preStmt.setString(2, fromEmail);
			preStmt.setString(3, toEmail);
			preStmt.executeUpdate();
			
			result = true;
			
		}catch(Exception e){
			System.out.println("ERROR: "+e);
		}finally{
		    if (rs != null)	{
	            try {
	               rs.close();
	            } catch (Exception e) {}
	               rs = null;
		    }
		    
		    if (stmt != null) {
	            try {
	               stmt.close();
	            } catch (Exception e) {}
	               stmt = null;
            }
		    
		    if (con != null) {
	            try {
	            	dbManager.returnBusyConnection(con);
	            } catch (Exception e) {
	            
	            }
	            con = null;
	        }
		}
	
		return result;
	}
	
	public static ArrayList<Message> getAllMessages(String fromEmail, String toEmail){
		ArrayList<Message> allMessages = new ArrayList<Message>();
		boolean result = false;
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Messages WHERE fromEmail='"+fromEmail+"' AND toEmail='"+toEmail+"' OR fromEmail='"+toEmail+"' AND toEmail='"+fromEmail+"' LIMIT 20 ORDER BY id DESC";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			while(more){
				allMessages.add(new Message(rs.getInt("id"), rs.getString("message"), rs.getString("fromEmail"), rs.getString("toEmail")));
			}

		}catch(Exception e){

		}finally{
		    if (rs != null)	{
	            try {
	               rs.close();
	            } catch (Exception e) {}
	               rs = null;
		    }
		    
		    if (stmt != null) {
	            try {
	               stmt.close();
	            } catch (Exception e) {}
	               stmt = null;
            }
		    
		    if (con != null) {
	            try {
	            	dbManager.returnBusyConnection(con);
	            } catch (Exception e) {
	            
	            }
	            con = null;
	        }
		}
		return allMessages;
		 
	}
	
}
