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

@Named(value = "deleteUserMBean")
@RequestScoped
public class DeleteUserMBean {
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;

	private String username;
	
	private ArrayList<UserinfoBasicDTO> users;

	@PostConstruct
	private void init() {
		users = userinfoBean.fetchUsersBasic();
	}
	
	public String deleteUser() {
		if (userinfoBean.removeUser(username))
			return "Success";
		return "Failure";
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

	public ArrayList<UserinfoBasicDTO> getUsers() {
		return users;
	}	
	
}
