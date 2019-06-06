package web;

import dto.ArticleFullDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import stateless.NewsBeanRemote;

@Named(value = "articleMBean")
@RequestScoped
public class ArticleMBean {
	
	@EJB
	NewsBeanRemote newsBean;
	
	private ArticleFullDTO article;

	@PostConstruct
	private void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String title = req.getParameter("id");

		if (title == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
			} catch(Exception ex) {}
		}
		else {
			title = title.replaceAll("-", " ");
			article = newsBean.fetchFullArticleByTitle(title);
			
			if (article == null) {
				try {
					System.out.println("Article is null");
					FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
				} catch(Exception ex) {}
			}
		}
	}
	
	public String getTitle() {
		if (article == null)
			return "Article does not exist";

		String t = article.getTitle();
		return (t.substring(0, 1).toUpperCase() + t.substring(1));
	}
	
	public String getBody() {
		if (article == null)
			return "";
		
		return article.getBody();
	}
	
	public String getPosted() {
		if (article == null)
			return "";
		
		return article.ddmmyyyy();
	}
}
