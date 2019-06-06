package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ARTICLE")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
	, @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id")
	, @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title")
	, @NamedQuery(name = "Article.findByPosted", query = "SELECT a FROM Article a WHERE a.posted = :posted")})
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Size(max = 30)
    @Column(name = "TITLE")
	private String title;
	@Basic(optional = false)
    @NotNull
    @Column(name = "POSTED")
    @Temporal(TemporalType.DATE)
	private Date posted;
	@Lob
    @Column(name = "BODY")
	private String body;

	public Article() {
	}

	public Article(Integer id) {
		this.id = id;
	}

	public Article(Date posted, String title, String body) {
		this.posted = posted;
		this.title = title;
		this.body = body;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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
		if (!(object instanceof Article)) {
			return false;
		}
		Article other = (Article) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Article[ id=" + id + " ]";
	}
	
}
