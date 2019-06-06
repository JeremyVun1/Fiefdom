package stateless;

import DAFacades.NewsFacade;
import dto.ArticleDTO;
import dto.ArticleFullDTO;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NewsBean implements NewsBeanRemote {
	
	@EJB
	NewsFacade newsFacade;
	
	@Override
	public boolean addArticle(ArticleFullDTO dto) {
		return newsFacade.addArticle(dto);
	}
	
	@Override
	public boolean removeArticle(int id) {
		if (newsFacade.find(id) == null)
			return false;
		
		return newsFacade.removeArticle(id);
	}
	
	@Override
	public boolean articleTitleExists(String title) {
		return newsFacade.titleExists(title);
	}
	
	@Override
	public boolean articleExists(int id) {
		return newsFacade.articleExists(id);
	}
	
	@Override
	public ArrayList<ArticleDTO> fetchArticleLinks(int n) {
		return newsFacade.fetchArticleLinks(n);
	}
	
	@Override
	public ArticleFullDTO fetchFullArticleByTitle(String title) {
		return newsFacade.fetchByTitle(title);
	}
	
	@Override
	public ArticleFullDTO fetchArticleFullById(int id) {
		return newsFacade.fetchById(id);
	}
}
