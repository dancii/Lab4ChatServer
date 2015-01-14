package com.cardan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.cardan.bo.Room;
import com.cardan.bo.User;

public class RoomDb extends Room{

	public RoomDb(String roomUsername, String roomMessage) {
		super(roomUsername, roomMessage);
	}

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
		boolean result = false;
	
		Statement stmt=null;
		String searchQuery="INSERT INTO Room(roomName) VALUES(?)";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			PreparedStatement preStmt=con.prepareStatement(searchQuery);
			preStmt.setString(1, roomName);
			preStmt.executeUpdate();
			
			result = true;
		}catch(Exception e){
			System.out.println("ERROR: "+e);
			result = false;
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
	
	public static boolean checkIfMemberOfRoom(String roomName, String roomMemberName){
		boolean result = false;
		Statement stmt=null;
		rs = null;
		
		String searchQuery="SELECT * FROM RoomMembers WHERE roomName='"+roomName+"' AND username='"+roomMemberName+"'";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			System.out.println("Count rows getAllRooms: "+rs.getRow());
			
			if(!rs.next()){
				result = false;
			}else{
				result = true;
			}
			
		}catch(Exception e){
			System.out.println("Error getAllRooms: "+e);
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
	
	public static boolean addRoomMember(String roomName, String roomMemberName){
		
		boolean isExisting = false;
		boolean isMember=false;
		isExisting = checkIfRoomExists(roomName);
		isMember = checkIfMemberOfRoom(roomName, roomMemberName);
		System.out.println("isExisting(true): "+isExisting+" isMember(false): "+isMember);
		if(isExisting && !isMember){
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
			System.out.println("Count rows getAllRooms: "+rs.getRow());
			/*do{
				System.out.println("Roomname: "+rs.getString("roomName"));
				roomNames.add(rs.getString("roomName"));
			}while(rs.next());*/
			
			rs.beforeFirst();
			while(rs.next()){
				System.out.println("Roomname: "+rs.getString("roomName"));
				roomNames.add(rs.getString("roomName"));
			}
			
		}catch(Exception e){
			System.out.println("Error getAllRooms: "+e);
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
		String searchQuery="INSERT INTO RoomMessages(roomName, username, roomMessage) VALUES(?,?,?)";
		
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
	
	public static ArrayList<Room> getAllRoomMessages(String roomName){
		ArrayList<Room> roomMessages = new ArrayList<Room>();
		Statement stmt=null;
		rs = null;
		System.out.println("ROOM NAME: "+roomName+ "count : "+roomName.length());
		String searchQuery="SELECT * FROM RoomMessages WHERE roomName='"+roomName+"' ORDER BY id DESC LIMIT 20";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			System.out.println("getAllRoomMessages COUNT: "+rs.getRow());
			
			rs.beforeFirst();
			
			while(rs.next()){
				roomMessages.add(new Room(rs.getString("username"),rs.getString("roomMessage")));
			}
			
			Collections.reverse(roomMessages);
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
		return roomMessages;
	}
	
	public static ArrayList<User> getAllRoomMembers(String roomName){
		ArrayList<User> convos = new ArrayList<User>();
		String result = "";
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM (Users JOIN RoomMembers on Users.username = RoomMembers.username) WHERE roomName = '"+roomName+"'";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());
			rs.beforeFirst();
			while(rs.next()){
				convos.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("androidChatId")));
				System.out.println("Username: "+rs.getString("username"));
				count++;
			}
			
			System.out.println("count:" + count);
			
		}catch(Exception e){
			System.out.println("getAllRoomMembers ERROR: "+e);
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
		
		return convos;
	}
	
	public static ArrayList<String> getAllRoomMembersWeb(String roomName){
		ArrayList<String> getAllRoomMembers = new ArrayList<String>();
		
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM RoomMembers WHERE roomName = '"+roomName+"'";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());
			rs.beforeFirst();
			while(rs.next()){
				getAllRoomMembers.add(rs.getString("username"));
				System.out.println("Username: "+rs.getString("username"));
				count++;
			}
			
			System.out.println("count:" + count);
			
		}catch(Exception e){
			System.out.println("getAllRoomMembers ERROR: "+e);
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
		
		return getAllRoomMembers;
	}
	
}
