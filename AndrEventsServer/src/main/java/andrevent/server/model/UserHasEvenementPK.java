/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class UserHasEvenementPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -556835049580182989L;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "User_id")
    private int userid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Evenement_id")
    private int evenementid;

    public UserHasEvenementPK() {
    }

    public UserHasEvenementPK(int userid, int evenementid) {
        this.userid = userid;
        this.evenementid = evenementid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEvenementid() {
        return evenementid;
    }

    public void setEvenementid(int evenementid) {
        this.evenementid = evenementid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userid;
        hash += (int) evenementid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasEvenementPK)) {
            return false;
        }
        UserHasEvenementPK other = (UserHasEvenementPK) object;
        if (this.userid != other.userid) {
            return false;
        }
        if (this.evenementid != other.evenementid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "andrevent.server.model.UserHasEvenementPK[ userid=" + userid + ", evenementid=" + evenementid + " ]";
    }
    
}
