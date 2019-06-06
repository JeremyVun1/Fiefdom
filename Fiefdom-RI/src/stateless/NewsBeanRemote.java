package stateless;

import dto.ArticleDTO;
import dto.ArticleFullDTO;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface NewsBeanRemote {
	public boolean addArticle(ArticleFullDTO dto);
	
	public boolean removeArticle(int id);
	
	public ArrayList<ArticleDTO> fetchArticleLinks(int n);
	
	public ArticleFullDTO fetchFullArticleByTitle(String title);
	
	public ArticleFullDTO fetchArticleFullById(int id);
	
	public boolean articleTitleExists(String title);
	public boolean articleExists(int id);
}
