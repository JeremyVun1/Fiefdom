package dto;

import java.io.Serializable;
import java.util.Date;

public class ArticleFullDTO extends ArticleDTO implements Serializable {

	private String body;
	
	public ArticleFullDTO(Date posted, String title, String body) {
		super(-1, posted, title);
		this.body = body;
	}

	public ArticleFullDTO(int id, Date posted, String title, String body) {
		super(id, posted, title);
		this.body = body;
	}

	public String getBody() {
		return body;
	}
	
}
