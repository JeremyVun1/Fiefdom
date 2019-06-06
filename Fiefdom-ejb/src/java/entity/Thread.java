package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "THREAD")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Thread.findAll", query = "SELECT t FROM Thread t")
	, @NamedQuery(name = "Thread.findByTid", query = "SELECT t FROM Thread t WHERE t.tid = :tid")
	, @NamedQuery(name = "Thread.findByToId", query = "SELECT t FROM Thread t WHERE t.toid = :toid")
	, @NamedQuery(name = "Thread.findByFromId", query = "SELECT t FROM Thread t WHERE t.fromid = :fromid")
	, @NamedQuery(name = "Thread.findBySubject", query = "SELECT t FROM Thread t WHERE t.subject = :subject")
	, @NamedQuery(name = "Thread.findByThreadtype", query = "SELECT t FROM Thread t WHERE t.threadtype = :threadtype")})
public class Thread implements Serializable {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tid")
	private Collection<Message> messageCollection;

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
	private Integer tid;
	@Basic(optional = false)
    @NotNull
    @Column(name = "POSTED")
    @Temporal(TemporalType.DATE)
	private Date posted;
	@Size(max = 15)
    @Column(name = "SUBJECT")
	private String subject;
	@Size(max = 2)
    @Column(name = "THREADTYPE")
	private String threadtype;
	@Column(name = "ACTIVETO")
	private boolean activeto;
	@Column(name = "ACTIVEFROM")
	private boolean activefrom;
	@Column(name = "RESPONDED")
	private boolean responded;
	@JoinColumn(name = "TOID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Fiefdom toid;
	@JoinColumn(name = "FROMID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Fiefdom fromid;

	public Thread() {
	}

	public Thread(Integer tid) {
		this.tid = tid;
	}
	
	public Thread(Fiefdom from, Fiefdom to, Date posted, String subject, String type) {
		this.fromid = from;
		this.toid = to;
		this.posted = posted;
		this.subject = subject;
		this.threadtype = type;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getThreadtype() {
		return threadtype;
	}

	public void setThreadtype(String threadtype) {
		this.threadtype = threadtype;
	}

	public Fiefdom getToid() {
		return toid;
	}

	public void setToid(Fiefdom toid) {
		this.toid = toid;
	}

	public Fiefdom getFromid() {
		return fromid;
	}

	public void setFromid(Fiefdom fromid) {
		this.fromid = fromid;
	}

	public boolean isActiveto() {
		return activeto;
	}

	public void setActiveto(boolean activeto) {
		this.activeto = activeto;
	}

	public boolean isActivefrom() {
		return activefrom;
	}

	public void setActivefrom(boolean activefrom) {
		this.activefrom = activefrom;
	}

	public boolean isResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Thread)) {
			return false;
		}
		Thread other = (Thread) object;
		if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Thread[ tid=" + tid + " ]";
	}

	@XmlTransient
	public Collection<Message> getMessageCollection() {
		return messageCollection;
	}

	public void setMessageCollection(Collection<Message> messageCollection) {
		this.messageCollection = messageCollection;
	}
	
}
