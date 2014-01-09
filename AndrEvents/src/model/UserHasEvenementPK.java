package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserHasEvenementPK {

	private Integer userid;
	private Integer evenementid;

	public UserHasEvenementPK() {

	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getEvenementid() {
		return evenementid;
	}

	public void setEvenementid(Integer evenementid) {
		this.evenementid = evenementid;
	}

}
