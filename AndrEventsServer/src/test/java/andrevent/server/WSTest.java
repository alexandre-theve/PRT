package andrevent.server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import andrevent.server.model.Evenement;
import andrevent.server.model.User;
import static org.junit.Assert.*;

public class WSTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		User userExpected = new User(1);
		
		// Getting user 1 by id
		User userFromWS = getUserById(1);
		assertEquals(userExpected, userFromWS);
		
		// Getting user 2 by id
		userFromWS = getUserById(2);
		userExpected = new User(2);
		assertEquals(userExpected, userFromWS);
		
		// Getting user 1 by login
		userFromWS = getUserByLogin("admin");
		assertEquals("admin", userFromWS.getLogin());
		
		// user subscription
		userExpected = new User();
		userExpected.setLogin("admin3");
		userExpected.setPassword("admin");
		userFromWS = setNewUser(userExpected);
		assertNotNull(userFromWS.getId());
		
		//event location
		List<Evenement> events = getEventsByUSer(1);
		assertFalse(events.size() != 0);
	}
	
	public List<Evenement> getEventsByUSer(Integer id){
		String JSON;
		try {
			JSON = RESTHelper.GET("http://192.168.70.233:8080/AndrEventServer/event/idUser/"+id);
			
			//List<Evenement> events = mapper.read(JSON, User.class);;
			
			//return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new ArrayList<Evenement>();
	}
	
	public User setNewUser(User user){
		String JSON;
		try {
			System.out.println(mapper.writeValueAsString(user));
			JSON = RESTHelper.POST("http://192.168.70.233:8080/AndrEventServer/user/",mapper.writeValueAsString(user));
			
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
			JSON = RESTHelper.GET("http://192.168.70.233:8080/AndrEventServer/user/login/"+login);
			
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
			JSON = RESTHelper.GET("http://192.168.70.233:8080/AndrEventServer/user/id/"+id);
			
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
