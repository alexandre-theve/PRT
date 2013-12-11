/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "user_has_evenement")
@NamedQueries({
    @NamedQuery(name = "UserHasEvenement.findAll", query = "SELECT u FROM UserHasEvenement u"),
    @NamedQuery(name = "UserHasEvenement.findByUserid", query = "SELECT u FROM UserHasEvenement u WHERE u.userHasEvenementPK.userid = :userid"),
    @NamedQuery(name = "UserHasEvenement.findByEvenementid", query = "SELECT u FROM UserHasEvenement u WHERE u.userHasEvenementPK.evenementid = :evenementid"),
    @NamedQuery(name = "UserHasEvenement.findByNotifications", query = "SELECT u FROM UserHasEvenement u WHERE u.notifications = :notifications"),
    @NamedQuery(name = "UserHasEvenement.findByCode", query = "SELECT u FROM UserHasEvenement u WHERE u.code = :code")})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@UserHasEvenementId")
public class UserHasEvenement implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserHasEvenementPK userHasEvenementPK;
    @Size(max = 45)
    @Column(name = "notifications")
    private String notifications;
    @Size(max = 45)
    @Column(name = "code")
    private String code;
    @JoinColumn(name = "Evenement_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evenement evenement;
    @JoinColumn(name = "User_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
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

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
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
