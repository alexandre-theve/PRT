package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.android.gms.maps.model.LatLng;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@EvenementId")
public class Evenement implements Serializable {
	private Integer id;
	private String nom;
	private Date dateDebut;
	// minutes
	private Date dateFin;
	private String lieu;
	private double latitude;
	private double longitude;
	private String description;
	private boolean valide;
	private User createur;
	private List<Tags> tagsList;
	private List<Listediffusion> listediffusionList;
	private List<Notifications> notificationsList;
	private List<UserHasEvenement> userHasEvenementList;

	public Evenement() {
		super();
	}

	public Evenement(Integer id) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public List<Listediffusion> getListediffusionList() {
        return listediffusionList;
    }

    public void setListediffusionList(List<Listediffusion> listediffusionList) {
        this.listediffusionList = listediffusionList;
    }

    public List<Notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public User getCreateur() {
        return createur;
    }

    public void setCreateur(User createur) {
        this.createur = createur;
    }

    
    public List<UserHasEvenement> getUserHasEvenementList() {
        return userHasEvenementList;
    }

    public void setUserHasEvenementList(List<UserHasEvenement> userHasEvenementList) {
        this.userHasEvenementList = userHasEvenementList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evenement)) {
            return false;
        }
        Evenement other = (Evenement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.Evenement[ id=" + id + " ]";
    }
}
