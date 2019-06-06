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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author elode
 */
@Entity
@Table(name = "FIEFDOM")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Fiefdom.findAll", query = "SELECT f FROM Fiefdom f")
	, @NamedQuery(name = "Fiefdom.findById", query = "SELECT f FROM Fiefdom f WHERE f.id = :id")
	, @NamedQuery(name = "Fiefdom.findByName", query = "SELECT f FROM Fiefdom f WHERE f.name = :name")
	, @NamedQuery(name = "Fiefdom.findByUser", query = "SELECT f FROM Fiefdom f WHERE f.uid = :u")
	, @NamedQuery(name = "Fiefdom.findByRace", query = "SELECT f FROM Fiefdom f WHERE f.race = :race")
	, @NamedQuery(name = "Fiefdom.findByGold", query = "SELECT f FROM Fiefdom f WHERE f.gold = :gold")
	, @NamedQuery(name = "Fiefdom.findByLand", query = "SELECT f FROM Fiefdom f WHERE f.land = :land")
	, @NamedQuery(name = "Fiefdom.findByFood", query = "SELECT f FROM Fiefdom f WHERE f.food = :food")
	, @NamedQuery(name = "Fiefdom.findByPeasants", query = "SELECT f FROM Fiefdom f WHERE f.peasants = :peasants")})
public class Fiefdom implements Serializable {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "defender")
	private Collection<Battlereport> battlereportCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "attacker")
	private Collection<Battlereport> battlereportCollection1;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	private Collection<Message> messageCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "toid")
	private Collection<Thread> threadCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "fromid")
	private Collection<Thread> threadCollection1;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "fiefdom")
	private Collection<Relation> relationCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "fiefdom1")
	private Collection<Relation> relationCollection1;

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NAME")
	private String name;
	@Basic(optional = false)
    @NotNull
    @Column(name = "RACE")
	private int race;
	@Basic(optional = false)
    @NotNull
    @Column(name = "GOLD")
	private int gold;
	@Basic(optional = false)
    @NotNull
    @Column(name = "LAND")
	private int land;
	@Basic(optional = false)
    @NotNull
    @Column(name = "FOOD")
	private int food;
	@Basic(optional = false)
    @NotNull
    @Column(name = "PEASANTS")
	private int peasants;
	@JoinColumn(name = "AID", referencedColumnName = "AID")
    @ManyToOne(optional = false)
	private Army aid;
	@JoinColumn(name = "BID", referencedColumnName = "BID")
    @ManyToOne(optional = false)
	private Buildings bid;
	@JoinColumn(name = "UID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Userinfo uid;

	public Fiefdom() {
	}

	public Fiefdom(Integer id) {
		this.id = id;
	}

	public Fiefdom(String name, int race, int gold, int land, int food, int peasants) {
		this.name = name;
		this.race = race;
		this.gold = gold;
		this.land = land;
		this.food = food;
		this.peasants = peasants;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getLand() {
		return land;
	}

	public void setLand(int land) {
		this.land = land;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getPeasants() {
		return peasants;
	}

	public void setPeasants(int peasants) {
		this.peasants = peasants;
	}

	public Army getAid() {
		return aid;
	}

	public void setAid(Army aid) {
		this.aid = aid;
	}

	public Buildings getBid() {
		return bid;
	}

	public void setBid(Buildings bid) {
		this.bid = bid;
	}

	public Userinfo getUid() {
		return uid;
	}

	public void setUid(Userinfo uid) {
		this.uid = uid;
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
		if (!(object instanceof Fiefdom)) {
			return false;
		}
		Fiefdom other = (Fiefdom) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Fiefdom[ id=" + id + " ]";
	}

	@XmlTransient
	public Collection<Relation> getRelationCollection() {
		return relationCollection;
	}

	public void setRelationCollection(Collection<Relation> relationCollection) {
		this.relationCollection = relationCollection;
	}

	@XmlTransient
	public Collection<Relation> getRelationCollection1() {
		return relationCollection1;
	}

	public void setRelationCollection1(Collection<Relation> relationCollection1) {
		this.relationCollection1 = relationCollection1;
	}

	@XmlTransient
	public Collection<Thread> getThreadCollection() {
		return threadCollection;
	}

	public void setThreadCollection(Collection<Thread> threadCollection) {
		this.threadCollection = threadCollection;
	}

	@XmlTransient
	public Collection<Thread> getThreadCollection1() {
		return threadCollection1;
	}

	public void setThreadCollection1(Collection<Thread> threadCollection1) {
		this.threadCollection1 = threadCollection1;
	}

	@XmlTransient
	public Collection<Message> getMessageCollection() {
		return messageCollection;
	}

	public void setMessageCollection(Collection<Message> messageCollection) {
		this.messageCollection = messageCollection;
	}

	@XmlTransient
	public Collection<Battlereport> getBattlereportCollection() {
		return battlereportCollection;
	}

	public void setBattlereportCollection(Collection<Battlereport> battlereportCollection) {
		this.battlereportCollection = battlereportCollection;
	}

	@XmlTransient
	public Collection<Battlereport> getBattlereportCollection1() {
		return battlereportCollection1;
	}

	public void setBattlereportCollection1(Collection<Battlereport> battlereportCollection1) {
		this.battlereportCollection1 = battlereportCollection1;
	}
	
}
