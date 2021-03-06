package com.cardan.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.cardan.bo.User;


public class UserDb extends User{

	static Connection con = null;
	static ResultSet rs = null;
	static DbManager dbManager=null;
	
	public UserDb(int id,String email, String androidChatId){
		super(id, email, androidChatId);
	}
	
	public static boolean checkEmailLegit(String username){
		boolean result = false;
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Users WHERE username='"+username+"'";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			if(!more){
				result = false;
			}else if(more){
				result = true;
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
		return result;
		 
	}
	
public static int registerToDbWebApp(String username){
		
		rs = null;
		
		System.out.println("Username trying to register: "+username);
		boolean userExists = checkEmailLegit(username);
		
		System.out.println("User found result: "+userExists);
		
		if(userExists == false){
			
			Statement stmt=null;
			String searchQuery="INSERT INTO Users(username) VALUES(?)";
			
			try{
				dbManager=DbManager.checkInstance();
				con=dbManager.getFreeConnection();
				PreparedStatement preStmt=con.prepareStatement(searchQuery);
				preStmt.setString(1, username);
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
			return 1;
		}else{
			
			return 0;
			
		}
}
	
	public static int registerToDbAndroid(String username, String androidChatId){
		
		rs = null;
		
		System.out.println("Username trying to register: "+username);
		boolean userExists = checkEmailLegit(username);
		
		System.out.println("User found result: "+userExists);
		
		if(userExists == false){
			
			Statement stmt=null;
			String searchQuery="INSERT INTO Users(username,androidChatId) VALUES(?,?)";
			
			try{
				dbManager=DbManager.checkInstance();
				con=dbManager.getFreeConnection();
				PreparedStatement preStmt=con.prepareStatement(searchQuery);
				preStmt.setString(1, username);
				preStmt.setString(2, androidChatId);
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
			return 1;
		}else{
			
			return 0;
			
		}
}
	
public static boolean alreadyInConvo(String fromUsername, String toUsername){
	
	int count = 0;
	ArrayList<User> convos = new ArrayList<User>();
	boolean result =false;
	Statement stmt=null;
	rs = null;
	String searchQuery="SELECT * FROM Conversations WHERE fromEmail='"+fromUsername+"' AND toEmail='"+toUsername+"' OR fromEmail='"+toUsername+"' AND toEmail='"+fromUsername+"'";
	
	try{
		dbManager=DbManager.checkInstance();
		con=dbManager.getFreeConnection();
		stmt=con.createStatement();
		rs=stmt.executeQuery(searchQuery);

		boolean more = rs.next();
		
		if(!more){
			result = false;
		}else if(more){
			result = true;
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
	
	return result;
}
	
public static String registerConvo(String fromUsername, String toUsername){
		
		rs = null;
		
		boolean userExists = checkEmailLegit(toUsername);
		boolean result = alreadyInConvo(fromUsername, toUsername);
		
		if(fromUsername.equalsIgnoreCase(toUsername) == false && result == false && userExists == true){
			
			Statement stmt=null;
			String searchQuery="INSERT INTO Conversations(fromEmail,toEmail) VALUES(?,?)";
			
			try{
				dbManager=DbManager.checkInstance();
				con=dbManager.getFreeConnection();
				PreparedStatement preStmt=con.prepareStatement(searchQuery);
				preStmt.setString(1, fromUsername);
				preStmt.setString(2, toUsername);
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
			return "ok";
		}else{
			System.out.println("Can not add yourself or already in convo!!!!");
			return "error";
			
		}
	}
	
	public static ArrayList<User> getAllConvos(String username){
		
		ArrayList<User> convos = new ArrayList<User>();
		String result = "";
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM (Users JOIN Conversations on username = toEmail OR username = fromEmail) WHERE (fromEmail='"+username+"' OR toEmail='"+username+"') AND isAccepted=1";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());

			do{
				if(rs.getString("username").equalsIgnoreCase(username)){
					System.out.println("found: "+rs.getString("username"));
				}else{
					convos.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("androidChatId")));
					System.out.println("Username: "+rs.getString("username"));
					count++;
				}
				
			}while(rs.next());
			
			System.out.println("count:" + count);
			
			System.out.println("END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
		
		return convos;
	}
	
	
	public static ArrayList<String> pendingFriendRequest(String email){
		ArrayList<String> pendingRequests = new ArrayList<String>();
		String result = "";
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Conversations WHERE toEmail='"+email+"' AND isAccepted=0";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());

			do{
				pendingRequests.add(rs.getString("fromEmail"));
				System.out.println("Username: "+rs.getString("fromEmail"));
				count++;
			}while(rs.next());
			
			System.out.println("count:" + count);
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
		
		return pendingRequests;
	}
	
	public static void acceptPendingFriend(String email, String friendEmail){
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Conversations WHERE fromEmail='"+friendEmail+"' AND toEmail='"+email+"'";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());
			System.out.println(rs.getString("fromEmail"));
			System.out.println(rs.getBoolean("isAccepted"));
			rs.beforeFirst();
			while(rs.next()){
				rs.updateBoolean("isAccepted", true);
				rs.updateRow();
			}
			
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
	
	public static void declineFriendRequest(String email, String friendEmail){
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Conversations WHERE fromEmail='"+friendEmail+"' AND toEmail='"+email+"'";
		
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery(searchQuery);
			
			boolean more = rs.next();
			
			int count = 0;
			
			System.out.println("Count rows: "+rs.getRow());
			System.out.println(rs.getString("fromEmail"));
			System.out.println(rs.getBoolean("isAccepted"));
			rs.beforeFirst();
			while(rs.next()){
				rs.deleteRow();
			}
			
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
	
	public static String findRegId(String username){
		String result ="";
		Statement stmt=null;
		rs = null;
		String searchQuery="SELECT * FROM Users WHERE username='"+username+"'";
		try{
			dbManager=DbManager.checkInstance();
			con=dbManager.getFreeConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			rs.beforeFirst();
			while(rs.next()){
				result = rs.getString("androidChatId");
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
		return result;
	}
	
}
