package admin;

import dto.UserinfoBasicDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import stateless.UserinfoManagementBeanRemote;

@Named(value = "suspendUserMBean")
@RequestScoped
public class SuspendUserMBean {
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;

	private String username;	
	private ArrayList<UserinfoBasicDTO> unsuspended;
	private ArrayList<UserinfoBasicDTO> suspended;

	@PostConstruct
	private void init() {
		unsuspended = userinfoBean.fetchUnsuspendedUsersBasic();
		suspended = userinfoBean.fetchSuspendedUsersBasic();
	}
	
	public String suspendUser() {
		if (userinfoBean.suspendUser(username))
			return "SuspendSuccess";
		return "SuspendFailure";
	}
	
	public String unsuspendUser() {
		if (userinfoBean.unsuspendUser(username))
			return "UnsuspendSuccess";
		return "UnsuspendFailure";
	}
	
	//VALIDATOR
	public void isExistingUser(FacesContext context, UIComponent component, Object value) {
		String uName = (String) value;

		if (uName != null && !userinfoBean.usernameExists(uName)) {
			String message = uName + " cannot be found";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	//GETTES AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<UserinfoBasicDTO> getUnsuspended() {
		return unsuspended;
	}

	public void setUnsuspended(ArrayList<UserinfoBasicDTO> unsuspended) {
		this.unsuspended = unsuspended;
	}

	public ArrayList<UserinfoBasicDTO> getSuspended() {
		return suspended;
	}

	public void setSuspended(ArrayList<UserinfoBasicDTO> suspended) {
		this.suspended = suspended;
	}
	
}
