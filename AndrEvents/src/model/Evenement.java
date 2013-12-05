package model;

import helpers.DateHelper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.Interval;
import org.joda.time.Period;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Evenement implements Serializable,Parcelable{
	private int id;
	private String nom;
	private Timestamp dateDebut;
	//minutes
	private Timestamp dateFin;
	private String lieu;
	private double latitude;
	private double longitude;
	private String description;
	private boolean valide;
	private User creator;
	
	
	public Evenement() {
		super();
	}
	public Evenement(String nom, Timestamp dateDebut, Timestamp dateFin, String lieu,
			double latitude, double longitude, String description, User creator) {
		super();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.lieu = lieu;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.valide = false;
		this.creator = creator;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Timestamp getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Timestamp dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Timestamp getDateFin() {
		return dateFin;
	}
	public void setDateFin(Timestamp dateFin) {
		this.dateFin = dateFin;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public LatLng getPosition(){
		return new LatLng(latitude, longitude);
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	public String getFormattedDate() {
		Period interval = new Period(dateDebut.getTime(),dateFin.getTime()); 
		if(interval.getDays() > 0){
			return "Du "+DateHelper.FORMATTED_DATE_FORMAT.format(new Date(dateDebut.getTime())) + " au " + DateHelper.FORMATTED_DATE_FORMAT.format(new Date(dateFin.getTime()));
		}
		return "Le " + DateHelper.FORMATTED_DATE_FORMAT.format(new Date(dateDebut.getTime())) + " à " + DateHelper.TIME_FORMAT.format(new Date(dateFin.getTime()));
	}
	
}
