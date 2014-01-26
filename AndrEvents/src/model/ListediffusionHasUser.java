package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@ListediffusionHasUserId")
public class ListediffusionHasUser implements Serializable {

	private ListediffusionHasUserPK listediffusionHasUserPK;
	private Boolean notifications;
	private User user;
	private Listediffusion listediffusion;

	public ListediffusionHasUser() {

	}

	public ListediffusionHasUser(ListediffusionHasUserPK listediffusionHasUserPK) {
		this.listediffusionHasUserPK = listediffusionHasUserPK;
	}

	public ListediffusionHasUser(int listeDiffusionid, int userid) {
		this.listediffusionHasUserPK = new ListediffusionHasUserPK(
				listeDiffusionid, userid);
	}

	public ListediffusionHasUserPK getListediffusionHasUserPK() {
		return listediffusionHasUserPK;
	}

	public void setListediffusionHasUserPK(
			ListediffusionHasUserPK listediffusionHasUserPK) {
		this.listediffusionHasUserPK = listediffusionHasUserPK;
	}

	public Boolean getNotifications() {
		return notifications;
	}

	public void setNotifications(Boolean notifications) {
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
		hash += (listediffusionHasUserPK != null ? listediffusionHasUserPK
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ListediffusionHasUser)) {
			return false;
		}
		ListediffusionHasUser other = (ListediffusionHasUser) object;
		if ((this.listediffusionHasUserPK == null && other.listediffusionHasUserPK != null)
				|| (this.listediffusionHasUserPK != null && !this.listediffusionHasUserPK
						.equals(other.listediffusionHasUserPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "andrevent.server.model.ListediffusionHasUser[ listediffusionHasUserPK="
				+ listediffusionHasUserPK + " ]";
	}
}
