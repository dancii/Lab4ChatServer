package com.cardan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.cardan.bo.Room;

public class RoomDb extends Room{

	static Connection con = null;
	static ResultSet rs = null;
	static DbManager dbManager=null;
	
	public static boolean checkIfRoomExists(String roomName){
		boolean isExisting = false;
		
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Room WHERE roomName='"+roomName+"'";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			if(!rs.next()){
				isExisting = false;
			}else{
				isExisting = true;
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
		return isExisting;
	}
	
	
	public static boolean addRoom(String roomName, String roomMemberName){
		boolean isExisting = false;
		
		isExisting = checkIfRoomExists(roomName);
		
		if(!isExisting){
			
			Statement stmt=null;
			String searchQuery="INSERT INTO Room(roomName) VALUES(?)";
			
			try{
				dbManager=DbManager.checkInstance();
				con=dbManager.getFreeConnection();
				PreparedStatement preStmt=con.prepareStatement(searchQuery);
				preStmt.setString(1, roomName);
				preStmt.executeUpdate();
				
				addRoomMember(roomName, roomMemberName);
				
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
			
			return isExisting;
		}else{
			return false;
		}
	}
	
	public static boolean addRoomMember(String roomName, String roomMemberName){
		
		boolean isExisting = false;
		isExisting = checkIfRoomExists(roomName);
		
		if(isExisting){
			Statement stmt=null;
			String searchQuery="INSERT INTO RoomMembers(roomName, username) VALUES(?,?)";
			
			try{
				dbManager=DbManager.checkInstance();
				con=dbManager.getFreeConnection();
				PreparedStatement preStmt=con.prepareStatement(searchQuery);
				preStmt.setString(1, roomName);
				preStmt.setString(2, roomMemberName);
				preStmt.executeUpdate();
				
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
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public static ArrayList<String> getAllRoomsUserMemberIn(String roomMemberName){
		ArrayList<String> roomNames = new ArrayList<String>();
		Statement stmt=null;
		rs = null;
		
		String searchQuery="SELECT * FROM RoomMembers WHERE username='"+roomMemberName+"'";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			do{
				roomNames.add(rs.getString("roomName"));
			}while(rs.next());
			
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
		return roomNames;
	}
	
	public static void saveRoomMessage(String roomName, String roomMemberName, String message){
		Statement stmt=null;
		String searchQuery="INSERT INTO RoomMessages(roomName, username, roomMessage) VALUES(?,?)";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			PreparedStatement preStmt=con.prepareStatement(searchQuery);
			preStmt.setString(1, roomName);
			preStmt.setString(2, roomMemberName);
			preStmt.setString(3, message);
			preStmt.executeUpdate();
			
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
	}
	
}
