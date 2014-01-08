/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "evenement")
@NamedQueries({
    @NamedQuery(name = "Evenement.findAll", query = "SELECT e FROM Evenement e"),
    @NamedQuery(name = "Evenement.findById", query = "SELECT e FROM Evenement e WHERE e.id = :id"),
    @NamedQuery(name = "Evenement.findByNom", query = "SELECT e FROM Evenement e WHERE e.nom = :nom"),
    @NamedQuery(name = "Evenement.findByDateDebut", query = "SELECT e FROM Evenement e WHERE e.dateDebut = :dateDebut"),
    @NamedQuery(name = "Evenement.findByDateFin", query = "SELECT e FROM Evenement e WHERE e.dateFin = :dateFin"),
    @NamedQuery(name = "Evenement.findByLieu", query = "SELECT e FROM Evenement e WHERE e.lieu = :lieu"),
    @NamedQuery(name = "Evenement.findByLatitude", query = "SELECT e FROM Evenement e WHERE e.latitude = :latitude"),
    @NamedQuery(name = "Evenement.findByLongitude", query = "SELECT e FROM Evenement e WHERE e.longitude = :longitude"),
    @NamedQuery(name = "Evenement.findByDescription", query = "SELECT e FROM Evenement e WHERE e.description = :description"),
    @NamedQuery(name = "Evenement.findByValide", query = "SELECT e FROM Evenement e WHERE e.valide = :valide"),
    @NamedQuery(name = "Evenement.findByLocation", query = "SELECT e FROM Evenement e WHERE POW(e.latitude - :latitude, 2) + POW(e.longitude - :longitude, 2) <= POW(:rayon, 2)"),
    @NamedQuery(name = "Evenement.findByLocation2", query = "SELECT e FROM Evenement e WHERE 6367 * ACOS(round(COS(RADIANS(90.0-e.latitude)) * COS(RADIANS(90.0-:latitude)) + SIN(RADIANS(90.0-e.latitude)) * SIN(RADIANS(90.0-:latitude)) * COS(RADIANS(e.longitude-:longitude)),15)) <= :rayon"),
    @NamedQuery(name = "Evenement.findByLocation3", query = "SELECT e FROM Evenement e WHERE 6367 * ACOS(round(COS(RADIANS(90-e.latitude)) * COS(RADIANS(90-50.42)) + SIN(RADIANS(90-e.latitude)) * SIN(RADIANS(90.0-:latitude)) * COS(RADIANS(e.longitude-:longitude)),15)) <= :rayon")})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@EvenementId")
public class Evenement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nom")
    private String nom;
    @Column(name = "dateDebut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Column(name = "dateFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    @Size(max = 45)
    @Column(name = "lieu")
    private String lieu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "valide")
    private Boolean valide = false;
    @JoinTable(name = "evenement_has_tags", joinColumns = {
        @JoinColumn(name = "Evenement_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Tags_id", referencedColumnName = "id")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tags> tagsList;
    @ManyToMany(mappedBy = "evenementList")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Listediffusion> listediffusionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evenementid", orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notifications> notificationsList;
    @JoinColumn(name = "createur", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evenement", orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UserHasEvenement> userHasEvenementList;

    public Evenement() {
    }

    public Evenement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public List<Listediffusion> getListediffusionList() {
        return listediffusionList;
    }

    public void setListediffusionList(List<Listediffusion> listediffusionList) {
        this.listediffusionList = listediffusionList;
    }

    public List<Notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public User getCreateur() {
        return createur;
    }

    public void setCreateur(User createur) {
        this.createur = createur;
    }

    
    public List<UserHasEvenement> getUserHasEvenementList() {
        return userHasEvenementList;
    }

    public void setUserHasEvenementList(List<UserHasEvenement> userHasEvenementList) {
        this.userHasEvenementList = userHasEvenementList;
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
        if (!(object instanceof Evenement)) {
            return false;
        }
        Evenement other = (Evenement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.Evenement[ id=" + id + " ]";
    }
    
}
