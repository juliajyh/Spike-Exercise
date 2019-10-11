package application;

import java.util.*;

import javafx.scene.control.TextField;

public class User {

	public String usname;
	public String pwd;
	public List<Room> rooms;
	public List<String> favos;
	public User() {
		usname = null;
		pwd = null;
		rooms = new ArrayList<Room>();
		favos = new ArrayList<String>();
	}
	
	public User(String usname, String pwd) {
		this.usname = usname;
		this.pwd = pwd;
		rooms = new ArrayList<Room>();
		favos = new ArrayList<String>();
	}

	public void addHouse(Room room) {
		// TODO Auto-generated method stub
		
	}
	
}
