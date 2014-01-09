package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@ListeDiffusionId")
public class ListeDiffusion {

	private Integer id;
	private String titre;
	private List<Evenement> evenementList;
	private List<ListediffusionHasUser> listediffusionHasUserList;

	public ListeDiffusion() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<Evenement> getEvenementList() {
		return evenementList;
	}

	public void setEvenementList(List<Evenement> evenementList) {
		this.evenementList = evenementList;
	}

	public List<ListediffusionHasUser> getListediffusionHasUserList() {
		return listediffusionHasUserList;
	}

	public void setListediffusionHasUserList(
			List<ListediffusionHasUser> listediffusionHasUserList) {
		this.listediffusionHasUserList = listediffusionHasUserList;
	}

}
