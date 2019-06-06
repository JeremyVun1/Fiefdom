package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ARMY")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Army.findAll", query = "SELECT a FROM Army a")
	, @NamedQuery(name = "Army.findByAid", query = "SELECT a FROM Army a WHERE a.aid = :aid")
	, @NamedQuery(name = "Army.findByKnights", query = "SELECT a FROM Army a WHERE a.knights = :knights")
	, @NamedQuery(name = "Army.findByArchers", query = "SELECT a FROM Army a WHERE a.archers = :archers")
	, @NamedQuery(name = "Army.findBySpears", query = "SELECT a FROM Army a WHERE a.spears = :spears")
	, @NamedQuery(name = "Army.findByWizards", query = "SELECT a FROM Army a WHERE a.wizards = :wizards")})
public class Army implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AID")
	private Integer aid;
	@Basic(optional = false)
    @NotNull
    @Column(name = "KNIGHTS")
	private int knights;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ARCHERS")
	private int archers;
	@Basic(optional = false)
    @NotNull
    @Column(name = "SPEARS")
	private int spears;
	@Basic(optional = false)
    @NotNull
    @Column(name = "WIZARDS")
	private int wizards;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "aid")
	private Collection<Fiefdom> fiefdomCollection;

	public Army() {
	}

	public Army(Integer aid) {
		this.aid = aid;
	}

	public Army(int knights, int archers, int spears, int wizards) {
		this.knights = knights;
		this.archers = archers;
		this.spears = spears;
		this.wizards = wizards;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public int getKnights() {
		return knights;
	}

	public void setKnights(int knights) {
		this.knights = knights;
	}

	public int getArchers() {
		return archers;
	}

	public void setArchers(int archers) {
		this.archers = archers;
	}

	public int getSpears() {
		return spears;
	}

	public void setSpears(int spears) {
		this.spears = spears;
	}

	public int getWizards() {
		return wizards;
	}

	public void setWizards(int wizards) {
		this.wizards = wizards;
	}

	@XmlTransient
	public Collection<Fiefdom> getFiefdomCollection() {
		return fiefdomCollection;
	}

	public void setFiefdomCollection(Collection<Fiefdom> fiefdomCollection) {
		this.fiefdomCollection = fiefdomCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (aid != null ? aid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Army)) {
			return false;
		}
		Army other = (Army) object;
		if ((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Army[ aid=" + aid + " ]";
	}
	
}
