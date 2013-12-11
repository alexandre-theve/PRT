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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "listediffusion_has_user")
@NamedQueries({
    @NamedQuery(name = "ListediffusionHasUser.findAll", query = "SELECT l FROM ListediffusionHasUser l"),
    @NamedQuery(name = "ListediffusionHasUser.findByListeDiffusionid", query = "SELECT l FROM ListediffusionHasUser l WHERE l.listediffusionHasUserPK.listeDiffusionid = :listeDiffusionid"),
    @NamedQuery(name = "ListediffusionHasUser.findByUserid", query = "SELECT l FROM ListediffusionHasUser l WHERE l.listediffusionHasUserPK.userid = :userid"),
    @NamedQuery(name = "ListediffusionHasUser.findByNotifications", query = "SELECT l FROM ListediffusionHasUser l WHERE l.notifications = :notifications")})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@ListediffusionHasUserId")
public class ListediffusionHasUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListediffusionHasUserPK listediffusionHasUserPK;
    @Size(max = 45)
    @Column(name = "notifications")
    private String notifications;
    @JoinColumn(name = "User_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "ListeDiffusion_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Listediffusion listediffusion;

    public ListediffusionHasUser() {
    }

    public ListediffusionHasUser(ListediffusionHasUserPK listediffusionHasUserPK) {
        this.listediffusionHasUserPK = listediffusionHasUserPK;
    }

    public ListediffusionHasUser(int listeDiffusionid, int userid) {
        this.listediffusionHasUserPK = new ListediffusionHasUserPK(listeDiffusionid, userid);
    }

    public ListediffusionHasUserPK getListediffusionHasUserPK() {
        return listediffusionHasUserPK;
    }

    public void setListediffusionHasUserPK(ListediffusionHasUserPK listediffusionHasUserPK) {
        this.listediffusionHasUserPK = listediffusionHasUserPK;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listediffusion getListediffusion() {
        return listediffusion;
    }

    public void setListediffusion(Listediffusion listediffusion) {
        this.listediffusion = listediffusion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listediffusionHasUserPK != null ? listediffusionHasUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListediffusionHasUser)) {
            return false;
        }
        ListediffusionHasUser other = (ListediffusionHasUser) object;
        if ((this.listediffusionHasUserPK == null && other.listediffusionHasUserPK != null) || (this.listediffusionHasUserPK != null && !this.listediffusionHasUserPK.equals(other.listediffusionHasUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.ListediffusionHasUser[ listediffusionHasUserPK=" + listediffusionHasUserPK + " ]";
    }
    
}
