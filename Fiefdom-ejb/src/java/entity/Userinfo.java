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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "USERINFO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Userinfo.findAll", query = "SELECT u FROM Userinfo u")
	, @NamedQuery(name = "Userinfo.findById", query = "SELECT u FROM Userinfo u WHERE u.id = :id")
	, @NamedQuery(name = "Userinfo.findByUsername", query = "SELECT u FROM Userinfo u WHERE u.username = :username")
	, @NamedQuery(name = "Userinfo.findByPassword", query = "SELECT u FROM Userinfo u WHERE u.password = :password")
	, @NamedQuery(name = "Userinfo.findByRolegroup", query = "SELECT u FROM Userinfo u WHERE u.rolegroup = :rolegroup")
	, @NamedQuery(name = "Userinfo.findByEmail", query = "SELECT u FROM Userinfo u WHERE u.email = :email")
	, @NamedQuery(name = "Userinfo.findByLoggedin", query = "SELECT u FROM Userinfo u WHERE u.loggedin = :loggedin")})
public class Userinfo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USERNAME")
	private String username;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "PASSWORD")
	private String password;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ROLEGROUP")
	private String rolegroup;
	// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMAIL")
	private String email;
	@Column(name = "LOGGEDIN")
	private Boolean loggedin;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "uid")
	private Collection<Fiefdom> fiefdomCollection;

	public Userinfo() {
	}

	public Userinfo(Integer id) {
		this.id = id;
	}

	public Userinfo(String username, String password, String rolegroup, String email) {
		this.username = username;
		this.password = password;
		this.rolegroup = rolegroup;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRolegroup() {
		return rolegroup;
	}

	public void setRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getLoggedin() {
		return loggedin;
	}

	public void setLoggedin(Boolean loggedin) {
		this.loggedin = loggedin;
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
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Userinfo)) {
			return false;
		}
		Userinfo other = (Userinfo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Userinfo[ id=" + id + " ]";
	}
	
}
