package andrevent.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import andrevent.server.model.Listediffusion;
import andrevent.server.model.ListediffusionHasUser;
import andrevent.server.model.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WSListeDiffusionTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		//event location
		List<Listediffusion> listediffusions = getListeDiffusion();
		System.out.println("Events for user 1 : " + listediffusions);
		assertTrue(listediffusions.size() != 0);
		
		System.out.println();
		
		//subscribe liste
		User user = subscribe(1, 2);
		System.out.println("subscribing user 1 to liste 2 : " + user);
		assertTrue(user.getListediffusionHasUserList().contains(new ListediffusionHasUser(2,1)));
		
		System.out.println();
		
		//unsubscribe liste
		user = unsubscribe(1, 2);
		System.out.println("unsubscribing user 1 to liste 2 : " + user);
		assertTrue(!user.getListediffusionHasUserList().contains(new ListediffusionHasUser(2,1)));
		
		System.out.println();
		
		//subscribe push notification
		Boolean result = subscribePush(1, 1);
		System.out.println("subscribing user 1 to push notification to liste 1 : " + result);
		assertTrue(result);
		
		System.out.println();
		
		//unsubscribe push notification
		result = unsubscribePush(1, 1);
		System.out.println("unsubscribing user 1 to push notification to liste 1 : " + result);
		assertTrue(result);
	}
	
	public List<Listediffusion> getListeDiffusion(){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/listes");
			
			List<Listediffusion> listediffusions = mapper.readValue(JSON, new TypeReference<List<Listediffusion>>(){});
			
			return listediffusions;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new ArrayList<Listediffusion>();
	}
	
	public User subscribe(Integer idUser, Integer idListe){
		String JSON;
		try {
			JSON = RESTHelper.POST(RESTHelper.url+"/AndrEventServer/listes/inscription/user/"+idUser+"/liste/"+idListe+"/push/true");
			
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
	
	public User unsubscribe(Integer idUser, Integer idListe){
		String JSON;
		try {
			JSON = RESTHelper.DELETE(RESTHelper.url+"/AndrEventServer/listes/inscription/user/"+idUser+"/liste/"+idListe);
			
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
	
	public Boolean subscribePush(Integer idUser, Integer idListe){
		String JSON;
		try {
			JSON = RESTHelper.PUT(RESTHelper.url+"/AndrEventServer/listes/inscription/user/"+idUser+"/liste/"+idListe+"/push/true");
			
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
	
	public Boolean unsubscribePush(Integer idUser, Integer idListe){
		String JSON;
		try {
			JSON = RESTHelper.PUT(RESTHelper.url+"/AndrEventServer/listes/inscription/user/"+idUser+"/liste/"+idListe+"/push/false");
			
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
}
