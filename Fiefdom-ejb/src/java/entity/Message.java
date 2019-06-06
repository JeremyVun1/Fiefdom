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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
	, @NamedQuery(name = "Message.findByMid", query = "SELECT m FROM Message m WHERE m.mid = :mid")
	, @NamedQuery(name = "Message.findByTid", query = "SELECT m FROM Message m WHERE m.tid = :tid")
	, @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message")})
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MID")
	private Integer mid;
	@Size(max = 1000)
    @Column(name = "MESSAGE")
	private String message;
	@JoinColumn(name = "AUTHOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Fiefdom author;
	@JoinColumn(name = "TID", referencedColumnName = "TID")
    @ManyToOne(optional = false)
	private Thread tid;
	@Basic(optional = false)
    @NotNull
    @Column(name = "POSTED")
    @Temporal(TemporalType.DATE)
	private Date posted;

	public Message() { }
	
	public Message(Date posted, Fiefdom author, String message, Thread tid) {
		this.posted = posted;
		this.tid = tid;
		this.author = author;
		this.message = message;
	}

	public Message(Integer mid) {
		this.mid = mid;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Fiefdom getAuthor() {
		return author;
	}

	public void setAuthor(Fiefdom author) {
		this.author = author;
	}

	public Thread getTid() {
		return tid;
	}

	public void setTid(Thread tid) {
		this.tid = tid;
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
		hash += (mid != null ? mid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Message)) {
			return false;
		}
		Message other = (Message) object;
		if ((this.mid == null && other.mid != null) || (this.mid != null && !this.mid.equals(other.mid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Message[ mid=" + mid + " ]";
	}
	
}
