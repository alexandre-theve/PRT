package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@UserHasEvenementId")
public class UserHasEvenement implements Serializable{

	private UserHasEvenementPK userHasEvenementPK;
	private Boolean notifications;
	private String code;
	private Evenement evenement;
	private User user;

	public UserHasEvenement() {

	}
	
	public UserHasEvenement(UserHasEvenementPK userHasEvenementPK) {
        this.userHasEvenementPK = userHasEvenementPK;
    }

    public UserHasEvenement(int userid, int evenementid) {
        this.userHasEvenementPK = new UserHasEvenementPK(userid, evenementid);
    }

    public UserHasEvenementPK getUserHasEvenementPK() {
        return userHasEvenementPK;
    }

    public void setUserHasEvenementPK(UserHasEvenementPK userHasEvenementPK) {
        this.userHasEvenementPK = userHasEvenementPK;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userHasEvenementPK != null ? userHasEvenementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasEvenement)) {
            return false;
        }
        UserHasEvenement other = (UserHasEvenement) object;
        if ((this.userHasEvenementPK == null && other.userHasEvenementPK != null) || (this.userHasEvenementPK != null && !this.userHasEvenementPK.equals(other.userHasEvenementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.UserHasEvenement[ userHasEvenementPK=" + userHasEvenementPK + " ]";
    }
}
