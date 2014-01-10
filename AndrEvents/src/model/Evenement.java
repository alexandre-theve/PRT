package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.android.gms.maps.model.LatLng;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@EvenementId")
public class Evenement implements Serializable {
	private int id;
	private String nom;
	private Timestamp dateDebut;
	// minutes
	private Timestamp dateFin;
	private String lieu;
	private double latitude;
	private double longitude;
	private String description;
	private boolean valide;
	private User createur;
	private List<Tags> tagsList;
	private List<ListeDiffusion> listediffusionList;
	private List<Notifications> notificationsList;
	private List<UserHasEvenement> userHasEvenementList;

	public Evenement() {
		super();
	}

	public Evenement(String nom, Timestamp dateDebut, Timestamp dateFin,
			String lieu, double latitude, double longitude, String description,
			User creator) {
		super();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.lieu = lieu;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.valide = false;
		this.createur = creator;
	}

	public LatLng getPosition(){
		return new LatLng(latitude, longitude);
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

	public User getCreateur() {
		return createur;
	}

	public void setCreateur(User createur) {
		this.createur = createur;
	}

	public List<Tags> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<Tags> tagsList) {
		this.tagsList = tagsList;
	}

	public List<ListeDiffusion> getListediffusionList() {
		return listediffusionList;
	}

	public void setListediffusionList(List<ListeDiffusion> listediffusionList) {
		this.listediffusionList = listediffusionList;
	}

	public List<Notifications> getNotificationsList() {
		return notificationsList;
	}

	public void setNotificationsList(List<Notifications> notificationsList) {
		this.notificationsList = notificationsList;
	}

	public List<UserHasEvenement> getUserHasEvenementList() {
		return userHasEvenementList;
	}

	public void setUserHasEvenementList(List<UserHasEvenement> userHasEvenementList) {
		this.userHasEvenementList = userHasEvenementList;
	}


}
