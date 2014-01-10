package controller;

import helpers.RESTHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Evenement;
import model.Tags;
import model.User;

public class EvenementController extends GenericController{
	
	ObjectMapper mapper = new ObjectMapper();
	
	public ArrayList<Evenement> findEvenementsAround(double lat, double longitude, int rayon){
		try {
			String JSON = RESTHelper.GET(URL+"/events/location/latitude/"+lat+"/longitude/"+longitude+"/rayon/"+rayon);
			return (ArrayList<Evenement>)(mapper.readValue(JSON, mapper.getTypeFactory().constructCollectionType(List.class, Evenement.class)));
		}
		catch (JsonParseException e) {
			e.printStackTrace();
			return new ArrayList<Evenement>();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return new ArrayList<Evenement>();
		}
		catch(ConnectException Ce){
			Ce.printStackTrace();
			return new ArrayList<Evenement>();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<Evenement>();
		}
		catch (Exception e){
			e.printStackTrace();
			return new ArrayList<Evenement>();
		}
		
	}
	
	public User subscribe(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.POST(URL
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/true");

			User user = mapper.readValue(JSON, User.class);

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

	public User unsubscribe(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.DELETE(URL
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent);

			User user = mapper.readValue(JSON, User.class);

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

	public Boolean subscribePush(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(URL
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/true");

			Boolean result = JSON.equals("true");

			return result;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public Boolean unsubscribePush(Integer idUser, Integer idEvent) {
		String JSON;
		try {
			JSON = RESTHelper.PUT(URL
					+ "/AndrEventServer/events/inscription/user/" + idUser
					+ "/event/" + idEvent + "/push/false");

			Boolean result = JSON.equals("true");

			return result;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public List<Evenement> getEvents() {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events");

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

			return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Evenement>();
	}

	public List<Evenement> getEventsByTag(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events/tag/" + id);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

			return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Evenement>();
	}

	public List<Evenement> getEventsByLocation(Double latitude,
			Double longitude, Double rayon) {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events/location/latitude/" + latitude
					+ "/longitude/" + longitude + "/rayon/" + rayon);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

			return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Evenement>();
	}

	public List<Evenement> getEventsByUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events/idUser/" + id);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

			return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Evenement>();
	}
	
	public List<Evenement> getEventsForUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events/suggestion/idUser/" + id);

			List<Evenement> events = mapper.readValue(JSON,
					new TypeReference<List<Evenement>>() {
					});

			return events;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Evenement>();
	}
	
	public List<Tags> getTagsForUSer(Integer id) {
		String JSON;
		try {
			JSON = RESTHelper.GET(URL
					+ "/AndrEventServer/events/suggestionTags/idUser/" + id);

			List<Tags> tags = mapper.readValue(JSON,
					new TypeReference<List<Tags>>() {
					});

			return tags;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Tags>();
	}
	
	
}
