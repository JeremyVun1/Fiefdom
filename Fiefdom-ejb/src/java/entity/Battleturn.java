/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JVun
 */
@Entity
@Table(name = "BATTLETURN")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Battleturn.findAll", query = "SELECT b FROM Battleturn b")
	, @NamedQuery(name = "Battleturn.findById", query = "SELECT b FROM Battleturn b WHERE b.id = :id")
	, @NamedQuery(name = "Battleturn.findByBrid", query = "SELECT b FROM Battleturn b WHERE b.brid = :brid order by b.turncount")
	, @NamedQuery(name = "Battleturn.findByIsattacker", query = "SELECT b FROM Battleturn b WHERE b.isattacker = :isattacker")
	, @NamedQuery(name = "Battleturn.findByAttackingunit", query = "SELECT b FROM Battleturn b WHERE b.attackingunit = :attackingunit")
	, @NamedQuery(name = "Battleturn.findByAttackingsize", query = "SELECT b FROM Battleturn b WHERE b.attackingsize = :attackingsize")
	, @NamedQuery(name = "Battleturn.findByArcherloss", query = "SELECT b FROM Battleturn b WHERE b.archerloss = :archerloss")
	, @NamedQuery(name = "Battleturn.findBySpearloss", query = "SELECT b FROM Battleturn b WHERE b.spearloss = :spearloss")
	, @NamedQuery(name = "Battleturn.findByKnightloss", query = "SELECT b FROM Battleturn b WHERE b.knightloss = :knightloss")
	, @NamedQuery(name = "Battleturn.findByWizardloss", query = "SELECT b FROM Battleturn b WHERE b.wizardloss = :wizardloss")})
public class Battleturn implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Column(name = "TURNCOUNT")
	private int turncount;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ISATTACKER")
	private Boolean isattacker;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ATTACKINGUNIT")
	private String attackingunit;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ATTACKINGSIZE")
	private int attackingsize;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ARCHERLOSS")
	private int archerloss;
	@Basic(optional = false)
    @NotNull
    @Column(name = "SPEARLOSS")
	private int spearloss;
	@Basic(optional = false)
    @NotNull
    @Column(name = "KNIGHTLOSS")
	private int knightloss;
	@Basic(optional = false)
    @NotNull
    @Column(name = "WIZARDLOSS")
	private int wizardloss;
	@JoinColumn(name = "BRID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Battlereport brid;

	public Battleturn() {
	}

	public Battleturn(Integer id) {
		this.id = id;
	}

	public Battleturn(int turnCount, Battlereport brid, Boolean isattacker, String attackingunit, int attackingsize, int archerloss, int spearloss, int knightloss, int wizardloss) {
		this.turncount = turnCount;
		this.isattacker = isattacker;
		this.attackingunit = attackingunit;
		this.attackingsize = attackingsize;
		this.archerloss = archerloss;
		this.spearloss = spearloss;
		this.knightloss = knightloss;
		this.wizardloss = wizardloss;
		this.brid = brid;
	}
	
	public Integer getTurnCount() {
		return turncount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsattacker() {
		return isattacker;
	}

	public void setIsattacker(Boolean isattacker) {
		this.isattacker = isattacker;
	}

	public String getAttackingunit() {
		return attackingunit;
	}

	public void setAttackingunit(String attackingunit) {
		this.attackingunit = attackingunit;
	}

	public int getAttackingsize() {
		return attackingsize;
	}

	public void setAttackingsize(int attackingsize) {
		this.attackingsize = attackingsize;
	}

	public int getArcherloss() {
		return archerloss;
	}

	public void setArcherloss(int archerloss) {
		this.archerloss = archerloss;
	}

	public int getSpearloss() {
		return spearloss;
	}

	public void setSpearloss(int spearloss) {
		this.spearloss = spearloss;
	}

	public int getKnightloss() {
		return knightloss;
	}

	public void setKnightloss(int knightloss) {
		this.knightloss = knightloss;
	}

	public int getWizardloss() {
		return wizardloss;
	}

	public void setWizardloss(int wizardloss) {
		this.wizardloss = wizardloss;
	}

	public Battlereport getBrid() {
		return brid;
	}

	public void setBrid(Battlereport brid) {
		this.brid = brid;
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
		if (!(object instanceof Battleturn)) {
			return false;
		}
		Battleturn other = (Battleturn) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Battleturn[ id=" + id + " ]";
	}
	
}
