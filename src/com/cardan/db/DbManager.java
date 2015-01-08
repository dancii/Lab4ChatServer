package com.cardan.db;

import java.sql.*;
import java.util.*;

	

public class DbManager {

	private static Connection con;
	private static String url;
	private static DbManager DBManInstance=null;
	private static ArrayList<Connection> freeCons;
	private static ArrayList<Connection> busyCons;
	
	protected DbManager(){
		freeCons=new ArrayList<Connection>();
		busyCons=new ArrayList<Connection>();
		
		for(int i=0;i<=5;i++){
			freeCons.add(createConnection());
		}
		
	}
	
	public Connection createConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			url = "jdbc:mysql://130.237.84.72/lab4Chat";
			return DriverManager.getConnection(url, "cardan", "o3U5ZyBQ");
		} catch (Exception e) {
			System.out.println("Can not make connection" + e);
			return null;
		}
	}
	
	public Connection getFreeConnection(){
		if(freeCons.isEmpty()){
			busyCons.add(createConnection());
			return busyCons.get(busyCons.size()-1);
		}else{
			busyCons.add(freeCons.remove(0));
			return busyCons.get(busyCons.size()-1);
		}
	}
	
	public static DbManager checkInstance(){
		if(DBManInstance==null){
			DBManInstance=new DbManager();
		}
		return DBManInstance;
	}
	
	public void returnBusyConnection(Connection con){
		Iterator iter = busyCons.iterator();
		int i=0;
		int id=0;
		while(iter.hasNext()){
			if(busyCons.get(i).equals(con)){
				freeCons.add(busyCons.remove(i));
				break;
			}
			i++;
		}
		
	}
	
}