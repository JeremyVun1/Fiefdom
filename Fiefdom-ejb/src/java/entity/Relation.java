package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "RELATION")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Relation.findAll", query = "SELECT r FROM Relation r")
	, @NamedQuery(name = "Relation.findByFid1", query = "SELECT r FROM Relation r WHERE r.relationPK.fid1 = :fid1")
	, @NamedQuery(name = "Relation.findByFid2", query = "SELECT r FROM Relation r WHERE r.relationPK.fid2 = :fid2")
	, @NamedQuery(name = "Relation.findByRelation", query = "SELECT r FROM Relation r WHERE r.relation = :relation")})
public class Relation implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected RelationPK relationPK;
	@Column(name = "RELATION")
	private Character relation;
	@JoinColumn(name = "FID2", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Fiefdom fiefdom;
	@JoinColumn(name = "FID1", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Fiefdom fiefdom1;

	public Relation() {
	}

	public Relation(RelationPK relationPK) {
		this.relationPK = relationPK;
	}

	public Relation(int fid1, int fid2) {
		this.relationPK = new RelationPK(fid1, fid2);
	}

	public RelationPK getRelationPK() {
		return relationPK;
	}

	public void setRelationPK(RelationPK relationPK) {
		this.relationPK = relationPK;
	}

	public Character getRelation() {
		return relation;
	}

	public void setRelation(Character relation) {
		this.relation = relation;
	}

	public Fiefdom getFiefdom() {
		return fiefdom;
	}

	public void setFiefdom(Fiefdom fiefdom) {
		this.fiefdom = fiefdom;
	}

	public Fiefdom getFiefdom1() {
		return fiefdom1;
	}

	public void setFiefdom1(Fiefdom fiefdom1) {
		this.fiefdom1 = fiefdom1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (relationPK != null ? relationPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Relation)) {
			return false;
		}
		Relation other = (Relation) object;
		if ((this.relationPK == null && other.relationPK != null) || (this.relationPK != null && !this.relationPK.equals(other.relationPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Relation[ relationPK=" + relationPK + " ]";
	}
	
}
