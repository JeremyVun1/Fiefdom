/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JVun
 */
@Entity
@Table(name = "BATTLEREPORT")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Battlereport.findAll", query = "SELECT b FROM Battlereport b")
	, @NamedQuery(name = "Battlereport.findById", query = "SELECT b FROM Battlereport b WHERE b.id = :id")
	, @NamedQuery(name = "Battlereport.findByAttackerwin", query = "SELECT b FROM Battlereport b WHERE b.attackerwin = :attackerwin")
	, @NamedQuery(name = "Battlereport.findByFiefdom", query = "SELECT b FROM Battlereport b WHERE b.attacker = :fiefdom OR b.defender = :fiefdom")
	, @NamedQuery(name = "Battlereport.findByGoldgain", query = "SELECT b FROM Battlereport b WHERE b.goldgain = :goldgain")
	, @NamedQuery(name = "Battlereport.findByLandgain", query = "SELECT b FROM Battlereport b WHERE b.landgain = :landgain")
	, @NamedQuery(name = "Battlereport.findByPeasantgain", query = "SELECT b FROM Battlereport b WHERE b.peasantgain = :peasantgain")
	, @NamedQuery(name = "Battlereport.findByAarchers", query = "SELECT b FROM Battlereport b WHERE b.aarchers = :aarchers")
	, @NamedQuery(name = "Battlereport.findByAspears", query = "SELECT b FROM Battlereport b WHERE b.aspears = :aspears")
	, @NamedQuery(name = "Battlereport.findByAknights", query = "SELECT b FROM Battlereport b WHERE b.aknights = :aknights")
	, @NamedQuery(name = "Battlereport.findByAwizards", query = "SELECT b FROM Battlereport b WHERE b.awizards = :awizards")
	, @NamedQuery(name = "Battlereport.findByDarchers", query = "SELECT b FROM Battlereport b WHERE b.darchers = :darchers")
	, @NamedQuery(name = "Battlereport.findByDspears", query = "SELECT b FROM Battlereport b WHERE b.dspears = :dspears")
	, @NamedQuery(name = "Battlereport.findByDknights", query = "SELECT b FROM Battlereport b WHERE b.dknights = :dknights")
	, @NamedQuery(name = "Battlereport.findByDwizards", query = "SELECT b FROM Battlereport b WHERE b.dwizards = :dwizards")
	, @NamedQuery(name = "Battlereport.findByAlossarchers", query = "SELECT b FROM Battlereport b WHERE b.alossarchers = :alossarchers")
	, @NamedQuery(name = "Battlereport.findByAlossspears", query = "SELECT b FROM Battlereport b WHERE b.alossspears = :alossspears")
	, @NamedQuery(name = "Battlereport.findByAlossknights", query = "SELECT b FROM Battlereport b WHERE b.alossknights = :alossknights")
	, @NamedQuery(name = "Battlereport.findByAlosswizards", query = "SELECT b FROM Battlereport b WHERE b.alosswizards = :alosswizards")
	, @NamedQuery(name = "Battlereport.findByDlossarchers", query = "SELECT b FROM Battlereport b WHERE b.dlossarchers = :dlossarchers")
	, @NamedQuery(name = "Battlereport.findByDlossspears", query = "SELECT b FROM Battlereport b WHERE b.dlossspears = :dlossspears")
	, @NamedQuery(name = "Battlereport.findByDlossknights", query = "SELECT b FROM Battlereport b WHERE b.dlossknights = :dlossknights")
	, @NamedQuery(name = "Battlereport.findByDlosswizards", query = "SELECT b FROM Battlereport b WHERE b.dlosswizards = :dlosswizards")
	, @NamedQuery(name = "Battlereport.findByDlosstowers", query = "SELECT b FROM Battlereport b WHERE b.dlosstowers = :dlosstowers")
	, @NamedQuery(name = "Battlereport.findByDlossmines", query = "SELECT b FROM Battlereport b WHERE b.dlossmines = :dlossmines")
	, @NamedQuery(name = "Battlereport.findByDlossmarkets", query = "SELECT b FROM Battlereport b WHERE b.dlossmarkets = :dlossmarkets")
	, @NamedQuery(name = "Battlereport.findByDlossfarms", query = "SELECT b FROM Battlereport b WHERE b.dlossfarms = :dlossfarms")})
