package controller;


import helpers.GPSHelper;
import helpers.RESTHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import model.Evenement;
import model.User;
import model.UserHasEvenement;
import android.content.Context;
import android.location.Location;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UserController extends GenericController{

	private User userConnected;
	ObjectMapper mapper = new ObjectMapper();
	private Context context;
	private GPSHelper gps;
	
	public UserController(){
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	public User getUserConnected() {
		return userConnected;
	}

	public List<Evenement> getEvenementsOfUSer(User user) {
		ArrayList<Evenement> evenements = new ArrayList<Evenement>();
		for(UserHasEvenement userHasEvenement : user.getUserHasEvenementList())
			evenements.add(userHasEvenement.getEvenement());
		return evenements;
	}
	
	public List<Evenement> getEvenementsCreatedByUser(User user) {
		return user.getEvenementList();
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

	public User loginUser(String login, String password) {
		
		try {
			String JSON = RESTHelper.GET(URL+"/AndrEventServer/user/login/"+login);
			return mapper.readValue(JSON, User.class);
		}
		catch (JsonParseException e) {
			e.printStackTrace();
			return new User(-1, login, password, "", "", "", "");
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
		System.out.println("isSubscribedTo " + user.getUserHasEvenementList() + " - " + evenement);
		for(UserHasEvenement userHasEvenement : user.getUserHasEvenementList())
			if(userHasEvenement.getUserHasEvenementPK().getEvenementid() == evenement.getId() && userHasEvenement.getUserHasEvenementPK().getUserid() == user.getId())
				return true;
		return false;
	}

	public User createUser(User user) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(URL+"/AndrEventServer/user/",mapper.writeValueAsString(user));
			Boolean result = JSON.equals("true");
			return user;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return new User();
		
	}

	public User editUser(User editingUser) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(URL+"/AndrEventServer/user/",mapper.writeValueAsString(editingUser));
			
			Boolean result = JSON.equals("true");
			
			return editingUser;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return new User();
	}

	public Location getUserCurrentPostition() {
		if(gps == null){
			gps = new GPSHelper(context);
		}
		return  gps.getLocation();
	}
	
	public UserHasEvenement getUserHasEvenementByCode(String code){
		String JSON;
		try {
			JSON = RESTHelper.GET(URL+"/AndrEventServer/user/code/"+code);
			
			UserHasEvenement user = mapper.readValue(JSON, UserHasEvenement.class);
			
			return user;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return new UserHasEvenement();
	}
	
	public UserHasEvenement getInscription(User user,Evenement evt){
		for (UserHasEvenement inscription : user.getUserHasEvenementList()) {
			if(inscription.getEvenement().equals(evt)){
				return inscription;
			}
		}
		return new UserHasEvenement();
	}
	

}
