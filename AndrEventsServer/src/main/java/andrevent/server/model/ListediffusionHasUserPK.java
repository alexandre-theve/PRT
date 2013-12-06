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
public class ListediffusionHasUserPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ListeDiffusion_id")
    private int listeDiffusionid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_id")
    private int userid;

    public ListediffusionHasUserPK() {
    }

    public ListediffusionHasUserPK(int listeDiffusionid, int userid) {
        this.listeDiffusionid = listeDiffusionid;
        this.userid = userid;
    }

    public int getListeDiffusionid() {
        return listeDiffusionid;
    }

    public void setListeDiffusionid(int listeDiffusionid) {
        this.listeDiffusionid = listeDiffusionid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) listeDiffusionid;
        hash += (int) userid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListediffusionHasUserPK)) {
            return false;
        }
        ListediffusionHasUserPK other = (ListediffusionHasUserPK) object;
        if (this.listeDiffusionid != other.listeDiffusionid) {
            return false;
        }
        if (this.userid != other.userid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ListediffusionHasUserPK[ listeDiffusionid=" + listeDiffusionid + ", userid=" + userid + " ]";
    }
    
}
