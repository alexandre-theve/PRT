package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@UserId")
public class User implements Serializable {
	private Integer id;
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	private String phone;
	private ArrayList<Evenement> evenementList;
	private List<ListediffusionHasUser> listediffusionHasUserList;
	private List<Recherches> recherchesList;
	private List<UserHasEvenement> userHasEvenementList;

	public User() {
		super();
	}

	public User(Integer id, String login, String password, String nom,
			String prenom, String email, String phone) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.phone = phone;
		this.evenementList = new ArrayList<Evenement>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Evenement> getEvenementList() {
		return evenementList;
	}

	public void setEvenementList(ArrayList<Evenement> evenements) {
		this.evenementList = evenements;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<ListediffusionHasUser> getListediffusionHasUserList() {
		return listediffusionHasUserList;
	}

	public void setListediffusionHasUserList(
			List<ListediffusionHasUser> listediffusionHasUserList) {
		this.listediffusionHasUserList = listediffusionHasUserList;
	}

	public List<Recherches> getRecherchesList() {
		return recherchesList;
	}

	public void setRecherchesList(List<Recherches> recherchesList) {
		this.recherchesList = recherchesList;
	}

	public List<UserHasEvenement> getUserHasEvenementList() {
		return userHasEvenementList;
	}

	public void setUserHasEvenementList(
			List<UserHasEvenement> userHasEvenementList) {
		this.userHasEvenementList = userHasEvenementList;
	}

}
