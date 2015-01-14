package com.cardan.bo;

import java.util.ArrayList;

import com.cardan.db.RoomDb;

public class Room {

	private int id;
	private String roomName;
	private String roomUsername;
	private String message;
	
	public Room(String roomUsername, String roomMessage){
		this.roomUsername=roomUsername;
		this.message=roomMessage;
	}
	
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
	
	public String getRoomUsername() {
		return roomUsername;
	}

	public void setRoomUsername(String roomUsername) {
		this.roomUsername = roomUsername;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static boolean checkIfRoomExists(String roomName){
		return RoomDb.checkIfRoomExists(roomName);
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
	
	public static ArrayList<User> getAllRoomMembers(String roomName){
		return RoomDb.getAllRoomMembers(roomName);
	}
	
	public static ArrayList<Room> getAllRoomMessages(String roomName){
		return RoomDb.getAllRoomMessages(roomName);
	}
	
	public static ArrayList<String> getAllRoomMembersWeb(String roomName){
		return RoomDb.getAllRoomMembersWeb(roomName);
	}
}
