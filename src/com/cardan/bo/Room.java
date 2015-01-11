package com.cardan.bo;

import java.util.ArrayList;

import com.cardan.db.RoomDb;

public class Room {

	private int id;
	private String roomName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public static boolean addRoom(String roomName, String roomMemberName){
		return RoomDb.addRoom(roomName, roomMemberName);
	} 
	
	public static boolean addRoomMember(String roomName, String roomMemberName){
		return RoomDb.addRoomMember(roomName, roomMemberName);
	}
	
	public static ArrayList<String> getAllRoomsUserMemberIn(String roomMemberName){
		return RoomDb.getAllRoomsUserMemberIn(roomMemberName);
	}
	
	public static void saveRoomMessage(String roomName, String roomMemberName, String message){
		RoomDb.saveRoomMessage(roomName, roomMemberName, message);
	}
	
}
