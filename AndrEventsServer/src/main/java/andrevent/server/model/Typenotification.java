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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "typenotification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typenotification.findAll", query = "SELECT t FROM Typenotification t"),
    @NamedQuery(name = "Typenotification.findById", query = "SELECT t FROM Typenotification t WHERE t.id = :id"),
    @NamedQuery(name = "Typenotification.findByTitre", query = "SELECT t FROM Typenotification t WHERE t.titre = :titre")})
public class Typenotification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "titre")
    private String titre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    private List<Notifications> notificationsList;

    public Typenotification() {
    }

    public Typenotification(Integer id) {
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
    public List<Notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
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
        if (!(object instanceof Typenotification)) {
            return false;
        }
        Typenotification other = (Typenotification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Typenotification[ id=" + id + " ]";
    }
    
}
