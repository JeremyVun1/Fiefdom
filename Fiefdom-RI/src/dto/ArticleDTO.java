package dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDTO implements Serializable {
	protected int id;
	protected Date posted;
	protected String title;
	
	public ArticleDTO(int id, Date posted, String title) {
		this.id = id;
		this.posted = posted;
		this.title = title;
	}

	public int getId() {
		return id;
	}
	
	public String ddmmyyyy() {
		return new SimpleDateFormat("dd-MM-yyyy").format(posted);
	}

	public Date getPosted() {
		return posted;
	}

	public String getTitle() {
		return title;
	}
	
	public String getLink() {
		String result = "/Fiefdom-war/article/article.xhtml?id=";
		result += title.replace(' ', '-');
		return result;
	}
}
