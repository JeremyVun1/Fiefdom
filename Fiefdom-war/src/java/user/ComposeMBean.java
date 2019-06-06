package user;

import dto.CreateThreadDTO;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import stateless.DiplomacyBeanRemote;
import stateless.FiefdomBeanRemote;
import web.SessionMBean;

@Named(value = "composeMBean")
@RequestScoped
public class ComposeMBean {
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	@EJB
	FiefdomBeanRemote fiefdomBean;
	
	@Inject
	SessionMBean sessBean;
	
	private int to;
	
	@NotBlank
	@Size(max = 15, message = "Subject cannot be greater than 15 characters long")
	private String subject;
	
	@NotBlank
	@Size(max = 1000, message = "Body cannot be greater than 1000 characters long")
	private String body;

	private String type;
	
	public String send() {
		CreateThreadDTO sendDTO = new CreateThreadDTO(subject, body, sessBean.getfId(), to, type);
		if (diplomacyBean.startThread(sendDTO))
			return "Success";
		else return "Failure";
	}
	
	//VALIDATORS
	public void isValidFiefdomId(FacesContext context, UIComponent component, Object value) {
		int fId = (int) value;

		if (!fiefdomBean.fiefdomExists(fId)) {
			String message = "Fiefdom #" + fId + " does not exist!";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
		else if (!fiefdomBean.isUsersFiefdom(fId)) {
			String message = "Cannot send message to yourself!";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	public void isValidType(FacesContext context, UIComponent component, Object value) {
		String msgType = (String)value;
		UIInput toComponent = (UIInput) component.getAttributes().get("toFId");
		int toFId = Integer.parseInt((String)toComponent.getSubmittedValue());
		
		//check if the message type is valid e.g. cannot declare war with a fiefdom if you are already at war with them
		if (fiefdomBean.fiefdomExists(toFId)) {
			if (msgType.equals("RA") && diplomacyBean.isAllyWith(toFId)) {
				String message = "You are already allied with Fiefdoom #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
			else if (msgType.equals("RA") && diplomacyBean.isAtWarWith(toFId)) {
				String message = "You are already at war with Fiefdoom #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
			else if (msgType.equals("BA") && !diplomacyBean.isAllyWith(toFId)) {
				String message = "You do not have an alliance with Fiefdoom #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
			else if (msgType.equals("DW") && diplomacyBean.isAtWarWith(toFId)) {
				String message = "You are already at war with Fiefdoom #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
			else if (msgType.equals("DW") && diplomacyBean.isAllyWith(toFId)) {
				String message = "You cannot declare war on an ally #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
			else if (msgType.equals("RP") && !diplomacyBean.isAtWarWith(toFId)) {
				String message = "You are not at war with Fiefdoom #" + toFId;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(msg);
			}
		}
	}

	//GETTER SETTER SPAM
	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public HashMap<String, String> getMessageTypes() {
		return diplomacyBean.getMessageTypes();
	}
}