public class Battlereport implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Column(name = "BATTLEDATE")
    @Temporal(TemporalType.DATE)
	private Date battledate;
	@Column(name = "ATTACKERWIN")
	private Boolean attackerwin;
	@Basic(optional = false)
    @NotNull
    @Column(name = "GOLDGAIN")
	private int goldgain;
	@Basic(optional = false)
    @NotNull
    @Column(name = "LANDGAIN")
	private int landgain;
	@Basic(optional = false)
    @NotNull
    @Column(name = "PEASANTGAIN")
	private int peasantgain;
	@Column(name = "AARCHERS")
	private Integer aarchers;
	@Column(name = "ASPEARS")
	private Integer aspears;
	@Column(name = "AKNIGHTS")
	private Integer aknights;
	@Column(name = "AWIZARDS")
	private Integer awizards;
	@Column(name = "DARCHERS")
	private Integer darchers;
	@Column(name = "DSPEARS")
	private Integer dspears;
	@Column(name = "DKNIGHTS")
	private Integer dknights;
	@Column(name = "DWIZARDS")
	private Integer dwizards;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ALOSSARCHERS")
	private int alossarchers;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ALOSSSPEARS")
	private int alossspears;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ALOSSKNIGHTS")
	private int alossknights;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ALOSSWIZARDS")
	private int alosswizards;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSARCHERS")
	private int dlossarchers;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSSPEARS")
	private int dlossspears;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSKNIGHTS")
	private int dlossknights;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSWIZARDS")
	private int dlosswizards;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSTOWERS")
	private int dlosstowers;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSMINES")
	private int dlossmines;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSMARKETS")
	private int dlossmarkets;
	@Basic(optional = false)
    @NotNull
    @Column(name = "DLOSSFARMS")
	private int dlossfarms;
	@JoinColumn(name = "DEFENDER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Fiefdom defender;
	@JoinColumn(name = "ATTACKER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Fiefdom attacker;

	public Battlereport() {
	}

	public Battlereport(Integer id) {
		this.id = id;
	}
	
	public Battlereport(Date date, Fiefdom a, Fiefdom d) {
		this.battledate = date;
		
		attacker = a;
		defender = d;
		
		this.attackerwin = false;
		
		this.goldgain = 0;
		this.landgain = 0;
		this.peasantgain = 0;
		
		this.alossarchers = 0;
		this.alossspears = 0;
		this.alossknights = 0;
		this.alosswizards = 0;
		
		this.dlossarchers = 0;
		this.dlossspears = 0;
		this.dlossknights = 0;
		this.dlosswizards = 0;
		
		this.dlosstowers = 0;
		this.dlossmines = 0;
		this.dlossmarkets = 0;
		this.dlossfarms = 0;
	}

	public Battlereport(Integer id, int goldgain, int landgain, int peasantgain, int alossarchers, int alossspears, int alossknights, int alosswizards, int dlossarchers, int dlossspears, int dlossknights, int dlosswizards, int dlosstowers, int dlossmines, int dlossmarkets, int dlossfarms) {
		this.id = id;
		this.goldgain = goldgain;
		this.landgain = landgain;
		this.peasantgain = peasantgain;
		this.alossarchers = alossarchers;
		this.alossspears = alossspears;
		this.alossknights = alossknights;
		this.alosswizards = alosswizards;
		this.dlossarchers = dlossarchers;
		this.dlossspears = dlossspears;
		this.dlossknights = dlossknights;
		this.dlosswizards = dlosswizards;
		this.dlosstowers = dlosstowers;
		this.dlossmines = dlossmines;
		this.dlossmarkets = dlossmarkets;
		this.dlossfarms = dlossfarms;
	}
	
	public void setInitialArmies(int aarchers, int aspears, int aknights, int awizards, int darchers, int  dspears, int dknights, int dwizards) {
		this.aarchers = aarchers;
		this.aspears = aspears;
		this.aknights = aknights;
		this.awizards = awizards;
		
		this.darchers = darchers;
		this.dspears = dspears;
		this.dknights = dknights;
		this.dwizards = dwizards;
	}

	public Integer getId() {
		return id;
	}

	public Date getBattledate() {
		return battledate;
	}

	public void setBattledate(Date battledate) {
		this.battledate = battledate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAttackerwin() {
		return attackerwin;
	}

	public void setAttackerwin(Boolean attackerwin) {
		this.attackerwin = attackerwin;
	}

	public int getGoldgain() {
		return goldgain;
	}

	public void setGoldgain(int goldgain) {
		this.goldgain = goldgain;
	}

	public int getLandgain() {
		return landgain;
	}

	public void setLandgain(int landgain) {
		this.landgain = landgain;
	}

	public int getPeasantgain() {
		return peasantgain;
	}

	public void setPeasantgain(int peasantgain) {
		this.peasantgain = peasantgain;
	}

	public Integer getAarchers() {
		return aarchers;
	}

	public void setAarchers(Integer aarchers) {
		this.aarchers = aarchers;
	}

	public Integer getAspears() {
		return aspears;
	}

	public void setAspears(Integer aspears) {
		this.aspears = aspears;
	}

	public Integer getAknights() {
		return aknights;
	}

	public void setAknights(Integer aknights) {
		this.aknights = aknights;
	}

	public Integer getAwizards() {
		return awizards;
	}

	public void setAwizards(Integer awizards) {
		this.awizards = awizards;
	}

	public Integer getDarchers() {
		return darchers;
	}

	public void setDarchers(Integer darchers) {
		this.darchers = darchers;
	}

	public Integer getDspears() {
		return dspears;
	}

	public void setDspears(Integer dspears) {
		this.dspears = dspears;
	}

	public Integer getDknights() {
		return dknights;
	}

	public void setDknights(Integer dknights) {
		this.dknights = dknights;
	}

	public Integer getDwizards() {
		return dwizards;
	}

	public void setDwizards(Integer dwizards) {
		this.dwizards = dwizards;
	}

	public int getAlossarchers() {
		return alossarchers;
	}

	public void setAlossarchers(int alossarchers) {
		this.alossarchers = alossarchers;
	}

	public int getAlossspears() {
		return alossspears;
	}

	public void setAlossspears(int alossspears) {
		this.alossspears = alossspears;
	}

	public int getAlossknights() {
		return alossknights;
	}

	public void setAlossknights(int alossknights) {
		this.alossknights = alossknights;
	}

	public int getAlosswizards() {
		return alosswizards;
	}

	public void setAlosswizards(int alosswizards) {
		this.alosswizards = alosswizards;
	}

	public int getDlossarchers() {
		return dlossarchers;
	}

	public void setDlossarchers(int dlossarchers) {
		this.dlossarchers = dlossarchers;
	}

	public int getDlossspears() {
		return dlossspears;
	}

	public void setDlossspears(int dlossspears) {
		this.dlossspears = dlossspears;
	}

	public int getDlossknights() {
		return dlossknights;
	}

	public void setDlossknights(int dlossknights) {
		this.dlossknights = dlossknights;
	}

	public int getDlosswizards() {
		return dlosswizards;
	}

	public void setDlosswizards(int dlosswizards) {
		this.dlosswizards = dlosswizards;
	}

	public int getDlosstowers() {
		return dlosstowers;
	}

	public void setDlosstowers(int dlosstowers) {
		this.dlosstowers = dlosstowers;
	}

	public int getDlossmines() {
		return dlossmines;
	}

	public void setDlossmines(int dlossmines) {
		this.dlossmines = dlossmines;
	}

	public int getDlossmarkets() {
		return dlossmarkets;
	}

	public void setDlossmarkets(int dlossmarkets) {
		this.dlossmarkets = dlossmarkets;
	}

	public int getDlossfarms() {
		return dlossfarms;
	}

	public void setDlossfarms(int dlossfarms) {
		this.dlossfarms = dlossfarms;
	}

	public Fiefdom getDefender() {
		return defender;
	}

	public void setDefender(Fiefdom defender) {
		this.defender = defender;
	}

	public Fiefdom getAttacker() {
		return attacker;
	}

	public void setAttacker(Fiefdom attacker) {
		this.attacker = attacker;
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
		if (!(object instanceof Battlereport)) {
			return false;
		}
		Battlereport other = (Battlereport) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Battlereport[ id=" + id + " ]";
	}
	
}
