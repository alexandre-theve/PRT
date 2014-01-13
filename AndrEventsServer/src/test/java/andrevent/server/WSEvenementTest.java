package andrevent.server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrevent.server.model.Evenement;
import andrevent.server.model.Tags;
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;
import static org.junit.Assert.*;

public class WSEvenementTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		// events
		List<Evenement> events = getEvents();
		System.out.println("Events : " + events);
		assertTrue(events.size() != 0);

		// events by user
		events = getEventsByUSer(1);
		System.out.println("Events of user 1 : " + events);
		assertTrue(events.size() != 0);
		
		// events suggestion for user
		events = getEventsForUSer(1);
		System.out.println("Events for user 1 : " + events);
		assertTrue(events.size() != 0);
		
		// tags suggestion for user
		List<Tags> tags = getTagsForUSer(1);
		System.out.println("Tags for user 1 : " + tags);
		assertTrue(tags.size() != 0);

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
		
		// event by tags
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(5);
		ids.add(6);
		events = getEventsByTags(ids);
		System.out.println("Events for tags 1, 5, 6 : " + events);
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
	
	public List<Evenement> getEventsByTags(ArrayList<Integer> ids) {
		String JSON;
		try {
			JSON = RESTHelper.POST(RESTHelper.url
					+ "/AndrEventServer/events/tags", mapper.writeValueAsString(ids));

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
	
	public List<Evenement> getEventsForUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events/suggestion/idUser/" + id);

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
	
	public List<Tags> getTagsForUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url
					+ "/AndrEventServer/events/suggestionTags/idUser/" + id);

			List<Tags> tags = mapper.readValue(JSON,
					new TypeReference<List<Tags>>() {
					});

			return tags;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}

		return new ArrayList<Tags>();
	}
}
