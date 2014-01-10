package controller;

import helpers.RESTHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.location.Location;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Evenement;
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
	
	public boolean subscribe(Evenement evenement, User userConnected) {
		userConnected.getEvenementList().add(evenement);
		return true;
	}

	
	
	
}
