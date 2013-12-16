package controller;

import helpers.RESTHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import model.Evenement;
import model.User;

public class UserController {

	private User userConnected;
	ObjectMapper mapper = new ObjectMapper();
	
	public User getUserConnected() {
		return userConnected;
	}

	private User getErrorUser(){
		return new User(-1,"inconnu","inconnu", "inconnu", "inconnu","inconnu","inconnu");
		
	}
	public void setUserConnected(User userConnected) {	
		this.userConnected = userConnected;
	}

	public static User getAdmin() {
		return new User(0, "admin", "password", "Admin", "Admin",
				"admin@admin.Fr", "0600000000");
	}

	public User getUser1(String login, String password) {
		
		/*
		User newUserConnected = new User(1, "lilion", "password", "Devos",
				"Clément", "cdevos.0@gmail.com", "0620044858");*/
		try {
			String JSON = RESTHelper.GET("http://192.168.70.233:8080/AndrEventServer/user/login/"+login);
			return mapper.readValue(JSON, User.class);
		}
		catch (JsonParseException e) {
			e.printStackTrace();
			return new User(0, login, password, "", "", "", "");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return getErrorUser();
		}
		catch(ConnectException Ce){
			Ce.printStackTrace();
			return getErrorUser();
		}
		catch (IOException e) {
			e.printStackTrace();
			return getErrorUser();
		}
		catch (Exception e){
			e.printStackTrace();
			return getErrorUser();
		}
	}

	public String getFullname(User user) {
		return user.getPrenom() + " " + user.getNom();
	}

	public boolean isSubscribedTo(User user, Evenement evenement) {
		return user.getEvenementList().contains(evenement);
	}

	public User createUser(User creatingUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
