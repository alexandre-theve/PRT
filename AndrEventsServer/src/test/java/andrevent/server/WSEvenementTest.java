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
import andrevent.server.model.UserHasEvenement;
import static org.junit.Assert.*;

public class WSEvenementTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		// event location
		List<Evenement> events = getEvents();
		System.out.println("Events : " + events);
		assertTrue(events.size() != 0);

		// event location
		events = getEventsByUSer(1);
		System.out.println("Events for user 1 : " + events);
		assertTrue(events.size() != 0);

		System.out.println();

		// event location
		events = getEventsByLocation(50.428038, 2.832823, 1.0);
		System.out.println("Events in radius 1km : " + events);
		assertTrue(events.size() == 0);

		System.out.println();

		events = getEventsByLocation(50.428038, 2.832823, 2.0);
		System.out.println("Events in radius 2km : " + events);
		assertTrue(events.size() > 0);

		System.out.println();

		// event by tag
		events = getEventsByTag(1);
		System.out.println("Events for tag 1 : " + events);
		assertTrue(events.size() != 0);

		System.out.println();

		// subscribe event
		User user = subscribe(1, 2);
		System.out.println("subscribing user 1 to event 2 : " + user);
		assertTrue(user.getUserHasEvenementList().contains(
				new UserHasEvenement(1, 2)));

		System.out.println();

		// unsubscribe event
		user = unsubscribe(1, 2);
		System.out.println("unsubscribing user 1 to event 2 : " + user);
		assertTrue(!user.getUserHasEvenementList().contains(
				new UserHasEvenement(1, 2)));

		System.out.println();

		// subscribe push notification
		Boolean result = subscribePush(1, 1);
		System.out
				.println("subscribing user 1 to push notification to event 1 : "
						+ result);
		assertTrue(result);

		System.out.println();

		// unsubscribe push notification
		result = unsubscribePush(1, 1);
		System.out
				.println("unsubscribing user 1 to push notification to event 1 : "
						+ result);
		assertTrue(result);
	}

	public User subscribe(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.POST(RESTHelper.url
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/true");

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

	public User unsubscribe(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.DELETE(RESTHelper.url
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent);

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

	public Boolean subscribePush(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(RESTHelper.url
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/true");

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

	public Boolean unsubscribePush(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(RESTHelper.url
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/false");

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
	
	public List<Evenement> getEvents() {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events");

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

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

	public List<Evenement> getEventsByTag(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events/tag/" + id);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

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

	public List<Evenement> getEventsByLocation(Double latitude,
			Double longitude, Double rayon) {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events/location/latitude/" + latitude
					+ "/longitude/" + longitude + "/rayon/" + rayon);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

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

	public List<Evenement> getEventsByUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events/idUser/" + id);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

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
