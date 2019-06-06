package user;

import dto.MessageDTO;
import dto.ThreadDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import stateless.DiplomacyBeanRemote;
import stateless.FiefdomBeanRemote;
import web.SessionMBean;

@Named(value = "viewThreadMBean")
@ConversationScoped
public class ViewThreadMBean implements Serializable {
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	@EJB
	FiefdomBeanRemote fiefdomBean;
	
	@Inject
	SessionMBean sessBean;
	
	@Inject
	private Conversation conversation;
	
	private ThreadDTO thread;
	private ArrayList<MessageDTO> messages;
	
	private boolean responseRequired;
	
	@NotBlank
	@Size(max = 1000, message = "Body cannot be greater than 1000 characters long")
	private String body;

	@PostConstruct
	private void init() {
		if (conversation.isTransient())
			conversation.begin();

		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		try {
			int tId = Integer.parseInt(req.getParameter("id"));
			
			thread = diplomacyBean.getThread(tId);
			messages = diplomacyBean.getThreadMessages(tId);
			
			responseRequired = (thread.getFromId() == sessBean.getfId()) ? false : !thread.isResponded();
		} catch (Exception ex1) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
			} catch (Exception ex2) { }
		}
	}
	
	public String reply() {
		if (diplomacyBean.sendMessage(thread, body)) {
			conversation.end();
			return "Success";
		}			
		else {
			conversation.end();
			return "Failure";
		}
	}
	
	public String accept() {
		if (diplomacyBean.accept(thread)) {
			conversation.end();
			return "Success";
		} else {
			conversation.end();
			return "Failure";
		}
	}
	
	public String decline() {
		if (diplomacyBean.decline(thread)) {
			conversation.end();
			return "Success";
		} else {
			conversation.end();
			return "Failure";
		}
	}
	
	public String delete() {
		if (diplomacyBean.deleteThread(thread)) {
			conversation.end();
			return "DeleteSuccess";
		} else {
			conversation.end();
			return "DeleteFailure";
		}
	}

	public ThreadDTO getThread() {
		return thread;
	}

	public void setThread(ThreadDTO thread) {
		this.thread = thread;
	}

	public ArrayList<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<MessageDTO> messages) {
		this.messages = messages;
	}
	
	public String getThreadAuthor() {
		return fiefdomBean.getFiefdomName(thread.getFromId());
	}
	
	public String getTypeFullStr() {
		return thread.getTypeFullStr();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isResponseRequired() {
		return responseRequired;
	}
}
