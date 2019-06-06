/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author elode
 */
@Entity
@Table(name = "BUILDINGS")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Buildings.findAll", query = "SELECT b FROM Buildings b")
	, @NamedQuery(name = "Buildings.findByBid", query = "SELECT b FROM Buildings b WHERE b.bid = :bid")
	, @NamedQuery(name = "Buildings.findByFarms", query = "SELECT b FROM Buildings b WHERE b.farms = :farms")
	, @NamedQuery(name = "Buildings.findByMarkets", query = "SELECT b FROM Buildings b WHERE b.markets = :markets")
	, @NamedQuery(name = "Buildings.findByMines", query = "SELECT b FROM Buildings b WHERE b.mines = :mines")
	, @NamedQuery(name = "Buildings.findByTowers", query = "SELECT b FROM Buildings b WHERE b.towers = :towers")})
public class Buildings implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BID")
	private Integer bid;
	@Basic(optional = false)
    @NotNull
    @Column(name = "FARMS")
	private int farms;
	@Basic(optional = false)
    @NotNull
    @Column(name = "MARKETS")
	private int markets;
	@Basic(optional = false)
    @NotNull
    @Column(name = "MINES")
	private int mines;
	@Basic(optional = false)
    @NotNull
    @Column(name = "TOWERS")
	private int towers;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bid")
	private Collection<Fiefdom> fiefdomCollection;

	public Buildings() {
	}

	public Buildings(Integer bid) {
		this.bid = bid;
	}

	public Buildings(int farms, int markets, int mines, int towers) {
		this.farms = farms;
		this.markets = markets;
		this.mines = mines;
		this.towers = towers;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public int getFarms() {
		return farms;
	}

	public void setFarms(int farms) {
		this.farms = farms;
	}

	public int getMarkets() {
		return markets;
	}

	public void setMarkets(int markets) {
		this.markets = markets;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getTowers() {
		return towers;
	}

	public void setTowers(int towers) {
		this.towers = towers;
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
		hash += (bid != null ? bid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Buildings)) {
			return false;
		}
		Buildings other = (Buildings) object;
		if ((this.bid == null && other.bid != null) || (this.bid != null && !this.bid.equals(other.bid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Buildings[ bid=" + bid + " ]";
	}
	
}
