package controller;

import helpers.DateHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Evenement;
import model.User;

import org.joda.time.Period;

public class EvenementController {
	public ArrayList<Evenement> findEvenementsAround(double lat, double longitude, int rayon){
		ArrayList<Evenement> toReturn = new ArrayList<Evenement>();
		Calendar cal =  Calendar.getInstance();
		cal.set(2013, 10, 01,8, 00,00);
		Timestamp debts = new Timestamp(cal.getTimeInMillis());
		
		cal.set(2013, 12, 31,23, 00,00);
		Timestamp endts = new Timestamp(cal.getTimeInMillis());
		toReturn.add(new Evenement("Exposition Egypte",
				debts, endts,
				"Louvre-Lens",
				50.43135,2.80458,
				"Magnifique exposition sur des trucs du louvre",
				UserController.getAdmin()));
		cal.set(2013, 28, 11,19, 00,00);
		debts = new Timestamp(cal.getTimeInMillis());
		cal.set(2013, 28, 11,23, 00,00);
		endts = new Timestamp(cal.getTimeInMillis());
		toReturn.add(new Evenement("Concert de rock",
				debts, endts,
				"Le colisée Lens",
				50.48860,2.83086,
				"Concert de rock des papys du web",
				UserController.getAdmin()));
		
		cal.set(2013, 05,12 ,8, 00,00);
		debts = new Timestamp(cal.getTimeInMillis());
		cal.set(2013, 05,12 ,9, 00,00);
		endts = new Timestamp(cal.getTimeInMillis());
		toReturn.add(new Evenement("Rencontres IG2Iennes",
				debts, endts,
				"IG2I",
				50.43515,2.82351,
				"Comme tous les ans, venez rencontrer Atos",
				UserController.getAdmin()));
		return toReturn;
		
	}

	public boolean subscribe(Evenement evenement, User userConnected) {
		userConnected.getEvenementList().add(evenement);
		return true;
	}
	
	
}
