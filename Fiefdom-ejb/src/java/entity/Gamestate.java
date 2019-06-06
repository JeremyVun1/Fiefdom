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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JVun
 */
@Entity
@Table(name = "GAMESTATE")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Gamestate.findAll", query = "SELECT g FROM Gamestate g")
	, @NamedQuery(name = "Gamestate.findById", query = "SELECT g FROM Gamestate g WHERE g.id = :id")
	, @NamedQuery(name = "Gamestate.findByTurncount", query = "SELECT g FROM Gamestate g WHERE g.turncount = :turncount")})
public class Gamestate implements Serializable {

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

	public Gamestate() {
	}

	public Gamestate(Integer id) {
		this.id = id;
	}

	public Gamestate(Integer id, int turncount) {
		this.id = id;
		this.turncount = turncount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTurncount() {
		return turncount;
	}

	public void setTurncount(int turncount) {
		this.turncount = turncount;
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
		if (!(object instanceof Gamestate)) {
			return false;
		}
		Gamestate other = (Gamestate) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Gamestate[ id=" + id + " ]";
	}
	
}
