package controller;

import java.util.ArrayList;

import model.User;

public class UserController {
	public static User getAdmin(){
		return new User(0, "admin", "password", "Admin", "Admin", "admin@admin.Fr","0600000000"); 		
	}
	public static User getUser1(){
		 	ArrayList<String> tags = new ArrayList<String>();
	        tags.add("concert");
	        tags.add("rock");
	        User userConnected = new User(1,"lilion","password","Devos","Clément","cdevos.0@gmail.com","0620044858");
	        userConnected.setTags(tags);
	        return userConnected;
		
	}
}
