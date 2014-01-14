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
	private List<Evenement> evenementList = new ArrayList<Evenement>();
	private List<ListediffusionHasUser> listediffusionHasUserList = new ArrayList<ListediffusionHasUser>();
	private List<Recherches> recherchesList = new ArrayList<Recherches>();
	private List<UserHasEvenement> userHasEvenementList = new ArrayList<UserHasEvenement>();

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

	public void copy(User user) {
		id = user.getId();
		login = user.getLogin();
		password = user.getPassword();
		email = user.getEmail();
		nom = user.getNom();
		prenom = user.getPrenom();
		phone = user.getPhone();
	}

    public User(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public List<ListediffusionHasUser> getListediffusionHasUserList() {
        return listediffusionHasUserList;
    }

    public void setListediffusionHasUserList(List<ListediffusionHasUser> listediffusionHasUserList) {
        this.listediffusionHasUserList = listediffusionHasUserList;
    }

    
    public List<Evenement> getEvenementList() {
        return evenementList;
    }

    public void setEvenementList(List<Evenement> evenementList) {
        this.evenementList = evenementList;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return prenom+ " " + nom;
    }    
}
