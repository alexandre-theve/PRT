package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private int id;
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	private String phone;
	private ArrayList<Evenement> evenements;
	private ArrayList<String> tags;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User(int id, String login, String password, String nom,
			String prenom, String email,String Phone) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.phone = phone;
		this.evenements = new ArrayList<Evenement>();
		this.tags = new  ArrayList<String>();
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
	public ArrayList<Evenement> getEvenements() {
		return evenements;
	}
	public void setEvenements(ArrayList<Evenement> evenements) {
		this.evenements = evenements;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFullname() {
		return prenom + " " + nom;
	}
	public boolean isSubscribedTo(Evenement evenement) {
		return evenements.contains(evenement);
	}
	
	
	
	
}
