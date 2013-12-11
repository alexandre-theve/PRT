/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "listediffusion")
@NamedQueries({
    @NamedQuery(name = "Listediffusion.findAll", query = "SELECT l FROM Listediffusion l"),
    @NamedQuery(name = "Listediffusion.findById", query = "SELECT l FROM Listediffusion l WHERE l.id = :id"),
    @NamedQuery(name = "Listediffusion.findByTitre", query = "SELECT l FROM Listediffusion l WHERE l.titre = :titre")})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@ListeDiffusionId")
public class Listediffusion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "titre")
    private String titre;
    @JoinTable(name = "listediffusion_has_evenement", joinColumns = {
        @JoinColumn(name = "ListeDiffusion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Evenement_id", referencedColumnName = "id")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Evenement> evenementList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listediffusion")
    @LazyCollection(LazyCollectionOption.FALSE)
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

    @XmlTransient
    public List<Evenement> getEvenementList() {
        return evenementList;
    }

    public void setEvenementList(List<Evenement> evenementList) {
        this.evenementList = evenementList;
    }

    @XmlTransient
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
