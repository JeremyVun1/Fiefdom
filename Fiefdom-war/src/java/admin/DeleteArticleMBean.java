package admin;

import dto.ArticleDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.NewsBeanRemote;

@Named(value = "deleteArticleMBean")
@RequestScoped
public class DeleteArticleMBean {
	
	@EJB
	NewsBeanRemote newsBean;

	private int id;
	private String title;
	
	private ArrayList<ArticleDTO> articles;

	@PostConstruct
	public void allArticles() {
		articles = newsBean.fetchArticleLinks(-1);
	}

	public String deleteArticle() {
		if (newsBean.removeArticle(id))
			return "Success";
		return "Falure";
	}
	
	private String sanitize(String s) {
		//strip everything out except alpha numberic and spaces
		return s.replaceAll("[^a-zA-Z0-9 ]", "");
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public ArrayList<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticleDTO> articles) {
		this.articles = articles;
	}
}
