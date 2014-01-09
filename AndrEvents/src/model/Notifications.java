package model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@NotificationsId")
public class Notifications {

	private Integer id;
	private Date date;
	private String titre;
	private Evenement evenementid;
	private Typenotification type;

	public Notifications() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Evenement getEvenementid() {
		return evenementid;
	}

	public void setEvenementid(Evenement evenementid) {
		this.evenementid = evenementid;
	}

	public Typenotification getType() {
		return type;
	}

	public void setType(Typenotification type) {
		this.type = type;
	}

}
