package model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.android.gms.maps.model.LatLng;

public class Evenement implements Serializable{
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
		
	
}
