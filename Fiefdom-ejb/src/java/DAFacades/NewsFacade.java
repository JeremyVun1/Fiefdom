package DAFacades;

import dto.ArticleDTO;
import dto.ArticleFullDTO;
import entity.Article;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class NewsFacade {

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;

	public Article find(int id) {
		return em.find(Article.class, id);
	}

	public boolean removeArticle(int id) {
		Article a = find(id);

		if (a == null) {
			return false;
		}

		em.remove(a);
		return true;
	}

	public ArticleFullDTO fetchByTitle(String title) {
		Query q = em.createNamedQuery("Article.findByTitle").setParameter("title", title);

		List<Article> a = q.getResultList();
		if (a.isEmpty()) {
			return null;
		}

		return DAO2DTOFull(a.get(0));
	}

	public ArticleFullDTO fetchById(int id) {
		Article a = em.find(Article.class, id);

		if (a == null) {
			return null;
		}

		return DAO2DTOFull(a);
	}

	//get n most recent articles
	public ArrayList<ArticleDTO> fetchArticleLinks(int n) {
		ArrayList<ArticleDTO> result = new ArrayList<ArticleDTO>();

		String qStr = "SELECT a FROM Article a order by a.posted";
		Query q = em.createQuery(qStr);
		
		//limit results to search for
		if(n >0)
			q.setMaxResults(n);
		
		List<Article> qResult = q.getResultList();

		for (Article a : qResult) {
			result.add(DAO2DTOShort(a));
		}

		return result;
	}
	
	public boolean titleExists(String title) {
		title = sanitize(title);
		
		if (fetchByTitle(title) == null)
			return false;
		return true;
	}
	
	public boolean articleExists(int id) {
		return (find(id) != null);
	}

	public boolean addArticle(ArticleFullDTO dto) {
		try {
			Article a = DTOFull2DAO(dto);
			em.merge(a);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private Article DTOFull2DAO(ArticleFullDTO dto) {
		String title = sanitize(dto.getTitle()); //make sure no funny business in title	
		return new Article(dto.getPosted(), title, dto.getBody());
	}

	private ArticleDTO DAO2DTOShort(Article a) {
		return new ArticleDTO(a.getId(), a.getPosted(), a.getTitle());
	}

	private ArticleFullDTO DAO2DTOFull(Article a) {
		return new ArticleFullDTO(a.getId(), a.getPosted(), a.getTitle(), a.getBody());
	}

	private String sanitize(String s) {
		//alpha numeric
		return s.replaceAll("[^a-zA-Z0-9 ]", "");
	}
}
