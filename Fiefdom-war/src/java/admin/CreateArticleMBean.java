package admin;

import dto.ArticleFullDTO;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import stateless.NewsBeanRemote;

@Named(value = "createArticleMBean")
@RequestScoped
public class CreateArticleMBean {
	
	@EJB
	NewsBeanRemote newsBean;
	
	@NotBlank
	@Size(max = 20, message = "Title cannot be greater than 20 characters long")
	private String title;
	
	@NotBlank
	@Size(max = 2147483647, message = "Article cannot be greater than 2,147,483,647 characters long")
	private String body;

	public String createArticle() {
		ArticleFullDTO a = new ArticleFullDTO(new Date(), sanitize(title), body);
		
		if (newsBean.addArticle(a))
			return "Success";
		else return "Failure";
	}
	
	//validators
	public void isUniqueTitle(FacesContext context, UIComponent component, Object value) {
		String title = sanitize((String)value);

		if (title != null && newsBean.articleTitleExists(title)) {
			String message = "Another article with found with title: " + title;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	private String sanitize(String s) {
		//strip everything out except alpha numberic and spaces
		return s.replaceAll("[^a-zA-Z0-9 ]", "");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
