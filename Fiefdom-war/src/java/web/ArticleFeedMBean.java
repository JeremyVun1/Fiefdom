package web;

import dto.ArticleDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.NewsBeanRemote;

@Named(value = "articleFeedMBean")
@RequestScoped
public class ArticleFeedMBean {
	
	@EJB
	NewsBeanRemote news;
	
	private ArrayList<ArticleDTO> articleShorts;
	
	@PostConstruct
	private void init() {
		int numArticleToDisplay = 5;
		articleShorts = news.fetchArticleLinks(numArticleToDisplay);
	}

	public ArrayList<ArticleDTO> getArticleShorts() {
		return articleShorts;
	}
	
}
