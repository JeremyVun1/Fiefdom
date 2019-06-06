package admin;

import dto.RacesDTO;
import dto.UserinfoBasicDTO;
import dto.UserinfoFullDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.inject.Inject;
import stateless.UserinfoManagementBeanRemote;
import stateless.GameInfoBeanRemote;

@Named(value = "viewUserMBean")
@ConversationScoped
public class ViewUserMBean implements Serializable {
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	GameInfoBeanRemote infoBean;
	
	@Inject
	private Conversation conversation;

	private int id;
	private String fiefdomName;
	private String username;
	private String email;
	private String roleGroup;
	private String race;
	
	private RacesDTO racesDTO;
	private Map<String, Integer> races;
	private ArrayList<UserinfoBasicDTO> users;

	@PostConstruct
	private void init() {
		if (conversation.isTransient())
			conversation.begin();
		
		users = userinfoBean.fetchUsersBasic();
		
		racesDTO = infoBean.getRaces();
		races = racesDTO.getRaces();
	}
	
	public void chooseUserById() {
		if (id == 0)
			return;
		
		UserinfoFullDTO u = userinfoBean.fetchUserFull(id);
		
		if (u == null)
			return;
		else populate(u);
	}
	
	public void chooseUserByUsername() {
		if (username == null)
			return;
		
		UserinfoFullDTO u = userinfoBean.fetchUserFull(username);
		
		if (u == null)
			return;
		else populate(u);
	}
	
	private void populate(UserinfoFullDTO u) {		
		fiefdomName = u.getFiefdomName();
		username = u.getUsername();
		email = u.getEmail();
		roleGroup = u.getRolegroup();
		race = u.getRace();
		
		if (!conversation.isTransient())
			conversation.end();
	}
	
	//VALIDATORS
	public void isExistingUser(FacesContext context, UIComponent component, Object value) {
		String uName = (String) value;

		if (uName != null && !userinfoBean.usernameExists(uName)) {
			String message = "username " + uName + " not found";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}

	//GETTERS AND SETTES
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<UserinfoBasicDTO> getUsers() {
		return users;
	}

	public String getFiefdomName() {
		return fiefdomName;
	}

	public void setFiefdomName(String fiefdomName) {
		this.fiefdomName = fiefdomName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public Map<String, Integer> getRaces() {
		return races;
	}

	public void setRaces(Map<String, Integer> races) {
		this.races = races;
	}
	
}
