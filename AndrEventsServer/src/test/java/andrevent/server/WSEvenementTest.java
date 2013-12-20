package andrevent.server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrevent.server.model.Evenement;
import andrevent.server.model.User;
import static org.junit.Assert.*;

public class WSEvenementTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		//event location
		List<Evenement> events = getEventsByUSer(1);
		System.out.println("Events for user 1 : " + events);
		assertTrue(events.size() != 0);
		
		System.out.println();
		
		//event location
		events = getEventsByLocation(50.428038, 2.832823, 1.0);
		System.out.println("Events in radius 1km : " + events);
		assertTrue(events.size() == 0);

		System.out.println();

		events = getEventsByLocation(50.428038, 2.832823, 2.0);
		System.out.println("Events in radius 2km : " + events);
		assertTrue(events.size() > 0);
		
		System.out.println();
		
		//event by tag
		events = getEventsByTag(1);
		System.out.println("Events for tag 1 : " + events);
		assertTrue(events.size() != 0);
	}
	
	public List<Evenement> getEventsByTag(Integer id){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/events/tag/"+id);
			
			List<Evenement> events = mapper.readValue(JSON, new TypeReference<List<Evenement>>(){});
			
			return events;
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
	
	public List<Evenement> getEventsByLocation(Double latitude, Double longitude, Double rayon){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/events/location/latitude/"+latitude+"/longitude/"+longitude+"/rayon/"+rayon);
			
			List<Evenement> events = mapper.readValue(JSON, new TypeReference<List<Evenement>>(){});
			
			return events;
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
	
	public List<Evenement> getEventsByUSer(Integer id){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/events/idUser/"+id);
			
			List<Evenement> events = mapper.readValue(JSON, new TypeReference<List<Evenement>>(){});
			
			return events;
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
}
