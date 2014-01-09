package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@ListediffusionHasUserId")
public class ListediffusionHasUser {

	private ListediffusionHasUserPK listediffusionHasUserPK;
	private Boolean notifications;
	private User user;
	private ListeDiffusion listediffusion;

	public ListediffusionHasUser() {

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

	public ListeDiffusion getListediffusion() {
		return listediffusion;
	}

	public void setListediffusion(ListeDiffusion listediffusion) {
		this.listediffusion = listediffusion;
	}

}
