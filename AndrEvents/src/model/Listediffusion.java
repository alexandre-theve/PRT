package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@ListeDiffusionId")
public class Listediffusion implements Serializable {

	private Integer id;
	private String titre;
	private List<Evenement> evenementList;
	private List<ListediffusionHasUser> listediffusionHasUserList;

	public Listediffusion() {

	}

	public Listediffusion(Integer id) {
        this.id = id;
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

    public void setListediffusionHasUserList(List<ListediffusionHasUser> listediffusionHasUserList) {
        this.listediffusionHasUserList = listediffusionHasUserList;
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
        if (!(object instanceof Listediffusion)) {
            return false;
        }
        Listediffusion other = (Listediffusion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.Listediffusion[ id=" + id + " ]";
    }

}
