package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListediffusionHasUserPK implements Serializable{

	private Integer listeDiffusionid;
	private Integer userid;

	public ListediffusionHasUserPK() {
	}

	public Integer getListeDiffusionid() {
		return listeDiffusionid;
	}

	public void setListeDiffusionid(Integer listeDiffusionid) {
		this.listeDiffusionid = listeDiffusionid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
