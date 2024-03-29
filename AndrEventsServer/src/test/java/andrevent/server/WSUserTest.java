package andrevent.server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrevent.server.model.Evenement;
import andrevent.server.model.Recherches;
import andrevent.server.model.User;
import static org.junit.Assert.*;

public class WSUserTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		User userExpected = new User(1);
		
		// Getting user 1 by id
		User userFromWS = getUserById(1);
		assertEquals(userExpected, userFromWS);
		System.out.println("Getting user by id 1");
		
		System.out.println();
		
		// Getting user 1 by login
		userFromWS = getUserByLogin("admin");
		System.out.println("Getting user by login " + userFromWS.getLogin());
		assertEquals("admin", userFromWS.getLogin());
		
		System.out.println();
		
		// user subscription
		userExpected = new User();
		userExpected.setLogin("admin3");
		userExpected.setPassword("admin");
		userFromWS = setNewUser(userExpected);
		System.out.println("creating user " + userFromWS.getId());
		assertNotNull(userFromWS.getId());
		
		System.out.println();
		
		// user editing
		userFromWS.setLogin("adminEdited");
		Boolean result = editUser(userFromWS);
		System.out.println("editing user " + userFromWS.getId() + " : " + result);
		assertTrue(result);
		
		System.out.println();
		
		// user deleting
		assertTrue(deleteUser(userFromWS.getId()));
		System.out.println("deleting user " + userFromWS.getId() + " : " + result);
		
		// save user search
		userFromWS = getUserById(1);
		Recherches recherche = new Recherches();
		recherche.setUserid(userFromWS);
		recherche.setKeyword("test recherche");
		recherche = setRecherche(recherche);
		System.out.println("creating recherche " + recherche.getId());
		assertNotNull(recherche.getId());
	}
	
	public Recherches setRecherche(Recherches recherche){
		String JSON;
		try {
			JSON = RESTHelper.POST(RESTHelper.url+"/AndrEventServer/user/recherche/",mapper.writeValueAsString(recherche));
			
			recherche = mapper.readValue(JSON, Recherches.class);
			
			return recherche;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new Recherches();
	}
	
	public Boolean deleteUser(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.DELETE(RESTHelper.url+"/AndrEventServer/user/id/"+id);
			
			Boolean result = JSON.equals("true");
			
			return result;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return false;
	}
	
	public Boolean editUser(User user) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(RESTHelper.url+"/AndrEventServer/user/",mapper.writeValueAsString(user));
			
			Boolean result = JSON.equals("true");
			
			return result;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return false;
	}
	
	public User setNewUser(User user){
		String JSON;
		try {
			JSON = RESTHelper.POST(RESTHelper.url+"/AndrEventServer/user/",mapper.writeValueAsString(user));
			
			user = mapper.readValue(JSON, User.class);
			
			return user;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new User();
	}
	
	public User getUserByLogin(String login){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/user/login/"+login);
			
			User user = mapper.readValue(JSON, User.class);
			
			return user;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new User();
	}
	
	public User getUserById(Integer id){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/user/id/"+id);
			
			User user = mapper.readValue(JSON, User.class);
			
			return user;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new User();
	}

}
