package controller;

import helpers.RESTHelper;

import java.io.IOException;
import java.net.ConnectException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Evenement;
import model.User;


public class UserController extends GenericController{

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

	public User loginUser(String login, String password) {
		
		try {
			String JSON = RESTHelper.GET(URL+"/user/login/"+login);
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
		return user.getEvenementList().contains(evenement);
	}

	public User createUser(User creatingUser) {
		
			String JSON;
			try {
				String jsonCreate = mapper.writeValueAsString(creatingUser);
				JSON = RESTHelper.POST_PUT(URL+"/user/","POST",jsonCreate);
				return mapper.readValue(JSON, User.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
				return creatingUser;
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

	public User editUser(User creatingUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
