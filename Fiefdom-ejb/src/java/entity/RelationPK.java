package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RelationPK implements Serializable {

	@Basic(optional = false)
    @NotNull
    @Column(name = "FID1")
	private int fid1;
	@Basic(optional = false)
    @NotNull
    @Column(name = "FID2")
	private int fid2;

	public RelationPK() {
	}

	public RelationPK(int fid1, int fid2) {
		this.fid1 = fid1;
		this.fid2 = fid2;
	}

	public int getFid1() {
		return fid1;
	}

	public void setFid1(int fid1) {
		this.fid1 = fid1;
	}

	public int getFid2() {
		return fid2;
	}

	public void setFid2(int fid2) {
		this.fid2 = fid2;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) fid1;
		hash += (int) fid2;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof RelationPK)) {
			return false;
		}
		RelationPK other = (RelationPK) object;
		if (this.fid1 != other.fid1) {
			return false;
		}
		if (this.fid2 != other.fid2) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.RelationPK[ fid1=" + fid1 + ", fid2=" + fid2 + " ]";
	}
	
}
