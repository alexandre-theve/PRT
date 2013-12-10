package controller;

import java.util.ArrayList;

import model.Evenement;
import model.User;

public class UserController {
	
	private User userConnected;

	public User getUserConnected() {
		return userConnected;
	}

	public void setUserConnected(User userConnected) {
		this.userConnected = userConnected;
	}
	
	public static User getAdmin(){
		return new User(0, "admin", "password", "Admin", "Admin", "admin@admin.Fr","0600000000"); 		
	}
	public static User getUser1(){
		 	ArrayList<String> tags = new ArrayList<String>();
	        tags.add("concert");
	        tags.add("rock");
	        User newUserConnected = new User(1,"lilion","password","Devos","Clément","cdevos.0@gmail.com","0620044858");
	        newUserConnected.setTags(tags);
	        return newUserConnected;
		
	}
	
	public String getFullname(User user) {
		return user.getPrenom() + " " + user.getNom();
	}
	public boolean isSubscribedTo(User user, Evenement evenement) {
		return user.getEvenements().contains(evenement);
	}
	
	
}
